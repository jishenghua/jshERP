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
}
