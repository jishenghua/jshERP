package com.jsh.erp.service.accountItem;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.AccountItem;
import com.jsh.erp.datasource.entities.AccountItemExample;
import com.jsh.erp.datasource.mappers.AccountItemMapper;
import com.jsh.erp.datasource.vo.AccountItemVo4List;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AccountItemService {
    private Logger logger = LoggerFactory.getLogger(AccountItemService.class);

    @Resource
    private AccountItemMapper accountItemMapper;

    public AccountItem getAccountItem(long id) {
        return accountItemMapper.selectByPrimaryKey(id);
    }

    public List<AccountItem> getAccountItem() {
        AccountItemExample example = new AccountItemExample();
        return accountItemMapper.selectByExample(example);
    }

    public List<AccountItem> select(String name, Integer type, String remark, int offset, int rows) {
        return accountItemMapper.selectByConditionAccountItem(name, type, remark, offset, rows);
    }

    public int countAccountItem(String name, Integer type, String remark) {
        return accountItemMapper.countsByAccountItem(name, type, remark);
    }

    public int insertAccountItem(String beanJson, HttpServletRequest request) {
        AccountItem accountItem = JSONObject.parseObject(beanJson, AccountItem.class);
        return accountItemMapper.insertSelective(accountItem);
    }

    public int updateAccountItem(String beanJson, Long id) {
        AccountItem accountItem = JSONObject.parseObject(beanJson, AccountItem.class);
        accountItem.setId(id);
        return accountItemMapper.updateByPrimaryKeySelective(accountItem);
    }

    public int deleteAccountItem(Long id) {
        return accountItemMapper.deleteByPrimaryKey(id);
    }

    public int batchDeleteAccountItem(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        AccountItemExample example = new AccountItemExample();
        example.createCriteria().andIdIn(idList);
        return accountItemMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        AccountItemExample example = new AccountItemExample();
        example.createCriteria().andIdNotEqualTo(id);
        List<AccountItem> list = accountItemMapper.selectByExample(example);
        return list.size();
    }

    public int insertAccountItemWithObj(AccountItem accountItem) {
        return accountItemMapper.insertSelective(accountItem);
    }

    public int updateAccountItemWithObj(AccountItem accountItem) {
        return accountItemMapper.updateByPrimaryKeySelective(accountItem);
    }

    public List<AccountItemVo4List> getDetailList(Long headerId) {
        return accountItemMapper.getDetailList(headerId);
    }
}
