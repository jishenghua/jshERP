package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SystemConfigModel implements Serializable
{
    private SystemConfigShowModel showModel = new SystemConfigShowModel();

    /**======开始接受页面参数=================**/
	private Long id = 0l;
    private String type = "";
	private String name = "";
	private String value = "";
	private String description = "";

    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";

	public SystemConfigShowModel getShowModel() {
		return showModel;
	}

	public void setShowModel(SystemConfigShowModel showModel) {
		this.showModel = showModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
}
