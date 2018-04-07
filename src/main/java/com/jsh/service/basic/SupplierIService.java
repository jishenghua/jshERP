package com.jsh.service.basic;

import com.jsh.base.BaseIService;
import com.jsh.model.po.Supplier;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

import java.io.File;
import java.io.InputStream;

public interface SupplierIService extends BaseIService<Supplier> {
    public void batchSetEnable(Boolean enable, String supplierIDs);

    public InputStream exmportExcel(String isAllPage, PageUtil<Supplier> pageUtil) throws JshException;

    public InputStream importExcel(File assetFile) throws JshException;
}
