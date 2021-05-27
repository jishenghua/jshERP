package com.jsh.erp.service.systemConfig;

import com.alibaba.fastjson.JSONObject;
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
    public Object selectOne(Long id) throws Exception {
        return systemConfigService.getSystemConfig(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getSystemConfigList(map);
    }

    private List<?> getSystemConfigList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String companyName = StringUtil.getInfo(search, "companyName");
        String order = QueryUtils.order(map);
        return systemConfigService.select(companyName, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String companyName = StringUtil.getInfo(search, "companyName");
        return systemConfigService.countSystemConfig(companyName);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request)throws Exception {
        return systemConfigService.insertSystemConfig(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return systemConfigService.updateSystemConfig(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return systemConfigService.deleteSystemConfig(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return systemConfigService.batchDeleteSystemConfig(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return systemConfigService.checkIsNameExist(id, name);
    }

}
