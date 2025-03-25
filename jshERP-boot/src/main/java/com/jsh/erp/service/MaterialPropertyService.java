package com.jsh.erp.service;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.MaterialProperty;
import com.jsh.erp.datasource.entities.MaterialPropertyExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.MaterialPropertyMapper;
import com.jsh.erp.datasource.mappers.MaterialPropertyMapperEx;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MaterialPropertyService {
    private Logger logger = LoggerFactory.getLogger(MaterialPropertyService.class);

    @Resource
    private MaterialPropertyMapper materialPropertyMapper;

    @Resource
    private MaterialPropertyMapperEx materialPropertyMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    public MaterialProperty getMaterialProperty(long id)throws Exception {
        MaterialProperty result=null;
        try{
            result=materialPropertyMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<MaterialProperty> getMaterialProperty()throws Exception {
        MaterialPropertyExample example = new MaterialPropertyExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<MaterialProperty>  list=null;
        try{
            list=materialPropertyMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<MaterialProperty> select(String name)throws Exception {
        List<MaterialProperty> list = new ArrayList<>();
        try{
            MaterialProperty mp1 = new MaterialProperty();
            MaterialProperty mp2 = new MaterialProperty();
            MaterialProperty mp3 = new MaterialProperty();
            mp1.setId(1L);
            mp1.setNativeName("扩展1");
            mp1.setAnotherName("扩展1");
            list.add(mp1);
            mp2.setId(2L);
            mp2.setNativeName("扩展2");
            mp2.setAnotherName("扩展2");
            list.add(mp2);
            mp3.setId(3L);
            mp3.setNativeName("扩展3");
            mp3.setAnotherName("扩展3");
            list.add(mp3);
            PageUtils.startPage();
            List<MaterialProperty> mpList = materialPropertyMapperEx.selectByConditionMaterialProperty(name);
            Map<String, String> mpMap = new HashMap<>();
            for(MaterialProperty mp: mpList) {
                mpMap.put(mp.getNativeName(), mp.getAnotherName());
            }
            //给list里面的别名和排序做更新
            for(MaterialProperty item: list) {
                if(mpMap.get(item.getNativeName())!=null) {
                    item.setAnotherName(mpMap.get(item.getNativeName()));
                }
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertMaterialProperty(JSONObject obj, HttpServletRequest request)throws Exception {
        MaterialProperty materialProperty = JSONObject.parseObject(obj.toJSONString(), MaterialProperty.class);
        int  result=0;
        try{
            result = materialPropertyMapper.insertSelective(materialProperty);
            logService.insertLog("商品属性",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(materialProperty.getNativeName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateMaterialProperty(JSONObject obj, HttpServletRequest request)throws Exception {
        MaterialProperty materialProperty = JSONObject.parseObject(obj.toJSONString(), MaterialProperty.class);
        int  result=0;
        try{
            result = materialPropertyMapper.updateByPrimaryKeySelective(materialProperty);
            logService.insertLog("商品属性",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(materialProperty.getNativeName()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteMaterialProperty(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialPropertyByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialProperty(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteMaterialPropertyByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteMaterialPropertyByIds(String ids) throws Exception{
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int  result=0;
        try{
            result = materialPropertyMapperEx.batchDeleteMaterialPropertyByIds(new Date(), userInfo == null ? null : userInfo.getId(), idArray);
            logService.insertLog("商品属性",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        return 0;
    }

    public boolean checkIsNativeNameExist(String nativeName) {
        int count = materialPropertyMapperEx.getCountByNativeName(nativeName);
        return count>0;
    }

    public int updateMaterialPropertyByNativeName(String nativeName, String anotherName) {
        materialPropertyMapperEx.updateMaterialPropertyByNativeName(nativeName, anotherName);
        return 1;
    }
}
