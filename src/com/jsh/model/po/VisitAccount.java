package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class VisitAccount implements java.io.Serializable
{
	private Long Id;
	private Depot depot;
	private String LouHao;
	private String HuHao;
	private String HuiFang;
	private String LuoShi;
	private String Name;
	private String Tel;
	private Timestamp AddTime;

	public VisitAccount()
	{
		
	}
	
	public VisitAccount(Long Id)
	{
		this.Id = Id;
	}

	public VisitAccount(Depot depot, String LouHao, String HuHao,
			String HuiFang,String LuoShi,String Name,String Tel,Timestamp AddTime)
	{
		this.depot = depot;
		this.LouHao = LouHao;
		this.HuHao = HuHao;
		this.HuiFang = HuiFang;
		this.LuoShi = LuoShi;
		this.Name = Name;
		this.Tel = Tel;
		this.AddTime = AddTime;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public String getLouHao() {
		return LouHao;
	}

	public void setLouHao(String louHao) {
		LouHao = louHao;
	}

	public String getHuHao() {
		return HuHao;
	}

	public void setHuHao(String huHao) {
		HuHao = huHao;
	}

	public String getHuiFang() {
		return HuiFang;
	}

	public void setHuiFang(String huiFang) {
		HuiFang = huiFang;
	}

	public String getLuoShi() {
		return LuoShi;
	}

	public void setLuoShi(String luoShi) {
		LuoShi = luoShi;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public Timestamp getAddTime() {
		return AddTime;
	}

	public void setAddTime(Timestamp addTime) {
		AddTime = addTime;
	}

	
}