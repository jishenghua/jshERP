package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperEx {

    List<User> selectByConditionUser(
            @Param("userName") String userName,
            @Param("loginName") String loginName,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByUser(
            @Param("userName") String userName,
            @Param("loginName") String loginName);
}