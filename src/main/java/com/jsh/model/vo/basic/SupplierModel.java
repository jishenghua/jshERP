package com.jsh.model.vo.basic;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class SupplierModel implements Serializable {
    private SupplierShowModel showModel = new SupplierShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String supplier = "";

    /**
     * 类型
     */
    private String type = "";

    /**
     * 联系人
     */
    private String contacts = "";

    /**
     * 联系电话
     */
    private String phonenum = "";

    /**
     * 电子邮箱
     */
    private String email = "";

    /**
     * 预付款
     */
    private Double AdvanceIn;

    /**
     * 期初应收
     */
    private Double BeginNeedGet;

    /**
     * 期初应付
     */
    private Double BeginNeedPay;

    /**
     * 累计应收
     */
    private Double AllNeedGet;

    /**
     * 累计应付
     */
    private Double AllNeedPay;

    /**
     * 描述信息
     */
    private String description = "";

    private String fax = "";
    private String telephone = "";
    private String address = "";
    private String taxNum = "";
    private String bankName = "";
    private String accountNumber = "";
    private Double taxRate;

    private String UBType = ""; //UBType，UserBusiness类型

    private String UBKeyId = ""; //UBKeyId，UserBusiness关键id

    /**
     * 导入excel文件
     */
    private File supplierFile;

    /**
     * 启用
     */
    private Boolean enabled = false;

    /**
     * 供应商ID
     */
    private Long supplierID = 0l;

    /**
     * 供应商IDs 批量操作使用
     */
    private String supplierIDs = "";

    /**
     * 每页显示的个数
     */
    private int pageSize = 10;

    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";
    private String browserType = ""; //浏览器类型
    private String fileName = ""; //文件名称
    private InputStream excelStream;  //输入流，导出excel文件

    public SupplierShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(SupplierShowModel showModel) {
        this.showModel = showModel;
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

    public Double getAdvanceIn() {
        return AdvanceIn;
    }

    public void setAdvanceIn(Double advanceIn) {
        AdvanceIn = advanceIn;
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

    public Long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Long supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierIDs() {
        return supplierIDs;
    }

    public void setSupplierIDs(String supplierIDs) {
        this.supplierIDs = supplierIDs;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    public String getUBType() {
        return UBType;
    }

    public void setUBType(String UBType) {
        this.UBType = UBType;
    }

    public String getUBKeyId() {
        return UBKeyId;
    }

    public void setUBKeyId(String UBKeyId) {
        this.UBKeyId = UBKeyId;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public File getSupplierFile() {
        return supplierFile;
    }

    public void setSupplierFile(File supplierFile) {
        this.supplierFile = supplierFile;
    }
}
