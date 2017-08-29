package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.model.po.Material;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface MaterialIDAO extends BaseIDAO<Material>
{
    public void batchSetEnable(Boolean enable,String supplierIDs);

    public void findUnitName(PageUtil<Material> pageUtil,Long mId) throws JshException;
}
