package com.jsh.service.asset;

import com.jsh.exception.AmsException;
import com.jsh.model.po.Asset;
import com.jsh.util.common.PageUtil;

public interface ReportIService
{
    /**
     * 查找报表数据
     * @param asset
     * @throws AmsException
     */
    void find(PageUtil<Asset> asset,String reportType,String reportName)throws AmsException;
}
