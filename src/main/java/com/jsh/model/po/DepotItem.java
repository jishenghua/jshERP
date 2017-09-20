package com.jsh.model.po;

@SuppressWarnings("serial")
public class DepotItem implements java.io.Serializable
{
	private Long Id;
	private DepotHead HeaderId;
	private Material MaterialId;
	private String MUnit; //计量单位
	private Double OperNumber;
	private Double BasicNumber;
	private Double UnitPrice;
	private Double TaxUnitPrice; //含税单价
	private Double AllPrice;
	private String Remark;
	private String Img;
	private Depot DepotId; //仓库ID
	private Depot AnotherDepotId; //对方仓库Id
	private Double TaxRate; //税率
	private Double TaxMoney; //税额
	private Double TaxLastMoney; //价税合计
	private String OtherField1; //自定义字段1-品名
	private String OtherField2; //自定义字段2-型号
	private String OtherField3; //自定义字段3-制造商
	private String OtherField4; //自定义字段4
	private String OtherField5; //自定义字段5
	private String MType; //商品类型


	public DepotItem()
	{
		
	}
	
	public DepotItem(Long Id)
	{
		this.Id = Id ;
	}

	public DepotItem(DepotHead headerId, Material materialId, String mUnit,
			Double operNumber, Double basicNumber, Double unitPrice, Double taxUnitPrice, Double allPrice, String remark, String img,
			 Depot depotId, Depot anotherDepotId, Double taxRate, Double taxMoney, Double taxLastMoney,
			 String otherField1, String otherField2, String otherField3, String otherField4, String otherField5, String mType) {
		super();
		HeaderId = headerId;
		MaterialId = materialId;
		MUnit = mUnit;
		OperNumber = operNumber;
		BasicNumber = basicNumber;
		UnitPrice = unitPrice;
		TaxUnitPrice = taxUnitPrice;
		AllPrice = allPrice;
		Remark = remark;
		Img = img;
		DepotId = depotId;
		AnotherDepotId = anotherDepotId;
		TaxRate = taxRate;
		TaxMoney = taxMoney;
		TaxLastMoney = taxLastMoney;
		OtherField1 = otherField1;
		OtherField2 = otherField2;
		OtherField3 = otherField3;
		OtherField4 = otherField4;
		OtherField5 = otherField5;
		MType = mType;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public DepotHead getHeaderId() {
		return HeaderId;
	}

	public void setHeaderId(DepotHead headerId) {
		HeaderId = headerId;
	}

	public Material getMaterialId() {
		return MaterialId;
	}

	public void setMaterialId(Material materialId) {
		MaterialId = materialId;
	}

	public String getMUnit() {
		return MUnit;
	}

	public void setMUnit(String MUnit) {
		this.MUnit = MUnit;
	}

	public Double getTaxUnitPrice() {
		return TaxUnitPrice;
	}

	public void setTaxUnitPrice(Double taxUnitPrice) {
		TaxUnitPrice = taxUnitPrice;
	}

	public Double getOperNumber() {
		return OperNumber;
	}

	public void setOperNumber(Double operNumber) {
		OperNumber = operNumber;
	}

	public Double getBasicNumber() {
		return BasicNumber;
	}

	public void setBasicNumber(Double basicNumber) {
		BasicNumber = basicNumber;
	}

	public Double getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		UnitPrice = unitPrice;
	}

	public Double getAllPrice() {
		return AllPrice;
	}

	public void setAllPrice(Double allPrice) {
		AllPrice = allPrice;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getImg() {
		return Img;
	}

	public void setImg(String img) {
		Img = img;
	}

	public Depot getDepotId() {
		return DepotId;
	}

	public void setDepotId(Depot depotId) {
		DepotId = depotId;
	}

	public Depot getAnotherDepotId() {
		return AnotherDepotId;
	}

	public void setAnotherDepotId(Depot anotherDepotId) {
		AnotherDepotId = anotherDepotId;
	}

	public Double getTaxRate() {
		return TaxRate;
	}

	public void setTaxRate(Double taxRate) {
		TaxRate = taxRate;
	}

	public Double getTaxMoney() {
		return TaxMoney;
	}

	public void setTaxMoney(Double taxMoney) {
		TaxMoney = taxMoney;
	}

	public Double getTaxLastMoney() {
		return TaxLastMoney;
	}

	public void setTaxLastMoney(Double taxLastMoney) {
		TaxLastMoney = taxLastMoney;
	}

	public String getOtherField1() {
		return OtherField1;
	}

	public void setOtherField1(String otherField1) {
		OtherField1 = otherField1;
	}

	public String getOtherField2() {
		return OtherField2;
	}

	public void setOtherField2(String otherField2) {
		OtherField2 = otherField2;
	}

	public String getOtherField3() {
		return OtherField3;
	}

	public void setOtherField3(String otherField3) {
		OtherField3 = otherField3;
	}

	public String getOtherField4() {
		return OtherField4;
	}

	public void setOtherField4(String otherField4) {
		OtherField4 = otherField4;
	}

	public String getOtherField5() {
		return OtherField5;
	}

	public void setOtherField5(String otherField5) {
		OtherField5 = otherField5;
	}

	public String getMType() {
		return MType;
	}

	public void setMType(String MType) {
		this.MType = MType;
	}
}
