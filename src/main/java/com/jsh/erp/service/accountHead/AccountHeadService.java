package com.jsh.erp.service.accountHead;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.AccountHead;
import com.jsh.erp.datasource.entities.AccountHeadExample;
import com.jsh.erp.datasource.entities.AccountHeadVo4ListEx;
import com.jsh.erp.datasource.mappers.AccountHeadMapper;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountHeadService {
    private Logger logger = LoggerFactory.getLogger(AccountHeadService.class);

    @Resource
    private AccountHeadMapper accountHeadMapper;

    public AccountHead getAccountHead(long id) {
        return accountHeadMapper.selectByPrimaryKey(id);
    }

    public List<AccountHead> getAccountHead() {
        AccountHeadExample example = new AccountHeadExample();
        return accountHeadMapper.selectByExample(example);
    }

    public List<AccountHeadVo4ListEx> select(String type, String billNo, String beginTime, String endTime, int offset, int rows) {
        List<AccountHeadVo4ListEx> resList = new ArrayList<AccountHeadVo4ListEx>();
        List<AccountHeadVo4ListEx> list = accountHeadMapper.selectByConditionAccountHead(type, billNo, beginTime, endTime, offset, rows);
        if (null != list) {
            for (AccountHeadVo4ListEx ah : list) {
                if(ah.getChangeamount() != null) {
                    ah.setChangeamount(Math.abs(ah.getChangeamount()));
                }
                if(ah.getTotalprice() != null) {
                    ah.setTotalprice(Math.abs(ah.getTotalprice()));
                }
                resList.add(ah);
            }
        }
        return resList;
    }

    public int countAccountHead(String type, String billNo, String beginTime, String endTime) {
        return accountHeadMapper.countsByAccountHead(type, billNo, beginTime, endTime);
    }

    public int insertAccountHead(String beanJson, HttpServletRequest request) {
        AccountHead accountHead = JSONObject.parseObject(beanJson, AccountHead.class);
        return accountHeadMapper.insertSelective(accountHead);
    }

    public int updateAccountHead(String beanJson, Long id) {
        AccountHead accountHead = JSONObject.parseObject(beanJson, AccountHead.class);
        accountHead.setId(id);
        return accountHeadMapper.updateByPrimaryKeySelective(accountHead);
    }

    public int deleteAccountHead(Long id) {
        return accountHeadMapper.deleteByPrimaryKey(id);
    }

    public int batchDeleteAccountHead(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        AccountHeadExample example = new AccountHeadExample();
        example.createCriteria().andIdIn(idList);
        return accountHeadMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        AccountHeadExample example = new AccountHeadExample();
        example.createCriteria().andIdNotEqualTo(id);
        List<AccountHead> list = accountHeadMapper.selectByExample(example);
        return list.size();
    }

    public Long getMaxId() {
        return accountHeadMapper.getMaxId();
    }

    public Double findAllMoney(Integer supplierId, String type, String mode, String endTime) {
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "TotalPrice";
        }
        return accountHeadMapper.findAllMoney(supplierId, type, modeName, endTime);
    }

    public List<AccountHeadVo4ListEx> getDetailByNumber(String billNo) {
        List<AccountHeadVo4ListEx> resList = new ArrayList<AccountHeadVo4ListEx>();
        List<AccountHeadVo4ListEx> list = accountHeadMapper.getDetailByNumber(billNo);
        if (null != list) {
            for (AccountHeadVo4ListEx ah : list) {
                if(ah.getChangeamount() != null) {
                    ah.setChangeamount(Math.abs(ah.getChangeamount()));
                }
                if(ah.getTotalprice() != null) {
                    ah.setTotalprice(Math.abs(ah.getTotalprice()));
                }
                resList.add(ah);
            }
        }
        return resList;
    }

}
