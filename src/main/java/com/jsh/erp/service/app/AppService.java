package com.jsh.erp.service.app;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.App;
import com.jsh.erp.datasource.entities.AppExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.datasource.mappers.AppMapper;
import com.jsh.erp.datasource.mappers.AppMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.service.userBusiness.UserBusinessService;
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
public class AppService {
    private Logger logger = LoggerFactory.getLogger(AppService.class);

    @Resource
    private AppMapper appMapper;
    @Resource
    private AppMapperEx appMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    @Resource
    private UserBusinessService userBusinessService;

    public List<App> findDock()throws Exception{
        AppExample example = new AppExample();
        example.createCriteria().andZlEqualTo("dock").andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<App> list=null;
        try{
            list=appMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }
    /**
     * create by: cjl
     * description:
     *  桌面功能菜单初始化列表
     * create time: 2019/1/11 16:59
     * @Param: null
     * @return
     */
    public List<App> findDesk()throws Exception{
        AppExample example = new AppExample();
        example.createCriteria().andZlEqualTo("desk").andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<App> list=null;
        try{
            list=appMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public App getApp(long id)throws Exception {
        App result=null;
        try{
            result=appMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    public List<App> getApp()throws Exception {
        AppExample example = new AppExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<App> list=null;
        try{
            list=appMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public List<App> select(String name, String type, int offset, int rows)throws Exception {
        List<App> list=null;
        try{
            list=appMapperEx.selectByConditionApp(name, type, offset, rows);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public Long countApp(String name, String type)throws Exception {
        Long result=null;
        try{
            result=appMapperEx.countsByApp(name, type);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertApp(String beanJson, HttpServletRequest request)throws Exception {
        App app = JSONObject.parseObject(beanJson, App.class);
        int result=0;
        try{
            result=appMapper.insertSelective(app);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateApp(String beanJson, Long id) throws Exception{
        App app = JSONObject.parseObject(beanJson, App.class);
        app.setId(id);
        int result=0;
        try{
            result=appMapper.updateByPrimaryKeySelective(app);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteApp(Long id)throws Exception {
        int result=0;
        try{
            result=appMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteApp(String ids)throws Exception {
        List<Long> idList = StringUtil.strToLongList(ids);
        AppExample example = new AppExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=appMapper.deleteByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    public List<App> findRoleAPP()throws Exception{
        AppExample example = new AppExample();
        example.createCriteria().andEnabledEqualTo(true).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<App> list=null;
        try{
            list=appMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }

    public List<App> findAppInIds(String ids, String type)throws Exception{
        List<Long> idList = StringUtil.strToLongList(ids);
        AppExample example = new AppExample();
        example.createCriteria().andZlEqualTo(type).andEnabledEqualTo(true).andIdIn(idList)
                .andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Sort");
        List<App> list=null;
        try{
            list=appMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteAppByIds(String ids) throws Exception{
        logService.insertLog(BusinessConstants.LOG_INTERFACE_NAME_APP,
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int  result=0;
        try{
            result=appMapperEx.batchDeleteAppByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                    ExceptionConstants.DATA_WRITE_FAIL_MSG);
        }
        return result;
    }

    public List<App> findAppByUserId(String userId)throws Exception {
        List<UserBusiness> roleList = userBusinessService.findRoleByUserId(userId);
        String roles = null;
        if(roleList!=null && roleList.size()>0 && roleList.get(0)!=null){
            roles = roleList.get(0).getValue();
        }
        if(roles!=null) {
            roles = roles.replaceAll("\\]\\[",",").replaceAll("\\]","").replaceAll("\\[",""); //转为逗号隔开的
        }
        List<UserBusiness> appList = userBusinessService.findAppByRoles(roles);
        String apps = null;
        if(appList!=null && appList.size()>0 && appList.get(0)!=null){
            apps = appList.get(0).getValue();
        }
        if(apps!=null) {
            apps = apps.replaceAll("\\]\\[",",").replaceAll("\\]","").replaceAll("\\[",""); //转为逗号隔开的
        }

        List<App> deskList = findAppInIds(apps,"desk");

        return deskList;
    }

    /**
     * 通过number列表查询app list
     * @param numberList
     * @return
     */
    public List<App> findAppByNumber(List<String> numberList) throws Exception{

        AppExample example = new AppExample();
        example.createCriteria().andEnabledEqualTo(true).andNumberIn(numberList);
        List<App> list=null;
        try{
            list=appMapper.selectByExample(example);
        }catch(Exception e){
            logger.error("异常码[{}],异常提示[{}],异常[{}]",
                    ExceptionConstants.DATA_READ_FAIL_CODE,ExceptionConstants.DATA_READ_FAIL_MSG,e);
            throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                    ExceptionConstants.DATA_READ_FAIL_MSG);
        }
        return list;
    }
}
