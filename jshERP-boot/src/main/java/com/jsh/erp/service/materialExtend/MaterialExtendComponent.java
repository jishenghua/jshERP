package com.jsh.erp.service.materialExtend;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.service.ICommonQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "material_extend")
@MaterialExtendResource
public class MaterialExtendComponent implements ICommonQuery {

    @Resource
    private MaterialExtendService materialExtendService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return materialExtendService.getMaterialExtend(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getMaterialList(map);
    }

    private List<?> getMaterialList(Map<String, String> map) throws Exception{

        return null;
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {

        return 0L;
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request) throws Exception{
        return materialExtendService.insertMaterialExtend(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return materialExtendService.updateMaterialExtend(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return materialExtendService.deleteMaterialExtend(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return materialExtendService.batchDeleteMaterialExtendByIds(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return 0;
    }

}
