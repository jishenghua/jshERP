package com.jsh.junitest.json;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CableStream implements Serializable
{
	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 频点频率
	 */
	private Integer cablehz; 
	
	/**
	 * 符号率
	 */
	private Integer symbolrate;
	
	/**
	 * 调制方式
	 */
	private Integer cableMode;
	
	/**
	 * 传输编号
	 */
	private Integer transNum;
	
	/**
	 * 服务编号
	 */
	private Integer serviceNum;
	
	/**
	 * 码率
	 */
	private Float rate;
	
	/**
	 * 规格
	 */
	private Short cablenorms;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getCablehz()
	{
		return cablehz;
	}

	public void setCablehz(Integer cablehz)
	{
		this.cablehz = cablehz;
	}

	public Integer getSymbolrate()
	{
		return symbolrate;
	}

	public void setSymbolrate(Integer symbolrate)
	{
		this.symbolrate = symbolrate;
	}

	public Integer getCableMode()
	{
		return cableMode;
	}

	public void setCableMode(Integer cableMode)
	{
		this.cableMode = cableMode;
	}

	public Integer getTransNum()
	{
		return transNum;
	}

	public void setTransNum(Integer transNum)
	{
		this.transNum = transNum;
	}

	public Integer getServiceNum()
	{
		return serviceNum;
	}

	public void setServiceNum(Integer serviceNum)
	{
		this.serviceNum = serviceNum;
	}

	public Float getRate()
	{
		return rate;
	}

	public void setRate(Float rate)
	{
		this.rate = rate;
	}

	public Short getCablenorms()
	{
		return cablenorms;
	}

	public void setCablenorms(Short cablenorms)
	{
		this.cablenorms = cablenorms;
	}
}