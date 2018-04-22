package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FunctionsModel implements Serializable {
    private FunctionsShowModel showModel = new FunctionsShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 编号
     */
    private String Number = "";
    /**
     * 名称
     */
    private String Name = "";
    /**
     * 上级编号
     */
    private String PNumber = "";
    /**
     * 链接
     */
    private String URL = "";
    /**
     * 收缩
     */
    private Boolean State = false;
    /**
     * 排序
     */
    private String Sort = "";
    /**
     * 启用
     */
    private Boolean Enabled = false;
    /**
     * 类型
     */
    private String Type = "";
    /**
     * 功能按钮
     */
    private String PushBtn = "";
    /**
     * 拥有的功能列表
     */
    private String hasFunctions = "";
    /**
     * 分类ID
     */
    private Long functionsID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String functionsIDs = "";

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
     * UBType，UserBusiness类型
     */
    private String UBType = "";

    /**
     * UBKeyId，UserBusiness关键id
     */
    private String UBKeyId = "";

    public FunctionsShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(FunctionsShowModel showModel) {
        this.showModel = showModel;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPNumber() {
        return PNumber;
    }

    public void setPNumber(String pNumber) {
        PNumber = pNumber;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public Boolean getState() {
        return State;
    }

    public void setState(Boolean state) {
        State = state;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Long getFunctionsID() {
        return functionsID;
    }

    public void setFunctionsID(Long functionsID) {
        this.functionsID = functionsID;
    }

    public String getFunctionsIDs() {
        return functionsIDs;
    }

    public void setFunctionsIDs(String functionsIDs) {
        this.functionsIDs = functionsIDs;
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

    public String getUBType() {
        return UBType;
    }

    public void setUBType(String uBType) {
        UBType = uBType;
    }

    public String getUBKeyId() {
        return UBKeyId;
    }

    public void setUBKeyId(String uBKeyId) {
        UBKeyId = uBKeyId;
    }

    public String getHasFunctions() {
        return hasFunctions;
    }

    public void setHasFunctions(String hasFunctions) {
        this.hasFunctions = hasFunctions;
    }

    public String getPushBtn() {
        return PushBtn;
    }

    public void setPushBtn(String pushBtn) {
        PushBtn = pushBtn;
    }
}
