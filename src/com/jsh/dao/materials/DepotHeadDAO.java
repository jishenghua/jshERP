package com.jsh.dao.materials;

import org.hibernate.Query;

import com.jsh.base.BaseDAO;
import com.jsh.exception.AmsException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.common.PageUtil;
import com.jsh.util.common.SearchConditionUtil;

public class DepotHeadDAO extends BaseDAO<DepotHead> implements DepotHeadIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<DepotHead> getEntityClass()
    {
        return DepotHead.class;
    }
	
    @SuppressWarnings("unchecked")
    @Override
	public void find(PageUtil<DepotHead> pageUtil,String maxid) throws AmsException
    {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select max(Id) as Id from DepotHead depotHead where 1=1 " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }
}
