package com.jsh.model.po;

@SuppressWarnings("serial")
public class Material implements java.io.Serializable
{
	private Long Id;
	private MaterialCategory materialCategory;
	private String Name;
	private String Model;
	private String Color;
	private String Unit;
    private Double RetailPrice;
    private Double LowPrice;
    private Double PresetPriceOne;
    private Double PresetPriceTwo;
	private String Remark;

	public Material()
	{

	}

	public Material(Long Id)
	{
		this.Id = Id;
	}

	public Material(MaterialCategory materialCategory, String name,
                    String model, String color, String unit, String remark,
                    Double retailPrice, Double lowPrice, Double presetPriceOne, Double presetPriceTwo)
    {
		super();
		this.materialCategory = materialCategory;
		Name = name;
		Model = model;
		Color = color;
		Unit = unit;
        RetailPrice = retailPrice;
        LowPrice = lowPrice;
        PresetPriceOne = presetPriceOne;
        PresetPriceTwo = presetPriceTwo;
		Remark = remark;
	}

	public Long getId()
    {
		return Id;
	}

	public void setId(Long id)
    {
		Id = id;
	}

	public MaterialCategory getMaterialCategory()
    {
		return materialCategory;
	}

	public void setMaterialCategory(MaterialCategory materialCategory)
    {
		this.materialCategory = materialCategory;
	}

	public String getName()
    {
		return Name;
	}

	public void setName(String name)
    {
		Name = name;
	}

	public String getModel()
    {
		return Model;
	}

	public void setModel(String model)
    {
		Model = model;
	}

	public String getColor()
    {
		return Color;
	}

	public void setColor(String color)
    {
		Color = color;
	}

	public String getUnit()
    {
		return Unit;
	}

	public void setUnit(String unit)
    {
		Unit = unit;
	}
    
    public void setRetailPrice(Double retailPrice)
    {
        RetailPrice = retailPrice;
    }

    public Double getRetailPrice()
    {
        return RetailPrice;
    }

    public void setLowPrice(Double lowPrice)
    {
        LowPrice = lowPrice;
    }

    public Double getLowPrice()
    {
        return LowPrice;
    }

    public void setPresetPriceOne(Double presetPriceOne)
    {
        PresetPriceOne = presetPriceOne;
    }

    public Double getPresetPriceOne()
    {
        return PresetPriceOne;
    }

    public void setPresetPriceTwo(Double presetPriceTwo)
    {
        PresetPriceTwo = presetPriceTwo;
    }

    public Double getPresetPriceTwo()
    {
        return PresetPriceTwo;
    }

	public String getRemark()
    {
		return Remark;
	}

	public void setRemark(String remark)
    {
		Remark = remark;
	}

}
