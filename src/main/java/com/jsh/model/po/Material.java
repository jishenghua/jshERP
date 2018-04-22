package com.jsh.model.po;

import java.util.Map;

@SuppressWarnings("serial")
public class Material implements java.io.Serializable {
    private Long Id;
    private MaterialCategory materialCategory;
    private String Name;
    private String Mfrs;
    private Double Packing;
    private Double SafetyStock;
    private String Model;
    private String Standard;
    private String Color;
    private String Unit;
    private Double RetailPrice;
    private Double LowPrice;
    private Double PresetPriceOne;
    private Double PresetPriceTwo;
    private Unit UnitId;
    private String FirstOutUnit;
    private String FirstInUnit;
    private String PriceStrategy;
    private String Remark;
    private Boolean Enabled;
    private String OtherField1;
    private String OtherField2;
    private String OtherField3;

    //----------以下属性导入exel表格使用--------------------
    /**
     * 类型 right--正确 warn--警告  wrong--错误
     */
    private Map<Integer, String> cellInfo;

    /**
     * 行号
     */
    private Integer rowLineNum;

    private String safetyStockStr;

    public Material() {

    }

    public Material(Long Id) {
        this.Id = Id;
    }

    public Material(MaterialCategory materialCategory, String name, String mfrs, Double packing,
                    Double safetyStock, String model, String standard, String color, String unit, String remark,
                    Double retailPrice, Double lowPrice, Double presetPriceOne, Double presetPriceTwo,
                    Unit unitId, String firstOutUnit, String firstInUnit, String priceStrategy, Boolean enabled,
                    String otherField1, String otherField2, String otherField3) {
        super();
        this.materialCategory = materialCategory;
        Name = name;
        Mfrs = mfrs;
        Packing = packing;
        SafetyStock = safetyStock;
        Model = model;
        Standard = standard;
        Color = color;
        Unit = unit;
        RetailPrice = retailPrice;
        LowPrice = lowPrice;
        PresetPriceOne = presetPriceOne;
        PresetPriceTwo = presetPriceTwo;
        Remark = remark;
        UnitId = unitId;
        FirstOutUnit = firstOutUnit;
        FirstInUnit = firstInUnit;
        PriceStrategy = priceStrategy;
        Enabled = enabled;
        OtherField1 = otherField1;
        OtherField2 = otherField2;
        OtherField3 = otherField3;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public MaterialCategory getMaterialCategory() {
        return materialCategory;
    }

    public void setMaterialCategory(MaterialCategory materialCategory) {
        this.materialCategory = materialCategory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String standard) {
        Standard = standard;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public Double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        RetailPrice = retailPrice;
    }

    public Double getLowPrice() {
        return LowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        LowPrice = lowPrice;
    }

    public Double getPresetPriceOne() {
        return PresetPriceOne;
    }

    public void setPresetPriceOne(Double presetPriceOne) {
        PresetPriceOne = presetPriceOne;
    }

    public Double getPresetPriceTwo() {
        return PresetPriceTwo;
    }

    public void setPresetPriceTwo(Double presetPriceTwo) {
        PresetPriceTwo = presetPriceTwo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getMfrs() {
        return Mfrs;
    }

    public void setMfrs(String mfrs) {
        Mfrs = mfrs;
    }

    public Double getPacking() {
        return Packing;
    }

    public void setPacking(Double packing) {
        Packing = packing;
    }

    public Double getSafetyStock() {
        return SafetyStock;
    }

    public void setSafetyStock(Double safetyStock) {
        SafetyStock = safetyStock;
    }

    public Unit getUnitId() {
        return UnitId;
    }

    public void setUnitId(Unit unitId) {
        UnitId = unitId;
    }

    public String getFirstOutUnit() {
        return FirstOutUnit;
    }

    public void setFirstOutUnit(String firstOutUnit) {
        FirstOutUnit = firstOutUnit;
    }

    public String getFirstInUnit() {
        return FirstInUnit;
    }

    public void setFirstInUnit(String firstInUnit) {
        FirstInUnit = firstInUnit;
    }

    public String getPriceStrategy() {
        return PriceStrategy;
    }

    public void setPriceStrategy(String priceStrategy) {
        PriceStrategy = priceStrategy;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
    }

    public String getOtherField1() {
        return OtherField1;
    }

    public void setOtherField1(String otherField1) {
        OtherField1 = otherField1;
    }

    public String getOtherField3() {
        return OtherField3;
    }

    public void setOtherField3(String otherField3) {
        OtherField3 = otherField3;
    }

    public String getOtherField2() {
        return OtherField2;
    }

    public void setOtherField2(String otherField2) {
        OtherField2 = otherField2;
    }

    public Map<Integer, String> getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(Map<Integer, String> cellInfo) {
        this.cellInfo = cellInfo;
    }

    public Integer getRowLineNum() {
        return rowLineNum;
    }

    public void setRowLineNum(Integer rowLineNum) {
        this.rowLineNum = rowLineNum;
    }

    public String getSafetyStockStr() {
        return safetyStockStr;
    }

    public void setSafetyStockStr(String safetyStockStr) {
        this.safetyStockStr = safetyStockStr;
    }
}
