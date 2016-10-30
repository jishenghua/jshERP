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
import com.jsh.exception.AmsException;
import com.jsh.model.po.App;
import com.jsh.model.po.Functions;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.FunctionsModel;
import com.jsh.service.basic.FunctionsIService;
import com.jsh.service.basic.UserBusinessIService;
import com.jsh.util.common.PageUtil;

@SuppressWarnings("serial")
public class FunctionsAction extends BaseAction<FunctionsModel>
{
    private FunctionsIService functionsService;
    private UserBusinessIService userBusinessService;
    private FunctionsModel model = new FunctionsModel();
	/**
	 * 增加功能
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加功能信息方法create()===================");
	    Boolean flag = false;
		try
		{
			Functions functions = new Functions();
			functions.setNumber(model.getNumber());
			functions.setName(model.getName());
			functions.setPNumber(model.getPNumber());
			functions.setURL(model.getURL());
			functions.setState(model.getState());
			functions.setSort(model.getSort());
			functions.setEnabled(model.getEnabled());
			functions.setType(model.getType());
			
			functionsService.create(functions);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加功能信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加功能信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加功能", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加功能名称为  "+ model.getName() + " " + tipMsg + "！", "增加功能" + tipMsg));
		Log.infoFileSync("==================结束调用增加功能方法create()===================");
	}
	
	/**
	 * 删除功能
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除功能信息方法delete()================");
	    try 
	    {
	    	functionsService.delete(model.getFunctionsID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getFunctionsID() + "  的功能异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除功能", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除功能ID为  "+ model.getFunctionsID() + " " + tipMsg + "！", "删除功能" + tipMsg));
	    Log.infoFileSync("====================结束调用删除功能信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新功能
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	Functions functions = functionsService.get(model.getFunctionsID());
        	functions.setNumber(model.getNumber());
			functions.setName(model.getName());
			functions.setPNumber(model.getPNumber());
			functions.setURL(model.getURL());
			functions.setState(model.getState());
			functions.setSort(model.getSort());
			functions.setEnabled(model.getEnabled());
			functions.setType(model.getType());
        	functionsService.update(functions);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改功能ID为 ： " + model.getFunctionsID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改功能回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新功能", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新功能ID为  "+ model.getFunctionsID() + " " + tipMsg + "！", "更新功能" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID功能
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	functionsService.batchDelete(model.getFunctionsIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除功能ID为：" + model.getFunctionsIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除功能", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除功能ID为  "+ model.getFunctionsIDs() + " " + tipMsg + "！", "批量删除功能" + tipMsg));
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
	        flag = functionsService.checkIsNameExist("Name",model.getName(),"Id", model.getFunctionsID());
        } 
	    catch (DataAccessException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查功能名称为：" + model.getName() + " ID为： " + model.getFunctionsID() + " 是否存在异常！");
        }
	    finally
	    {
	        try 
	        {
                toClient(flag.toString());
            }
	        catch (IOException e) 
	        {
                Log.errorFileSync(">>>>>>>>>>>>回写检查功能名称为：" + model.getName() + " ID为： " + model.getFunctionsID() + " 是否存在异常！",e);
            }
	    }
	}
	
	/**
	 * 查找功能信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Functions> pageUtil = new  PageUtil<Functions>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            functionsService.find(pageUtil);
            List<Functions> dataList = pageUtil.getPageList();
            
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
                for(Functions functions:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", functions.getId());
                    item.put("Number", functions.getNumber());
                    item.put("Name", functions.getName());
                    item.put("PNumber", functions.getPNumber());
                    item.put("URL", functions.getURL());
                    item.put("State", functions.getState());
                    item.put("Sort", functions.getSort());
                    item.put("Enabled", functions.getEnabled());
                    item.put("Type", functions.getType());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找功能信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询功能信息结果异常", e);
        }
	}
    
    
	/**
	 * 角色对应功能显示
	 * @return
	 */
    public void findRoleFunctions()
	{
	    try 
	    {
	        PageUtil<Functions> pageUtil = new  PageUtil<Functions>();
            pageUtil.setPageSize(200);
            pageUtil.setAdvSearch(getCondition_RoleFunctions("0"));
            functionsService.find(pageUtil);
            List<Functions> dataList = pageUtil.getPageList();
            
            //开始拼接json数据
			  JSONObject outer = new JSONObject();
			  outer.put("id", 1);
	          outer.put("text", "功能列表");
	          outer.put("state", "open");
			  //存放数据json数组
			  JSONArray dataArray = new JSONArray();
			  if(null != dataList)
			  {
			      for(Functions functions:dataList)
			      {
			          JSONObject item = new JSONObject();
			          item.put("id", functions.getId());
				      item.put("text", functions.getName());
				      
				      //勾选判断1
				      Boolean flag = false;
					  try 
					  {
					      flag = userBusinessService.checkIsUserBusinessExist("Type",model.getUBType(),"KeyId",model.getUBKeyId(),"Value","["+functions.getId().toString()+"]");
				      } 
					  catch (DataAccessException e) 
					  {
				          Log.errorFileSync(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
				      }
				      if (flag==true){item.put("checked", true);}
				      //结束  
				      
				          pageUtil.setAdvSearch(getCondition_RoleFunctions(functions.getNumber()));
			              functionsService.find(pageUtil);
			              List<Functions> dataList1 = pageUtil.getPageList();
			              JSONArray dataArray1 = new JSONArray();
			              if(null != dataList1)
						  {
			            	  
			            	  for(Functions functions1:dataList1)
						      {  
			            		 item.put("state", "open");   //如果不为空，节点不展开
						         JSONObject item1 = new JSONObject();
						         item1.put("id", functions1.getId());
						         item1.put("text", functions1.getName());
							     					     
							          //勾选判断2
								      //Boolean flag = false;
									  try 
									  {
									      flag = userBusinessService.checkIsUserBusinessExist("Type",model.getUBType(),"KeyId",model.getUBKeyId(),"Value","["+functions1.getId().toString()+"]");
								      } 
									  catch (DataAccessException e) 
									  {
								          Log.errorFileSync(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
								      }
								      if (flag==true){item1.put("checked", true);}
								      //结束  
						            
							          pageUtil.setAdvSearch(getCondition_RoleFunctions(functions1.getNumber()));
						              functionsService.find(pageUtil);
						              List<Functions> dataList2 = pageUtil.getPageList();
						              JSONArray dataArray2 = new JSONArray();
						              if(null != dataList2)
									  {
						            	  
						            	  for(Functions functions2:dataList2)
									      {  
						            		 item1.put("state", "closed");   //如果不为空，节点不展开
									         JSONObject item2 = new JSONObject();
									         item2.put("id", functions2.getId());
									         item2.put("text", functions2.getName());
									         
										          //勾选判断3
											      //Boolean flag = false;
												  try 
												  {
												      flag = userBusinessService.checkIsUserBusinessExist("Type",model.getUBType(),"KeyId",model.getUBKeyId(),"Value","["+functions2.getId().toString()+"]");
											      } 
												  catch (DataAccessException e) 
												  {
											          Log.errorFileSync(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
											      }
											      if (flag==true){item2.put("checked", true);}
											      //结束  
											      
										          pageUtil.setAdvSearch(getCondition_RoleFunctions(functions2.getNumber()));
									              functionsService.find(pageUtil);
									              List<Functions> dataList3 = pageUtil.getPageList();
									              JSONArray dataArray3 = new JSONArray();
									              if(null != dataList3)
												  {
									            	  
									            	  for(Functions functions3:dataList3)
												      {  
									            		 item2.put("state", "closed");   //如果不为空，节点不展开
												         JSONObject item3 = new JSONObject();
												         item3.put("id", functions3.getId());
												         item3.put("text", functions3.getName());
											             
													          //勾选判断4
														      //Boolean flag = false;
															  try 
															  {
															      flag = userBusinessService.checkIsUserBusinessExist("Type",model.getUBType(),"KeyId",model.getUBKeyId(),"Value","["+functions3.getId().toString()+"]");
														      } 
															  catch (DataAccessException e) 
															  {
														          Log.errorFileSync(">>>>>>>>>>>>>>>>>设置角色对应的功能：类型" + model.getUBType() + " KeyId为： " + model.getUBKeyId() + " 存在异常！");
														      }
														      if (flag==true){item3.put("checked", true);}
														      //结束  
												         
												         dataArray3.add(item3);
												         item2.put("children", dataArray3);   
												      }
												  }
									         
										     dataArray2.add(item2);
										     item1.put("children", dataArray2);   
									      }
									  }
							     
								 dataArray1.add(item1);
								 item.put("children", dataArray1);  
						     }
			            	 
						  }
			              
				      dataArray.add(item);
			      }
			      outer.put("children", dataArray);
			  }
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
	 * 页面显示菜单
	 * @return
	 */
    public void findMenu()
	{
	    try 
	    {
	    	String fc=model.getHasFunctions(); //当前用户所拥有的功能列表，格式如：[1][2][5]
	        PageUtil<Functions> pageUtil = new  PageUtil<Functions>();
            pageUtil.setPageSize(200);
            pageUtil.setAdvSearch(getCondition_RoleFunctions(model.getPNumber()));
            functionsService.find(pageUtil);
            List<Functions> dataList = pageUtil.getPageList();
            
			  //存放数据json数组
			  JSONArray dataArray = new JSONArray();
			  if(null != dataList)
			  {
			      for(Functions functions:dataList)
			      {
			          JSONObject item = new JSONObject();
			          item.put("id", functions.getId());
			          
				          pageUtil.setAdvSearch(getCondition_RoleFunctions(functions.getNumber()));
			              functionsService.find(pageUtil);
			              List<Functions> dataList1 = pageUtil.getPageList();
			              JSONArray dataArray1 = new JSONArray();
			              if(dataList1.size()!=0)
						  {
			            	  item.put("text", functions.getName()); //是目录就没链接
			            	  for(Functions functions1:dataList1)
						      {  
			            		 item.put("state", "open");   //如果不为空，节点展开
						         JSONObject item1 = new JSONObject();
						         
							     pageUtil.setAdvSearch(getCondition_RoleFunctions(functions1.getNumber()));
						         functionsService.find(pageUtil);
						         List<Functions> dataList2 = pageUtil.getPageList();
						         if(fc.indexOf("["+functions1.getId().toString()+"]")!=-1||dataList2.size()!=0)
								 {
							              item1.put("id", functions1.getId());
							              JSONArray dataArray2 = new JSONArray();
							              if(dataList2.size()!=0)
										  {
							            	  item1.put("text", functions1.getName());//是目录就没链接
							            	  for(Functions functions2:dataList2)
										      {  
							            		 item1.put("state", "closed");   //如果不为空，节点不展开
										         JSONObject item2 = new JSONObject();
										         
										          pageUtil.setAdvSearch(getCondition_RoleFunctions(functions2.getNumber()));
									              functionsService.find(pageUtil);
									              List<Functions> dataList3 = pageUtil.getPageList();
									              if(fc.indexOf("["+functions2.getId().toString()+"]")!=-1||dataList3.size()!=0)
									              {  
											              item2.put("id", functions2.getId());
											              JSONArray dataArray3 = new JSONArray();
											              if(dataList3.size()!=0)
														  {
											            	  item2.put("text", functions2.getName());//是目录就没链接
											            	  for(Functions functions3:dataList3)
														      {  
											            		 item2.put("state", "closed");   //如果不为空，节点不展开
														         JSONObject item3 = new JSONObject();
														         item3.put("id", functions3.getId());
														         item3.put("text", functions3.getName());
														         //
														         dataArray3.add(item3);
														         item2.put("children", dataArray3);   
														      }
														  }
											              else
											              {
											            	  //不是目录，有链接
											            	  item2.put("text", "<a onclick=\"NewTab('"+functions2.getName()+"','"+functions2.getURL()+"')\">"+functions2.getName()+"</a>");
											              }
											         
												     dataArray2.add(item2);
												     item1.put("children", dataArray2);   
										         }
										      }
										  }
							              else
							              {
							            	  //不是目录，有链接
							            	  item1.put("text", "<a onclick=\"NewTab('"+functions1.getName()+"','"+functions1.getURL()+"')\">"+functions1.getName()+"</a>");
							              }
								     
									 dataArray1.add(item1);
									 item.put("children", dataArray1);  
							     }
						      }
						  }
			              else
			              {
			            	  //不是目录，有链接
			            	  item.put("text", "<a onclick=\"NewTab('"+functions.getName()+"','"+functions.getURL()+"')\">"+functions.getName()+"</a>");
			              }
			              
				      dataArray.add(item);
			      }
			  }
			  //回写查询结果
			  toClient(dataArray.toString());
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
        condition.put("Type_s_eq", model.getType());
        condition.put("Sort_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-角色对应功能
	 * @return
	 */
	private Map<String,Object> getCondition_RoleFunctions(String num)
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Enabled_n_eq", 1);
        condition.put("PNumber_s_eq", num);
        condition.put("Sort_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public FunctionsModel getModel()
	{
		return model;
	}
	public void setFunctionsService(FunctionsIService functionsService)
    {
        this.functionsService = functionsService;
    }

	public void setUserBusinessService(UserBusinessIService userBusinessService) {
		this.userBusinessService = userBusinessService;
	}
	
}
