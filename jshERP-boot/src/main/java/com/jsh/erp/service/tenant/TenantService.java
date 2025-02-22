package com.jsh.erp.service.tenant;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.Tenant;
import com.jsh.erp.datasource.entities.TenantEx;
import com.jsh.erp.datasource.entities.TenantExample;
import com.jsh.erp.datasource.entities.UserEx;
import com.jsh.erp.datasource.mappers.TenantMapper;
import com.jsh.erp.datasource.mappers.TenantMapperEx;
import com.jsh.erp.datasource.mappers.UserBusinessMapperEx;
import com.jsh.erp.datasource.mappers.UserMapperEx;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.PageUtils;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TenantService {
    private Logger logger = LoggerFactory.getLogger(TenantService.class);

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private TenantMapperEx tenantMapperEx;

    @Resource
    private UserMapperEx userMapperEx;

    @Resource
    private UserBusinessMapperEx userBusinessMapperEx;

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @Value("${manage.roleId}")
    private Integer manageRoleId;

    public Tenant getTenant(long id)throws Exception {
        Tenant result=null;
        try{
            result=tenantMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Tenant> getTenant()throws Exception {
        TenantExample example = new TenantExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Tenant> list=null;
        try{
            list=tenantMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<TenantEx> select(String loginName, String type, String enabled, String remark)throws Exception {
        List<TenantEx> list = null;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                PageUtils.startPage();
                list = tenantMapperEx.selectByConditionTenant(loginName, type, enabled, remark);
                if (null != list) {
                    for (TenantEx tenantEx : list) {
                        tenantEx.setCreateTimeStr(Tools.getCenternTime(tenantEx.getCreateTime()));
                        tenantEx.setExpireTimeStr(Tools.getCenternTime(tenantEx.getExpireTime()));
                    }
                }
            }
        } catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertTenant(JSONObject obj, HttpServletRequest request)throws Exception {
        UserEx ue = JSONObject.parseObject(obj.toJSONString(), UserEx.class);
        int result = 0;
        try{
            ue.setUsername(ue.getLoginName());
            userService.checkLoginName(ue); //检查登录名
            userService.registerUser(ue,manageRoleId,request);
            result = 1;
        } catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateTenant(JSONObject obj, HttpServletRequest request)throws Exception {
        Tenant tenant = JSONObject.parseObject(obj.toJSONString(), Tenant.class);
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                //如果租户下的用户限制数量为1，则将该租户之外的用户全部禁用
                if (1 == tenant.getUserNumLimit()) {
                    userMapperEx.disableUserByLimit(tenant.getTenantId());
                }
                result = tenantMapper.updateByPrimaryKeySelective(tenant);
                //更新租户对应的角色
                if(obj.get("roleId")!=null) {
                    String ubValue = "[" + obj.getString("roleId") + "]";
                    userBusinessMapperEx.updateValueByTypeAndKeyId("UserRole", tenant.getTenantId().toString(), ubValue);
                }
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteTenant(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = tenantMapper.deleteByPrimaryKey(id);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteTenant(String ids, HttpServletRequest request)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        TenantExample example = new TenantExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                result = tenantMapper.deleteByExample(example);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String name)throws Exception {
        TenantExample example = new TenantExample();
        example.createCriteria().andIdNotEqualTo(id).andLoginNameEqualTo(name).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Tenant> list=null;
        try{
            list= tenantMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public Tenant getTenantByTenantId(long tenantId) {
        Tenant tenant = new Tenant();
        TenantExample example = new TenantExample();
        example.createCriteria().andTenantIdEqualTo(tenantId).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Tenant> list = tenantMapper.selectByExample(example);
        if(list.size()>0) {
            tenant = list.get(0);
        }
        return tenant;
    }

    public int batchSetStatus(Boolean status, String ids)throws Exception {
        int result=0;
        try{
            if(BusinessConstants.DEFAULT_MANAGER.equals(userService.getCurrentUser().getLoginName())) {
                String statusStr = "";
                if (status) {
                    statusStr = "批量启用";
                } else {
                    statusStr = "批量禁用";
                }
                logService.insertLog("用户",
                        new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ids).append("-").append(statusStr).toString(),
                        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
                List<Long> idList = StringUtil.strToLongList(ids);
                Tenant tenant = new Tenant();
                tenant.setEnabled(status);
                TenantExample example = new TenantExample();
                example.createCriteria().andIdIn(idList);
                result = tenantMapper.updateByExampleSelective(tenant, example);
            }
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
}
