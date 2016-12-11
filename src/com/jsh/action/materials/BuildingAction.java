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
import com.jsh.model.po.Depot;
import com.jsh.model.po.Building;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.materials.BuildingModel;
import com.jsh.service.materials.BuildingIService;
import com.jsh.util.PageUtil;

@SuppressWarnings("serial")
public class BuildingAction extends BaseAction<BuildingModel>
{
    private BuildingIService buildingService;
    private BuildingModel model = new BuildingModel();
    
    
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
            condition.put("ProjectId_n_eq", model.getProjectId());
            condition.put("Id_s_order", "asc");
            buildingService.find(pageUtil);
            mapData.put("buildingList", pageUtil.getPageList());
        }
        catch (Exception e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>查找系统基础数据信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }
	/**
	 * 增加单元
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加单元信息方法create()===================");
	    Boolean flag = false;
		try
		{
			Building building = new Building();
			building.setDepot(new Depot(model.getProjectId()));
			
			building.setName(model.getName());
			building.setRemark(model.getRemark());
			building.setEnabled(model.getEnabled());
			buildingService.create(building);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加单元信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加单元信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加单元", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加单元名称为  "+ model.getName() + " " + tipMsg + "！", "增加单元" + tipMsg));
		Log.infoFileSync("==================结束调用增加单元方法create()===================");
	}
	
	/**
	 * 删除单元
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除单元信息方法delete()================");
	    try 
	    {
	    	buildingService.delete(model.getBuildingID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getBuildingID() + "  的单元异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除单元", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除单元ID为  "+ model.getBuildingID() + " " + tipMsg + "！", "删除单元" + tipMsg));
	    Log.infoFileSync("====================结束调用删除单元信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新单元
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	Building building = buildingService.get(model.getBuildingID());
        	building.setDepot(new Depot(model.getProjectId()));
        	
			building.setName(model.getName());
			building.setRemark(model.getRemark());
			building.setEnabled(model.getEnabled());
        	buildingService.update(building);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改单元ID为 ： " + model.getBuildingID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改单元回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新单元", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新单元ID为  "+ model.getBuildingID() + " " + tipMsg + "！", "更新单元" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID单元
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	buildingService.batchDelete(model.getBuildingIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除单元ID为：" + model.getBuildingIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除单元", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除单元ID为  "+ model.getBuildingIDs() + " " + tipMsg + "！", "批量删除单元" + tipMsg));
	    return SUCCESS;
	}
	
	/**
	 * 查找单元信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Building> pageUtil = new  PageUtil<Building>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            buildingService.find(pageUtil);
            List<Building> dataList = pageUtil.getPageList();
            
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
                for(Building building:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", building.getId());
                    item.put("ProjectId", building.getDepot().getId());
                    item.put("ProjectName", building.getDepot().getName());
                    item.put("Name", building.getName());
                    item.put("Remark", building.getRemark());
                    item.put("Enabled", building.getEnabled());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找单元信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询单元信息结果异常", e);
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
        condition.put("Name_s_like", model.getName());
        condition.put("Id_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public BuildingModel getModel()
	{
		return model;
	}
	public void setBuildingService(BuildingIService buildingService)
    {
        this.buildingService = buildingService;
    }
}
