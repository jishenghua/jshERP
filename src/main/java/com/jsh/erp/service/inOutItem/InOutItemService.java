package com.jsh.erp.service.inOutItem;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.AccountItem;
import com.jsh.erp.datasource.entities.InOutItem;
import com.jsh.erp.datasource.entities.InOutItemExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.AccountItemMapperEx;
import com.jsh.erp.datasource.mappers.InOutItemMapper;
import com.jsh.erp.datasource.mappers.InOutItemMapperEx;
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
import java.util.Date;
import java.util.List;

@Service
public class InOutItemService {
    private Logger logger = LoggerFactory.getLogger(InOutItemService.class);

    @Resource
    private InOutItemMapper inOutItemMapper;

    @Resource
    private InOutItemMapperEx inOutItemMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    @Resource
    private AccountItemMapperEx accountItemMapperEx;

    public InOutItem getInOutItem(long id) {
        return inOutItemMapper.selectByPrimaryKey(id);
    }

    public List<InOutItem> getInOutItem() {
        InOutItemExample example = new InOutItemExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        return inOutItemMapper.selectByExample(example);
    }

    public List<InOutItem> select(String name, String type, String remark, int offset, int rows) {
        return inOutItemMapperEx.selectByConditionInOutItem(name, type, remark, offset, rows);
    }

    public Long countInOutItem(String name, String type, String remark) {
        return inOutItemMapperEx.countsByInOutItem(name, type, remark);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertInOutItem(String beanJson, HttpServletRequest request) {
        InOutItem depot = JSONObject.parseObject(beanJson, InOutItem.class);
        return inOutItemMapper.insertSelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateInOutItem(String beanJson, Long id) {
        InOutItem depot = JSONObject.parseObject(beanJson, InOutItem.class);
        depot.setId(id);
        return inOutItemMapper.updateByPrimaryKeySelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteInOutItem(Long id) {
        return inOutItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteInOutItem(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        InOutItemExample example = new InOutItemExample();
        example.createCriteria().andIdIn(idList);
        return inOutItemMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        InOutItemExample example = new InOutItemExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<InOutItem> list = inOutItemMapper.selectByExample(example);
        return list.size();
    }

    public List<InOutItem> findBySelect(String type) {
        InOutItemExample example = new InOutItemExample();
        if (type.equals("in")) {
            example.createCriteria().andTypeEqualTo("收入").andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        } else if (type.equals("out")) {
            example.createCriteria().andTypeEqualTo("支出").andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        }
        example.setOrderByClause("id desc");
        return inOutItemMapper.selectByExample(example);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteInOutItemByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_IN_OUT_ITEM,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return inOutItemMapperEx.batchDeleteInOutItemByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  正常删除，要考虑数据完整性，进行完整性校验
     * create time: 2019/4/10 16:23
     * @Param: ids
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteInOutItemByIdsNormal(String ids) throws Exception {
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
        List<AccountItem> accountItemList=accountItemMapperEx.getAccountItemListByInOutItemIds(idArray);
        if(accountItemList!=null&&accountItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,InOutItemIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验通过执行删除操作
         * */
        deleteTotal= batchDeleteInOutItemByIds(ids);
        return deleteTotal;

    }
}
