package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class DepotHeadVo4Body {

    private Long id;

    private String info;

    private String inserted;

    private String deleted;

    private String updated;

    private BigDecimal preTotalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInserted() {
        return inserted;
    }

    public void setInserted(String inserted) {
        this.inserted = inserted;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public BigDecimal getPreTotalPrice() {
        return preTotalPrice;
    }

    public void setPreTotalPrice(BigDecimal preTotalPrice) {
        this.preTotalPrice = preTotalPrice;
    }
}