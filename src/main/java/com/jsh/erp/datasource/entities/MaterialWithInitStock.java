package com.jsh.erp.datasource.entities;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Map;

public class MaterialWithInitStock extends Material {

    private Map<Long, BigDecimal> stockMap;

    private JSONObject materialExObj;

    public Map<Long, BigDecimal> getStockMap() {
        return stockMap;
    }

    public void setStockMap(Map<Long, BigDecimal> stockMap) {
        this.stockMap = stockMap;
    }

    public JSONObject getMaterialExObj() {
        return materialExObj;
    }

    public void setMaterialExObj(JSONObject materialExObj) {
        this.materialExObj = materialExObj;
    }
}