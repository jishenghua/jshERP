package com.jsh.erp.service.log;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Log;
import com.jsh.erp.datasource.entities.LogExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.LogMapper;
import com.jsh.erp.datasource.mappers.LogMapperEx;
import com.jsh.erp.datasource.vo.LogVo4List;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.jsh.erp.utils.Tools.getLocalIp;

@Service
public class LogService {
    private Logger logger = LoggerFactory.getLogger(LogService.class);
    @Resource
    private LogMapper logMapper;

    @Resource
    private LogMapperEx logMapperEx;

    public Log getLog(long id)throws Exception {
        Log result=null;
        try{
            result=logMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    public List<Log> getLog()throws Exception {
        LogExample example = new LogExample();
        List<Log> list=null;
        try{
            list=logMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public List<LogVo4List> select(String operation, Integer usernameID, String clientIp, Integer status, String beginTime, String endTime,
                                   String contentdetails, int offset, int rows)throws Exception {
        List<LogVo4List> list=null;
        try{
            list=logMapperEx.selectByConditionLog(operation, usernameID, clientIp, status, beginTime, endTime,
                    contentdetails, offset, rows);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public Long countLog(String operation, Integer usernameID, String clientIp, Integer status, String beginTime, String endTime,
                        String contentdetails)throws Exception {
        Long result=null;
        try{
            result=logMapperEx.countsByLog(operation, usernameID, clientIp, status, beginTime, endTime, contentdetails);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertLog(String beanJson, HttpServletRequest request) throws Exception{
        Log log = JSONObject.parseObject(beanJson, Log.class);
        int result=0;
        try{
            result=logMapper.insertSelective(log);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateLog(String beanJson, Long id)throws Exception {
        Log log = JSONObject.parseObject(beanJson, Log.class);
        log.setId(id);
        int result=0;
        try{
            result=logMapper.updateByPrimaryKeySelective(log);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteLog(Long id)throws Exception {
        int result=0;
        try{
            result=logMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteLog(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        LogExample example = new LogExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=logMapper.deleteByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    /**
     * 获取用户id
     * @param request
     * @return
     */
    public Long getUserId(HttpServletRequest request) throws Exception{
        Object userInfo = request.getSession().getAttribute("user");
        if(userInfo!=null) {
            User user = (User) userInfo;
            return user.getId();
        } else {
            return null;
        }
    }

    public String getModule(String apiName)throws Exception{
        String moduleName = null;
        switch (apiName) {
            case BusinessConstants.LOG_INTERFACE_NAME_USER:
                moduleName = BusinessConstants.LOG_MODULE_NAME_USER; break;
            case BusinessConstants.LOG_INTERFACE_NAME_ROLE:
                moduleName = BusinessConstants.LOG_MODULE_NAME_ROLE; break;
            case BusinessConstants.LOG_INTERFACE_NAME_APP:
                moduleName =BusinessConstants.LOG_MODULE_NAME_APP; break;
            case BusinessConstants.LOG_INTERFACE_NAME_DEPOT:
                moduleName = BusinessConstants.LOG_MODULE_NAME_DEPOT; break;
            case BusinessConstants.LOG_INTERFACE_NAME_FUNCTIONS:
                moduleName = BusinessConstants.LOG_MODULE_NAME_FUNCTIONS; break;
            case BusinessConstants.LOG_INTERFACE_NAME_IN_OUT_ITEM:
                moduleName = BusinessConstants.LOG_MODULE_NAME_IN_OUT_ITEM; break;
            case BusinessConstants.LOG_INTERFACE_NAME_UNIT:
                moduleName = BusinessConstants.LOG_MODULE_NAME_UNIT; break;
            case BusinessConstants.LOG_INTERFACE_NAME_PERSON:
                moduleName = BusinessConstants.LOG_MODULE_NAME_PERSON; break;
            case BusinessConstants.LOG_INTERFACE_NAME_USER_BUSINESS:
                moduleName = BusinessConstants.LOG_MODULE_NAME_USER_BUSINESS; break;
            case BusinessConstants.LOG_INTERFACE_NAME_SYSTEM_CONFIG:
                moduleName = BusinessConstants.LOG_MODULE_NAME_SYSTEM_CONFIG; break;
            case BusinessConstants.LOG_INTERFACE_NAME_MATERIAL_PROPERTY:
                moduleName = BusinessConstants.LOG_MODULE_NAME_MATERIAL_PROPERTY; break;
            case BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT:
                moduleName = BusinessConstants.LOG_MODULE_NAME_ACCOUNT; break;
            case BusinessConstants.LOG_INTERFACE_NAME_SUPPLIER:
                moduleName = BusinessConstants.LOG_MODULE_NAME_SUPPLIER; break;
            case BusinessConstants.LOG_INTERFACE_NAME_MATERIAL_CATEGORY:
                moduleName = BusinessConstants.LOG_MODULE_NAME_MATERIAL_CATEGORY; break;
            case BusinessConstants.LOG_INTERFACE_NAME_MATERIAL:
                moduleName = BusinessConstants.LOG_MODULE_NAME_MATERIAL; break;
            case BusinessConstants.LOG_INTERFACE_NAME_DEPOT_HEAD:
                moduleName = BusinessConstants.LOG_MODULE_NAME_DEPOT_HEAD; break;
            case BusinessConstants.LOG_INTERFACE_NAME_DEPOT_ITEM:
                moduleName = BusinessConstants.LOG_MODULE_NAME_DEPOT_ITEM; break;
            case BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT_HEAD:
                moduleName = BusinessConstants.LOG_MODULE_NAME_ACCOUNT_HEAD; break;
            case BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT_ITEM:
                moduleName = BusinessConstants.LOG_MODULE_NAME_ACCOUNT_ITEM; break;
            case BusinessConstants.LOG_INTERFACE_NAME_SERIAL_NUMBER:
                moduleName = BusinessConstants.LOG_MODULE_NAME_SERIAL_NUMBER; break;
            case BusinessConstants.LOG_INTERFACE_NAME_ORGANIZATION:
                moduleName = BusinessConstants.LOG_MODULE_NAME_ORGANIZATION; break;
        }
        return moduleName;
    }

    public void insertLog(String apiName, String type, HttpServletRequest request)throws Exception{
        Log log = new Log();
        log.setUserid(getUserId(request));
        log.setOperation(getModule(apiName));
        log.setClientip(getLocalIp(request));
        log.setCreatetime(new Date());
        Byte status = 0;
        log.setStatus(status);
        log.setContentdetails(type + getModule(apiName));
        log.setRemark(type + getModule(apiName));
        try{
            logMapper.insertSelective(log);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }

    }

}
