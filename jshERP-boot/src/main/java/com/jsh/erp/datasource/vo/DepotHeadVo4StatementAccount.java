package com.jsh.erp.datasource.vo;


import java.math.BigDecimal;

public class DepotHeadVo4StatementAccount {

    private Long id;

    private String supplier;

    private String contacts;

    private String telephone;

    private String phoneNum;

    private String email;

    /**
     * 起始期初金额
     */
    private BigDecimal beginNeed;

    /**
     * 上期欠款金额
     */
    private BigDecimal preDebtMoney;

    /**
     * 上期退货的欠款金额
     */
    private BigDecimal preReturnDebtMoney;


    /**
     * 上期收付款
     */
    private BigDecimal preBackMoney;

    /**
     * 期初应收
     */
    private BigDecimal preNeed;

    /**
     * 本期欠款
     */
    private BigDecimal debtMoney;

    /**
     * 本期退货的欠款金额
     */
    private BigDecimal returnDebtMoney;

    /**
     * 本期收付款
     */
    private BigDecimal backMoney;

    /**
     * 期末应收
     */
    private BigDecimal allNeed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBeginNeed() {
        return beginNeed;
    }

    public void setBeginNeed(BigDecimal beginNeed) {
        this.beginNeed = beginNeed;
    }

    public BigDecimal getPreDebtMoney() {
        return preDebtMoney;
    }

    public void setPreDebtMoney(BigDecimal preDebtMoney) {
        this.preDebtMoney = preDebtMoney;
    }

    public BigDecimal getPreReturnDebtMoney() {
        return preReturnDebtMoney;
    }

    public void setPreReturnDebtMoney(BigDecimal preReturnDebtMoney) {
        this.preReturnDebtMoney = preReturnDebtMoney;
    }

    public BigDecimal getPreBackMoney() {
        return preBackMoney;
    }

    public void setPreBackMoney(BigDecimal preBackMoney) {
        this.preBackMoney = preBackMoney;
    }

    public BigDecimal getPreNeed() {
        return preNeed;
    }

    public void setPreNeed(BigDecimal preNeed) {
        this.preNeed = preNeed;
    }

    public BigDecimal getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(BigDecimal debtMoney) {
        this.debtMoney = debtMoney;
    }

    public BigDecimal getReturnDebtMoney() {
        return returnDebtMoney;
    }

    public void setReturnDebtMoney(BigDecimal returnDebtMoney) {
        this.returnDebtMoney = returnDebtMoney;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public BigDecimal getAllNeed() {
        return allNeed;
    }

    public void setAllNeed(BigDecimal allNeed) {
        this.allNeed = allNeed;
    }
}