package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.MaterialPropertyIDAO;
import com.jsh.model.po.MaterialProperty;

public class MaterialPropertyService extends BaseService<MaterialProperty> implements MaterialPropertyIService {
    @SuppressWarnings("unused")
    private MaterialPropertyIDAO materialPropertyDao;


    public void setMaterialPropertyDao(MaterialPropertyIDAO materialPropertyDao) {
        this.materialPropertyDao = materialPropertyDao;
    }


    @Override
    protected Class<MaterialProperty> getEntityClass() {
        return MaterialProperty.class;
    }

}
