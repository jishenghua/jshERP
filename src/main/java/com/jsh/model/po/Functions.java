package com.jsh.model.po;

@SuppressWarnings("serial")
public class Functions implements java.io.Serializable {
    private Long Id;
    private String Number;
    private String Name;
    private String PNumber;
    private String URL;
    private Boolean State;
    private String Sort;
    private Boolean Enabled;
    private String Type;
    private String PushBtn;

    public Functions() {

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

    public String getPNumber() {
        return PNumber;
    }

    public void setPNumber(String pNumber) {
        PNumber = pNumber;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public Boolean getState() {
        return State;
    }

    public void setState(Boolean state) {
        State = state;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPushBtn() {
        return PushBtn;
    }

    public void setPushBtn(String pushBtn) {
        PushBtn = pushBtn;
    }
}