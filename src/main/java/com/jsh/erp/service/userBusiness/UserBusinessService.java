package com.jsh.erp.service.userBusiness;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.UserBusinessMapper;
import com.jsh.erp.datasource.mappers.UserBusinessMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.CommonQueryManager;
import com.jsh.erp.service.app.AppService;
import com.jsh.erp.service.functions.FunctionsService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    @Resource
    private FunctionsService functionsService;

    @Resource
    private AppService appService;

    @Resource
    private CommonQueryManager configResourceManager;

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
    public int insertUserBusiness(String beanJson, HttpServletRequest request) throws Exception {
        UserBusiness userBusiness = JSONObject.parseObject(beanJson, UserBusiness.class);
        int result=0;
        try{
            result=userBusinessMapper.insertSelective(userBusiness);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        // 更新应用权限
        if (BusinessConstants.TYPE_NAME_ROLE_FUNCTIONS.equals(userBusiness.getType()) && result > 0) {
            result = insertOrUpdateAppValue(BusinessConstants.TYPE_NAME_ROLE_APP, userBusiness.getKeyid(), userBusiness.getValue());
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUserBusiness(String beanJson, Long id) throws Exception {
        UserBusiness userBusiness = JSONObject.parseObject(beanJson, UserBusiness.class);
        userBusiness.setId(id);
        int result=0;
        try{
            result=userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        // 更新应用权限
        if (BusinessConstants.TYPE_NAME_ROLE_FUNCTIONS.equals(userBusiness.getType()) && result > 0) {
            result = insertOrUpdateAppValue(BusinessConstants.TYPE_NAME_ROLE_APP, userBusiness.getKeyid(), userBusiness.getValue());
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteUserBusiness(Long id)throws Exception {
        int result=0;
        try{
            result=userBusinessMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUserBusiness(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=userBusinessMapper.deleteByExample(example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        return 1;
    }

    public List<UserBusiness> getBasicData(String keyId, String type)throws Exception{
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andKeyidEqualTo(keyId).andTypeEqualTo(type)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<UserBusiness> list=null;
        try{
            list= userBusinessMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long checkIsValueExist(String type, String keyId)throws Exception {
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andTypeEqualTo(type).andKeyidEqualTo(keyId)
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

    public Boolean checkIsUserBusinessExist(String TypeVale, String KeyIdValue, String UBValue)throws Exception {
        UserBusinessExample example = new UserBusinessExample();
        String newVaule = "%" + UBValue + "%";
        if(TypeVale !=null && KeyIdValue !=null) {
            example.createCriteria().andTypeEqualTo(TypeVale).andKeyidEqualTo(KeyIdValue).andValueLike(newVaule)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        } else {
            example.createCriteria().andValueLike(newVaule)
                    .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        }
        List<UserBusiness> list=null;
        try{
            list=  userBusinessMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(list!=null&&list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateBtnStr(Long userBusinessId, String btnStr) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER_BUSINESS,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(userBusinessId).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setBtnstr(btnStr);
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andIdEqualTo(userBusinessId);
        int result=0;
        try{
            result=  userBusinessMapper.updateByExampleSelective(userBusiness, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<UserBusiness> findRoleByUserId(String userId)throws Exception{
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andKeyidEqualTo(userId).andTypeEqualTo("UserRole")
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<UserBusiness> list=null;
        try{
            list=  userBusinessMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<UserBusiness> findAppByRoles(String roles)throws Exception{
        List<String> rolesList = StringUtil.strToStringList(roles);
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andKeyidIn(rolesList).andTypeEqualTo("RoleAPP")
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<UserBusiness> list=null;
        try{
            list=  userBusinessMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUserBusinessByIds(String ids) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER_BUSINESS,
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

    /**
     * 通过功能（RoleFunctions）权限更新应用（RoleApp）权限
     * @param type
     * @param keyId
     * @param functionIds
     * @return
     */
    public int insertOrUpdateAppValue(String type, String keyId, String functionIds) throws Exception{
        int result=0;
        functionIds = functionIds.replaceAll("\\]\\[", ",").
                replaceAll("\\[","").replaceAll("\\]","");
        List<Functions> functionsList = functionsService.findByIds(functionIds);
        if (!CollectionUtils.isEmpty(functionsList)) {
            Set<String> appNumbers = new HashSet<>();
            String appNumber;
            for (Functions functions : functionsList) {
                appNumber = functions.getNumber().substring(0, 2);
                appNumbers.add(appNumber);
            }
            List<String> appNumberList = new ArrayList<>(appNumbers);
            List<App> appList = appService.findAppByNumber(appNumberList);
            StringBuilder appIdSb = new StringBuilder();
            if (!CollectionUtils.isEmpty(appList)) {
                for (App app : appList) {
                    appIdSb.append("[" + app.getId() + "]");
                }
                List<UserBusiness> userBusinessList = getBasicData(keyId, type);
                try{
                    if(userBusinessList.size() > 0) {
                        UserBusiness userBusiness = userBusinessList.get(0);
                        userBusiness.setValue(appIdSb.toString());
                        result = userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
                    } else {
                        UserBusiness userBusiness = new UserBusiness();
                        userBusiness.setType(type);
                        userBusiness.setKeyid(keyId);
                        userBusiness.setValue(appIdSb.toString());
                        result = userBusinessMapper.insertSelective(userBusiness);
                    }
                }catch(Exception e){
                    JshException.writeFail(logger, e);
                }
            }
        }
        return result;
    }
}
