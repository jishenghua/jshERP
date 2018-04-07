package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Material;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;
import org.hibernate.Query;

public class MaterialDAO extends BaseDAO<Material> implements MaterialIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<Material> getEntityClass() {
        return Material.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void batchSetEnable(Boolean enable, String supplierIDs) {
        String sql = "update jsh_material m set m.enabled=" + enable + " where m.id in (" + supplierIDs + ")";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findUnitName(PageUtil<Material> pageUtil, Long mId) throws JshException {
        //多表联查,多表连查，此处用到了createSQLQuery，可以随便写sql语句，很方便,
        StringBuffer queryString = new StringBuffer();
        queryString.append("select jsh_unit.UName  from jsh_unit inner join jsh_material on UnitId=jsh_unit.id where jsh_material.id=" + mId);
        Query query;
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

}
