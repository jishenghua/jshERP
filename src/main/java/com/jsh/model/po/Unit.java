package com.jsh.model.po;

@SuppressWarnings("serial")
public class Unit implements java.io.Serializable
{
	private Long id;
	private String UName;

	public Unit()
	{

	}

	public Unit(Long id) {
		this.id = id;
	}

	public Unit(String UName)
	{
		this.UName = UName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUName() {
		return UName;
	}

	public void setUName(String UName) {
		this.UName = UName;
	}

}