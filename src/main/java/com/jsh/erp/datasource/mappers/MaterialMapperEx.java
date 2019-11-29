package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.AccountHead;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialVo4Unit;
import org.apache.ibatis.annotations.Param;

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
            @Param("name") String name,
            @Param("model") String model,
            @Param("categoryIds") String categoryIds,
            @Param("mpList") String mpList,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByMaterial(
            @Param("name") String name,
            @Param("model") String model,
            @Param("categoryIds") String categoryIds,
            @Param("mpList") String mpList);

    String findUnitName(@Param("mId") Long mId);

    List<MaterialVo4Unit> findById(@Param("id") Long id);

    List<MaterialVo4Unit> findBySelect();

    int updatePriceNullByPrimaryKey(Long id);

    int updateUnitIdNullByPrimaryKey(Long id);

    List<MaterialVo4Unit> findByAll(
            @Param("name") String name,
            @Param("model") String model,
            @Param("categoryIds") String categoryIds);
    /**
     * 通过商品名称查询商品信息
     * */
    List<Material> findByMaterialName(@Param("name") String name);
    /**
     * 获取开启序列号并且状态正常的商品列表
     * */
    List<Material> getMaterialEnableSerialNumberList(Map<String, Object> parameterMap);

    int batchDeleteMaterialByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    List<Material> getMaterialListByCategoryIds(@Param("categoryIds") String[] categoryIds);

    List<Material> getMaterialListByUnitIds(@Param("unitIds") String[] unitIds);

    int insertSelectiveEx(Material record);
}
