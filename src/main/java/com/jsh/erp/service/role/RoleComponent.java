package com.jsh.erp.service.role;

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
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getRoleList(map);
    }

    private List<?> getRoleList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String order = QueryUtils.order(map);
        String filter = QueryUtils.filter(map);
        return roleService.select(name, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public int counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        return roleService.countRole(name);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return roleService.insertRole(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return roleService.updateRole(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return roleService.deleteRole(id);
    }

    @Override
    public int batchDelete(String ids) {
        return roleService.batchDeleteRole(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return 0;
    }

}
