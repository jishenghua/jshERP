package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotItemStockWarningCount {

    private Long MId;

    private String MName;

    private String MModel;

    private String MaterialUnit;

    private String MColor;

    private String MStandard;

    private String MMfrs;

    private String unitName;

    private String MaterialOther;

    private String MOtherField1;

    private String MOtherField2;

    private String MOtherField3;

    private BigDecimal safetystock;//安全库存量

    private BigDecimal currentNumber;//库存

    private BigDecimal linjieNumber;//临界库存

    public Long getMId() {
        return MId;
    }

    public void setMId(Long MId) {
        this.MId = MId;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getMModel() {
        return MModel;
    }

    public void setMModel(String MModel) {
        this.MModel = MModel;
    }

    public String getMaterialUnit() {
        return MaterialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        MaterialUnit = materialUnit;
    }

    public String getMColor() {
        return MColor;
    }

    public void setMColor(String MColor) {
        this.MColor = MColor;
    }

    public String getMStandard() {
        return MStandard;
    }

    public void setMStandard(String MStandard) {
        this.MStandard = MStandard;
    }

    public String getMMfrs() {
        return MMfrs;
    }

    public void setMMfrs(String MMfrs) {
        this.MMfrs = MMfrs;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public BigDecimal getSafetystock() {
        return safetystock;
    }

    public void setSafetystock(BigDecimal safetystock) {
        this.safetystock = safetystock;
    }

    public BigDecimal getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(BigDecimal currentNumber) {
        this.currentNumber = currentNumber;
    }

    public BigDecimal getLinjieNumber() {
        return linjieNumber;
    }

    public void setLinjieNumber(BigDecimal linjieNumber) {
        this.linjieNumber = linjieNumber;
    }
}
