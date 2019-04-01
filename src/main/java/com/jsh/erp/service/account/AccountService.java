package com.jsh.erp.service.account;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.datasource.vo.AccountVo4InOutList;
import com.jsh.erp.datasource.vo.AccountVo4List;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountMapperEx accountMapperEx;

    @Resource
    private DepotHeadMapper depotHeadMapper;

    @Resource
    private AccountHeadMapper accountHeadMapper;

    @Resource
    private AccountItemMapper accountItemMapper;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;

    public Account getAccount(long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    public List<Account> getAccount() {
        AccountExample example = new AccountExample();
        return accountMapper.selectByExample(example);
    }

    public List<AccountVo4List> select(String name, String serialNo, String remark, int offset, int rows) {
        List<AccountVo4List> resList = new ArrayList<AccountVo4List>();
        List<AccountVo4List> list = accountMapperEx.selectByConditionAccount(name, serialNo, remark, offset, rows);
        String timeStr = Tools.getCurrentMonth();
        if (null != list && null !=timeStr) {
            for (AccountVo4List al : list) {
                DecimalFormat df = new DecimalFormat(".##");
                BigDecimal thisMonthAmount = getAccountSum(al.getId(), timeStr, "month").add(getAccountSumByHead(al.getId(), timeStr, "month")).add(getAccountSumByDetail(al.getId(), timeStr, "month")).add(getManyAccountSum(al.getId(), timeStr, "month"));
                String thisMonthAmountFmt = "0";
                if ((thisMonthAmount.compareTo(BigDecimal.ZERO))!=0) {
                    thisMonthAmountFmt = df.format(thisMonthAmount);
                }
                al.setThismonthamount(thisMonthAmountFmt);  //本月发生额
                BigDecimal currentAmount = getAccountSum(al.getId(), "", "month").add(getAccountSumByHead(al.getId(), "", "month")).add(getAccountSumByDetail(al.getId(), "", "month")).add(getManyAccountSum(al.getId(), "", "month")) .add(al.getInitialamount()) ;
                al.setCurrentamount(currentAmount);
                resList.add(al);
            }
        }
        return resList;
    }

    public Long countAccount(String name, String serialNo, String remark) {
        return accountMapperEx.countsByAccount(name, serialNo, remark);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertAccount(String beanJson, HttpServletRequest request) {
        Account account = JSONObject.parseObject(beanJson, Account.class);
        if(account.getInitialamount() == null) {
            account.setInitialamount(BigDecimal.ZERO);
        }
        account.setIsdefault(false);
        return accountMapper.insertSelective(account);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAccount(String beanJson, Long id) {
        Account account = JSONObject.parseObject(beanJson, Account.class);
        account.setId(id);
        return accountMapper.updateByPrimaryKeySelective(account);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteAccount(Long id) {
        return accountMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccount(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        AccountExample example = new AccountExample();
        example.createCriteria().andIdIn(idList);
        return accountMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        AccountExample example = new AccountExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);

        List<Account> list = accountMapper.selectByExample(example);
        return list.size();
    }

    public List<Account> findBySelect() {
        AccountExample example = new AccountExample();
        example.setOrderByClause("id desc");
        return accountMapper.selectByExample(example);
    }

    /**
     * 单个账户的金额求和-入库和出库
     *
     * @param id
     * @return
     */
    public BigDecimal getAccountSum(Long id, String timeStr, String type) {
        BigDecimal accountSum = BigDecimal.ZERO;
        try {
            DepotHeadExample example = new DepotHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andAccountidEqualTo(id).andPaytypeNotEqualTo("预付款")
                    .andOpertimeGreaterThanOrEqualTo(bTime).andOpertimeLessThanOrEqualTo(eTime);
                } else if (type.equals("date")) {
                    example.createCriteria().andAccountidEqualTo(id).andPaytypeNotEqualTo("预付款")
                    .andOpertimeLessThanOrEqualTo(mTime);
                }
            } else {
                example.createCriteria().andAccountidEqualTo(id).andPaytypeNotEqualTo("预付款");
            }
            List<DepotHead> dataList = depotHeadMapper.selectByExample(example);
            if (dataList != null) {
                for (DepotHead depotHead : dataList) {
                    if(depotHead.getChangeamount()!=null) {
                        accountSum = accountSum .add(depotHead.getChangeamount()) ;
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>查找进销存信息异常", e);
        }
        return accountSum;
    }

    /**
     * 单个账户的金额求和-收入、支出、转账的单据表头的合计
     *
     * @param id
     * @return
     */
    public BigDecimal getAccountSumByHead(Long id, String timeStr, String type) {
        BigDecimal accountSum = BigDecimal.ZERO;
        try {
            AccountHeadExample example = new AccountHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andAccountidEqualTo(id)
                            .andBilltimeGreaterThanOrEqualTo(bTime).andBilltimeLessThanOrEqualTo(eTime);
                } else if (type.equals("date")) {
                    example.createCriteria().andAccountidEqualTo(id)
                            .andBilltimeLessThanOrEqualTo(mTime);
                }
            } else {
                example.createCriteria().andAccountidEqualTo(id);
            }
            List<AccountHead> dataList = accountHeadMapper.selectByExample(example);
            if (dataList != null) {
                for (AccountHead accountHead : dataList) {
                    if(accountHead.getChangeamount()!=null) {
                        accountSum = accountSum.add(accountHead.getChangeamount());
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>查找进销存信息异常", e);
        }
        return accountSum;
    }

    /**
     * 单个账户的金额求和-收款、付款、转账、收预付款的单据明细的合计
     *
     * @param id
     * @return
     */
    public BigDecimal getAccountSumByDetail(Long id, String timeStr, String type) {
        BigDecimal accountSum =BigDecimal.ZERO ;
        try {
            AccountHeadExample example = new AccountHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andBilltimeGreaterThanOrEqualTo(bTime).andBilltimeLessThanOrEqualTo(eTime);
                } else if (type.equals("date")) {
                    example.createCriteria().andBilltimeLessThanOrEqualTo(mTime);
                }
            }
            List<AccountHead> dataList = accountHeadMapper.selectByExample(example);
            if (dataList != null) {
                String ids = "";
                for (AccountHead accountHead : dataList) {
                    ids = ids + accountHead.getId() + ",";
                }
                if (!ids.equals("")) {
                    ids = ids.substring(0, ids.length() - 1);
                }

                AccountItemExample exampleAi = new AccountItemExample();
                if (!ids.equals("")) {
                    List<Long> idList = StringUtil.strToLongList(ids);
                    exampleAi.createCriteria().andAccountidEqualTo(id).andHeaderidIn(idList);
                } else {
                    exampleAi.createCriteria().andAccountidEqualTo(id);
                }
                List<AccountItem> dataListOne = accountItemMapper.selectByExample(exampleAi);
                if (dataListOne != null) {
                    for (AccountItem accountItem : dataListOne) {
                        if(accountItem.getEachamount()!=null) {
                            accountSum = accountSum.add(accountItem.getEachamount());
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>查找进销存信息异常", e);
        } catch (Exception e) {
            logger.error(">>>>>>>>>异常信息：", e);
        }
        return accountSum;
    }

    /**
     * 单个账户的金额求和-多账户的明细合计
     *
     * @param id
     * @return
     */
    public BigDecimal getManyAccountSum(Long id, String timeStr, String type) {
        BigDecimal accountSum = BigDecimal.ZERO;
        try {
            DepotHeadExample example = new DepotHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andAccountidlistLike("%" +id.toString() + "%")
                            .andOpertimeGreaterThanOrEqualTo(bTime).andOpertimeLessThanOrEqualTo(eTime);
                } else if (type.equals("date")) {
                    example.createCriteria().andAccountidlistLike("%" +id.toString() + "%")
                            .andOpertimeLessThanOrEqualTo(mTime);
                }
            } else {
                example.createCriteria().andAccountidlistLike("%" +id.toString() + "%");
            }
            List<DepotHead> dataList = depotHeadMapper.selectByExample(example);
            if (dataList != null) {
                for (DepotHead depotHead : dataList) {
                    String accountIdList = depotHead.getAccountidlist();
                    String accountMoneyList = depotHead.getAccountmoneylist();
                    accountIdList = accountIdList.replace("[", "").replace("]", "").replace("\"", "");
                    accountMoneyList = accountMoneyList.replace("[", "").replace("]", "").replace("\"", "");
                    String[] aList = accountIdList.split(",");
                    String[] amList = accountMoneyList.split(",");
                    for (int i = 0; i < aList.length; i++) {
                        if (aList[i].toString().equals(id.toString())) {
                            accountSum = accountSum .add(new BigDecimal(amList[i]));
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            logger.error(">>>>>>>>>查找信息异常", e);
        }
        return accountSum;
    }

    public List<AccountVo4InOutList> findAccountInOutList(Long accountId, Integer offset, Integer rows) {
        return accountMapperEx.findAccountInOutList(accountId, offset, rows);
    }

    public int findAccountInOutListCount(Long accountId) {
        return accountMapperEx.findAccountInOutListCount(accountId);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAmountIsDefault(Boolean isDefault, Long accountId) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT,BusinessConstants.LOG_OPERATION_TYPE_EDIT+accountId,
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        Account account = new Account();
        account.setIsdefault(isDefault);
        AccountExample example = new AccountExample();
        example.createCriteria().andIdEqualTo(accountId);
        return accountMapper.updateByExampleSelective(account, example);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return accountMapperEx.batchDeleteAccountByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
}
