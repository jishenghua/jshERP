package com.jsh.dao.asset;

import com.jsh.exception.AmsException;
import com.jsh.model.po.Asset;
import com.jsh.util.common.PageUtil;

public interface ReportIDAO
{
    /**
     * 查找资产列表
     * @param pageUtil 分页工具类
     * @param reportType 报表统计字段
     * @throws AmsException
     */
    void find(PageUtil<Asset> pageUtil,String reportType,String reportName) throws AmsException;
}
