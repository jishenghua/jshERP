package com.jsh.erp.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.datasource.entities.UserBusinessExample;
import com.jsh.erp.datasource.mappers.UserBusinessMapper;
import com.jsh.erp.datasource.mappers.UserBusinessMapperEx;
import com.jsh.erp.exception.JshException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserBusinessService {
    private Logger logger = LoggerFactory.getLogger(UserBusinessService.class);

    @Resource
    private UserBusinessMapper userBusinessMapper;
    @Resource
    private UserBusinessMapperEx userBusinessMapperEx;
    @Resource
    private LogService logService;
    @Resource
    private UserService userService;

    public UserBusiness getUserBusiness(long id)throws Exception {
        UserBusiness result=null;
        try{
            result=userBusinessMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<UserBusiness> getUserBusiness()throws Exception {
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<UserBusiness> list=null;
        try{
            list=userBusinessMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertUserBusiness(JSONObject obj, HttpServletRequest request) throws Exception {
        UserBusiness userBusiness = JSONObject.parseObject(obj.toJSONString(), UserBusiness.class);
        int result=0;
        try{
            String value = userBusiness.getValue();
            String newValue = value.replaceAll(",","\\]\\[");
            newValue = newValue.replaceAll("\\[0\\]","").replaceAll("\\[\\]","");
            userBusiness.setValue(newValue);
            result=userBusinessMapper.insertSelective(userBusiness);
            logService.insertLog("关联关系", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUserBusiness(JSONObject obj, HttpServletRequest request) throws Exception {
        UserBusiness userBusiness = JSONObject.parseObject(obj.toJSONString(), UserBusiness.class);
        int result=0;
        try{
            String value = userBusiness.getValue();
            String newValue = value.replaceAll(",","\\]\\[");
            newValue = newValue.replaceAll("\\[0\\]","").replaceAll("\\[\\]","");
            userBusiness.setValue(newValue);
            result=userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
            logService.insertLog("关联关系", BusinessConstants.LOG_OPERATION_TYPE_EDIT, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteUserBusiness(Long id, HttpServletRequest request)throws Exception {
        return batchDeleteUserBusinessByIds(id.toString());
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUserBusiness(String ids, HttpServletRequest request)throws Exception {
        return batchDeleteUserBusinessByIds(ids);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUserBusinessByIds(String ids) throws Exception{
        logService.insertLog("关联关系",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result=0;
        try{
            result=  userBusinessMapperEx.batchDeleteUserBusinessByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        return 1;
    }

    public List<UserBusiness> getBasicData(String keyId, String type)throws Exception{
        List<UserBusiness> list=null;
        try{
            list= userBusinessMapperEx.getBasicDataByKeyIdAndType(keyId, type);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public String getUBValueByTypeAndKeyId(String type, String keyId) throws Exception {
        String ubValue = "";
        List<UserBusiness> ubList = getBasicData(keyId, type);
        if(ubList!=null && ubList.size()>0) {
            ubValue = ubList.get(0).getValue();
        }
        return ubValue;
    }

    public Long checkIsValueExist(String type, String keyId)throws Exception {
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andTypeEqualTo(type).andKeyIdEqualTo(keyId)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<UserBusiness> list=null;
        try{
            list= userBusinessMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        Long id = null;
        if(list!=null&&list.size() > 0) {
            id = list.get(0).getId();
        }
        return id;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateBtnStr(String keyId, String type, String btnStr) throws Exception{
        logService.insertLog("关联关系",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append("角色的按钮权限").toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setBtnStr(btnStr);
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andKeyIdEqualTo(keyId).andTypeEqualTo(type);
        int result=0;
        try{
            result=  userBusinessMapper.updateByExampleSelective(userBusiness, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<Long> getUBKeyIdByTypeAndOneValue(String type, String oneValue) {
        return userBusinessMapperEx.getUBKeyIdByTypeAndOneValue(type, oneValue);
    }

    public int updateOneValueByKeyIdAndType(String type, JSONArray keyIdArr, String oneValue) {
        int res = 0;
        try {
            Map<String, String> keyIdMap = new HashMap<>();
            List<UserBusiness> oldUbList = userBusinessMapperEx.getOldListByType(type);
            for(Object keyIdObj: keyIdArr) {
                String keyId = keyIdObj.toString();
                keyIdMap.put(keyId, keyId);
                List<UserBusiness> ubList = userBusinessMapperEx.getBasicDataByKeyIdAndType(keyId, type);
                if(ubList.size()>0) {
                    String valueStr = ubList.get(0).getValue();
                    Boolean flag = valueStr.contains("[" + oneValue + "]");
                    if(flag) {
                        //存在则忽略
                    } else {
                        //不存在则追加并更新
                        valueStr = valueStr + "[" + oneValue + "]";
                        UserBusiness userBusiness = new UserBusiness();
                        userBusiness.setId(ubList.get(0).getId());
                        userBusiness.setValue(valueStr);
                        userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
                    }
                } else {
                    //新增数据
                    UserBusiness userBusiness = new UserBusiness();
                    userBusiness.setType(type);
                    userBusiness.setKeyId(keyId);
                    userBusiness.setValue("[" + oneValue + "]");
                    userBusinessMapper.insertSelective(userBusiness);
                }
            }
            //检查被移除的keyId
            for(UserBusiness item: oldUbList) {
                String oldValue = item.getValue();
                String oldkeyId = item.getKeyId();
                if(keyIdMap.get(oldkeyId) == null) {
                    //处理被删除的keyId
                    String valueStr = "[" + oneValue + "]";
                    if(oldValue.equals(valueStr)) {
                        //说明value里面只有一条数据，需要进行逻辑删除
                        UserBusiness userBusiness = new UserBusiness();
                        userBusiness.setId(item.getId());
                        userBusiness.setDeleteFlag("1");
                        userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
                    } else {
                        //多条进行替换后再更新
                        String newValue = oldValue.replace(valueStr, "");
                        UserBusiness userBusiness = new UserBusiness();
                        userBusiness.setId(item.getId());
                        userBusiness.setValue(newValue);
                        userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
                    }
                }
            }
            res = 1;
        } catch (Exception e) {
            res = 0;
            logger.error(e.getMessage(), e);
        }
        return res;
    }
}
