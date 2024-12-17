package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.entities.MaterialInitialStock;
import com.jsh.erp.datasource.entities.MaterialInitialStockExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialInitialStockMapperEx {

    int batchInsert(List<MaterialInitialStock> list);

    List<MaterialInitialStock> getInitialStockMapByIdList(
            @Param("materialIdList") List<Long> materialIdList);

    List<MaterialInitialStock> getListExceptZero();

    void batchDeleteByDepots(@Param("ids") String ids[]);
}