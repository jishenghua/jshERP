package com.jsh.erp.datasource.entities;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/3/8 15:12
 */
public class UserEx extends User{
    //机构简称
    private String orgAbr;
    //机构id
    private Long orgaId;
    //用户在部门中排序
    private String userBlngOrgaDsplSeq;
    //机构用户关联关系id
    private Long orgaUserRelId;

    private Long roleId;

    private String roleName;

    private String userType;

    private String userNumLimit;

    private String billsNumLimit;

    public String getOrgAbr() {
        return orgAbr;
    }

    public void setOrgAbr(String orgAbr) {
        this.orgAbr = orgAbr;
    }

    public Long getOrgaId() {
        return orgaId;
    }

    public void setOrgaId(Long orgaId) {
        this.orgaId = orgaId;
    }

    public String getUserBlngOrgaDsplSeq() {
        return userBlngOrgaDsplSeq;
    }

    public void setUserBlngOrgaDsplSeq(String userBlngOrgaDsplSeq) {
        this.userBlngOrgaDsplSeq = userBlngOrgaDsplSeq;
    }

    public Long getOrgaUserRelId() {
        return orgaUserRelId;
    }

    public void setOrgaUserRelId(Long orgaUserRelId) {
        this.orgaUserRelId = orgaUserRelId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserNumLimit() {
        return userNumLimit;
    }

    public void setUserNumLimit(String userNumLimit) {
        this.userNumLimit = userNumLimit;
    }

    public String getBillsNumLimit() {
        return billsNumLimit;
    }

    public void setBillsNumLimit(String billsNumLimit) {
        this.billsNumLimit = billsNumLimit;
    }
}
