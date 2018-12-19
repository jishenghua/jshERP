package com.jsh.erp.service.materialProperty;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.MaterialProperty;
import com.jsh.erp.datasource.entities.MaterialPropertyExample;
import com.jsh.erp.datasource.mappers.MaterialPropertyMapper;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MaterialPropertyService {
    private Logger logger = LoggerFactory.getLogger(MaterialPropertyService.class);

    @Resource
    private MaterialPropertyMapper materialPropertyMapper;

    public MaterialProperty getMaterialProperty(long id) {
        return materialPropertyMapper.selectByPrimaryKey(id);
    }

    public List<MaterialProperty> getMaterialProperty() {
        MaterialPropertyExample example = new MaterialPropertyExample();
        return materialPropertyMapper.selectByExample(example);
    }
    public List<MaterialProperty> select(String name, int offset, int rows) {
        return materialPropertyMapper.selectByConditionMaterialProperty(name, offset, rows);
    }

    public int countMaterialProperty(String name) {
        return materialPropertyMapper.countsByMaterialProperty(name);
    }

    public int insertMaterialProperty(String beanJson, HttpServletRequest request) {
        MaterialProperty materialProperty = JSONObject.parseObject(beanJson, MaterialProperty.class);
        return materialPropertyMapper.insertSelective(materialProperty);
    }

    public int updateMaterialProperty(String beanJson, Long id) {
        MaterialProperty materialProperty = JSONObject.parseObject(beanJson, MaterialProperty.class);
        materialProperty.setId(id);
        return materialPropertyMapper.updateByPrimaryKeySelective(materialProperty);
    }

    public int deleteMaterialProperty(Long id) {
        return materialPropertyMapper.deleteByPrimaryKey(id);
    }

    public int batchDeleteMaterialProperty(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        MaterialPropertyExample example = new MaterialPropertyExample();
        example.createCriteria().andIdIn(idList);
        return materialPropertyMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        return 0;
    }
}
