package com.jsh.model.po;

@SuppressWarnings("serial")
public class Person implements java.io.Serializable
{
	private Long Id;
	private String Type;
	private String Name;

	public Person()
	{
		
	}
	
	public Person(Long Id)
	{
		this.Id = Id;
	}

	public Person(String type, String name) {
		Type = type;
		Name = name;
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

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
}