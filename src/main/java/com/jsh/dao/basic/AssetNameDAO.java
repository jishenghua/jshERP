package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Assetname;

public class AssetNameDAO extends BaseDAO<Assetname> implements AssetNameIDAO
{
	/**
     * 设置dao映射基类
     * @return
     */
	@Override
    public Class<Assetname> getEntityClass()
    {
        return Assetname.class;
    }
}
