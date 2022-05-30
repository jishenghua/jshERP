package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class MaterialInitialStockWithMaterial {

    private Long materialId;

    private BigDecimal number;

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}