package com.jsh.action.materials;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Account;
import com.jsh.model.po.Depot;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Person;
import com.jsh.model.po.Supplier;
import com.jsh.model.vo.materials.DepotHeadModel;
import com.jsh.service.materials.DepotHeadIService;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;


/*
 * 单据表头管理
 * @author jishenghua  qq:752718920
*/
@SuppressWarnings("serial")
public class DepotHeadAction extends BaseAction<DepotHeadModel>
{
    private DepotHeadIService depotHeadService;
    private DepotHeadModel model = new DepotHeadModel();
    
	/*
	 * 获取MaxId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getMaxId()  
	{
        Map<String,List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try
        {
        	depotHeadService.find(pageUtil,"maxId");
            mapData.put("depotHeadMax", pageUtil.getPageList());
        }
        catch (Exception e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>查找最大的Id信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
	}
	
	/**
	 * 增加仓管通
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加仓管通信息信息方法create()===================");
	    Boolean flag = false;
		try
		{
			DepotHead depotHead = new DepotHead();
			depotHead.setType(model.getType());
			depotHead.setSubType(model.getSubType());
			depotHead.setProjectId(new Depot(model.getProjectId()));
			depotHead.setNumber(model.getNumber());
			depotHead.setOperPersonName(getUser().getUsername());
			depotHead.setCreateTime(new Timestamp(new Date().getTime()));
			try
            {
				depotHead.setOperTime(new Timestamp(Tools.parse(model.getOperTime(), "yyyy-MM-dd").getTime()));
            }
			catch (ParseException e)
            {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }
			if(model.getOrganId()!=null){depotHead.setOrganId(new Supplier(model.getOrganId()));}
			if(model.getHandsPersonId()!=null){depotHead.setHandsPersonId(new Person(model.getHandsPersonId()));}			
			if(model.getAccountId()!=null){depotHead.setAccountId(new Account(model.getAccountId()));}
			depotHead.setChangeAmount(model.getChangeAmount());
			if(model.getAllocationProjectId()!=null){depotHead.setAllocationProjectId(new Depot(model.getAllocationProjectId()));}
			depotHead.setTotalPrice(model.getTotalPrice());
			depotHead.setPayType(model.getPayType());
			depotHead.setRemark(model.getRemark());
			depotHeadService.create(depotHead);
			
			//========标识位===========
		    flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加仓管通信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加仓管通信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加仓管通", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加仓管通编号为  "+ model.getNumber() + " " + tipMsg + "！", "增加仓管通" + tipMsg));
		Log.infoFileSync("==================结束调用增加仓管通方法create()===================");
	}
	
	/**
	 * 删除仓管通
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除仓管通信息方法delete()================");
	    try 
	    {
	    	depotHeadService.delete(model.getDepotHeadID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getDepotHeadID() + "  的仓管通异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除仓管通", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除仓管通ID为  "+ model.getDepotHeadID() + " " + tipMsg + "！", "删除仓管通" + tipMsg));
	    Log.infoFileSync("====================结束调用删除仓管通信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新仓管通
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	DepotHead depotHead = depotHeadService.get(model.getDepotHeadID());
			depotHead.setType(model.getType());
			depotHead.setSubType(model.getSubType());
			depotHead.setProjectId(new Depot(model.getProjectId()));
			depotHead.setNumber(model.getNumber());
			depotHead.setOperPersonName(getUser().getUsername());
			try
            {
				depotHead.setOperTime(new Timestamp(Tools.parse(model.getOperTime(), "yyyy-MM-dd").getTime()));
            }
			catch (ParseException e)
            {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析入库时间格式异常", e);
            }
			if(model.getOrganId()!=null){depotHead.setOrganId(new Supplier(model.getOrganId()));}
			if(model.getHandsPersonId()!=null){depotHead.setHandsPersonId(new Person(model.getHandsPersonId()));}
			if(model.getAccountId()!=null){depotHead.setAccountId(new Account(model.getAccountId()));}			
			depotHead.setChangeAmount(model.getChangeAmount());
			if(model.getAllocationProjectId()!=null){depotHead.setAllocationProjectId(new Depot(model.getAllocationProjectId()));}
			depotHead.setTotalPrice(model.getTotalPrice());
			depotHead.setPayType(model.getPayType());
			depotHead.setRemark(model.getRemark());
        	depotHeadService.update(depotHead);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改仓管通ID为 ： " + model.getDepotHeadID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改仓管通回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新仓管通", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新仓管通ID为  "+ model.getDepotHeadID() + " " + tipMsg + "！", "更新仓管通" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID仓管通
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	depotHeadService.batchDelete(model.getDepotHeadIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除仓管通ID为：" + model.getDepotHeadIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除仓管通", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除仓管通ID为  "+ model.getDepotHeadIDs() + " " + tipMsg + "！", "批量删除仓管通" + tipMsg));
	    return SUCCESS;
	}
	
	/**
	 * 查找仓管通信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<DepotHead> pageUtil = new  PageUtil<DepotHead>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            depotHeadService.find(pageUtil);
            List<DepotHead> dataList = pageUtil.getPageList();
            
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotHead depotHead:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", depotHead.getId());
                    item.put("ProjectId", depotHead.getProjectId()==null?"":depotHead.getProjectId().getId());
                    item.put("ProjectName", depotHead.getProjectId()==null?"":depotHead.getProjectId().getName());
                    item.put("Number", depotHead.getNumber());
                    item.put("OperPersonName", depotHead.getOperPersonName());
                    item.put("CreateTime", Tools.getCurrentMonth(depotHead.getCreateTime()));
                    item.put("OperTime", Tools.getCurrentMonth(depotHead.getOperTime()));
                    item.put("OrganId", depotHead.getOrganId()==null?"":depotHead.getOrganId().getId());
                    item.put("OrganName", depotHead.getOrganId()==null?"":depotHead.getOrganId().getSupplier());
                    item.put("HandsPersonId", depotHead.getHandsPersonId()==null?"":depotHead.getHandsPersonId().getId());
                    item.put("HandsPersonName", depotHead.getHandsPersonId()==null?"":depotHead.getHandsPersonId().getName());
                    item.put("AccountId", depotHead.getAccountId()==null?"":depotHead.getAccountId().getId());
                    item.put("AccountName", depotHead.getAccountId()==null?"":depotHead.getAccountId().getName());
                    item.put("ChangeAmount", depotHead.getChangeAmount()==null?"":Math.abs(depotHead.getChangeAmount()));
                    item.put("AllocationProjectId", depotHead.getAllocationProjectId()==null?"":depotHead.getAllocationProjectId().getId());
                    item.put("AllocationProjectName", depotHead.getAllocationProjectId()==null?"":depotHead.getAllocationProjectId().getName());                    
                    item.put("TotalPrice", depotHead.getTotalPrice()==null?"":Math.abs(depotHead.getTotalPrice()));
					item.put("payType", depotHead.getPayType()==null?"":depotHead.getPayType());
                    item.put("Remark", depotHead.getRemark());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找仓管通信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询仓管通信息结果异常", e);
        }
	}
    
	/**
	 * 查找进销存_根据月份(报表)
	 * @return
	 */
    public void findByMonth() {
	    try 
	    {
	    	PageUtil<DepotHead> pageUtil = new  PageUtil<DepotHead>();
	    	pageUtil.setPageSize(1000);
            pageUtil.setCurPage(1);
	    	pageUtil.setAdvSearch(getConditionHead());
            depotHeadService.find(pageUtil);
            List<DepotHead> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();            
	    	String headId = "";
	    	if(null != dataList)
            {
                for(DepotHead depotHead:dataList)
                {
                	headId = headId + depotHead.getId() + ",";
                }
            }
	    	if(headId!="") {
	    		headId = headId.substring(0, headId.lastIndexOf(","));
	    	}
	    	outer.put("HeadIds", headId);
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找仓管通信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询仓管通信息结果异常", e);
        }
	}
    
    /**
     * 查询单位的累计应收和累计应付
     * @return
     */
    public void findTotalPay() {
    	try
	    {
			JSONObject outer = new JSONObject();     	
			Double sum = 0.0;
			String getS = model.getSupplierId();
			//进销部分
			sum = sum + (allMoney(getS, "入库", "采购", "合计") - allMoney(getS, "入库", "采购", "实际"));
			sum = sum + (allMoney(getS, "入库", "销售退货", "合计") - allMoney(getS, "入库", "销售退货", "实际"));	
			sum = sum + (allMoney(getS, "入库", "其他", "合计") - allMoney(getS, "入库", "其他", "实际"));	
			sum = sum - (allMoney(getS, "出库", "销售", "合计") - allMoney(getS, "出库", "销售", "实际"));	
			sum = sum - (allMoney(getS, "出库", "采购退货", "合计") - allMoney(getS, "出库", "采购退货", "实际"));	
			sum = sum - (allMoney(getS, "出库", "其他", "合计") - allMoney(getS, "出库", "其他", "实际"));			
	    	outer.put("getAllMoney", sum);
            toClient(outer.toString());
        }
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询结果异常", e);
        }
	}
    
    
    /**
     * 统计总金额
     * @param type
     * @param subType 
     * @param mode 合计或者金额
     * @return
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Double allMoney(String getS, String type, String subType, String mode) {
		Log.infoFileSync("getS:" + getS);
    	Double allMoney = 0.0;
    	String allReturn = "";
		PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {        	
        	Integer supplierId = Integer.valueOf(getS);
        	depotHeadService.findAllMoney(pageUtil, supplierId, type, subType, mode);
			allReturn = pageUtil.getPageList().toString();
			allReturn = allReturn.substring(1,allReturn.length()-1);
			if(allReturn.equals("null")){
				allReturn = "0";
			}
		} catch (JshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        allMoney = Double.parseDouble(allReturn);
        //返回正数，如果负数也转为正数
        if(allMoney<0){
        	allMoney = -allMoney;
        }
		return allMoney;    	     
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
        if(model.getAllocationProjectId()!=null)
        {condition.put("AllocationProjectId_n_eq", model.getAllocationProjectId());}
        if(model.getProjectId()!=null) {
			condition.put("ProjectId_n_eq", model.getProjectId());
		}
		else {
			condition.put("ProjectId_s_in", model.getDepotIds());
		}
        condition.put("Type_s_eq",model.getType());
        condition.put("SubType_s_eq",model.getSubType());
        condition.put("Number_s_like",model.getNumber());
        condition.put("OperTime_s_gteq",model.getBeginTime());
        condition.put("OperTime_s_lteq",model.getEndTime());
        condition.put("Id_s_order","desc");
        return condition;
    }
	
	private Map<String,Object> getConditionHead()
    {
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("OperTime_s_lteq",model.getMonthTime() + "-31 00:00:00");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	public DepotHeadModel getModel()
	{
		return model;
	}
	public void setDepotHeadService(DepotHeadIService depotHeadService)
    {
        this.depotHeadService = depotHeadService;
    }
}
