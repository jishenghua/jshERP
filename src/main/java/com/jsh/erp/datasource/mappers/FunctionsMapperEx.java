package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Functions;
import com.jsh.erp.datasource.entities.FunctionsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FunctionsMapperEx {

    List<Functions> selectByConditionFunctions(
            @Param("name") String name,
            @Param("type") String type,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int countsByFunctions(
            @Param("name") String name,
            @Param("type") String type);
}