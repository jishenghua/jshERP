package com.jsh.erp.datasource.entities;

public class SystemConfig {
    private Long id;

    private String companyName;

    private String companyContacts;

    private String companyAddress;

    private String companyTel;

    private String companyFax;

    private String companyPostCode;

    private String saleAgreement;

    private String depotFlag;

    private String customerFlag;

    private String minusStockFlag;

    private String purchaseBySaleFlag;

    private String multiLevelApprovalFlag;

    private String multiBillType;

    private String forceApprovalFlag;

    private String updateUnitPriceFlag;

    private String overLinkBillFlag;

    private String inOutManageFlag;

    private Long tenantId;

    private String deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyContacts() {
        return companyContacts;
    }

    public void setCompanyContacts(String companyContacts) {
        this.companyContacts = companyContacts == null ? null : companyContacts.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel == null ? null : companyTel.trim();
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax == null ? null : companyFax.trim();
    }

    public String getCompanyPostCode() {
        return companyPostCode;
    }

    public void setCompanyPostCode(String companyPostCode) {
        this.companyPostCode = companyPostCode == null ? null : companyPostCode.trim();
    }

    public String getSaleAgreement() {
        return saleAgreement;
    }

    public void setSaleAgreement(String saleAgreement) {
        this.saleAgreement = saleAgreement == null ? null : saleAgreement.trim();
    }

    public String getDepotFlag() {
        return depotFlag;
    }

    public void setDepotFlag(String depotFlag) {
        this.depotFlag = depotFlag == null ? null : depotFlag.trim();
    }

    public String getCustomerFlag() {
        return customerFlag;
    }

    public void setCustomerFlag(String customerFlag) {
        this.customerFlag = customerFlag == null ? null : customerFlag.trim();
    }

    public String getMinusStockFlag() {
        return minusStockFlag;
    }

    public void setMinusStockFlag(String minusStockFlag) {
        this.minusStockFlag = minusStockFlag == null ? null : minusStockFlag.trim();
    }

    public String getPurchaseBySaleFlag() {
        return purchaseBySaleFlag;
    }

    public void setPurchaseBySaleFlag(String purchaseBySaleFlag) {
        this.purchaseBySaleFlag = purchaseBySaleFlag == null ? null : purchaseBySaleFlag.trim();
    }

    public String getMultiLevelApprovalFlag() {
        return multiLevelApprovalFlag;
    }

    public void setMultiLevelApprovalFlag(String multiLevelApprovalFlag) {
        this.multiLevelApprovalFlag = multiLevelApprovalFlag == null ? null : multiLevelApprovalFlag.trim();
    }

    public String getMultiBillType() {
        return multiBillType;
    }

    public void setMultiBillType(String multiBillType) {
        this.multiBillType = multiBillType == null ? null : multiBillType.trim();
    }

    public String getForceApprovalFlag() {
        return forceApprovalFlag;
    }

    public void setForceApprovalFlag(String forceApprovalFlag) {
        this.forceApprovalFlag = forceApprovalFlag == null ? null : forceApprovalFlag.trim();
    }

    public String getUpdateUnitPriceFlag() {
        return updateUnitPriceFlag;
    }

    public void setUpdateUnitPriceFlag(String updateUnitPriceFlag) {
        this.updateUnitPriceFlag = updateUnitPriceFlag == null ? null : updateUnitPriceFlag.trim();
    }

    public String getOverLinkBillFlag() {
        return overLinkBillFlag;
    }

    public void setOverLinkBillFlag(String overLinkBillFlag) {
        this.overLinkBillFlag = overLinkBillFlag == null ? null : overLinkBillFlag.trim();
    }

    public String getInOutManageFlag() {
        return inOutManageFlag;
    }

    public void setInOutManageFlag(String inOutManageFlag) {
        this.inOutManageFlag = inOutManageFlag == null ? null : inOutManageFlag.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }
}