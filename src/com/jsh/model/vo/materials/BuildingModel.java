package com.jsh.model.vo.materials;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BuildingModel implements Serializable
{
    private BuildingShowModel showModel = new BuildingShowModel();

    /**======开始接受页面参数=================**/
    /**
     * ProjectId
     */
    private Long ProjectId;
    /**
     * 名称
     */
    private String Name = "";
    /**
     * 备注
     */
    private String Remark = "";
    /**
     * 启用
     */
    private Boolean Enabled = false;
    
    /**
     * 分类ID
     */
    private Long buildingID = 0l;
    
    /**
     * 分类IDs 批量操作使用
     */
    private String buildingIDs = "";
    
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

	public BuildingShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(BuildingShowModel showModel) {
		this.showModel = showModel;
	}

	public Long getProjectId() {
		return ProjectId;
	}

	public void setProjectId(Long projectId) {
		ProjectId = projectId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public Boolean getEnabled() {
		return Enabled;
	}

	public void setEnabled(Boolean enabled) {
		Enabled = enabled;
	}

	public Long getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(Long buildingID) {
		this.buildingID = buildingID;
	}

	public String getBuildingIDs() {
		return buildingIDs;
	}

	public void setBuildingIDs(String buildingIDs) {
		this.buildingIDs = buildingIDs;
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
