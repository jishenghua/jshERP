package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.DepotItem;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;
import org.hibernate.Query;

public class DepotItemDAO extends BaseDAO<DepotItem> implements DepotItemIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<DepotItem> getEntityClass() {
        return DepotItem.class;
    }

    private final static String TYPE = "入库";

    @SuppressWarnings("unchecked")
    @Override
    public void findByType(PageUtil<DepotItem> pageUtil, String type, Integer dId, Long MId, String MonthTime, Boolean isPrev) throws JshException {
        //多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
        Query query;
        StringBuilder queryString = new StringBuilder();
        if (TYPE.equals(type)) {
            if (isPrev) {
                queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem di,jsh_depothead dh where di.HeaderId = dh.id ");
                queryString.append(" and ((type='入库' and DepotId='").append(dId).append("') ").append(" or (SubType='调拨' and AnotherDepotId='").append(dId).append("') ").append(" or (SubType='礼品充值' and AnotherDepotId='").append(dId).append("')) ");
                queryString.append(" and MaterialId =").append(MId).append(" and dh.OperTime <'").append(MonthTime).append("-01 00:00:00' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            } else {
                queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem di,jsh_depothead dh  where di.HeaderId = dh.id ");
                queryString.append(" and ((type='入库' and DepotId='").append(dId).append("') ").append(" or (SubType='调拨' and AnotherDepotId='").append(dId).append("') ").append(" or (SubType='礼品充值' and AnotherDepotId='").append(dId).append("')) ");
                queryString.append(" and MaterialId =").append(MId).append(" and dh.OperTime >='").append(MonthTime).append("-01 00:00:00' and dh.OperTime <='").append(MonthTime).append("-31 59:59:59' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            }
        } else {
            if (isPrev) {
                queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
                queryString.append(" and DepotId='").append(dId).append("'");
                queryString.append(" and MaterialId =").append(MId).append(" and jsh_depothead.OperTime <'").append(MonthTime).append("-01 00:00:00' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            } else {
                queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
                queryString.append(" and DepotId='").append(dId).append("'");
                queryString.append(" and MaterialId =").append(MId).append(" and jsh_depothead.OperTime >='").append(MonthTime).append("-01 00:00:00' and jsh_depothead.OperTime <='").append(MonthTime).append("-31 59:59:59' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            }
        }
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findPriceByType(PageUtil<DepotItem> pageUtil, String type, Integer dId, Long MId, String MonthTime, Boolean isPrev) throws JshException {
        //多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
        Query query;
        StringBuilder queryString = new StringBuilder();
        if (TYPE.equals(type)) {
            if (isPrev) {
                queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem di,jsh_depothead dh where di.HeaderId = dh.id ");
                queryString.append(" and ((type='入库' and DepotId='").append(dId).append("') ").append(" or (SubType='调拨' and AnotherDepotId='").append(dId).append("') ").append(" or (SubType='礼品充值' and AnotherDepotId='").append(dId).append("')) ");
                queryString.append(" and MaterialId =").append(MId).append(" and dh.OperTime <'").append(MonthTime).append("-01 00:00:00' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            } else {
                queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem di,jsh_depothead dh  where di.HeaderId = dh.id ");
                queryString.append(" and ((type='入库' and DepotId='").append(dId).append("') ").append(" or (SubType='调拨' and AnotherDepotId='").append(dId).append("') ").append(" or (SubType='礼品充值' and AnotherDepotId='").append(dId).append("')) ");
                queryString.append(" and MaterialId =").append(MId).append(" and dh.OperTime >='").append(MonthTime).append("-01 00:00:00' and dh.OperTime <='").append(MonthTime).append("-31 59:59:59' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            }
        } else {
            if (isPrev) {
                queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
                queryString.append(" and DepotId='").append(dId).append("'");
                queryString.append(" and MaterialId =").append(MId).append(" and jsh_depothead.OperTime <'").append(MonthTime).append("-01 00:00:00' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            } else {
                queryString.append("select sum(AllPrice) as AllPrice from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='出库'");
                queryString.append(" and DepotId='").append(dId).append("'");
                queryString.append(" and MaterialId =").append(MId).append(" and jsh_depothead.OperTime >='").append(MonthTime).append("-01 00:00:00' and jsh_depothead.OperTime <='").append(MonthTime).append("-31 59:59:59' ");
                query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
            }
        }
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findByTypeAndMaterialId(PageUtil<DepotItem> pageUtil, String type, Long MId) throws JshException {
        //多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
        Query query;
        StringBuilder queryString = new StringBuilder();
        if (TYPE.equals(type)) {
            queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId where type='入库'");
            queryString.append(" and MaterialId =").append(MId);
            query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        } else {
            queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId where type='出库'");
            queryString.append(" and SubType!='调拨' and SubType!='礼品充值' and MaterialId =").append(MId);
            query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        }
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findDetailByTypeAndMaterialId(PageUtil<DepotItem> pageUtil, Long MId) throws JshException {
        //多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
        Query query;
        StringBuilder queryString = new StringBuilder();
        queryString.append("select dh.Number,concat(dh.SubType,dh.Type) as newType, " +
                "case when type='入库' then di.BasicNumber when type='出库' then 0-di.BasicNumber else 0 end as b_num, " +
                "date_format(dh.OperTime,'%Y-%m-%d %H:%i:%S') as oTime " +
                "from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId where type!='其它' " +
                "and SubType!='调拨' and SubType!='礼品充值' ");
        queryString.append(" and MaterialId =").append(MId).append(" ORDER BY oTime desc ");
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

    private final static String SUM_TYPE = "Number";

    @SuppressWarnings("unchecked")
    @Override
    public void buyOrSale(PageUtil<DepotItem> pageUtil, String type, String subType, Long MId, String MonthTime, String sumType) throws JshException {
        //多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
        Query query;
        if (SUM_TYPE.equals(sumType)) {
            query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='" + type + "' and subType='" + subType + "' and MaterialId =" + MId + " and jsh_depothead.OperTime >='" + MonthTime + "-01 00:00:00' and jsh_depothead.OperTime <='" + MonthTime + "-31 59:59:59' " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        } else {
            query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery("select sum(AllPrice) as AllPrice from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and type='" + type + "' and subType='" + subType + "' and MaterialId =" + MId + " and jsh_depothead.OperTime >='" + MonthTime + "-01 00:00:00' and jsh_depothead.OperTime <='" + MonthTime + "-31 59:59:59' " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        }
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    private final static String IN = "in";
    private final static String OUT = "out";

    @SuppressWarnings("unchecked")
    @Override
    public void findGiftByType(PageUtil<DepotItem> pageUtil, String subType, Integer ProjectId, Long MId, String type) throws JshException {
        //多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便
        Query query;
        StringBuilder queryString = new StringBuilder();
        queryString.append("select sum(BasicNumber) as BasicNumber from jsh_depotitem,jsh_depothead  where jsh_depotitem.HeaderId = jsh_depothead.id and jsh_depothead.SubType='").append(subType).append("'");
        if (ProjectId != null) {
            if (IN.equals(type)) {
                queryString.append(" and jsh_depotitem.AnotherDepotId='").append(ProjectId).append("'"); //礼品充值时
            } else if (OUT.equals(type)) {
                queryString.append(" and  jsh_depotitem.DepotId='").append(ProjectId).append("'");
            }
        }
        queryString.append(" and jsh_depotitem.MaterialId =").append(MId);
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }
}



