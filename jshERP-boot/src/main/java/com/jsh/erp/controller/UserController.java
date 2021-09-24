package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.SysLoginModel;
import com.jsh.erp.datasource.entities.Tenant;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserEx;
import com.jsh.erp.datasource.vo.TreeNodeEx;
import com.jsh.erp.exception.BusinessParamCheckingException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.redis.RedisService;
import com.jsh.erp.service.tenant.TenantService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji_sheng_hua 华夏erp
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${manage.roleId}")
    private Integer manageRoleId;

    @Value("${demonstrate.open}")
    private boolean demonstrateOpen;

    @Resource
    private UserService userService;

    @Resource
    private TenantService tenantService;

    @Resource
    private LogService logService;

    @Resource
    private RedisService redisService;

    private static final String TEST_USER = "jsh";
    private static String SUCCESS = "操作成功";
    private static String ERROR = "操作失败";
    private static final String HTTP = "http://";
    private static final String CODE_OK = "200";
    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @PostMapping(value = "/login")
    public BaseResponseInfo login(@RequestBody User userParam,
                        HttpServletRequest request)throws Exception {
        logger.info("============用户登录 login 方法调用开始==============");
        String msgTip = "";
        User user=null;
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String loginName = userParam.getLoginName().trim();
            String password = userParam.getPassword().trim();
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
                userStatus = userService.validateUser(loginName, password);
            } catch (Exception e) {
                e.printStackTrace();
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
                    user = userService.getUserByLoginName(loginName);
                    if(user.getTenantId()!=null) {
                        token = token + "_" + user.getTenantId();
                    }
                    redisService.storageObjectBySession(token,"userId",user.getId());
                    if(user.getTenantId()!=null) {
                        Tenant tenant = tenantService.getTenantByTenantId(user.getTenantId());
                        if(tenant!=null) {
                            Long tenantId = tenant.getTenantId();
                            Integer userNumLimit = tenant.getUserNumLimit();
                            if(tenantId!=null) {
                                redisService.storageObjectBySession(token,"userNumLimit",userNumLimit); //用户限制数
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("msgTip", msgTip);
            if(user!=null){
                String roleType = userService.getRoleTypeByUserId(user.getId()); //角色类型
                redisService.storageObjectBySession(token,"roleType",roleType);
                redisService.storageObjectBySession(token,"clientIp", Tools.getLocalIp(request));
                logService.insertLogWithUserId(user.getId(), user.getTenantId(), "用户",
                        new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_LOGIN).append(user.getLoginName()).toString(),
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
                JSONArray btnStrArr = userService.getBtnStrArrById(user.getId());
                data.put("token", token);
                data.put("user", user);
                //用户的按钮权限
                if(!"admin".equals(user.getLoginName())){
                    data.put("userBtn", btnStrArr);
                }
                data.put("roleType", roleType);
            }
            res.code = 200;
            res.data = data;
            logger.info("===============用户登录 login 方法调用结束===============");
        } catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.code = 500;
            res.data = "用户登录失败";
        }
        return res;
    }

    @GetMapping(value = "/getUserSession")
    public BaseResponseInfo getSessionUser(HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Object> data = new HashMap<>();
            Long userId = Long.parseLong(redisService.getObjectFromSessionByKey(request,"userId").toString());
            User user = userService.getUser(userId);
            user.setPassword(null);
            data.put("user", user);
            res.code = 200;
            res.data = data;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取session失败";
        }
        return res;
    }

    @GetMapping(value = "/logout")
    public BaseResponseInfo logout(HttpServletRequest request, HttpServletResponse response)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            redisService.deleteObjectBySession(request,"userId");
            response.sendRedirect("/login.html");
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "退出失败";
        }
        return res;
    }

    @PostMapping(value = "/resetPwd")
    public String resetPwd(@RequestBody JSONObject jsonObject,
                                     HttpServletRequest request) throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        Long id = jsonObject.getLong("id");
        String password = "123456";
        String md5Pwd = Tools.md5Encryp(password);
        int update = userService.resetPwd(md5Pwd, id);
        if(update > 0) {
            return returnJson(objectMap, SUCCESS, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ERROR, ErpInfo.ERROR.code);
        }
    }

    @PutMapping(value = "/updatePwd")
    public String updatePwd(@RequestBody JSONObject jsonObject, HttpServletRequest request)throws Exception {
        Integer flag = 0;
        Map<String, Object> objectMap = new HashMap<String, Object>();
        try {
            String info = "";
            Long userId = jsonObject.getLong("userId");
            String oldpwd = jsonObject.getString("oldpassword");
            String password = jsonObject.getString("password");
            User user = userService.getUser(userId);
            //必须和原始密码一致才可以更新密码
            if(demonstrateOpen && user.getLoginName().equals(TEST_USER)){
                flag = 3; //jsh用户不能修改密码
                info = "jsh用户不能修改密码";
            } else if (oldpwd.equalsIgnoreCase(user.getPassword())) {
                user.setPassword(password);
                flag = userService.updateUserByObj(user); //1-成功
                info = "修改成功";
            } else {
                flag = 2; //原始密码输入错误
                info = "原始密码输入错误";
            }
            objectMap.put("status", flag);
            if(flag > 0) {
                return returnJson(objectMap, info, ErpInfo.OK.code);
            } else {
                return returnJson(objectMap, ERROR, ErpInfo.ERROR.code);
            }
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>修改用户ID为 ： " + jsonObject.getLong("userId") + "密码信息失败", e);
            flag = 3;
            objectMap.put("status", flag);
            return returnJson(objectMap, ERROR, ErpInfo.ERROR.code);
        }
    }

    /**
     * 获取全部用户数据列表
     * @param request
     * @return
     */
    @GetMapping(value = "/getAllList")
    public BaseResponseInfo getAllList(HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            List<User> dataList = userService.getUser();
            if(dataList!=null) {
                data.put("userList", dataList);
            }
            res.code = 200;
            res.data = data;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取失败";
        }
        return res;
    }

    /**
     * 用户列表，用于用户下拉框
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getUserList")
    public JSONArray getUserList(HttpServletRequest request)throws Exception {
        JSONArray dataArray = new JSONArray();
        try {
            List<User> dataList = userService.getUser();
            if (null != dataList) {
                for (User user : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", user.getId());
                    item.put("userName", user.getUsername());
                    dataArray.add(item);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return dataArray;
    }

    /**
     * create by: cjl
     * description:
     *  新增用户及机构和用户关系
     * create time: 2019/3/8 16:06
     * @Param: beanJson
     * @return java.lang.Object
     */
    @PostMapping("/addUser")
    @ResponseBody
    public Object addUser(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        Long userNumLimit = Long.parseLong(redisService.getObjectFromSessionByKey(request,"userNumLimit").toString());
        Long count = userService.countUser(null,null);
        if(count>= userNumLimit) {
            throw new BusinessParamCheckingException(ExceptionConstants.USER_OVER_LIMIT_FAILED_CODE,
                    ExceptionConstants.USER_OVER_LIMIT_FAILED_MSG);
        } else {
            UserEx ue= JSONObject.parseObject(obj.toJSONString(), UserEx.class);
            userService.addUserAndOrgUserRel(ue);
        }
        return result;
    }

    /**
     * create by: cjl
     * description:
     *  修改用户及机构和用户关系
     * create time: 2019/3/8 16:06
     * @Param: beanJson
     * @return java.lang.Object
     */
    @PutMapping("/updateUser")
    @ResponseBody
    public Object updateUser(@RequestBody JSONObject obj)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        UserEx ue= JSONObject.parseObject(obj.toJSONString(), UserEx.class);
        userService.updateUserAndOrgUserRel(ue);
        return result;
    }

    /**
     * 注册用户
     * @param ue
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/registerUser")
    public Object registerUser(@RequestBody UserEx ue,
                               HttpServletRequest request)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        ue.setUsername(ue.getLoginName());
        userService.checkUserNameAndLoginName(ue); //检查用户名和登录名
        ue = userService.registerUser(ue,manageRoleId,request);
        return result;
    }

    @RequestMapping("/getOrganizationUserTree")
    public JSONArray getOrganizationUserTree()throws Exception{
        JSONArray arr=new JSONArray();
        List<TreeNodeEx> organizationUserTree= userService.getOrganizationUserTree();
        if(organizationUserTree!=null&&organizationUserTree.size()>0){
            for(TreeNodeEx node:organizationUserTree){
                String str=JSON.toJSONString(node);
                JSONObject obj=JSON.parseObject(str);
                arr.add(obj) ;
            }
        }
        return arr;
    }

    /**
     * 获取当前用户的角色类型
     * @param request
     * @return
     */
    @GetMapping("/getRoleTypeByCurrentUser")
    public BaseResponseInfo getRoleTypeByCurrentUser(HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            String roleType = redisService.getObjectFromSessionByKey(request,"roleType").toString();
            data.put("roleType", roleType);
            res.code = 200;
            res.data = data;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取失败";
        }
        return res;
    }

    @GetMapping(value = "/randomImage/{key}")
    public BaseResponseInfo randomImage(HttpServletResponse response,@PathVariable String key){
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Object> data = new HashMap<>();
            String codeNum = Tools.getCharAndNum(4);
            String base64 = RandImageUtil.generate(codeNum);
            data.put("codeNum", codeNum);
            data.put("base64", base64);
            res.code = 200;
            res.data = data;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取失败";
        }
        return res;
    }

    /**
     * 批量设置状态-启用或者禁用
     * @param jsonObject
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request)throws Exception {
        Byte status = jsonObject.getByte("status");
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = userService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 获取当前用户的用户数量和租户信息
     * @param request
     * @return
     */
    @GetMapping(value = "/infoWithTenant")
    public BaseResponseInfo randomImage(HttpServletRequest request){
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Object> data = new HashMap<>();
            Long userId = Long.parseLong(redisService.getObjectFromSessionByKey(request,"userId").toString());
            User user = userService.getUser(userId);
            //获取当前用户数
            Long userCurrentNum = userService.countUser(null, null);
            Tenant tenant = tenantService.getTenantByTenantId(user.getTenantId());
            data.put("type", tenant.getType()); //租户类型，0免费租户，1付费租户
            data.put("expireTime", Tools.parseDateToStr(tenant.getExpireTime()));
            data.put("userCurrentNum", userCurrentNum);
            data.put("userNumLimit", tenant.getUserNumLimit());
            res.code = 200;
            res.data = data;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取失败";
        }
        return res;
    }
}
