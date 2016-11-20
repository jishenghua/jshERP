package com.jsh.dao.basic;

import org.hibernate.Query;

import com.jsh.base.BaseDAO;
import com.jsh.exception.JshException;
import com.jsh.model.po.Asset;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.common.PageUtil;
import com.jsh.util.common.SearchConditionUtil;

public class UserBusinessDAO extends BaseDAO<UserBusiness> implements UserBusinessIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<UserBusiness> getEntityClass()
    {
        return UserBusiness.class;
    }
	
    @SuppressWarnings("unchecked")
    @Override
	public void find(PageUtil<UserBusiness> pageUtil,String ceshi) throws JshException
    {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select count(id),sum(id) from UserBusiness userBusiness where 1=1 " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }
}
