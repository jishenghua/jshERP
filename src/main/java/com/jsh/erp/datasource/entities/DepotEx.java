package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/2/25 11:40
 */
public class DepotEx extends Depot{
    //负责人名字
    private String principalName;

    private BigDecimal stock;

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }
}
