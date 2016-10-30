package com.jsh.model.vo.materials;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MaterialModel implements Serializable
{
    private MaterialShowModel showModel = new MaterialShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String Name = "";
    
    /**
     * 型号
     */
    private String Model = "";
    
    /**
     * 颜色
     */
    private String Color = "";
    
    /**
     * 单位
     */
    private String Unit = "";
    
    /**
     * 备注
     */
    private String Remark = "";
    
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

	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
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

}
