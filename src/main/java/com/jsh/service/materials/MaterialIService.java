package com.jsh.service.materials;

import com.jsh.base.BaseIService;
import com.jsh.model.po.Material;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

import java.io.File;
import java.io.InputStream;

public interface MaterialIService extends BaseIService<Material> {
    public void batchSetEnable(Boolean enable, String supplierIDs);

    public void findUnitName(PageUtil<Material> material, Long mId) throws JshException;

    public InputStream exmportExcel(String isAllPage, PageUtil<Material> pageUtil) throws JshException;

    public InputStream importExcel(File materialFile) throws JshException;
}
