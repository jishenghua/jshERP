package com.jsh.erp.service.materialCategory;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.materialProperty.MaterialPropertyResource;
import com.jsh.erp.service.materialProperty.MaterialPropertyService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "materialCategory_component")
@MaterialCategoryResource
public class MaterialCategoryComponent implements ICommonQuery {

    @Resource
    private MaterialCategoryService materialCategoryService;

    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getMaterialCategoryList(map);
    }

    private List<?> getMaterialCategoryList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        Integer parentId = StringUtil.parseInteger(StringUtil.getInfo(search, "parentId"));
        String order = QueryUtils.order(map);
        return materialCategoryService.select(name, parentId, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public int counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        Integer parentId = StringUtil.parseInteger(StringUtil.getInfo(search, "parentId"));
        return materialCategoryService.countMaterialCategory(name, parentId);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return materialCategoryService.insertMaterialCategory(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return materialCategoryService.updateMaterialCategory(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return materialCategoryService.deleteMaterialCategory(id);
    }

    @Override
    public int batchDelete(String ids) {
        return materialCategoryService.batchDeleteMaterialCategory(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return materialCategoryService.checkIsNameExist(id, name);
    }

}
