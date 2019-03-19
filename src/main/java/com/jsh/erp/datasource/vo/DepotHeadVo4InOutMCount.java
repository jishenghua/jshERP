package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotHeadVo4InOutMCount {

    private Long MaterialId;

    private String mName;

    private String Model;

    private String categoryName;

    private BigDecimal numSum;

    private BigDecimal priceSum;

    private Long tenantId;

    public Long getMaterialId() {
        return MaterialId;
    }

    public void setMaterialId(Long materialId) {
        MaterialId = materialId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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