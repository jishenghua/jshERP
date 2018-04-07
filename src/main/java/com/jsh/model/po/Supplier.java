package com.jsh.model.po;

import java.util.Map;

@SuppressWarnings("serial")
public class Supplier implements java.io.Serializable {
    private Long id = 0l;
    private String supplier = "";
    private String type = "";
    private String contacts = "";
    private String phonenum = "";
    private String fax = "";
    private String telephone = "";
    private String email = "";
    private String address = "";
    private Double advanceIn = 0d;
    private String taxNum = "";
    private String bankName = "";
    private String accountNumber = "";
    private Double taxRate = 0d;
    private Double BeginNeedGet = 0d;
    private Double BeginNeedPay = 0d;
    private Double AllNeedGet = 0d;
    private Double AllNeedPay = 0d;
    private Short isystem = 1;
    private String description = "";
    private Boolean enabled = true;

    //----------以下属性导入exel表格使用--------------------
    /**
     * 类型 right--正确 warn--警告  wrong--错误
     */
    private Map<Integer, String> cellInfo;

    /**
     * 行号
     */
    private Integer rowLineNum;

    private String advanceInStr;

    private String beginNeedGetStr;

    private String beginNeedPayStr;

    private String taxRateStr;

    private String enabledStr;


    public Supplier() {

    }

    public Supplier(Long id) {
        this.id = id;
    }

    public Supplier(String supplier, String type, String contacts, String phonenum,
                    String fax, String telephone, String email, String address, Short isystem, String description,
                    Boolean enabled, Double advanceIn, String taxNum, String bankName, String accountNumber, Double taxRate,
                    Double beginNeedGet, Double beginNeedPay, Double allNeedGet, Double allNeedPay) {
        super();
        this.supplier = supplier;
        this.type = type;
        this.contacts = contacts;
        this.phonenum = phonenum;
        this.fax = fax;
        this.telephone = telephone;
        this.address = address;
        this.email = email;
        this.BeginNeedGet = beginNeedGet;
        this.BeginNeedPay = beginNeedPay;
        this.AllNeedGet = allNeedGet;
        this.AllNeedPay = allNeedPay;
        this.isystem = isystem;
        this.description = description;
        this.enabled = enabled;
        this.advanceIn = advanceIn;
        this.taxNum = taxNum;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.taxRate = taxRate;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBeginNeedGet() {
        return BeginNeedGet;
    }

    public void setBeginNeedGet(Double beginNeedGet) {
        BeginNeedGet = beginNeedGet;
    }

    public Double getBeginNeedPay() {
        return BeginNeedPay;
    }

    public void setBeginNeedPay(Double beginNeedPay) {
        BeginNeedPay = beginNeedPay;
    }

    public Double getAllNeedGet() {
        return AllNeedGet;
    }

    public void setAllNeedGet(Double allNeedGet) {
        AllNeedGet = allNeedGet;
    }

    public Double getAllNeedPay() {
        return AllNeedPay;
    }

    public void setAllNeedPay(Double allNeedPay) {
        AllNeedPay = allNeedPay;
    }

    public Short getIsystem() {
        return isystem;
    }

    public void setIsystem(Short isystem) {
        this.isystem = isystem;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Double getAdvanceIn() {
        return advanceIn;
    }

    public void setAdvanceIn(Double advanceIn) {
        this.advanceIn = advanceIn;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public Map<Integer, String> getCellInfo() {
        return cellInfo;
    }

    public void setCellInfo(Map<Integer, String> cellInfo) {
        this.cellInfo = cellInfo;
    }

    public Integer getRowLineNum() {
        return rowLineNum;
    }

    public void setRowLineNum(Integer rowLineNum) {
        this.rowLineNum = rowLineNum;
    }

    public String getAdvanceInStr() {
        return advanceInStr;
    }

    public void setAdvanceInStr(String advanceInStr) {
        this.advanceInStr = advanceInStr;
    }

    public String getBeginNeedGetStr() {
        return beginNeedGetStr;
    }

    public void setBeginNeedGetStr(String beginNeedGetStr) {
        this.beginNeedGetStr = beginNeedGetStr;
    }

    public String getBeginNeedPayStr() {
        return beginNeedPayStr;
    }

    public void setBeginNeedPayStr(String beginNeedPayStr) {
        this.beginNeedPayStr = beginNeedPayStr;
    }

    public String getTaxRateStr() {
        return taxRateStr;
    }

    public void setTaxRateStr(String taxRateStr) {
        this.taxRateStr = taxRateStr;
    }

    public String getEnabledStr() {
        return enabledStr;
    }

    public void setEnabledStr(String enabledStr) {
        this.enabledStr = enabledStr;
    }
}
