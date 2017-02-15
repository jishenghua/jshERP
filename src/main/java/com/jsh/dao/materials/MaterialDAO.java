package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Material;

public class MaterialDAO extends BaseDAO<Material> implements MaterialIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<Material> getEntityClass()
    {
        return Material.class;
    }
}
