package com.jsh.model.vo.materials;

import java.io.Serializable;
import com.sun.jmx.snmp.Timestamp;

@SuppressWarnings("serial")
public class DepotHeadModel implements Serializable
{
    private DepotHeadShowModel showModel = new DepotHeadShowModel();

    /**======开始接受页面参数=================**/
	private String Type = "";
	private String SubType = "";
	private Long ProjectId;
	private String Number = "";
	private String OperPersonName = "";	
	private String OperTime;	
	private Long OrganId;
	private Long HandsPersonId;
	private Long WareHousePersonId;
	private String SettlementWay = "";	
	private Long BuildingId;
	private Long AllocationProjectId;	
	private String Remark = "";	
	private String State = "";	
	private String ReAuditPersonName = "";	
	private String Reason = "";	
	
	private String BeginTime; //查询开始时间
	private String EndTime;  //查询结束时间
	private String MonthTime;  //查询月份
    /**
     * 分类ID
     */
    private Long depotHeadID = 0l;
    
    /**
     * 分类IDs 批量操作使用
     */
    private String depotHeadIDs = "";
    
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

	public DepotHeadShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(DepotHeadShowModel showModel) {
		this.showModel = showModel;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getSubType() {
		return SubType;
	}

	public void setSubType(String subType) {
		SubType = subType;
	}

	public Long getProjectId() {
		return ProjectId;
	}

	public void setProjectId(Long projectId) {
		ProjectId = projectId;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getOperPersonName() {
		return OperPersonName;
	}

	public void setOperPersonName(String operPersonName) {
		OperPersonName = operPersonName;
	}

	public Long getOrganId() {
		return OrganId;
	}

	public void setOrganId(Long organId) {
		OrganId = organId;
	}

	public Long getHandsPersonId() {
		return HandsPersonId;
	}

	public void setHandsPersonId(Long handsPersonId) {
		HandsPersonId = handsPersonId;
	}

	public Long getWareHousePersonId() {
		return WareHousePersonId;
	}

	public void setWareHousePersonId(Long wareHousePersonId) {
		WareHousePersonId = wareHousePersonId;
	}

	public String getSettlementWay() {
		return SettlementWay;
	}

	public void setSettlementWay(String settlementWay) {
		SettlementWay = settlementWay;
	}

	public Long getBuildingId() {
		return BuildingId;
	}

	public void setBuildingId(Long buildingId) {
		BuildingId = buildingId;
	}

	public Long getAllocationProjectId() {
		return AllocationProjectId;
	}

	public void setAllocationProjectId(Long allocationProjectId) {
		AllocationProjectId = allocationProjectId;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getReAuditPersonName() {
		return ReAuditPersonName;
	}

	public void setReAuditPersonName(String reAuditPersonName) {
		ReAuditPersonName = reAuditPersonName;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public Long getDepotHeadID() {
		return depotHeadID;
	}

	public void setDepotHeadID(Long depotHeadID) {
		this.depotHeadID = depotHeadID;
	}

	public String getDepotHeadIDs() {
		return depotHeadIDs;
	}

	public void setDepotHeadIDs(String depotHeadIDs) {
		this.depotHeadIDs = depotHeadIDs;
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

	public String getBeginTime() {
		return BeginTime;
	}

	public void setBeginTime(String beginTime) {
		BeginTime = beginTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public String getOperTime() {
		return OperTime;
	}

	public void setOperTime(String operTime) {
		OperTime = operTime;
	}

	public String getMonthTime() {
		return MonthTime;
	}

	public void setMonthTime(String monthTime) {
		MonthTime = monthTime;
	}
	
}
