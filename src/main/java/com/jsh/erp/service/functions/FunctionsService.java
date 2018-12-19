package com.jsh.erp.service.functions;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Functions;
import com.jsh.erp.datasource.entities.FunctionsExample;
import com.jsh.erp.datasource.mappers.FunctionsMapper;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class FunctionsService {
    private Logger logger = LoggerFactory.getLogger(FunctionsService.class);

    @Resource
    private FunctionsMapper functionsMapper;

    public Functions getFunctions(long id) {
        return functionsMapper.selectByPrimaryKey(id);
    }

    public List<Functions> getFunctions() {
        FunctionsExample example = new FunctionsExample();
        return functionsMapper.selectByExample(example);
    }

    public List<Functions> select(String name, String type, int offset, int rows) {
        return functionsMapper.selectByConditionFunctions(name, type, offset, rows);
    }

    public int countFunctions(String name, String type) {
        return functionsMapper.countsByFunctions(name, type);
    }

    public int insertFunctions(String beanJson, HttpServletRequest request) {
        Functions depot = JSONObject.parseObject(beanJson, Functions.class);
        return functionsMapper.insertSelective(depot);
    }

    public int updateFunctions(String beanJson, Long id) {
        Functions depot = JSONObject.parseObject(beanJson, Functions.class);
        depot.setId(id);
        return functionsMapper.updateByPrimaryKeySelective(depot);
    }

    public int deleteFunctions(Long id) {
        return functionsMapper.deleteByPrimaryKey(id);
    }

    public int batchDeleteFunctions(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andIdIn(idList);
        return functionsMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name);
        List<Functions> list = functionsMapper.selectByExample(example);
        return list.size();
    }

    public List<Functions> getRoleFunctions(String pNumber) {
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andEnabledEqualTo(true).andPnumberEqualTo(pNumber);
        example.setOrderByClause("Sort");
        List<Functions> list = functionsMapper.selectByExample(example);
        return list;
    }

    public List<Functions> findRoleFunctions(String pnumber){
        FunctionsExample example = new FunctionsExample();
        example.createCriteria().andEnabledEqualTo(true).andPnumberEqualTo(pnumber);
        example.setOrderByClause("Sort");
        List<Functions> list = functionsMapper.selectByExample(example);
        return list;
    }
}
