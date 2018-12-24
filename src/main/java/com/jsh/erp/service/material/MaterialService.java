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

    public List<MaterialVo4Unit> select(String name, String model,Long categoryId, String categoryIds,String mpList, int offset, int rows) {
        String[] mpArr = mpList.split(",");
        List<MaterialVo4Unit> resList = new ArrayList<MaterialVo4Unit>();
        List<MaterialVo4Unit> list = materialMapper.selectByConditionMaterial(name, model,categoryId,categoryIds,mpList, offset, rows);
        if (null != list) {
            for (MaterialVo4Unit m : list) {
                //扩展信息
                String materialOther = "";
                for (int i = 0; i < mpArr.length; i++) {
                    if (mpArr[i].equals("颜色")) {
                        materialOther = materialOther + ((m.getColor() == null || m.getColor().equals("")) ? "" : "(" + m.getColor() + ")");
                    }
                    if (mpArr[i].equals("规格")) {
                        materialOther = materialOther + ((m.getStandard() == null || m.getStandard().equals("")) ? "" : "(" + m.getStandard() + ")");
                    }
                    if (mpArr[i].equals("制造商")) {
                        materialOther = materialOther + ((m.getMfrs() == null || m.getMfrs().equals("")) ? "" : "(" + m.getMfrs() + ")");
                    }
                    if (mpArr[i].equals("自定义1")) {
                        materialOther = materialOther + ((m.getOtherfield1() == null || m.getOtherfield1().equals("")) ? "" : "(" + m.getOtherfield1() + ")");
                    }
                    if (mpArr[i].equals("自定义2")) {
                        materialOther = materialOther + ((m.getOtherfield2() == null || m.getOtherfield2().equals("")) ? "" : "(" + m.getOtherfield2() + ")");
                    }
                    if (mpArr[i].equals("自定义3")) {
                        materialOther = materialOther + ((m.getOtherfield3() == null || m.getOtherfield3().equals("")) ? "" : "(" + m.getOtherfield3() + ")");
                    }
                }
                m.setMaterialOther(materialOther);
                resList.add(m);
            }
        }
        return resList;
    }

    public int countMaterial(String name, String model,Long categoryId, String categoryIds,String mpList) {
        return materialMapper.countsByMaterial(name, model,categoryId,categoryIds,mpList);
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
