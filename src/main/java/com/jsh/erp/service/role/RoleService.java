package com.jsh.erp.service.role;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Role;
import com.jsh.erp.datasource.entities.RoleExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserExample;
import com.jsh.erp.datasource.mappers.RoleMapper;
import com.jsh.erp.datasource.mappers.RoleMapperEx;
import com.jsh.erp.datasource.mappers.UserMapper;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.RegExpTools;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMapperEx roleMapperEx;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;

    public Role getRole(long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> getRole() {
        RoleExample example = new RoleExample();
        return roleMapper.selectByExample(example);
    }

    public List<Role> select(String name, int offset, int rows) {
        return roleMapperEx.selectByConditionRole(name, offset, rows);
    }

    public Long countRole(String name) {
        return roleMapperEx.countsByRole(name);
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
    /**
     * create by: qiankunpingtai
     * website：http://39.105.146.63/symphony/
     * description:
     *  逻辑删除角色信息
     * create time: 2019/3/28 15:44
     * @Param: ids
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteRoleByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_SERIAL_NUMBER,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return roleMapperEx.batchDeleteRoleByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
}
