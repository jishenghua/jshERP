package com.jsh.erp.service.systemConfig;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.datasource.entities.SystemConfigExample;
import com.jsh.erp.datasource.mappers.SystemConfigMapper;
import com.jsh.erp.datasource.mappers.SystemConfigMapperEx;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SystemConfigService {
    private Logger logger = LoggerFactory.getLogger(SystemConfigService.class);

    @Resource
    private SystemConfigMapper systemConfigMapper;

    @Resource
    private SystemConfigMapperEx systemConfigMapperEx;

    public SystemConfig getSystemConfig(long id) {
        return systemConfigMapper.selectByPrimaryKey(id);
    }

    public List<SystemConfig> getSystemConfig() {
        SystemConfigExample example = new SystemConfigExample();
        return systemConfigMapper.selectByExample(example);
    }
    public List<SystemConfig> select(int offset, int rows) {
        return systemConfigMapperEx.selectByConditionSystemConfig(offset, rows);
    }

    public Long countSystemConfig() {
        return systemConfigMapperEx.countsBySystemConfig();
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
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name);
        List<SystemConfig> list = systemConfigMapper.selectByExample(example);
        return list.size();
    }
}
