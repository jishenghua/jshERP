package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.MaterialCategory;

public class MaterialCategoryDAO extends BaseDAO<MaterialCategory> implements MaterialCategoryIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<MaterialCategory> getEntityClass()
    {
        return MaterialCategory.class;
    }
}
