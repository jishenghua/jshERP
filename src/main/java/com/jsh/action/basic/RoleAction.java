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
import com.jsh.model.po.App;
import com.jsh.model.po.Role;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.RoleModel;
import com.jsh.service.basic.RoleIService;
import com.jsh.service.basic.UserBusinessIService;
import com.jsh.util.PageUtil;
/*
 * 角色管理
 * @author jishenghua  qq:7-5-2-7-1-8-9-2-0
*/
@SuppressWarnings("serial")
public class RoleAction extends BaseAction<RoleModel>
{
    private RoleIService roleService;
    private UserBusinessIService userBusinessService;
    private RoleModel model = new RoleModel();
	/**
	 * 增加角色
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加角色信息方法create()===================");
	    Boolean flag = false;
		try
		{
			Role role = new Role();
			role.setName(model.getName());
			roleService.create(role);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加角色信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加角色信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加角色", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加角色名称为  "+ model.getName() + " " + tipMsg + "！", "增加角色" + tipMsg));
		Log.infoFileSync("==================结束调用增加角色方法create()===================");
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除角色信息方法delete()================");
	    try 
	    {
	    	roleService.delete(model.getRoleID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getRoleID() + "  的角色异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除角色", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除角色ID为  "+ model.getRoleID() + " " + tipMsg + "！", "删除角色" + tipMsg));
	    Log.infoFileSync("====================结束调用删除角色信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新角色
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	Role role = roleService.get(model.getRoleID());
        	role.setName(model.getName());
        	roleService.update(role);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改角色ID为 ： " + model.getRoleID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改角色回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新角色", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新角色ID为  "+ model.getRoleID() + " " + tipMsg + "！", "更新角色" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID角色
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	roleService.batchDelete(model.getRoleIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除角色ID为：" + model.getRoleIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除角色", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除角色ID为  "+ model.getRoleIDs() + " " + tipMsg + "！", "批量删除角色" + tipMsg));
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
	        flag = roleService.checkIsNameExist("name",model.getName(),"Id", model.getRoleID());
        } 
	    catch (DataAccessException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查角色名称为：" + model.getName() + " ID为： " + model.getRoleID() + " 是否存在异常！");
        }
	    finally
	    {
	        try 
	        {
                toClient(flag.toString());
            }
	        catch (IOException e) 
	        {
                Log.errorFileSync(">>>>>>>>>>>>回写检查角色名称为：" + model.getName() + " ID为： " + model.getRoleID() + " 是否存在异常！",e);
            }
	    }
	}
	
	/**
	 * 查找角色信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Role> pageUtil = new  PageUtil<Role>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            roleService.find(pageUtil);
            List<Role> dataList = pageUtil.getPageList();
            
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
                for(Role role:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", role.getId());
                    //供应商名称
                    item.put("Name", role.getName());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找角色信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询角色信息结果异常", e);
        }
	}
	
	/**
	 * 用户对应角色显示
	 * @return
	 */
    public void findUserRole()
	{
	    try 
	    {
	        PageUtil<Role> pageUtil = new  PageUtil<Role>();
            pageUtil.setPageSize(100);
            //pageUtil.setCurPage(model.getPageNo());

            pageUtil.setAdvSearch(getCondition_UserRole());
            roleService.find(pageUtil);
            List<Role> dataList = pageUtil.getPageList();
            
            //开始拼接json数据
			  JSONObject outer = new JSONObject();
			  outer.put("id", 1);
	          outer.put("text", "角色列表");
	          outer.put("state", "open");
			  //存放数据json数组
			  JSONArray dataArray = new JSONArray();
			  if(null != dataList)
			  {
			      for(Role role:dataList)
			      {
			          JSONObject item = new JSONObject();
			          item.put("id", role.getId());
				      item.put("text", role.getName());
				        //勾选判断1
				        Boolean flag = false;
					    try 
					    {
					        flag = userBusinessService.checkIsUserBusinessExist("Type",model.getUBType(),"KeyId",model.getUBKeyId(),"Value","["+role.getId().toString()+"]");
				        } 
					    catch (DataAccessException e) 
					    {
				            Log.errorFileSync(">>>>>>>>>>>>>>>>>设置用户对应的角色：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找角色异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询角色结果异常", e);
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
        condition.put("Name_s_like", model.getName());
        condition.put("Id_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-用户对应角色
	 * @return
	 */
	private Map<String,Object> getCondition_UserRole()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Id_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public RoleModel getModel()
	{
		return model;
	}
	public void setRoleService(RoleIService roleService)
    {
        this.roleService = roleService;
    }
	public void setUserBusinessService(UserBusinessIService userBusinessService) {
		this.userBusinessService = userBusinessService;
	}
}
