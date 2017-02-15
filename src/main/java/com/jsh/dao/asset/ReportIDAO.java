package com.jsh.dao.asset;

import com.jsh.util.JshException;
import com.jsh.model.po.Asset;
import com.jsh.util.PageUtil;

public interface ReportIDAO
{
    /**
     * 查找资产列表
     * @param pageUtil 分页工具类
     * @param reportType 报表统计字段
     * @throws JshException
     */
    void find(PageUtil<Asset> pageUtil,String reportType,String reportName) throws JshException;
}
