package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Role;

public class RoleDAO extends BaseDAO<Role> implements RoleIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<Role> getEntityClass()
    {
        return Role.class;
    }
}
