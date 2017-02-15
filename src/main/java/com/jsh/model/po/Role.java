package com.jsh.model.po;

@SuppressWarnings("serial")
public class Role implements java.io.Serializable
{
	private Long Id;
	private String Name;

	public Role()
	{
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	

}