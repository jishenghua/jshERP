package com.jsh.erp.service.accountHead;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.AccountHeadMapper;
import com.jsh.erp.datasource.mappers.AccountHeadMapperEx;
import com.jsh.erp.datasource.mappers.AccountItemMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
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

@Service
public class AccountHeadService {
    private Logger logger = LoggerFactory.getLogger(AccountHeadService.class);

    @Resource
    private AccountHeadMapper accountHeadMapper;

    @Resource
    private AccountHeadMapperEx accountHeadMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    @Resource
    private AccountItemMapperEx accountItemMapperEx;

    public AccountHead getAccountHead(long id) {
        return accountHeadMapper.selectByPrimaryKey(id);
    }

    public List<AccountHead> getAccountHead() {
        AccountHeadExample example = new AccountHeadExample();
        return accountHeadMapper.selectByExample(example);
    }

    public List<AccountHeadVo4ListEx> select(String type, String billNo, String beginTime, String endTime, int offset, int rows) {
        List<AccountHeadVo4ListEx> resList = new ArrayList<AccountHeadVo4ListEx>();
        List<AccountHeadVo4ListEx> list = accountHeadMapperEx.selectByConditionAccountHead(type, billNo, beginTime, endTime, offset, rows);
        if (null != list) {
            for (AccountHeadVo4ListEx ah : list) {
                if(ah.getChangeamount() != null) {
                    ah.setChangeamount(ah.getChangeamount().abs());
                }
                if(ah.getTotalprice() != null) {
                    ah.setTotalprice(ah.getTotalprice().abs());
                }
                resList.add(ah);
            }
        }
        return resList;
    }

    public Long countAccountHead(String type, String billNo, String beginTime, String endTime) {
        return accountHeadMapperEx.countsByAccountHead(type, billNo, beginTime, endTime);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertAccountHead(String beanJson, HttpServletRequest request) {
        AccountHead accountHead = JSONObject.parseObject(beanJson, AccountHead.class);
        return accountHeadMapper.insertSelective(accountHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateAccountHead(String beanJson, Long id) {
        AccountHead accountHead = JSONObject.parseObject(beanJson, AccountHead.class);
        accountHead.setId(id);
        return accountHeadMapper.updateByPrimaryKeySelective(accountHead);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteAccountHead(Long id) {
        return accountHeadMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountHead(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        AccountHeadExample example = new AccountHeadExample();
        example.createCriteria().andIdIn(idList);
        return accountHeadMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        AccountHeadExample example = new AccountHeadExample();
        example.createCriteria().andIdNotEqualTo(id).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<AccountHead> list = accountHeadMapper.selectByExample(example);
        return list.size();
    }

    public Long getMaxId() {
        return accountHeadMapperEx.getMaxId();
    }

    public BigDecimal findAllMoney(Integer supplierId, String type, String mode, String endTime) {
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "TotalPrice";
        }
        return accountHeadMapperEx.findAllMoney(supplierId, type, modeName, endTime);
    }

    public List<AccountHeadVo4ListEx> getDetailByNumber(String billNo) {
        List<AccountHeadVo4ListEx> resList = new ArrayList<AccountHeadVo4ListEx>();
        List<AccountHeadVo4ListEx> list = accountHeadMapperEx.getDetailByNumber(billNo);
        if (null != list) {
            for (AccountHeadVo4ListEx ah : list) {
                if(ah.getChangeamount() != null) {
                    ah.setChangeamount(ah.getChangeamount().abs());
                }
                if(ah.getTotalprice() != null) {
                    ah.setTotalprice(ah.getTotalprice().abs());
                }
                resList.add(ah);
            }
        }
        return resList;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountHeadByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_ACCOUNT_HEAD,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return accountHeadMapperEx.batchDeleteAccountHeadByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  正常删除，要考虑数据完整性，进行完整性校验
     * create time: 2019/4/10 15:49
     * @Param: ids
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAccountHeadByIdsNormal(String ids) throws Exception {
        /**
         * 校验
         * 1、财务子表	jsh_accountitem
         * 是否有相关数据
         * */
        int deleteTotal=0;
        if(StringUtils.isEmpty(ids)){
            return deleteTotal;
        }
        String [] idArray=ids.split(",");
        /**
         * 校验财务子表	jsh_accountitem
         * */
        List<AccountItem> accountItemList=accountItemMapperEx.getAccountItemListByHeaderIds(idArray);
        if(accountItemList!=null&&accountItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,HeaderIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验通过执行删除操作
         * */
        deleteTotal= batchDeleteAccountHeadByIds(ids);
        return deleteTotal;
    }
}
