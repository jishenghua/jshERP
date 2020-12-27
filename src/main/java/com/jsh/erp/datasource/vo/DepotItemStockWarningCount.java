package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotItemStockWarningCount {

    private String MaterialName;

    private String MaterialModel;

    private String MaterialStandard;

    private String MMfrs;

    private String categoryName;

    private String MaterialOther;

    private String MOtherField1;

    private String MOtherField2;

    private String MOtherField3;

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

    public String getMaterialStandard() {
        return MaterialStandard;
    }

    public void setMaterialStandard(String materialStandard) {
        MaterialStandard = materialStandard;
    }

    public String getMMfrs() {
        return MMfrs;
    }

    public void setMMfrs(String MMfrs) {
        this.MMfrs = MMfrs;
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

    public String getMOtherField1() {
        return MOtherField1;
    }

    public void setMOtherField1(String MOtherField1) {
        this.MOtherField1 = MOtherField1;
    }

    public String getMOtherField2() {
        return MOtherField2;
    }

    public void setMOtherField2(String MOtherField2) {
        this.MOtherField2 = MOtherField2;
    }

    public String getMOtherField3() {
        return MOtherField3;
    }

    public void setMOtherField3(String MOtherField3) {
        this.MOtherField3 = MOtherField3;
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
