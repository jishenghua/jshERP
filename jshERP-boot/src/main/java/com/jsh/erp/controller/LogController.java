package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.Log;
import com.jsh.erp.datasource.vo.LogVo4List;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;
import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

/**
 * @author ji sheng hua 752*718*920
 */
@RestController
@RequestMapping(value = "/log")
@Api(tags = {"日志管理"})
public class LogController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(LogController.class);

    @Resource
    private LogService logService;


    @GetMapping(value = "/info")
    @ApiOperation(value = "根据id获取信息")
    public String getList(@RequestParam("id") Long id,
                          HttpServletRequest request) throws Exception {
        Log log = logService.getLog(id);
        Map<String, Object> objectMap = new HashMap<>();
        if(log != null) {
            objectMap.put("info", log);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取信息列表")
    public TableDataInfo getList(@RequestParam(value = Constants.SEARCH, required = false) String search,
                                 HttpServletRequest request)throws Exception {
        String operation = StringUtil.getInfo(search, "operation");
        String userInfo = StringUtil.getInfo(search, "userInfo");
        String clientIp = StringUtil.getInfo(search, "clientIp");
        String tenantLoginName = StringUtil.getInfo(search, "tenantLoginName");
        String tenantType = StringUtil.getInfo(search, "tenantType");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String content = StringUtil.getInfo(search, "content");
        List<LogVo4List> list = logService.select(operation, userInfo, clientIp, tenantLoginName, tenantType, beginTime, endTime, content);
        return getDataTable(list);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增")
    public String addResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int insert = logService.insertLog(obj, request);
        return returnStr(objectMap, insert);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "修改")
    public String updateResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int update = logService.updateLog(obj, request);
        return returnStr(objectMap, update);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除")
    public String deleteResource(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = logService.deleteLog(id, request);
        return returnStr(objectMap, delete);
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    public String batchDeleteResource(@RequestParam("ids") String ids, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = logService.batchDeleteLog(ids, request);
        return returnStr(objectMap, delete);
    }
}