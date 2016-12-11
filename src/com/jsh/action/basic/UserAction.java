package com.jsh.action.basic;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;
import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.util.ExceptionCodeConstants;
import com.jsh.model.po.Basicuser;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.UserModel;
import com.jsh.service.basic.UserIService;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
/*
 * 用户管理
 * @author jishenghua  qq:752718920
*/
@SuppressWarnings("serial")
public class UserAction extends BaseAction<UserModel> 
{
	private UserModel model = new UserModel();
	private UserIService userService;
	
	/**
	 * 需要判断用户状态，用户名密码错误不能登录 ，黑名单用户不能登录，如果已经登录过，不再进行处理，直接进入管理页面
	 * @return
	 */
	public String login()
	{
		Log.infoFileSync("============用户登录 login 方法调用开始==============");
		String username = model.getLoginame().trim();
		String password = model.getPassword().trim();
		//因密码用MD5加密，需要对密码进行转化
		try
		{
			password = Tools.md5Encryp(password);
			System.out.println(password);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			Log.errorFileSync(">>>>>>>>>>>>>>转化MD5字符串错误 ：" + e.getMessage(), e);
		}
		
		//判断用户是否已经登录过，登录过不再处理
		Basicuser sessionUser = (Basicuser)getSession().get("user");
		if(null != sessionUser && username.equalsIgnoreCase(sessionUser.getLoginame())
		       && sessionUser.getPassword().equals(password))
		{			
			Log.infoFileSync("====用户 "+ username + "已经登录过, login 方法调用结束====");
			model.getShowModel().setMsgTip("user already login");
			/*return "login";*/
		}
		
		//获取用户状态
		int userStatus = -1;
		try
		{
			userStatus = userService.validateUser(username, password);
		}
		catch (Exception e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>用户  " + username + " 登录 login 方法 访问服务层异常====",e);
			model.getShowModel().setMsgTip("access service exception");
		}
		switch (userStatus)
		{
			case ExceptionCodeConstants.UserExceptionCode.USER_NOT_EXIST:
				model.getShowModel().setMsgTip("user is not exist");
				break;
			case ExceptionCodeConstants.UserExceptionCode.USER_PASSWORD_ERROR:
				model.getShowModel().setMsgTip("user password error");
				break;
			case ExceptionCodeConstants.UserExceptionCode.BLACK_USER:
				model.getShowModel().setMsgTip("user is black");
				break;
			case ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION:
				model.getShowModel().setMsgTip("access service error");
				break;
			default:
				try
				{
					//验证通过 ，可以登录，放入session，记录登录日志
					Basicuser user = userService.getUser(username);
					logService.create(new Logdetails(user, "登录系统", model.getClientIp(),
							new Timestamp(System.currentTimeMillis()), (short)0,"管理用户：" + username + " 登录系统",username + " 登录系统"));					
					model.getShowModel().setMsgTip("user can login");
					getSession().put("user", user);
				}
				catch (Exception e)
				{
					Log.errorFileSync(">>>>>>>>>>>>>>>查询用户名为:" + username + " ，用户信息异常", e);
				}
				break;
		}
		/*if(ExceptionCodeConstants.UserExceptionCode.USER_CONDITION_FIT == userStatus)
		    return "login";*/
		Log.infoFileSync("===============用户登录 login 方法调用结束===============");
		return SUCCESS;
	}
	
	/**
	 * 用户退出登录
	 * @return
	 */
	public String logout()
	{
		logService.create(new Logdetails(getUser(), "退出系统", model.getClientIp(),
				new Timestamp(System.currentTimeMillis()), (short)0,
				"管理用户：" + getUser().getLoginame() + " 退出系统",getUser().getLoginame() + " 退出系统"));
		getSession().remove("user");
		return SUCCESS;
	}
	
	/**
	 * 增加用户
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加用户方法===================");
	    Boolean flag = false;
		try
		{
		    Basicuser user = new Basicuser();
		    user.setDepartment(model.getDepartment());
		    user.setDescription(model.getDescription());
		    user.setEmail(model.getEmail());
//		    user.setIsmanager(model.getIsmanager());
		    user.setIsystem((short)1);
		    user.setIsmanager((short)1);
		    user.setLoginame(model.getLoginame());
		    String password ="123456";
		    //因密码用MD5加密，需要对密码进行转化
		    try 
		    {
				password=Tools.md5Encryp(password);
			} 
		    catch (NoSuchAlgorithmException e) 
		    {
					e.printStackTrace();
					Log.errorFileSync(">>>>>>>>>>>>>>转化MD5字符串错误 ：" + e.getMessage(), e);
			}
		    user.setPassword(password);
		    
		    user.setPhonenum(model.getPhonenum());
		    user.setPosition(model.getPosition());
		    user.setUsername(model.getUsername());
		    
			userService.create(user);
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加用户异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加用户回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加用户", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加用户名称为  "+ model.getUsername() + " " + tipMsg + "！", "增加用户" + tipMsg));
		Log.infoFileSync("==================结束调用增加用户方法===================");
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除用户信息方法delete()================");
	    try 
	    {
            userService.delete(model.getUserID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getUserID() + "  的用户异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除用户", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除用户ID为  "+ model.getUserID() + " " + tipMsg + "！", "删除用户" + tipMsg));
	    Log.infoFileSync("====================结束调用删除用户信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新用户
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
            Basicuser user = userService.get(model.getUserID());
            user.setDepartment(model.getDepartment());
		    user.setDescription(model.getDescription());
		    user.setEmail(model.getEmail());
		    //user.setIsmanager(model.getIsmanager());
		    user.setLoginame(model.getLoginame());
		    //user.setPassword(model.getPassword());
		    user.setPhonenum(model.getPhonenum());
		    user.setPosition(model.getPosition());
		    user.setUsername(model.getUsername());
		    userService.update(user);
		    
		    //看是否需要更新seesion中user
		    if(getUser().getId() == model.getUserID())
		    {
		        getSession().put("user", user);
		    }
		    
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改用户ID为 ： " + model.getUserID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改用户回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新用户", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新用户ID为  "+ model.getUserID() + " " + tipMsg + "！", "更新用户" + tipMsg));
	}
	
	/**
	 * 修改密码
	 */
	public void updatePwd()
	{
	    Integer flag = 0;
        try
        {
        	Basicuser user = getUser();
        	String orgPassword = Tools.md5Encryp(model.getOrgpwd());
        	String md5Pwd = Tools.md5Encryp(model.getPassword());
        	//必须和原始密码一致才可以更新密码
        	if(orgPassword.equalsIgnoreCase(user.getPassword()))
        	{
        		
                user.setPassword(md5Pwd);
                userService.update(user);
                
                //看是否需要更新seesion中user
//                if(getUser().getId() == model.getUserID())
//                {
//                    getSession().put("user", user);
//                }
                
                flag = 1;
                tipMsg = "成功";
                tipType = 0;
        	}
        	else
        	{
        		flag = 2;
        		tipMsg = "失败";
        		tipType = 1;
        	}
            
        } 
        catch (Exception e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改用户ID为 ： " + model.getUserID() + "密码信息失败", e);
            flag = 3;
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
                Log.errorFileSync(">>>>>>>>>>>>修改用户密码回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新用户", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新用户ID为  "+ model.getUserID() + "密码信息 " + tipMsg + "！", "更新用户" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID用户
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
            userService.batchDelete(model.getUserIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除用户ID为：" + model.getUserIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除用户", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除用户ID为  "+ model.getUserIDs() + " " + tipMsg + "！", "批量删除用户" + tipMsg));
	    return SUCCESS;
	}
	
	/**
	 * 检查输入名称是否存在
	 */
	public void checkIsNameExist()
	{
	    Boolean flag = false;
	    String fieldName = "";
	    String fieldValue = "";
	    try 
	    {
	    	if(0 == model.getCheckFlag())
	    	{
	    		fieldName = "username";
	    		fieldValue = model.getUsername();
	    	}
	    	else
	    	{
	    		fieldName = "loginame";
	    		fieldValue = model.getLoginame();
	    	}
    		flag = userService.checkIsNameExist(fieldName,fieldValue, model.getUserID());
        } 
	    catch (Exception e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查用户名称为：" + fieldValue + " ID为： " + model.getUserID() + " 是否存在异常！");
        }
	    finally
	    {
	        try 
	        {
                toClient(flag.toString());
            }
	        catch (IOException e) 
	        {
                Log.errorFileSync(">>>>>>>>>>>>回写检查用户名称为：" + fieldValue + " ID为： " + model.getUserID() + " 是否存在异常！",e);
            }
	    }
	}
	
	/**
	 * 查找用户信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Basicuser> pageUtil = new  PageUtil<Basicuser>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            userService.find(pageUtil);
            List<Basicuser> dataList = pageUtil.getPageList();
            
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
                for(Basicuser user:dataList)
                {
                	
                    JSONObject item = new JSONObject();
                    item.put("id", user.getId());
                    item.put("username", user.getUsername());
                    item.put("loginame", Tools.dealNullStr(user.getLoginame()));
                    item.put("password", Tools.dealNullStr(user.getPassword()));
                    item.put("position", Tools.dealNullStr(user.getPosition()));
                    item.put("department", Tools.dealNullStr(user.getDepartment()));
                    item.put("email", Tools.dealNullStr(user.getEmail()));
                    item.put("phonenum", Tools.dealNullStr(user.getPhonenum()));
                    item.put("ismanager", user.getIsmanager()== (short)0?"是":"否");
                    item.put("isystem",user.getIsystem() == (short)0?"是":"否");
                    item.put("status", user.getStatus());
                    item.put("description", Tools.dealNullStr(user.getDescription()));
                    item.put("remark",user.getRemark());
                    item.put("op", user.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>查找用户信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>回写查询用户信息结果异常", e);
        }
	}
	
	/**
	 * 拼接搜索条件
	 * @return 拼接后的条件
	 */
	private Map<String,Object> getCondition()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("username_s_like", model.getUsername());
        condition.put("loginame_s_like", model.getLoginame());
        condition.put("id_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public UserModel getModel()
	{
		return model;
	}
	public void setUserService(UserIService userService)
	{
		this.userService = userService;
	}
}
