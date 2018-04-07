package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Functions;

public class FunctionsDAO extends BaseDAO<Functions> implements FunctionsIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<Functions> getEntityClass() {
        return Functions.class;
    }
}
