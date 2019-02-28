package com.jsh.erp.service.account;

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

@Service(value = "account_component")
@AccountResource
public class AccountComponent implements ICommonQuery {

    @Resource
    private AccountService accountService;

    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getAccountList(map);
    }

    private List<?> getAccountList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String serialNo = StringUtil.getInfo(search, "serialNo");
        String remark = StringUtil.getInfo(search, "remark");
        String order = QueryUtils.order(map);
        return accountService.select(name, serialNo, remark, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String serialNo = StringUtil.getInfo(search, "serialNo");
        String remark = StringUtil.getInfo(search, "remark");
        return accountService.countAccount(name, serialNo, remark);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return accountService.insertAccount(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return accountService.updateAccount(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return accountService.deleteAccount(id);
    }

    @Override
    public int batchDelete(String ids) {
        return accountService.batchDeleteAccount(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return accountService.checkIsNameExist(id, name);
    }

}
