package com.jsh.model.vo.asset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"serial","rawtypes"})
public class ReportShowModel implements Serializable
{
    //保存报表数据
	private List reportData = new ArrayList();
    //保存提示信息
    private String msgTip = "";
    
    public List getReportData()
    {
        return reportData;
    }
    
	public void setReportData(List reportData)
    {
        this.reportData = reportData;
    }
    
    public String getMsgTip()
    {
        return msgTip;
    }
    
    public void setMsgTip(String msgTip)
    {
        this.msgTip = msgTip;
    }
}
