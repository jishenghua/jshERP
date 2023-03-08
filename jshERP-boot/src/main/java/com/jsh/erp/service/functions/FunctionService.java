package com.jsh.erp.service.functions;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Function;
import com.jsh.erp.datasource.entities.FunctionEx;
import com.jsh.erp.datasource.entities.FunctionExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.FunctionMapper;
import com.jsh.erp.datasource.mappers.FunctionMapperEx;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
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
public class FunctionService {
    private Logger logger = LoggerFactory.getLogger(FunctionService.class);

    @Resource
    private FunctionMapper functionsMapper;

    @Resource
    private FunctionMapperEx functionMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private SystemConfigService systemConfigService;
    @Resource
    private LogService logService;

    public Function getFunction(long id)throws Exception {
        Function result=null;
        try{
            result=functionsMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Function> getFunctionListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Function> list = new ArrayList<>();
        try{
            FunctionExample example = new FunctionExample();
            example.createCriteria().andIdIn(idList);
            list = functionsMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Function> getFunction()throws Exception {
        FunctionExample example = new FunctionExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Function> list=null;
        try{
            list=functionsMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<FunctionEx> select(String name, String type, int offset, int rows)throws Exception {
        List<FunctionEx> list=null;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                list = functionMapperEx.selectByConditionFunction(name, type, offset, rows);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countFunction(String name, String type)throws Exception {
        Long result=null;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = functionMapperEx.countsByFunction(name, type);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertFunction(JSONObject obj, HttpServletRequest request)throws Exception {
        Function functions = JSONObject.parseObject(obj.toJSONString(), Function.class);
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                functions.setState(false);
                functions.setType("电脑版");
                result = functionsMapper.insertSelective(functions);
                logService.insertLog("功能",
                        new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(functions.getName()).toString(), request);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateFunction(JSONObject obj, HttpServletRequest request) throws Exception{
        Function functions = JSONObject.parseObject(obj.toJSONString(), Function.class);
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = functionsMapper.updateByPrimaryKeySelective(functions);
                logService.insertLog("功能",
                        new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(functions.getName()).toString(), request);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteFunction(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteFunctionByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteFunction(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteFunctionByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteFunctionByIds(String ids)throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Function> list = getFunctionListByIds(ids);
        for(Function functions: list){
            sb.append("[").append(functions.getName()).append("]");
        }
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = functionMapperEx.batchDeleteFunctionByIds(new Date(), userInfo == null ? null : userInfo.getId(), idArray);
                logService.insertLog("功能", sb.toString(),
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        FunctionExample example = new FunctionExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Function> list=null;
        try{
            list = functionsMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public int checkIsNumberExist(Long id, String number)throws Exception {
        FunctionExample example = new FunctionExample();
        example.createCriteria().andIdNotEqualTo(id).andNumberEqualTo(number).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Function> list=null;
        try{
            list = functionsMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public List<Function> getRoleFunction(String pNumber)throws Exception {
        FunctionExample example = new FunctionExample();
        example.createCriteria().andEnabledEqualTo(true).andParentNumberEqualTo(pNumber)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<Function> list=null;
        try{
            list = functionsMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Function> findRoleFunction(String pnumber)throws Exception{
        List<Function> list=null;
        try{
            Boolean multiLevelApprovalFlag = systemConfigService.getMultiLevelApprovalFlag();
            FunctionExample example = new FunctionExample();
            FunctionExample.Criteria criteria = example.createCriteria();
            criteria.andEnabledEqualTo(true).andParentNumberEqualTo(pnumber)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
            if("0".equals(pnumber)) {
                if(!multiLevelApprovalFlag) {
                    criteria.andUrlNotEqualTo("/workflow");
                }
            }
            example.setOrderByClause("Sort");
            list =functionsMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Function> findByIds(String functionsIds)throws Exception{
        List<Long> idList = StringUtil.strToLongList(functionsIds);
        FunctionExample example = new FunctionExample();
        example.createCriteria().andEnabledEqualTo(true).andIdIn(idList).andPushBtnIsNotNull().andPushBtnNotEqualTo("")
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort asc");
        List<Function> list=null;
        try{
            list =functionsMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }
}
