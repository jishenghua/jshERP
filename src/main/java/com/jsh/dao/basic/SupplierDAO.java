package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Supplier;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;
import org.hibernate.Query;

public class SupplierDAO extends BaseDAO<Supplier> implements SupplierIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
	public Class<Supplier> getEntityClass()
	{
		return Supplier.class;
	}

}
