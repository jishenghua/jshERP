package com.jsh.erp.service.userBusiness;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.App;
import com.jsh.erp.datasource.entities.Functions;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.datasource.entities.UserBusinessExample;
import com.jsh.erp.datasource.mappers.UserBusinessMapper;
import com.jsh.erp.service.CommonQueryManager;
import com.jsh.erp.service.app.AppService;
import com.jsh.erp.service.functions.FunctionsService;
import com.jsh.erp.datasource.mappers.UserBusinessMapperEx;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public UserBusiness getUserBusiness(long id) {
        return userBusinessMapper.selectByPrimaryKey(id);
    }

    public List<UserBusiness> getUserBusiness() {
        UserBusinessExample example = new UserBusinessExample();
        return userBusinessMapper.selectByExample(example);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertUserBusiness(String beanJson, HttpServletRequest request) {
        UserBusiness userBusiness = JSONObject.parseObject(beanJson, UserBusiness.class);
        int inserts = userBusinessMapper.insertSelective(userBusiness);
        // 更新应用权限
        if (("RoleFunctions").equals(userBusiness.getType()) && inserts > 0) {
            inserts = insertOrUpdateAppValue(BusinessConstants.TYPE_NAME_ROLE_APP, userBusiness.getKeyid(), userBusiness.getValue());
        }
        return inserts;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUserBusiness(String beanJson, Long id) {
        UserBusiness userBusiness = JSONObject.parseObject(beanJson, UserBusiness.class);
        userBusiness.setId(id);
        int updates = userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
        // 更新应用权限
        if (("RoleFunctions").equals(userBusiness.getType()) && updates > 0) {
            updates = insertOrUpdateAppValue(BusinessConstants.TYPE_NAME_ROLE_APP, userBusiness.getKeyid(), userBusiness.getValue());
        }
        return updates;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteUserBusiness(Long id) {
        return userBusinessMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUserBusiness(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andIdIn(idList);
        return userBusinessMapper.deleteByExample(example);
    }

    public int checkIsNameExist(Long id, String name) {
        return 1;
    }

    public List<UserBusiness> getBasicData(String keyId, String type){
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andKeyidEqualTo(keyId).andTypeEqualTo(type);
        List<UserBusiness> list = userBusinessMapper.selectByExample(example);
        return list;
    }

    public Long checkIsValueExist(String type, String keyId) {
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andTypeEqualTo(type).andKeyidEqualTo(keyId);
        List<UserBusiness> list = userBusinessMapper.selectByExample(example);
        Long id = null;
        if(list.size() > 0) {
            id = list.get(0).getId();
        }
        return id;
    }

    public Boolean checkIsUserBusinessExist(String TypeVale, String KeyIdValue, String UBValue) {
        UserBusinessExample example = new UserBusinessExample();
        String newVaule = "%" + UBValue + "%";
        if(TypeVale !=null && KeyIdValue !=null) {
            example.createCriteria().andTypeEqualTo(TypeVale).andKeyidEqualTo(KeyIdValue).andValueLike(newVaule);
        } else {
            example.createCriteria().andValueLike(newVaule);
        }
        List<UserBusiness> list = userBusinessMapper.selectByExample(example);
        if(list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateBtnStr(Long userBusinessId, String btnStr) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER_BUSINESS,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(userBusinessId).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setBtnstr(btnStr);
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andIdEqualTo(userBusinessId);
        return userBusinessMapper.updateByExampleSelective(userBusiness, example);
    }

    public List<UserBusiness> findRoleByUserId(String userId){
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andKeyidEqualTo(userId).andTypeEqualTo("UserRole");
        List<UserBusiness> list = userBusinessMapper.selectByExample(example);
        return list;
    }

    public List<UserBusiness> findAppByRoles(String roles){
        List<String> rolesList = StringUtil.strToStringList(roles);
        UserBusinessExample example = new UserBusinessExample();
        example.createCriteria().andKeyidIn(rolesList).andTypeEqualTo("RoleAPP");
        List<UserBusiness> list = userBusinessMapper.selectByExample(example);
        return list;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteUserBusinessByIds(String ids) {
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_USER_BUSINESS,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        return userBusinessMapperEx.batchDeleteUserBusinessByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
    }

    /**
     * 通过功能（RoleFunctions）权限更新应用（RoleApp）权限
     * @param type
     * @param keyId
     * @param functionIds
     * @return
     */
    public int insertOrUpdateAppValue(String type, String keyId, String functionIds) {

        int updates = 0;

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
                if(userBusinessList.size() > 0) {
                    UserBusiness userBusiness = userBusinessList.get(0);
                    userBusiness.setValue(appIdSb.toString());
                    updates = userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
                } else {
                    UserBusiness userBusiness = new UserBusiness();
                    userBusiness.setType(type);
                    userBusiness.setKeyid(keyId);
                    userBusiness.setValue(appIdSb.toString());
                    updates = userBusinessMapper.insertSelective(userBusiness);
                }
            }
        }
        return updates;
    }
}
