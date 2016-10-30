package com.jsh.junitest.json;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Channel implements Serializable
{
	/**
	 * 频道ID
	 */
	private Long id;
	
	/**
	 * 频道名称
	 */
	private String channelName ;
	
	/**
	 * 分类
	 */
	private Long category;
	
	/**
	 * 台标路径
	 */
	private String tvLogo;
	
	/**
	 * 地区
	 */
	private Long area;
	
	/**
	 * 频道类型 0==直播 1==虚拟
	 */
	private Short type;
	
	/**
	 * json字符串
	 */
	private String ipStreamJson;
	
	/**
	 * ip流列表
	 */
	private List<IPStream> ipStream;
	
	/**
	 * json字符串
	 */
	private String cableStreamJson;
	
	/**
	 * cable流列表
	 */
	private List<CableStream> cableStream;
	

	public Channel()
	{
		super();
	}

	
	
	public Channel(String channelName, Long category, String tvLogo,
			Long area, Short type, String ipStreamJson,
			List<IPStream> ipStream, String cableStreamJson,
			List<CableStream> cableStream)
	{
		super();
		this.channelName = channelName;
		this.category = category;
		this.tvLogo = tvLogo;
		this.area = area;
		this.type = type;
		this.ipStreamJson = ipStreamJson;
		this.ipStream = ipStream;
		this.cableStreamJson = cableStreamJson;
		this.cableStream = cableStream;
	}



	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getChannelName()
	{
		return channelName;
	}

	public void setChannelName(String channelName)
	{
		this.channelName = channelName;
	}

	public Long getCategory()
	{
		return category;
	}

	public void setCategory(Long category)
	{
		this.category = category;
	}

	public String getTvLogo()
	{
		return tvLogo;
	}

	public void setTvLogo(String tvLogo)
	{
		this.tvLogo = tvLogo;
	}

	public Long getArea()
	{
		return area;
	}

	public void setArea(Long area)
	{
		this.area = area;
	}

	public Short getType()
	{
		return type;
	}

	public void setType(Short type)
	{
		this.type = type;
	}

	public List<IPStream> getIpStream()
	{
		return ipStream;
	}

	public void setIpStream(List<IPStream> ipStream)
	{
		this.ipStream = ipStream;
	}

	public List<CableStream> getCableStream()
	{
		return cableStream;
	}

	public void setCableStream(List<CableStream> cableStream)
	{
		this.cableStream = cableStream;
	}

	public String getIpStreamJson()
	{
		return ipStreamJson;
	}

	public void setIpStreamJson(String ipStreamJson)
	{
		this.ipStreamJson = ipStreamJson;
	}

	public String getCableStreamJson()
	{
		return cableStreamJson;
	}

	public void setCableStreamJson(String cableStreamJson)
	{
		this.cableStreamJson = cableStreamJson;
	}
}
