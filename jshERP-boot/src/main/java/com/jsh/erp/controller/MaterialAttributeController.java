package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.MaterialAttribute;
import com.jsh.erp.datasource.entities.Person;
import com.jsh.erp.service.materialAttribute.MaterialAttributeService;
import com.jsh.erp.utils.BaseResponseInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ji sheng hua jshERP
 */
@RestController
@RequestMapping(value = "/materialAttribute")
@Api(tags = {"商品属性"})
public class MaterialAttributeController {
    private Logger logger = LoggerFactory.getLogger(MaterialAttributeController.class);

    @Resource
    private MaterialAttributeService materialAttributeService;

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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return dataArray;
    }
}
