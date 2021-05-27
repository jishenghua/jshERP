package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.entities.MaterialExtend;

import java.math.BigDecimal;

public class MaterialExtendVo4List extends MaterialExtend {

    private String supplier;

    private String originPlace;

    private String unit;

    private String brandName;

    private BigDecimal guaranteePeriod;

    private BigDecimal memberDecimal;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BigDecimal getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(BigDecimal guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public BigDecimal getMemberDecimal() {
        return memberDecimal;
    }

    public void setMemberDecimal(BigDecimal memberDecimal) {
        this.memberDecimal = memberDecimal;
    }
}
