package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;

public class MaterialCountVo {

    private Long headerId;

    private BigDecimal materialCount;

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public BigDecimal getMaterialCount() {
        return materialCount;
    }

    public void setMaterialCount(BigDecimal materialCount) {
        this.materialCount = materialCount;
    }
}
