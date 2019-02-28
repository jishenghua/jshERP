package com.jsh.erp.service.log;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "log_component")
@LogResource
public class LogComponent implements ICommonQuery {

    @Resource
    private LogService logService;

    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getUserList(map);
    }

    private List<?> getUserList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String operation = StringUtil.getInfo(search, "operation");
        Integer usernameID = StringUtil.parseInteger(StringUtil.getInfo(search, "usernameID"));
        String clientIp = StringUtil.getInfo(search, "clientIp");
        Integer status = StringUtil.parseInteger(StringUtil.getInfo(search, "status"));
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String contentdetails = StringUtil.getInfo(search, "contentdetails");
        String order = QueryUtils.order(map);
        return logService.select(operation, usernameID, clientIp, status, beginTime, endTime, contentdetails,
                QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String operation = StringUtil.getInfo(search, "operation");
        Integer usernameID = StringUtil.parseInteger(StringUtil.getInfo(search, "usernameID"));
        String clientIp = StringUtil.getInfo(search, "clientIp");
        Integer status = StringUtil.parseInteger(StringUtil.getInfo(search, "status"));
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String contentdetails = StringUtil.getInfo(search, "contentdetails");
        return logService.countLog(operation, usernameID, clientIp, status, beginTime, endTime, contentdetails);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return logService.insertLog(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return logService.updateLog(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return logService.deleteLog(id);
    }

    @Override
    public int batchDelete(String ids) {
        return logService.batchDeleteLog(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return 0;
    }

}
