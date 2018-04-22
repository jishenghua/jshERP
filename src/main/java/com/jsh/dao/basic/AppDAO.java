package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.App;

public class AppDAO extends BaseDAO<App> implements AppIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<App> getEntityClass() {
        return App.class;
    }
}
