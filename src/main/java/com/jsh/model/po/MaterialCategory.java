package com.jsh.model.po;

@SuppressWarnings("serial")
public class MaterialCategory implements java.io.Serializable
{
	private Long Id;
	private String Name;
	private Short CategoryLevel;
	private MaterialCategory materialCategory;
	
	
	public MaterialCategory()
	{
		
	}
	
	public MaterialCategory(Long Id)
	{
		this.Id = Id;
	}

	public MaterialCategory(String name, Short categoryLevel,
			MaterialCategory materialCategory) {
		Name = name;
		CategoryLevel = categoryLevel;
		this.materialCategory = materialCategory;
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

	public Short getCategoryLevel() {
		return CategoryLevel;
	}

	public void setCategoryLevel(Short categoryLevel) {
		CategoryLevel = categoryLevel;
	}

	public MaterialCategory getMaterialCategory() {
		return materialCategory;
	}

	public void setMaterialCategory(MaterialCategory materialCategory) {
		this.materialCategory = materialCategory;
	}

}