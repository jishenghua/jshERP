package com.jsh.junitest.json;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IPStream implements Serializable
{
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 协议
	 */
	private Short protocol;
	
	/**
	 *IP地址
	 */
	private String IPUrl;
	
	/**
	 * 端口
	 */
	private Integer port;
	
	/**
	 * 类型--输入输出 0==输入 1==输出
	 */
	private Short type;
	
	/**
	 * 视频格式
	 */
	private Short IPformat;
	
	/**
	 * 视频规格 0==标清 1==高清
	 */
	private Short IPnorms;
	
	/**
	 * 码率
	 */
	private Float rate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Short getProtocol()
	{
		return protocol;
	}

	public void setProtocol(Short protocol)
	{
		this.protocol = protocol;
	}

	public String getIPUrl()
	{
		return IPUrl;
	}

	public void setIPUrl(String iPUrl)
	{
		IPUrl = iPUrl;
	}

	public Integer getPort()
	{
		return port;
	}

	public void setPort(Integer port)
	{
		this.port = port;
	}

	public Short getType()
	{
		return type;
	}

	public void setType(Short type)
	{
		this.type = type;
	}

	public Short getIPformat()
	{
		return IPformat;
	}

	public void setIPformat(Short iPformat)
	{
		IPformat = iPformat;
	}

	public Short getIPnorms()
	{
		return IPnorms;
	}

	public void setIPnorms(Short iPnorms)
	{
		IPnorms = iPnorms;
	}

	public Float getRate()
	{
		return rate;
	}

	public void setRate(Float rate)
	{
		this.rate = rate;
	}
}
