package com.jsh.erp.service.systemConfig;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.datasource.entities.SystemConfigExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.SystemConfigMapper;
import com.jsh.erp.datasource.mappers.SystemConfigMapperEx;
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

    public SystemConfig getSystemConfig(long id) {
        return systemConfigMapper.selectByPrimaryKey(id);
    }

    public List<SystemConfig> getSystemConfig() {
        SystemConfigExample example = new SystemConfigExample();
        return systemConfigMapper.selectByExample(example);
    }
    public List<SystemConfig> select(String companyName, int offset, int rows) {
        return systemConfigMapperEx.selectByConditionSystemConfig(companyName, offset, rows);
    }

    public Long countSystemConfig(String companyName) {
        return systemConfigMapperEx.countsBySystemConfig(companyName);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertSystemConfig(String beanJson, HttpServletRequest request) {
        SystemConfig systemConfig = JSONObject.parseObject(beanJson, SystemConfig.class);
        return systemConfigMapper.insertSelective(systemConfig);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateSystemConfig(String beanJson, Long id) {
        SystemConfig systemConfig = JSONObject.parseObject(beanJson, SystemConfig.class);
        systemConfig.setId(id);
        return systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteSystemConfig(Long id) {
        return systemConfigMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfig(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andIdIn(idList);
        return systemConfigMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        SystemConfigExample example = new SystemConfigExample();
        example.createCriteria().andIdNotEqualTo(id).andCompanyNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<SystemConfig> list = systemConfigMapper.selectByExample(example);
        return list.size();
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteSystemConfigByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_SYSTEM_CONFIG,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return systemConfigMapperEx.batchDeleteSystemConfigByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
}
