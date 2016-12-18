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
import com.jsh.model.po.Person;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.materials.PersonModel;
import com.jsh.service.materials.PersonIService;
import com.jsh.util.PageUtil;
/*
 * 经手人管理
 * @author jishenghua  qq:752718920
*/
@SuppressWarnings("serial")
public class PersonAction extends BaseAction<PersonModel>
{
    private PersonIService personService;
    private PersonModel model = new PersonModel();
    
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
            personService.find(pageUtil);
            mapData.put("personList", pageUtil.getPageList());
        }
        catch (Exception e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>查找系统基础数据信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPersonByType()
    {
        Map<String,List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try
        {
            Map<String,Object> condition = pageUtil.getAdvSearch();
            condition.put("Type_s_eq", model.getType());
            condition.put("Id_s_order", "asc");
            personService.find(pageUtil);
            mapData.put("personList", pageUtil.getPageList());
        }
        catch (Exception e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>查找系统基础数据信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }
	
	/**
	 * 增加经手人
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加经手人信息方法create()===================");
	    Boolean flag = false;
		try
		{
			Person person = new Person();
			person.setDepot(new Depot(model.getProjectId()));
			
			person.setType(model.getType());
			person.setName(model.getName());
			personService.create(person);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加经手人信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加经手人信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加经手人", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加经手人名称为  "+ model.getName() + " " + tipMsg + "！", "增加经手人" + tipMsg));
		Log.infoFileSync("==================结束调用增加经手人方法create()===================");
	}
	
	/**
	 * 删除经手人
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除经手人信息方法delete()================");
	    try 
	    {
	    	personService.delete(model.getPersonID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getPersonID() + "  的经手人异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除经手人", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除经手人ID为  "+ model.getPersonID() + " " + tipMsg + "！", "删除经手人" + tipMsg));
	    Log.infoFileSync("====================结束调用删除经手人信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新经手人
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	Person person = personService.get(model.getPersonID());
        	person.setDepot(new Depot(model.getProjectId()));
        	
			person.setType(model.getType());
			person.setName(model.getName());
        	personService.update(person);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改经手人ID为 ： " + model.getPersonID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改经手人回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新经手人", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新经手人ID为  "+ model.getPersonID() + " " + tipMsg + "！", "更新经手人" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID经手人
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	personService.batchDelete(model.getPersonIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除经手人ID为：" + model.getPersonIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除经手人", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除经手人ID为  "+ model.getPersonIDs() + " " + tipMsg + "！", "批量删除经手人" + tipMsg));
	    return SUCCESS;
	}
	
	/**
	 * 查找经手人信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Person> pageUtil = new  PageUtil<Person>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            personService.find(pageUtil);
            List<Person> dataList = pageUtil.getPageList();
            
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Person person:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", person.getId());
                    item.put("ProjectId", person.getDepot().getId());
                    item.put("ProjectName", person.getDepot().getName());
                    item.put("Type", person.getType());
                    item.put("Name", person.getName());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找经手人信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询经手人信息结果异常", e);
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
        condition.put("Type_s_eq", model.getType());
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public PersonModel getModel()
	{
		return model;
	}
	public void setPersonService(PersonIService personService)
    {
        this.personService = personService;
    }
}
