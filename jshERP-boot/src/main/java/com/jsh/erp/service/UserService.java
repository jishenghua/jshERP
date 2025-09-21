package com.jsh.erp.service;

import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.TenantMapper;
import com.jsh.erp.exception.BusinessParamCheckingException;
import com.jsh.erp.utils.*;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.mappers.UserMapper;
import com.jsh.erp.datasource.mappers.UserMapperEx;
import com.jsh.erp.datasource.vo.TreeNodeEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private UserMapperEx userMapperEx;
    @Resource
    private OrgaUserRelService orgaUserRelService;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;
    @Resource
    private TenantService tenantService;
    @Resource
    private UserBusinessService userBusinessService;
    @Resource
    private RoleService roleService;
    @Resource
    private FunctionService functionService;
    @Resource
    private PlatformConfigService platformConfigService;
    @Resource
    private RedisService redisService;

    @Value("${tenant.userNumLimit}")
    private Integer userNumLimit;

    @Value("${tenant.tryDayLimit}")
    private Integer tryDayLimit;

    public User getUser(long id)throws Exception {
        User result=null;
        try{
            //先校验是否登录，然后才能查询用户数据
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            Long userId = this.getUserId(request);
            if(userId!=null) {
                result = userMapper.selectByPrimaryKey(id);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<User> getUserListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<User> list = new ArrayList<>();
        try{
            UserExample example = new UserExample();
            example.createCriteria().andIdIn(idList);
            list = userMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<User> getUser(HttpServletRequest request) throws Exception {
        List<User> list=null;
        try{
            //先校验是否登录，然后才能查询用户数据
            Long userId = this.getUserId(request);
            if(userId!=null) {
                UserExample example = new UserExample();
                example.createCriteria().andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                list = userMapper.selectByExample(example);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<UserEx> select(String userName, String loginName)throws Exception {
        List<UserEx> list=null;
        try {
            //先校验是否登录，然后才能查询用户数据
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            Long userId = this.getUserId(request);
            if(userId!=null) {
                PageUtils.startPage();
                list = userMapperEx.selectByConditionUser(userName, loginName);
                for (UserEx ue : list) {
                    String userType = "";
                    if (ue.getId().equals(ue.getTenantId())) {
                        userType = "租户";
                    } else if (ue.getTenantId() == null) {
                        userType = "超管";
                    } else {
                        userType = "普通";
                    }
                    ue.setUserType(userType);
                    //是否经理
                    String leaderFlagStr = "";
                    if ("1".equals(ue.getLeaderFlag())) {
                        leaderFlagStr = "是";
                    } else {
                        leaderFlagStr = "否";
                    }
                    ue.setLeaderFlagStr(leaderFlagStr);
                }
            }
        } catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countUser(String userName, String loginName)throws Exception {
        Long result=null;
        try{
            result=userMapperEx.countsByUser(userName, loginName);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertUser(JSONObject obj, HttpServletRequest request)throws Exception {
        User user = JSONObject.parseObject(obj.toJSONString(), User.class);
        String password = "123456";
        //因密码用MD5加密，需要对密码进行转化
        try {
            password = Tools.md5Encryp(password);
            user.setPassword(password);
        } catch (NoSuchAlgorithmException e) {
            logger.error(">>>>>>>>>>>>>>转化MD5字符串错误 ：" + e.getMessage());
        }
        int result=0;
        try{
            result=userMapper.insertSelective(user);
            logService.insertLog("用户",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(user.getLoginName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUser(JSONObject obj, HttpServletRequest request) throws Exception{
        User user = JSONObject.parseObject(obj.toJSONString(), User.class);
        int result=0;
        try{
            //判断是否登录过
            Object userId = redisService.getObjectFromSessionByKey(request,"userId");
            if (userId != null) {
                result = userMapper.updateByPrimaryKeySelective(user);
                logService.insertLog("用户",
                        new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(user.getLoginName()).toString(), request);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUserByObj(User user, HttpServletRequest request) throws Exception{
        int result=0;
        try{
            //判断是否登录过
            Object userId = redisService.getObjectFromSessionByKey(request,"userId");
            if (userId != null) {
                result = userMapper.updateByPrimaryKeySelective(user);
                logService.insertLog("用户",
                        new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(user.getId()).toString(),
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int resetPwd(String md5Pwd, Long id, HttpServletRequest request) throws Exception{
        int result=0;
        User u = getUser(id);
        String loginName = u.getLoginName();
        if("admin".equals(loginName)){
            logger.info("禁止重置超管密码");
        } else {
            User user = new User();
            user.setId(id);
            user.setPassword(md5Pwd);
            try{
                //判断是否登录过
                Object userId = redisService.getObjectFromSessionByKey(request,"userId");
                if (userId != null) {
                    result = userMapper.updateByPrimaryKeySelective(user);
                    logService.insertLog("用户",
                            new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(),
                            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
                }
            }catch(Exception e){
                JshException.writeFail(logger, e);
            }
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteUser(Long id, HttpServletRequest request)throws Exception {
        return batDeleteUser(id.toString(), request);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUser(String ids, HttpServletRequest request)throws Exception {
        return batDeleteUser(ids, request);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batDeleteUser(String ids, HttpServletRequest request) throws Exception{
        int result=0;
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<User> list = getUserListByIds(ids);
        for(User user: list){
            if(user.getId().equals(user.getTenantId())) {
                logger.error("异常码[{}],异常提示[{}],参数,ids:[{}]",
                        ExceptionConstants.USER_LIMIT_TENANT_DELETE_CODE,ExceptionConstants.USER_LIMIT_TENANT_DELETE_MSG,ids);
                throw new BusinessRunTimeException(ExceptionConstants.USER_LIMIT_TENANT_DELETE_CODE,
                        ExceptionConstants.USER_LIMIT_TENANT_DELETE_MSG);
            }
            sb.append("[").append(user.getLoginName()).append("]");
        }
        String[] idsArray =ids.split(",");
        try{
            //判断是否登录过
            Object userId = redisService.getObjectFromSessionByKey(request,"userId");
            if (userId != null) {
                result = userMapperEx.batDeleteOrUpdateUser(idsArray);
                logService.insertLog("用户", sb.toString(),
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        if(result<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids:[{}]",
                    ExceptionConstants.USER_DELETE_FAILED_CODE,ExceptionConstants.USER_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.USER_DELETE_FAILED_CODE,
                    ExceptionConstants.USER_DELETE_FAILED_MSG);
        }
        return result;
    }

    /**
     * 校验验证码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String code, String uuid) throws Exception {
        PlatformConfig platformConfig = platformConfigService.getInfoByKey("checkcode_flag");
        if(platformConfig!=null && "1".equals(platformConfig.getPlatformValue())) {
            if(StringUtil.isNotEmpty(code) && StringUtil.isNotEmpty(uuid)) {
                code = code.trim();
                uuid = uuid.trim();
                String verifyKey = BusinessConstants.CAPTCHA_CODE_KEY + uuid;
                String captcha = redisService.getCacheObject(verifyKey);
                redisService.deleteObject(verifyKey);
                if (captcha == null) {
                    logger.error("异常码[{}],异常提示[{}]", ExceptionConstants.USER_JCAPTCHA_EXPIRE_CODE, ExceptionConstants.USER_JCAPTCHA_EXPIRE_MSG);
                    throw new BusinessRunTimeException(ExceptionConstants.USER_JCAPTCHA_EXPIRE_CODE, ExceptionConstants.USER_JCAPTCHA_EXPIRE_MSG);
                }
                if (!code.equalsIgnoreCase(captcha)) {
                    logger.error("异常码[{}],异常提示[{}]", ExceptionConstants.USER_JCAPTCHA_ERROR_CODE, ExceptionConstants.USER_JCAPTCHA_ERROR_MSG);
                    throw new BusinessRunTimeException(ExceptionConstants.USER_JCAPTCHA_ERROR_CODE, ExceptionConstants.USER_JCAPTCHA_ERROR_MSG);
                }
            } else {
                logger.error("异常码[{}],异常提示[{}]", ExceptionConstants.USER_JCAPTCHA_EMPTY_CODE, ExceptionConstants.USER_JCAPTCHA_EMPTY_MSG);
                throw new BusinessRunTimeException(ExceptionConstants.USER_JCAPTCHA_EMPTY_CODE, ExceptionConstants.USER_JCAPTCHA_EMPTY_MSG);
            }
        }
    }

    /**
     * 用户登录
     * @param loginName
     * @param password
     * @param request
     * @return
     * @throws Exception
     */
    public Map<String, Object> login(String loginName, String password, HttpServletRequest request) throws Exception {
        Map<String, Object> data = new HashMap<>();
        String msgTip = "";
        User user = null;
        //判断用户是否已经登录过，登录过不再处理
        Object userId = redisService.getObjectFromSessionByKey(request,"userId");
        if (userId != null) {
            logger.info("====用户已经登录过, login 方法调用结束====");
            msgTip = "user already login";
        }
        //获取用户状态
        int userStatus = -1;
        try {
            redisService.deleteObjectBySession(request,"userId");
            userStatus = validateUser(loginName, password);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>用户  " + loginName + " 登录 login 方法 访问服务层异常====", e);
            msgTip = "access service exception";
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "") + "";
        switch (userStatus) {
            case ExceptionCodeConstants.UserExceptionCode.USER_NOT_EXIST:
                msgTip = "user is not exist";
                break;
            case ExceptionCodeConstants.UserExceptionCode.USER_PASSWORD_ERROR:
                msgTip = "user password error";
                break;
            case ExceptionCodeConstants.UserExceptionCode.BLACK_USER:
                msgTip = "user is black";
                break;
            case ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION:
                msgTip = "access service error";
                break;
            case ExceptionCodeConstants.UserExceptionCode.BLACK_TENANT:
                msgTip = "tenant is black";
                break;
            case ExceptionCodeConstants.UserExceptionCode.EXPIRE_TENANT:
                msgTip = "tenant is expire";
                break;
            case ExceptionCodeConstants.UserExceptionCode.USER_CONDITION_FIT:
                msgTip = "user can login";
                //验证通过 ，可以登录，放入session，记录登录日志
                user = getUserByLoginName(loginName);
                if(user.getTenantId()!=null) {
                    token = token + "_" + user.getTenantId();
                }
                redisService.storageObjectBySession(token,"userId",user.getId());
                break;
            default:
                break;
        }
        data.put("msgTip", msgTip);
        if(user!=null){
            //校验下密码是不是过于简单
            boolean pwdSimple = false;
            if(user.getPassword().equals(Tools.md5Encryp(BusinessConstants.USER_DEFAULT_PASSWORD))) {
                pwdSimple = true;
            }
            user.setPassword(null);
            if(BusinessConstants.DEFAULT_MANAGER.equals(user.getLoginName())) {
                //如果是管理员，则发送登录邮件
                sendEmailToCurrentUser(request, user);
            }
            redisService.storageObjectBySession(token,"clientIp", Tools.getLocalIp(request));
            logService.insertLogWithUserId(user.getId(), user.getTenantId(), "用户",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_LOGIN).append(user.getLoginName()).toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            data.put("token", token);
            data.put("user", user);
            data.put("pwdSimple", pwdSimple);
        }
        return data;
    }

    public int validateUser(String loginName, String password) throws Exception {
        /**默认是可以登录的*/
        List<User> list = null;
        try {
            UserExample example = new UserExample();
            example.createCriteria().andLoginNameEqualTo(loginName).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            list = userMapper.selectByExample(example);
            if (null != list && list.size() == 0) {
                return ExceptionCodeConstants.UserExceptionCode.USER_NOT_EXIST;
            } else if(list.size() ==1) {
                if(list.get(0).getStatus()!=0) {
                    return ExceptionCodeConstants.UserExceptionCode.BLACK_USER;
                }
                Long tenantId = list.get(0).getTenantId();
                Tenant tenant = tenantService.getTenantByTenantId(tenantId);
                if(tenant!=null) {
                    if(tenant.getEnabled()!=null && !tenant.getEnabled()) {
                        return ExceptionCodeConstants.UserExceptionCode.BLACK_TENANT;
                    }
                    if(tenant.getExpireTime()!=null && tenant.getExpireTime().getTime()<System.currentTimeMillis()){
                        return ExceptionCodeConstants.UserExceptionCode.EXPIRE_TENANT;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(">>>>>>>>访问验证用户姓名是否存在后台信息异常", e);
            return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
        }
        try {
            UserExample example = new UserExample();
            example.createCriteria().andLoginNameEqualTo(loginName).andPasswordEqualTo(password)
                    .andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            list = userMapper.selectByExample(example);
            if (null != list && list.size() == 0) {
                return ExceptionCodeConstants.UserExceptionCode.USER_PASSWORD_ERROR;
            }
        } catch (Exception e) {
            logger.error(">>>>>>>>>>访问验证用户密码后台信息异常", e);
            return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
        }
        return ExceptionCodeConstants.UserExceptionCode.USER_CONDITION_FIT;
    }

    public User getUserByLoginName(String loginName)throws Exception {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(loginName).andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<User> list=null;
        try{
            list= userMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        User user =null;
        if(list!=null&&list.size()>0){
            user = list.get(0);
        }
        return user;
    }

    /**
     * 发送邮件给当前用户
     * @param request
     * @param user
     * @throws Exception
     */
    private void sendEmailToCurrentUser(HttpServletRequest request, User user) throws Exception {
        String platformName = platformConfigService.getPlatformConfigByKey("platform_name").getPlatformValue();
        String emailFrom = platformConfigService.getPlatformConfigByKey("email_from").getPlatformValue();
        String emailAuthCode = platformConfigService.getPlatformConfigByKey("email_auth_code").getPlatformValue();
        String emailSmtpHost = platformConfigService.getPlatformConfigByKey("email_smtp_host").getPlatformValue();
        if(StringUtil.isNotEmpty(emailFrom) && StringUtil.isNotEmpty(emailAuthCode) && StringUtil.isNotEmpty(emailSmtpHost)
                && StringUtil.isNotEmpty(user.getEmail())) {
            String emailSubject = "用户" + user.getLoginName() + "成功登录" + platformName;
            String emailBody = "用户" + user.getLoginName() + "成功登录" + platformName + "，登录时间：" + Tools.getCenternTime(new Date())
                    + "，登录IP：" + Tools.getLocalIp(request);
            platformConfigService.sendEmail(emailFrom, emailAuthCode, emailSmtpHost, user.getEmail(), emailSubject, emailBody);
        }
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        UserExample example = new UserExample();
        List<Byte> userStatus = new ArrayList<>();
        userStatus.add(BusinessConstants.USER_STATUS_NORMAL);
        example.createCriteria().andIdNotEqualTo(id).andLoginNameEqualTo(name).andStatusIn(userStatus)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<User> list=null;
        try{
            list= userMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }
    /**
     * create by: cjl
     * description:
     *  获取当前用户信息
     * create time: 2019/1/24 10:01
     * @Param:
     * @return com.jsh.erp.datasource.entities.User
     */
    public User getCurrentUser()throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Long userId = Long.parseLong(redisService.getObjectFromSessionByKey(request,"userId").toString());
        return getUser(userId);
    }

    /**
     * 根据用户名查询id
     * @param loginName
     * @return
     */
    public Long getIdByLoginName(String loginName) {
        Long userId = 0L;
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(loginName).andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<User> list = userMapper.selectByExample(example);
        if(list!=null) {
            userId = list.get(0).getId();
        }
        return userId;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addUserAndOrgUserRel(UserEx ue, HttpServletRequest request) throws Exception{
        if(BusinessConstants.DEFAULT_MANAGER.equals(ue.getLoginName())) {
            throw new BusinessRunTimeException(ExceptionConstants.USER_NAME_LIMIT_USE_CODE,
                    ExceptionConstants.USER_NAME_LIMIT_USE_MSG);
        } else {
            logService.insertLog("用户",
                    BusinessConstants.LOG_OPERATION_TYPE_ADD,
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            //检查用户名和登录名
            checkLoginName(ue);
            //新增用户信息
            ue= this.addUser(ue);
            if(ue==null){
                logger.error("异常码[{}],异常提示[{}],参数,[{}]",
                        ExceptionConstants.USER_ADD_FAILED_CODE,ExceptionConstants.USER_ADD_FAILED_MSG);
                throw new BusinessRunTimeException(ExceptionConstants.USER_ADD_FAILED_CODE,
                        ExceptionConstants.USER_ADD_FAILED_MSG);
            }
            //用户id，根据用户名查询id
            Long userId = getIdByLoginName(ue.getLoginName());
            if(ue.getRoleId()!=null){
                JSONObject ubObj = new JSONObject();
                ubObj.put("type", "UserRole");
                ubObj.put("keyid", userId);
                ubObj.put("value", "[" + ue.getRoleId() + "]");
                userBusinessService.insertUserBusiness(ubObj, request);
            }
            if(ue.getOrgaId()==null){
                //如果没有选择机构，就不建机构和用户的关联关系
                return;
            }
            if(ue.getOrgaId()!=null && "1".equals(ue.getLeaderFlag())){
                //检查当前机构是否存在经理
                List<User> checkList = userMapperEx.getListByOrgaId(ue.getId(), ue.getOrgaId());
                if(checkList.size()>0) {
                    throw new BusinessRunTimeException(ExceptionConstants.USER_LEADER_IS_EXIST_CODE,
                            ExceptionConstants.USER_LEADER_IS_EXIST_MSG);
                }
            }
            //新增用户和机构关联关系
            OrgaUserRel oul=new OrgaUserRel();
            //机构id
            oul.setOrgaId(ue.getOrgaId());
            oul.setUserId(userId);
            //用户在机构中的排序
            oul.setUserBlngOrgaDsplSeq(ue.getUserBlngOrgaDsplSeq());
            oul=orgaUserRelService.addOrgaUserRel(oul);
            if(oul==null){
                logger.error("异常码[{}],异常提示[{}],参数,[{}]",
                        ExceptionConstants.ORGA_USER_REL_ADD_FAILED_CODE,ExceptionConstants.ORGA_USER_REL_ADD_FAILED_MSG);
                throw new BusinessRunTimeException(ExceptionConstants.ORGA_USER_REL_ADD_FAILED_CODE,
                        ExceptionConstants.ORGA_USER_REL_ADD_FAILED_MSG);
            }
        }
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public UserEx addUser(UserEx ue) throws Exception{
        /**
         * 新增用户默认设置
         * 1、密码默认123456
         * 2是否系统自带默认为非系统自带
         * 3是否管理者默认为员工
         * 4默认用户状态为正常
         * */
        ue.setPassword(Tools.md5Encryp(BusinessConstants.USER_DEFAULT_PASSWORD));
        ue.setIsystem(BusinessConstants.USER_NOT_SYSTEM);
        if(ue.getIsmanager()==null){
            ue.setIsmanager(BusinessConstants.USER_NOT_MANAGER);
        }
        ue.setStatus(BusinessConstants.USER_STATUS_NORMAL);
        int result=0;
        try{
            result= userMapper.insertSelective(ue);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        if(result>0){
            return ue;
        }
        return null;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void registerUser(UserEx ue, Integer manageRoleId, HttpServletRequest request) throws Exception{
        /**
         * 多次创建事务，事物之间无法协同，应该在入口处创建一个事务以做协调
         */
        if(BusinessConstants.DEFAULT_MANAGER.equals(ue.getLoginName())) {
            throw new BusinessRunTimeException(ExceptionConstants.USER_NAME_LIMIT_USE_CODE,
                    ExceptionConstants.USER_NAME_LIMIT_USE_MSG);
        } else {
            ue.setPassword(ue.getPassword());
            ue.setIsystem(BusinessConstants.USER_NOT_SYSTEM);
            if (ue.getIsmanager() == null) {
                ue.setIsmanager(BusinessConstants.USER_NOT_MANAGER);
            }
            ue.setStatus(BusinessConstants.USER_STATUS_NORMAL);
            try{
                userMapper.insertSelective(ue);
                Long userId = getIdByLoginName(ue.getLoginName());
                ue.setId(userId);
            }catch(Exception e){
                JshException.writeFail(logger, e);
            }
            //更新租户id
            User user = new User();
            user.setId(ue.getId());
            user.setTenantId(ue.getId());
            userService.updateUserTenant(user);
            //新增用户与角色的关系
            JSONObject ubObj = new JSONObject();
            ubObj.put("type", "UserRole");
            ubObj.put("keyid", ue.getId());
            JSONArray ubArr = new JSONArray();
            ubArr.add(manageRoleId);
            ubObj.put("value", ubArr.toString());
            ubObj.put("tenantId", ue.getId());
            userBusinessService.insertUserBusiness(ubObj, null);
            //创建租户信息
            JSONObject tenantObj = new JSONObject();
            tenantObj.put("tenantId", ue.getId());
            tenantObj.put("loginName",ue.getLoginName());
            tenantObj.put("userNumLimit", ue.getUserNumLimit());
            tenantObj.put("expireTime", ue.getExpireTime());
            tenantObj.put("remark", ue.getRemark());
            Tenant tenant = JSONObject.parseObject(tenantObj.toJSONString(), Tenant.class);
            tenant.setCreateTime(new Date());
            if(tenant.getUserNumLimit()==null) {
                tenant.setUserNumLimit(userNumLimit); //默认用户限制数量
            }
            if(tenant.getExpireTime()==null) {
                tenant.setExpireTime(Tools.addDays(new Date(), tryDayLimit)); //租户允许试用的天数
            }
            tenantMapper.insertSelective(tenant);
            logger.info("===============创建租户信息完成===============");
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateUserTenant(User user) throws Exception{
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        try{
            userMapper.updateByPrimaryKeySelective(user);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateUserAndOrgUserRel(UserEx ue, HttpServletRequest request) throws Exception{
        if(BusinessConstants.DEFAULT_MANAGER.equals(ue.getLoginName())) {
            throw new BusinessRunTimeException(ExceptionConstants.USER_NAME_LIMIT_USE_CODE,
                    ExceptionConstants.USER_NAME_LIMIT_USE_MSG);
        } else {
            logService.insertLog("用户",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ue.getId()).toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            //检查用户名和登录名
            checkLoginName(ue);
            //更新用户信息
            ue = this.updateUser(ue);
            if (ue == null) {
                logger.error("异常码[{}],异常提示[{}],参数,[{}]",
                        ExceptionConstants.USER_EDIT_FAILED_CODE, ExceptionConstants.USER_EDIT_FAILED_MSG);
                throw new BusinessRunTimeException(ExceptionConstants.USER_EDIT_FAILED_CODE,
                        ExceptionConstants.USER_EDIT_FAILED_MSG);
            }
            if(ue.getRoleId()!=null){
                JSONObject ubObj = new JSONObject();
                ubObj.put("type", "UserRole");
                ubObj.put("keyid", ue.getId());
                ubObj.put("value", "[" + ue.getRoleId() + "]");
                Long ubId = userBusinessService.checkIsValueExist("UserRole", ue.getId().toString());
                if(ubId!=null) {
                    ubObj.put("id", ubId);
                    userBusinessService.updateUserBusiness(ubObj, request);
                } else {
                    userBusinessService.insertUserBusiness(ubObj, request);
                }
            }
            if (ue.getOrgaId() == null) {
                //如果没有选择机构，就不建机构和用户的关联关系
                return;
            }
            if(ue.getOrgaId()!=null && "1".equals(ue.getLeaderFlag())){
                //检查当前机构是否存在经理
                List<User> checkList = userMapperEx.getListByOrgaId(ue.getId(), ue.getOrgaId());
                if(checkList.size()>0) {
                    throw new BusinessRunTimeException(ExceptionConstants.USER_LEADER_IS_EXIST_CODE,
                            ExceptionConstants.USER_LEADER_IS_EXIST_MSG);
                }
            }
            //更新用户和机构关联关系
            OrgaUserRel oul = new OrgaUserRel();
            //机构和用户关联关系id
            oul.setId(ue.getOrgaUserRelId());
            //机构id
            oul.setOrgaId(ue.getOrgaId());
            //用户id
            oul.setUserId(ue.getId());
            //用户在机构中的排序
            oul.setUserBlngOrgaDsplSeq(ue.getUserBlngOrgaDsplSeq());
            if (oul.getId() != null) {
                //已存在机构和用户的关联关系，更新
                oul = orgaUserRelService.updateOrgaUserRel(oul);
            } else {
                //不存在机构和用户的关联关系，新建
                oul = orgaUserRelService.addOrgaUserRel(oul);
            }
            if (oul == null) {
                logger.error("异常码[{}],异常提示[{}],参数,[{}]",
                        ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_CODE, ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_MSG);
                throw new BusinessRunTimeException(ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_CODE,
                        ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_MSG);
            }
        }
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public UserEx updateUser(UserEx ue)throws Exception{
        int result =0;
        try{
            result=userMapper.updateByPrimaryKeySelective(ue);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        if(result>0){
            return ue;
        }
        return null;
    }
    /**
     *  检查登录名不能重复
     * create time: 2019/3/12 11:36
     * @Param: userEx
     * @return void
     */
    public void checkLoginName(UserEx userEx)throws Exception{
        List<User> list=null;
        if(userEx==null){
            return;
        }
        Long userId=userEx.getId();
        //检查登录名
        if(!StringUtils.isEmpty(userEx.getLoginName())){
            String loginName=userEx.getLoginName();
            list=this.getUserListByloginName(loginName);
            if(list!=null&&list.size()>0){
                if(list.size()>1){
                    //超过一条数据存在，该登录名已存在
                    logger.error("异常码[{}],异常提示[{}],参数,loginName:[{}]",
                            ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG,loginName);
                    throw new BusinessRunTimeException(ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,
                            ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG);
                }
                //一条数据，新增时抛出异常，修改时和当前的id不同时抛出异常
                if(list.size()==1){
                    if(userId==null||(userId!=null&&!userId.equals(list.get(0).getId()))){
                        logger.error("异常码[{}],异常提示[{}],参数,loginName:[{}]",
                                ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG,loginName);
                        throw new BusinessRunTimeException(ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,
                                ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG);
                    }
                }
            }
        }
    }
    /**
     * 通过登录名获取用户列表
     * */
    public List<User> getUserListByloginName(String loginName){
        List<User> list =null;
        try{
            list=userMapperEx.getUserListByUserNameOrLoginName(null,loginName);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<TreeNodeEx> getOrganizationUserTree()throws Exception {
        List<TreeNodeEx> list =null;
        try{
            list=userMapperEx.getNodeTree();
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    /**
     * 根据用户id查询角色信息
     * @param userId
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public Role getRoleTypeByUserId(long userId) throws Exception {
        Role role = new Role();
        List<UserBusiness> list = userBusinessService.getBasicData(String.valueOf(userId), "UserRole");
        UserBusiness ub = null;
        if(list.size() > 0) {
            ub = list.get(0);
            String values = ub.getValue();
            String roleId = null;
            if(values!=null) {
                values = values.replaceAll("\\[\\]",",").replace("[","").replace("]","");
            }
            String [] valueArray=values.split(",");
            if(valueArray.length>0) {
                roleId = valueArray[0];
            }
            role = roleService.getRoleWithoutTenant(Long.parseLong(roleId));
        }
        return role;
    }

    /**
     * 获取用户id
     * @param request
     * @return
     */
    public Long getUserId(HttpServletRequest request) throws Exception{
        Object userIdObj = redisService.getObjectFromSessionByKey(request,"userId");
        Long userId = null;
        if(userIdObj != null) {
            userId = Long.parseLong(userIdObj.toString());
        }
        return userId;
    }

    /**
     * 用户的按钮权限
     * @param userId
     * @return
     * @throws Exception
     */
    public JSONArray getBtnStrArrById(Long userId) throws Exception {
        JSONArray btnStrArr = new JSONArray();
        List<UserBusiness> userRoleList = userBusinessService.getBasicData(userId.toString(), "UserRole");
        if(userRoleList!=null && userRoleList.size()>0) {
            String roleValue = userRoleList.get(0).getValue();
            if(StringUtil.isNotEmpty(roleValue) && roleValue.indexOf("[")>-1 && roleValue.indexOf("]")>-1){
                roleValue = roleValue.replace("[", "").replace("]", ""); //角色id-单个
                List<UserBusiness> roleFunctionsList = userBusinessService.getBasicData(roleValue, "RoleFunctions");
                if(roleFunctionsList!=null && roleFunctionsList.size()>0) {
                    String btnStr = roleFunctionsList.get(0).getBtnStr();
                    if(StringUtil.isNotEmpty(btnStr)){
                        btnStrArr = JSONArray.parseArray(btnStr);
                    }
                }
            }
        }
        //将数组中的funId转为url
        JSONArray btnStrWithUrlArr = new JSONArray();
        if(btnStrArr.size()>0) {
            List<Function> functionList = functionService.getFunction();
            Map<Long, String> functionMap = new HashMap<>();
            for (Function function: functionList) {
                functionMap.put(function.getId(), function.getUrl());
            }
            for (Object obj : btnStrArr) {
                JSONObject btnStrObj = JSONObject.parseObject(obj.toString());
                Long funId = btnStrObj.getLong("funId");
                JSONObject btnStrWithUrlObj = new JSONObject();
                btnStrWithUrlObj.put("url", functionMap.get(funId));
                btnStrWithUrlObj.put("btnStr", btnStrObj.getString("btnStr"));
                btnStrWithUrlArr.add(btnStrWithUrlObj);
            }
        }
        return btnStrWithUrlArr;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Byte status, String ids, HttpServletRequest request)throws Exception {
        int result=0;
        List<User> list = getUserListByIds(ids);
        //选中的用户的数量
        int selectUserSize = list.size();
        //查询启用状态的用户的数量
        int enableUserSize = getUser(request).size();
        User userInfo = userService.getCurrentUser();
        Tenant tenant = tenantService.getTenantByTenantId(userInfo.getTenantId());
        if(tenant!=null) {
            if (selectUserSize + enableUserSize > tenant.getUserNumLimit() && status == 0) {
                throw new BusinessParamCheckingException(ExceptionConstants.USER_ENABLE_OVER_LIMIT_FAILED_CODE,
                        ExceptionConstants.USER_ENABLE_OVER_LIMIT_FAILED_MSG);
            }
        }
        StringBuilder userStr = new StringBuilder();
        List<Long> idList = new ArrayList<>();
        for(User user: list) {
            if(user.getId().equals(user.getTenantId())) {
                //租户不能进行禁用
            } else {
                idList.add(user.getId());
                userStr.append(user.getLoginName()).append(" ");
            }
        }
        String statusStr ="";
        if(status == 0) {
            statusStr ="批量启用";
        } else if(status == 2) {
            statusStr ="批量禁用";
        }
        if(idList.size()>0) {
            User user = new User();
            user.setStatus(status);
            UserExample example = new UserExample();
            example.createCriteria().andIdIn(idList);
            result = userMapper.updateByExampleSelective(user, example);
            logService.insertLog("用户",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(userStr).append("-").append(statusStr).toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        } else {
            result = 1;
        }
        return result;
    }

    public User getUserByWeixinCode(String weixinCode) throws Exception {
        String weixinLogin = platformConfigService.getPlatformConfigByKey("weixinUrl").getPlatformValue() + BusinessConstants.WEIXIN_LOGIN;
        String weixinAppid = platformConfigService.getPlatformConfigByKey("weixinAppid").getPlatformValue();
        String weixinSecret = platformConfigService.getPlatformConfigByKey("weixinSecret").getPlatformValue();
        String url = weixinLogin + "?appid=" + weixinAppid + "&secret=" + weixinSecret + "&js_code=" + weixinCode
                + "&grant_type=authorization_code";
        JSONObject jsonObject = HttpClient.httpGet(url);
        if(jsonObject!=null) {
            String weixinOpenId = jsonObject.getString("openid");
            if(StringUtil.isNotEmpty(weixinOpenId)) {
                return userMapperEx.getUserByWeixinOpenId(weixinOpenId);
            }
        }
        return null;
    }

    public int weixinBind(String loginName, String password, String weixinCode) throws Exception {
        String weixinUrl = platformConfigService.getPlatformConfigByKey("weixinUrl").getPlatformValue();
        String weixinAppid = platformConfigService.getPlatformConfigByKey("weixinAppid").getPlatformValue();
        String weixinSecret = platformConfigService.getPlatformConfigByKey("weixinSecret").getPlatformValue();
        String url = weixinUrl + "?appid=" + weixinAppid + "&secret=" + weixinSecret + "&js_code=" + weixinCode
                + "&grant_type=authorization_code";
        JSONObject jsonObject = HttpClient.httpGet(url);
        if(jsonObject!=null) {
            String weixinOpenId = jsonObject.getString("openid");
            if(StringUtil.isNotEmpty(weixinOpenId)) {
                return userMapperEx.updateUserWithWeixinOpenId(loginName, password, weixinOpenId);
            }
        }
        return 0;
    }
}
