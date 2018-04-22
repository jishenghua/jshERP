package com.jsh.dao.asset;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Asset;

public class AssetDAO extends BaseDAO<Asset> implements AssetIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<Asset> getEntityClass() {
        return Asset.class;
    }
}
