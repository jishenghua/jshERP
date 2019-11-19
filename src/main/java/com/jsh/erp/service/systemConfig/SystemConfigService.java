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
import com.jsh.erp.exception.JshException;
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
            JshException.readFail(logger, e);
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
            JshException.readFail(logger, e);
        }
        return list;
    }
    public List<SystemConfig> select(String companyName, int offset, int rows)throws Exception {
        List<SystemConfig> list=null;
        try{
            list=systemConfigMapperEx.selectByConditionSystemConfig(companyName, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countSystemConfig(String companyName)throws Exception {
        Long result=null;
        try{
            result=systemConfigMapperEx.countsBySystemConfig(companyName);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSystemConfig(String beanJson, HttpServletRequest request) throws Exception{
        SystemConfig systemConfig = JSONObject.parseObject(beanJson, SystemConfig.class);
        int result=0;
        try{
            result=systemConfigMapper.insertSelective(systemConfig);
            logService.insertLog("系统配置", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSystemConfig(String beanJson, Long id, HttpServletRequest request) throws Exception{
        SystemConfig systemConfig = JSONObject.parseObject(beanJson, SystemConfig.class);
        systemConfig.setId(id);
        int result=0;
        try{
            result=systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
            logService.insertLog("系统配置",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSystemConfig(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            result=systemConfigMapper.deleteByPrimaryKey(id);
            logService.insertLog("系统配置",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfig(String ids, HttpServletRequest request)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=systemConfigMapper.deleteByExample(example);
            logService.insertLog("系统配置", "批量删除,id集:" + ids, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
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
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfigByIds(String ids)throws Exception {
        logService.insertLog("系统配置",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            result=systemConfigMapperEx.batchDeleteSystemConfigByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    /**
     * 获取仓库开关
     * @return
     * @throws Exception
     */
    public boolean getDepotFlag() throws Exception {
        boolean depotFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getDepotFlag();
            if(("1").equals(flag)) {
                depotFlag = true;
            }
        }
        return depotFlag;
    }

    /**
     * 获取客户开关
     * @return
     * @throws Exception
     */
    public boolean getCustomerFlag() throws Exception {
        boolean customerFlag = false;
        List<SystemConfig> list = getSystemConfig();
        if(list.size()>0) {
            String flag = list.get(0).getCustomerFlag();
            if(("1").equals(flag)) {
                customerFlag = true;
            }
        }
        return customerFlag;
    }
}
