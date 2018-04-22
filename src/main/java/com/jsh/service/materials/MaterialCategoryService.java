package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.MaterialCategoryIDAO;
import com.jsh.model.po.MaterialCategory;

public class MaterialCategoryService extends BaseService<MaterialCategory> implements MaterialCategoryIService {
    @SuppressWarnings("unused")
    private MaterialCategoryIDAO materialCategoryDao;


    public void setMaterialCategoryDao(MaterialCategoryIDAO materialCategoryDao) {
        this.materialCategoryDao = materialCategoryDao;
    }


    @Override
    protected Class<MaterialCategory> getEntityClass() {
        return MaterialCategory.class;
    }

}
