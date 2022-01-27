package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.entities.Log;

public class LogVo4List extends Log {

    private String loginName;

    private String userName;

    private String createTimeStr;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}