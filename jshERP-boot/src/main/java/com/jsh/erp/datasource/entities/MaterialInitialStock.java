package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class MaterialInitialStock {
    private Long id;

    private Long materialId;

    private Long depotId;

    private BigDecimal number;

    private BigDecimal lowSafeStock;

    private BigDecimal highSafeStock;

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

    public Long getDepotId() {
        return depotId;
    }

    public void setDepotId(Long depotId) {
        this.depotId = depotId;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
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