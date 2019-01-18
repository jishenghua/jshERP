package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;

public class AccountVo4List {

    private Long id;

    private String name;

    private String serialno;

    private BigDecimal initialamount;

    private BigDecimal currentamount;

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

    public BigDecimal getInitialamount() {
        return initialamount;
    }

    public void setInitialamount(BigDecimal initialamount) {
        this.initialamount = initialamount;
    }

    public BigDecimal getCurrentamount() {
        return currentamount;
    }

    public void setCurrentamount(BigDecimal currentamount) {
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