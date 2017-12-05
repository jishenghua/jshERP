package com.jsh.dao.materials;

import org.hibernate.Query;

import com.jsh.base.BaseDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;

public class DepotHeadDAO extends BaseDAO<DepotHead> implements DepotHeadIDAO {
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
    public void find(PageUtil<DepotHead> pageUtil,String maxid) throws JshException
    {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select max(Id) as Id from DepotHead depotHead where 1=1 " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }
    
    @SuppressWarnings("unchecked")
    public void findAllMoney(PageUtil<DepotHead> pageUtil, Integer supplierId, String type, String subType, String mode) throws JshException
    {
        Query query;
        String modeName = "";
        if(mode.equals("实际")){
        	modeName = "ChangeAmount";
        }
        else if(mode.equals("合计")){
        	modeName = "DiscountLastMoney";
        }
        query= this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select sum(" + modeName + ") as allMoney from DepotHead depotHead where Type='" + type + "' and SubType = '" + subType + "' and OrganId =" + supplierId + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void batchSetStatus(Boolean status,String depotHeadIDs) {
        String sql="update jsh_depothead d set d.Status=" + status + " where d.id in (" + depotHeadIDs + ")";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public void findInDetail(PageUtil pageUtil,String beginTime,String endTime,String type,Long pid,String dids,Long oId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dh.Number,m.`name`,m.Model,di.UnitPrice,di.OperNumber,di.AllPrice,s.supplier,d.dName,date_format(dh.OperTime, '%Y-%m-%d') " +
                "from jsh_depothead dh inner join jsh_depotitem di on di.HeaderId=dh.id " +
                "inner join jsh_material m on m.id=di.MaterialId " +
                "inner join jsh_supplier s on s.id=dh.OrganId " +
                "inner join (select id,name as dName from jsh_depot) d on d.id=di.DepotId " +
                "where dh.OperTime >='"+ beginTime +"' and dh.OperTime <='"+ endTime +"' ");
        if(oId!=null){
            queryString.append(" and dh.OrganId = "+ oId );
        }
        if(pid!=null){
            queryString.append(" and di.DepotId=" + pid );
        }
        else {
            queryString.append(" and di.DepotId in (" + dids + ")" );
        }
        if(type!=null && !type.equals("")) {
            queryString.append(" and dh.Type='"+ type +"'");
        }
        queryString.append(" ORDER BY OperTime DESC,Number desc");
        Query query;
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
    public void findInOutMaterialCount(PageUtil pageUtil,String beginTime,String endTime,String type,Long pid,String dids,Long oId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select di.MaterialId, m.mName,m.Model,m.categoryName, ");
        //数量汇总
        queryString.append(" (select sum(jdi.BasicNumber) numSum from jsh_depothead jdh INNER JOIN jsh_depotitem jdi " +
                "on jdh.id=jdi.HeaderId where jdi.MaterialId=di.MaterialId " +
                " and jdh.type='"+ type +"' and jdh.OperTime >='"+ beginTime +"' and jdh.OperTime <='"+ endTime +"'");
        if(oId!=null){
            queryString.append(" and jdh.OrganId = "+ oId );
        }
        if(pid!=null){
            queryString.append(" and jdi.DepotId=" + pid );
        }
        else {
            queryString.append(" and jdi.DepotId in (" + dids + ")" );
        }
        queryString.append(" ) numSum, ");
        //金额汇总
        queryString.append(" (select sum(jdi.AllPrice) priceSum from jsh_depothead jdh INNER JOIN jsh_depotitem jdi " +
                "on jdh.id=jdi.HeaderId where jdi.MaterialId=di.MaterialId " +
                " and jdh.type='"+ type +"' and jdh.OperTime >='"+ beginTime +"' and jdh.OperTime <='"+ endTime +"'");
        if(oId!=null){
            queryString.append(" and jdh.OrganId = "+ oId );
        }
        if(pid!=null){
            queryString.append(" and jdi.DepotId=" + pid );
        }
        else {
            queryString.append(" and jdi.DepotId in (" + dids + ")" );
        }
        queryString.append(" ) priceSum ");

        queryString.append(" from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId " +
                " INNER JOIN (SELECT jsh_material.id,jsh_material.name mName, Model,jsh_materialcategory.`Name` categoryName from jsh_material INNER JOIN jsh_materialcategory on jsh_material.CategoryId=jsh_materialcategory.Id) m " +
                " on m.Id=di.MaterialId where dh.type='"+ type +"' and dh.OperTime >='"+ beginTime +"' and dh.OperTime <='"+ endTime +"' ");
        if(oId!=null){
            queryString.append(" and dh.OrganId = "+ oId );
        }
        if(pid!=null){
            queryString.append(" and di.DepotId=" + pid );
        }
        else {
            queryString.append(" and di.DepotId in (" + dids + ")" );
        }
        queryString.append(" GROUP BY di.MaterialId,m.mName,m.Model,m.categoryName ");
        Query query;
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
    public void findMaterialsListByHeaderId(PageUtil pageUtil,Long headerId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select group_concat(concat(jsh_material.`Name`,' ',jsh_material.Model)) as mName from jsh_depotitem inner join jsh_material " +
                " on jsh_depotitem.MaterialId = jsh_material.Id where jsh_depotitem.HeaderId ="+ headerId);
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    public void findStatementAccount(PageUtil pageUtil,String beginTime,String endTime,Long organId,String supType) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dh.Number,concat(dh.SubType,dh.Type) as newType,dh.DiscountLastMoney,dh.ChangeAmount,s.supplier,date_format(dh.OperTime,'%Y-%m-%d %H:%i:%S') as oTime from jsh_depothead dh " +
                "inner join jsh_supplier s on s.id=dh.OrganId where s.type='" + supType + "' and dh.SubType!='其它' " +
                "and dh.OperTime >='"+ beginTime +"' and dh.OperTime<='"+ endTime +"' ");
        if(organId!=null && !organId.equals("")) {
            queryString.append(" and dh.OrganId='"+ organId +"' ");
        }
        queryString.append("UNION ALL " +
                "select ah.BillNo,ah.Type as newType,ah.TotalPrice,ah.ChangeAmount,s.supplier,date_format(ah.BillTime,'%Y-%m-%d %H:%i:%S') as oTime from jsh_accounthead ah " +
                "inner join jsh_supplier s on s.id=ah.OrganId where s.type='" + supType + "' " +
                "and ah.BillTime >='"+ beginTime +"' and ah.BillTime<='"+ endTime +"' ");
        if(organId!=null && !organId.equals("")) {
            queryString.append(" and ah.OrganId='"+ organId +"' ");
        }
        queryString.append(" ORDER BY oTime");
        Query query;
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
    public void getHeaderIdByMaterial(PageUtil pageUtil,String materialParam,String depotIds) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dt.HeaderId from jsh_depotitem dt INNER JOIN jsh_material m on dt.MaterialId = m.Id where ( m.`Name` "+
                " like '%" + materialParam + "%' or m.Model like '%" + materialParam + "%') ");
        if(!depotIds.equals("")){
            queryString.append(" and dt.DepotId in (" + depotIds + ") ");
        }
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }
}
