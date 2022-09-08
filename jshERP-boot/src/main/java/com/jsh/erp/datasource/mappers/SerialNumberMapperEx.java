package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.SerialNumber;
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
     * 卖出： update jsh_serial_number set is_Sell='1' ,depothead_Id='depotheadId' where 1=1 and material_Id='materialId'
     * and is_Sell !='1' and delete_Flag !='1'  {limit 0，count}
     * */
    int sellSerialNumber(@Param("materialId")Long materialId, @Param("outBillNo")String outBillNo, @Param("snArray") String snArray[], @Param("updateTime") Date updateTime,@Param("updater") Long updater);
    /**
     * 赎回：update jsh_serial_number set is_Sell='0',depothead_Id=null  where 1=1 and material_Id='materialId'
     *      and depothead_Id='depotheadId' and is_Sell ！='0' and delete_Flag !='1' {limit 0，count}
     * */
    int cancelSerialNumber(@Param("materialId")Long materialId, @Param("outBillNo")String outBillNo, @Param("count")Integer count, @Param("updateTime") Date updateTime,@Param("updater") Long updater);
    /**
     * 批量添加序列号
     * */
    int batAddSerialNumber(@Param("list") List<SerialNumberEx> list);

    int batchDeleteSerialNumberByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    List<SerialNumberEx> getEnableSerialNumberList(@Param("number") String number,
                                                 @Param("name") String name,
                                                 @Param("depotId") Long depotId,
                                                 @Param("barCode") String barCode,
                                                 @Param("offset") Integer offset, @Param("rows") Integer rows);

    Long getEnableSerialNumberCount(@Param("number") String number,
                                    @Param("name") String name,
                                    @Param("depotId") Long depotId,
                                    @Param("barCode") String barCode);
}
