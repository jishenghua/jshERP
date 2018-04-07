package com.jsh.model.po;

@SuppressWarnings("serial")
public class MaterialProperty implements java.io.Serializable {
    private Long id;
    private String nativeName;
    private Boolean enabled;
    private String sort;
    private String anotherName;

    public MaterialProperty() {

    }

    public MaterialProperty(Long id) {
        this.id = id;
    }

    public MaterialProperty(String nativeName, Boolean enabled, String sort, String anotherName) {
        nativeName = nativeName;
        enabled = enabled;
        sort = sort;
        anotherName = anotherName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAnotherName() {
        return anotherName;
    }

    public void setAnotherName(String anotherName) {
        this.anotherName = anotherName;
    }
}