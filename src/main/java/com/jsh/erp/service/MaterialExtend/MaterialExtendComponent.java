package com.jsh.erp.service.MaterialExtend;

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
    public int insert(String beanJson, HttpServletRequest request) throws Exception{
        return materialExtendService.insertMaterialExtend(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id, HttpServletRequest request)throws Exception {
        return materialExtendService.updateMaterialExtend(beanJson, id, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return materialExtendService.deleteMaterialExtend(id, request);
    }

    @Override
    public int batchDelete(String ids, HttpServletRequest request)throws Exception {
        return materialExtendService.batchDeleteMaterialExtendByIds(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return materialExtendService.checkIsExist(id, name);
    }

}
