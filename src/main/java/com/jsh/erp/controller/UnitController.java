package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.unit.UnitService;
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
 * @Date: 2019/4/1 15:38
 */
@RestController
@RequestMapping(value = "/unit")
public class UnitController {
    private Logger logger = LoggerFactory.getLogger(UnitController.class);
    @Resource
    private UnitService unitService;
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  批量删除系统配置信息
     * create time: 2019/3/29 11:15
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteUnitByIds")
    public Object batchDeleteUnitByIds(@RequestParam("ids") String ids,@RequestParam(value="deleteType",
            required =false,defaultValue= BusinessConstants.DELETE_TYPE_NORMAL)String deleteType) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i=0;
        if(BusinessConstants.DELETE_TYPE_NORMAL.equals(deleteType)){
            i= unitService.batchDeleteUnitByIdsNormal(ids);
        }else if(BusinessConstants.DELETE_TYPE_FORCE.equals(deleteType)){
            i= unitService.batchDeleteUnitByIds(ids);
        }else{
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}],deleteType[{}]",
                    ExceptionConstants.DELETE_REFUSED_CODE,ExceptionConstants.DELETE_REFUSED_MSG,ids,deleteType);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_REFUSED_CODE,
                    ExceptionConstants.DELETE_REFUSED_MSG);
        }
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.UNIT_DELETE_FAILED_CODE,ExceptionConstants.UNIT_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.UNIT_DELETE_FAILED_CODE,
                    ExceptionConstants.UNIT_DELETE_FAILED_MSG);
        }
        return result;
    }
}
