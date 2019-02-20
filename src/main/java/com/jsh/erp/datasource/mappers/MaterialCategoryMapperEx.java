package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.vo.TreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/2/18 17:23
 */
public interface MaterialCategoryMapperEx {
    List<MaterialCategory> selectByConditionMaterialCategory(
            @Param("name") String name,
            @Param("parentId") Integer parentId,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    int countsByMaterialCategory(
            @Param("name") String name,
            @Param("parentId") Integer parentId);

    List<TreeNode> getNodeTree();
    List<TreeNode> getNextNodeTree(@Param("id") Long id);

    int addMaterialCategory(MaterialCategory mc);

    int batchDeleteMaterialCategoryByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);

    int editMaterialCategory(MaterialCategory mc);

    List<MaterialCategory> getMaterialCategoryBySerialNo(@Param("serialNo") String serialNo);
}
