package com.jsh.erp.service.systemConfig;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.systemConfig.SystemConfigResource;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "systemConfig_component")
@SystemConfigResource
public class SystemConfigComponent implements ICommonQuery {

    @Resource
    private SystemConfigService systemConfigService;

    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getSystemConfigList(map);
    }

    private List<?> getSystemConfigList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String companyName = StringUtil.getInfo(search, "companyName");
        String order = QueryUtils.order(map);
        return systemConfigService.select(companyName, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String companyName = StringUtil.getInfo(search, "companyName");
        return systemConfigService.countSystemConfig(companyName);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return systemConfigService.insertSystemConfig(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return systemConfigService.updateSystemConfig(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return systemConfigService.deleteSystemConfig(id);
    }

    @Override
    public int batchDelete(String ids) {
        return systemConfigService.batchDeleteSystemConfig(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return systemConfigService.checkIsNameExist(id, name);
    }

}
