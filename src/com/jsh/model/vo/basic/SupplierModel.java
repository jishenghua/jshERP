package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SupplierModel implements Serializable
{
	private SupplierShowModel showModel = new SupplierShowModel();
	
	/**======开始接受页面参数=================**/
	/**
	 * 供应商名称
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
    
    public void setBeginNeedGet(Double beginNeedGet)
    {
        BeginNeedGet = beginNeedGet;
    }

    public Double getBeginNeedGet()
    {
        return BeginNeedGet;
    }

    public void setBeginNeedPay(Double beginNeedPay)
    {
        BeginNeedPay = beginNeedPay;
    }

    public Double getBeginNeedPay()
    {
        return BeginNeedPay;
    }

    public void setAllNeedGet(Double allNeedGet)
    {
        AllNeedGet = allNeedGet;
    }

    public Double getAllNeedGet()
    {
        return AllNeedGet;
    }

    public void setAllNeedPay(Double allNeedPay)
    {
        AllNeedPay = allNeedPay;
    }

    public Double getAllNeedPay()
    {
        return AllNeedPay;
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



}
