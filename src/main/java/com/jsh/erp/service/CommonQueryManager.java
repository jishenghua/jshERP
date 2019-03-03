package com.jsh.erp.service;

import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Log;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.LogMapper;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.Tools.getLocalIp;

/**
 * @author jishenghua 752718920 2018-10-7 15:25:58
 */
@Service
public class CommonQueryManager {

    @Resource
    private InterfaceContainer container;

    @Resource
    private LogService logService;

    /**
     * 查询单条
     *
     * @param apiName 接口名称
     * @param id      ID
     */
    public Object selectOne(String apiName, String id) {
        if (StringUtil.isNotEmpty(apiName) && StringUtil.isNotEmpty(id)) {
            return container.getCommonQuery(apiName).selectOne(id);
        }
        return null;
    }

    /**
     * 查询
     * @param apiName
     * @param parameterMap
     * @return
     */
    public List<?> select(String apiName, Map<String, String> parameterMap) {
        if (StringUtil.isNotEmpty(apiName)) {
            return container.getCommonQuery(apiName).select(parameterMap);
        }
        return new ArrayList<Object>();
    }

    /**
     * 计数
     * @param apiName
     * @param parameterMap
     * @return
     */
    public Long counts(String apiName, Map<String, String> parameterMap) {
        if (StringUtil.isNotEmpty(apiName)) {
            return container.getCommonQuery(apiName).counts(parameterMap);
        }
        return BusinessConstants.DEFAULT_LIST_NULL_NUMBER;
    }

    /**
     * 插入
     * @param apiName
     * @param beanJson
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insert(String apiName, String beanJson, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(apiName)) {
            logService.insertLog(apiName, "新增", request);
            return container.getCommonQuery(apiName).insert(beanJson, request);
        }
        return 0;
    }

    /**
     * 更新
     * @param apiName
     * @param beanJson
     * @param id
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int update(String apiName, String beanJson, Long id, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(apiName)) {
            logService.insertLog(apiName, "更新,id:" + id, request);
            return container.getCommonQuery(apiName).update(beanJson, id);
        }
        return 0;
    }

    /**
     * 删除
     * @param apiName
     * @param id
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int delete(String apiName, Long id, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(apiName)) {
            logService.insertLog(apiName, "删除,id:" + id, request);
            return container.getCommonQuery(apiName).delete(id);
        }
        return 0;
    }

    /**
     * 批量删除
     * @param apiName
     * @param ids
     * @return
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDelete(String apiName, String ids, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(apiName)) {
            logService.insertLog(apiName, "批量删除,id集:" + ids, request);
            return container.getCommonQuery(apiName).batchDelete(ids);
        }
        return 0;
    }

    /**
     * 判断是否存在
     * @param apiName
     * @param id
     * @param name
     * @return
     */
    public int checkIsNameExist(String apiName, Long id, String name) {
        if (StringUtil.isNotEmpty(apiName)) {
            return container.getCommonQuery(apiName).checkIsNameExist(id, name);
        }
        return 0;
    }






}