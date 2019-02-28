package com.jsh.erp.service.inOutItem;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "inOutItem_component")
@InOutItemResource
public class InOutItemComponent implements ICommonQuery {

    @Resource
    private InOutItemService inOutItemService;

    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getFunctionsList(map);
    }

    private List<?> getFunctionsList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String type = StringUtil.getInfo(search, "type");
        String remark = StringUtil.getInfo(search, "remark");
        String order = QueryUtils.order(map);
        return inOutItemService.select(name, type, remark, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String type = StringUtil.getInfo(search, "type");
        String remark = StringUtil.getInfo(search, "remark");
        return inOutItemService.countInOutItem(name, type, remark);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return inOutItemService.insertInOutItem(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return inOutItemService.updateInOutItem(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return inOutItemService.deleteInOutItem(id);
    }

    @Override
    public int batchDelete(String ids) {
        return inOutItemService.batchDeleteInOutItem(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return inOutItemService.checkIsNameExist(id, name);
    }

}
