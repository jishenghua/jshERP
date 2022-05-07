package com.jsh.erp.service.depotHead;

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

@Service(value = "depotHead_component")
@DepotHeadResource
public class DepotHeadComponent implements ICommonQuery {

    @Resource
    private DepotHeadService depotHeadService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return depotHeadService.getDepotHead(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getDepotHeadList(map);
    }

    private List<?> getDepotHeadList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String type = StringUtil.getInfo(search, "type");
        String subType = StringUtil.getInfo(search, "subType");
        String roleType = StringUtil.getInfo(search, "roleType");
        String status = StringUtil.getInfo(search, "status");
        String number = StringUtil.getInfo(search, "number");
        String linkNumber = StringUtil.getInfo(search, "linkNumber");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String materialParam = StringUtil.getInfo(search, "materialParam");
        Long organId = StringUtil.parseStrLong(StringUtil.getInfo(search, "organId"));
        Long creator = StringUtil.parseStrLong(StringUtil.getInfo(search, "creator"));
        Long depotId = StringUtil.parseStrLong(StringUtil.getInfo(search, "depotId"));
        Long accountId = StringUtil.parseStrLong(StringUtil.getInfo(search, "accountId"));
        String remark = StringUtil.getInfo(search, "remark");
        return depotHeadService.select(type, subType, roleType, status, number, linkNumber, beginTime, endTime, materialParam,
                organId, creator, depotId, accountId, remark, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String type = StringUtil.getInfo(search, "type");
        String subType = StringUtil.getInfo(search, "subType");
        String roleType = StringUtil.getInfo(search, "roleType");
        String status = StringUtil.getInfo(search, "status");
        String number = StringUtil.getInfo(search, "number");
        String linkNumber = StringUtil.getInfo(search, "linkNumber");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String materialParam = StringUtil.getInfo(search, "materialParam");
        Long organId = StringUtil.parseStrLong(StringUtil.getInfo(search, "organId"));
        Long creator = StringUtil.parseStrLong(StringUtil.getInfo(search, "creator"));
        Long depotId = StringUtil.parseStrLong(StringUtil.getInfo(search, "depotId"));
        Long accountId = StringUtil.parseStrLong(StringUtil.getInfo(search, "accountId"));
        String remark = StringUtil.getInfo(search, "remark");
        return depotHeadService.countDepotHead(type, subType, roleType, status, number, linkNumber, beginTime, endTime, materialParam,
                organId, creator, depotId, accountId, remark);
    }

    @Override
    public int insert(JSONObject obj, HttpServletRequest request) throws Exception{
        return depotHeadService.insertDepotHead(obj, request);
    }

    @Override
    public int update(JSONObject obj, HttpServletRequest request)throws Exception {
        return depotHeadService.updateDepotHead(obj, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return depotHeadService.deleteDepotHead(id, request);
    }

    @Override
    public int deleteBatch(String ids, HttpServletRequest request)throws Exception {
        return depotHeadService.batchDeleteDepotHead(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return depotHeadService.checkIsNameExist(id, name);
    }

}
