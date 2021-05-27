package com.jsh.erp.service.user;

import com.alibaba.fastjson.JSONObject;
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
    public Object selectOne(Long id) throws Exception {
        return userService.getUser(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getUserList(map);
    }

    private List<?> getUserList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String userName = StringUtil.getInfo(search, "userName");
        String loginName = StringUtil.getInfo(search, "loginName");
        String order = QueryUtils.order(map);
        String filter = QueryUtils.filter(map);
        return userService.select(userName, loginName, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String userName = StringUtil.getInfo(search, "userName");
        String loginName = StringUtil.getInfo(search, "loginName");
        return userService.countUser(userName, loginName);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request)throws Exception {
        return userService.insertUser(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return userService.updateUser(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return userService.deleteUser(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return userService.batchDeleteUser(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return userService.checkIsNameExist(id, name);
    }

}
