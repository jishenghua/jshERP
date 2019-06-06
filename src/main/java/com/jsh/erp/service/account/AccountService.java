package com.jsh.erp.service.account;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.*;
import com.jsh.erp.datasource.vo.AccountVo4InOutList;
import com.jsh.erp.datasource.vo.AccountVo4List;
import com.jsh.erp.exception.BusinessRunTimeException;
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

    public Account getAccount(long id) throws Exception{
        return accountMapper.selectByPrimaryKey(id);
    }

    public List<Account> getAccount() throws Exception{
        AccountExample example = new AccountExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Account> list=null;
        try{
            list=accountMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;

    }

    public List<AccountVo4List> select(String name, String serialNo, String remark, int offset, int rows) throws Exception{
        List<AccountVo4List> resList = new ArrayList<AccountVo4List>();
        List<AccountVo4List> list=null;
        try{
            list = accountMapperEx.selectByConditionAccount(name, serialNo, remark, offset, rows);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
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

    public Long countAccount(String name, String serialNo, String remark)throws Exception {
        Long result=null;
        try{
            result=accountMapperEx.countsByAccount(name, serialNo, remark);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertAccount(String beanJson, HttpServletRequest request)throws Exception {
        Account account = JSONObject.parseObject(beanJson, Account.class);
        if(account.getInitialamount() == null) {
            account.setInitialamount(BigDecimal.ZERO);
        }
        account.setIsdefault(false);
        int result=0;
        try{
            result = accountMapper.insertSelective(account);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAccount(String beanJson, Long id)throws Exception {
        Account account = JSONObject.parseObject(beanJson, Account.class);
        account.setId(id);
        int result=0;
        try{
            result = accountMapper.updateByPrimaryKeySelective(account);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteAccount(Long id) throws Exception{
        int result=0;
        try{
            result = accountMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccount(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        AccountExample example = new AccountExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result = accountMapper.deleteByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
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
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list==null?0:list.size();
    }

    public List<Account> findBySelect()throws Exception {
        AccountExample example = new AccountExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("id desc");
        List<Account> list=null;
        try{
            list = accountMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    /**
     * 单个账户的金额求和-入库和出库
     *
     * @param id
     * @return
     */
    public BigDecimal getAccountSum(Long id, String timeStr, String type) throws Exception{
        BigDecimal accountSum = BigDecimal.ZERO;
        try {
            DepotHeadExample example = new DepotHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andAccountidEqualTo(id).andPaytypeNotEqualTo("预付款")
                    .andOpertimeGreaterThanOrEqualTo(bTime).andOpertimeLessThanOrEqualTo(eTime)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                } else if (type.equals("date")) {
                    example.createCriteria().andAccountidEqualTo(id).andPaytypeNotEqualTo("预付款")
                    .andOpertimeLessThanOrEqualTo(mTime).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                }
            } else {
                example.createCriteria().andAccountidEqualTo(id).andPaytypeNotEqualTo("预付款")
                        .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            }
            List<DepotHead> dataList=null;
            try{
                dataList = depotHeadMapper.selectByExample(example);
            }catch(Exception e){
                logger.error("异常码[{}],异常提示[{}],异常[{}]",
                        ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
                throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                        ExceptionConstants.DATA_READ_FAIL_MSG);
            }
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
    public BigDecimal getAccountSumByHead(Long id, String timeStr, String type) throws Exception{
        BigDecimal accountSum = BigDecimal.ZERO;
        try {
            AccountHeadExample example = new AccountHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andAccountidEqualTo(id)
                            .andBilltimeGreaterThanOrEqualTo(bTime).andBilltimeLessThanOrEqualTo(eTime)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                } else if (type.equals("date")) {
                    example.createCriteria().andAccountidEqualTo(id)
                            .andBilltimeLessThanOrEqualTo(mTime)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                }
            } else {
                example.createCriteria().andAccountidEqualTo(id)
                        .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            }
            List<AccountHead> dataList=null;
            try{
                dataList = accountHeadMapper.selectByExample(example);
            }catch(Exception e){
                logger.error("异常码[{}],异常提示[{}],异常[{}]",
                        ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
                throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                        ExceptionConstants.DATA_READ_FAIL_MSG);
            }
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
    public BigDecimal getAccountSumByDetail(Long id, String timeStr, String type)throws Exception {
        BigDecimal accountSum =BigDecimal.ZERO ;
        try {
            AccountHeadExample example = new AccountHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andBilltimeGreaterThanOrEqualTo(bTime).andBilltimeLessThanOrEqualTo(eTime)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                } else if (type.equals("date")) {
                    example.createCriteria().andBilltimeLessThanOrEqualTo(mTime)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                }
            }
            List<AccountHead> dataList=null;
            try{
                dataList = accountHeadMapper.selectByExample(example);
            }catch(Exception e){
                logger.error("异常码[{}],异常提示[{}],异常[{}]",
                        ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
                throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                        ExceptionConstants.DATA_READ_FAIL_MSG);
            }
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
                    exampleAi.createCriteria().andAccountidEqualTo(id).andHeaderidIn(idList)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                } else {
                    exampleAi.createCriteria().andAccountidEqualTo(id)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
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
    public BigDecimal getManyAccountSum(Long id, String timeStr, String type)throws Exception {
        BigDecimal accountSum = BigDecimal.ZERO;
        try {
            DepotHeadExample example = new DepotHeadExample();
            if (!timeStr.equals("")) {
                Date bTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                Date eTime = StringUtil.getDateByString(timeStr + "-31 00:00:00", null);
                Date mTime = StringUtil.getDateByString(timeStr + "-01 00:00:00", null);
                if (type.equals("month")) {
                    example.createCriteria().andAccountidlistLike("%" +id.toString() + "%")
                            .andOpertimeGreaterThanOrEqualTo(bTime).andOpertimeLessThanOrEqualTo(eTime)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                } else if (type.equals("date")) {
                    example.createCriteria().andAccountidlistLike("%" +id.toString() + "%")
                            .andOpertimeLessThanOrEqualTo(mTime)
                            .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
                }
            } else {
                example.createCriteria().andAccountidlistLike("%" +id.toString() + "%")
                        .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            }
            List<DepotHead> dataList=null;
            try{
                dataList = depotHeadMapper.selectByExample(example);
            }catch(Exception e){
                logger.error("异常码[{}],异常提示[{}],异常[{}]",
                        ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
                throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                        ExceptionConstants.DATA_READ_FAIL_MSG);
            }
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

    public List<AccountVo4InOutList> findAccountInOutList(Long accountId, Integer offset, Integer rows) throws Exception{
        List<AccountVo4InOutList> list=null;
        try{
            list = accountMapperEx.findAccountInOutList(accountId, offset, rows);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public int findAccountInOutListCount(Long accountId) throws Exception{
        int result=0;
        try{
            result = accountMapperEx.findAccountInOutListCount(accountId);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAmountIsDefault(Boolean isDefault, Long accountId) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT,BusinessConstants.LOG_OPERATION_TYPE_EDIT+accountId,
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        Account account = new Account();
        account.setIsdefault(isDefault);
        AccountExample example = new AccountExample();
        example.createCriteria().andIdEqualTo(accountId);
        int result=0;
        try{
            result = accountMapper.updateByExampleSelective(account, example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountByIds(String ids) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            result = accountMapperEx.batchDeleteAccountByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  正常删除，要考虑数据完整性，进行完整性校验
     * create time: 2019/4/10 10:31
     * @Param: ids
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountByIdsNormal(String ids) throws Exception{
        /**
         * 校验：
         * 1、财务主表	jsh_accounthead
         * 2、财务子表	jsh_accountitem
         * 3、单据主表	jsh_depothead
         * 是否有相关数据
         * */
        int deleteTotal=0;
        if(StringUtils.isEmpty(ids)){
            return deleteTotal;
        }
        String [] idArray=ids.split(",");
        /**
         * 校验财务主表	jsh_accounthead
         * */
        List<AccountHead> accountHeadList=null;
        try{
            accountHeadList = accountHeadMapperEx.getAccountHeadListByAccountIds(idArray);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        if(accountHeadList!=null&&accountHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,AccountIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验财务子表	jsh_accountitem
         * */
        List<AccountItem> accountItemList=null;
        try{
            accountItemList = accountItemMapperEx.getAccountItemListByAccountIds(idArray);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        if(accountItemList!=null&&accountItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,AccountIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验单据主表	jsh_depothead
         * */
        List<DepotHead> depotHeadList =null;
        try{
            depotHeadList = depotHeadMapperEx.getDepotHeadListByAccountIds(idArray);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        if(depotHeadList!=null&&depotHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,AccountIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验通过执行删除操作
         * */
        deleteTotal= batchDeleteAccountByIds(ids);
        return deleteTotal;

    }
}
