package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class AccountHead implements java.io.Serializable {
    private Long Id;
    private String Type;
    private Supplier OrganId;
    private Person HandsPersonId;
    private Double ChangeAmount;
    private Double TotalPrice;
    private Account AccountId;
    private String BillNo;
    private Timestamp BillTime;
    private String Remark;

    public AccountHead() {

    }

    public AccountHead(Long Id) {
        this.Id = Id;
    }

    public AccountHead(String type, Supplier organId,
                       Person handsPersonId, Double changeAmount, Double totalPrice,
                       Account accountId, String billNo, Timestamp billTime, String remark) {
        super();
        Type = type;
        OrganId = organId;
        HandsPersonId = handsPersonId;
        ChangeAmount = changeAmount;
        TotalPrice = totalPrice;
        AccountId = accountId;
        BillNo = billNo;
        BillTime = billTime;
        Remark = remark;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Supplier getOrganId() {
        return OrganId;
    }

    public void setOrganId(Supplier organId) {
        OrganId = organId;
    }

    public Person getHandsPersonId() {
        return HandsPersonId;
    }

    public void setHandsPersonId(Person handsPersonId) {
        HandsPersonId = handsPersonId;
    }

    public Double getChangeAmount() {
        return ChangeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        ChangeAmount = changeAmount;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public Account getAccountId() {
        return AccountId;
    }

    public void setAccountId(Account accountId) {
        AccountId = accountId;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public Timestamp getBillTime() {
        return BillTime;
    }

    public void setBillTime(Timestamp billTime) {
        BillTime = billTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
