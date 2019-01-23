package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class MaterialVo4Unit {

    private Long id;

    private Long categoryid;

    private String name;

    private String mfrs;

    private BigDecimal packing;

    private BigDecimal safetystock;

    private String model;

    private String standard;

    private String color;

    private String unit;

    private String remark;

    private BigDecimal retailprice;

    private BigDecimal lowprice;

    private BigDecimal presetpriceone;

    private BigDecimal presetpricetwo;

    private Long unitid;

    private String firstoutunit;

    private String firstinunit;

    private String pricestrategy;

    private Boolean enabled;

    private String otherfield1;

    private String otherfield2;

    private String otherfield3;

    private String unitName;

    private String categoryName;

    private String materialOther;
    /**
     * 2019-01-21新增字段enableSerialNumber
     *是否开启序列号
     * */
    private Boolean enableSerialNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMfrs() {
        return mfrs;
    }

    public void setMfrs(String mfrs) {
        this.mfrs = mfrs;
    }

    public BigDecimal getPacking() {
        return packing;
    }

    public void setPacking(BigDecimal packing) {
        this.packing = packing;
    }

    public BigDecimal getSafetystock() {
        return safetystock;
    }

    public void setSafetystock(BigDecimal safetystock) {
        this.safetystock = safetystock;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getRetailprice() {
        return retailprice;
    }

    public void setRetailprice(BigDecimal retailprice) {
        this.retailprice = retailprice;
    }

    public BigDecimal getLowprice() {
        return lowprice;
    }

    public void setLowprice(BigDecimal lowprice) {
        this.lowprice = lowprice;
    }

    public BigDecimal getPresetpriceone() {
        return presetpriceone;
    }

    public void setPresetpriceone(BigDecimal presetpriceone) {
        this.presetpriceone = presetpriceone;
    }

    public BigDecimal getPresetpricetwo() {
        return presetpricetwo;
    }

    public void setPresetpricetwo(BigDecimal presetpricetwo) {
        this.presetpricetwo = presetpricetwo;
    }

    public Long getUnitid() {
        return unitid;
    }

    public void setUnitid(Long unitid) {
        this.unitid = unitid;
    }

    public String getFirstoutunit() {
        return firstoutunit;
    }

    public void setFirstoutunit(String firstoutunit) {
        this.firstoutunit = firstoutunit;
    }

    public String getFirstinunit() {
        return firstinunit;
    }

    public void setFirstinunit(String firstinunit) {
        this.firstinunit = firstinunit;
    }

    public String getPricestrategy() {
        return pricestrategy;
    }

    public void setPricestrategy(String pricestrategy) {
        this.pricestrategy = pricestrategy;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getOtherfield1() {
        return otherfield1;
    }

    public void setOtherfield1(String otherfield1) {
        this.otherfield1 = otherfield1;
    }

    public String getOtherfield2() {
        return otherfield2;
    }

    public void setOtherfield2(String otherfield2) {
        this.otherfield2 = otherfield2;
    }

    public String getOtherfield3() {
        return otherfield3;
    }

    public void setOtherfield3(String otherfield3) {
        this.otherfield3 = otherfield3;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMaterialOther() {
        return materialOther;
    }

    public void setMaterialOther(String materialOther) {
        this.materialOther = materialOther;
    }

    public Boolean getEnableSerialNumber() {
        return enableSerialNumber;
    }

    public void setEnableSerialNumber(Boolean enableSerialNumber) {
        this.enableSerialNumber = enableSerialNumber;
    }
}