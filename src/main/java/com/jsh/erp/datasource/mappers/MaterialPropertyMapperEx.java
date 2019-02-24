package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.MaterialProperty;
import com.jsh.erp.datasource.entities.MaterialPropertyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialPropertyMapperEx {

    List<MaterialProperty> selectByConditionMaterialProperty(
            @Param("name") String name,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int countsByMaterialProperty(@Param("name") String name);
}