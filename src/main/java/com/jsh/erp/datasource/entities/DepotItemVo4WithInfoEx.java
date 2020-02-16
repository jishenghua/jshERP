package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class DepotItemVo4WithInfoEx extends DepotItem{

    private Long MId;

    private String MName;

    private String MModel;

    private String MaterialUnit;

    private String MColor;

    private String MStandard;

    private String MMfrs;

    private String MOtherField1;

    private String MOtherField2;

    private String MOtherField3;

    private String DepotName;

    private String AnotherDepotName;

    private Long UnitId;

    private String UName;

    private BigDecimal presetPriceOne;

    private String priceStrategy;

    private String barCode;

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
        return DepotName;
    }

    public void setDepotName(String depotName) {
        DepotName = depotName;
    }

    public String getAnotherDepotName() {
        return AnotherDepotName;
    }

    public void setAnotherDepotName(String anotherDepotName) {
        AnotherDepotName = anotherDepotName;
    }

    public Long getUnitId() {
        return UnitId;
    }

    public void setUnitId(Long unitId) {
        UnitId = unitId;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public BigDecimal getPresetPriceOne() {
        return presetPriceOne;
    }

    public void setPresetPriceOne(BigDecimal presetPriceOne) {
        this.presetPriceOne = presetPriceOne;
    }

    public String getPriceStrategy() {
        return priceStrategy;
    }

    public void setPriceStrategy(String priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}