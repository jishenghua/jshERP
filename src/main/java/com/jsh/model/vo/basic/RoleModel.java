package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RoleModel implements Serializable {
    private RoleShowModel showModel = new RoleShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 角色名称
     */
    private String Name = "";

    /**
     * 分类ID
     */
    private Long roleID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String roleIDs = "";

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

    public RoleShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(RoleShowModel showModel) {
        this.showModel = showModel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public String getRoleIDs() {
        return roleIDs;
    }

    public void setRoleIDs(String roleIDs) {
        this.roleIDs = roleIDs;
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

}
