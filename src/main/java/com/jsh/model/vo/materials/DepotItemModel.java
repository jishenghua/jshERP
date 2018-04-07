package com.jsh.model.vo.materials;

import java.io.InputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class DepotItemModel implements Serializable {
    private DepotItemShowModel showModel = new DepotItemShowModel();

    /**
     * ======开始接受页面参数=================
     **/
    private Long HeaderId;
    private Long MaterialId;
    private String MUnit; //计量单位
    private Double OperNumber;
    private Double BasicNumber;
    private Double UnitPrice;
    private Double TaxUnitPrice; //含税单价
    private Double AllPrice;
    private String Remark = "";
    private String Img = "";

    private Long DepotId;
    private Long AnotherDepotId;
    private Double TaxRate;
    private Double TaxMoney;
    private Double TaxLastMoney;
    private String OtherField1;
    private String OtherField2;
    private String OtherField3;
    private String OtherField4;
    private String OtherField5;
    private String MType;

    private String Inserted = "";    //json插入记录
    private String Deleted = "";    //json删除记录
    private String Updated = "";    //json修改记录

    private String HeadIds = "";    //表头集合列表
    private String MaterialIds = "";    //材料列表
    private String MonthTime = "";  //月份
    private Integer ProjectId = null;
    private String browserType = "";
    /**
     * 文件名称
     */
    private String fileName = "";
    /**
     * 分类ID
     */
    private Long depotItemID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String depotItemIDs = "";

    /**
     * 每页显示的个数
     */
    private int pageSize = 800;

    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";


    /**
     * 输入流，导出excel文件
     */
    private InputStream excelStream;

    private String mpList = ""; //商品属性

    public DepotItemShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(DepotItemShowModel showModel) {
        this.showModel = showModel;
    }

    public Long getHeaderId() {
        return HeaderId;
    }

    public void setHeaderId(Long headerId) {
        HeaderId = headerId;
    }

    public Long getMaterialId() {
        return MaterialId;
    }

    public void setMaterialId(Long materialId) {
        MaterialId = materialId;
    }

    public String getMUnit() {
        return MUnit;
    }

    public void setMUnit(String MUnit) {
        this.MUnit = MUnit;
    }

    public Double getTaxUnitPrice() {
        return TaxUnitPrice;
    }

    public void setTaxUnitPrice(Double taxUnitPrice) {
        TaxUnitPrice = taxUnitPrice;
    }

    public Double getOperNumber() {
        return OperNumber;
    }

    public void setOperNumber(Double operNumber) {
        OperNumber = operNumber;
    }

    public Double getBasicNumber() {
        return BasicNumber;
    }

    public void setBasicNumber(Double basicNumber) {
        BasicNumber = basicNumber;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public Double getAllPrice() {
        return AllPrice;
    }

    public void setAllPrice(Double allPrice) {
        AllPrice = allPrice;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public Long getDepotItemID() {
        return depotItemID;
    }

    public void setDepotItemID(Long depotItemID) {
        this.depotItemID = depotItemID;
    }

    public String getDepotItemIDs() {
        return depotItemIDs;
    }

    public void setDepotItemIDs(String depotItemIDs) {
        this.depotItemIDs = depotItemIDs;
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

    public String getInserted() {
        return Inserted;
    }

    public void setInserted(String inserted) {
        Inserted = inserted;
    }

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String deleted) {
        Deleted = deleted;
    }

    public String getUpdated() {
        return Updated;
    }

    public void setUpdated(String updated) {
        Updated = updated;
    }

    public String getHeadIds() {
        return HeadIds;
    }

    public void setHeadIds(String headIds) {
        HeadIds = headIds;
    }

    public String getMonthTime() {
        return MonthTime;
    }

    public void setMonthTime(String monthTime) {
        MonthTime = monthTime;
    }

    public Integer getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Integer projectId) {
        ProjectId = projectId;
    }

    public String getMaterialIds() {
        return MaterialIds;
    }

    public void setMaterialIds(String materialIds) {
        MaterialIds = materialIds;
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

    public Long getDepotId() {
        return DepotId;
    }

    public void setDepotId(Long depotId) {
        DepotId = depotId;
    }

    public Long getAnotherDepotId() {
        return AnotherDepotId;
    }

    public void setAnotherDepotId(Long anotherDepotId) {
        AnotherDepotId = anotherDepotId;
    }

    public Double getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(Double taxRate) {
        TaxRate = taxRate;
    }

    public Double getTaxMoney() {
        return TaxMoney;
    }

    public void setTaxMoney(Double taxMoney) {
        TaxMoney = taxMoney;
    }

    public Double getTaxLastMoney() {
        return TaxLastMoney;
    }

    public void setTaxLastMoney(Double taxLastMoney) {
        TaxLastMoney = taxLastMoney;
    }

    public String getOtherField1() {
        return OtherField1;
    }

    public void setOtherField1(String otherField1) {
        OtherField1 = otherField1;
    }

    public String getOtherField2() {
        return OtherField2;
    }

    public void setOtherField2(String otherField2) {
        OtherField2 = otherField2;
    }

    public String getOtherField3() {
        return OtherField3;
    }

    public void setOtherField3(String otherField3) {
        OtherField3 = otherField3;
    }

    public String getOtherField4() {
        return OtherField4;
    }

    public void setOtherField4(String otherField4) {
        OtherField4 = otherField4;
    }

    public String getOtherField5() {
        return OtherField5;
    }

    public void setOtherField5(String otherField5) {
        OtherField5 = otherField5;
    }

    public String getMType() {
        return MType;
    }

    public void setMType(String MType) {
        this.MType = MType;
    }

    public String getMpList() {
        return mpList;
    }

    public void setMpList(String mpList) {
        this.mpList = mpList;
    }
}
