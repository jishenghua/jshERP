package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Supplier;
import org.hibernate.Query;

public class SupplierDAO extends BaseDAO<Supplier> implements SupplierIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<Supplier> getEntityClass() {
        return Supplier.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void batchSetEnable(Boolean enable, String supplierIDs) {
        String sql = "update jsh_supplier s set s.enabled=" + enable + " where s.id in (" + supplierIDs + ")";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }
}
