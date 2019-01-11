package com.jsh.erp.service.role;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Role;
import com.jsh.erp.datasource.entities.RoleExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserExample;
import com.jsh.erp.datasource.mappers.RoleMapper;
import com.jsh.erp.datasource.mappers.UserMapper;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.RegExpTools;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    public Role getRole(long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> getRole() {
        RoleExample example = new RoleExample();
        return roleMapper.selectByExample(example);
    }

    public List<Role> select(String name, int offset, int rows) {
        return roleMapper.selectByConditionRole(name, offset, rows);
    }

    public int countRole(String name) {
        return roleMapper.countsByRole(name);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertRole(String beanJson, HttpServletRequest request) {
        Role role = JSONObject.parseObject(beanJson, Role.class);
        return roleMapper.insertSelective(role);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateRole(String beanJson, Long id) {
        Role role = JSONObject.parseObject(beanJson, Role.class);
        role.setId(id);
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteRole(Long id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteRole(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        RoleExample example = new RoleExample();
        example.createCriteria().andIdIn(idList);
        return roleMapper.deleteByExample(example);
    }

    public List<Role> findUserRole(){
        RoleExample example = new RoleExample();
        example.setOrderByClause("Id");
        List<Role> list = roleMapper.selectByExample(example);
        return list;
    }
}
