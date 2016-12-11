package com.jsh.dao.materials;

import org.hibernate.Query;

import com.jsh.base.BaseDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.AccountHead;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;

public class AccountHeadDAO extends BaseDAO<AccountHead> implements AccountHeadIDAO
{
    /**
     * 设置dao映射基类
     * @return
     */
    @Override
    public Class<AccountHead> getEntityClass()
    {
        return AccountHead.class;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void find(PageUtil<AccountHead> pageUtil,String maxid) throws JshException
    {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select max(Id) as Id from AccountHead accountHead where 1=1 " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }
}
