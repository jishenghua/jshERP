package com.jsh.model.po;

@SuppressWarnings("serial")
public class Category implements java.io.Serializable
{
	private Long id;
	private String assetname;
	private Short isystem;
	private String description;

	public Category()
	{
		
	}
	
	public Category(Long id)
	{
		this.id = id ;
	}

	public Category(String assetname, Short isystem, String description)
	{
		this.assetname = assetname;
		this.isystem = isystem;
		this.description = description;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getAssetname()
	{
		return this.assetname;
	}

	public void setAssetname(String assetname)
	{
		this.assetname = assetname;
	}

	public Short getIsystem()
	{
		return this.isystem;
	}

	public void setIsystem(Short isystem)
	{
		this.isystem = isystem;
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