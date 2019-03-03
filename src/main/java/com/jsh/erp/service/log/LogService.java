package com.jsh.erp.service.log;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Log;
import com.jsh.erp.datasource.entities.LogExample;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.LogMapper;
import com.jsh.erp.datasource.mappers.LogMapperEx;
import com.jsh.erp.datasource.vo.LogVo4List;
import com.jsh.erp.utils.ExceptionCodeConstants;
import com.jsh.erp.utils.JshException;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static com.jsh.erp.utils.Tools.getLocalIp;

@Service
public class LogService {
    private Logger logger = LoggerFactory.getLogger(LogService.class);
    @Resource
    private LogMapper logMapper;

    @Resource
    private LogMapperEx logMapperEx;

    public Log getLog(long id) {
        return logMapper.selectByPrimaryKey(id);
    }

    public List<Log> getLog() {
        LogExample example = new LogExample();
        return logMapper.selectByExample(example);
    }

    public List<LogVo4List> select(String operation, Integer usernameID, String clientIp, Integer status, String beginTime, String endTime,
                                   String contentdetails, int offset, int rows) {
        return logMapperEx.selectByConditionLog(operation, usernameID, clientIp, status, beginTime, endTime,
                            contentdetails, offset, rows);
    }

    public Long countLog(String operation, Integer usernameID, String clientIp, Integer status, String beginTime, String endTime,
                        String contentdetails) {
        return logMapperEx.countsByLog(operation, usernameID, clientIp, status, beginTime, endTime, contentdetails);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertLog(String beanJson, HttpServletRequest request) {
        Log log = JSONObject.parseObject(beanJson, Log.class);
        return logMapper.insertSelective(log);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateLog(String beanJson, Long id) {
        Log log = JSONObject.parseObject(beanJson, Log.class);
        log.setId(id);
        return logMapper.updateByPrimaryKeySelective(log);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteLog(Long id) {
        return logMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteLog(String ids) {
        List<Long> idList = StringUtil.strToLongList(ids);
        LogExample example = new LogExample();
        example.createCriteria().andIdIn(idList);
        return logMapper.deleteByExample(example);
    }

    /**
     * 获取用户id
     * @param request
     * @return
     */
    public Long getUserId(HttpServletRequest request) {
        Object userInfo = request.getSession().getAttribute("user");
        if(userInfo!=null) {
            User user = (User) userInfo;
            return user.getId();
        } else {
            return null;
        }
    }

    public String getModule(String apiName){
        String moduleName = null;
        switch (apiName) {
            case "user":
                moduleName = "用户"; break;
            case "role":
                moduleName = "角色"; break;
            case "app":
                moduleName = "应用"; break;
            case "depot":
                moduleName = "仓库"; break;
            case "functions":
                moduleName = "功能"; break;
            case "inOutItem":
                moduleName = "收支项目"; break;
            case "unit":
                moduleName = "计量单位"; break;
            case "person":
                moduleName = "经手人"; break;
            case "userBusiness":
                moduleName = "关联关系"; break;
            case "systemConfig":
                moduleName = "系统配置"; break;
            case "materialProperty":
                moduleName = "商品属性"; break;
            case "account":
                moduleName = "账户"; break;
            case "supplier":
                moduleName = "商家"; break;
            case "materialCategory":
                moduleName = "商品类型"; break;
            case "material":
                moduleName = "商品"; break;
            case "depotHead":
                moduleName = "单据"; break;
            case "depotItem":
                moduleName = "单据明细"; break;
            case "accountHead":
                moduleName = "财务"; break;
            case "accountItem":
                moduleName = "财务明细"; break;
        }
        return moduleName;
    }

    public void insertLog(String apiName, String type, HttpServletRequest request){
        Log log = new Log();
        log.setUserid(getUserId(request));
        log.setOperation(getModule(apiName));
        log.setClientip(getLocalIp(request));
        log.setCreatetime(new Date());
        Byte status = 0;
        log.setStatus(status);
        log.setContentdetails(type + getModule(apiName));
        log.setRemark(type + getModule(apiName));
        logMapper.insertSelective(log);
    }

}
