package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.RoleIDAO;
import com.jsh.dao.basic.UserBusinessIDAO;
import com.jsh.model.po.Role;

public class RoleService extends BaseService<Role> implements RoleIService
{
	@SuppressWarnings("unused")
	private RoleIDAO roleDao;
	@SuppressWarnings("unused")
	private UserBusinessIDAO userBusinessDao;

	public void setRoleDao(RoleIDAO roleDao) 
	{
		this.roleDao = roleDao;
	}
	
	public void setUserBusinessDao(UserBusinessIDAO userBusinessDao) {
		this.userBusinessDao = userBusinessDao;
	}
	
	@Override
	protected Class<Role> getEntityClass()
	{
		return Role.class;
	}
    
}
