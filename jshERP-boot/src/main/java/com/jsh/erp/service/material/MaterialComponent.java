package com.jsh.erp.service.material;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.depot.DepotResource;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "material_component")
@MaterialResource
public class MaterialComponent implements ICommonQuery {

    @Resource
    private MaterialService materialService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return materialService.getMaterial(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getMaterialList(map);
    }

    private List<?> getMaterialList(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String categoryId = StringUtil.getInfo(search, "categoryId");
        String materialParam = StringUtil.getInfo(search, "materialParam");
        String color = StringUtil.getInfo(search, "color");
        String weight = StringUtil.getInfo(search, "weight");
        String expiryNum = StringUtil.getInfo(search, "expiryNum");
        String enableSerialNumber = StringUtil.getInfo(search, "enableSerialNumber");
        String enableBatchNumber = StringUtil.getInfo(search, "enableBatchNumber");
        String enabled = StringUtil.getInfo(search, "enabled");
        String remark = StringUtil.getInfo(search, "remark");
        String mpList = StringUtil.getInfo(search, "mpList");
        return materialService.select(materialParam, color, weight, expiryNum,
                enableSerialNumber, enableBatchNumber, enabled, remark, categoryId, mpList, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String categoryId = StringUtil.getInfo(search, "categoryId");
        String materialParam = StringUtil.getInfo(search, "materialParam");
        String color = StringUtil.getInfo(search, "color");
        String weight = StringUtil.getInfo(search, "weight");
        String expiryNum = StringUtil.getInfo(search, "expiryNum");
        String enableSerialNumber = StringUtil.getInfo(search, "enableSerialNumber");
        String enableBatchNumber = StringUtil.getInfo(search, "enableBatchNumber");
        String enabled = StringUtil.getInfo(search, "enabled");
        String remark = StringUtil.getInfo(search, "remark");
        String mpList = StringUtil.getInfo(search, "mpList");
        return materialService.countMaterial(materialParam, color, weight, expiryNum,
                enableSerialNumber, enableBatchNumber, enabled, remark, categoryId, mpList);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request) throws Exception{
        return materialService.insertMaterial(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return materialService.updateMaterial(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return materialService.deleteMaterial(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return materialService.batchDeleteMaterial(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return materialService.checkIsNameExist(id, name);
    }

}
