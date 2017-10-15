package com.jsh.model.vo.materials;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MaterialPropertyModel implements Serializable
{
    private MaterialCategoryShowModel showModel = new MaterialCategoryShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String nativeName;
    
    /**
     * 是否启用
     */
	private Boolean enabled  = true;

    /**
     * 排序
     */
    private String sort;

	/**
	 * 别名
	 */
	private String anotherName;

	/**
	 * Id编号
	 */
	private Long id;
    
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

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAnotherName() {
		return anotherName;
	}

	public void setAnotherName(String anotherName) {
		this.anotherName = anotherName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
