package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccountModel implements Serializable
{
    private AccountShowModel showModel = new AccountShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String name = "";

    /**
     * 编号
     */
    private String serialNo = "";
    
    /**
     * 期初金额
     */
    private Double initialAmount;
    
    /**
     * 当前余额
     */
    private Double currentAmount;

    /**
     * 是否设为默认
     */
    private Boolean isDefault;

    /**
     * 备注
     */
    private String remark = "";

    /**
     * 分类ID
     */
    private Long accountID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String accountIDs = "";

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

    public void setShowModel(AccountShowModel showModel)
    {
        this.showModel = showModel;
    }

    public AccountShowModel getShowModel()
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

    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }

    public String getSerialNo()
    {
        return serialNo;
    }

    public void setInitialAmount(Double initialAmount)
    {
        this.initialAmount = initialAmount;
    }

    public Double getInitialAmount()
    {
        return initialAmount;
    }

    public void setCurrentAmount(Double currentAmount)
    {
        this.currentAmount = currentAmount;
    }

    public Double getCurrentAmount()
    {
        return currentAmount;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setAccountID(Long accountID)
    {
        this.accountID = accountID;
    }

    public Long getAccountID()
    {
        return accountID;
    }

    public void setAccountIDs(String accountIDs)
    {
        this.accountIDs = accountIDs;
    }

    public String getAccountIDs()
    {
        return accountIDs;
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
