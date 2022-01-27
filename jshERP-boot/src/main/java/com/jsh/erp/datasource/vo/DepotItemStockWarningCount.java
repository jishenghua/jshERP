package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotItemStockWarningCount {

    private Long MId;

    private String barCode;

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

    private String depotName;

    private BigDecimal currentNumber;

    private BigDecimal lowSafeStock;

    private BigDecimal highSafeStock;

    private BigDecimal lowCritical;

    private BigDecimal highCritical;

    public Long getMId() {
        return MId;
    }

    public void setMId(Long MId) {
        this.MId = MId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public BigDecimal getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(BigDecimal currentNumber) {
        this.currentNumber = currentNumber;
    }

    public BigDecimal getLowSafeStock() {
        return lowSafeStock;
    }

    public void setLowSafeStock(BigDecimal lowSafeStock) {
        this.lowSafeStock = lowSafeStock;
    }

    public BigDecimal getHighSafeStock() {
        return highSafeStock;
    }

    public void setHighSafeStock(BigDecimal highSafeStock) {
        this.highSafeStock = highSafeStock;
    }

    public BigDecimal getLowCritical() {
        return lowCritical;
    }

    public void setLowCritical(BigDecimal lowCritical) {
        this.lowCritical = lowCritical;
    }

    public BigDecimal getHighCritical() {
        return highCritical;
    }

    public void setHighCritical(BigDecimal highCritical) {
        this.highCritical = highCritical;
    }
}
