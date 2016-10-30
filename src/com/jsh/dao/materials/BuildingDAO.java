package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Building;

public class BuildingDAO extends BaseDAO<Building> implements BuildingIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<Building> getEntityClass()
    {
        return Building.class;
    }
}
