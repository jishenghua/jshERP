package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;
import java.util.Date;

public class DepotHead {
    private Long id;

    private String type;

    private String subType;

    private String defaultNumber;

    private String number;

    private Date createTime;

    private Date operTime;

    private Long organId;

    private Long handsPersonId;

    private Long creator;

    private Long accountId;

    private BigDecimal changeAmount;

    private BigDecimal totalPrice;

    private String payType;

    private String billType;

    private String remark;

    private String fileName;

    private String salesMan;

    private String accountIdList;

    private String accountMoneyList;

    private BigDecimal discount;

    private BigDecimal discountMoney;

    private BigDecimal discountLastMoney;

    private BigDecimal otherMoney;

    private String otherMoneyList;

    private String otherMoneyItem;

    private Integer accountDay;

    private String status;

    private String linkNumber;

    private Long tenantId;

    private String deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType == null ? null : subType.trim();
    }

    public String getDefaultNumber() {
        return defaultNumber;
    }

    public void setDefaultNumber(String defaultNumber) {
        this.defaultNumber = defaultNumber == null ? null : defaultNumber.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public Long getOrganId() {
        return organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public Long getHandsPersonId() {
        return handsPersonId;
    }

    public void setHandsPersonId(Long handsPersonId) {
        this.handsPersonId = handsPersonId;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(String salesMan) {
        this.salesMan = salesMan == null ? null : salesMan.trim();
    }

    public String getAccountIdList() {
        return accountIdList;
    }

    public void setAccountIdList(String accountIdList) {
        this.accountIdList = accountIdList == null ? null : accountIdList.trim();
    }

    public String getAccountMoneyList() {
        return accountMoneyList;
    }

    public void setAccountMoneyList(String accountMoneyList) {
        this.accountMoneyList = accountMoneyList == null ? null : accountMoneyList.trim();
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getDiscountLastMoney() {
        return discountLastMoney;
    }

    public void setDiscountLastMoney(BigDecimal discountLastMoney) {
        this.discountLastMoney = discountLastMoney;
    }

    public BigDecimal getOtherMoney() {
        return otherMoney;
    }

    public void setOtherMoney(BigDecimal otherMoney) {
        this.otherMoney = otherMoney;
    }

    public String getOtherMoneyList() {
        return otherMoneyList;
    }

    public void setOtherMoneyList(String otherMoneyList) {
        this.otherMoneyList = otherMoneyList == null ? null : otherMoneyList.trim();
    }

    public String getOtherMoneyItem() {
        return otherMoneyItem;
    }

    public void setOtherMoneyItem(String otherMoneyItem) {
        this.otherMoneyItem = otherMoneyItem == null ? null : otherMoneyItem.trim();
    }

    public Integer getAccountDay() {
        return accountDay;
    }

    public void setAccountDay(Integer accountDay) {
        this.accountDay = accountDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getLinkNumber() {
        return linkNumber;
    }

    public void setLinkNumber(String linkNumber) {
        this.linkNumber = linkNumber == null ? null : linkNumber.trim();
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