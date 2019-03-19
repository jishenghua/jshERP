package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;
import java.util.Date;

public class DepotHeadVo4InDetail {

    private String Number;

    private String MName;

    private String Model;

    private BigDecimal UnitPrice;

    private BigDecimal OperNumber;

    private BigDecimal AllPrice;

    private String SName;

    private String DName;

    private String OperTime;

    private String NewType;

    private Long tenantId;

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public BigDecimal getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        UnitPrice = unitPrice;
    }

    public BigDecimal getOperNumber() {
        return OperNumber;
    }

    public void setOperNumber(BigDecimal operNumber) {
        OperNumber = operNumber;
    }

    public BigDecimal getAllPrice() {
        return AllPrice;
    }

    public void setAllPrice(BigDecimal allPrice) {
        AllPrice = allPrice;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public String getOperTime() {
        return OperTime;
    }

    public void setOperTime(String operTime) {
        OperTime = operTime;
    }

    public String getNewType() {
        return NewType;
    }

    public void setNewType(String newType) {
        NewType = newType;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}