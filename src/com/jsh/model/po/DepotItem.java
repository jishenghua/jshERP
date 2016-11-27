package com.jsh.model.po;

@SuppressWarnings("serial")
public class DepotItem implements java.io.Serializable
{
	private Long Id;
	private DepotHead HeaderId;
	private Material MaterialId;
	private Double OperNumber;
	private Double UnitPrice;
	private Double Incidentals;
	private String Remark;
	private String Img;

	public DepotItem()
	{
		
	}
	
	public DepotItem(Long Id)
	{
		this.Id = Id ;
	}

	public DepotItem(DepotHead headerId, Material materialId,
			Double operNumber, Double unitPrice, Double incidentals,
			String remark, String img) {
		super();
		HeaderId = headerId;
		MaterialId = materialId;
		OperNumber = operNumber;
		UnitPrice = unitPrice;
		Incidentals = incidentals;
		Remark = remark;
		Img = img;
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

	public Double getOperNumber() {
		return OperNumber;
	}

	public void setOperNumber(Double operNumber) {
		OperNumber = operNumber;
	}

	public Double getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		UnitPrice = unitPrice;
	}

	public Double getIncidentals() {
		return Incidentals;
	}

	public void setIncidentals(Double incidentals) {
		Incidentals = incidentals;
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


}
