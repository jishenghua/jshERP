package com.jsh.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jsh.util.JshException;
import com.jsh.model.po.Basicuser;
import com.jsh.util.PageUtil;
/**
 * 底层服务层
 * @author ji-sheng-hua qq752718920
 * @param <T>
 */
public abstract class BaseService<T> implements BaseIService<T>
{
	/**
	 * Dao对象
	 */
	private BaseIDAO<T> baseDao;
	
	protected Class<T> entityClass;

	public void setBaseDao(BaseIDAO<T> baseDao)
	{
		this.baseDao = baseDao;
		setPoJoClass(getEntityClass());
	}

	protected BaseIDAO<T> getBaseDao()
	{
		return this.baseDao;
	}

	private void setPoJoClass(Class<T> c)
	{
		this.baseDao.setPoJoClass(c);
	}

	protected abstract Class<T> getEntityClass();

	@Override
	public Serializable create(T t) throws DataAccessException
	{
		return baseDao.create(t);
	}
	
	@Override
    public void save(T t) throws DataAccessException
    {
        baseDao.save(t);
    }

	@Override
	public void delete(T t) throws DataAccessException
	{
		baseDao.delete(t);
	}

	@Override
	public void delete(Long id) throws DataAccessException
	{
		baseDao.batchDelete(id.toString());
	}

	@Override
	public T get(Long objID) throws DataAccessException
	{
		return baseDao.get(objID);
	}

	@Override
	public void update(T t) throws DataAccessException
	{
		baseDao.update(t);
	}

	@Override
	public void batchDelete(String objIDs) throws DataAccessException
	{
		baseDao.batchDelete(objIDs);
	}

	@Override
	public void find(PageUtil<T> pageUtil) throws DataAccessException
	{
		baseDao.find(pageUtil);
	}
	
	@Override
	public Boolean checkIsNameExist(String filedName, String filedVale,String idFiled,Long objectID) throws DataAccessException
	{
		PageUtil<T> pageUtil = new  PageUtil<T>();
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put(filedName + "_s_eq", filedVale);
        condition.put(idFiled + "_n_neq", objectID);
        pageUtil.setAdvSearch(condition);
        baseDao.find(pageUtil);
        
        List<T> dataList = pageUtil.getPageList();
        if(null != dataList && dataList.size() > 0)
            return true;
        return false;
	}
	
	@Override
	public Boolean checkIsUserBusinessExist(String TypeName,String TypeVale,String KeyIdName,String KeyIdValue,String UBName,String UBValue) throws DataAccessException
	{
		PageUtil<T> pageUtil = new  PageUtil<T>();
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put(TypeName + "_s_eq", TypeVale);
        condition.put(KeyIdName + "_s_eq", KeyIdValue);
        condition.put(UBName + "_s_like", UBValue);
        pageUtil.setAdvSearch(condition);
        baseDao.find(pageUtil);
        
        List<T> dataList = pageUtil.getPageList();
        if(null != dataList && dataList.size() > 0)
            return true;
        return false;
	}
	
	@Override
	public Boolean checkIsValueExist(String TypeName, String TypeVale, String KeyIdName, String KeyIdValue) throws DataAccessException
	{
		PageUtil<T> pageUtil = new  PageUtil<T>();
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put(TypeName + "_s_eq", TypeVale);
        condition.put(KeyIdName + "_s_eq", KeyIdValue);
        pageUtil.setAdvSearch(condition);
        baseDao.find(pageUtil);
        
        List<T> dataList = pageUtil.getPageList();
        if(null != dataList && dataList.size() > 0)
            return true;
        return false;
	}
}
