package com.jsh.action.basic;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.exception.JshException;
import com.jsh.model.po.App;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.AppModel;
import com.jsh.service.basic.AppIService;
import com.jsh.service.basic.UserBusinessIService;
import com.jsh.util.common.PageUtil;
/**
 * 应用
 * @author ji_sheng_hua
 */
@SuppressWarnings("serial")
public class AppAction extends BaseAction<AppModel>
{
    private AppIService appService;
    private UserBusinessIService userBusinessService;
    private AppModel model = new AppModel();


	/**
	 * 增加应用
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加应用方法create()===================");
	    Boolean flag = false;
		try
		{
			App app = new App();
			app.setNumber(model.getNumber());
			app.setName(model.getName());
			app.setType(model.getType());
			
			try {
				if(model.getIcon()!=null)
				{
					String path = ServletActionContext.getServletContext().getRealPath("/upload/images/deskIcon"); 
					String iconName=model.getIcon();
					File file1 = new File(iconName);  //文件
					String FileName = file1.getName();  //获取文件名
					app.setIcon(FileName); //设置图片ICON
					InputStream is = new FileInputStream(iconName);
					File file = new File(path, FileName);
	       			OutputStream os = new FileOutputStream(file);
	       			byte[] b = new byte[1024];
	       			int bs = 0;
	       			while ((bs = is.read(b)) > 0) {
	       				os.write(b, 0, bs);
	       			}
	       			is.close();
	       			os.close();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			app.setURL(model.getURL());
			app.setWidth(model.getWidth());
			app.setHeight(model.getHeight());
			app.setReSize(model.getReSize());
			app.setOpenMax(model.getOpenMax());
			app.setFlash(model.getFlash());
			app.setZL(model.getZL());
			app.setSort(model.getSort());
			app.setRemark(model.getRemark());
			app.setEnabled(model.getEnabled());		
			appService.create(app);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加应用异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加应用回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加应用", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加应用名称为  "+ model.getName() + " " + tipMsg + "！", "增加应用" + tipMsg));
		Log.infoFileSync("==================结束调用增加应用方法create()===================");
	}
	
	/**
	 * 删除应用
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除应用方法delete()================");
	    try 
	    {
	    	appService.delete(model.getAppID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getAppID() + "  的应用异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除应用", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除应用ID为  "+ model.getAppID() + " " + tipMsg + "！", "删除应用" + tipMsg));
	    Log.infoFileSync("====================结束调用删除应用方法delete()================");
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
        	App app = appService.get(model.getAppID());
			app.setNumber(model.getNumber());
			app.setName(model.getName());
			app.setType(model.getType());
			//app.setIcon(model.getIcon());
			app.setURL(model.getURL());
			app.setWidth(model.getWidth());
			app.setHeight(model.getHeight());
			app.setReSize(model.getReSize());
			app.setOpenMax(model.getOpenMax());
			app.setFlash(model.getFlash());
			app.setZL(model.getZL());
			app.setSort(model.getSort());
			app.setRemark(model.getRemark());
			app.setEnabled(model.getEnabled());		
        	appService.update(app);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改应用ID为 ： " + model.getAppID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改应用回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新应用", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新应用ID为  "+ model.getAppID() + " " + tipMsg + "！", "更新应用" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID应用
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	appService.batchDelete(model.getAppIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除应用ID为：" + model.getAppIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除应用", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除应用ID为  "+ model.getAppIDs() + " " + tipMsg + "！", "批量删除应用" + tipMsg));
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
	        flag = appService.checkIsNameExist("name",model.getName(),"Id", model.getAppID());
        } 
	    catch (DataAccessException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查应用名称为：" + model.getName() + " ID为： " + model.getAppID() + " 是否存在异常！");
        }
	    finally
	    {
	        try 
	        {
                toClient(flag.toString());
            }
	        catch (IOException e) 
	        {
                Log.errorFileSync(">>>>>>>>>>>>回写检查应用名称为：" + model.getName() + " ID为： " + model.getAppID() + " 是否存在异常！",e);
            }
	    }
	}
	
	/**
	 * 查找应用信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<App> pageUtil = new  PageUtil<App>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            appService.find(pageUtil);
            List<App> dataList = pageUtil.getPageList();
            
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
                for(App app:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", app.getId());
                    //应用名称
                    item.put("Number", app.getNumber());
                    item.put("Name", app.getName());
                    item.put("Type", app.getType());
                    item.put("Icon", app.getIcon());
                    item.put("URL", app.getURL());
                    item.put("Width", app.getWidth());
                    item.put("Height", app.getHeight());
                    item.put("ReSize", app.getReSize());
                    item.put("OpenMax", app.getOpenMax());
                    item.put("Flash", app.getFlash());
                    item.put("ZL", app.getZL());
                    item.put("Sort", app.getSort());
                    item.put("Remark", app.getRemark());
                    item.put("Enabled", app.getEnabled());	
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找应用异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询应用结果异常", e);
        }
	}
    
	/**
	 * 桌面应用显示
	 * @return
	 */
    public void findDesk()
	{
	    try 
	    {
	        PageUtil<App> pageUtil = new  PageUtil<App>();
            pageUtil.setPageSize(100);
            //pageUtil.setCurPage(model.getPageNo());
            
            JSONObject outer = new JSONObject();
            
            //下面是dock
            pageUtil.setAdvSearch(getCondition_dock());
            appService.find(pageUtil);
            List<App> dataList1 = pageUtil.getPageList();
            
            //开始拼接json数据
            //存放数据json数组
            JSONArray dataArray1 = new JSONArray();
            if(null != dataList1)
            {
                for(App app:dataList1)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", app.getId());
                    item.put("title", app.getName());
                    item.put("type", app.getType());
                    item.put("icon", "../../upload/images/deskIcon/"+app.getIcon());
                    item.put("url", app.getURL());
                    item.put("width", app.getWidth());
                    item.put("height", app.getHeight());
                    item.put("isresize", app.getReSize());
                    item.put("isopenmax", app.getOpenMax());
                    item.put("isflash", app.getFlash());
                    dataArray1.add(item);
                }
            }
            outer.put("dock", dataArray1);
            
            //下面是desk
            pageUtil.setAdvSearch(getCondition_desk());
            appService.find(pageUtil);
            List<App> dataList2 = pageUtil.getPageList();
            
            //开始拼接json数据
            //存放数据json数组
            JSONArray dataArray2 = new JSONArray();
            if(null != dataList2)
            {
                for(App app:dataList2)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", app.getId());
                    item.put("title", app.getName());
                    item.put("type", app.getType());
                    item.put("icon", "../../upload/images/deskIcon/"+app.getIcon());
                    item.put("url", "../../pages/common/menu.jsp?appID="+app.getNumber()+"&id="+app.getId());
                    item.put("width", app.getWidth());
                    item.put("height", app.getHeight());
                    item.put("isresize", app.getReSize());
                    item.put("isopenmax", app.getOpenMax());
                    item.put("isflash", app.getFlash());
                    dataArray2.add(item);
                }
            }
            outer.put("desk", dataArray2);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找应用异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询应用结果异常", e);
        }
	}
    
    
	/**
	 * 角色对应应用显示
	 * @return
	 */
    public void findRoleAPP()
	{
	    try 
	    {
	        PageUtil<App> pageUtil = new  PageUtil<App>();
            pageUtil.setPageSize(100);
            //pageUtil.setCurPage(model.getPageNo());

            pageUtil.setAdvSearch(getCondition_RoleAPP());
            appService.find(pageUtil);
            List<App> dataList = pageUtil.getPageList();
            
            //开始拼接json数据
			  JSONObject outer = new JSONObject();
			  outer.put("id", 1);
	          outer.put("text", "应用列表");
	          outer.put("state", "open");
			  //存放数据json数组
			  JSONArray dataArray = new JSONArray();
			  if(null != dataList)
			  {
			      for(App app:dataList)
			      {
			          JSONObject item = new JSONObject();
			          item.put("id", app.getId());
				      item.put("text", app.getName());
				        //勾选判断1
				        Boolean flag = false;
					    try 
					    {
					        flag = userBusinessService.checkIsUserBusinessExist("Type",model.getUBType(),"KeyId",model.getUBKeyId(),"Value","["+app.getId().toString()+"]");
				        } 
					    catch (DataAccessException e) 
					    {
				            Log.errorFileSync(">>>>>>>>>>>>>>>>>设置角色对应的应用：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找应用异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询应用结果异常", e);
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
        condition.put("Type_s_like", model.getType());
        condition.put("Sort_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-桌面dock
	 * @return
	 */
	private Map<String,Object> getCondition_dock()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("ZL_s_eq", "dock");
        condition.put("Enabled_n_eq", 1);
        condition.put("Sort_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-桌面desk
	 * @return
	 */
	private Map<String,Object> getCondition_desk()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("ZL_s_eq", "desk");
        condition.put("Enabled_n_eq", 1);
        condition.put("Sort_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-角色对应应用
	 * @return
	 */
	private Map<String,Object> getCondition_RoleAPP()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Enabled_n_eq", 1);
        condition.put("Sort_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public AppModel getModel()
	{
		return model;
	}
	public void setAppService(AppIService appService)
    {
        this.appService = appService;
    }

	public void setUserBusinessService(UserBusinessIService userBusinessService) {
		this.userBusinessService = userBusinessService;
	}
	
}
