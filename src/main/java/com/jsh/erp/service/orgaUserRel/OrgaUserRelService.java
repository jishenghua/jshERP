package com.jsh.erp.service.orgaUserRel;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.OrgaUserRel;
import com.jsh.erp.datasource.entities.OrgaUserRelExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.OrgaUserRelMapper;
import com.jsh.erp.datasource.mappers.OrgaUserRelMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.organization.OrganizationService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/3/11 18:11
 */
@Service
public class OrgaUserRelService {
    private Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    @Resource
    private OrgaUserRelMapper orgaUserRelMapper;
    @Resource
    private OrgaUserRelMapperEx orgaUserRelMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    public OrgaUserRel getOrgaUserRel(long id) throws Exception{
        return orgaUserRelMapper.selectByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertOrgaUserRel(String beanJson, HttpServletRequest request) throws Exception{
        OrgaUserRel orgaUserRel = JSONObject.parseObject(beanJson, OrgaUserRel.class);
        int result=0;
        try{
            result=orgaUserRelMapper.insertSelective(orgaUserRel);
            logService.insertLog("用户与机构关系", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateOrgaUserRel(String beanJson, Long id, HttpServletRequest request) throws Exception{
        OrgaUserRel orgaUserRel = JSONObject.parseObject(beanJson, OrgaUserRel.class);
        orgaUserRel.setId(id);
        int result=0;
        try{
            result=orgaUserRelMapper.updateByPrimaryKeySelective(orgaUserRel);
            logService.insertLog("用户与机构关系",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteOrgaUserRel(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            result=orgaUserRelMapper.deleteByPrimaryKey(id);
            logService.insertLog("用户与机构关系",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteOrgaUserRel(String ids, HttpServletRequest request)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        OrgaUserRelExample example = new OrgaUserRelExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=orgaUserRelMapper.deleteByExample(example);
            logService.insertLog("用户与机构关系", "批量删除,id集:" + ids, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    /**
     * create by: cjl
     * description:
     *  新增机构用户关联关系,反显id
     * create time: 2019/3/12 9:40
     * @Param: orgaUserRel
     * @return void
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public OrgaUserRel addOrgaUserRel(OrgaUserRel orgaUserRel) throws Exception{
        Date date = new Date();
        User userInfo=userService.getCurrentUser();
        //创建时间
        if(orgaUserRel.getCreateTime()==null){
            orgaUserRel.setCreateTime(date);
        }
        //创建人
        if(orgaUserRel.getCreator()==null){
            orgaUserRel.setCreator(userInfo==null?null:userInfo.getId());
        }
        //更新时间
        if(orgaUserRel.getUpdateTime()==null){
            orgaUserRel.setUpdateTime(date);
        }
        //更新人
        if(orgaUserRel.getUpdater()==null){
            orgaUserRel.setUpdater(userInfo==null?null:userInfo.getId());
        }
        orgaUserRel.setDeleteFlag(BusinessConstants.DELETE_FLAG_EXISTS);
        int result=0;
        try{
            result=orgaUserRelMapperEx.addOrgaUserRel(orgaUserRel);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        if(result>0){
            return orgaUserRel;
        }
        return null;
    }
    /**
     * create by: cjl
     * description:
     *  更新机构用户关联关系
     * create time: 2019/3/12 9:40
     * @Param: orgaUserRel
     * @return void
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public OrgaUserRel updateOrgaUserRel(OrgaUserRel orgaUserRel) throws Exception{
        User userInfo=userService.getCurrentUser();
        //更新时间
        if(orgaUserRel.getUpdateTime()==null){
            orgaUserRel.setUpdateTime(new Date());
        }
        //更新人
        if(orgaUserRel.getUpdater()==null){
            orgaUserRel.setUpdater(userInfo==null?null:userInfo.getId());
        }
        int result=0;
        try{
            result=orgaUserRelMapperEx.updateOrgaUserRel(orgaUserRel);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        if(result>0){
            return orgaUserRel;
        }
        return null;
    }
}
