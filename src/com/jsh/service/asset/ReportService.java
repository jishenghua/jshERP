package com.jsh.service.asset;

import com.jsh.dao.asset.ReportIDAO;
import com.jsh.exception.AmsException;
import com.jsh.model.po.Asset;
import com.jsh.util.common.PageUtil;

public class ReportService implements ReportIService
{
    private ReportIDAO reportDao;

    public void setReportDao(ReportIDAO reportDao)
    {
        this.reportDao = reportDao;
    }

    @Override
    public void find(PageUtil<Asset> pageUtil, String reportType,String reportName) throws AmsException
    {
        reportDao.find(pageUtil, reportType,reportName);
    }
    
}
