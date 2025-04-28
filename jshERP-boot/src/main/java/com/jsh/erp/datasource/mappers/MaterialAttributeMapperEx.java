package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.MaterialAttribute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialAttributeMapperEx {

    List<MaterialAttribute> selectByConditionMaterialAttribute(
            @Param("attributeName") String attributeName,
            @Param("attributeValue") String attributeValue);

    int batchDeleteMaterialAttributeByIds(
            @Param("ids") String ids[]);
}