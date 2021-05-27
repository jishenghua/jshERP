package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.DepotItem;
import com.jsh.erp.datasource.entities.DepotItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepotItemMapper {
    long countByExample(DepotItemExample example);

    int deleteByExample(DepotItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DepotItem record);

    int insertSelective(DepotItem record);

    List<DepotItem> selectByExample(DepotItemExample example);

    DepotItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DepotItem record, @Param("example") DepotItemExample example);

    int updateByExample(@Param("record") DepotItem record, @Param("example") DepotItemExample example);

    int updateByPrimaryKeySelective(DepotItem record);

    int updateByPrimaryKey(DepotItem record);
}