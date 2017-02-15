package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LogModel implements Serializable 
{
	private LogShowModel showModel = new LogShowModel();
	/**======开始接受页面参数=================**/
	/**
	 * 用户ID
	 */
	private Long usernameID ;
	
	/**
	 * 操作
	 */
	private String operation;
	
	/**
	 * 开始时间
	 */
	private String beginTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 是否成功 0==成功 1==失败
	 */
	private Short status;
	
	/**
	 * 操作具体内容
	 */
	private String contentdetails;
	
	/**
	 * 描述信息
	 */
	private String remark = "";
	
	/**
	 * 日志ID
	 */
	private Long logID = 0l;
	
	/**
	 * 日志IDs 批量操作使用
	 */
	private String logIDs = "";
	
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
	public LogShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(LogShowModel showModel) {
		this.showModel = showModel;
	}

	public Long getLogID()
	{
		return logID;
	}

	public void setLogID(Long logID)
	{
		this.logID = logID;
	}

	public String getLogIDs()
	{
		return logIDs;
	}

	public void setLogIDs(String logIDs)
	{
		this.logIDs = logIDs;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public String getClientIp()
	{
		return clientIp;
	}

	public void setClientIp(String clientIp)
	{
		this.clientIp = clientIp;
	}

	public Long getUsernameID()
	{
		return usernameID;
	}

	public void setUsernameID(Long usernameID)
	{
		this.usernameID = usernameID;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public String getBeginTime()
	{
		if(null == beginTime || beginTime.length() == 0)
			return beginTime;
		return beginTime + " 00:00:00";
	}

	public void setBeginTime(String beginTime)
	{
		this.beginTime = beginTime;
	}

	public String getEndTime()
	{
		if(null == endTime || endTime.length() ==0)
			return endTime;
		return endTime + " 23:59:59";
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public Short getStatus()
	{
		return status;
	}

	public void setStatus(Short status)
	{
		this.status = status;
	}

	public String getContentdetails()
	{
		return contentdetails;
	}

	public void setContentdetails(String contentdetails)
	{
		this.contentdetails = contentdetails;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}
}
