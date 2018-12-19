package com.jsh.erp.datasource.vo;

public class AccountVo4List {

    private Long id;

    private String name;

    private String serialno;

    private Double initialamount;

    private Double currentamount;

    private String remark;

    private Boolean isdefault;

    private String thismonthamount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public Double getInitialamount() {
        return initialamount;
    }

    public void setInitialamount(Double initialamount) {
        this.initialamount = initialamount;
    }

    public Double getCurrentamount() {
        return currentamount;
    }

    public void setCurrentamount(Double currentamount) {
        this.currentamount = currentamount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public String getThismonthamount() {
        return thismonthamount;
    }

    public void setThismonthamount(String thismonthamount) {
        this.thismonthamount = thismonthamount;
    }
}