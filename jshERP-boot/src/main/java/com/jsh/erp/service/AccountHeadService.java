package com.jsh.erp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.AccountHeadMapper;
import com.jsh.erp.datasource.mappers.AccountHeadMapperEx;
import com.jsh.erp.datasource.mappers.AccountItemMapperEx;
import com.jsh.erp.datasource.mappers.AccountMapper;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jsh.erp.utils.Tools.getCenternTime;

@Service
public class AccountHeadService {
    private Logger logger = LoggerFactory.getLogger(AccountHeadService.class);
    @Resource
    private AccountHeadMapper accountHeadMapper;
    @Resource
    private AccountHeadMapperEx accountHeadMapperEx;
    @Resource
    private OrgaUserRelService orgaUserRelService;
    @Resource
    private AccountItemService accountItemService;
    @Resource
    private UserService userService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private SystemConfigService systemConfigService;
    @Resource
    private LogService logService;
    @Resource
    private AccountItemMapperEx accountItemMapperEx;
    @Resource
    private AccountMapper accountMapper;

    public AccountHead getAccountHead(long id) throws Exception {
        AccountHead result=null;
        try{
            result=accountHeadMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<AccountHead> getAccountHeadListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<AccountHead> list = new ArrayList<>();
        try{
            AccountHeadExample example = new AccountHeadExample();
            example.createCriteria().andIdIn(idList);
            list = accountHeadMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<AccountHead> getAccountHead() throws Exception{
        AccountHeadExample example = new AccountHeadExample();
        List<AccountHead> list=null;
        try{
            list=accountHeadMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<AccountHeadVo4ListEx> select(String type, String billNo, String beginTime, String endTime,
                                             Long organId, Long creator, Long handsPersonId, Long accountId, String status,
                                             String remark, String number, Long inOutItemId) throws Exception{
        List<AccountHeadVo4ListEx> list = new ArrayList<>();
        try{
            String [] creatorArray = getCreatorArray();
            beginTime = Tools.parseDayToTime(beginTime,BusinessConstants.DAY_FIRST_TIME);
            endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
            PageUtils.startPage();
            list = accountHeadMapperEx.selectByConditionAccountHead(type, creatorArray, billNo,
                    beginTime, endTime, organId, creator, handsPersonId, accountId, status, remark, number, inOutItemId);
            if (null != list) {
                for (AccountHeadVo4ListEx ah : list) {
                    if(ah.getChangeAmount() != null) {
                        if(BusinessConstants.TYPE_MONEY_IN.equals(ah.getType())) {
                            ah.setChangeAmount(ah.getChangeAmount());
                        } else if(BusinessConstants.TYPE_MONEY_OUT.equals(ah.getType())) {
                            ah.setChangeAmount(BigDecimal.ZERO.subtract(ah.getChangeAmount()));
                        } else {
                            ah.setChangeAmount(ah.getChangeAmount().abs());
                        }
                    }
                    if(ah.getTotalPrice() != null) {
                        if(BusinessConstants.TYPE_MONEY_IN.equals(ah.getType())) {
                            ah.setTotalPrice(ah.getTotalPrice());
                        } else if(BusinessConstants.TYPE_MONEY_OUT.equals(ah.getType())) {
                            ah.setTotalPrice(BigDecimal.ZERO.subtract(ah.getTotalPrice()));
                        } else {
                            ah.setTotalPrice(ah.getTotalPrice().abs());
                        }
                    }
                    if(ah.getBillTime() !=null) {
                        ah.setBillTimeStr(getCenternTime(ah.getBillTime()));
                    }
                }
            }
        } catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    /**
     * 根据角色类型获取操作员数组
     * @return
     * @throws Exception
     */
    public String[] getCreatorArray() throws Exception {
        String creator = "";
        User user = userService.getCurrentUser();
        String roleType = userService.getRoleTypeByUserId(user.getId()).getType(); //角色类型
        if(BusinessConstants.ROLE_TYPE_PRIVATE.equals(roleType)) {
            creator = user.getId().toString();
        } else if(BusinessConstants.ROLE_TYPE_THIS_ORG.equals(roleType)) {
            creator = orgaUserRelService.getUserIdListByUserId(user.getId());
        }
        String [] creatorArray=null;
        if(StringUtil.isNotEmpty(creator)){
            creatorArray = creator.split(",");
        }
        return creatorArray;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertAccountHead(JSONObject obj, HttpServletRequest request) throws Exception{
        AccountHead accountHead = JSONObject.parseObject(obj.toJSONString(), AccountHead.class);
        int result=0;
        try{
            User userInfo=userService.getCurrentUser();
            accountHead.setCreator(userInfo==null?null:userInfo.getId());
            result = accountHeadMapper.insertSelective(accountHead);
            logService.insertLog("财务",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(accountHead.getBillNo()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAccountHead(JSONObject obj, HttpServletRequest request)throws Exception {
        AccountHead accountHead = JSONObject.parseObject(obj.toJSONString(), AccountHead.class);
        int result=0;
        try{
            result = accountHeadMapper.updateByPrimaryKeySelective(accountHead);
            logService.insertLog("财务",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(accountHead.getBillNo()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteAccountHead(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteAccountHeadByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountHead(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteAccountHeadByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountHeadByIds(String ids)throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        List<AccountHead> list = getAccountHeadListByIds(ids);
        for(AccountHead accountHead: list){
            if(!"0".equals(accountHead.getStatus())) {
                throw new BusinessRunTimeException(ExceptionConstants.ACCOUNT_HEAD_UN_AUDIT_DELETE_FAILED_CODE,
                        String.format(ExceptionConstants.ACCOUNT_HEAD_UN_AUDIT_DELETE_FAILED_MSG));
            }
        }
        //删除主表
        accountItemMapperEx.batchDeleteAccountItemByHeadIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        //删除子表
        accountHeadMapperEx.batchDeleteAccountHeadByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        //路径列表
        List<String> pathList = new ArrayList<>();
        for(AccountHead accountHead: list){
            sb.append("[").append(accountHead.getBillNo()).append("]");
            if(StringUtil.isNotEmpty(accountHead.getFileName())) {
                pathList.add(accountHead.getFileName());
            }
            if("收预付款".equals(accountHead.getType())){
                if (accountHead.getOrganId() != null) {
                    //更新会员预付款
                    supplierService.updateAdvanceIn(accountHead.getOrganId());
                }
            }
        }
        //逻辑删除文件
        systemConfigService.deleteFileByPathList(pathList);
        logService.insertLog("财务", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        return 1;
    }

    /**
     * 校验单据编号是否存在
     * @param id
     * @param billNo
     * @return
     * @throws Exception
     */
    public int checkIsBillNoExist(Long id, String billNo)throws Exception {
        AccountHeadExample example = new AccountHeadExample();
        example.createCriteria().andIdNotEqualTo(id).andBillNoEqualTo(billNo).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<AccountHead> list = null;
        try{
            list = accountHeadMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(String status, String accountHeadIds)throws Exception {
        int result = 0;
        try{
            List<Long> ahIds = new ArrayList<>();
            List<Long> ids = StringUtil.strToLongList(accountHeadIds);
            for(Long id: ids) {
                AccountHead accountHead = getAccountHead(id);
                if("0".equals(status)){
                    if("1".equals(accountHead.getStatus())) {
                        ahIds.add(id);
                    }
                } else if("1".equals(status)){
                    if("0".equals(accountHead.getStatus())) {
                        ahIds.add(id);
                    }
                }
            }
            if(ahIds.size()>0) {
                AccountHead accountHead = new AccountHead();
                accountHead.setStatus(status);
                AccountHeadExample example = new AccountHeadExample();
                example.createCriteria().andIdIn(ahIds);
                result = accountHeadMapper.updateByExampleSelective(accountHead, example);
            } else {
                return 1;
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void addAccountHeadAndDetail(String beanJson, String rows, HttpServletRequest request) throws Exception {
        AccountHead accountHead = JSONObject.parseObject(beanJson, AccountHead.class);
        //校验单号是否重复
        if(checkIsBillNoExist(0L, accountHead.getBillNo())>0) {
            throw new BusinessRunTimeException(ExceptionConstants.ACCOUNT_HEAD_BILL_NO_EXIST_CODE,
                    String.format(ExceptionConstants.ACCOUNT_HEAD_BILL_NO_EXIST_MSG));
        }
        //校验付款账户和明细中的账户重复（转账单据）
        if(BusinessConstants.TYPE_GIRO.equals(accountHead.getType())) {
            JSONArray rowArr = JSONArray.parseArray(rows);
            if (null != rowArr && rowArr.size()>0) {
                for (int i = 0; i < rowArr.size(); i++) {
                    JSONObject object = JSONObject.parseObject(rowArr.getString(i));
                    if (object.get("accountId") != null && !object.get("accountId").equals("")) {
                        Long accoutId = object.getLong("accountId");
                        String accountName = accountMapper.selectByPrimaryKey(accoutId).getName();
                        if(accoutId.equals(accountHead.getAccountId())) {
                            throw new BusinessRunTimeException(ExceptionConstants.ACCOUNT_HEAD_ACCOUNT_REPEAT_CODE,
                                    String.format(ExceptionConstants.ACCOUNT_HEAD_ACCOUNT_REPEAT_MSG, accountName));
                        }
                    }
                }
            }
        }
        User userInfo=userService.getCurrentUser();
        accountHead.setCreator(userInfo==null?null:userInfo.getId());
        if(StringUtil.isEmpty(accountHead.getStatus())) {
            accountHead.setStatus(BusinessConstants.BILLS_STATUS_UN_AUDIT);
        }
        accountHeadMapper.insertSelective(accountHead);
        //根据单据编号查询单据id
        AccountHeadExample dhExample = new AccountHeadExample();
        dhExample.createCriteria().andBillNoEqualTo(accountHead.getBillNo()).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<AccountHead> list = accountHeadMapper.selectByExample(dhExample);
        if(list!=null) {
            Long headId = list.get(0).getId();
            String type = list.get(0).getType();
            /**处理单据子表信息*/
            accountItemService.saveDetials(rows, headId, type, request);
        }
        if("收预付款".equals(accountHead.getType())){
            //更新会员预付款
            supplierService.updateAdvanceIn(accountHead.getOrganId());
        }
        logService.insertLog("财务单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(accountHead.getBillNo()).toString(), request);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void updateAccountHeadAndDetail(String beanJson, String rows, HttpServletRequest request) throws Exception {
        AccountHead accountHead = JSONObject.parseObject(beanJson, AccountHead.class);
        //校验单号是否重复
        if(checkIsBillNoExist(accountHead.getId(), accountHead.getBillNo())>0) {
            throw new BusinessRunTimeException(ExceptionConstants.ACCOUNT_HEAD_BILL_NO_EXIST_CODE,
                    String.format(ExceptionConstants.ACCOUNT_HEAD_BILL_NO_EXIST_MSG));
        }
        accountHeadMapper.updateByPrimaryKeySelective(accountHead);
        //根据单据编号查询单据id
        AccountHeadExample dhExample = new AccountHeadExample();
        dhExample.createCriteria().andBillNoEqualTo(accountHead.getBillNo()).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<AccountHead> list = accountHeadMapper.selectByExample(dhExample);
        if(list!=null) {
            Long headId = list.get(0).getId();
            String type = list.get(0).getType();
            /**处理单据子表信息*/
            accountItemService.saveDetials(rows, headId, type, request);
        }
        if("收预付款".equals(accountHead.getType())){
            //更新会员预付款
            supplierService.updateAdvanceIn(accountHead.getOrganId());
        }
        logService.insertLog("财务单据",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(accountHead.getBillNo()).toString(), request);
    }

    public List<AccountHeadVo4ListEx> getDetailByNumber(String billNo)throws Exception {
        List<AccountHeadVo4ListEx> resList = new ArrayList<AccountHeadVo4ListEx>();
        List<AccountHeadVo4ListEx> list = null;
        try{
            list = accountHeadMapperEx.getDetailByNumber(billNo);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if (null != list) {
            for (AccountHeadVo4ListEx ah : list) {
                if(ah.getChangeAmount() != null) {
                    if(BusinessConstants.TYPE_MONEY_IN.equals(ah.getType())) {
                        ah.setChangeAmount(ah.getChangeAmount());
                    } else if(BusinessConstants.TYPE_MONEY_OUT.equals(ah.getType())) {
                        ah.setChangeAmount(BigDecimal.ZERO.subtract(ah.getChangeAmount()));
                    } else {
                        ah.setChangeAmount(ah.getChangeAmount().abs());
                    }
                }
                if(ah.getTotalPrice() != null) {
                    if(BusinessConstants.TYPE_MONEY_IN.equals(ah.getType())) {
                        ah.setTotalPrice(ah.getTotalPrice());
                    } else if(BusinessConstants.TYPE_MONEY_OUT.equals(ah.getType())) {
                        ah.setTotalPrice(BigDecimal.ZERO.subtract(ah.getTotalPrice()));
                    } else {
                        ah.setTotalPrice(ah.getTotalPrice().abs());
                    }
                }
                if(ah.getBillTime() !=null) {
                    ah.setBillTimeStr(getCenternTime(ah.getBillTime()));
                }
                resList.add(ah);
            }
        }
        return resList;
    }

    public List<AccountItem> getFinancialBillNoByBillIdList(List<Long> idList) {
        return accountHeadMapperEx.getFinancialBillNoByBillIdList(idList);
    }

    public List<AccountHead> getFinancialBillNoByBillId(Long billId) {
        return accountHeadMapperEx.getFinancialBillNoByBillId(billId);
    }
}
