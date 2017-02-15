package com.jsh.model.po;

@SuppressWarnings("serial")
public class Supplier implements java.io.Serializable
{
	private Long id;
	private String supplier;
	private String type;
	private String contacts;
	private String phonenum;
	private String email;
    private Double BeginNeedGet;
    private Double BeginNeedPay;
    private Double AllNeedGet;
    private Double AllNeedPay;
	private Short isystem;
	private String description;
	private Boolean enabled;

	public Supplier()
	{
		
	}
	
	public Supplier(Long id)
	{
	    this.id = id;
	}

	public Supplier(String supplier, String type, String contacts, String phonenum, 
    String email, Short isystem, String description, Boolean enabled,
                    Double beginNeedGet,Double beginNeedPay,Double allNeedGet,Double allNeedPay) {
		super();
		this.supplier = supplier;
		this.type = type;
		this.contacts = contacts;
		this.phonenum = phonenum;
		this.email = email;
        this.BeginNeedGet = beginNeedGet;
        this.BeginNeedPay = beginNeedPay;
        this.AllNeedGet = allNeedGet;
        this.AllNeedPay = allNeedPay; 
		this.isystem = isystem;
		this.description = description;
		this.enabled = enabled;
	}

	public Long getId()
    {
		return id;
	}

	public void setId(Long id)
    {
		this.id = id;
	}

	public String getSupplier()
    {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    public void setBeginNeedGet(Double beginNeedGet)
    {
        BeginNeedGet = beginNeedGet;
    }

    public Double getBeginNeedGet()
    {
        return BeginNeedGet;
    }

    public void setBeginNeedPay(Double beginNeedPay)
    {
        BeginNeedPay = beginNeedPay;
    }

    public Double getBeginNeedPay()
    {
        return BeginNeedPay;
    }

    public void setAllNeedGet(Double allNeedGet)
    {
        AllNeedGet = allNeedGet;
    }

    public Double getAllNeedGet()
    {
        return AllNeedGet;
    }

    public void setAllNeedPay(Double allNeedPay)
    {
        AllNeedPay = allNeedPay;
    }

    public Double getAllNeedPay()
    {
        return AllNeedPay;
    }

	public Short getIsystem() {
		return isystem;
	}

	public void setIsystem(Short isystem) {
		this.isystem = isystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}	
    
}
