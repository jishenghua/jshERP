package com.jsh.erp.datasource.entities;

import java.util.Date;

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

    private Integer userNumLimit;

    private String expireTime;

    private String leaderFlagStr;

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

    public Integer getUserNumLimit() {
        return userNumLimit;
    }

    public void setUserNumLimit(Integer userNumLimit) {
        this.userNumLimit = userNumLimit;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLeaderFlagStr() {
        return leaderFlagStr;
    }

    public void setLeaderFlagStr(String leaderFlagStr) {
        this.leaderFlagStr = leaderFlagStr;
    }
}
