package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotItemStockWarningCount {


    private String MaterialName;

    private String MaterialModel;

    private String categoryName;

    private String MaterialOther;

    private String MaterialUnit;

    private BigDecimal safetystock;//安全库存量

    private BigDecimal BasicInNumber;//入库量

    private BigDecimal BasicOutNumber;//出库量


    private BigDecimal BasicNumber;//库存

    private BigDecimal BasicLinjieNumber;//临界库存

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public String getMaterialModel() {
        return MaterialModel;
    }

    public void setMaterialModel(String materialModel) {
        MaterialModel = materialModel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMaterialOther() {
        return MaterialOther;
    }

    public void setMaterialOther(String materialOther) {
        MaterialOther = materialOther;
    }

    public String getMaterialUnit() {
        return MaterialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        MaterialUnit = materialUnit;
    }

    public BigDecimal getSafetystock() {
        return safetystock;
    }

    public void setSafetystock(BigDecimal safetystock) {
        this.safetystock = safetystock;
    }

    public BigDecimal getBasicInNumber() {
        return BasicInNumber;
    }

    public void setBasicInNumber(BigDecimal basicInNumber) {
        BasicInNumber = basicInNumber;
    }

    public BigDecimal getBasicOutNumber() {
        return BasicOutNumber;
    }

    public void setBasicOutNumber(BigDecimal basicOutNumber) {
        BasicOutNumber = basicOutNumber;
    }

    public BigDecimal getBasicNumber() {
        return BasicNumber;
    }

    public void setBasicNumber(BigDecimal basicNumber) {
        BasicNumber = basicNumber;
    }

    public BigDecimal getBasicLinjieNumber() {
        return BasicLinjieNumber;
    }

    public void setBasicLinjieNumber(BigDecimal basicLinjieNumber) {
        BasicLinjieNumber = basicLinjieNumber;
    }
}
