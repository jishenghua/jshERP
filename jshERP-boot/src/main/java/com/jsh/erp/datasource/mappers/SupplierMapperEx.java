package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.entities.SupplierExample;
import com.jsh.erp.datasource.vo.SupplierSimple;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SupplierMapperEx {

    List<Supplier> selectByConditionSupplier(
            @Param("supplier") String supplier,
            @Param("type") String type,
            @Param("contacts") String contacts,
            @Param("phonenum") String phonenum,
            @Param("telephone") String telephone,
            @Param("creatorArray") String[] creatorArray);

    List<Supplier> findByAll(
            @Param("supplier") String supplier,
            @Param("type") String type,
            @Param("phonenum") String phonenum,
            @Param("telephone") String telephone);

    int batchDeleteSupplierByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    Supplier getSupplierByNameAndType(
            @Param("supplier") String supplier,
            @Param("type") String type);

    List<SupplierSimple> getAllCustomer();

    List<Supplier> findByTypeAndKey(
            @Param("type") String type,
            @Param("key") String key,
            @Param("limit") Integer limit);

    Supplier getInfoById(
            @Param("id") Long id);
}