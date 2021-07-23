package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.MaterialAttribute;
import com.jsh.erp.service.materialAttribute.MaterialAttributeService;
import com.jsh.erp.utils.BaseResponseInfo;
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
public class MaterialAttributeController {
    private Logger logger = LoggerFactory.getLogger(MaterialAttributeController.class);

    @Resource
    private MaterialAttributeService materialAttributeService;

    @GetMapping("/getAll")
    public BaseResponseInfo getAll(HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            JSONObject obj = materialAttributeService.getAll();
            res.code = 200;
            res.data = obj;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
