package com.jsh.model.po;

@SuppressWarnings("serial")
public class Assetname implements java.io.Serializable {
    private Long id;
    private String assetname;
    private Short isystem;
    private Category category;
    private String description;
    private Short isconsumables;

    public Assetname() {

    }

    public Assetname(Long id) {
        this.id = id;
    }

    public Assetname(String assetname, Short isystem, String description,
                     Short isconsumables, Category category) {
        this.assetname = assetname;
        this.isystem = isystem;
        this.description = description;
        this.isconsumables = isconsumables;
        this.category = category;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetname() {
        return this.assetname;
    }

    public void setAssetname(String assetname) {
        this.assetname = assetname;
    }

    public Short getIsystem() {
        return this.isystem;
    }

    public void setIsystem(Short isystem) {
        this.isystem = isystem;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getIsconsumables() {
        return this.isconsumables;
    }

    public void setIsconsumables(Short isconsumables) {
        this.isconsumables = isconsumables;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}