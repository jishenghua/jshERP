package com.jsh.erp.datasource.vo;


import java.util.Date;

public class DepotHeadVo4InDetail {

    private String Number;

    private String MName;

    private String Model;

    private Double UnitPrice;

    private Double OperNumber;

    private Double AllPrice;

    private String SName;

    private String DName;

    private String OperTime;

    private String NewType;

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

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public Double getOperNumber() {
        return OperNumber;
    }

    public void setOperNumber(Double operNumber) {
        OperNumber = operNumber;
    }

    public Double getAllPrice() {
        return AllPrice;
    }

    public void setAllPrice(Double allPrice) {
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
}