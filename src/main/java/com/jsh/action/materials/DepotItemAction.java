package com.jsh.action.materials;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;
import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.util.AssetConstants;
import com.jsh.util.JshException;
import com.jsh.model.po.Asset;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Material;
import com.jsh.model.vo.materials.DepotItemModel;
import com.jsh.service.materials.DepotHeadIService;
import com.jsh.service.materials.DepotItemIService;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
/*
 * 单据明细管理
 * @author jishenghua  qq:752718920
*/
@SuppressWarnings("serial")
public class DepotItemAction extends BaseAction<DepotItemModel>
{
    private DepotItemIService depotItemService;
    private DepotItemModel model = new DepotItemModel();
	/**
	 * action返回excel结果
	 */
	public static final String EXCEL = "excel";
	
	/**
	 * 保存明细
	 * @return
	 */
	public void saveDetials()
	{
	    Log.infoFileSync("==================开始调用保存仓管通明细信息方法saveDetials()===================");
	    Boolean flag = false;
		try
		{
			Long headerId=model.getHeaderId();
			String inserted=model.getInserted();
			String deleted=model.getDeleted();
			String updated=model.getUpdated();
			//转为json
			JSONArray insertedJson = JSONArray.fromObject(inserted);
			JSONArray deletedJson = JSONArray.fromObject(deleted);
			JSONArray updatedJson = JSONArray.fromObject(updated);
			if(null != insertedJson)
			{
				for(int i = 0;i < insertedJson.size(); i++)
				{
					DepotItem depotItem = new DepotItem();
					JSONObject tempInsertedJson = JSONObject.fromObject(insertedJson.get(i));
					depotItem.setHeaderId(new DepotHead(headerId));
					depotItem.setMaterialId(new Material(tempInsertedJson.getLong("MaterialId")));
					depotItem.setOperNumber(tempInsertedJson.getDouble("OperNumber"));
					if(tempInsertedJson.get("UnitPrice")!=null){depotItem.setUnitPrice(tempInsertedJson.getDouble("UnitPrice"));}
					if(tempInsertedJson.get("AllPrice")!=null){depotItem.setAllPrice(tempInsertedJson.getDouble("AllPrice"));}
					depotItem.setRemark(tempInsertedJson.getString("Remark"));
					depotItemService.create(depotItem);
				}
			}
			if(null != deletedJson)
			{
				for(int i = 0;i < deletedJson.size(); i++)
				{
					JSONObject tempDeletedJson = JSONObject.fromObject(deletedJson.get(i));
					depotItemService.delete(tempDeletedJson.getLong("Id"));
				}
			}
			if(null != updatedJson)
			{
				for(int i = 0;i < updatedJson.size(); i++)
				{
					JSONObject tempUpdatedJson = JSONObject.fromObject(updatedJson.get(i));
					DepotItem depotItem = depotItemService.get(tempUpdatedJson.getLong("Id"));
					depotItem.setMaterialId(new Material(tempUpdatedJson.getLong("MaterialId")));
					depotItem.setOperNumber(tempUpdatedJson.getDouble("OperNumber"));
					if(tempUpdatedJson.get("UnitPrice")!=null){depotItem.setUnitPrice(tempUpdatedJson.getDouble("UnitPrice"));}
					if(tempUpdatedJson.get("AllPrice")!=null){depotItem.setAllPrice(tempUpdatedJson.getDouble("AllPrice"));}
					depotItem.setRemark(tempUpdatedJson.getString("Remark"));
					depotItemService.create(depotItem);
				}
			}
			
			//========标识位===========
		    flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>保存仓管通明细信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>保存仓管通明细信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "保存仓管通明细", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "保存仓管通明细对应主表编号为  "+ model.getHeaderId() + " " + tipMsg + "！", "保存仓管通明细" + tipMsg));
		Log.infoFileSync("==================结束调用保存仓管通明细方法saveDetials()===================");
	}

	
	/**
	 * 查找仓管通信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    String MaterialName = ((depotItem.getMaterialId().getModel().equals(""))?"":""+depotItem.getMaterialId().getModel())+" "+depotItem.getMaterialId().getName()
                    +((depotItem.getMaterialId().getColor() == null)?"(":"("+depotItem.getMaterialId().getColor()) + ")"
                    +((depotItem.getMaterialId().getUnit() == null)?"(":"("+depotItem.getMaterialId().getUnit()) + ")";
                    item.put("MaterialName", MaterialName);
                    item.put("OperNumber", depotItem.getOperNumber());
                    item.put("UnitPrice", depotItem.getUnitPrice());
                    item.put("AllPrice", depotItem.getAllPrice());
                    item.put("Remark", depotItem.getRemark());
                    item.put("Img", depotItem.getImg());
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
	 * 查找进销存
	 * @return
	 */
    public void findByAll()
	{
	    try 
	    {	    	
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    Integer prevSum = sumNumber("入库",depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumNumber("出库",depotItem.getMaterialId().getId(),model.getMonthTime(),true);
                    Integer InSum = sumNumber("入库",depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Integer OutSum = sumNumber("出库",depotItem.getMaterialId().getId(),model.getMonthTime(),false);                    
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("prevSum", prevSum);
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("thisSum", prevSum + InSum - OutSum);
                    item.put("thisAllPrice", depotItem.getUnitPrice() * (prevSum + InSum - OutSum));
                    item.put("UnitPrice", depotItem.getUnitPrice());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}
	/**
	 * 进货统计
	 * @return
	 */
    public void buyIn()
	{
	    try 
	    {	    	
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    Integer InSum = sumNumberBuyOrSale("入库","采购",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Integer OutSum = sumNumberBuyOrSale("出库","采购退货",depotItem.getMaterialId().getId(),model.getMonthTime());  
                    Double InSumPrice = sumPriceBuyOrSale("入库","采购",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Double OutSumPrice = sumPriceBuyOrSale("出库","采购退货",depotItem.getMaterialId().getId(),model.getMonthTime());  
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("InSumPrice", InSumPrice);
                    item.put("OutSumPrice", OutSumPrice);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}
	/**
	 * 销售统计
	 * @return
	 */
    public void saleOut()
	{
	    try 
	    {	    	
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    Integer OutSum = sumNumberBuyOrSale("出库","销售",depotItem.getMaterialId().getId(),model.getMonthTime());   
                    Integer InSum = sumNumberBuyOrSale("入库","销售退货",depotItem.getMaterialId().getId(),model.getMonthTime()); 
                    Double OutSumPrice = sumPriceBuyOrSale("出库","销售",depotItem.getMaterialId().getId(),model.getMonthTime()); 
                    Double InSumPrice = sumPriceBuyOrSale("入库","销售退货",depotItem.getMaterialId().getId(),model.getMonthTime());                    
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("OutSum", OutSum);
                    item.put("InSum", InSum);
                    item.put("OutSumPrice", OutSumPrice);
                    item.put("InSumPrice", InSumPrice);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}
	/**
	 * 统计总计金额
	 * @return
	 */
    public void totalCountMoney()
	{
	    try 
	    {	    	
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
	        pageUtil.setPageSize(0);
	        pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            Double thisAllPrice = 0.0;
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    Integer prevSum = sumNumber("入库",depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumNumber("出库",depotItem.getMaterialId().getId(),model.getMonthTime(),true);
                    Integer InSum = sumNumber("入库",depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Integer OutSum = sumNumber("出库",depotItem.getMaterialId().getId(),model.getMonthTime(),false);                    
                    thisAllPrice = thisAllPrice + depotItem.getUnitPrice() * (prevSum + InSum - OutSum);
                }
            }
            outer.put("totalCount", thisAllPrice);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}
    
    /**
	 * 导出excel表格
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public String exportExcel()
	{
		Log.infoFileSync("===================调用导出信息action方法exportExcel开始=======================");
		try
		{			
			PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    Integer prevSum = sumNumber("入库",depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumNumber("出库",depotItem.getMaterialId().getId(),model.getMonthTime(),true);
                    Integer InSum = sumNumber("入库",depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Integer OutSum = sumNumber("出库",depotItem.getMaterialId().getId(),model.getMonthTime(),false);                    
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("prevSum", prevSum);
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("thisSum", prevSum + InSum - OutSum);
                    item.put("thisAllPrice", depotItem.getUnitPrice() * (prevSum + InSum - OutSum));
                    item.put("UnitPrice", depotItem.getUnitPrice());
                    dataArray.add(item);
                }
            }
			String isCurrentPage = "allPage"; 
			model.setFileName(Tools.changeUnicode("report.xls",model.getBrowserType()));
			model.setExcelStream(depotItemService.exmportExcel(isCurrentPage,dataArray));			
		}
		catch (Exception e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>调用导出信息action方法exportExcel异常",e);
			model.getShowModel().setMsgTip("export excel exception");
		}
		Log.infoFileSync("===================调用导出信息action方法exportExcel结束==================");
		return EXCEL;
	}
    
    @SuppressWarnings("unchecked")
	public Integer sumNumber(String type,Long MId,String MonthTime, Boolean isPrev) {
    	Integer sumNumber = 0;
    	String allNumber = "";
    	PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
			depotItemService.findByType(pageUtil, type, MId, MonthTime, isPrev);
			allNumber = pageUtil.getPageList().toString();
			allNumber = allNumber.substring(1,allNumber.length()-1);
			if(allNumber.equals("null")){
				allNumber = "0";
			}
			allNumber = allNumber.replace(".0", "");
		} catch (JshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumNumber = Integer.parseInt(allNumber);
		return sumNumber;    	     
    }
    @SuppressWarnings("unchecked")
	public Integer sumNumberBuyOrSale(String type,String subType,Long MId,String MonthTime) {
    	Integer sumNumber = 0;
    	String allNumber = "";
    	String sumType = "Number";
    	PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
			depotItemService.buyOrSale(pageUtil, type, subType, MId, MonthTime, sumType);
			allNumber = pageUtil.getPageList().toString();
			allNumber = allNumber.substring(1,allNumber.length()-1);
			if(allNumber.equals("null")){
				allNumber = "0";
			}
			allNumber = allNumber.replace(".0", "");
		} catch (JshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumNumber = Integer.parseInt(allNumber);
		return sumNumber;    	     
    }
    @SuppressWarnings("unchecked")
	public Double sumPriceBuyOrSale(String type,String subType,Long MId,String MonthTime) {
    	Double sumPrice = 0.0;
    	String allPrice = "";
    	String sumType = "Price";
    	PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
			depotItemService.buyOrSale(pageUtil, type, subType, MId, MonthTime, sumType);
			allPrice = pageUtil.getPageList().toString();
			allPrice = allPrice.substring(1,allPrice.length()-1);
			if(allPrice.equals("null")){
				allPrice = "0";
			}
			allPrice = allPrice.replace(".0", "");
		} catch (JshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumPrice = Double.parseDouble(allPrice);
		return sumPrice;    	     
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
        condition.put("HeaderId_n_eq", model.getHeaderId());
        condition.put("Id_s_order","asc");
        return condition;
    }
	
	private Map<String,Object> getConditionALL()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("HeaderId_s_in", model.getHeadIds());
        condition.put("MaterialId_s_in", model.getMaterialIds());
        condition.put("MaterialId_s_gb","aaa");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public DepotItemModel getModel()
	{
		return model;
	}
	public void setDepotItemService(DepotItemIService depotItemService)
    {
        this.depotItemService = depotItemService;
    }
}
