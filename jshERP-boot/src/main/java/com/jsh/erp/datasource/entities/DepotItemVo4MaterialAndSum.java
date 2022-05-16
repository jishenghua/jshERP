package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class DepotItemVo4MaterialAndSum {

    private Long materialExtendId;

    private BigDecimal operNumber;

    public Long getMaterialExtendId() {
        return materialExtendId;
    }

    public void setMaterialExtendId(Long materialExtendId) {
        this.materialExtendId = materialExtendId;
    }

    public BigDecimal getOperNumber() {
        return operNumber;
    }

    public void setOperNumber(BigDecimal operNumber) {
        this.operNumber = operNumber;
    }
}