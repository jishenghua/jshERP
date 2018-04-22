package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;
import org.hibernate.Query;

public class UserBusinessDAO extends BaseDAO<UserBusiness> implements UserBusinessIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<UserBusiness> getEntityClass() {
        return UserBusiness.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void find(PageUtil<UserBusiness> pageUtil, String ceshi) throws JshException {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select count(id),sum(id) from UserBusiness userBusiness where 1=1 " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }
}
