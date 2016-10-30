package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Basicuser;

public class UserDAO extends BaseDAO<Basicuser> implements UserIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
	public Class<Basicuser> getEntityClass()
	{
		return Basicuser.class;
	}
	
    
}
