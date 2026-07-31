package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrentStockVo {

    private Long depotId;

    private Long materialId;

    private BigDecimal currentNumber;
}
