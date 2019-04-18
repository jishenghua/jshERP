package com.jsh.erp.service.depotHead;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "depotHead_component")
@DepotHeadResource
public class DepotHeadComponent implements ICommonQuery {

    @Resource
    private DepotHeadService depotHeadService;

    @Override
    public Object selectOne(String condition)throws Exception {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getDepotHeadList(map);
    }

    private List<?> getDepotHeadList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String type = StringUtil.getInfo(search, "type");
        String subType = StringUtil.getInfo(search, "subType");
        String number = StringUtil.getInfo(search, "number");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String dhIds = StringUtil.getInfo(search, "dhIds");
        String order = QueryUtils.order(map);
        return depotHeadService.select(type, subType, number, beginTime, endTime, dhIds, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String type = StringUtil.getInfo(search, "type");
        String subType = StringUtil.getInfo(search, "subType");
        String number = StringUtil.getInfo(search, "number");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String dhIds = StringUtil.getInfo(search, "dhIds");
        return depotHeadService.countDepotHead(type, subType, number, beginTime, endTime, dhIds);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) throws Exception{
        return depotHeadService.insertDepotHead(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id)throws Exception {
        return depotHeadService.updateDepotHead(beanJson, id);
    }

    @Override
    public int delete(Long id)throws Exception {
        return depotHeadService.deleteDepotHead(id);
    }

    @Override
    public int batchDelete(String ids)throws Exception {
        return depotHeadService.batchDeleteDepotHead(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return depotHeadService.checkIsNameExist(id, name);
    }

}
