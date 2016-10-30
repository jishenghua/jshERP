package com.jsh.model.po;

@SuppressWarnings("serial")
public class Person implements java.io.Serializable
{
	private Long Id;
	private Depot depot;
	private String Type;
	private String Name;

	public Person()
	{
		
	}
	
	public Person(Long Id)
	{
		this.Id = Id;
	}

	public Person(Depot depot, String type, String name) {
		this.depot = depot;
		Type = type;
		Name = name;
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

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
}