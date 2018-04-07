package com.jsh.model.vo.materials;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MaterialCategoryModel implements Serializable {
    private MaterialCategoryShowModel showModel = new MaterialCategoryShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String Name = "";

    /**
     * 等级
     */
    private Short CategoryLevel;

    /**
     * ParentId
     */
    private Long ParentId;

    /**
     * 分类ID
     */
    private Long materialCategoryID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String materialCategoryIDs = "";

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

    public MaterialCategoryShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(MaterialCategoryShowModel showModel) {
        this.showModel = showModel;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Short getCategoryLevel() {
        return CategoryLevel;
    }

    public void setCategoryLevel(Short categoryLevel) {
        CategoryLevel = categoryLevel;
    }

    public Long getParentId() {
        return ParentId;
    }

    public void setParentId(Long parentId) {
        ParentId = parentId;
    }

    public Long getMaterialCategoryID() {
        return materialCategoryID;
    }

    public void setMaterialCategoryID(Long materialCategoryID) {
        this.materialCategoryID = materialCategoryID;
    }

    public String getMaterialCategoryIDs() {
        return materialCategoryIDs;
    }

    public void setMaterialCategoryIDs(String materialCategoryIDs) {
        this.materialCategoryIDs = materialCategoryIDs;
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

}
