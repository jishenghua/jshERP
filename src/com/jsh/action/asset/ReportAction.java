package com.jsh.action.asset;

import java.util.HashMap;
import java.util.Map;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.exception.AmsException;
import com.jsh.model.po.Asset;
import com.jsh.model.vo.asset.ReportModel;
import com.jsh.service.asset.ReportIService;
import com.jsh.util.common.PageUtil;

@SuppressWarnings("serial")
public class ReportAction extends BaseAction<ReportModel>
{
    private ReportModel model = new ReportModel();
    private ReportIService reportService;
    
    /**
     * 查找资产信息
     * @return
     */
    public String find()
    {
        try 
        {
            PageUtil<Asset> pageUtil = new  PageUtil<Asset>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition());
            String reportType = getReportType(new HashMap<String,Object>());
            reportService.find(pageUtil,reportType.split("_")[0],reportType.split("_")[1]);
            model.getShowModel().setReportData(pageUtil.getPageList());
        } 
        catch (AmsException e) 
        {
            Log.errorFileSync(">>>>>>>>>查找资产信息异常", e);
            model.getShowModel().setMsgTip("get report data exception");
        } 
        return SUCCESS;
    }
    
    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getCondition()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("assetname.id_n_eq", model.getAssetNameID());
        condition.put("assetname.category.id_n_eq", model.getAssetCategoryID());
        condition.put("user.id_n_eq", model.getUsernameID());
        condition.put("status_n_eq", model.getStatus());
        condition.put("supplier.id_n_eq", model.getSupplierID());
        condition.put("dataSum_s_order","desc");
        //拼接统计数据条件
        getReportType(condition);
        return condition;
    }
    
    /**
     * 获取统计条件
     * @param condition
     */
    private String getReportType(Map<String,Object> condition)
    {
//        <option value="0">资产状态</option> 
//        <option value="1">资产类型</option> 
//        <option value="2">供应商</option>  
//        <option value="3">资产名称</option> 
//        <option value="4">所属用户</option>
        int reportType = model.getReportType();
        String reportTypeInfo = "";
        String reportTypeName = "";
        switch(reportType)
        {
            case 0:
                condition.put("status_s_gb", "group");
                reportTypeInfo = "status";
                reportTypeName = "status";
                break;
            case 1:
                condition.put("assetname.category.id_s_gb", "group");
                reportTypeInfo = "assetname.category.id";
                reportTypeName = "assetname.category.assetname";
                break;
            case 2:
                condition.put("supplier.id_s_gb", "group");
                reportTypeInfo = "supplier.id";
                reportTypeName = "supplier.supplier";
                break;
            case 3:
                condition.put("assetname.id_s_gb", "group");
                reportTypeInfo = "assetname.id";
                reportTypeName = "assetname.assetname";
                break;
            case 4:
                condition.put("user.id_s_gb", "group");
                reportTypeInfo = "user.id";
                reportTypeName = "user.username";
                break;
        }
        return reportTypeInfo + "_" + reportTypeName;
    }
    //=========Spring注入以及model驱动公共方法===========
    public void setReportService(ReportIService reportService)
    {
        this.reportService = reportService;
    }

	@Override
	public ReportModel getModel()
	{
		return model;
	}
}
