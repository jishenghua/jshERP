package com.jsh.erp.service.app;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.App;
import com.jsh.erp.datasource.entities.AppExample;
import com.jsh.erp.datasource.mappers.AppMapper;
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
        return appMapper.selectByConditionApp(name, type, offset, rows);
    }

    public int countApp(String name, String type) {
        return appMapper.countsByApp(name, type);
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
}
