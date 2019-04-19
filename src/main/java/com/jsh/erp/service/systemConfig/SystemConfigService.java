package com.jsh.erp.service.systemConfig;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.datasource.entities.SystemConfigExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.SystemConfigMapper;
import com.jsh.erp.datasource.mappers.SystemConfigMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class SystemConfigService {
    private Logger logger = LoggerFactory.getLogger(SystemConfigService.class);

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Resource
    private SystemConfigMapperEx systemConfigMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    public SystemConfig getSystemConfig(long id)throws Exception {
        SystemConfig result=null;
        try{
            result=systemConfigMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    public List<SystemConfig> getSystemConfig()throws Exception {
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<SystemConfig> list=null;
        try{
            list=systemConfigMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }
    public List<SystemConfig> select(String companyName, int offset, int rows)throws Exception {
        List<SystemConfig> list=null;
        try{
            list=systemConfigMapperEx.selectByConditionSystemConfig(companyName, offset, rows);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public Long countSystemConfig(String companyName)throws Exception {
        Long result=null;
        try{
            result=systemConfigMapperEx.countsBySystemConfig(companyName);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSystemConfig(String beanJson, HttpServletRequest request) throws Exception{
        SystemConfig systemConfig = JSONObject.parseObject(beanJson, SystemConfig.class);
        int result=0;
        try{
            result=systemConfigMapper.insertSelective(systemConfig);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSystemConfig(String beanJson, Long id) throws Exception{
        SystemConfig systemConfig = JSONObject.parseObject(beanJson, SystemConfig.class);
        systemConfig.setId(id);
        int result=0;
        try{
            result=systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSystemConfig(Long id)throws Exception {
        int result=0;
        try{
            result=systemConfigMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfig(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=systemConfigMapper.deleteByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name) throws Exception{
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andIdNotEqualTo(id).andCompanyNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<SystemConfig> list =null;
        try{
            list=systemConfigMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list==null?0:list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfigByIds(String ids)throws Exception {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_SYSTEM_CONFIG,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            result=systemConfigMapperEx.batchDeleteSystemConfigByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }
}
