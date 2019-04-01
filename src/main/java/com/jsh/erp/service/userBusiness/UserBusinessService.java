package com.jsh.erp.service.userBusiness;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.datasource.entities.UserBusinessExample;
import com.jsh.erp.datasource.mappers.UserBusinessMapper;
import com.jsh.erp.datasource.mappers.UserBusinessMapperEx;
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
        return userBusinessMapper.insertSelective(userBusiness);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateUserBusiness(String beanJson, Long id) {
        UserBusiness userBusiness = JSONObject.parseObject(beanJson, UserBusiness.class);
        userBusiness.setId(id);
        return userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
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
}
