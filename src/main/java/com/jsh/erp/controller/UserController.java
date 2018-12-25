package com.jsh.erp.controller;

import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji_sheng_hua 华夏erp
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Resource
    private UserService userService;

    private static String message = "成功";

    @PostMapping(value = "/login")
    public BaseResponseInfo login(@RequestParam(value = "loginame", required = false) String loginame,
                        @RequestParam(value = "password", required = false) String password,
                        HttpServletRequest request) {
        logger.info("============用户登录 login 方法调用开始==============");
        String msgTip = "";
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            String username = loginame.trim();
            password = password.trim();
            //因密码用MD5加密，需要对密码进行转化
            try {
                password = Tools.md5Encryp(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                logger.error(">>>>>>>>>>>>>>转化MD5字符串错误 ：" + e.getMessage(), e);
            }
            //判断用户是否已经登录过，登录过不再处理
            Object userInfo = request.getSession().getAttribute("user");
            User sessionUser = new User();
            if (userInfo != null) {
                sessionUser = (User) userInfo;
            }
            if (sessionUser != null && username.equalsIgnoreCase(sessionUser.getLoginame())
                    && sessionUser.getPassword().equals(password)) {
                logger.info("====用户 " + username + "已经登录过, login 方法调用结束====");
                msgTip = "user already login";
            }
            //获取用户状态
            int userStatus = -1;
            try {
                userStatus = userService.validateUser(username, password);
            } catch (Exception e) {
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
                        //验证通过 ，可以登录，放入session，记录登录日志
                        User user = userService.getUserByUserName(username);
    //                    logService.create(new Logdetails(user, "登录系统", model.getClientIp(),
    //                            new Timestamp(System.currentTimeMillis()), (short) 0, "管理用户：" + username + " 登录系统", username + " 登录系统"));
                        msgTip = "user can login";
                        request.getSession().setAttribute("user",user);
                    } catch (Exception e) {
                        logger.error(">>>>>>>>>>>>>>>查询用户名为:" + username + " ，用户信息异常", e);
                    }
                    break;
            }
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("msgTip", msgTip);
            res.code = 200;
            res.data = data;
            logger.info("===============用户登录 login 方法调用结束===============");
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "用户登录失败";
        }
        return res;
    }

    @GetMapping(value = "/getUserSession")
    public BaseResponseInfo getSessionUser(HttpServletRequest request) {
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
    public BaseResponseInfo logout(HttpServletRequest request, HttpServletResponse response) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            request.getSession().removeAttribute("user");
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
                                     HttpServletRequest request) throws NoSuchAlgorithmException {
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
                            @RequestParam("oldpwd") String oldpwd, HttpServletRequest request) {
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
}
