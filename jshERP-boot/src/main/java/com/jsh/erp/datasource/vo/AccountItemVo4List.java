package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.entities.AccountItem;

public class AccountItemVo4List extends AccountItem {

    private String accountName;

    private String inOutItemName;

    private String billNumber;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getInOutItemName() {
        return inOutItemName;
    }

    public void setInOutItemName(String inOutItemName) {
        this.inOutItemName = inOutItemName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
}