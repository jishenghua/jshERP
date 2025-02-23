package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.InOutItem;
import com.jsh.erp.datasource.entities.InOutItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface InOutItemMapperEx {

    List<InOutItem> selectByConditionInOutItem(
            @Param("name") String name,
            @Param("type") String type,
            @Param("remark") String remark);

    int batchDeleteInOutItemByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);
}