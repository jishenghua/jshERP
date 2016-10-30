package com.jsh.model.po;

@SuppressWarnings("serial")
public class App implements java.io.Serializable
{
	private Long Id;
	private String Number;
	private String Name;
	private String Type;
	private String Icon;
	private String URL;
	private String Width;
	private String Height;
	private Boolean ReSize;
	private Boolean OpenMax;
	private Boolean Flash;
	private String ZL;
	private String Sort;
	private String Remark;
	private Boolean Enabled;
	
	
	public App()
	{
		
	}
	
	public App(Long Id)
	{
		this.Id = Id ;
	}

	public App(String Number, String Name, String Type, String Icon, String URL, String Width, 
			String Height, Boolean ReSize, Boolean OpenMax, Boolean Flash, String ZL, String Sort, 
			String Remark, Boolean Enabled)
	{
		this.Number = Number;
		this.Name = Name;
		this.Type = Type;
		this.Icon = Icon;
		this.URL = URL;
		this.Width = Width;
		this.Height = Height;
		this.ReSize = ReSize;
		this.OpenMax = OpenMax;
		this.Flash = Flash;
		this.ZL = ZL;
		this.Sort = Sort;
		this.Remark = Remark;
		this.Enabled = Enabled;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getWidth() {
		return Width;
	}

	public void setWidth(String width) {
		Width = width;
	}

	public String getHeight() {
		return Height;
	}

	public void setHeight(String height) {
		Height = height;
	}

	public Boolean getReSize() {
		return ReSize;
	}

	public void setReSize(Boolean reSize) {
		ReSize = reSize;
	}

	public Boolean getOpenMax() {
		return OpenMax;
	}

	public void setOpenMax(Boolean openMax) {
		OpenMax = openMax;
	}

	public Boolean getFlash() {
		return Flash;
	}

	public void setFlash(Boolean flash) {
		Flash = flash;
	}

	public String getZL() {
		return ZL;
	}

	public void setZL(String zL) {
		ZL = zL;
	}

	public String getSort() {
		return Sort;
	}

	public void setSort(String sort) {
		Sort = sort;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public Boolean getEnabled() {
		return Enabled;
	}

	public void setEnabled(Boolean enabled) {
		Enabled = enabled;
	}


}