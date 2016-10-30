package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.BuildingIDAO;
import com.jsh.model.po.Building;

public class BuildingService extends BaseService<Building> implements BuildingIService
{
	@SuppressWarnings("unused")
	private BuildingIDAO buildingDao;

	
	public void setBuildingDao(BuildingIDAO buildingDao) {
		this.buildingDao = buildingDao;
	}


	@Override
	protected Class<Building> getEntityClass()
	{
		return Building.class;
	}
    
}
