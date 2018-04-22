package com.jsh.model.po;

@SuppressWarnings("serial")
public class Basicuser implements java.io.Serializable {
    private Long id;
    private String username;
    private String loginame;
    private String password;
    private String position;
    private String department;
    private String email;
    private String phonenum;
    private Short ismanager;
    private Short isystem;
    private Short status;
    private String description;
    private String remark;

    public Basicuser() {
    }

    public Basicuser(Long id) {
        this.id = id;
    }

    public Basicuser(String username, String loginame, String password,
                     String position, String department, String email, String phonenum,
                     Short ismanager, Short isystem, Short status, String description,
                     String remark) {
        this.username = username;
        this.loginame = loginame;
        this.password = password;
        this.position = position;
        this.department = department;
        this.email = email;
        this.phonenum = phonenum;
        this.ismanager = ismanager;
        this.isystem = isystem;
        this.status = status;
        this.description = description;
        this.remark = remark;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginame() {
        return this.loginame;
    }

    public void setLoginame(String loginame) {
        this.loginame = loginame;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenum() {
        return this.phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public Short getIsmanager() {
        return this.ismanager;
    }

    public void setIsmanager(Short ismanager) {
        this.ismanager = ismanager;
    }

    public Short getIsystem() {
        return this.isystem;
    }

    public void setIsystem(Short isystem) {
        this.isystem = isystem;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}