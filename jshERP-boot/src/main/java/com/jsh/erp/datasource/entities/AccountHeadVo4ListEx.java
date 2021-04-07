package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.Date;

public class AccountHeadVo4ListEx {

    private Long id;

    private String type;

    private Long organId;

    private Long handsPersonId;

    private BigDecimal changeAmount;

    private BigDecimal totalPrice;

    private Long accountId;

    private String billNo;

    private Date billTime;

    private String remark;

    private Long tenantId;

    private String deleteFlag;

    private String organName;

    private String handsPersonName;

    private Long creator;

    private String userName;

    private String accountName;

    private String billTimeStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public Long getHandsPersonId() {
        return handsPersonId;
    }

    public void setHandsPersonId(Long handsPersonId) {
        this.handsPersonId = handsPersonId;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getHandsPersonName() {
        return handsPersonName;
    }

    public void setHandsPersonName(String handsPersonName) {
        this.handsPersonName = handsPersonName;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBillTimeStr() {
        return billTimeStr;
    }

    public void setBillTimeStr(String billTimeStr) {
        this.billTimeStr = billTimeStr;
    }
}