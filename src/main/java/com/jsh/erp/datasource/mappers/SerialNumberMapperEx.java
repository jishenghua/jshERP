package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.SerialNumber;
import com.jsh.erp.datasource.entities.SerialNumberEx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/21 17:09
 */
public interface SerialNumberMapperEx {
    /**
     * 根据条件查询序列号列表
     * */
     List<SerialNumberEx> selectByConditionSerialNumber(@Param("serialNumber") String serialNumber, @Param("materialName") String materialName,@Param("offset") Integer offset,@Param("rows") Integer rows);
    /**
     * 根据条件查询序列号数量
     * */
     int countSerialNumber(@Param("serialNumber")String serialNumber,@Param("materialName")String materialName);
     /**
      * 通过id查询序列号复合信息
      * */
     List<SerialNumberEx> findById(Long id);
     /**
      * 通过序列号查询序列号实体信息
      * */
     List<SerialNumberEx> findBySerialNumber(@Param("serialNumber") String serialNumber);
     /**
      * 新增序列号信息
      * */
    int addSerialNumber(SerialNumberEx serialNumberEx);
    /**
     * 修改序列号信息
     * */
    int updateSerialNumber(SerialNumberEx serialNumberEx);
}
