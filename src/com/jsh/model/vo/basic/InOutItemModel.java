package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InOutItemModel implements Serializable
{
    private InOutItemShowModel showModel = new InOutItemShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String name = "";

    /**
     * 类型
     */
    private String type = "";

    /**
     * 备注
     */
    private String remark = "";

    /**
     * 分类ID
     */
    private Long inOutItemID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String inOutItemIDs = "";

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

    public void setShowModel(InOutItemShowModel showModel)
    {
        this.showModel = showModel;
    }

    public InOutItemShowModel getShowModel()
    {
        return showModel;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setInOutItemID(Long inOutItemID)
    {
        this.inOutItemID = inOutItemID;
    }

    public Long getInOutItemID()
    {
        return inOutItemID;
    }

    public void setInOutItemIDs(String inOutItemIDs)
    {
        this.inOutItemIDs = inOutItemIDs;
    }

    public String getInOutItemIDs()
    {
        return inOutItemIDs;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
    }

    public String getClientIp()
    {
        return clientIp;
    }
 }
