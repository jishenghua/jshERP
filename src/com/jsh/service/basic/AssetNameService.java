package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.AssetNameIDAO;
import com.jsh.model.po.Assetname;

public class AssetNameService extends BaseService<Assetname> implements AssetNameIService
{
	@SuppressWarnings("unused")
	private AssetNameIDAO assetNameDao;

	public void setAssetNameDao(AssetNameIDAO assetNameDao)
    {
        this.assetNameDao = assetNameDao;
    }

	@Override
	protected Class<Assetname> getEntityClass()
	{
		return Assetname.class;
	}
}
