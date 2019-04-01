package com.jsh.erp.service.orgaUserRel;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.OrgaUserRelMapper;
import com.jsh.erp.datasource.mappers.OrgaUserRelMapperEx;
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
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertOrgaUserRel(String beanJson, HttpServletRequest request) {
        OrgaUserRel orgaUserRel = JSONObject.parseObject(beanJson, OrgaUserRel.class);
        return orgaUserRelMapper.insertSelective(orgaUserRel);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateOrgaUserRel(String beanJson, Long id) {
        OrgaUserRel orgaUserRel = JSONObject.parseObject(beanJson, OrgaUserRel.class);
        orgaUserRel.setId(id);
        return orgaUserRelMapper.updateByPrimaryKeySelective(orgaUserRel);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteOrgaUserRel(Long id) {
        return orgaUserRelMapper.deleteByPrimaryKey(id);
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteOrgaUserRel(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        OrgaUserRelExample example = new OrgaUserRelExample();
        example.createCriteria().andIdIn(idList);
        return orgaUserRelMapper.deleteByExample(example);
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
        int i=orgaUserRelMapperEx.addOrgaUserRel(orgaUserRel);
        if(i>0){
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
    public OrgaUserRel updateOrgaUserRel(OrgaUserRel orgaUserRel) {
        User userInfo=userService.getCurrentUser();
        //更新时间
        if(orgaUserRel.getUpdateTime()==null){
            orgaUserRel.setUpdateTime(new Date());
        }
        //更新人
        if(orgaUserRel.getUpdater()==null){
            orgaUserRel.setUpdater(userInfo==null?null:userInfo.getId());
        }
       int i= orgaUserRelMapperEx.updateOrgaUserRel(orgaUserRel);
        if(i>0){
            return orgaUserRel;
        }
        return null;
    }
}
