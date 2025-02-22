package com.jsh.erp.datasource.mappers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jsh.erp.datasource.entities.TenantEx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TenantMapperEx {

    List<TenantEx> selectByConditionTenant(
            @Param("loginName") String loginName,
            @Param("type") String type,
            @Param("enabled") String enabled,
            @Param("remark") String remark);
}