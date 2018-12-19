package com.jsh.erp.service.material;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Material;
import com.jsh.erp.datasource.entities.MaterialExample;
import com.jsh.erp.datasource.entities.MaterialVo4Unit;
import com.jsh.erp.datasource.mappers.MaterialMapper;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {
    private Logger logger = LoggerFactory.getLogger(MaterialService.class);

    @Resource
    private MaterialMapper materialMapper;

    public Material getMaterial(long id) {
        return materialMapper.selectByPrimaryKey(id);
    }

    public List<Material> getMaterial() {
        MaterialExample example = new MaterialExample();
        return materialMapper.selectByExample(example);
    }

    public List<Material> select(String name, String model, int offset, int rows) {
        return materialMapper.selectByConditionMaterial(name, model, offset, rows);
    }

    public int countMaterial(String name, String model) {
        return materialMapper.countsByMaterial(name, model);
    }

    public int insertMaterial(String beanJson, HttpServletRequest request) {
        Material material = JSONObject.parseObject(beanJson, Material.class);
        return materialMapper.insertSelective(material);
    }

    public int updateMaterial(String beanJson, Long id) {
        Material material = JSONObject.parseObject(beanJson, Material.class);
        material.setId(id);
        return materialMapper.updateByPrimaryKeySelective(material);
    }

    public int deleteMaterial(Long id) {
        return materialMapper.deleteByPrimaryKey(id);
    }

    public int batchDeleteMaterial(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdIn(idList);
        return materialMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name);
        List<Material> list = materialMapper.selectByExample(example);
        return list.size();
    }

    public int checkIsExist(Long id, String name, String model, String color, String standard, String mfrs,
                            String otherField1, String otherField2, String otherField3, String unit, Long unitId) {
        MaterialExample example = new MaterialExample();
        if (id > 0) {
            example.createCriteria().andIdNotEqualTo(id);
        }
        example.createCriteria().andNameEqualTo(name).andModelEqualTo(model).andColorEqualTo(color)
                                .andStandardEqualTo(standard).andMfrsEqualTo(mfrs)
                                .andOtherfield1EqualTo(otherField1).andOtherfield2EqualTo(otherField2).andOtherfield2EqualTo(otherField3);
        if (unit !=null) {
            example.createCriteria().andUnitEqualTo(unit);
        }
        if (unitId !=null) {
            example.createCriteria().andUnitidEqualTo(unitId);
        }
        List<Material> list = materialMapper.selectByExample(example);
        return list.size();
    }

    public int batchSetEnable(Boolean enabled, String materialIDs) {
        List<Long> ids = StringUtil.strToLongList(materialIDs);
        Material material = new Material();
        material.setEnabled(enabled);
        MaterialExample example = new MaterialExample();
        example.createCriteria().andIdIn(ids);
        return materialMapper.updateByExampleSelective(material, example);
    }

    public String findUnitName(Long mId){
        return materialMapper.findUnitName(mId);
    }

    public List<MaterialVo4Unit> findById(Long id){
        return materialMapper.findById(id);
    }

    public List<MaterialVo4Unit> findBySelect(){
        return materialMapper.findBySelect();
    }

    public List<Material> findByOrder(){
        MaterialExample example = new MaterialExample();
        example.setOrderByClause("Name,Model asc");
        return materialMapper.selectByExample(example);
    }

}
