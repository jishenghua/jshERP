package com.jsh.erp.datasource.entities;

import java.util.Date;

public class DepotItemVo4DetailByTypeAndMId {

    private String number;

    private String newtype;

    private Integer bnum;

    private Date otime;
    //仓库名称
    private String depotName;
    //调入仓库名称
    private String depotInName;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNewtype() {
        return newtype;
    }

    public void setNewtype(String newtype) {
        this.newtype = newtype;
    }

    public Integer getBnum() {
        return bnum;
    }

    public void setBnum(Integer bnum) {
        this.bnum = bnum;
    }

    public Date getOtime() {
        return otime;
    }

    public void setOtime(Date otime) {
        this.otime = otime;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getDepotInName() {
        return depotInName;
    }

    public void setDepotInName(String depotInName) {
        this.depotInName = depotInName;
    }
}