package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Unit;
import com.jsh.erp.datasource.entities.UnitExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UnitMapperEx {

    List<Unit> selectByConditionUnit(
            @Param("name") String name,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByUnit(
            @Param("name") String name);

    int batchDeleteUnitByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    void updateRatioTwoById(@Param("id") Long id);

    void updateRatioThreeById(@Param("id") Long id);
}