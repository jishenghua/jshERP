package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.MaterialCurrentStock;
import com.jsh.erp.datasource.entities.MaterialInitialStock;
import com.jsh.erp.datasource.entities.MaterialInitialStockExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialInitialStockMapperEx {

    int batchInsert(List<MaterialInitialStock> list);

}