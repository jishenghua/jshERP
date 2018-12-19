package com.jsh.erp.datasource.vo;

public class AccountItemVo4List {

    private Long id;

    private Long headerid;

    private Long accountid;

    private Long inoutitemid;

    private Double eachamount;

    private String remark;

    private String accountName;

    private String inOutItemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHeaderid() {
        return headerid;
    }

    public void setHeaderid(Long headerid) {
        this.headerid = headerid;
    }

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    public Long getInoutitemid() {
        return inoutitemid;
    }

    public void setInoutitemid(Long inoutitemid) {
        this.inoutitemid = inoutitemid;
    }

    public Double getEachamount() {
        return eachamount;
    }

    public void setEachamount(Double eachamount) {
        this.eachamount = eachamount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getInOutItemName() {
        return inOutItemName;
    }

    public void setInOutItemName(String inOutItemName) {
        this.inOutItemName = inOutItemName;
    }
}