package com.jsh.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.jsh.util.common.PageUtil;

/**
 * 常用增删改查操作
 * @author ji-sheng-hua 
 * @param <T>
 */
public interface BaseIDAO<T>
{

	/**
	 * 设置操作类对象
	 * @param paramClass
	 */
	void setPoJoClass(Class<T> paramClass);
	
	/**
	 * 增加
	 * @param t 对象
	 * @throws DataAccessException
	 */
	Serializable create(T t)throws DataAccessException;
	
	/**
     * 增加
     * @param t 对象
     * @throws DataAccessException
     */
    void save(T t)throws DataAccessException;
	
	/**
	 * 删除
	 * @param t 对象
	 * @throws DataAccessException
	 */
	void delete(T t)throws DataAccessException;
	
	/**
	 * 获取
	 * @param objID ID
	 * @return 对象
	 * @throws DataAccessException
	 */
	T get(Long objID)throws DataAccessException;
	
	/**
	 * 修改信息
	 * @param t 要修改的对象
	 * @throws DataAccessException
	 */
	void update(T t)throws DataAccessException;
	
	/**
	 * 批量删除信息
	 * @param 以逗号分割的ID
	 * @throws DataAccessException
	 */
	void batchDelete(String objIDs)throws DataAccessException;
	
	/**
	 * 查找列表
	 * @param pageUtil 分页工具类
	 * @throws DataAccessException
	 */
	void find(PageUtil<T> pageUtil)throws DataAccessException;
	
	/**
	 * 根据条件查询列表--没有分页信息
	 * @param conditon 查询条件
	 * @return 查询列表数据
	 */
	List<T> find(Map<String,Object> conditon)throws DataAccessException;
	
	/**
	 * 根据hql查询 --没有分页信息
	 * @param hql hibernate查询
	 * @return 查询列表数据
	 */
	List<T> find(String hql)throws DataAccessException;
	
	/**
	 * 根据搜索条件查询--分页
	 * @param conditon 查询条件
	 * @param pageSize 每页个数
	 * @param pageNo 页码
	 * @return 查询列表数据
	 * @throws DataAccessException
	 */
	List<T> find(Map<String,Object> conditon,int pageSize,int pageNo)throws DataAccessException;
	
	/**
	 * 根据hql查询--分页
	 * @param hql hibernate查询语句
	 * @param pageSize 每页个数
	 * @param pageNo 页码
	 * @return 查询列表数据
	 * @throws DataAccessException
	 */
	List<T> find(String hql,int pageSize,int pageNo)throws DataAccessException;
	
	/**
	 * 查找符合条件的总数
	 * @param conditon
	 * @return
	 * @throws DataAccessException
	 */
	Integer countSum(Map<String,Object> conditon)throws DataAccessException;
	
	/**
	 * 查找符合条件的总数
	 * @param hql
	 * @return
	 * @throws DataAccessException
	 */
	Integer countSum(String hql)throws DataAccessException;
}
