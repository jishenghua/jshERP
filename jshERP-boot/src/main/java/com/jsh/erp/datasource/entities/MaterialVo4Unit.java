package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class MaterialVo4Unit extends Material{

    private String unitName;

    private BigDecimal ratio;

    private String categoryName;

    private String materialOther;

    private BigDecimal stock;

    private BigDecimal purchaseDecimal;

    private BigDecimal commodityDecimal;

    private BigDecimal wholesaleDecimal;

    private BigDecimal lowDecimal;

    private BigDecimal billPrice;

    private String mBarCode;

    private String commodityUnit;

    private Long meId;

    private BigDecimal initialStock;

    private BigDecimal currentUnitPrice;

    private BigDecimal currentStock;

    private BigDecimal currentStockPrice;

    private BigDecimal currentStockMovePrice;

    private BigDecimal currentWeight;

    private String sku;

    private Long depotId;

    /**
     * 换算为大单位的库存
     */
    private String bigUnitStock;

    private String imgSmall;

    private String imgLarge;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMaterialOther() {
        return materialOther;
    }

    public void setMaterialOther(String materialOther) {
        this.materialOther = materialOther;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getPurchaseDecimal() {
        return purchaseDecimal;
    }

    public void setPurchaseDecimal(BigDecimal purchaseDecimal) {
        this.purchaseDecimal = purchaseDecimal;
    }

    public BigDecimal getCommodityDecimal() {
        return commodityDecimal;
    }

    public void setCommodityDecimal(BigDecimal commodityDecimal) {
        this.commodityDecimal = commodityDecimal;
    }

    public BigDecimal getWholesaleDecimal() {
        return wholesaleDecimal;
    }

    public void setWholesaleDecimal(BigDecimal wholesaleDecimal) {
        this.wholesaleDecimal = wholesaleDecimal;
    }

    public BigDecimal getLowDecimal() {
        return lowDecimal;
    }

    public void setLowDecimal(BigDecimal lowDecimal) {
        this.lowDecimal = lowDecimal;
    }

    public BigDecimal getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(BigDecimal billPrice) {
        this.billPrice = billPrice;
    }

    public String getmBarCode() {
        return mBarCode;
    }

    public void setmBarCode(String mBarCode) {
        this.mBarCode = mBarCode;
    }

    public String getCommodityUnit() {
        return commodityUnit;
    }

    public void setCommodityUnit(String commodityUnit) {
        this.commodityUnit = commodityUnit;
    }

    public Long getMeId() {
        return meId;
    }

    public void setMeId(Long meId) {
        this.meId = meId;
    }

    public BigDecimal getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(BigDecimal initialStock) {
        this.initialStock = initialStock;
    }

    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    public BigDecimal getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(BigDecimal currentStock) {
        this.currentStock = currentStock;
    }

    public BigDecimal getCurrentStockPrice() {
        return currentStockPrice;
    }

    public void setCurrentStockPrice(BigDecimal currentStockPrice) {
        this.currentStockPrice = currentStockPrice;
    }

    public BigDecimal getCurrentStockMovePrice() {
        return currentStockMovePrice;
    }

    public void setCurrentStockMovePrice(BigDecimal currentStockMovePrice) {
        this.currentStockMovePrice = currentStockMovePrice;
    }

    public BigDecimal getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(BigDecimal currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getDepotId() {
        return depotId;
    }

    public void setDepotId(Long depotId) {
        this.depotId = depotId;
    }

    public String getBigUnitStock() {
        return bigUnitStock;
    }

    public void setBigUnitStock(String bigUnitStock) {
        this.bigUnitStock = bigUnitStock;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getImgLarge() {
        return imgLarge;
    }

    public void setImgLarge(String imgLarge) {
        this.imgLarge = imgLarge;
    }
}