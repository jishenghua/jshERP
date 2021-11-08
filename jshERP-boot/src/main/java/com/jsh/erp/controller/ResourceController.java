package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.service.CommonQueryManager;
import com.jsh.erp.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * by jishenghua 2018-9-12 23:58:10 华夏erp
 */
@RestController
@Api(tags = {"资源接口"})
public class ResourceController {

    @Resource
    private CommonQueryManager configResourceManager;

    @GetMapping(value = "/{apiName}/info")
    @ApiOperation(value = "根据id获取信息")
    public String getList(@PathVariable("apiName") String apiName,
                          @RequestParam("id") Long id,
                          HttpServletRequest request) throws Exception {
        Object obj = configResourceManager.selectOne(apiName, id);
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if(obj != null) {
            objectMap.put("info", obj);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/{apiName}/list")
    @ApiOperation(value = "获取信息列表")
    public String getList(@PathVariable("apiName") String apiName,
                        @RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
                        @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
                        @RequestParam(value = Constants.SEARCH, required = false) String search,
                        HttpServletRequest request)throws Exception {
        Map<String, String> parameterMap = ParamUtils.requestToMap(request);
        parameterMap.put(Constants.SEARCH, search);
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (pageSize != null && pageSize <= 0) {
            pageSize = 10;
        }
        String offset = ParamUtils.getPageOffset(currentPage, pageSize);
        if (StringUtil.isNotEmpty(offset)) {
            parameterMap.put(Constants.OFFSET, offset);
        }
        List<?> list = configResourceManager.select(apiName, parameterMap);
        if (list != null) {
            objectMap.put("total", configResourceManager.counts(apiName, parameterMap));
            objectMap.put("rows", list);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            objectMap.put("total", BusinessConstants.DEFAULT_LIST_NULL_NUMBER);
            objectMap.put("rows", new ArrayList<Object>());
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
    }

    @PostMapping(value = "/{apiName}/add", produces = {"application/javascript", "application/json"})
    @ApiOperation(value = "新增")
    public String addResource(@PathVariable("apiName") String apiName,
                              @RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int insert = configResourceManager.insert(apiName, obj, request);
        if(insert > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else if(insert == -1) {
            return returnJson(objectMap, ErpInfo.TEST_USER.name, ErpInfo.TEST_USER.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @PutMapping(value = "/{apiName}/update", produces = {"application/javascript", "application/json"})
    @ApiOperation(value = "修改")
    public String updateResource(@PathVariable("apiName") String apiName,
                                 @RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int update = configResourceManager.update(apiName, obj, request);
        if(update > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else if(update == -1) {
            return returnJson(objectMap, ErpInfo.TEST_USER.name, ErpInfo.TEST_USER.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @DeleteMapping(value = "/{apiName}/delete", produces = {"application/javascript", "application/json"})
    @ApiOperation(value = "删除")
    public String deleteResource(@PathVariable("apiName") String apiName,
                                 @RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int delete = configResourceManager.delete(apiName, id, request);
        if(delete > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else if(delete == -1) {
            return returnJson(objectMap, ErpInfo.TEST_USER.name, ErpInfo.TEST_USER.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @DeleteMapping(value = "/{apiName}/deleteBatch", produces = {"application/javascript", "application/json"})
    @ApiOperation(value = "批量删除")
    public String batchDeleteResource(@PathVariable("apiName") String apiName,
                                      @RequestParam("ids") String ids, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int delete = configResourceManager.deleteBatch(apiName, ids, request);
        if(delete > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else if(delete == -1) {
            return returnJson(objectMap, ErpInfo.TEST_USER.name, ErpInfo.TEST_USER.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/{apiName}/checkIsNameExist")
    @ApiOperation(value = "检查名称是否存在")
    public String checkIsNameExist(@PathVariable("apiName") String apiName,
                                   @RequestParam Long id, @RequestParam(value ="name", required = false) String name,
                                   HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int exist = configResourceManager.checkIsNameExist(apiName, id, name);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }


}
