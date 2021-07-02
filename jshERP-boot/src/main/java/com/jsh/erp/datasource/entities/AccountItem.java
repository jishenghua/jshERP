package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class AccountItem {
    private Long id;

    private Long headerId;

    private Long accountId;

    private Long inOutItemId;

    private Long billId;

    private BigDecimal needDebt;

    private BigDecimal finishDebt;

    private BigDecimal eachAmount;

    private String remark;

    private Long tenantId;

    private String deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getInOutItemId() {
        return inOutItemId;
    }

    public void setInOutItemId(Long inOutItemId) {
        this.inOutItemId = inOutItemId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public BigDecimal getNeedDebt() {
        return needDebt;
    }

    public void setNeedDebt(BigDecimal needDebt) {
        this.needDebt = needDebt;
    }

    public BigDecimal getFinishDebt() {
        return finishDebt;
    }

    public void setFinishDebt(BigDecimal finishDebt) {
        this.finishDebt = finishDebt;
    }

    public BigDecimal getEachAmount() {
        return eachAmount;
    }

    public void setEachAmount(BigDecimal eachAmount) {
        this.eachAmount = eachAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }
}