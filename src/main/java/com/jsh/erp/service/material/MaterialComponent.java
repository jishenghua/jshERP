package com.jsh.erp.service.material;

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
        String name = StringUtil.getInfo(search, "name");
        String model = StringUtil.getInfo(search, "model");
        String categoryIds = StringUtil.getInfo(search, "categoryIds");
        String mpList = StringUtil.getInfo(search, "mpList");
        String order = QueryUtils.order(map);
        return materialService.select(name, model,categoryIds,mpList, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String model = StringUtil.getInfo(search, "model");
        String categoryIds = StringUtil.getInfo(search, "categoryIds");
        String mpList = StringUtil.getInfo(search, "mpList");
        return materialService.countMaterial(name, model,categoryIds,mpList);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) throws Exception{
        return materialService.insertMaterial(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id, HttpServletRequest request)throws Exception {
        return materialService.updateMaterial(beanJson, id, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return materialService.deleteMaterial(id, request);
    }

    @Override
    public int batchDelete(String ids, HttpServletRequest request)throws Exception {
        return materialService.batchDeleteMaterial(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return materialService.checkIsNameExist(id, name);
    }

}
