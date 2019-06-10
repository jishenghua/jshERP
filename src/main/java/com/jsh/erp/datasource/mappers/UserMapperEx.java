package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserEx;
import com.jsh.erp.datasource.entities.UserExample;
import com.jsh.erp.datasource.vo.TreeNode;
import com.jsh.erp.datasource.vo.TreeNodeEx;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserMapperEx {

    List<User> selectByConditionUser(
            @Param("userName") String userName,
            @Param("loginName") String loginName,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByUser(
            @Param("userName") String userName,
            @Param("loginName") String loginName);

    List<UserEx> getUserList(Map<String, Object> parameterMap);

    int addUser(UserEx ue);

    int updateUser(UserEx ue);
    /**
     * 这个查询不添加租户id，保证登录名全局唯一
     * */
    List<User> getUserListByLoginName(@Param("loginame") String loginame);

    int batDeleteOrUpdateUser(@Param("ids") String ids[], @Param("status") byte status);

    List<TreeNodeEx> getNodeTree();
    List<TreeNodeEx> getNextNodeTree(Map<String, Object> parameterMap);

    List<User> getUserListByUserNameAndTenantId(@Param("userName")String userName, @Param("tenantId")Long tenantId);

    String addRegisterUserNotInclueUser(@Param("userId") Long userId,@Param("tenantId") Long tenantId,@Param("roleId") Long roleId);
    List<User> getUserListByloginNameAndPassword(@Param("loginame")String loginame, @Param("password")String password);
}