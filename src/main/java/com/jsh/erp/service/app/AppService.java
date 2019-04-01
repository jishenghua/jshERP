package com.jsh.erp.service.app;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.App;
import com.jsh.erp.datasource.entities.AppExample;
import com.jsh.erp.datasource.entities.UserBusiness;
import com.jsh.erp.datasource.mappers.AppMapper;
import com.jsh.erp.datasource.mappers.AppMapperEx;
import com.jsh.erp.service.userBusiness.UserBusinessService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AppService {
    private Logger logger = LoggerFactory.getLogger(AppService.class);

    @Resource
    private AppMapper appMapper;
    @Resource
    private AppMapperEx appMapperEx;

    @Resource
    private UserBusinessService userBusinessService;

    public List<App> findDock(){
        AppExample example = new AppExample();
        example.createCriteria().andZlEqualTo("dock").andEnabledEqualTo(true);
        example.setOrderByClause("Sort");
        List<App> list = appMapper.selectByExample(example);
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
    public List<App> findDesk(){
        AppExample example = new AppExample();
        example.createCriteria().andZlEqualTo("desk").andEnabledEqualTo(true);
        example.setOrderByClause("Sort");
        List<App> list = appMapper.selectByExample(example);
        return list;
    }

    public App getApp(long id) {
        return appMapper.selectByPrimaryKey(id);
    }

    public List<App> getApp() {
        AppExample example = new AppExample();
        return appMapper.selectByExample(example);
    }

    public List<App> select(String name, String type, int offset, int rows) {
        return appMapperEx.selectByConditionApp(name, type, offset, rows);
    }

    public Long countApp(String name, String type) {
        return appMapperEx.countsByApp(name, type);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertApp(String beanJson, HttpServletRequest request) {
        App app = JSONObject.parseObject(beanJson, App.class);
        return appMapper.insertSelective(app);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateApp(String beanJson, Long id) {
        App app = JSONObject.parseObject(beanJson, App.class);
        app.setId(id);
        return appMapper.updateByPrimaryKeySelective(app);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteApp(Long id) {
        return appMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteApp(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        AppExample example = new AppExample();
        example.createCriteria().andIdIn(idList);
        return appMapper.deleteByExample(example);
    }

    public List<App> findRoleAPP(){
        AppExample example = new AppExample();
        example.createCriteria().andEnabledEqualTo(true);
        example.setOrderByClause("Sort");
        List<App> list = appMapper.selectByExample(example);
        return list;
    }

    public List<App> findAppInIds(String ids, String type){
        List<Long> idList = StringUtil.strToLongList(ids);
        AppExample example = new AppExample();
        example.createCriteria().andZlEqualTo(type).andEnabledEqualTo(true).andIdIn(idList);
        example.setOrderByClause("Sort");
        List<App> list = appMapper.selectByExample(example);
        return list;
    }

    public List<App> findAppByUserId(String userId) {
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
    public List<App> findAppByNumber(List<String> numberList) {

        AppExample example = new AppExample();
        example.createCriteria().andEnabledEqualTo(true).andNumberIn(numberList);
        return appMapper.selectByExample(example);
    }
}
