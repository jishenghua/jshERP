package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Depot;

public class DepotDAO extends BaseDAO<Depot> implements DepotIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<Depot> getEntityClass() {
        return Depot.class;
    }
}
