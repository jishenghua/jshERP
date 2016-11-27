package com.jsh.dao.materials;

import org.hibernate.Query;

import com.jsh.base.BaseDAO;
import com.jsh.exception.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.util.common.PageUtil;
import com.jsh.util.common.SearchConditionUtil;

public class DepotItemDAO extends BaseDAO<DepotItem> implements DepotItemIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<DepotItem> getEntityClass()
    {
        return DepotItem.class;
    }
	
    @SuppressWarnings("unchecked")
    @Override
	public void findByType(PageUtil<DepotItem> pageUtil,String type,Long MId,String MonthTime,Boolean isPrev) throws JshException
    {
    	//多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
    	Query query;
    	if(isPrev) {
    		query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery("select sum(OperNumber) as OperNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='" + type +"' and MaterialId ="+ MId + " and jsh_depothead.OperTime <'"+ MonthTime +"-01 00:00:00' " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
    	}
    	else {
    		query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery("select sum(OperNumber) as OperNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='" + type +"' and MaterialId ="+ MId + " and jsh_depothead.OperTime >='"+ MonthTime +"-01 00:00:00' and jsh_depothead.OperTime <='"+ MonthTime +"-31 00:00:00' " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
    	}        
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }
}
