package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.SystemConfig;
import com.jsh.erp.datasource.entities.SystemConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemConfigMapperEx {

    List<SystemConfig> selectByConditionSystemConfig(
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int countsBySystemConfig();
}