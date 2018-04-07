package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.SystemConfigIDAO;
import com.jsh.model.po.SystemConfig;

public class SystemConfigService extends BaseService<SystemConfig> implements SystemConfigIService {
    @SuppressWarnings("unused")
    private SystemConfigIDAO systemConfigDao;

    public void setSystemConfigDao(SystemConfigIDAO systemConfigDao) {
        this.systemConfigDao = systemConfigDao;
    }

    @Override
    protected Class<SystemConfig> getEntityClass() {
        return SystemConfig.class;
    }

}
