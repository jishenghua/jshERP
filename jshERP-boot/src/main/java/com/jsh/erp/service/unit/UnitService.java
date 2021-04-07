package com.jsh.erp.service.unit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.MaterialMapperEx;
import com.jsh.erp.datasource.mappers.UnitMapper;
import com.jsh.erp.datasource.mappers.UnitMapperEx;
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
public class UnitService {
    private Logger logger = LoggerFactory.getLogger(UnitService.class);

    @Resource
    private UnitMapper unitMapper;

    @Resource
    private UnitMapperEx unitMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    @Resource
    private MaterialMapperEx materialMapperEx;

    public Unit getUnit(long id)throws Exception {
        Unit result=null;
        try{
            result=unitMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Unit> getUnitListByIds(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        List<Unit> list = new ArrayList<>();
        try{
            UnitExample example = new UnitExample();
            example.createCriteria().andIdIn(idList);
            list = unitMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Unit> getUnit()throws Exception {
        UnitExample example = new UnitExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Unit> list=null;
        try{
            list=unitMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Unit> select(String name, int offset, int rows)throws Exception {
        List<Unit> list=null;
        try{
            list=unitMapperEx.selectByConditionUnit(name, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countUnit(String name)throws Exception {
        Long result=null;
        try{
            result=unitMapperEx.countsByUnit(name);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertUnit(JSONObject obj, HttpServletRequest request)throws Exception {
        Unit unit = JSONObject.parseObject(obj.toJSONString(), Unit.class);
        int result=0;
        try{
            unit.setName(unit.getBasicUnit() + "," + unit.getOtherUnit() + "(1:" + unit.getRatio() + ")");
            result=unitMapper.insertSelective(unit);
            logService.insertLog("计量单位",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(unit.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUnit(JSONObject obj, HttpServletRequest request)throws Exception {
        Unit unit = JSONObject.parseObject(obj.toJSONString(), Unit.class);
        int result=0;
        try{
            unit.setName(unit.getBasicUnit() + "," + unit.getOtherUnit() + "(1:" + unit.getRatio() + ")");
            result=unitMapper.updateByPrimaryKeySelective(unit);
            logService.insertLog("计量单位",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(unit.getName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteUnit(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteUnitByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUnit(String ids, HttpServletRequest request) throws Exception{
        return batchDeleteUnitByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUnitByIds(String ids)throws Exception {
        int result=0;
        String [] idArray=ids.split(",");
        //校验产品表	jsh_material
        List<Material> materialList=null;
        try{
            materialList=materialMapperEx.getMaterialListByUnitIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(materialList!=null&&materialList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,UnitIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        //记录日志
        StringBuffer sb = new StringBuffer();
        sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
        List<Unit> list = getUnitListByIds(ids);
        for(Unit unit: list){
            sb.append("[").append(unit.getName()).append("]");
        }
        logService.insertLog("计量单位", sb.toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        //校验通过执行删除操作
        try{
            result=unitMapperEx.batchDeleteUnitByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        UnitExample example = new UnitExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Unit> list=null;
        try{
            list=unitMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    /**
     * 根据名称获取类型
     * @param name
     */
    public Long getUnitIdByName(String name){
        Long unitId = 0L;
        UnitExample example = new UnitExample();
        example.createCriteria().andNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Unit> list = unitMapper.selectByExample(example);
        if(list!=null && list.size()>0) {
            unitId = list.get(0).getId();
        }
        return unitId;
    }
}
