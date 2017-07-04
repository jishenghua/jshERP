package com.jsh.action.materials;

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
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.Material;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.MaterialCategory;
import com.jsh.model.po.Supplier;
import com.jsh.model.vo.materials.MaterialModel;
import com.jsh.service.materials.MaterialIService;
import com.jsh.util.PageUtil;
/*
 * 商品管理
 * @author jishenghua  qq:752718920 
*/
@SuppressWarnings("serial")
public class MaterialAction extends BaseAction<MaterialModel>
{
    private MaterialIService materialService;
    private MaterialModel model = new MaterialModel();
    
	/**
	 * 增加商品
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加商品信息方法create()===================");
	    Boolean flag = false;
		try
		{
			Material material = new Material();
			material.setMaterialCategory(new MaterialCategory(model.getCategoryId()));
			
			material.setName(model.getName());
			material.setModel(model.getModel());
			material.setColor(model.getColor());
			material.setUnit(model.getUnit());
            material.setRetailPrice(model.getRetailPrice());
            material.setLowPrice(model.getLowPrice());
            material.setPresetPriceOne(model.getPresetPriceOne());
            material.setPresetPriceTwo(model.getPresetPriceTwo());
			material.setRemark(model.getRemark());
			materialService.create(material);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加商品信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加商品信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加商品", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加商品名称为  "+ model.getName() + " " + tipMsg + "！", "增加商品" + tipMsg));
		Log.infoFileSync("==================结束调用增加商品方法create()===================");
	}
	
	/**
	 * 删除商品
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除商品信息方法delete()================");
	    try 
	    {
	    	materialService.delete(model.getMaterialID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getMaterialID() + "  的商品异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除商品", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除商品ID为  "+ model.getMaterialID() + " " + tipMsg + "！", "删除商品" + tipMsg));
	    Log.infoFileSync("====================结束调用删除商品信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新商品
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	Material material = materialService.get(model.getMaterialID());
        	material.setMaterialCategory(new MaterialCategory(model.getCategoryId()));
			
			material.setName(model.getName());
			material.setModel(model.getModel());
			material.setColor(model.getColor());
			material.setUnit(model.getUnit());
            material.setRetailPrice(model.getRetailPrice());
            material.setLowPrice(model.getLowPrice());
            material.setPresetPriceOne(model.getPresetPriceOne());
            material.setPresetPriceTwo(model.getPresetPriceTwo());
			material.setRemark(model.getRemark());
			material.setName(model.getName());
        	materialService.update(material);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改商品ID为 ： " + model.getMaterialID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改商品回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新商品", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新商品ID为  "+ model.getMaterialID() + " " + tipMsg + "！", "更新商品" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID商品
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	materialService.batchDelete(model.getMaterialIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除商品ID为：" + model.getMaterialIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除商品", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除商品ID为  "+ model.getMaterialIDs() + " " + tipMsg + "！", "批量删除商品" + tipMsg));
	    return SUCCESS;
	}
	
	/**
	 * 查找商品信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            Long lei=model.getCategoryId();
            if(1==lei) //判断值还真不能用String类型的判断
            {
            	pageUtil.setAdvSearch(getCondition_all());
            }
            else if(1!=lei)
            {
                pageUtil.setAdvSearch(getCondition());
            }
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Material material:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    item.put("Name", material.getName());
                    item.put("Model", material.getModel());
                    item.put("Color", material.getColor());
                    item.put("Unit", material.getUnit());
                    item.put("RetailPrice", material.getRetailPrice());
                    item.put("LowPrice", material.getLowPrice());
                    item.put("PresetPriceOne", material.getPresetPriceOne());
                    item.put("PresetPriceTwo", material.getPresetPriceTwo());
                    item.put("Remark", material.getRemark());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找商品信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询商品信息结果异常", e);
        }
	}

    /**
     * 根据id来查询商品名称
     * @return
     */
    public void findById() {
        try
        {
            PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setAdvSearch(getConditionById());
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Material material:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    item.put("Name", material.getName());
                    item.put("Model", material.getModel());
                    item.put("Color", material.getColor());
                    item.put("Unit", material.getUnit());
                    item.put("RetailPrice", material.getRetailPrice());
                    item.put("LowPrice", material.getLowPrice());
                    item.put("PresetPriceOne", material.getPresetPriceOne());
                    item.put("PresetPriceTwo", material.getPresetPriceTwo());
                    item.put("Remark", material.getRemark());
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
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找商品信息异常", e);
        }
        catch (IOException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询商品信息结果异常", e);
        }
    }
    
	/**
	 * 查找商品信息-下拉框
	 * @return
	 */
    public void findBySelect()
	{
	    try 
	    {
	        PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select());
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Material material:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    //名称
                    String MaterialName = ((material.getModel().equals(""))?"":""+material.getModel()) +" "+ material.getName() 
                    + ((material.getColor() == null || material.getColor().equals(""))?"":"("+material.getColor() + ")")
                    + ((material.getUnit() == null || material.getUnit().equals(""))?"":"("+material.getUnit() + ")");
                    item.put("MaterialName", MaterialName);
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>查找供应商信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>回写查询供应商信息结果异常", e);
        }
	}
    
	/**
	 * 查找商品信息-统计排序
	 * @return
	 */
    public void findByOrder()
	{
	    try 
	    {
	        PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Order());
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONObject outer = new JSONObject();             
            String mId = "";
	    	if(null != dataList)
            {
                for(Material material:dataList)
                {
                	mId = mId + material.getId() + ",";
                }
            }
	    	if(mId!="") {
	    		mId = mId.substring(0, mId.lastIndexOf(","));
	    	}
	    	outer.put("mIds", mId);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>查找供应商信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>回写查询供应商信息结果异常", e);
        }
	}
    
	/**
	 * 拼接搜索条件(查全部)
	 * @return
	 */
	private Map<String,Object> getCondition_all()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Id_s_order", "asc");
        return condition;
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
        condition.put("CategoryId_s_in", model.getCategoryIds());
        condition.put("Id_s_order", "asc");
        return condition;
    }

    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getConditionById()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Id_n_eq", model.getMaterialID());
        return condition;
    }
	
	/**
	 * 拼接搜索条件-下拉框
	 * @return
	 */
	private Map<String,Object> getCondition_Select()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Id_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-下拉框
	 * @return
	 */
	private Map<String,Object> getCondition_Order()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Name,Model_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public MaterialModel getModel()
	{
		return model;
	}
	public void setMaterialService(MaterialIService materialService)
    {
        this.materialService = materialService;
    }
}
