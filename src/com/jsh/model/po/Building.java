package com.jsh.model.po;

@SuppressWarnings("serial")
public class Building implements java.io.Serializable
{
	private Long Id;
	private Depot depot;
	private String Name;
	private String Remark;
	private Boolean Enabled;

	public Building()
	{
		
	}
	
	public Building(Long Id)
	{
		this.Id = Id;
	}

	public Building(Depot depot, String name, String remark, Boolean enabled) {
		super();
		this.depot = depot;
		Name = name;
		Remark = remark;
		Enabled = enabled;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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
	
}