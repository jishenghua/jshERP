package com.jsh.service.asset;

import com.jsh.dao.asset.ReportIDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.Asset;
import com.jsh.util.PageUtil;

public class ReportService implements ReportIService
{
    private ReportIDAO reportDao;

    public void setReportDao(ReportIDAO reportDao)
    {
        this.reportDao = reportDao;
    }

    @Override
    public void find(PageUtil<Asset> pageUtil, String reportType,String reportName) throws JshException
    {
        reportDao.find(pageUtil, reportType,reportName);
    }
    
}
