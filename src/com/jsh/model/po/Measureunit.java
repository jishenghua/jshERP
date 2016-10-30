package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class Measureunit implements java.io.Serializable
{
	private Long id;
	private String unitname;
	private String description;
	private Short isystem;

	public Measureunit()
	{
		
	}
	
	public Measureunit(Long id)
	{
		this.id = id;
	}

	public Measureunit(String unitname, Timestamp createtime, Long creator,
			Timestamp updatetime, Long updator, String description,
			Short isystem)
	{
		this.unitname = unitname;
		this.description = description;
		this.isystem = isystem;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUnitname()
	{
		return this.unitname;
	}

	public void setUnitname(String unitname)
	{
		this.unitname = unitname;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Short getIsystem()
	{
		return this.isystem;
	}

	public void setIsystem(Short isystem)
	{
		this.isystem = isystem;
	}

}