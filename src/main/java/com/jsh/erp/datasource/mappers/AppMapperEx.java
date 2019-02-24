package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.App;
import com.jsh.erp.datasource.entities.AppExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppMapperEx {

    List<App> selectByConditionApp(
            @Param("name") String name,
            @Param("type") String type,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int countsByApp(
            @Param("name") String name,
            @Param("type") String type);
}