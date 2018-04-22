package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AssetNameModel implements Serializable {
    private AssetNameShowModel showModel = new AssetNameShowModel();
    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String assetName = "";

    /**
     * 是否易耗品
     */
    private Short consumable;

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 分类ID
     */
    private Long categoryID;

    /**
     * ID
     */
    private Long assetNameID = 0l;

    /**
     * IDs 批量操作使用
     */
    private String assetNameIDs = "";

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

    public AssetNameShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(AssetNameShowModel showModel) {
        this.showModel = showModel;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAssetNameID() {
        return assetNameID;
    }

    public void setAssetNameID(Long assetNameID) {
        this.assetNameID = assetNameID;
    }

    public String getAssetNameIDs() {
        return assetNameIDs;
    }

    public void setAssetNameIDs(String assetNameIDs) {
        this.assetNameIDs = assetNameIDs;
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

    public Short getConsumable() {
        return consumable;
    }

    public void setConsumable(Short consumable) {
        this.consumable = consumable;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }
}
