package com.jsh.erp.service.tenant;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.user.UserResource;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "tenant_component")
@TenantResource
public class TenantComponent implements ICommonQuery {

    @Resource
    private TenantService tenantService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return tenantService.getTenant(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getTenantList(map);
    }

    private List<?> getTenantList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String loginName = StringUtil.getInfo(search, "loginName");
        String type = StringUtil.getInfo(search, "type");
        String enabled = StringUtil.getInfo(search, "enabled");
        String remark = StringUtil.getInfo(search, "remark");
        return tenantService.select(loginName, type, enabled, remark, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String loginName = StringUtil.getInfo(search, "loginName");
        String type = StringUtil.getInfo(search, "type");
        String enabled = StringUtil.getInfo(search, "enabled");
        String remark = StringUtil.getInfo(search, "remark");
        return tenantService.countTenant(loginName, type, enabled, remark);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request)throws Exception {
        return tenantService.insertTenant(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return tenantService.updateTenant(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return tenantService.deleteTenant(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return tenantService.batchDeleteTenant(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return tenantService.checkIsNameExist(id, name);
    }

}
