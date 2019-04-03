package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.materialProperty.MaterialPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description
 *
 * @Author: qiankunpingtai
 * @Date: 2019/3/29 15:24
 */
@RestController
@RequestMapping(value = "/materialProperty")
public class MaterialPropertyController {
    private Logger logger = LoggerFactory.getLogger(MaterialPropertyController.class);
    @Resource
    private MaterialPropertyService materialPropertyService;
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  批量删除商品扩展信息
     * create time: 2019/3/29 11:15
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteMaterialPropertyByIds")
    public Object batchDeleteMaterialPropertyByIds(@RequestParam("ids") String ids) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i= materialPropertyService.batchDeleteMaterialPropertyByIds(ids);
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.MATERIAL_PROPERTY_DELETE_FAILED_CODE,ExceptionConstants.MATERIAL_PROPERTY_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.MATERIAL_PROPERTY_DELETE_FAILED_CODE,
                    ExceptionConstants.MATERIAL_PROPERTY_DELETE_FAILED_MSG);
        }
        return result;
    }
}
