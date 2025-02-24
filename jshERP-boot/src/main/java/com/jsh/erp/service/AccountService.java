package com.jsh.erp.service;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.base.PageDomain;
import com.jsh.erp.base.TableSupport;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.datasource.vo.AccountVo4InOutList;
import com.jsh.erp.datasource.vo.AccountVo4List;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.utils.PageUtils;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

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
    private DepotHeadMapperEx depotHeadMapperEx;

    @Resource
    private AccountHeadMapper accountHeadMapper;
    @Resource
    private AccountHeadMapperEx accountHeadMapperEx;

    @Resource
    private AccountItemMapper accountItemMapper;
    @Resource
    private AccountItemMapperEx accountItemMapperEx;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;
    @Resource
    private SystemConfigService systemConfigService;

    public Account getAccount(long id) throws Exception{
        return accountMapper.selectByPrimaryKey(id);
    }

    public List<Account> getAccountListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Account> list = new ArrayList<>();
        try{
            AccountExample example = new AccountExample();
            example.createCriteria().andIdIn(idList);
            list = accountMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Account> getAccount() throws Exception{
        List<Account> list=null;
        try{
            AccountExample example = new AccountExample();
            example.createCriteria().andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            example.setOrderByClause("sort asc, id desc");
            list=accountMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Account> getAccountByParam(String name, String serialNo) throws Exception{
        List<Account> list=null;
        try{
            list=accountMapperEx.getAccountByParam(name, serialNo);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<AccountVo4List> select(String name, String serialNo, String remark) throws Exception{
        List<AccountVo4List> list = null;
        try{
            PageUtils.startPage();
            list = accountMapperEx.selectByConditionAccount(name, serialNo, remark);
            String timeStr = Tools.getCurrentMonth();
            String bTime = Tools.firstDayOfMonth(timeStr) + BusinessConstants.DAY_FIRST_TIME;
            String eTime = Tools.lastDayOfMonth(timeStr) + BusinessConstants.DAY_LAST_TIME;
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            Map<Long, BigDecimal> thisMonthAccountSumMap = new HashMap<>();
            Map<Long, BigDecimal> thisMonthAccountSumByHeadMap = new HashMap<>();
            Map<Long, BigDecimal> thisMonthAccountSumByDetailMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumByHeadMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumByDetailMap = new HashMap<>();
            PageDomain pageDomain = TableSupport.buildPageRequest();
            int offset = (pageDomain.getCurrentPage() - 1) * pageDomain.getPageSize();
            int rows = pageDomain.getPageSize();
            List<AccountVo4Sum> thisMonthAmountList = accountMapperEx.getAccountSumByParam(name, serialNo, bTime, eTime, forceFlag, offset, rows);
            List<AccountVo4Sum> currentAmountList = accountMapperEx.getAccountSumByParam(name, serialNo, null, null, forceFlag, offset, rows);
            List<DepotHead> thisMonthManyAmountList = accountMapperEx.getManyAccountSumByParam(bTime, eTime, forceFlag);
            List<DepotHead> currentManyAmountList = accountMapperEx.getManyAccountSumByParam(null, null, forceFlag);
            for (AccountVo4Sum thisMonthAmount: thisMonthAmountList) {
                thisMonthAccountSumMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSum());
                thisMonthAccountSumByHeadMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSumByHead());
                thisMonthAccountSumByDetailMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSumByDetail());
            }
            for (AccountVo4Sum currentAmount: currentAmountList) {
                currentAccountSumMap.put(currentAmount.getId(), currentAmount.getAccountSum());
                currentAccountSumByHeadMap.put(currentAmount.getId(), currentAmount.getAccountSumByHead());
                currentAccountSumByDetailMap.put(currentAmount.getId(), currentAmount.getAccountSumByDetail());
            }
            if (null != list) {
                for (AccountVo4List al : list) {
                    DecimalFormat df = new DecimalFormat(".##");
                    BigDecimal thisMonthAmount = thisMonthAccountSumMap.get(al.getId())
                            .add(thisMonthAccountSumByHeadMap.get(al.getId()))
                            .add(thisMonthAccountSumByDetailMap.get(al.getId()))
                            .add(getManyAccountSumParse(al.getId(), thisMonthManyAmountList));
                    String thisMonthAmountFmt = "0";
                    if ((thisMonthAmount.compareTo(BigDecimal.ZERO))!=0) {
                        thisMonthAmountFmt = df.format(thisMonthAmount);
                    }
                    al.setThisMonthAmount(thisMonthAmountFmt);  //本月发生额
                    BigDecimal currentAmount = currentAccountSumMap.get(al.getId())
                            .add(currentAccountSumByHeadMap.get(al.getId()))
                            .add(currentAccountSumByDetailMap.get(al.getId()))
                            .add(getManyAccountSumParse(al.getId(), currentManyAmountList))
                            .add(al.getInitialAmount()) ;
                    al.setCurrentAmount(currentAmount);
                }
            }
        } catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertAccount(JSONObject obj, HttpServletRequest request)throws Exception {
        Account account = JSONObject.parseObject(obj.toJSONString(), Account.class);
        if(account.getInitialAmount() == null) {
            account.setInitialAmount(BigDecimal.ZERO);
        }
        List<Account> accountList = getAccountByParam(null, null);
        if(accountList.size() == 0) {
            account.setIsDefault(true);
        } else {
            account.setIsDefault(false);
        }
        account.setEnabled(true);
        int result=0;
        try{
            result = accountMapper.insertSelective(account);
            logService.insertLog("账户",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(account.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAccount(JSONObject obj, HttpServletRequest request)throws Exception {
        Account account = JSONObject.parseObject(obj.toJSONString(), Account.class);
        int result=0;
        try{
            result = accountMapper.updateByPrimaryKeySelective(account);
            logService.insertLog("账户",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(account.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteAccount(Long id, HttpServletRequest request) throws Exception{
        return batchDeleteAccountByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccount(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteAccountByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountByIds(String ids) throws Exception{
        int result=0;
        String [] idArray=ids.split(",");
        //校验财务主表	jsh_accounthead
        List<AccountHead> accountHeadList=null;
        try{
            accountHeadList = accountHeadMapperEx.getAccountHeadListByAccountIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(accountHeadList!=null&&accountHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,AccountIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //校验财务子表	jsh_accountitem
        List<AccountItem> accountItemList=null;
        try{
            accountItemList = accountItemMapperEx.getAccountItemListByAccountIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(accountItemList!=null&&accountItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,AccountIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //校验单据主表	jsh_depot_head
        List<DepotHead> depotHeadList =null;
        try{
            depotHeadList = depotHeadMapperEx.getDepotHeadListByAccountIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotHeadList!=null&&depotHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,AccountIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Account> list = getAccountListByIds(ids);
        for(Account account: list){
            sb.append("[").append(account.getName()).append("]");
        }
        logService.insertLog("账户", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        //校验通过执行删除操作
        try{
            result = accountMapperEx.batchDeleteAccountByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        AccountExample example = new AccountExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Account> list=null;
        try{
            list = accountMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public List<Account> findBySelect()throws Exception {
        AccountExample example = new AccountExample();
        example.createCriteria().andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("sort asc, id desc");
        List<Account> list=null;
        try{
            list = accountMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    /**
     * 单个账户的金额求和-入库和出库
     * @return
     */
    public BigDecimal getAccountSum(Long accountId, String beginTime, String endTime, Boolean forceFlag) {
        return accountMapperEx.getAccountSum(accountId, beginTime, endTime, forceFlag);
    }

    /**
     * 单个账户的金额求和-收入、支出、转账的单据表头的合计
     * @return
     */
    public BigDecimal getAccountSumByHead(Long accountId, String beginTime, String endTime, Boolean forceFlag) {
        return accountMapperEx.getAccountSumByHead(accountId, beginTime, endTime, forceFlag);
    }

    /**
     * 单个账户的金额求和-收款、付款、转账、收预付款的单据明细的合计
     * @return
     */
    public BigDecimal getAccountSumByDetail(Long accountId, String beginTime, String endTime, Boolean forceFlag) {
        return accountMapperEx.getAccountSumByDetail(accountId, beginTime, endTime, forceFlag);
    }

    /**
     * 单个账户的金额求和-多账户的明细合计
     * @return
     */
    public BigDecimal getManyAccountSum(Long accountId, String beginTime, String endTime, Boolean forceFlag) {
        BigDecimal accountSum = BigDecimal.ZERO;
        List<DepotHead> dataList = accountMapperEx.getManyAccountSum(accountId, beginTime, endTime, forceFlag);
        if (dataList != null) {
            for (DepotHead depotHead : dataList) {
                if(depotHead != null) {
                    String accountIdList = depotHead.getAccountIdList();
                    String accountMoneyList = depotHead.getAccountMoneyList();
                    if(StringUtil.isNotEmpty(accountIdList) && StringUtil.isNotEmpty(accountMoneyList)) {
                        String[] aList = accountIdList.split(",");
                        String[] amList = accountMoneyList.split(",");
                        for (int i = 0; i < aList.length; i++) {
                            if (aList[i].equals(accountId.toString())) {
                                if(amList.length>0) {
                                    accountSum = accountSum.add(new BigDecimal(amList[i]));
                                }
                            }
                        }
                    }
                }
            }
        }
        return accountSum;
    }

    /**
     * 单个账户的金额求和-多账户的明细合计(格式化)
     * @return
     */
    public BigDecimal getManyAccountSumParse(Long accountId, List<DepotHead> manyAmountList) {
        BigDecimal accountSum = BigDecimal.ZERO;
        if (manyAmountList != null) {
            for (DepotHead depotHead : manyAmountList) {
                String accountIdList = depotHead.getAccountIdList();
                String accountMoneyList = depotHead.getAccountMoneyList();
                if(StringUtil.isNotEmpty(accountIdList) && StringUtil.isNotEmpty(accountMoneyList)) {
                    String[] aList = accountIdList.split(",");
                    String[] amList = accountMoneyList.split(",");
                    for (int i = 0; i < aList.length; i++) {
                        if (aList[i].equals(accountId.toString())) {
                            if(amList.length>0) {
                                accountSum = accountSum.add(new BigDecimal(amList[i]));
                            }
                        }
                    }
                }
            }
        }
        return accountSum;
    }

    public List<AccountVo4InOutList> findAccountInOutList(Long accountId, String number, String beginTime, String endTime,
                                                          Boolean forceFlag, Integer offset, Integer rows) throws Exception{
        List<AccountVo4InOutList> list=null;
        try{
            list = accountMapperEx.findAccountInOutList(accountId, number, beginTime, endTime, forceFlag, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public int findAccountInOutListCount(Long accountId, String number, String beginTime, String endTime, Boolean forceFlag) throws Exception{
        int result=0;
        try{
            result = accountMapperEx.findAccountInOutListCount(accountId, number, beginTime, endTime, forceFlag);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateIsDefault(Long accountId) throws Exception{
        int result=0;
        try{
            //全部取消默认
            Account allAccount = new Account();
            allAccount.setIsDefault(false);
            AccountExample allExample = new AccountExample();
            allExample.createCriteria();
            accountMapper.updateByExampleSelective(allAccount, allExample);
            //给指定账户设为默认
            Account account = new Account();
            account.setIsDefault(true);
            AccountExample example = new AccountExample();
            example.createCriteria().andIdEqualTo(accountId);
            accountMapper.updateByExampleSelective(account, example);
            logService.insertLog("账户",BusinessConstants.LOG_OPERATION_TYPE_EDIT+accountId,
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            result = 1;
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public Map<Long,String> getAccountMap() throws Exception {
        List<Account> accountList = getAccount();
        Map<Long,String> accountMap = new HashMap<>();
        for(Account account : accountList){
            accountMap.put(account.getId(), account.getName());
        }
        return accountMap;
    }

    public String getAccountStrByIdAndMoney(Map<Long,String> accountMap, String accountIdList, String accountMoneyList){
        StringBuffer sb = new StringBuffer();
        List<Long> idList = StringUtil.strToLongList(accountIdList);
        List<BigDecimal> moneyList = StringUtil.strToBigDecimalList(accountMoneyList);
        for (int i = 0; i < idList.size(); i++) {
            Long id = idList.get(i);
            BigDecimal money =  moneyList.get(i).abs();
            sb.append(accountMap.get(id) + "(" + money + "元) ");
        }
        return sb.toString();
    }

    public List<AccountVo4List> listWithBalance(String name, String serialNo) throws Exception {
        List<AccountVo4List> list = null;
        try{
            PageUtils.startPage();
            list = accountMapperEx.selectByConditionAccount(name, serialNo, null);
            String timeStr = Tools.getCurrentMonth();
            String bTime = Tools.firstDayOfMonth(timeStr) + BusinessConstants.DAY_FIRST_TIME;
            String eTime = Tools.lastDayOfMonth(timeStr) + BusinessConstants.DAY_LAST_TIME;
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            Map<Long, BigDecimal> thisMonthAccountSumMap = new HashMap<>();
            Map<Long, BigDecimal> thisMonthAccountSumByHeadMap = new HashMap<>();
            Map<Long, BigDecimal> thisMonthAccountSumByDetailMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumByHeadMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumByDetailMap = new HashMap<>();
            PageDomain pageDomain = TableSupport.buildPageRequest();
            int offset = (pageDomain.getCurrentPage() - 1) * pageDomain.getPageSize();
            int rows = pageDomain.getPageSize();
            List<AccountVo4Sum> thisMonthAmountList = accountMapperEx.getAccountSumByParam(name, serialNo, bTime, eTime, forceFlag, offset, rows);
            List<AccountVo4Sum> currentAmountList = accountMapperEx.getAccountSumByParam(name, serialNo, null, null, forceFlag, offset, rows);
            List<DepotHead> thisMonthManyAmountList = accountMapperEx.getManyAccountSumByParam(bTime, eTime, forceFlag);
            List<DepotHead> currentManyAmountList = accountMapperEx.getManyAccountSumByParam(null, null, forceFlag);
            for (AccountVo4Sum thisMonthAmount: thisMonthAmountList) {
                thisMonthAccountSumMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSum());
                thisMonthAccountSumByHeadMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSumByHead());
                thisMonthAccountSumByDetailMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSumByDetail());
            }
            for (AccountVo4Sum currentAmount: currentAmountList) {
                currentAccountSumMap.put(currentAmount.getId(), currentAmount.getAccountSum());
                currentAccountSumByHeadMap.put(currentAmount.getId(), currentAmount.getAccountSumByHead());
                currentAccountSumByDetailMap.put(currentAmount.getId(), currentAmount.getAccountSumByDetail());
            }
            if (null != list) {
                for (AccountVo4List al : list) {
                    DecimalFormat df = new DecimalFormat(".##");
                    BigDecimal thisMonthAmount = thisMonthAccountSumMap.get(al.getId())
                            .add(thisMonthAccountSumByHeadMap.get(al.getId()))
                            .add(thisMonthAccountSumByDetailMap.get(al.getId()))
                            .add(getManyAccountSumParse(al.getId(), thisMonthManyAmountList));
                    String thisMonthAmountFmt = "0";
                    if ((thisMonthAmount.compareTo(BigDecimal.ZERO))!=0) {
                        thisMonthAmountFmt = df.format(thisMonthAmount);
                    }
                    al.setThisMonthAmount(thisMonthAmountFmt);  //本月发生额
                    BigDecimal currentAmount = currentAccountSumMap.get(al.getId())
                            .add(currentAccountSumByHeadMap.get(al.getId()))
                            .add(currentAccountSumByDetailMap.get(al.getId()))
                            .add(getManyAccountSumParse(al.getId(), currentManyAmountList))
                            .add(al.getInitialAmount());
                    al.setCurrentAmount(currentAmount);
                }
            }
        } catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Map<String, Object> getStatistics(String name, String serialNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Account> list = getAccountByParam(name, serialNo);
            String timeStr = Tools.getCurrentMonth();
            String bTime = Tools.firstDayOfMonth(timeStr) + BusinessConstants.DAY_FIRST_TIME;
            String eTime = Tools.lastDayOfMonth(timeStr) + BusinessConstants.DAY_LAST_TIME;
            BigDecimal allMonthAmount = BigDecimal.ZERO;
            BigDecimal allCurrentAmount = BigDecimal.ZERO;
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            Map<Long, BigDecimal> thisMonthAccountSumMap = new HashMap<>();
            Map<Long, BigDecimal> thisMonthAccountSumByHeadMap = new HashMap<>();
            Map<Long, BigDecimal> thisMonthAccountSumByDetailMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumByHeadMap = new HashMap<>();
            Map<Long, BigDecimal> currentAccountSumByDetailMap = new HashMap<>();
            List<AccountVo4Sum> thisMonthAmountList = accountMapperEx.getAccountSumByParam(name, serialNo, bTime, eTime, forceFlag, null, null);
            List<AccountVo4Sum> currentAmountList = accountMapperEx.getAccountSumByParam(name, serialNo, null, null, forceFlag, null, null);
            List<DepotHead> thisMonthManyAmountList = accountMapperEx.getManyAccountSumByParam(bTime, eTime, forceFlag);
            List<DepotHead> currentManyAmountList = accountMapperEx.getManyAccountSumByParam(null, null, forceFlag);
            for (AccountVo4Sum thisMonthAmount: thisMonthAmountList) {
                thisMonthAccountSumMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSum());
                thisMonthAccountSumByHeadMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSumByHead());
                thisMonthAccountSumByDetailMap.put(thisMonthAmount.getId(), thisMonthAmount.getAccountSumByDetail());
            }
            for (AccountVo4Sum currentAmount: currentAmountList) {
                currentAccountSumMap.put(currentAmount.getId(), currentAmount.getAccountSum());
                currentAccountSumByHeadMap.put(currentAmount.getId(), currentAmount.getAccountSumByHead());
                currentAccountSumByDetailMap.put(currentAmount.getId(), currentAmount.getAccountSumByDetail());
            }
            if (null != list) {
                for (Account a : list) {
                    BigDecimal monthAmount = thisMonthAccountSumMap.get(a.getId())
                            .add(thisMonthAccountSumByHeadMap.get(a.getId()))
                            .add(thisMonthAccountSumByDetailMap.get(a.getId()))
                            .add(getManyAccountSumParse(a.getId(), thisMonthManyAmountList));
                    BigDecimal currentAmount = currentAccountSumMap.get(a.getId())
                            .add(currentAccountSumByHeadMap.get(a.getId()))
                            .add(currentAccountSumByDetailMap.get(a.getId()))
                            .add(getManyAccountSumParse(a.getId(), currentManyAmountList))
                            .add(a.getInitialAmount());
                    allMonthAmount = allMonthAmount.add(monthAmount);
                    allCurrentAmount = allCurrentAmount.add(currentAmount);
                }
            }
            map.put("allMonthAmount", priceFormat(allMonthAmount));  //本月发生额
            map.put("allCurrentAmount", priceFormat(allCurrentAmount));  //当前总金额
        } catch (Exception e) {
            JshException.readFail(logger, e);
        }
        return map;
    }

    /**
     * 价格格式化
     * @param price
     * @return
     */
    private String priceFormat(BigDecimal price) {
        String priceFmt = "0";
        DecimalFormat df = new DecimalFormat(".##");
        if ((price.compareTo(BigDecimal.ZERO))!=0) {
            priceFmt = df.format(price);
        }
        return priceFmt;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String ids)throws Exception {
        logService.insertLog("账户",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ENABLED).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> accountIds = StringUtil.strToLongList(ids);
        Account account = new Account();
        account.setEnabled(status);
        AccountExample example = new AccountExample();
        example.createCriteria().andIdIn(accountIds);
        int result=0;
        try{
            result = accountMapper.updateByExampleSelective(account, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
}
