package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class AccountVo4Sum {

    private Long id;

    private BigDecimal accountSum;

    private BigDecimal accountSumByHead;

    private BigDecimal accountSumByDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAccountSum() {
        return accountSum;
    }

    public void setAccountSum(BigDecimal accountSum) {
        this.accountSum = accountSum;
    }

    public BigDecimal getAccountSumByHead() {
        return accountSumByHead;
    }

    public void setAccountSumByHead(BigDecimal accountSumByHead) {
        this.accountSumByHead = accountSumByHead;
    }

    public BigDecimal getAccountSumByDetail() {
        return accountSumByDetail;
    }

    public void setAccountSumByDetail(BigDecimal accountSumByDetail) {
        this.accountSumByDetail = accountSumByDetail;
    }

}