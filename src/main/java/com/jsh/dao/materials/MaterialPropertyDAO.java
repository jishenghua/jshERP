package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.MaterialProperty;

public class MaterialPropertyDAO extends BaseDAO<MaterialProperty> implements MaterialPropertyIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<MaterialProperty> getEntityClass()
    {
        return MaterialProperty.class;
    }
}
