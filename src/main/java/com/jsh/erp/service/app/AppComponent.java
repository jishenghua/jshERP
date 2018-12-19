package com.jsh.erp.service.app;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service(value = "app_component")
@AppResource
public class AppComponent implements ICommonQuery {

    @Resource
    private AppService appService;

    @Override
    public Object selectOne(String condition) {
        return null;
    }

    @Override
    public List<?> select(Map<String, String> map) {
        return getAppList(map);
    }

    private List<?> getAppList(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String type = StringUtil.getInfo(search, "type");
        String order = QueryUtils.order(map);
        return appService.select(name, type, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public int counts(Map<String, String> map) {
        String search = map.get(Constants.SEARCH);
        String name = StringUtil.getInfo(search, "name");
        String type = StringUtil.getInfo(search, "type");
        return appService.countApp(name, type);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request) {
        return appService.insertApp(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id) {
        return appService.updateApp(beanJson, id);
    }

    @Override
    public int delete(Long id) {
        return appService.deleteApp(id);
    }

    @Override
    public int batchDelete(String ids) {
        return appService.batchDeleteApp(ids);
    }

    @Override
    public int checkIsNameExist(Long id, String name) {
        return 0;
    }

}
