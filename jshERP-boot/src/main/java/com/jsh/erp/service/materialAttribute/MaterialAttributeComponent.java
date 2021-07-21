package com.jsh.erp.service.materialAttribute;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "materialAttribute_component")
@MaterialAttributeResource
public class MaterialAttributeComponent implements ICommonQuery {

    @Resource
    private MaterialAttributeService materialAttributeService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return materialAttributeService.getMaterialAttribute(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getMaterialList(map);
    }

    private List<?> getMaterialList(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String attributeField = StringUtil.getInfo(search, "attributeField");
        return materialAttributeService.select(attributeField, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String attributeField = StringUtil.getInfo(search, "attributeField");
        return materialAttributeService.countMaterialAttribute(attributeField);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request) throws Exception{
        return materialAttributeService.insertMaterialAttribute(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return materialAttributeService.updateMaterialAttribute(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return materialAttributeService.deleteMaterialAttribute(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return materialAttributeService.batchDeleteMaterialAttribute(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return materialAttributeService.checkIsNameExist(id, name);
    }

}
