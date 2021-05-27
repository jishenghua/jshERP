package com.jsh.erp.service.unit;

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

@Service(value = "unit_component")
@UnitResource
public class UnitComponent implements ICommonQuery {

    @Resource
    private UnitService unitService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return unitService.getUnit(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getUnitList(map);
    }

    private List<?> getUnitList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String order = QueryUtils.order(map);
        return unitService.select(name, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        return unitService.countUnit(name);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request)throws Exception {
        return unitService.insertUnit(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return unitService.updateUnit(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return unitService.deleteUnit(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return unitService.batchDeleteUnit(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return unitService.checkIsNameExist(id, name);
    }

}
