package com.jsh.model.vo.materials;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PersonModel implements Serializable {
    private PersonShowModel showModel = new PersonShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 类型
     */
    private String Type = "";
    /**
     * 姓名
     */
    private String Name = "";

    /**
     * 分类ID
     */
    private Long personID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String personIDs = "";

    /**
     * 每页显示的个数
     */
    private int pageSize = 10;

    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";

    public PersonShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(PersonShowModel showModel) {
        this.showModel = showModel;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public String getPersonIDs() {
        return personIDs;
    }

    public void setPersonIDs(String personIDs) {
        this.personIDs = personIDs;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

}
