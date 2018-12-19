package com.jsh.erp.service.unit;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Unit;
import com.jsh.erp.datasource.entities.UnitExample;
import com.jsh.erp.datasource.mappers.UnitMapper;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UnitService {
    private Logger logger = LoggerFactory.getLogger(UnitService.class);

    @Resource
    private UnitMapper unitMapper;

    public Unit getUnit(long id) {
        return unitMapper.selectByPrimaryKey(id);
    }

    public List<Unit> getUnit() {
        UnitExample example = new UnitExample();
        return unitMapper.selectByExample(example);
    }

    public List<Unit> select(String name, int offset, int rows) {
        return unitMapper.selectByConditionUnit(name, offset, rows);
    }

    public int countUnit(String name) {
        return unitMapper.countsByUnit(name);
    }

    public int insertUnit(String beanJson, HttpServletRequest request) {
        Unit unit = JSONObject.parseObject(beanJson, Unit.class);
        return unitMapper.insertSelective(unit);
    }

    public int updateUnit(String beanJson, Long id) {
        Unit unit = JSONObject.parseObject(beanJson, Unit.class);
        unit.setId(id);
        return unitMapper.updateByPrimaryKeySelective(unit);
    }

    public int deleteUnit(Long id) {
        return unitMapper.deleteByPrimaryKey(id);
    }

    public int batchDeleteUnit(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        UnitExample example = new UnitExample();
        example.createCriteria().andIdIn(idList);
        return unitMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        UnitExample example = new UnitExample();
        example.createCriteria().andIdNotEqualTo(id).andUnameEqualTo(name);
        List<Unit> list = unitMapper.selectByExample(example);
        return list.size();
    }
}
