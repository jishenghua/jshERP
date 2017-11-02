package com.jsh.dao.materials;

import org.hibernate.Query;

import com.jsh.base.BaseDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;

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
	public void findByType(PageUtil<DepotItem> pageUtil,String type,Integer dId,Long MId,String MonthTime,Boolean isPrev) throws JshException {
    	//多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
    	Query query;
		StringBuffer queryString = new StringBuffer();
		if(type.equals("入库")) {
			if (isPrev) {
				queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem di,jsh_depothead dh where di.HeaderId = dh.id ");
				queryString.append(" and ((type='入库' and DepotId='" + dId + "') " +
						" or (SubType='调拨' and AnotherDepotId='" + dId + "') " +
						" or (SubType='礼品充值' and AnotherDepotId='" + dId + "')) ");
				queryString.append(" and MaterialId =" + MId + " and dh.OperTime <'" + MonthTime + "-01 00:00:00' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			} else {
				queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem di,jsh_depothead dh  where di.HeaderId = dh.id ");
				queryString.append(" and ((type='入库' and DepotId='" + dId + "') " +
						" or (SubType='调拨' and AnotherDepotId='" + dId + "') " +
						" or (SubType='礼品充值' and AnotherDepotId='" + dId + "')) ");
				queryString.append(" and MaterialId =" + MId + " and dh.OperTime >='" + MonthTime + "-01 00:00:00' and dh.OperTime <='" + MonthTime + "-31 59:59:59' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			}
		}
		else {
			if (isPrev) {
				queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
				queryString.append(" and DepotId='" + dId + "'");
				queryString.append(" and MaterialId =" + MId + " and jsh_depothead.OperTime <'" + MonthTime + "-01 00:00:00' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			} else {
				queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
				queryString.append(" and DepotId='" + dId + "'");
				queryString.append(" and MaterialId =" + MId + " and jsh_depothead.OperTime >='" + MonthTime + "-01 00:00:00' and jsh_depothead.OperTime <='" + MonthTime + "-31 59:59:59' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			}
		}
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

	@SuppressWarnings("unchecked")
	@Override
	public void findPriceByType(PageUtil<DepotItem> pageUtil,String type,Integer dId,Long MId,String MonthTime,Boolean isPrev) throws JshException
	{
		//多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
		Query query;
		StringBuffer queryString = new StringBuffer();
		if(type.equals("入库")) {
			if (isPrev) {
				queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem di,jsh_depothead dh where di.HeaderId = dh.id ");
				queryString.append(" and ((type='入库' and DepotId='" + dId + "') " +
						" or (SubType='调拨' and AnotherDepotId='" + dId + "') " +
						" or (SubType='礼品充值' and AnotherDepotId='" + dId + "')) ");
				queryString.append(" and MaterialId =" + MId + " and dh.OperTime <'" + MonthTime + "-01 00:00:00' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			} else {
				queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem di,jsh_depothead dh  where di.HeaderId = dh.id ");
				queryString.append(" and ((type='入库' and DepotId='" + dId + "') " +
						" or (SubType='调拨' and AnotherDepotId='" + dId + "') " +
						" or (SubType='礼品充值' and AnotherDepotId='" + dId + "')) ");
				queryString.append(" and MaterialId =" + MId + " and dh.OperTime >='" + MonthTime + "-01 00:00:00' and dh.OperTime <='" + MonthTime + "-31 59:59:59' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			}
		}
		else {
			if (isPrev) {
				queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
				queryString.append(" and DepotId='" + dId + "'");
				queryString.append(" and MaterialId =" + MId + " and jsh_depothead.OperTime <'" + MonthTime + "-01 00:00:00' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			} else {
				queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
				queryString.append(" and DepotId='" + dId + "'");
				queryString.append(" and MaterialId =" + MId + " and jsh_depothead.OperTime >='" + MonthTime + "-01 00:00:00' and jsh_depothead.OperTime <='" + MonthTime + "-31 59:59:59' ");
				query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
			}
		}
		pageUtil.setTotalCount(query.list().size());
		pageUtil.setPageList(query.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void findByTypeAndMaterialId(PageUtil<DepotItem> pageUtil,String type,Long MId) throws JshException {
		//多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
		Query query;
		StringBuffer queryString = new StringBuffer();
		if(type.equals("入库")) {
			queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId where type='入库'");
			queryString.append(" and MaterialId ="+ MId);
			query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
		}
		else {
			queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId where type='出库'");
			queryString.append(" and SubType!='调拨' and SubType!='礼品充值' and MaterialId ="+ MId);
			query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
		}
		pageUtil.setTotalCount(query.list().size());
		pageUtil.setPageList(query.list());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void findDetailByTypeAndMaterialId(PageUtil<DepotItem> pageUtil,Long MId) throws JshException {
		//多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
		Query query;
		StringBuffer queryString = new StringBuffer();
		queryString.append("select dh.Number,concat(dh.SubType,dh.Type) as newType,di.BasicNumber,date_format(dh.OperTime,'%Y-%m-%d %H:%i:%S') as oTime from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId where type='入库' ");
		queryString.append(" and MaterialId ="+ MId);
		queryString.append(" union all ");
		queryString.append("select dh.Number,concat(dh.SubType,dh.Type) as newType,0-di.BasicNumber,date_format(dh.OperTime,'%Y-%m-%d %H:%i:%S') as oTime from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId where type='出库' ");
		queryString.append(" and SubType!='调拨' and SubType!='礼品充值' and MaterialId ="+ MId);
		query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
		pageUtil.setTotalCount(query.list().size());
		// 分页查询
		int pageNo = pageUtil.getCurPage();
		int pageSize = pageUtil.getPageSize();
		if (0 != pageNo && 0 != pageSize) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		pageUtil.setPageList(query.list());
	}
    
    @SuppressWarnings("unchecked")
    @Override
	public void buyOrSale(PageUtil<DepotItem> pageUtil,String type, String subType,Long MId,String MonthTime, String sumType) throws JshException
    {
    	//多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
    	Query query;
    	if(sumType.equals("Number")) {
    		query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='" + type +"' and subType='" + subType +"' and MaterialId ="+ MId + " and jsh_depothead.OperTime >='"+ MonthTime +"-01 00:00:00' and jsh_depothead.OperTime <='"+ MonthTime +"-31 59:59:59' " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
    	}
    	else {
    		query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery("select sum(AllPrice) as AllPrice from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='" + type +"' and subType='" + subType +"' and MaterialId ="+ MId + " and jsh_depothead.OperTime >='"+ MonthTime +"-01 00:00:00' and jsh_depothead.OperTime <='"+ MonthTime +"-31 59:59:59' " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        }
    	pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

	@SuppressWarnings("unchecked")
	@Override
	public void findGiftByType(PageUtil<DepotItem> pageUtil,String subType,Integer ProjectId,Long MId,String type) throws JshException
	{
		//多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
		Query query;
		StringBuffer queryString = new StringBuffer();
		queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and jsh_depothead.SubType='" + subType +"'");
		if(ProjectId!=null) {
			if(type.equals("in")){
				queryString.append(" and jsh_depotitem.AnotherDepotId='" + ProjectId +"'"); //礼品充值时
			}
			else if(type.equals("out")){
				queryString.append(" and  jsh_depotitem.DepotId='" + ProjectId +"'");
			}
		}
		queryString.append(" and jsh_depotitem.MaterialId ="+ MId);
		query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
		pageUtil.setTotalCount(query.list().size());
		pageUtil.setPageList(query.list());
	}
}



