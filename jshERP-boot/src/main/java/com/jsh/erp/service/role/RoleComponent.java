package com.jsh.erp.service.role;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "role_component")
@RoleResource
public class RoleComponent implements ICommonQuery {

    @Resource
    private RoleService roleService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return roleService.getRole(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getRoleList(map);
    }

    private List<?> getRoleList(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String description = StringUtil.getInfo(search, "description");
        return roleService.select(name, description, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String description = StringUtil.getInfo(search, "description");
        return roleService.countRole(name, description);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request)throws Exception {
        return roleService.insertRole(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return roleService.updateRole(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return roleService.deleteRole(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return roleService.batchDeleteRole(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return roleService.checkIsNameExist(id, name);
    }

}
