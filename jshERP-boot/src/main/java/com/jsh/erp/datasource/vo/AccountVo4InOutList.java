package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;

public class AccountVo4InOutList {

    private Long accountId;

    private String number;

    private String type;

    private String fromType;

    private String supplierName;

    private BigDecimal changeAmount;

    private BigDecimal balance;

    private String operTime;

    private String aList;

    private String amList;

    private Long tenantId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public String getaList() {
        return aList;
    }

    public void setaList(String aList) {
        this.aList = aList;
    }

    public String getAmList() {
        return amList;
    }

    public void setAmList(String amList) {
        this.amList = amList;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}