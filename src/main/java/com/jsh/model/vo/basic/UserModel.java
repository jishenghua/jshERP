package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserModel implements Serializable {
    private UserShowModel showModel = new UserShowModel();

    /*+++++用户登录开始+++++++++++*/
    private String username = "";
    private String password = "";
    /*+++++用户登录结束+++++++++++*/

    /**
     * ===============以下处理用户管理部分===============
     */
    /**
     * 用户登录名称
     */
    private String loginame;

    /**
     * 职位
     */
    private String position;

    /**
     * 部门
     */
    private String department;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话号码
     */
    private String phonenum;

    /**
     * 是否管理员 0 ==管理员  1==非管理员
     */
    private Short ismanager = 1;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 用户ID
     */
    private Long userID = 0l;

    /**
     * 用户IDs 批量操作使用
     */
    private String userIDs = "";

    /**
     * 每页显示的个数
     */
    private int pageSize = 10;

    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";

    /**
     * 根据标识判断是校验登录名称还是用户名称 0==用户名称 1==登录名称
     */
    private int checkFlag = 0;

    private String orgpwd = "";

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(UserShowModel showModel) {
        this.showModel = showModel;
    }

    public String getLoginame() {
        return loginame;
    }

    public void setLoginame(String loginame) {
        this.loginame = loginame;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public Short getIsmanager() {
        return ismanager;
    }

    public void setIsmanager(Short ismanager) {
        this.ismanager = ismanager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(String userIDs) {
        this.userIDs = userIDs;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(int checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getOrgpwd() {
        return orgpwd;
    }

    public void setOrgpwd(String orgpwd) {
        this.orgpwd = orgpwd;
    }

}
