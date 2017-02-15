package com.jsh.action.asset;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.util.AssetConstants;
import com.jsh.util.JshException;
import com.jsh.model.po.Asset;
import com.jsh.model.po.Assetname;
import com.jsh.model.po.Basicuser;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Supplier;
import com.jsh.model.vo.asset.AssetModel;
import com.jsh.service.asset.AssetIService;
import com.jsh.service.basic.AssetNameIService;
import com.jsh.service.basic.CategoryIService;
import com.jsh.service.basic.SupplierIService;
import com.jsh.service.basic.UserIService;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;

@SuppressWarnings("serial")
public class AssetAction extends BaseAction<AssetModel>
{
    private AssetModel model = new AssetModel();
    private AssetIService assetService;
    private CategoryIService categoryService;
    private SupplierIService supplierService;
    private UserIService userService;
    private AssetNameIService assetnameService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getBasicData()
    {
        Map<String,List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try
        {
            Map<String,Object> condition = pageUtil.getAdvSearch();
            condition.put("id_s_order", "desc");
            categoryService.find(pageUtil);
            mapData.put("categoryList", pageUtil.getPageList());
            supplierService.find(pageUtil);
            mapData.put("supplierList", pageUtil.getPageList());
            
            condition.put("isystem_n_eq", 1);
            condition.put("id_s_order", "desc");
            userService.find(pageUtil);
            mapData.put("userList", pageUtil.getPageList());
            
            //清除搜索条件 防止对查询有影响
            condition.remove("isystem_n_eq");
            
            assetnameService.find(pageUtil);
            mapData.put("assetnameList", pageUtil.getPageList());
        }
        catch (Exception e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>查找系统基础数据信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }
    
    /**
     * 增加资产
     * @return
     */
    public void create()
    {
        Log.infoFileSync("==================开始调用增加资产方法===================");
        Boolean flag = false;
        try
        {
            Asset asset = new Asset();
            //添加设置
            asset.setAssetname(new Assetname(model.getAssetNameID()));
            
            asset.setLocation(model.getLocation());
            asset.setStatus(model.getStatus());
            asset.setPrice(model.getPrice());
            
            if(null != model.getUserID())
            {
                asset.setUser( new Basicuser(model.getUserID()));
            }
            try
            {
                //购买日期
                asset.setPurchasedate(new Timestamp(Tools.parse(model.getPurchasedate(), "yyyy-MM-dd").getTime()));
                //有效日期
                asset.setPeriodofvalidity(new Timestamp(Tools.parse(model.getPeriodofvalidity(), "yyyy-MM-dd").getTime()));
                //保修日期
                asset.setWarrantydate(new Timestamp(Tools.parse(model.getWarrantydate(), "yyyy-MM-dd").getTime()));
            }
            catch (ParseException e)
            {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }
            
            asset.setAssetnum(model.getAssetnum());
            asset.setSerialnum(model.getSerialnum());
            asset.setLabels(model.getLabels());
            
            asset.setSupplier( new Supplier(model.getSupplierID()));
            asset.setDescription(model.getDescription());
            
            asset.setCreatetime(new Timestamp(new Date().getTime()));
            asset.setCreator(getUser());
            asset.setUpdatetime(new Timestamp(new Date().getTime()));
            asset.setUpdator(getUser());
            asset.setAddMonth(Tools.getCurrentMonth());
            assetService.create(asset);
            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加资产异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加资产回写客户端结果异常", e);
            }
        }
        
        logService.create(new Logdetails(getUser(), "增加资产", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加资产名称ID为  "+ model.getAssetNameID() + " " + tipMsg + "！", "增加资产" + tipMsg));
        Log.infoFileSync("==================结束调用增加资产方法===================");
    }
    
    /**
     * 删除资产
     * @return
     */
    public String delete()
    {
        Log.infoFileSync("====================开始调用删除资产信息方法delete()================");
        try 
        {
            assetService.delete(model.getAssetID());
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getAssetID() + "  的资产异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除资产", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "删除资产ID为  "+ model.getAssetID() + " " + tipMsg + "！", "删除资产" + tipMsg));
        Log.infoFileSync("====================结束调用删除资产信息方法delete()================");
        return SUCCESS;
    }
    
    /**
     * 更新资产
     * @return
     */
    public void update()
    {
        Boolean flag = false;
        try
        {
            Asset asset = assetService.get(model.getAssetID());
            
            //设置要更新的熟悉值
            asset.setAssetname(new Assetname(model.getAssetNameID()));
            
            asset.setLocation(model.getLocation());
            asset.setStatus(model.getStatus());
            asset.setPrice(model.getPrice());
            
            if(null != model.getUserID())
                asset.setUser(new Basicuser(model.getUserID()));
            else
            	asset.setUser(null);
            try
            {
                //购买日期
                asset.setPurchasedate(new Timestamp(Tools.parse(model.getPurchasedate(), "yyyy-MM-dd").getTime()));
                //有效日期
                asset.setPeriodofvalidity(new Timestamp(Tools.parse(model.getPeriodofvalidity(), "yyyy-MM-dd").getTime()));
                //保修日期
                asset.setWarrantydate(new Timestamp(Tools.parse(model.getWarrantydate(), "yyyy-MM-dd").getTime()));
            }
            catch (ParseException e)
            {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }
            
            asset.setAssetnum(model.getAssetnum());
            asset.setSerialnum(model.getSerialnum());
            asset.setLabels(model.getLabels());
            
            asset.setSupplier(new Supplier(model.getSupplierID()));
            asset.setDescription(model.getDescription());
            
            asset.setUpdatetime(new Timestamp(new Date().getTime()));
            asset.setUpdator(getUser());
            assetService.update(asset);
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改资产ID为 ： " + model.getAssetID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改资产回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新资产", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新资产ID为  "+ model.getAssetID() + " " + tipMsg + "！", "更新资产" + tipMsg));
    }
    
    /**
     * 批量删除指定ID资产
     * @return
     */
    public String batchDelete()
    {
        try
        {
            assetService.batchDelete(model.getAssetIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>批量删除资产ID为：" + model.getAssetIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        
        logService.create(new Logdetails(getUser(), "批量删除资产", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除资产ID为  "+ model.getAssetIDs() + " " + tipMsg + "！", "批量删除资产" + tipMsg));
        return SUCCESS;
    }
    
    /**
     * 查找资产信息
     * @return
     */
    public void findBy()
    {
        try 
        {
            PageUtil<Asset> pageUtil = new  PageUtil<Asset>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            assetService.find(pageUtil);
            
            getSession().put("pageUtil", pageUtil);
            List<Asset> dataList = pageUtil.getPageList();
            
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
                for(Asset asset:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", asset.getId());
                    //添加资产属性
                    item.put("assetname", asset.getAssetname().getAssetname());
                    item.put("assetnameID", asset.getAssetname().getId());
                    //单价
                    item.put("price", Tools.dealNullStr(asset.getPrice() + ""));
                    //分类
                    item.put("category", asset.getAssetname().getCategory().getAssetname());
                    item.put("categoryID", asset.getAssetname().getCategory().getId());
                    //资产的状态：0==在库，1==在用，2==消费
                    item.put("status", getStatusInfo(asset.getStatus()));
                    item.put("statushort", asset.getStatus());
                    //在用用户名称
                    item.put("username", asset.getUser()==null?"":asset.getUser().getUsername());
                    item.put("userID", asset.getUser()==null?"":asset.getUser().getId());
                    //位置
                    item.put("location", Tools.dealNullStr(asset.getLocation()));
                    
                    //购买日期
                    item.put("purchasedate", asset.getPurchasedate()==null?"":Tools.getCurrentMonth(asset.getPurchasedate()));
                    //有效日期
                    item.put("periodofvalidity", asset.getPeriodofvalidity()==null?"":Tools.getCurrentMonth(asset.getPeriodofvalidity()));
                    //保修日期
                    item.put("warrantydate", asset.getWarrantydate()==null?"":Tools.getCurrentMonth(asset.getWarrantydate()));
                    //资产编号
                    item.put("assetnum", Tools.dealNullStr(asset.getAssetnum()));
                    //资产序列号
                    item.put("serialnum", Tools.dealNullStr(asset.getSerialnum()));
                    //供应商
                    item.put("supplier", asset.getSupplier()==null?"":asset.getSupplier().getSupplier());
                    //供应商
                    item.put("supplierID", asset.getSupplier()==null?"":asset.getSupplier().getId());
                    //标签
                    item.put("labels", Tools.dealNullStr(asset.getLabels()));
                    item.put("description", Tools.dealNullStr(asset.getDescription()));
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
            Log.errorFileSync(">>>>>>>>>查找资产信息异常", e);
        } 
        catch (IOException e) 
        {
            Log.errorFileSync(">>>>>>>>>回写查询资产信息结果异常", e);
        }
    }
    
    /**
	 * 导出excel表格
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public String exportExcel()
	{
		Log.infoFileSync("===================调用导出资产信息action方法exportExcel开始=======================");
		try
		{			
			PageUtil<Asset> pageUtil = (PageUtil<Asset>)getSession().get("pageUtil");
			
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            //pageUtil.setAdvSearch(getCondition());
			String isCurrentPage = model.getIsAllData(); 
			model.setFileName(Tools.changeUnicode(model.getFileName() + ".xls",model.getBrowserType()));
			model.setExcelStream(assetService.exmportExcel(isCurrentPage,pageUtil));			
		}
		catch (Exception e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>调用导出资产信息action方法exportExcel异常",e);
			model.getShowModel().setMsgTip("export excel exception");
		}
		Log.infoFileSync("===================调用导出资产信息action方法exportExcel结束==================");
		return AssetConstants.BusinessForExcel.EXCEL;
	}
	
	
	/**
	 * 导入资产excel表格内容
	 */
	public String importExcel()
	{
	    //资产excel表格file
		Boolean result = false;
		String returnStr = "";
	    try
		{
			InputStream in = assetService.importExcel(model.getAssetFile(), model.getIsCheck());
			
			if(null != in)
			{
				model.setFileName(Tools.getRandomChar() + Tools.getNow2(Calendar.getInstance().getTime()) + "_wrong.xls");
				model.setExcelStream(in);	
				returnStr = AssetConstants.BusinessForExcel.EXCEL;
			}
			else
			{
				result = true;
				try
				{
					toClient(result.toString());
				}
				catch (IOException e)
				{
					Log.errorFileSync(">>>>>>>>>回写导入资产信息结果异常", e);
				}
				//导入数据成功
				returnStr = SUCCESS;
			}
				
		}
		catch (JshException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>导入excel表格信息异常", e);
		}
	    return returnStr;
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
        condition.put("user.id_n_eq", model.getUserID());
        condition.put("status_n_eq", model.getStatus());
        condition.put("supplier.id_n_eq", model.getSupplierID());
        condition.put("createtime_s_order", "desc");
        return condition;
    }
    
    /**
     * 根据状态码转化成说明字符串
     * 资产的状态：0==在库，1==在用，2==消费
     * @param statusCode
     * @return
     */
    private String getStatusInfo(short statusCode)
    {
        String statusInfo = "";
        switch(statusCode)
        {
            case AssetConstants.BusinessForExcel.EXCEl_STATUS_ZAIKU:
                statusInfo = "在库";
            break;
            
            case AssetConstants.BusinessForExcel.EXCEl_STATUS_INUSE:
                statusInfo = "在用";
            break;
            
            case AssetConstants.BusinessForExcel.EXCEl_STATUS_CONSUME:
                statusInfo = "消费";
            break;
        }
        return statusInfo;
    }
    
    //=========Spring注入以及model驱动公共方法===========
    public void setAssetService(AssetIService assetService)
    {
        this.assetService = assetService;
    }
    
    public void setCategoryService(CategoryIService categoryService)
    {
        this.categoryService = categoryService;
    }

    public void setSupplierService(SupplierIService supplierService)
    {
        this.supplierService = supplierService;
    }

    public void setUserService(UserIService userService)
    {
        this.userService = userService;
    }
    
    public void setAssetnameService(AssetNameIService assetnameService)
    {
        this.assetnameService = assetnameService;
    }

	@Override
	public AssetModel getModel()
	{
		return model;
	}
}
