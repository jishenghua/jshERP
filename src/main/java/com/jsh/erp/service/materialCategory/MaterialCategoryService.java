package com.jsh.erp.service.materialCategory;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.MaterialCategory;
import com.jsh.erp.datasource.entities.MaterialCategoryExample;
import com.jsh.erp.datasource.mappers.MaterialCategoryMapper;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MaterialCategoryService {
    private Logger logger = LoggerFactory.getLogger(MaterialCategoryService.class);

    @Resource
    private MaterialCategoryMapper materialCategoryMapper;

    public MaterialCategory getMaterialCategory(long id) {
        return materialCategoryMapper.selectByPrimaryKey(id);
    }

    public List<MaterialCategory> getMaterialCategory() {
        MaterialCategoryExample example = new MaterialCategoryExample();
        return materialCategoryMapper.selectByExample(example);
    }

    public List<MaterialCategory> getAllList(Long parentId) {
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andParentidEqualTo(parentId).andIdNotEqualTo(1l);
        example.setOrderByClause("id");
        return materialCategoryMapper.selectByExample(example);
    }

    public List<MaterialCategory> select(String name, Integer parentId, int offset, int rows) {
        return materialCategoryMapper.selectByConditionMaterialCategory(name, parentId, offset, rows);
    }

    public int countMaterialCategory(String name, Integer parentId) {
        return materialCategoryMapper.countsByMaterialCategory(name, parentId);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterialCategory(String beanJson, HttpServletRequest request) {
        MaterialCategory materialCategory = JSONObject.parseObject(beanJson, MaterialCategory.class);
        return materialCategoryMapper.insertSelective(materialCategory);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterialCategory(String beanJson, Long id) {
        MaterialCategory materialCategory = JSONObject.parseObject(beanJson, MaterialCategory.class);
        materialCategory.setId(id);
        return materialCategoryMapper.updateByPrimaryKeySelective(materialCategory);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterialCategory(Long id) {
        return materialCategoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialCategory(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andIdIn(idList);
        return materialCategoryMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        return 0;
    }

    public List<MaterialCategory> findById(Long id) {
        MaterialCategoryExample example = new MaterialCategoryExample();
        example.createCriteria().andIdEqualTo(id);
        return materialCategoryMapper.selectByExample(example);
    }
}
