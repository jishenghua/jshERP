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
import com.jsh.util.JshException;
import com.jsh.model.po.Depot;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Role;
import com.jsh.model.vo.basic.DepotModel;
import com.jsh.service.basic.DepotIService;
import com.jsh.service.basic.UserBusinessIService;
import com.jsh.util.PageUtil;
/**
 * 仓库管理
 * @author jishenghua  qq:7-5-2-7-1-8-9-2-0
 */
@SuppressWarnings("serial")
public class DepotAction extends BaseAction<DepotModel>
{
    private DepotIService depotService;
    private UserBusinessIService userBusinessService;
    private DepotModel model = new DepotModel();
    
    
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
            condition.put("sort_s_order", "asc");
            depotService.find(pageUtil);
            mapData.put("depotList", pageUtil.getPageList());
        }
        catch (Exception e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>查找系统基础数据信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }
    
	/**
	 * 增加仓库
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加仓库信息方法create()===================");
	    Boolean flag = false;
		try
		{
			Depot depot = new Depot();
			depot.setName(model.getName());
			depot.setType(model.getType());
			depot.setSort(model.getSort());
			depot.setRemark(model.getRemark());
			depotService.create(depot);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加仓库信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加仓库信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加仓库", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加仓库名称为  "+ model.getName() + " " + tipMsg + "！", "增加仓库" + tipMsg));
		Log.infoFileSync("==================结束调用增加仓库方法create()===================");
	}
	
	/**
	 * 删除仓库
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除仓库信息方法delete()================");
	    try 
	    {
	    	depotService.delete(model.getDepotID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getDepotID() + "  的仓库异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除仓库", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除仓库ID为  "+ model.getDepotID() + " " + tipMsg + "！", "删除仓库" + tipMsg));
	    Log.infoFileSync("====================结束调用删除仓库信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新仓库
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	Depot depot = depotService.get(model.getDepotID());
        	depot.setName(model.getName());
			depot.setType(model.getType());
        	depot.setSort(model.getSort());
        	depot.setRemark(model.getRemark());
        	depotService.update(depot);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改仓库ID为 ： " + model.getDepotID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改仓库回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新仓库", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新仓库ID为  "+ model.getDepotID() + " " + tipMsg + "！", "更新仓库" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID仓库
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	depotService.batchDelete(model.getDepotIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除仓库ID为：" + model.getDepotIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除仓库", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除仓库ID为  "+ model.getDepotIDs() + " " + tipMsg + "！", "批量删除仓库" + tipMsg));
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
	        flag = depotService.checkIsNameExist("name",model.getName(),"id", model.getDepotID());
        } 
	    catch (DataAccessException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查仓库名称为：" + model.getName() + " ID为： " + model.getDepotID() + " 是否存在异常！");
        }
	    finally
	    {
	        try 
	        {
                toClient(flag.toString());
            }
	        catch (IOException e) 
	        {
                Log.errorFileSync(">>>>>>>>>>>>回写检查仓库名称为：" + model.getName() + " ID为： " + model.getDepotID() + " 是否存在异常！",e);
            }
	    }
	}
	
	/**
	 * 查找仓库信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Depot> pageUtil = new  PageUtil<Depot>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            depotService.find(pageUtil);
            List<Depot> dataList = pageUtil.getPageList();
            
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Depot depot:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", depot.getId());
                    //供应商名称
                    item.put("name", depot.getName());
					item.put("type", depot.getType());
                    item.put("sort", depot.getSort());
                    item.put("remark", depot.getRemark());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找仓库信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询仓库信息结果异常", e);
        }
	}

	/**
	 * 查找礼品卡-虚拟仓库
	 * @return
	 */
	public void findGiftByType()
	{
		try
		{
			PageUtil<Depot> pageUtil = new  PageUtil<Depot>();
			pageUtil.setPageSize(0);
			pageUtil.setCurPage(0);
			pageUtil.setAdvSearch(getConditionByType());
			depotService.find(pageUtil);
			List<Depot> dataList = pageUtil.getPageList();
			//存放数据json数组
			JSONArray dataArray = new JSONArray();
			if(null != dataList)
			{
				for(Depot depot:dataList)
				{
					JSONObject item = new JSONObject();
					item.put("id", depot.getId());
					//仓库名称
					item.put("name", depot.getName());
					dataArray.add(item);
				}
			}
			//回写查询结果
			toClient(dataArray.toString());
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>查找仓库信息异常", e);
		}
		catch (IOException e)
		{
			Log.errorFileSync(">>>>>>>>>回写查询仓库信息结果异常", e);
		}
	}
	
	/**
	 * 用户对应仓库显示
	 * @return
	 */
    public void findUserDepot()
	{
	    try 
	    {
	        PageUtil<Depot> pageUtil = new  PageUtil<Depot>();
            pageUtil.setPageSize(100);
            //pageUtil.setCurPage(model.getPageNo());

            pageUtil.setAdvSearch(getCondition_UserDepot());
            depotService.find(pageUtil);
            List<Depot> dataList = pageUtil.getPageList();
            
            //开始拼接json数据
			  JSONObject outer = new JSONObject();
			  outer.put("id", 1);
	          outer.put("text", "仓库列表");
	          outer.put("state", "open");
			  //存放数据json数组
			  JSONArray dataArray = new JSONArray();
			  if(null != dataList)
			  {
			      for(Depot depot:dataList)
			      {
			          JSONObject item = new JSONObject();
			          item.put("id", depot.getId());
				      item.put("text", depot.getName());
				        //勾选判断1
				        Boolean flag = false;
					    try 
					    {
					        flag = userBusinessService.checkIsUserBusinessExist("Type",model.getUBType(),"KeyId",model.getUBKeyId(),"Value","["+depot.getId().toString()+"]");
				        } 
					    catch (DataAccessException e) 
					    {
				            Log.errorFileSync(">>>>>>>>>>>>>>>>>设置用户对应的仓库：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
				        }
				        if (flag==true){item.put("checked", true);}
				        //结束  
				      dataArray.add(item);
			      }
			  }
			  outer.put("children", dataArray);
			  //回写查询结果
			  toClient("["+outer.toString()+"]");
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找仓库异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询仓库结果异常", e);
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
        condition.put("name_s_like", model.getName());
        condition.put("remark_s_like", model.getRemark());
		condition.put("type_n_eq", model.getType());  //0-仓库，1-礼品卡
        condition.put("sort_s_order", "asc");
        return condition;
    }

	/**
	 * 拼接搜索条件
	 * @return
	 */
	private Map<String,Object> getConditionByType()
	{
		/**
		 * 拼接搜索条件
		 */
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("type_n_eq", model.getType());  //0-仓库，1-礼品卡
		condition.put("sort_s_order", "asc");
		return condition;
	}
	
	/**
	 * 拼接搜索条件-用户对应部门
	 * @return
	 */
	private Map<String,Object> getCondition_UserDepot()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("sort_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public DepotModel getModel()
	{
		return model;
	}
	public void setDepotService(DepotIService depotService)
    {
        this.depotService = depotService;
    }

	public void setUserBusinessService(UserBusinessIService userBusinessService) {
		this.userBusinessService = userBusinessService;
	}
	
}
