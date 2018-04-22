package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.SystemConfig;

public class SystemConfigDAO extends BaseDAO<SystemConfig> implements SystemConfigIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<SystemConfig> getEntityClass() {
        return SystemConfig.class;
    }
}
