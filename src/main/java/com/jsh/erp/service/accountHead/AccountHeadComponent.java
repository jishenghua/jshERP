package com.jsh.erp.service.accountHead;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "accountHead_component")
@AccountHeadResource
public class AccountHeadComponent implements ICommonQuery {

    @Resource
    private AccountHeadService accountHeadService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return accountHeadService.getAccountHead(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getAccountHeadList(map);
    }

    private List<?> getAccountHeadList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String type = StringUtil.getInfo(search, "type");
        String billNo = StringUtil.getInfo(search, "billNo");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String order = QueryUtils.order(map);
        return accountHeadService.select(type, billNo, beginTime, endTime, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String type = StringUtil.getInfo(search, "type");
        String billNo = StringUtil.getInfo(search, "billNo");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        return accountHeadService.countAccountHead(type, billNo, beginTime, endTime);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) throws Exception{
        return accountHeadService.insertAccountHead(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id, HttpServletRequest request)throws Exception {
        return accountHeadService.updateAccountHead(beanJson, id, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return accountHeadService.deleteAccountHead(id, request);
    }

    @Override
    public int batchDelete(String ids, HttpServletRequest request)throws Exception {
        return accountHeadService.batchDeleteAccountHead(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return accountHeadService.checkIsNameExist(id, name);
    }

}
