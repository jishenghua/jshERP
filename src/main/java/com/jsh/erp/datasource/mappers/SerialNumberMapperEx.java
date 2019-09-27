package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.SerialNumberEx;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
    Long countSerialNumber(@Param("serialNumber")String serialNumber,@Param("materialName")String materialName);
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
    /**
     * 查询指定商品下有效的序列号数量
     * 未删除为卖出的视为有效
     * */
    int findSerialNumberByMaterialId(@Param("materialId") Long materialId);
    /**
     * 查询符合条件的序列号数量
     * */
    int countSerialNumberByMaterialIdAndDepotheadId(@Param("materialId")Long materialId, @Param("depotheadId")Long depotheadId, @Param("isSell")String isSell);
    /**
     * 卖出： update jsh_serial_number set is_Sell='1' ,depothead_Id='depotheadId' where 1=1 and material_Id='materialId'
     * and is_Sell !='1' and delete_Flag !='1'  {limit 0，count}
     * */
    int sellSerialNumber(@Param("materialId")Long materialId, @Param("depotheadId")Long depotheadId,@Param("count")Integer count, @Param("updateTime") Date updateTime,@Param("updater") Long updater);
    /**
     * 赎回：update jsh_serial_number set is_Sell='0'  where 1=1 and material_Id='materialId'
     *      and depothead_Id='depotheadId' and is_Sell ！='0' and delete_Flag !='1' {limit 0，count}
     * */
    int cancelSerialNumber(@Param("materialId")Long materialId, @Param("depotheadId")Long depotheadId, @Param("count")Integer count, @Param("updateTime") Date updateTime,@Param("updater") Long updater);
    /**
     * 批量添加序列号
     * */
    int batAddSerialNumber(@Param("list") List<SerialNumberEx> list);

    int batchDeleteSerialNumberByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);
}
