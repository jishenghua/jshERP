package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.MaterialIDAO;
import com.jsh.model.po.Material;

public class MaterialService extends BaseService<Material> implements MaterialIService
{
	@SuppressWarnings("unused")
	private MaterialIDAO materialDao;

	
	public void setMaterialDao(MaterialIDAO materialDao) {
		this.materialDao = materialDao;
	}


	@Override
	protected Class<Material> getEntityClass()
	{
		return Material.class;
	}
    
}
