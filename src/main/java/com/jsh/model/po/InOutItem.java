package com.jsh.model.po;

@SuppressWarnings("serial")
public class InOutItem implements java.io.Serializable
{
    private Long Id;
    private String Name;
    private String Type;
    private String Remark;

    public InOutItem()
    {

    }

    public InOutItem(Long Id)
    {
        this.Id = Id;
    }

    public InOutItem(String name, String type, String remark) {
        Name = name;
        Type = type;
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

    public void setType(String type)
    {
        Type = type;
    }

    public String getType()
    {
        return Type;
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
