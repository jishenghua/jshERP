package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

public class DepotItem {
    private Long id;

    private Long headerid;

    private Long materialid;

    private Long materialExtendId;

    private String munit;

    private BigDecimal opernumber;

    private BigDecimal basicnumber;

    private BigDecimal unitprice;

    private BigDecimal taxunitprice;

    private BigDecimal allprice;

    private String remark;

    private String img;

    private BigDecimal incidentals;

    private Long depotid;

    private Long anotherdepotid;

    private BigDecimal taxrate;

    private BigDecimal taxmoney;

    private BigDecimal taxlastmoney;

    private String otherfield1;

    private String otherfield2;

    private String otherfield3;

    private String otherfield4;

    private String otherfield5;

    private String mtype;

    private Long tenantId;

    private String deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHeaderid() {
        return headerid;
    }

    public void setHeaderid(Long headerid) {
        this.headerid = headerid;
    }

    public Long getMaterialid() {
        return materialid;
    }

    public void setMaterialid(Long materialid) {
        this.materialid = materialid;
    }

    public Long getMaterialExtendId() {
        return materialExtendId;
    }

    public void setMaterialExtendId(Long materialExtendId) {
        this.materialExtendId = materialExtendId;
    }

    public String getMunit() {
        return munit;
    }

    public void setMunit(String munit) {
        this.munit = munit == null ? null : munit.trim();
    }

    public BigDecimal getOpernumber() {
        return opernumber;
    }

    public void setOpernumber(BigDecimal opernumber) {
        this.opernumber = opernumber;
    }

    public BigDecimal getBasicnumber() {
        return basicnumber;
    }

    public void setBasicnumber(BigDecimal basicnumber) {
        this.basicnumber = basicnumber;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public BigDecimal getTaxunitprice() {
        return taxunitprice;
    }

    public void setTaxunitprice(BigDecimal taxunitprice) {
        this.taxunitprice = taxunitprice;
    }

    public BigDecimal getAllprice() {
        return allprice;
    }

    public void setAllprice(BigDecimal allprice) {
        this.allprice = allprice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public BigDecimal getIncidentals() {
        return incidentals;
    }

    public void setIncidentals(BigDecimal incidentals) {
        this.incidentals = incidentals;
    }

    public Long getDepotid() {
        return depotid;
    }

    public void setDepotid(Long depotid) {
        this.depotid = depotid;
    }

    public Long getAnotherdepotid() {
        return anotherdepotid;
    }

    public void setAnotherdepotid(Long anotherdepotid) {
        this.anotherdepotid = anotherdepotid;
    }

    public BigDecimal getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(BigDecimal taxrate) {
        this.taxrate = taxrate;
    }

    public BigDecimal getTaxmoney() {
        return taxmoney;
    }

    public void setTaxmoney(BigDecimal taxmoney) {
        this.taxmoney = taxmoney;
    }

    public BigDecimal getTaxlastmoney() {
        return taxlastmoney;
    }

    public void setTaxlastmoney(BigDecimal taxlastmoney) {
        this.taxlastmoney = taxlastmoney;
    }

    public String getOtherfield1() {
        return otherfield1;
    }

    public void setOtherfield1(String otherfield1) {
        this.otherfield1 = otherfield1 == null ? null : otherfield1.trim();
    }

    public String getOtherfield2() {
        return otherfield2;
    }

    public void setOtherfield2(String otherfield2) {
        this.otherfield2 = otherfield2 == null ? null : otherfield2.trim();
    }

    public String getOtherfield3() {
        return otherfield3;
    }

    public void setOtherfield3(String otherfield3) {
        this.otherfield3 = otherfield3 == null ? null : otherfield3.trim();
    }

    public String getOtherfield4() {
        return otherfield4;
    }

    public void setOtherfield4(String otherfield4) {
        this.otherfield4 = otherfield4 == null ? null : otherfield4.trim();
    }

    public String getOtherfield5() {
        return otherfield5;
    }

    public void setOtherfield5(String otherfield5) {
        this.otherfield5 = otherfield5 == null ? null : otherfield5.trim();
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype == null ? null : mtype.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }
}