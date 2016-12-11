package com.jsh.service.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.util.ExceptionCodeConstants;
import com.jsh.dao.basic.UserBusinessIDAO;
import com.jsh.dao.basic.UserIDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.Basicuser;
import com.jsh.util.PageUtil;

public class UserService extends BaseService<Basicuser> implements UserIService
{
	private PageUtil<Basicuser> pageUtil = new  PageUtil<Basicuser>();
	private Map<String,Object> condition = new HashMap<String,Object>();
	private UserIDAO userDao;

	@Override
	public int validateUser(String username, String password)throws JshException
	{
		try
		{
			//全局变量 每次使用前清除
			condition.clear();
			
			/**默认是可以登录的*/
			List<Basicuser> list = null ;
			try
			{
				
		        condition.put("loginame_s_eq", username);
		        pageUtil.setAdvSearch(condition);
		        userDao.find(pageUtil);
				list = pageUtil.getPageList();
			}
			catch (Exception e)
			{
				Log.errorFileSync(">>>>>>>>访问验证用户姓名是否存在后台信息异常",e);
				return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
			}
			
			if(null !=list && list.size() == 0)
				return ExceptionCodeConstants.UserExceptionCode.USER_NOT_EXIST ;
			
			try
			{
				condition.put("loginame_s_eq", username);
				condition.put("password_s_eq", password);
		        pageUtil.setAdvSearch(condition);
		        userDao.find(pageUtil);
				list = pageUtil.getPageList();
			}
			catch (Exception e)
			{
				Log.errorFileSync(">>>>>>>>>>访问验证用户密码后台信息异常",e);
				return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
			}
			
			if(null !=list && list.size() == 0)
				return ExceptionCodeConstants.UserExceptionCode.USER_PASSWORD_ERROR;
			return ExceptionCodeConstants.UserExceptionCode.USER_CONDITION_FIT;
		}
		catch (Exception e) 
		{
			throw new JshException("unknown exception",e);
		}
	}

	@Override
	public Basicuser getUser(String username) throws JshException
	{
		//全局变量 每次使用前清除
		condition.clear();
		condition.put("loginame_s_eq", username);
        pageUtil.setAdvSearch(condition);
        userDao.find(pageUtil);
        List<Basicuser> list = pageUtil.getPageList();
		if(null != list && list.size() >0)
			return list.get(0); 
		else
			throw new JshException("no username exist");
	}
	
	@Override
	public Boolean checkIsNameExist(String field,String username, Long userID)throws JshException
	{
		condition.clear();
        condition.put(field + "_s_eq", username);
        condition.put("id_n_neq", userID);
        pageUtil.setAdvSearch(condition);
        userDao.find(pageUtil);
        
        List<Basicuser> dataList = pageUtil.getPageList();
        if(null != dataList && dataList.size() > 0)
            return true;
        return false;
	}
	
	//==============spring注入等公共方法，与业务无关=========================
	public void setUserDao(UserIDAO userDao)
	{
		this.userDao = userDao;
	}

	@Override
	protected Class<Basicuser> getEntityClass()
	{
		return Basicuser.class;
	}
}
