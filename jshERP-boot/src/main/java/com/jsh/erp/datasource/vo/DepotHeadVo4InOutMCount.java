package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;

public class DepotHeadVo4InOutMCount {

    private Long materialId;

    private String barCode;

    private String mName;

    private String model;

    private String standard;

    private String color;

    private String brand;

    private String mfrs;

    private String categoryName;

    private String materialUnit;

    private BigDecimal numSum;

    private BigDecimal priceSum;

    private Long tenantId;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMfrs() {
        return mfrs;
    }

    public void setMfrs(String mfrs) {
        this.mfrs = mfrs;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public BigDecimal getNumSum() {
        return numSum;
    }

    public void setNumSum(BigDecimal numSum) {
        this.numSum = numSum;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}