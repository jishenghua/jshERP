package com.jsh.erp.datasource.entities;

public class Unit {
    private Long id;

    private String name;

    private String basicUnit;

    private String otherUnit;

    private String otherUnitTwo;

    private String otherUnitThree;

    private Integer ratio;

    private Integer ratioTwo;

    private Integer ratioThree;

    private Long tenantId;

    private String deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBasicUnit() {
        return basicUnit;
    }

    public void setBasicUnit(String basicUnit) {
        this.basicUnit = basicUnit == null ? null : basicUnit.trim();
    }

    public String getOtherUnit() {
        return otherUnit;
    }

    public void setOtherUnit(String otherUnit) {
        this.otherUnit = otherUnit == null ? null : otherUnit.trim();
    }

    public String getOtherUnitTwo() {
        return otherUnitTwo;
    }

    public void setOtherUnitTwo(String otherUnitTwo) {
        this.otherUnitTwo = otherUnitTwo == null ? null : otherUnitTwo.trim();
    }

    public String getOtherUnitThree() {
        return otherUnitThree;
    }

    public void setOtherUnitThree(String otherUnitThree) {
        this.otherUnitThree = otherUnitThree == null ? null : otherUnitThree.trim();
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Integer getRatioTwo() {
        return ratioTwo;
    }

    public void setRatioTwo(Integer ratioTwo) {
        this.ratioTwo = ratioTwo;
    }

    public Integer getRatioThree() {
        return ratioThree;
    }

    public void setRatioThree(Integer ratioThree) {
        this.ratioThree = ratioThree;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }
}