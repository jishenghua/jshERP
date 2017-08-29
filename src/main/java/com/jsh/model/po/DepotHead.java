package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DepotHead implements java.io.Serializable
{
	private Long Id;
	private String Type;
	private String SubType;
	private Depot ProjectId;
	private String DefaultNumber;
	private String Number;
	private String OperPersonName;	
	private Timestamp CreateTime;	
	private Timestamp OperTime;	
	private Supplier OrganId;
	private Person HandsPersonId;
	private String Salesman;  //业务员（可以多个）[2][3]
	private Account AccountId;
	private Double ChangeAmount;
	private String AccountIdList; //多账户ID列表 [2][3]
	private String AccountMoneyList; //多账户金额列表 [{"[2]",22},{"[3]",33}]
	private Double Discount; //优惠率  0.10
	private Double DiscountMoney; //优惠金额 10
	private Double DiscountLastMoney; //优惠后金额  90
	private Double OtherMoney; //销售或采购费用 100
	private String OtherMoneyList; //销售或采购费用涉及项目Id数组（包括快递、招待等）[2][3]
	private String OtherMoneyItem; //销售费用涉及项目（包括快递、招待等） [{"[2]",22},{"[3]",33}]
	private Integer AccountDay; //结算天数
	private Depot AllocationProjectId;	
	private Double TotalPrice;
	private String PayType;
	private Boolean Status = false; //单据状态
	private String Remark;	

	public DepotHead()
	{
		
	}

	public DepotHead(Long Id)
	{
		this.Id = Id ;
	}

	public DepotHead(String type, String subType, Depot projectId, String defaultNumber, String number, String operPersonName, Timestamp createTime,
			Timestamp operTime, Supplier organId, Person handsPersonId, String salesman, String accountIdList,String accountMoneyList,
			Double discount, Double discountMoney,Double discountLastMoney, Double otherMoney, String otherMoneyItem,Integer accountDay,
			Account accountId, Double changeAmount, Depot allocationProjectId, Double totalPrice,String payType, Boolean status, String remark) {
		super();
		Type = type;
		SubType = subType;
		ProjectId = projectId;
		DefaultNumber = defaultNumber;
		Number = number;
		OperPersonName = operPersonName;
		CreateTime = createTime;
		OperTime = operTime;
		OrganId = organId;
		HandsPersonId = handsPersonId;
		Salesman= salesman;
		AccountIdList= accountIdList;
		AccountMoneyList= accountMoneyList;
		Discount= discount;
		DiscountMoney = discountMoney;
		DiscountLastMoney = discountLastMoney;
		OtherMoney = otherMoney;
		OtherMoneyItem = otherMoneyItem;
		AccountDay = accountDay;
		AccountId = accountId;
		ChangeAmount = changeAmount;
		AllocationProjectId = allocationProjectId;
		TotalPrice = totalPrice;
		PayType = payType;
		Status = status;
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

	public String getDefaultNumber() {
		return DefaultNumber;
	}

	public void setDefaultNumber(String defaultNumber) {
		DefaultNumber = defaultNumber;
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

	public String getPayType() {
		return PayType;
	}

	public void setPayType(String payType) {
		PayType = payType;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getSalesman() {
		return Salesman;
	}

	public void setSalesman(String salesman) {
		Salesman = salesman;
	}

	public String getAccountIdList() {
		return AccountIdList;
	}

	public void setAccountIdList(String accountIdList) {
		AccountIdList = accountIdList;
	}

	public String getAccountMoneyList() {
		return AccountMoneyList;
	}

	public void setAccountMoneyList(String accountMoneyList) {
		AccountMoneyList = accountMoneyList;
	}

	public Double getDiscount() {
		return Discount;
	}

	public void setDiscount(Double discount) {
		Discount = discount;
	}

	public Double getDiscountMoney() {
		return DiscountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		DiscountMoney = discountMoney;
	}

	public Double getDiscountLastMoney() {
		return DiscountLastMoney;
	}

	public void setDiscountLastMoney(Double discountLastMoney) {
		DiscountLastMoney = discountLastMoney;
	}

	public Double getOtherMoney() {
		return OtherMoney;
	}

	public void setOtherMoney(Double otherMoney) {
		OtherMoney = otherMoney;
	}

	public String getOtherMoneyList() {
		return OtherMoneyList;
	}

	public void setOtherMoneyList(String otherMoneyList) {
		OtherMoneyList = otherMoneyList;
	}

	public String getOtherMoneyItem() {
		return OtherMoneyItem;
	}

	public void setOtherMoneyItem(String otherMoneyItem) {
		OtherMoneyItem = otherMoneyItem;
	}

	public Integer getAccountDay() {
		return AccountDay;
	}

	public void setAccountDay(Integer accountDay) {
		AccountDay = accountDay;
	}

	public Boolean getStatus() {
		return Status;
	}

	public void setStatus(Boolean status) {
		Status = status;
	}
}