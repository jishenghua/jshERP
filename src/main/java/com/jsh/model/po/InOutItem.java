package com.jsh.model.po;

@SuppressWarnings("serial")
public class InOutItem implements java.io.Serializable {
    private Long Id;
    private String Name;
    private String Type;
    private String Remark;

    public InOutItem() {

    }

    public InOutItem(Long Id) {
        this.Id = Id;
    }

    public InOutItem(String name, String type, String remark) {
        Name = name;
        Type = type;
        Remark = remark;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
