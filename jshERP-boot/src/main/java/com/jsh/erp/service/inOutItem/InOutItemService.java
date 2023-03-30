package com.jsh.erp.service.inOutItem;

import com.alibaba.fastjson.JSONObject;
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
import com.jsh.erp.exception.JshException;
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
import java.util.ArrayList;
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

    public InOutItem getInOutItem(long id)throws Exception {
        InOutItem result=null;
        try{
            result=inOutItemMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<InOutItem> getInOutItemListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<InOutItem> list = new ArrayList<>();
        try{
            InOutItemExample example = new InOutItemExample();
            example.createCriteria().andIdIn(idList);
            list = inOutItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<InOutItem> getInOutItem()throws Exception {
        InOutItemExample example = new InOutItemExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<InOutItem> list=null;
        try{
            list=inOutItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<InOutItem> select(String name, String type, String remark, int offset, int rows)throws Exception {
        List<InOutItem> list=null;
        try{
            list=inOutItemMapperEx.selectByConditionInOutItem(name, type, remark, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countInOutItem(String name, String type, String remark)throws Exception {
        Long result=null;
        try{
            result=inOutItemMapperEx.countsByInOutItem(name, type, remark);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertInOutItem(JSONObject obj, HttpServletRequest request)throws Exception {
        InOutItem inOutItem = JSONObject.parseObject(obj.toJSONString(), InOutItem.class);
        int result=0;
        try{
            inOutItem.setEnabled(true);
            result=inOutItemMapper.insertSelective(inOutItem);
            logService.insertLog("收支项目",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(inOutItem.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateInOutItem(JSONObject obj, HttpServletRequest request)throws Exception {
        InOutItem inOutItem = JSONObject.parseObject(obj.toJSONString(), InOutItem.class);
        int result=0;
        try{
            result=inOutItemMapper.updateByPrimaryKeySelective(inOutItem);
            logService.insertLog("收支项目",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(inOutItem.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteInOutItem(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteInOutItemByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteInOutItem(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteInOutItemByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteInOutItemByIds(String ids)throws Exception {
        int result = 0;
        String [] idArray=ids.split(",");
        //校验财务子表	jsh_accountitem
        List<AccountItem> accountItemList=null;
        try{
            accountItemList=accountItemMapperEx.getAccountItemListByInOutItemIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(accountItemList!=null&&accountItemList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,InOutItemIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //校验通过执行删除操作
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<InOutItem> list = getInOutItemListByIds(ids);
        for(InOutItem inOutItem: list){
            sb.append("[").append(inOutItem.getName()).append("]");
        }
        logService.insertLog("收支项目", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        try{
            result=inOutItemMapperEx.batchDeleteInOutItemByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        InOutItemExample example = new InOutItemExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<InOutItem> list = null;
        try{
            list=inOutItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }

        return list==null?0:list.size();
    }

    public List<InOutItem> findBySelect(String type)throws Exception {
        InOutItemExample example = new InOutItemExample();
        if (type.equals("in")) {
            example.createCriteria().andTypeEqualTo("收入").andEnabledEqualTo(true)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        } else if (type.equals("out")) {
            example.createCriteria().andTypeEqualTo("支出").andEnabledEqualTo(true)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        } else {
            example.createCriteria().andEnabledEqualTo(true)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        }
        example.setOrderByClause("sort asc, id desc");
        List<InOutItem> list = null;
        try{
            list=inOutItemMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String ids)throws Exception {
        logService.insertLog("收支项目",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ENABLED).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> inOutItemIds = StringUtil.strToLongList(ids);
        InOutItem inOutItem = new InOutItem();
        inOutItem.setEnabled(status);
        InOutItemExample example = new InOutItemExample();
        example.createCriteria().andIdIn(inOutItemIds);
        int result=0;
        try{
            result = inOutItemMapper.updateByExampleSelective(inOutItem, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
}
