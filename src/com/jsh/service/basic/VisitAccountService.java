package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.VisitAccountIDAO;
import com.jsh.model.po.VisitAccount;

public class VisitAccountService extends BaseService<VisitAccount> implements VisitAccountIService
{
	@SuppressWarnings("unused")
	private VisitAccountIDAO visitAccountDao;

	
	public void setVisitAccountDao(VisitAccountIDAO visitAccountDao) {
		this.visitAccountDao = visitAccountDao;
	}


	@Override
	protected Class<VisitAccount> getEntityClass()
	{
		return VisitAccount.class;
	}
    
}
