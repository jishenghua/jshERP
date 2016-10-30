package com.jsh.base;

import java.io.Serializable;

import org.springframework.dao.DataAccessException;

import com.jsh.util.common.PageUtil;

public interface BaseIService<T>
{
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
	 * 删除
	 * @param id 对象ID
	 * @throws DataAccessException
	 */
	void delete(Long id)throws DataAccessException;
	
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
	void find(PageUtil<T> pageUtil) throws DataAccessException;
	
	/**
	 * 检查名称是否存在，页面唯一性效验使用
	 * @param filedName 效验的字段名称
	 * @param filedVale 校验值
	 * @param idFiled ID字段名称
	 * @param objectID 修改时对象ID
	 * @return true==存在 false==不存在
	 * @throws DataAccessException
	 */
	Boolean checkIsNameExist(String filedName,String filedVale,String idFiled,Long objectID) throws DataAccessException;
	
	/**
	 * 检查UserBusiness是否存在，页面唯一性效验使用
	 * @param TypeName 类型名称
	 * @param TypeVale 类型值
	 * @param KeyIdName 关键id
	 * @param KeyIdValue 关键值
	 * @param UBName 关系名称
	 * @param UBValue 关系值
	 * @return true==存在 false==不存在
	 * @throws DataAccessException
	 */
	Boolean checkIsUserBusinessExist(String TypeName,String TypeVale,String KeyIdName,String KeyIdValue,String UBName,String UBValue) throws DataAccessException;
	
	/**
	 * 检查UserBusiness是否存在，页面唯一性效验使用
	 * @param TypeName 类型名称
	 * @param TypeVale 类型值
	 * @param KeyIdName 关键id
	 * @param KeyIdValue 关键值
	 * @return true==存在 false==不存在
	 * @throws DataAccessException
	 */
	Boolean checkIsValueExist(String TypeName, String TypeVale, String KeyIdName, String KeyIdValue) throws DataAccessException;	
	
	
	
}
