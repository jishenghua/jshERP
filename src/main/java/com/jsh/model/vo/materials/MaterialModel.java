package com.jsh.model.vo.materials;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class MaterialModel implements Serializable {
    private MaterialShowModel showModel = new MaterialShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String Name = "";

    private String Mfrs = ""; //制造商

    private Double Packing; //包装（KG/包）

    private Double SafetyStock; //安全存量（KG）
    /**
     * 型号
     */
    private String Model = "";

    /**
     * 规格
     */
    private String Standard = "";

    /**
     * 颜色
     */
    private String Color = "";

    /**
     * 单位
     */
    private String Unit = "";

    /**
     * 零售价
     */
    private Double RetailPrice;

    /**
     * 最低售价
     */
    private Double LowPrice;

    /**
     * 预设售价一
     */
    private Double PresetPriceOne;

    /**
     * 预设售价二
     */
    private Double PresetPriceTwo;

    /**
     * 备注
     */
    private String Remark = "";

    private Long UnitId;
    private String FirstOutUnit;
    private String FirstInUnit;
    private String PriceStrategy;

    /**
     * 导入excel文件
     */
    private File materialFile;

    private Boolean Enabled = true; //是否启用

    private String OtherField1;

    private String OtherField2;

    private String OtherField3;

    /**
     * CategoryId
     */
    private Long CategoryId;

    /**
     * CategoryIds 用于in子查询
     */
    private String CategoryIds = "1";

    /**
     * 分类ID
     */
    private Long materialID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String materialIDs = "";

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

    private String mpList = ""; //商品属性

    public MaterialShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(MaterialShowModel showModel) {
        this.showModel = showModel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMfrs() {
        return Mfrs;
    }

    public void setMfrs(String mfrs) {
        Mfrs = mfrs;
    }

    public Double getPacking() {
        return Packing;
    }

    public void setPacking(Double packing) {
        Packing = packing;
    }

    public Double getSafetyStock() {
        return SafetyStock;
    }

    public void setSafetyStock(Double safetyStock) {
        SafetyStock = safetyStock;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String standard) {
        Standard = standard;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public Double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        RetailPrice = retailPrice;
    }

    public Double getLowPrice() {
        return LowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        LowPrice = lowPrice;
    }

    public Double getPresetPriceOne() {
        return PresetPriceOne;
    }

    public void setPresetPriceOne(Double presetPriceOne) {
        PresetPriceOne = presetPriceOne;
    }

    public Double getPresetPriceTwo() {
        return PresetPriceTwo;
    }

    public void setPresetPriceTwo(Double presetPriceTwo) {
        PresetPriceTwo = presetPriceTwo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public Long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Long categoryId) {
        CategoryId = categoryId;
    }

    public Long getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Long materialID) {
        this.materialID = materialID;
    }

    public String getMaterialIDs() {
        return materialIDs;
    }

    public void setMaterialIDs(String materialIDs) {
        this.materialIDs = materialIDs;
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

    public String getCategoryIds() {
        return CategoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        CategoryIds = categoryIds;
    }

    public Long getUnitId() {
        return UnitId;
    }

    public void setUnitId(Long unitId) {
        UnitId = unitId;
    }

    public String getFirstOutUnit() {
        return FirstOutUnit;
    }

    public void setFirstOutUnit(String firstOutUnit) {
        FirstOutUnit = firstOutUnit;
    }

    public String getFirstInUnit() {
        return FirstInUnit;
    }

    public void setFirstInUnit(String firstInUnit) {
        FirstInUnit = firstInUnit;
    }

    public String getPriceStrategy() {
        return PriceStrategy;
    }

    public void setPriceStrategy(String priceStrategy) {
        PriceStrategy = priceStrategy;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
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

    public File getMaterialFile() {
        return materialFile;
    }

    public void setMaterialFile(File materialFile) {
        this.materialFile = materialFile;
    }

    public String getMpList() {
        return mpList;
    }

    public void setMpList(String mpList) {
        this.mpList = mpList;
    }
}
