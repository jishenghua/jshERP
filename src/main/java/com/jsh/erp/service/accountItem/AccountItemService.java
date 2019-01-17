package com.jsh.erp.service.accountItem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.AccountItem;
import com.jsh.erp.datasource.entities.AccountItemExample;
import com.jsh.erp.datasource.mappers.AccountItemMapper;
import com.jsh.erp.datasource.vo.AccountItemVo4List;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

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

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertAccountItem(String beanJson, HttpServletRequest request) {
        AccountItem accountItem = JSONObject.parseObject(beanJson, AccountItem.class);
        return accountItemMapper.insertSelective(accountItem);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAccountItem(String beanJson, Long id) {
        AccountItem accountItem = JSONObject.parseObject(beanJson, AccountItem.class);
        accountItem.setId(id);
        return accountItemMapper.updateByPrimaryKeySelective(accountItem);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteAccountItem(Long id) {
        return accountItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
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

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertAccountItemWithObj(AccountItem accountItem) {
        return accountItemMapper.insertSelective(accountItem);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAccountItemWithObj(AccountItem accountItem) {
        return accountItemMapper.updateByPrimaryKeySelective(accountItem);
    }

    public List<AccountItemVo4List> getDetailList(Long headerId) {
        return accountItemMapper.getDetailList(headerId);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public String saveDetials(String inserted, String deleted, String updated, Long headerId, String listType) throws DataAccessException {
            //转为json
            JSONArray insertedJson = JSONArray.parseArray(inserted);
            JSONArray deletedJson = JSONArray.parseArray(deleted);
            JSONArray updatedJson = JSONArray.parseArray(updated);
            if (null != insertedJson) {
                for (int i = 0; i < insertedJson.size(); i++) {
                    AccountItem accountItem = new AccountItem();
                    JSONObject tempInsertedJson = JSONObject.parseObject(insertedJson.getString(i));
                    accountItem.setHeaderid(headerId);
                    if (tempInsertedJson.get("AccountId") != null && !tempInsertedJson.get("AccountId").equals("")) {
                        accountItem.setAccountid(tempInsertedJson.getLong("AccountId"));
                    }
                    if (tempInsertedJson.get("InOutItemId") != null && !tempInsertedJson.get("InOutItemId").equals("")) {
                        accountItem.setInoutitemid(tempInsertedJson.getLong("InOutItemId"));
                    }
                    if (tempInsertedJson.get("EachAmount") != null && !tempInsertedJson.get("EachAmount").equals("")) {
                        BigDecimal eachAmount = tempInsertedJson.getBigDecimal("EachAmount");
                        if (listType.equals("付款")) {
                            eachAmount = BigDecimal.ZERO.subtract(eachAmount);
                        }
                        accountItem.setEachamount(eachAmount);
                    } else {
                        accountItem.setEachamount(BigDecimal.ZERO);
                    }
                    accountItem.setRemark(tempInsertedJson.getString("Remark"));
                    this.insertAccountItemWithObj(accountItem);
                }
            }
            if (null != deletedJson) {
                for (int i = 0; i < deletedJson.size(); i++) {
                    JSONObject tempDeletedJson = JSONObject.parseObject(deletedJson.getString(i));
                    this.deleteAccountItem(tempDeletedJson.getLong("Id"));
                }
            }
            if (null != updatedJson) {
                for (int i = 0; i < updatedJson.size(); i++) {
                    JSONObject tempUpdatedJson = JSONObject.parseObject(updatedJson.getString(i));
                    AccountItem accountItem = this.getAccountItem(tempUpdatedJson.getLong("Id"));
                    accountItem.setId(tempUpdatedJson.getLong("Id"));
                    accountItem.setHeaderid(headerId);
                    if (tempUpdatedJson.get("AccountId") != null && !tempUpdatedJson.get("AccountId").equals("")) {
                        accountItem.setAccountid(tempUpdatedJson.getLong("AccountId"));
                    }
                    if (tempUpdatedJson.get("InOutItemId") != null && !tempUpdatedJson.get("InOutItemId").equals("")) {
                        accountItem.setInoutitemid(tempUpdatedJson.getLong("InOutItemId"));
                    }
                    if (tempUpdatedJson.get("EachAmount") != null && !tempUpdatedJson.get("EachAmount").equals("")) {
                        BigDecimal eachAmount = tempUpdatedJson.getBigDecimal("EachAmount");
                        if (listType.equals("付款")) {
                            eachAmount = BigDecimal.ZERO.subtract(eachAmount);
                        }
                        accountItem.setEachamount(eachAmount);
                    } else {
                        accountItem.setEachamount(BigDecimal.ZERO);
                    }
                    accountItem.setRemark(tempUpdatedJson.getString("Remark"));
                    this.updateAccountItemWithObj(accountItem);
                }
            }

        return null;
    }

}
