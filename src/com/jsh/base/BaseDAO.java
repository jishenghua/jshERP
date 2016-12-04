package com.jsh.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jsh.util.common.PageUtil;
import com.jsh.util.common.SearchConditionUtil;
/**
 * 基础dao
 * @author ji_sheng_hua
 */
public class BaseDAO<T> extends HibernateDaoSupport implements BaseIDAO<T>
{
	protected Class<T> entityClass;

	public void setPoJoClass(Class<T> c)
	{
		this.entityClass = c;
	}

	protected Class<T> getEntityClass()
	{
		return this.entityClass;
	}

	@Override
	public Serializable create(T t) throws DataAccessException
	{
		return this.getHibernateTemplate().save(t);
	}

	@Override
	public void delete(T t) throws DataAccessException
	{
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public T get(Long objID) throws DataAccessException
	{
		return (T) this.getHibernateTemplate().get(getEntityClass(), objID);
	}

	@Override
	public void update(T t) throws DataAccessException
	{
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void batchDelete(String objIDs) throws DataAccessException
	{
		this.getHibernateTemplate().bulkUpdate("delete from " + getEntityClass().getName() + " where id in ("+ objIDs + ")");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void find(PageUtil<T> pageUtil) throws DataAccessException
	{
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery(" from " + getEntityClass().getName() + " where 1=1 "+
						SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
		pageUtil.setTotalCount(query.list().size());

		// 分页查询
		int pageNo = pageUtil.getCurPage();
		int pageSize = pageUtil.getPageSize();
		if (0 != pageNo && 0 != pageSize)
		{
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		pageUtil.setPageList(query.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(Map<String, Object> conditon)throws DataAccessException
	{
		return this.getHibernateTemplate().find(" from " + getEntityClass().getName() + " where 1=1 "+ SearchConditionUtil.getCondition(conditon));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql) throws DataAccessException
	{
		return this.getHibernateTemplate().find(" from " + getEntityClass().getName() + " where 1=1 "+ hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(Map<String, Object> conditon, int pageSize, int pageNo)throws DataAccessException
	{
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery(" from " + getEntityClass().getName() + " where 1=1 "+ SearchConditionUtil.getCondition(conditon));
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, int pageSize, int pageNo)throws DataAccessException
	{
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery(" from " + getEntityClass().getName() + " where 1=1 "+ hql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer countSum(Map<String, Object> conditon)throws DataAccessException
	{
		List<T> dataList = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery(" from " + getEntityClass().getName() + " where 1=1 "+ SearchConditionUtil.getCondition(conditon)).list();
		return dataList ==null?0:dataList.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer countSum(String hql) throws DataAccessException
	{
		List<T> dataList = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createQuery(" from " + getEntityClass().getName() + " where 1=1 "+ hql).list();
		return dataList ==null?0:dataList.size();
	}

    @Override
    public void save(T t) throws DataAccessException
    {
        this.getHibernateTemplate().save(t);
    }
}
