package com.jsh.erp.service.log;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Log;
import com.jsh.erp.datasource.entities.LogExample;
import com.jsh.erp.datasource.mappers.LogMapper;
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
import java.util.List;

@Service
public class LogService {
    private Logger logger = LoggerFactory.getLogger(LogService.class);
    @Resource
    private LogMapper logMapper;

    public Log getLog(long id) {
        return logMapper.selectByPrimaryKey(id);
    }

    public List<Log> getLog() {
        LogExample example = new LogExample();
        return logMapper.selectByExample(example);
    }

    public List<LogVo4List> select(String operation, Integer usernameID, String clientIp, Integer status, String beginTime, String endTime,
                                   String contentdetails, int offset, int rows) {
        return logMapper.selectByConditionLog(operation, usernameID, clientIp, status, beginTime, endTime,
                            contentdetails, offset, rows);
    }

    public int countLog(String operation, Integer usernameID, String clientIp, Integer status, String beginTime, String endTime,
                        String contentdetails) {
        return logMapper.countsByLog(operation, usernameID, clientIp, status, beginTime, endTime, contentdetails);
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

}
