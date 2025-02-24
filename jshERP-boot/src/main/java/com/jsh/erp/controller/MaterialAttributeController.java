package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.MaterialAttribute;
import com.jsh.erp.service.MaterialAttributeService;
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
 * @author ji sheng hua jshERP
 */
@RestController
@RequestMapping(value = "/materialAttribute")
@Api(tags = {"商品属性"})
public class MaterialAttributeController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MaterialAttributeController.class);

    @Resource
    private MaterialAttributeService materialAttributeService;

    @GetMapping(value = "/info")
    @ApiOperation(value = "根据id获取信息")
    public String getList(@RequestParam("id") Long id,
                          HttpServletRequest request) throws Exception {
        MaterialAttribute materialAttribute = materialAttributeService.getMaterialAttribute(id);
        Map<String, Object> objectMap = new HashMap<>();
        if(materialAttribute != null) {
            objectMap.put("info", materialAttribute);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取信息列表")
    public TableDataInfo getList(@RequestParam(value = Constants.SEARCH, required = false) String search,
                                 HttpServletRequest request)throws Exception {
        String attributeName = StringUtil.getInfo(search, "attributeName");
        List<MaterialAttribute> list = materialAttributeService.select(attributeName);
        return getDataTable(list);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增")
    public String addResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int insert = materialAttributeService.insertMaterialAttribute(obj, request);
        return returnStr(objectMap, insert);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "修改")
    public String updateResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int update = materialAttributeService.updateMaterialAttribute(obj, request);
        return returnStr(objectMap, update);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除")
    public String deleteResource(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = materialAttributeService.deleteMaterialAttribute(id, request);
        return returnStr(objectMap, delete);
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    public String batchDeleteResource(@RequestParam("ids") String ids, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = materialAttributeService.batchDeleteMaterialAttribute(ids, request);
        return returnStr(objectMap, delete);
    }

    @GetMapping(value = "/checkIsNameExist")
    @ApiOperation(value = "检查名称是否存在")
    public String checkIsNameExist(@RequestParam Long id, @RequestParam(value ="name", required = false) String name,
                                   HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int exist = materialAttributeService.checkIsNameExist(id, name);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 获取商品属性的名称列表
     * @param request
     * @return
     */
    @GetMapping(value = "/getNameList")
    @ApiOperation(value = "获取商品属性的名称列表")
    public JSONArray getNameList(HttpServletRequest request)throws Exception {
        JSONArray dataArray = new JSONArray();
        try {
            List<MaterialAttribute> materialAttributeList = materialAttributeService.getMaterialAttribute();
            if (null != materialAttributeList) {
                for (MaterialAttribute materialAttribute : materialAttributeList) {
                    JSONObject item = new JSONObject();
                    item.put("value", materialAttribute.getId().toString());
                    item.put("name", materialAttribute.getAttributeName());
                    dataArray.add(item);
                }
            }
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return dataArray;
    }

    /**
     * 获取id查询属性的值列表
     * @param request
     * @return
     */
    @GetMapping(value = "/getValueListById")
    @ApiOperation(value = "获取id查询属性的值列表")
    public JSONArray getValueListById(@RequestParam("id") Long id,
                                     HttpServletRequest request)throws Exception {
        JSONArray dataArray = new JSONArray();
        try {
            dataArray = materialAttributeService.getValueArrById(id);
        } catch(Exception e){
            logger.error(e.getMessage(), e);
        }
        return dataArray;
    }
}
