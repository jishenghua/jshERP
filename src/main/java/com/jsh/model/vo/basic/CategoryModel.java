package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CategoryModel implements Serializable {
    private CategoryShowModel showModel = new CategoryShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 分类名称
     */
    private String categoryName = "";

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 分类ID
     */
    private Long categoryID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String categoryIDs = "";

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

    public CategoryShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(CategoryShowModel showModel) {
        this.showModel = showModel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryIDs() {
        return categoryIDs;
    }

    public void setCategoryIDs(String categoryIDs) {
        this.categoryIDs = categoryIDs;
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
