package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;

public class FinishDepositVo {

    private String number;

    private BigDecimal finishDeposit;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getFinishDeposit() {
        return finishDeposit;
    }

    public void setFinishDeposit(BigDecimal finishDeposit) {
        this.finishDeposit = finishDeposit;
    }
}
