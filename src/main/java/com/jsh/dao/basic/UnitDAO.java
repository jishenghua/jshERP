package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Unit;

public class UnitDAO extends BaseDAO<Unit> implements UnitIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<Unit> getEntityClass() {
        return Unit.class;
    }
}
