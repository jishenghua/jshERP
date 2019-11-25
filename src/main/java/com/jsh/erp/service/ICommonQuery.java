package com.jsh.erp.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 通用查询接口
 * 功能：1、单条查询 2、分页+搜索 3、查询数量
 *
 * @author jishenghua
 * @version 1.0
 */
public interface ICommonQuery {
    /**
     * 根据id查询明细。
     *
     * @param id 资源id
     * @return 资源
     */
    Object selectOne(Long id) throws Exception;

    /**
     * 自定义查询
     *
     * @param parameterMap 查询参数
     * @return 查询结果
     */
    List<?> select(Map<String, String> parameterMap) throws Exception;

    /**
     * 查询数量
     *
     * @param parameterMap 查询参数
     * @return 查询结果
     */
    Long counts(Map<String, String> parameterMap) throws Exception;

    /**
     * 新增数据
     *
     * @param beanJson
     * @return
     */
    int insert(String beanJson, HttpServletRequest request) throws Exception;

    /**
     * 更新数据
     *
     * @param beanJson
     * @return
     */
    int update(String beanJson, Long id, HttpServletRequest request) throws Exception;

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    int delete(Long id, HttpServletRequest request) throws Exception;

    /**
     * 批量删除数据
     *
     * @param ids
     * @return
     */
    int batchDelete(String ids, HttpServletRequest request) throws Exception;

    /**
     * 查询名称是否存在
     *
     * @param id
     * @return
     */
    int checkIsNameExist(Long id, String name) throws Exception;
}