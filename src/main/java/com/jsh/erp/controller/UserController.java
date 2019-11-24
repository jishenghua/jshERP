package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Tenant;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserEx;
import com.jsh.erp.datasource.vo.TreeNodeEx;
import com.jsh.erp.exception.BusinessParamCheckingException;
import com.jsh.erp.service.log.LogService;
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

    @Resource
    private UserService userService;

    @Resource
    private TenantService tenantService;

    @Resource
    private LogService logService;

    private static String message = "成功";
    private static final String HTTP = "http://";
    private static final String CODE_OK = "200";

    @PostMapping(value = "/login")
    public BaseResponseInfo login(@RequestParam(value = "loginame", required = false) String loginame,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request)throws Exception {
        logger.info("============用户登录 login 方法调用开始==============");
        String msgTip = "";
        User user=null;
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String username = loginame.trim();
            password = password.trim();
            //判断用户是否已经登录过，登录过不再处理
            Object userInfo = request.getSession().getAttribute("user");
            User sessionUser = new User();
            if (userInfo != null) {
                sessionUser = (User) userInfo;
            }
            if (sessionUser != null && username.equalsIgnoreCase(sessionUser.getLoginame())) {
                logger.info("====用户 " + username + "已经登录过, login 方法调用结束====");
                msgTip = "user already login";
            }
            //获取用户状态
            int userStatus = -1;
            try {
                request.getSession().removeAttribute("tenantId");
                userStatus = userService.validateUser(username, password);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(">>>>>>>>>>>>>用户  " + username + " 登录 login 方法 访问服务层异常====", e);
                msgTip = "access service exception";
            }
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
                default:
                    try {
                        msgTip = "user can login";
                        //验证通过 ，可以登录，放入session，记录登录日志
                        user = userService.getUserByUserName(username);
                        request.getSession().setAttribute("user",user);
                        if(user.getTenantId()!=null) {
                            Tenant tenant = tenantService.getTenantByTenantId(user.getTenantId());
                            if(tenant!=null) {
                                Long tenantId = tenant.getTenantId();
                                Integer userNumLimit = tenant.getUserNumLimit();
                                Integer billsNumLimit = tenant.getBillsNumLimit();
                                if(tenantId!=null) {
                                    request.getSession().setAttribute("tenantId",tenantId); //租户tenantId
                                    request.getSession().setAttribute("userNumLimit",userNumLimit); //用户限制数
                                    request.getSession().setAttribute("billsNumLimit",billsNumLimit); //单据限制数
                                }
                            }
                        }
                        logService.insertLog("用户",
                                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_LOGIN).append(user.getId()).toString(),
                                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(">>>>>>>>>>>>>>>查询用户名为:" + username + " ，用户信息异常", e);
                    }
                    break;
            }
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("msgTip", msgTip);
            /**
             * 在IE模式下，无法获取到user数据，
             * 在此处明确添加上user信息
             * */
            if(user!=null){
                data.put("user",user);
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
            Map<String, Object> data = new HashMap<String, Object>();
            Object userInfo = request.getSession().getAttribute("user");
            if(userInfo!=null) {
                User user = (User) userInfo;
                user.setPassword(null);
                data.put("user", user);
            }
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
            request.getSession().removeAttribute("user");
            request.getSession().removeAttribute("tenantId");
            request.getSession().removeAttribute("userNumLimit");
            request.getSession().removeAttribute("billsNumLimit");
            response.sendRedirect("/login.html");
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "退出失败";
        }
        return res;
    }

    @PostMapping(value = "/resetPwd")
    public String resetPwd(@RequestParam("id") Long id,
                                     HttpServletRequest request) throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        String password = "123456";
        String md5Pwd = Tools.md5Encryp(password);
        int update = userService.resetPwd(md5Pwd, id);
        if(update > 0) {
            return returnJson(objectMap, message, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, message, ErpInfo.ERROR.code);
        }
    }

    @PostMapping(value = "/updatePwd")
    public String updatePwd(@RequestParam("userId") Long userId, @RequestParam("password") String password,
                            @RequestParam("oldpwd") String oldpwd, HttpServletRequest request)throws Exception {
        Integer flag = 0;
        Map<String, Object> objectMap = new HashMap<String, Object>();
        try {
            User user = userService.getUser(userId);
            String oldPassword = Tools.md5Encryp(oldpwd);
            String md5Pwd = Tools.md5Encryp(password);
            //必须和原始密码一致才可以更新密码
            if(user.getLoginame().equals("jsh")){
                flag = 3; //管理员jsh不能修改密码
            } else if (oldPassword.equalsIgnoreCase(user.getPassword())) {
                user.setPassword(md5Pwd);
                flag = userService.updateUserByObj(user); //1-成功
            } else {
                flag = 2; //原始密码输入错误
            }
            objectMap.put("status", flag);
            if(flag > 0) {
                return returnJson(objectMap, message, ErpInfo.OK.code);
            } else {
                return returnJson(objectMap, message, ErpInfo.ERROR.code);
            }
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>修改用户ID为 ： " + userId + "密码信息失败", e);
            flag = 3;
            objectMap.put("status", flag);
            return returnJson(objectMap, message, ErpInfo.ERROR.code);
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
     * create by: cjl
     * description:
     *  查询分页用户列表
     * create time: 2019/3/8 15:08
     * @Param: pageSize
     * @Param: currentPage
     * @Param: search
     * @return java.lang.String
     */
    @GetMapping(value = "/getUserList")
    public String getUserList(@RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
                                       @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
                                       @RequestParam(value = Constants.SEARCH, required = false) String search)throws Exception {

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        //查询参数
        JSONObject obj= JSON.parseObject(search);
        Set<String> key= obj.keySet();
        for(String keyEach: key){
            parameterMap.put(keyEach,obj.getString(keyEach));
        }
        PageQueryInfo queryInfo = new PageQueryInfo();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (pageSize == null || pageSize <= 0) {
            pageSize = BusinessConstants.DEFAULT_PAGINATION_PAGE_SIZE;
        }
        if (currentPage == null || currentPage <= 0) {
            currentPage = BusinessConstants.DEFAULT_PAGINATION_PAGE_NUMBER;
        }
        PageHelper.startPage(currentPage,pageSize,true);
        List<UserEx> list = userService.getUserList(parameterMap);
        //获取分页查询后的数据
        PageInfo<UserEx> pageInfo = new PageInfo<>(list);
        objectMap.put("page", queryInfo);
        if (list == null) {
            queryInfo.setRows(new ArrayList<Object>());
            queryInfo.setTotal(BusinessConstants.DEFAULT_LIST_NULL_NUMBER);
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
        queryInfo.setRows(list);
        queryInfo.setTotal(pageInfo.getTotal());
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
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
    public Object addUser(@RequestParam("info") String beanJson, HttpServletRequest request)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        Long userNumLimit = Long.parseLong(request.getSession().getAttribute("userNumLimit").toString());
        Long count = userService.countUser(null,null);
        if(count>= userNumLimit) {
            throw new BusinessParamCheckingException(ExceptionConstants.USER_OVER_LIMIT_FAILED_CODE,
                    ExceptionConstants.USER_OVER_LIMIT_FAILED_MSG);
        } else {
            UserEx ue= JSON.parseObject(beanJson, UserEx.class);
            userService.addUserAndOrgUserRel(ue);
        }
        return result;
    }


    /**
     * 注册用户
     * @param loginame
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/registerUser")
    public Object registerUser(@RequestParam(value = "loginame", required = false) String loginame,
                               @RequestParam(value = "password", required = false) String password,
                               HttpServletRequest request)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        UserEx ue= new UserEx();
        ue.setUsername(loginame);
        ue.setLoginame(loginame);
        ue.setPassword(password);
        userService.checkUserNameAndLoginName(ue); //检查用户名和登录名
        ue = userService.registerUser(ue,manageRoleId,request);
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
    @PostMapping("/updateUser")
    @ResponseBody
    public Object updateUser(@RequestParam("info") String beanJson,@RequestParam("id") Long id)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        UserEx ue= JSON.parseObject(beanJson, UserEx.class);
        ue.setId(id);
        userService.updateUserAndOrgUserRel(ue);
        return result;
    }
    @PostMapping("/deleteUser")
    @ResponseBody
    public Object deleteUser(@RequestParam("ids") String ids)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        userService.batDeleteUser(ids);
        return result;
    }
    @PostMapping("/batchDeleteUser")
    @ResponseBody
    public Object batchDeleteUser(@RequestParam("ids") String ids)throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        userService.batDeleteUser(ids);
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
}
