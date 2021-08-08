package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotItemVo4Stock {

    private BigDecimal inTotal;
    private BigDecimal outTotal;
    private BigDecimal transfInTotal;
    private BigDecimal transfOutTotal;
    private BigDecimal assemInTotal;
    private BigDecimal assemOutTotal;
    private BigDecimal disAssemInTotal;
    private BigDecimal disAssemOutTotal;

    public BigDecimal getInTotal() {
        return inTotal;
    }

    public void setInTotal(BigDecimal inTotal) {
        this.inTotal = inTotal;
    }

    public BigDecimal getOutTotal() {
        return outTotal;
    }

    public void setOutTotal(BigDecimal outTotal) {
        this.outTotal = outTotal;
    }

    public BigDecimal getTransfInTotal() {
        return transfInTotal;
    }

    public void setTransfInTotal(BigDecimal transfInTotal) {
        this.transfInTotal = transfInTotal;
    }

    public BigDecimal getTransfOutTotal() {
        return transfOutTotal;
    }

    public void setTransfOutTotal(BigDecimal transfOutTotal) {
        this.transfOutTotal = transfOutTotal;
    }

    public BigDecimal getAssemInTotal() {
        return assemInTotal;
    }

    public void setAssemInTotal(BigDecimal assemInTotal) {
        this.assemInTotal = assemInTotal;
    }

    public BigDecimal getAssemOutTotal() {
        return assemOutTotal;
    }

    public void setAssemOutTotal(BigDecimal assemOutTotal) {
        this.assemOutTotal = assemOutTotal;
    }

    public BigDecimal getDisAssemInTotal() {
        return disAssemInTotal;
    }

    public void setDisAssemInTotal(BigDecimal disAssemInTotal) {
        this.disAssemInTotal = disAssemInTotal;
    }

    public BigDecimal getDisAssemOutTotal() {
        return disAssemOutTotal;
    }

    public void setDisAssemOutTotal(BigDecimal disAssemOutTotal) {
        this.disAssemOutTotal = disAssemOutTotal;
    }
}
