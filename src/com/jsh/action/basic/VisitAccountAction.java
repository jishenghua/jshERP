package com.jsh.action.basic;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Category;
import com.jsh.model.po.Depot;
import com.jsh.model.po.VisitAccount;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.VisitAccountModel;
import com.jsh.service.basic.VisitAccountIService;
import com.jsh.util.common.PageUtil;

@SuppressWarnings("serial")
public class VisitAccountAction extends BaseAction<VisitAccountModel>
{
    private VisitAccountIService visitAccountService;
    private VisitAccountModel model = new VisitAccountModel();
	/**
	 * 增加回访
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加回访信息方法create()===================");
	    Boolean flag = false;
		try
		{
			VisitAccount visitAccount = new VisitAccount();
			visitAccount.setDepot(new Depot(model.getProjectId()));
			
			visitAccount.setLouHao(model.getLouHao());
			visitAccount.setHuHao(model.getHuHao());
			visitAccount.setHuiFang(model.getHuiFang());
			visitAccount.setLuoShi(model.getLuoShi());
			visitAccount.setName(model.getName());
			visitAccount.setTel(model.getTel());
			visitAccount.setAddTime(new Timestamp(new Date().getTime()));
			visitAccountService.create(visitAccount);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加回访信息异常", e);
			flag = false;
			tipMsg = "失败";
            tipType = 1;
		}
		finally
		{
		    try 
		    {
                toClient(flag.toString());
            } 
		    catch (IOException e) 
		    {
                Log.errorFileSync(">>>>>>>>>>>>增加回访信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加回访", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加回访名称为  "+ model.getName() + " " + tipMsg + "！", "增加回访" + tipMsg));
		Log.infoFileSync("==================结束调用增加回访方法create()===================");
	}
	
	/**
	 * 删除回访
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除回访信息方法delete()================");
	    try 
	    {
	    	visitAccountService.delete(model.getVisitAccountID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getVisitAccountID() + "  的回访异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除回访", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除回访ID为  "+ model.getVisitAccountID() + " " + tipMsg + "！", "删除回访" + tipMsg));
	    Log.infoFileSync("====================结束调用删除回访信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新回访
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	VisitAccount visitAccount = visitAccountService.get(model.getVisitAccountID());
        	visitAccount.setDepot(new Depot(model.getProjectId()));
        	
			visitAccount.setLouHao(model.getLouHao());
			visitAccount.setHuHao(model.getHuHao());
			visitAccount.setHuiFang(model.getHuiFang());
			visitAccount.setLuoShi(model.getLuoShi());
			visitAccount.setName(model.getName());
			visitAccount.setTel(model.getTel());
			visitAccount.setAddTime(new Timestamp(new Date().getTime()));
        	visitAccountService.update(visitAccount);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改回访ID为 ： " + model.getVisitAccountID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        }
        finally
        {
            try 
            {
                toClient(flag.toString());
            } 
            catch (IOException e) 
            {
                Log.errorFileSync(">>>>>>>>>>>>修改回访回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新回访", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新回访ID为  "+ model.getVisitAccountID() + " " + tipMsg + "！", "更新回访" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID回访
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	visitAccountService.batchDelete(model.getVisitAccountIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除回访ID为：" + model.getVisitAccountIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除回访", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除回访ID为  "+ model.getVisitAccountIDs() + " " + tipMsg + "！", "批量删除回访" + tipMsg));
	    return SUCCESS;
	}
	
	/**
	 * 查找回访信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<VisitAccount> pageUtil = new  PageUtil<VisitAccount>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            visitAccountService.find(pageUtil);
            List<VisitAccount> dataList = pageUtil.getPageList();
            
            //开始拼接json数据
//            {"total":28,"rows":[
//                {"productid":"AV-CB-01","attr1":"Adult Male","itemid":"EST-18"}
//            ]}
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(VisitAccount visitAccount:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", visitAccount.getId());
                    item.put("ProjectId", visitAccount.getDepot().getId());
                    item.put("ProjectName", visitAccount.getDepot().getName());
                    item.put("LouHao", visitAccount.getLouHao());
                    item.put("HuHao", visitAccount.getHuHao());
                    item.put("HuiFang", visitAccount.getHuiFang());
                    item.put("LuoShi", visitAccount.getLuoShi());
                    item.put("Name", visitAccount.getName());
                    item.put("Tel", visitAccount.getTel());
                    item.put("AddTime", visitAccount.getAddTime());
                    item.put("op", 1);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找回访信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询回访信息结果异常", e);
        }
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
        condition.put("ProjectId_n_eq", model.getProjectId());
        condition.put("LouHao_s_like", model.getLouHao());
        condition.put("AddTime_s_order", "desc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public VisitAccountModel getModel()
	{
		return model;
	}
	public void setVisitAccountService(VisitAccountIService visitAccountService)
    {
        this.visitAccountService = visitAccountService;
    }
}
