package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UnitModel implements Serializable {
    private DepotShowModel showModel = new DepotShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String UName = "";

    /**
     * ID
     */
    private Long unitID = 0l;

    /**
     * IDs 批量操作使用
     */
    private String unitIDs = "";

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


    public DepotShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(DepotShowModel showModel) {
        this.showModel = showModel;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public Long getUnitID() {
        return unitID;
    }

    public void setUnitID(Long unitID) {
        this.unitID = unitID;
    }

    public String getUnitIDs() {
        return unitIDs;
    }

    public void setUnitIDs(String unitIDs) {
        this.unitIDs = unitIDs;
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

}
