package com.jsh.erp.service.user;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.OrgaUserRel;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserEx;
import com.jsh.erp.datasource.entities.UserExample;
import com.jsh.erp.datasource.mappers.UserMapper;
import com.jsh.erp.datasource.mappers.UserMapperEx;
import com.jsh.erp.datasource.vo.TreeNode;
import com.jsh.erp.datasource.vo.TreeNodeEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.orgaUserRel.OrgaUserRelService;
import com.jsh.erp.utils.ExceptionCodeConstants;
import com.jsh.erp.utils.JshException;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserMapperEx userMapperEx;
    @Resource
    private OrgaUserRelService orgaUserRelService;
    @Resource
    private LogService logService;

    public User getUser(long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> getUser() {
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }

    public List<User> select(String userName, String loginName, int offset, int rows) {
        return userMapperEx.selectByConditionUser(userName, loginName, offset, rows);
    }

    public Long countUser(String userName, String loginName) {
        return userMapperEx.countsByUser(userName, loginName);
    }
    /**
     * create by: cjl
     * description:
     * 添加事务控制
     * create time: 2019/1/11 14:30
     * @Param: beanJson
     * @Param: request
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertUser(String beanJson, HttpServletRequest request) {
        User user = JSONObject.parseObject(beanJson, User.class);
        String password = "123456";
        //因密码用MD5加密，需要对密码进行转化
        try {
            password = Tools.md5Encryp(password);
            user.setPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>>>>>转化MD5字符串错误 ：" + e.getMessage());
        }
        return userMapper.insertSelective(user);
    }
    /**
     * create by: cjl
     * description:
     * 添加事务控制
     * create time: 2019/1/11 14:31
     * @Param: beanJson
     * @Param: id
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUser(String beanJson, Long id) {
        User user = JSONObject.parseObject(beanJson, User.class);
        user.setId(id);
        return userMapper.updateByPrimaryKeySelective(user);
    }
    /**
     * create by: cjl
     * description:
     * 添加事务控制
     * create time: 2019/1/11 14:32
     * @Param: user
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUserByObj(User user) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(user.getId()).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        return userMapper.updateByPrimaryKeySelective(user);
    }
    /**
     * create by: cjl
     * description:
     *  添加事务控制
     * create time: 2019/1/11 14:33
     * @Param: md5Pwd
     * @Param: id
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int resetPwd(String md5Pwd, Long id) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User user = new User();
        user.setId(id);
        user.setPassword(md5Pwd);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteUser(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUser(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        UserExample example = new UserExample();
        example.createCriteria().andIdIn(idList);
        return userMapper.deleteByExample(example);
    }

    public int validateUser(String username, String password) throws JshException {
        try {
            /**默认是可以登录的*/
            List<User> list = null;
            try {
                UserExample example = new UserExample();
                example.createCriteria().andLoginameEqualTo(username);
                list = userMapper.selectByExample(example);
            } catch (Exception e) {
                logger.error(">>>>>>>>访问验证用户姓名是否存在后台信息异常", e);
                return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
            }

            if (null != list && list.size() == 0) {
                return ExceptionCodeConstants.UserExceptionCode.USER_NOT_EXIST;
            }

            try {
                UserExample example = new UserExample();
                example.createCriteria().andLoginameEqualTo(username).andPasswordEqualTo(password);
                list = userMapper.selectByExample(example);
            } catch (Exception e) {
                logger.error(">>>>>>>>>>访问验证用户密码后台信息异常", e);
                return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
            }

            if (null != list && list.size() == 0) {
                return ExceptionCodeConstants.UserExceptionCode.USER_PASSWORD_ERROR;
            }
            return ExceptionCodeConstants.UserExceptionCode.USER_CONDITION_FIT;
        } catch (Exception e) {
            throw new JshException("unknown exception", e);
        }
    }

    public User getUserByUserName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        User user = list.get(0);
        return user;
    }

    public int checkIsNameExist(Long id, String name) {
        UserExample example = new UserExample();
        example.createCriteria().andIdNotEqualTo(id).andLoginameEqualTo(name);
        List<User> list = userMapper.selectByExample(example);
        return list.size();
    }
    /**
     * create by: cjl
     * description:
     *  获取当前用户信息
     * create time: 2019/1/24 10:01
     * @Param:
     * @return com.jsh.erp.datasource.entities.User
     */
    public User getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return (User)request.getSession().getAttribute("user");
    }

    public List<UserEx> getUserList(Map<String, Object> parameterMap) throws Exception{
        return userMapperEx.getUserList(parameterMap);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addUserAndOrgUserRel(UserEx ue) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER,
                BusinessConstants.LOG_OPERATION_TYPE_ADD,
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //检查用户名和登录名
        checkUserNameAndLoginName(ue);
        //新增用户信息
        ue= this.addUser(ue);
        if(ue==null){
            logger.error("异常码[{}],异常提示[{}],参数,[{}]",
                    ExceptionConstants.USER_ADD_FAILED_CODE,ExceptionConstants.USER_ADD_FAILED_MSG);
            throw new BusinessRunTimeException(ExceptionConstants.USER_ADD_FAILED_CODE,
                    ExceptionConstants.USER_ADD_FAILED_MSG);
        }
        if(ue.getOrgaId()==null){
            //如果没有选择机构，就不建机构和用户的关联关系
            return;
        }
        //新增用户和机构关联关系
        OrgaUserRel oul=new OrgaUserRel();
        //机构id
        oul.setOrgaId(ue.getOrgaId());
        //用户id
        oul.setUserId(ue.getId());
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
        int i=userMapperEx.addUser(ue);
        if(i>0){
            return ue;
        }
        return null;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateUserAndOrgUserRel(UserEx ue) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ue.getId()).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        //检查用户名和登录名
        checkUserNameAndLoginName(ue);
        //更新用户信息
        ue=this.updateUser(ue);
        if(ue==null){
            logger.error("异常码[{}],异常提示[{}],参数,[{}]",
                    ExceptionConstants.USER_EDIT_FAILED_CODE,ExceptionConstants.USER_EDIT_FAILED_MSG);
            throw new BusinessRunTimeException(ExceptionConstants.USER_EDIT_FAILED_CODE,
                    ExceptionConstants.USER_EDIT_FAILED_MSG);
        }
        if(ue.getOrgaId()==null){
            //如果没有选择机构，就不建机构和用户的关联关系
            return;
        }
        //更新用户和机构关联关系
        OrgaUserRel oul=new OrgaUserRel();
        //机构和用户关联关系id
        oul.setId(ue.getOrgaUserRelId());
        //机构id
        oul.setOrgaId(ue.getOrgaId());
        //用户id
        oul.setUserId(ue.getId());
        //用户在机构中的排序
        oul.setUserBlngOrgaDsplSeq(ue.getUserBlngOrgaDsplSeq());
        if(oul.getId()!=null){
            //已存在机构和用户的关联关系，更新
            oul=orgaUserRelService.updateOrgaUserRel(oul);
        }else{
            //不存在机构和用户的关联关系，新建
            oul=orgaUserRelService.addOrgaUserRel(oul);
        }
        if(oul==null){
            logger.error("异常码[{}],异常提示[{}],参数,[{}]",
                    ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_CODE,ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_MSG);
            throw new BusinessRunTimeException(ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_CODE,
                    ExceptionConstants.ORGA_USER_REL_EDIT_FAILED_MSG);
        }

    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public UserEx updateUser(UserEx ue){
        int i=userMapperEx.updateUser(ue);
        if(i>0){
            return ue;
        }
        return null;
    }
    /**
     * create by: cjl
     * description:
     *  检查用户名称和登录名不能重复
     * create time: 2019/3/12 11:36
     * @Param: userEx
     * @return void
     */
    public void checkUserNameAndLoginName(UserEx userEx){
        List<User> list=null;
        if(userEx==null){
            return;
        }
        Long userId=userEx.getId();
        //检查登录名
        if(!StringUtils.isEmpty(userEx.getLoginame())){
            String loginName=userEx.getLoginame();
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
        //检查用户名
        if(!StringUtils.isEmpty(userEx.getUsername())){
            String userName=userEx.getUsername();
            list=this.getUserListByUserName(userName);
            if(list!=null&&list.size()>0){
                if(list.size()>1){
                    //超过一条数据存在，该用户名已存在
                    logger.error("异常码[{}],异常提示[{}],参数,userName:[{}]",
                            ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG,userName);
                    throw new BusinessRunTimeException(ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,
                            ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG);
                }
                //一条数据，新增时抛出异常，修改时和当前的id不同时抛出异常
                if(list.size()==1){
                    if(userId==null||(userId!=null&&!userId.equals(list.get(0).getId()))){
                        logger.error("异常码[{}],异常提示[{}],参数,userName:[{}]",
                                ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG,userName);
                        throw new BusinessRunTimeException(ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,
                                ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG);
                    }
                }

            }
        }

    }
    /**
     * 通过用户名获取用户列表
     * */
    public List<User> getUserListByUserName(String userName){
        return userMapperEx.getUserListByUserNameOrLoginName(userName,null);
    }
    /**
     * 通过登录名获取用户列表
     * */
    public List<User> getUserListByloginName(String loginName){
        return userMapperEx.getUserListByUserNameOrLoginName(null,loginName);
    }
    /**
     * 批量删除用户
     * */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void batDeleteUser(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        String idsArray[]=ids.split(",");
        int i= userMapperEx.batDeleteOrUpdateUser(idsArray,BusinessConstants.USER_STATUS_DELETE);
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids:[{}]",
                    ExceptionConstants.USER_DELETE_FAILED_CODE,ExceptionConstants.USER_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.USER_DELETE_FAILED_CODE,
                    ExceptionConstants.USER_DELETE_FAILED_MSG);
        }
    }

    public List<TreeNodeEx> getOrganizationUserTree() {
        return userMapperEx.getNodeTree();
    }
}
