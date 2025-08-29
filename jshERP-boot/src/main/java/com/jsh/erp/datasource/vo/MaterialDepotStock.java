package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaterialDepotStock {

    private Long id;

    private Long materialId;

    private Long depotId;

    private String depotName;

    private BigDecimal currentNumber;

    private BigDecimal currentUnitPrice;

    private BigDecimal purchaseDecimal;

    private BigDecimal unitPrice;

    private BigDecimal allPrice;

}