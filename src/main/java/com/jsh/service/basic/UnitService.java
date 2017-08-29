package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.UnitIDAO;
import com.jsh.model.po.Unit;

public class UnitService extends BaseService<Unit> implements UnitIService
{
	@SuppressWarnings("unused")
	private UnitIDAO unitDao;


	public void setUnitDao(UnitIDAO unitDao)
	{
		this.unitDao = unitDao;
	}

	@Override
	protected Class<Unit> getEntityClass()
	{
		return Unit.class;
	}
    
}
