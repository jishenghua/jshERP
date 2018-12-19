package com.jsh.erp.service.user;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service(value = "user_component")
@UserResource
public class UserComponent implements ICommonQuery {

    @Resource
    private UserService userService;

    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getUserList(map);
    }

    private List<?> getUserList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String userName = StringUtil.getInfo(search, "userName");
        String loginName = StringUtil.getInfo(search, "loginName");
        String order = QueryUtils.order(map);
        String filter = QueryUtils.filter(map);
        return userService.select(userName, loginName, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public int counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String userName = StringUtil.getInfo(search, "userName");
        String loginName = StringUtil.getInfo(search, "loginName");
        return userService.countUser(userName, loginName);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return userService.insertUser(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return userService.updateUser(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return userService.deleteUser(id);
    }

    @Override
    public int batchDelete(String ids) {
        return userService.batchDeleteUser(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return userService.checkIsNameExist(id, name);
    }

}
