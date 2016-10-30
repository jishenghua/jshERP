package com.jsh.action.basic;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Category;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.CategoryModel;
import com.jsh.service.basic.CategoryIService;
import com.jsh.util.common.PageUtil;

@SuppressWarnings("serial")
public class CategoryAction extends BaseAction<CategoryModel>
{
    private CategoryIService categoryService;
    private CategoryModel model = new CategoryModel();
	/**
	 * 增加资产类型
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加资产类型方法create()===================");
	    Boolean flag = false;
		try
		{
		    Category category = new Category();
		    category.setAssetname(model.getCategoryName());
		    category.setIsystem((short)1);
		    category.setDescription(model.getDescription());
		    categoryService.create(category);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加资产类型异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加资产类型回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加资产类型", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加资产类型名称为  "+ model.getCategoryName() + " " + tipMsg + "！", "增加资产类型" + tipMsg));
		Log.infoFileSync("==================结束调用增加资产类型方法create()===================");
	}
	
	/**
	 * 删除资产类型
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除资产类型信息方法delete()================");
	    try 
	    {
	        categoryService.delete(model.getCategoryID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getCategoryID() + "  的资产类型异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除资产类型", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除资产类型ID为  "+ model.getCategoryID() + " " + tipMsg + "！", "删除资产类型" + tipMsg));
	    Log.infoFileSync("====================结束调用删除资产类型信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新资产类型
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
            Category category = categoryService.get(model.getCategoryID());
            category.setAssetname(model.getCategoryName());
            category.setDescription(model.getDescription());
            categoryService.update(category);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改资产类型ID为 ： " + model.getCategoryID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改资产类型回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新资产类型", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新资产类型ID为  "+ model.getCategoryID() + " " + tipMsg + "！", "更新资产类型" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID资产类型
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	        categoryService.batchDelete(model.getCategoryIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除资产类型ID为：" + model.getCategoryIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除资产类型", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除资产类型ID为  "+ model.getCategoryIDs() + " " + tipMsg + "！", "批量删除资产类型" + tipMsg));
	    return SUCCESS;
	}
	
	/**
	 * 检查输入名称是否存在
	 */
	public void checkIsNameExist()
	{
	    Boolean flag = false;
	    try 
	    {
	        flag = categoryService.checkIsNameExist("assetname",model.getCategoryName(),"id", model.getCategoryID());
        } 
	    catch (DataAccessException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查资产类型名称为：" + model.getCategoryName() + " ID为： " + model.getCategoryID() + " 是否存在异常！");
        }
	    finally
	    {
	        try 
	        {
                toClient(flag.toString());
            }
	        catch (IOException e) 
	        {
                Log.errorFileSync(">>>>>>>>>>>>回写检查资产类型名称为：" + model.getCategoryName() + " ID为： " + model.getCategoryID() + " 是否存在异常！",e);
            }
	    }
	}
	
	/**
	 * 查找供应商信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Category> pageUtil = new  PageUtil<Category>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            categoryService.find(pageUtil);
            List<Category> dataList = pageUtil.getPageList();
            
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
                for(Category category:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", category.getId());
                    //供应商名称
                    item.put("categoryname", category.getAssetname());
                    item.put("isystem", category.getIsystem() == (short)0?"是":"否");
                    item.put("description", category.getDescription());
                    item.put("op", category.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找资产类型信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询资产类型信息结果异常", e);
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
        condition.put("assetname_s_like", model.getCategoryName());
        condition.put("description_s_like", model.getDescription());
        condition.put("id_s_order", "desc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public CategoryModel getModel()
	{
		return model;
	}
	public void setCategoryService(CategoryIService categoryService)
    {
        this.categoryService = categoryService;
    }
}
