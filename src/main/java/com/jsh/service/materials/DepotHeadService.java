package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.DepotHeadIDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.PageUtil;

public class DepotHeadService extends BaseService<DepotHead> implements DepotHeadIService
{
	@SuppressWarnings("unused")
	private DepotHeadIDAO depotHeadDao;

	
	public void setDepotHeadDao(DepotHeadIDAO depotHeadDao) {
		this.depotHeadDao = depotHeadDao;
	}


	@Override
	protected Class<DepotHead> getEntityClass()
	{
		return DepotHead.class;
	}
	
    @Override
    public void find(PageUtil<DepotHead> pageUtil, String maxid) throws JshException
    {
    	depotHeadDao.find(pageUtil, maxid);
    }
}
