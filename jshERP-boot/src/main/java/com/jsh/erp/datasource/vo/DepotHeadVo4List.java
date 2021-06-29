package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.entities.DepotHead;

import java.math.BigDecimal;
import java.util.Date;

public class DepotHeadVo4List extends DepotHead{

    private String projectName;

    private String organName;

    private String userName;

    private String accountName;

    private String allocationProjectName;

    private String materialsList;

    private String salesManStr;

    private String operTimeStr;

    private BigDecimal finishDebt;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAllocationProjectName() {
        return allocationProjectName;
    }

    public void setAllocationProjectName(String allocationProjectName) {
        this.allocationProjectName = allocationProjectName;
    }

    public String getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(String materialsList) {
        this.materialsList = materialsList;
    }

    public String getSalesManStr() {
        return salesManStr;
    }

    public void setSalesManStr(String salesManStr) {
        this.salesManStr = salesManStr;
    }

    public String getOperTimeStr() {
        return operTimeStr;
    }

    public void setOperTimeStr(String operTimeStr) {
        this.operTimeStr = operTimeStr;
    }

    public BigDecimal getFinishDebt() {
        return finishDebt;
    }

    public void setFinishDebt(BigDecimal finishDebt) {
        this.finishDebt = finishDebt;
    }
}