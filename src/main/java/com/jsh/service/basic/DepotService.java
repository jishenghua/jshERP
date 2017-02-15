package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.DepotIDAO;
import com.jsh.dao.basic.UserBusinessIDAO;
import com.jsh.model.po.Depot;

public class DepotService extends BaseService<Depot> implements DepotIService
{
	@SuppressWarnings("unused")
	private DepotIDAO depotDao;
	@SuppressWarnings("unused")
	private UserBusinessIDAO userBusinessDao;


	public void setDepotDao(DepotIDAO depotDao) 
	{
		this.depotDao = depotDao;
	}
	
	public void setUserBusinessDao(UserBusinessIDAO userBusinessDao) {
		this.userBusinessDao = userBusinessDao;
	}


	@Override
	protected Class<Depot> getEntityClass()
	{
		return Depot.class;
	}
    
}
