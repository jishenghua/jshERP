package com.jsh.model.po;


@SuppressWarnings("serial")
public class Department implements java.io.Serializable
{
	private Long id;
	private String deptname;
	private Short isystem;
	private String description;

	public Department()
	{
		
	}

	public Department(Long id)
	{
		this.id = id;
	}
	
	public Department(String deptname, Short isystem, String description)
	{
		super();
		this.deptname = deptname;
		this.isystem = isystem;
		this.description = description;
	}

	public Short getIsystem()
	{
		return isystem;
	}

	public void setIsystem(Short isystem)
	{
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

	public String getDeptname()
	{
		return this.deptname;
	}

	public void setDeptname(String deptname)
	{
		this.deptname = deptname;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}