package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserBusinessModel implements Serializable {
    private UserBusinessShowModel showModel = new UserBusinessShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 角色名称
     */
    private String Type = "";

    /**
     * 主ID
     */
    private String KeyId = "";

    /**
     * 值
     */
    private String Value = "";

    private String BtnStr = "";

    /**
     * 分类ID
     */
    private Long userBusinessID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String userBusinessIDs = "";

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

    public UserBusinessShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(UserBusinessShowModel showModel) {
        this.showModel = showModel;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getKeyId() {
        return KeyId;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Long getUserBusinessID() {
        return userBusinessID;
    }

    public void setUserBusinessID(Long userBusinessID) {
        this.userBusinessID = userBusinessID;
    }

    public String getUserBusinessIDs() {
        return userBusinessIDs;
    }

    public void setUserBusinessIDs(String userBusinessIDs) {
        this.userBusinessIDs = userBusinessIDs;
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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getBtnStr() {
        return BtnStr;
    }

    public void setBtnStr(String btnStr) {
        BtnStr = btnStr;
    }
}
