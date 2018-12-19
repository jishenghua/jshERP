package com.jsh.erp.datasource.vo;


public class DepotHeadVo4InOutMCount {

    private Long MaterialId;

    private String mName;

    private String Model;

    private String categoryName;

    private Double numSum;

    private Double priceSum;

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

    public Double getNumSum() {
        return numSum;
    }

    public void setNumSum(Double numSum) {
        this.numSum = numSum;
    }

    public Double getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(Double priceSum) {
        this.priceSum = priceSum;
    }
}