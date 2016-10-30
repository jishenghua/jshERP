package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VisitAccountModel implements Serializable
{
    private VisitAccountShowModel showModel = new VisitAccountShowModel();

    /**======开始接受页面参数=================**/
    /**
     * ProjectId
     */
    private Long ProjectId;
    /**
     * 楼号
     */
    private String LouHao = "";
    /**
     * 户号
     */
    private String HuHao = "";
    /**
     * 回访情况
     */
    private String HuiFang = "";
    /**
     * 落实情况
     */
    private String LuoShi = "";
    /**
     * 住户姓名
     */
    private String Name = "";
    /**
     * 电话
     */
    private String Tel = "";
    /**
     * 时间
     */
    private String AddTime = "";
    
    /**
     * 分类ID
     */
    private Long visitAccountID = 0l;
    
    /**
     * 分类IDs 批量操作使用
     */
    private String visitAccountIDs = "";
    
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


	public VisitAccountShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(VisitAccountShowModel showModel) {
		this.showModel = showModel;
	}

	public Long getProjectId() {
		return ProjectId;
	}

	public void setProjectId(Long projectId) {
		ProjectId = projectId;
	}

	public String getLouHao() {
		return LouHao;
	}

	public void setLouHao(String louHao) {
		LouHao = louHao;
	}

	public String getHuHao() {
		return HuHao;
	}

	public void setHuHao(String huHao) {
		HuHao = huHao;
	}

	public String getHuiFang() {
		return HuiFang;
	}

	public void setHuiFang(String huiFang) {
		HuiFang = huiFang;
	}

	public String getLuoShi() {
		return LuoShi;
	}

	public void setLuoShi(String luoShi) {
		LuoShi = luoShi;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getAddTime() {
		return AddTime;
	}

	public void setAddTime(String addTime) {
		AddTime = addTime;
	}

	public Long getVisitAccountID() {
		return visitAccountID;
	}

	public void setVisitAccountID(Long visitAccountID) {
		this.visitAccountID = visitAccountID;
	}

	public String getVisitAccountIDs() {
		return visitAccountIDs;
	}

	public void setVisitAccountIDs(String visitAccountIDs) {
		this.visitAccountIDs = visitAccountIDs;
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
