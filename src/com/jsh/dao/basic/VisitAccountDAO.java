package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.VisitAccount;

public class VisitAccountDAO extends BaseDAO<VisitAccount> implements VisitAccountIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<VisitAccount> getEntityClass()
    {
        return VisitAccount.class;
    }
}
