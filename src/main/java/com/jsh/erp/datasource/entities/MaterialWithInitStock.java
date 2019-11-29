package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.Map;

public class MaterialWithInitStock extends Material {

    private Map<Long, BigDecimal> stockMap;

    public Map<Long, BigDecimal> getStockMap() {
        return stockMap;
    }

    public void setStockMap(Map<Long, BigDecimal> stockMap) {
        this.stockMap = stockMap;
    }
}