package com.jsh.model.po;

@SuppressWarnings("serial")
public class Account implements java.io.Serializable
{
    private Long Id;
    private String Name;
    private String SerialNo;
    private Double InitialAmount;
    private Double CurrentAmount;
    private Boolean IsDefault;
    private String Remark;

    public Account()
    {

    }

    public Account(Long Id)
    {
        this.Id = Id;
    }

    public Account(String name, String serialNo, Double initialAmount, Double currentAmount, Boolean isDefault, String remark) {
        Name = name;
        SerialNo = serialNo;
        InitialAmount = initialAmount;
        CurrentAmount = currentAmount;
        IsDefault = isDefault;
        Remark = remark;
    }

    public void setId(Long id)
    {
        Id = id;
    }

    public Long getId()
    {
        return Id;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getName()
    {
        return Name;
    }

    public void setSerialNo(String serialNo)
    {
        SerialNo = serialNo;
    }

    public String getSerialNo()
    {
        return SerialNo;
    }

    public void setInitialAmount(Double initialAmount)
    {
        InitialAmount = initialAmount;
    }

    public Double getInitialAmount()
    {
        return InitialAmount;
    }

    public void setCurrentAmount(Double currentAmount)
    {
        CurrentAmount = currentAmount;
    }

    public Double getCurrentAmount()
    {
        return CurrentAmount;
    }

    public Boolean getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        IsDefault = isDefault;
    }

    public void setRemark(String remark)
    {
        Remark = remark;
    }

    public String getRemark()
    {
        return Remark;
    } 
}
