package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/22 14:54
 */
public interface MaterialMapperEx {

    List<MaterialVo4Unit> selectByConditionMaterial(
            @Param("materialParam") String materialParam,
            @Param("color") String color,
            @Param("materialOther") String materialOther,
            @Param("weight") String weight,
            @Param("expiryNum") String expiryNum,
            @Param("enableSerialNumber") String enableSerialNumber,
            @Param("enableBatchNumber") String enableBatchNumber,
            @Param("enabled") String enabled,
            @Param("remark") String remark,
            @Param("idList") List<Long> idList,
            @Param("mpList") String mpList,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByMaterial(
            @Param("materialParam") String materialParam,
            @Param("color") String color,
            @Param("materialOther") String materialOther,
            @Param("weight") String weight,
            @Param("expiryNum") String expiryNum,
            @Param("enableSerialNumber") String enableSerialNumber,
            @Param("enableBatchNumber") String enableBatchNumber,
            @Param("enabled") String enabled,
            @Param("remark") String remark,
            @Param("idList") List<Long> idList,
            @Param("mpList") String mpList);

    Long insertSelectiveEx(Material record);

    List<Unit> findUnitList(@Param("mId") Long mId);

    List<MaterialVo4Unit> findById(@Param("id") Long id);

    List<MaterialVo4Unit> findByIdWithBarCode(@Param("meId") Long meId);

    List<MaterialVo4Unit> findBySelectWithBarCode(@Param("idList") List<Long> idList,
                                                  @Param("q") String q,
                                                  @Param("enableSerialNumber") String enableSerialNumber,
                                                  @Param("enableBatchNumber") String enableBatchNumber,
                                                  @Param("offset") Integer offset,
                                                  @Param("rows") Integer rows);

    int findBySelectWithBarCodeCount(@Param("idList") List<Long> idList,
                                     @Param("q") String q,
                                     @Param("enableSerialNumber") String enableSerialNumber,
                                     @Param("enableBatchNumber") String enableBatchNumber);

    List<MaterialVo4Unit> exportExcel(
            @Param("materialParam") String materialParam,
            @Param("color") String color,
            @Param("weight") String weight,
            @Param("expiryNum") String expiryNum,
            @Param("enabled") String enabled,
            @Param("enableSerialNumber") String enableSerialNumber,
            @Param("enableBatchNumber") String enableBatchNumber,
            @Param("remark") String remark,
            @Param("idList") List<Long> idList);
    /**
     * 通过商品名称查询商品信息
     * */
    List<Material> findByMaterialName(@Param("name") String name);
    /**
     * 获取开启序列号并且状态正常的商品列表
     * */
    List<MaterialVo4Unit> getMaterialEnableSerialNumberList(@Param("q") String q,
                                                     @Param("offset") Integer offset,
                                                     @Param("rows") Integer rows);

    Long getMaterialEnableSerialNumberCount(@Param("q") String q);

    int batchDeleteMaterialByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    List<Material> getMaterialListByCategoryIds(@Param("categoryIds") String[] categoryIds);

    List<Material> getMaterialListByUnitIds(@Param("unitIds") String[] unitIds);

    String getMaxBarCode();

    List<MaterialVo4Unit> getMaterialByMeId(
            @Param("meId") Long meId);

    List<String> getMaterialNameList();

    int setUnitIdToNull(@Param("id") Long id);

    int setExpiryNumToNull(@Param("id") Long id);

    List<MaterialVo4Unit> getMaterialByBarCode(@Param("barCodeArray") String [] barCodeArray);

    List<MaterialVo4Unit> getMaterialByBarCodeAndWithOutMId(
            @Param("barCodeArray") String [] barCodeArray,
            @Param("mId") Long mId);

    List<MaterialInitialStockWithMaterial> getInitialStockWithMaterial(
            @Param("depotList") List<Long> depotList);

    List<MaterialVo4Unit> getListWithStock(
            @Param("depotList") List<Long> depotList,
            @Param("idList") List<Long> idList,
            @Param("materialParam") String materialParam,
            @Param("zeroStock") Integer zeroStock,
            @Param("column") String column,
            @Param("order") String order,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int getListWithStockCount(
            @Param("depotList") List<Long> depotList,
            @Param("idList") List<Long> idList,
            @Param("materialParam") String materialParam,
            @Param("zeroStock") Integer zeroStock);

    MaterialVo4Unit getTotalStockAndPrice(
            @Param("depotList") List<Long> depotList,
            @Param("idList") List<Long> idList,
            @Param("materialParam") String materialParam);

    int checkIsExist(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("model") String model,
            @Param("color") String color,
            @Param("standard") String standard,
            @Param("mfrs") String mfrs,
            @Param("otherField1") String otherField1,
            @Param("otherField2") String otherField2,
            @Param("otherField3") String otherField3,
            @Param("unit") String unit,
            @Param("unitId") Long unitId);
}
