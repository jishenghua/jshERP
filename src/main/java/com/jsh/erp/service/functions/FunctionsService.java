package com.jsh.erp.service.functions;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Functions;
import com.jsh.erp.datasource.entities.FunctionsExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.FunctionsMapper;
import com.jsh.erp.datasource.mappers.FunctionsMapperEx;
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
public class FunctionsService {
    private Logger logger = LoggerFactory.getLogger(FunctionsService.class);

    @Resource
    private FunctionsMapper functionsMapper;

    @Resource
    private FunctionsMapperEx functionsMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    public Functions getFunctions(long id) {
        return functionsMapper.selectByPrimaryKey(id);
    }

    public List<Functions> getFunctions() {
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        return functionsMapper.selectByExample(example);
    }

    public List<Functions> select(String name, String type, int offset, int rows) {
        return functionsMapperEx.selectByConditionFunctions(name, type, offset, rows);
    }

    public Long countFunctions(String name, String type) {
        return functionsMapperEx.countsByFunctions(name, type);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertFunctions(String beanJson, HttpServletRequest request) {
        Functions depot = JSONObject.parseObject(beanJson, Functions.class);
        return functionsMapper.insertSelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateFunctions(String beanJson, Long id) {
        Functions depot = JSONObject.parseObject(beanJson, Functions.class);
        depot.setId(id);
        return functionsMapper.updateByPrimaryKeySelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteFunctions(Long id) {
        return functionsMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteFunctions(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andIdIn(idList);
        return functionsMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Functions> list = functionsMapper.selectByExample(example);
        return list.size();
    }

    public List<Functions> getRoleFunctions(String pNumber) {
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andEnabledEqualTo(true).andPnumberEqualTo(pNumber)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<Functions> list = functionsMapper.selectByExample(example);
        return list;
    }

    public List<Functions> findRoleFunctions(String pnumber){
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andEnabledEqualTo(true).andPnumberEqualTo(pnumber)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<Functions> list = functionsMapper.selectByExample(example);
        return list;
    }

    public List<Functions> findByIds(String functionsIds){
        List<Long> idList = StringUtil.strToLongList(functionsIds);
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andEnabledEqualTo(true).andIdIn(idList)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort asc");
        List<Functions> list = functionsMapper.selectByExample(example);
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteFunctionsByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_FUNCTIONS,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return functionsMapperEx.batchDeleteFunctionsByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }
}
