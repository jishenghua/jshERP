package com.jsh.erp.service.accountItem;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "accountItem_component")
@AccountItemResource
public class AccountItemComponent implements ICommonQuery {

    @Resource
    private AccountItemService accountItemService;

    @Override
    public Object selectOne(String condition)throws Exception {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getAccountItemList(map);
    }

    private List<?> getAccountItemList(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        Integer type = StringUtil.parseInteger(StringUtil.getInfo(search, "type"));
        String remark = StringUtil.getInfo(search, "remark");
        String order = QueryUtils.order(map);
        return accountItemService.select(name, type, remark, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        Integer type = StringUtil.parseInteger(StringUtil.getInfo(search, "type"));
        String remark = StringUtil.getInfo(search, "remark");
        return accountItemService.countAccountItem(name, type, remark);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) throws Exception{
        return accountItemService.insertAccountItem(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id)throws Exception {
        return accountItemService.updateAccountItem(beanJson, id);
    }

    @Override
    public int delete(Long id)throws Exception {
        return accountItemService.deleteAccountItem(id);
    }

    @Override
    public int batchDelete(String ids)throws Exception {
        return accountItemService.batchDeleteAccountItem(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return accountItemService.checkIsNameExist(id, name);
    }

}
