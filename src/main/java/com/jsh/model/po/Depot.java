package com.jsh.model.po;

@SuppressWarnings("serial")
public class Depot implements java.io.Serializable {
    private Long id;
    private String name;
    private String address;
    private Double warehousing;
    private Double truckage;
    private Integer type;
    private String sort;
    private String remark;

    public Depot() {

    }

    public Depot(Long id) {
        this.id = id;
    }

    public Depot(String name, String address, Double warehousing, Double truckage, Integer type, String sort, String remark) {
        this.name = name;
        this.address = address;
        this.warehousing = warehousing;
        this.truckage = truckage;
        this.type = type;
        this.sort = sort;
        this.remark = remark;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getWarehousing() {
        return warehousing;
    }

    public void setWarehousing(Double warehousing) {
        this.warehousing = warehousing;
    }

    public Double getTruckage() {
        return truckage;
    }

    public void setTruckage(Double truckage) {
        this.truckage = truckage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}