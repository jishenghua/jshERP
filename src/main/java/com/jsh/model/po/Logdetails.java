package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class Logdetails implements java.io.Serializable {

    private Long id;
    private Basicuser user;
    private String operation;
    private String clientIp;
    private Timestamp createtime;
    private Short status;
    private String contentdetails;
    private String remark;

    public Logdetails() {

    }

    public Logdetails(Long id) {
        this.id = id;
    }

    public Logdetails(Basicuser user, String operation, String clientIp,
                      Timestamp createtime, Short status, String contentdetails,
                      String remark) {
        this.user = user;
        this.operation = operation;
        this.clientIp = clientIp;
        this.createtime = createtime;
        this.status = status;
        this.contentdetails = contentdetails;
        this.remark = remark;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Basicuser getUser() {
        return user;
    }

    public void setUser(Basicuser user) {
        this.user = user;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Timestamp getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getContentdetails() {
        return this.contentdetails;
    }

    public void setContentdetails(String contentdetails) {
        this.contentdetails = contentdetails;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}