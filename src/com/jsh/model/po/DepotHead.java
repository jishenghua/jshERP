package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DepotHead implements java.io.Serializable
{
	private Long Id;
	private String Type;
	private String SubType;
	private Depot ProjectId;
	private String Number;
	private String OperPersonName;	
	private Timestamp CreateTime;	
	private Timestamp OperTime;	
	private Supplier OrganId;
	private Person HandsPersonId;
	private Account AccountId;
	private Double ChangeAmount;
	private Depot AllocationProjectId;	
	private Double TotalPrice;
	private String Remark;	

	public DepotHead()
	{
		
	}

	public DepotHead(Long Id)
	{
		this.Id = Id ;
	}

	public DepotHead(String type, String subType, Depot projectId,
			String number, String operPersonName, Timestamp createTime,
			Timestamp operTime, Supplier organId, Person handsPersonId,
			Account accountId, Double changeAmount, Depot allocationProjectId, Double totalPrice, String remark) {
		super();
		Type = type;
		SubType = subType;
		ProjectId = projectId;
		Number = number;
		OperPersonName = operPersonName;
		CreateTime = createTime;
		OperTime = operTime;
		OrganId = organId;
		HandsPersonId = handsPersonId;
		AccountId = accountId;
		ChangeAmount = changeAmount;
		AllocationProjectId = allocationProjectId;
		TotalPrice = totalPrice;
		Remark = remark;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public Depot getProjectId() {
		return ProjectId;
	}

	public void setProjectId(Depot projectId) {
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

	public Timestamp getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Timestamp createTime) {
		CreateTime = createTime;
	}

	public Timestamp getOperTime() {
		return OperTime;
	}

	public void setOperTime(Timestamp operTime) {
		OperTime = operTime;
	}

	public Supplier getOrganId() {
		return OrganId;
	}

	public void setOrganId(Supplier organId) {
		OrganId = organId;
	}

	public Person getHandsPersonId() {
		return HandsPersonId;
	}

	public void setHandsPersonId(Person handsPersonId) {
		HandsPersonId = handsPersonId;
	}
	
	public Account getAccountId() {
		return AccountId;
	}

	public void setAccountId(Account accountId) {
		AccountId = accountId;
	}

	public Double getChangeAmount() {
		return ChangeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		ChangeAmount = changeAmount;
	}

	public Depot getAllocationProjectId() {
		return AllocationProjectId;
	}

	public void setAllocationProjectId(Depot allocationProjectId) {
		AllocationProjectId = allocationProjectId;
	}

	public Double getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		TotalPrice = totalPrice;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}
}