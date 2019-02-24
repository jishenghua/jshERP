package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Unit;
import com.jsh.erp.datasource.entities.UnitExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnitMapperEx {

    List<Unit> selectByConditionUnit(
            @Param("name") String name,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int countsByUnit(
            @Param("name") String name);
}