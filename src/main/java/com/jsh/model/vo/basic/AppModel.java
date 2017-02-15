package com.jsh.model.vo.basic;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class AppModel implements Serializable
{
    private AppShowModel showModel = new AppShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 代号
     */
    private String Number = "";

    /**
     * 名称
     */
    private String Name = "";
    
    /**
     * 类型
     */
    private String Type = "";
    
    /**
     * 图标
     */
    private String Icon = "";
    
    /**
     * 链接
     */
    private String URL = "";
    
    /**
     * 宽度
     */
    private String Width = "";
    
    /**
     * 高度
     */
    private String Height = "";
    
    /**
     * 拉伸
     */
    private Boolean ReSize = false;
    
    /**
     * 最大化
     */
    private Boolean OpenMax = false;
    
    /**
     * Flash
     */
    private Boolean Flash = false;
    
    /**
     * 种类
     */
    private String ZL = "";
    
    /**
     * 排序号
     */
    private String Sort = "";

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
    private Long appID = 0l;
    
    /**
     * 分类IDs 批量操作使用
     */
    private String appIDs = "";
    
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
    

	public AppShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(AppShowModel showModel) {
		this.showModel = showModel;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getWidth() {
		return Width;
	}

	public void setWidth(String width) {
		Width = width;
	}

	public String getHeight() {
		return Height;
	}

	public void setHeight(String height) {
		Height = height;
	}

	public Boolean getReSize() {
		return ReSize;
	}

	public void setReSize(Boolean reSize) {
		ReSize = reSize;
	}

	public Boolean getOpenMax() {
		return OpenMax;
	}

	public void setOpenMax(Boolean openMax) {
		OpenMax = openMax;
	}

	public Boolean getFlash() {
		return Flash;
	}

	public void setFlash(Boolean flash) {
		Flash = flash;
	}

	public String getZL() {
		return ZL;
	}

	public void setZL(String zL) {
		ZL = zL;
	}

	public String getSort() {
		return Sort;
	}

	public void setSort(String sort) {
		Sort = sort;
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

	public Long getAppID() {
		return appID;
	}

	public void setAppID(Long appID) {
		this.appID = appID;
	}

	public String getAppIDs() {
		return appIDs;
	}

	public void setAppIDs(String appIDs) {
		this.appIDs = appIDs;
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
