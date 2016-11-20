package com.jsh.service.asset;

import com.jsh.exception.JshException;
import com.jsh.model.po.Asset;
import com.jsh.util.common.PageUtil;

public interface ReportIService
{
    /**
     * 查找报表数据
     * @param asset
     * @throws JshException
     */
    void find(PageUtil<Asset> asset,String reportType,String reportName)throws JshException;
}
