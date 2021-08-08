package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.Date;

public class MaterialExtend {
    private Long id;

    private Long materialId;

    private String barCode;

    private String commodityUnit;

    private String sku;

    private BigDecimal purchaseDecimal;

    private BigDecimal commodityDecimal;

    private BigDecimal wholesaleDecimal;

    private BigDecimal lowDecimal;

    private String defaultFlag;

    private Date createTime;

    private String createSerial;

    private String updateSerial;

    private Long updateTime;

    private Long tenantId;

    private String deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    public String getCommodityUnit() {
        return commodityUnit;
    }

    public void setCommodityUnit(String commodityUnit) {
        this.commodityUnit = commodityUnit == null ? null : commodityUnit.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public BigDecimal getPurchaseDecimal() {
        return purchaseDecimal;
    }

    public void setPurchaseDecimal(BigDecimal purchaseDecimal) {
        this.purchaseDecimal = purchaseDecimal;
    }

    public BigDecimal getCommodityDecimal() {
        return commodityDecimal;
    }

    public void setCommodityDecimal(BigDecimal commodityDecimal) {
        this.commodityDecimal = commodityDecimal;
    }

    public BigDecimal getWholesaleDecimal() {
        return wholesaleDecimal;
    }

    public void setWholesaleDecimal(BigDecimal wholesaleDecimal) {
        this.wholesaleDecimal = wholesaleDecimal;
    }

    public BigDecimal getLowDecimal() {
        return lowDecimal;
    }

    public void setLowDecimal(BigDecimal lowDecimal) {
        this.lowDecimal = lowDecimal;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag == null ? null : defaultFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateSerial() {
        return createSerial;
    }

    public void setCreateSerial(String createSerial) {
        this.createSerial = createSerial == null ? null : createSerial.trim();
    }

    public String getUpdateSerial() {
        return updateSerial;
    }

    public void setUpdateSerial(String updateSerial) {
        this.updateSerial = updateSerial == null ? null : updateSerial.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
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