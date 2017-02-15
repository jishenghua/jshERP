package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DepotModel implements Serializable
{
    private DepotShowModel showModel = new DepotShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 仓库名称
     */
    private String name = "";

    /**
     * 排序
     */
    private String sort = "";
    
    /**
     * 描述
     */
    private String remark = "";
    
    /**
     * 分类ID
     */
    private Long depotID = 0l;
    
    /**
     * 分类IDs 批量操作使用
     */
    private String depotIDs = "";
    
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
    
    /**
     * UBType，UserBusiness类型
     */
    private String UBType = "";
    
    /**
     * UBKeyId，UserBusiness关键id
     */
    private String UBKeyId = "";

	public DepotShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(DepotShowModel showModel) {
		this.showModel = showModel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getDepotID() {
		return depotID;
	}

	public void setDepotID(Long depotID) {
		this.depotID = depotID;
	}

	public String getDepotIDs() {
		return depotIDs;
	}

	public void setDepotIDs(String depotIDs) {
		this.depotIDs = depotIDs;
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

	public String getUBType() {
		return UBType;
	}

	public void setUBType(String uBType) {
		UBType = uBType;
	}

	public String getUBKeyId() {
		return UBKeyId;
	}

	public void setUBKeyId(String uBKeyId) {
		UBKeyId = uBKeyId;
	}


}
