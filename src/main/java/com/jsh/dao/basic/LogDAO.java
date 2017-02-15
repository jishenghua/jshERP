package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Logdetails;

public class LogDAO extends BaseDAO<Logdetails> implements LogIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
	public Class<Logdetails> getEntityClass()
	{
		return Logdetails.class;
	}

}
