package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.SupplierIDAO;
import com.jsh.model.po.Supplier;
import com.jsh.util.JshException;

public class SupplierService extends BaseService<Supplier> implements SupplierIService
{
	@SuppressWarnings("unused")
	private SupplierIDAO supplierDao;
	
	public void setSupplierDao(SupplierIDAO supplierDao)
	{
		this.supplierDao = supplierDao;
	}
	/**
     * 设置映射基类
     * @return
     */
	@Override
	protected Class<Supplier> getEntityClass()
    {
        return Supplier.class;
    }

}
