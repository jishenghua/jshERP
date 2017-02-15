package com.jsh.model.po;

import java.sql.Timestamp;
import java.util.Map;

@SuppressWarnings("serial")
public class Asset implements java.io.Serializable
{
	private Long id;
	private Assetname assetname;
	private String location;
	private Short status;
	private Basicuser user;
	private Double price;
	private Timestamp purchasedate;
	private Timestamp periodofvalidity;
	private Timestamp warrantydate;
	private String assetnum;
	private String serialnum;
	private Supplier supplier;
	private String labels;
	private String description;
	private String addMonth;
	private Timestamp createtime;
	private Basicuser creator;
	private Timestamp updatetime;
	private Basicuser updator;
	
	//----------以下属性导入exel表格使用--------------------
	/**
     * 类型 right--正确 warn--警告  wrong--错误
     */
    private Map<Integer,String> cellInfo;
    
    /**
     * 行号
     */
    private Integer rowLineNum;
    
    /**
     * 保存价格
     */
    private String priceStr;
    
    /**
     * 资产名称
     */
    private String assetnameStr;
    
    /**
     * 资产类型
     */
    private String category;
    
    /**
     * 购买日期
     */
    private String purchasedateStr;
    
    /**
     * 有效日期
     */
    private String periodofvalidityStr;
    
    /**
     * 保修日期
     */
    private String warrantydateStr;
    
	public Asset()
	{
		
	}
	
	public Asset(Long id)
	{
		this.id = id;
	}
	
	public Asset(Assetname assetname, String location,
			Short status, Basicuser user, Double price, Timestamp purchasedate,
			Timestamp periodofvalidity, Timestamp warrantydate,
			String assetnum, String serialnum, Supplier supplier,
			String description, Timestamp createtime, Basicuser creator,
			Timestamp updatetime,String labels, Basicuser updator,String addMonth)
	{
		super();
		this.assetname = assetname;
		this.location = location;
		this.status = status;
		this.user = user;
		this.price = price;
		this.purchasedate = purchasedate;
		this.periodofvalidity = periodofvalidity;
		this.warrantydate = warrantydate;
		this.assetnum = assetnum;
		this.serialnum = serialnum;
		this.supplier = supplier;
		this.description = description;
		this.createtime = createtime;
		this.creator = creator;
		this.updatetime = updatetime;
		this.updator = updator;
		this.labels = labels;
		this.addMonth = addMonth;
	}



	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Assetname getAssetname()
	{
		return assetname;
	}

	public void setAssetname(Assetname assetname)
	{
		this.assetname = assetname;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public Short getStatus()
	{
		return status;
	}

	public void setStatus(Short status)
	{
		this.status = status;
	}

	public Basicuser getUser()
	{
		return user;
	}

	public void setUser(Basicuser user)
	{
		this.user = user;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public Timestamp getPurchasedate()
	{
		return purchasedate;
	}

	public void setPurchasedate(Timestamp purchasedate)
	{
		this.purchasedate = purchasedate;
	}

	public Timestamp getPeriodofvalidity()
	{
		return periodofvalidity;
	}

	public void setPeriodofvalidity(Timestamp periodofvalidity)
	{
		this.periodofvalidity = periodofvalidity;
	}

	public Timestamp getWarrantydate()
	{
		return warrantydate;
	}

	public void setWarrantydate(Timestamp warrantydate)
	{
		this.warrantydate = warrantydate;
	}

	public String getAssetnum()
	{
		return assetnum;
	}

	public void setAssetnum(String assetnum)
	{
		this.assetnum = assetnum;
	}

	public String getSerialnum()
	{
		return serialnum;
	}

	public void setSerialnum(String serialnum)
	{
		this.serialnum = serialnum;
	}

	public Supplier getSupplier()
	{
		return supplier;
	}

	public void setSupplier(Supplier supplier)
	{
		this.supplier = supplier;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Timestamp getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(Timestamp createtime)
	{
		this.createtime = createtime;
	}

	public Basicuser getCreator()
	{
		return creator;
	}

	public void setCreator(Basicuser creator)
	{
		this.creator = creator;
	}

	public Timestamp getUpdatetime()
	{
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime)
	{
		this.updatetime = updatetime;
	}

	public Basicuser getUpdator()
	{
		return updator;
	}

	public void setUpdator(Basicuser updator)
	{
		this.updator = updator;
	}

    public String getLabels()
    {
        return labels;
    }

    public void setLabels(String labels)
    {
        this.labels = labels;
    }

    public String getAddMonth()
    {
        return addMonth;
    }

    public void setAddMonth(String addMonth)
    {
        this.addMonth = addMonth;
    }

    public Integer getRowLineNum()
    {
        return rowLineNum;
    }

    public void setRowLineNum(Integer rowLineNum)
    {
        this.rowLineNum = rowLineNum;
    }

    public Map<Integer, String> getCellInfo()
    {
        return cellInfo;
    }

    public void setCellInfo(Map<Integer, String> cellInfo)
    {
        this.cellInfo = cellInfo;
    }

    public String getPriceStr()
    {
        return priceStr;
    }

    public void setPriceStr(String priceStr)
    {
        this.priceStr = priceStr;
    }

    public String getAssetnameStr()
    {
        return assetnameStr;
    }

    public void setAssetnameStr(String assetnameStr)
    {
        this.assetnameStr = assetnameStr;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getPurchasedateStr()
    {
        return purchasedateStr;
    }

    public void setPurchasedateStr(String purchasedateStr)
    {
        this.purchasedateStr = purchasedateStr;
    }

    public String getPeriodofvalidityStr()
    {
        return periodofvalidityStr;
    }

    public void setPeriodofvalidityStr(String periodofvalidityStr)
    {
        this.periodofvalidityStr = periodofvalidityStr;
    }

    public String getWarrantydateStr()
    {
        return warrantydateStr;
    }

    public void setWarrantydateStr(String warrantydateStr)
    {
        this.warrantydateStr = warrantydateStr;
    }
}