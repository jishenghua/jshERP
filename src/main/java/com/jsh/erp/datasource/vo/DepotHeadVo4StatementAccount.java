package com.jsh.erp.datasource.vo;


public class DepotHeadVo4StatementAccount {

    private String number;

    private String type;

    private Double discountLastMoney;

    private Double changeAmount;

    private Double allPrice;

    private String supplierName;

    private String oTime;

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

    public Double getDiscountLastMoney() {
        return discountLastMoney;
    }

    public void setDiscountLastMoney(Double discountLastMoney) {
        this.discountLastMoney = discountLastMoney;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
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
}