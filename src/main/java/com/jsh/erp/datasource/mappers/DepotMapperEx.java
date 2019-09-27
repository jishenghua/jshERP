package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DepotEx;
import com.jsh.erp.datasource.entities.DepotExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DepotMapperEx {

    List<Depot> selectByConditionDepot(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("remark") String remark,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByDepot(
            @Param("name") String name,
            @Param("type") Integer type,
            @Param("remark") String remark);

    List<DepotEx> getDepotList(Map<String, Object> params);

    int batchDeleteDepotByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);
}