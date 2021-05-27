package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.entities.MaterialCurrentStockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MaterialCurrentStockMapper {
    long countByExample(MaterialCurrentStockExample example);

    int deleteByExample(MaterialCurrentStockExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MaterialCurrentStock record);

    int insertSelective(MaterialCurrentStock record);

    List<MaterialCurrentStock> selectByExample(MaterialCurrentStockExample example);

    MaterialCurrentStock selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MaterialCurrentStock record, @Param("example") MaterialCurrentStockExample example);

    int updateByExample(@Param("record") MaterialCurrentStock record, @Param("example") MaterialCurrentStockExample example);

    int updateByPrimaryKeySelective(MaterialCurrentStock record);

    int updateByPrimaryKey(MaterialCurrentStock record);
}