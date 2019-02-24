package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DepotExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepotMapperEx {

    List<Depot> selectByConditionDepot(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("remark") String remark,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int countsByDepot(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("remark") String remark);
}