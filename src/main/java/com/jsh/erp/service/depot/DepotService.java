package com.jsh.erp.service.depot;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Depot;
import com.jsh.erp.datasource.entities.DepotEx;
import com.jsh.erp.datasource.entities.DepotExample;
import com.jsh.erp.datasource.mappers.DepotMapper;
import com.jsh.erp.datasource.mappers.DepotMapperEx;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class DepotService {
    private Logger logger = LoggerFactory.getLogger(DepotService.class);

    @Resource
    private DepotMapper depotMapper;

    @Resource
    private DepotMapperEx depotMapperEx;

    public Depot getDepot(long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    public List<Depot> getDepot() {
        DepotExample example = new DepotExample();
        return depotMapper.selectByExample(example);
    }

    public List<Depot> getAllList() {
        DepotExample example = new DepotExample();
        example.setOrderByClause("sort");
        return depotMapper.selectByExample(example);
    }

    public List<Depot> select(String name, Integer type, String remark, int offset, int rows) {
        return depotMapperEx.selectByConditionDepot(name, type, remark, offset, rows);
    }

    public Long countDepot(String name, Integer type, String remark) {
        return depotMapperEx.countsByDepot(name, type, remark);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertDepot(String beanJson, HttpServletRequest request) {
        Depot depot = JSONObject.parseObject(beanJson, Depot.class);
        return depotMapper.insertSelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateDepot(String beanJson, Long id) {
        Depot depot = JSONObject.parseObject(beanJson, Depot.class);
        depot.setId(id);
        return depotMapper.updateByPrimaryKeySelective(depot);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteDepot(Long id) {
        return depotMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteDepot(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        DepotExample example = new DepotExample();
        example.createCriteria().andIdIn(idList);
        return depotMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        DepotExample example = new DepotExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name);
        List<Depot> list = depotMapper.selectByExample(example);
        return list.size();
    }

    public List<Depot> findUserDepot(){
        DepotExample example = new DepotExample();
        example.createCriteria().andTypeEqualTo(0);
        example.setOrderByClause("Sort");
        List<Depot> list = depotMapper.selectByExample(example);
        return list;
    }

    public List<Depot> findGiftByType(Integer type){
        DepotExample example = new DepotExample();
        example.createCriteria().andTypeEqualTo(type);
        example.setOrderByClause("Sort");
        List<Depot> list = depotMapper.selectByExample(example);
        return list;
    }

    public List<DepotEx> getDepotList(Map<String, Object> parameterMap) {
        return depotMapperEx.getDepotList(parameterMap);
    }

}
