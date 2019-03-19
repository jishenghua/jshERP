package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotHeadVo4StatementAccount {

    private String number;

    private String type;

    private BigDecimal discountLastMoney;

    private BigDecimal changeAmount;

    private BigDecimal allPrice;

    private String supplierName;

    private String oTime;

    private Long tenantId;

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

    public BigDecimal getDiscountLastMoney() {
        return discountLastMoney;
    }

    public void setDiscountLastMoney(BigDecimal discountLastMoney) {
        this.discountLastMoney = discountLastMoney;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(BigDecimal allPrice) {
        this.allPrice = allPrice;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getoTime() {
        return oTime;
    }

    public void setoTime(String oTime) {
        this.oTime = oTime;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}