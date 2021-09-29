package com.jsh.erp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.SerialNumber;
import com.jsh.erp.datasource.entities.SerialNumberEx;
import com.jsh.erp.exception.BusinessParamCheckingException;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.serialNumber.SerialNumberService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * Description
 *
 * @Author: cjl
 * @Date: 2019/1/22 10:29
 */
@RestController
public class SerialNumberController {
    private Logger logger = LoggerFactory.getLogger(SerialNumberController.class);

    @Resource
    private SerialNumberService serialNumberService;
    /**
     * create by: cjl
     * description:
     *  检查序列号是否存在
     * create time: 2019/1/22 11:02
     * @Param: id
     * @Param: materialName
     * @Param: serialNumber
     * @Param: request
     * @return java.lang.Object
     */
    @PostMapping("/serialNumber/checkIsExist")
    @ResponseBody
    public Object checkIsExist(@RequestParam("id") Long id, @RequestParam("materialName") String materialName,
                               @RequestParam("serialNumber") String serialNumber, HttpServletRequest request) throws Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        if(StringUtil.isEmpty(serialNumber)){
            throw new BusinessParamCheckingException(ExceptionConstants.SERIAL_NUMBERE_NOT_BE_EMPTY_CODE,
                    ExceptionConstants.SERIAL_NUMBERE_NOT_BE_EMPTY_MSG);
        }
        serialNumberService.checkIsExist(id, materialName, serialNumber);
        return result;
    }

    /**
     * create by: cjl
     * description:
     *批量添加序列号
     * create time: 2019/1/29 15:11
     * @Param: materialName
     * @Param: serialNumberPrefix
     * @Param: batAddTotal
     * @Param: remark
     * @return java.lang.Object
     */
    @PostMapping("/serialNumber/batAddSerialNumber")
    @ResponseBody
    public String batAddSerialNumber(@RequestBody JSONObject jsonObject, HttpServletRequest request)throws Exception{
        Map<String, Object> objectMap = new HashMap<>();
        String materialCode = jsonObject.getString("materialCode");
        String serialNumberPrefix = jsonObject.getString("serialNumberPrefix");
        Integer batAddTotal = jsonObject.getInteger("batAddTotal");
        String remark = jsonObject.getString("remark");
        int insert = serialNumberService.batAddSerialNumber(materialCode,serialNumberPrefix,batAddTotal,remark);
        if(insert > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else if(insert == -1) {
            return returnJson(objectMap, ErpInfo.TEST_USER.name, ErpInfo.TEST_USER.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 获取序列号商品
     * @param name
     * @param depotId
     * @param barCode
     * @param currentPage
     * @param pageSize
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/serialNumber/getEnableSerialNumberList")
    public BaseResponseInfo getEnableSerialNumberList(@RequestParam("name") String name,
                                                      @RequestParam("depotId") Long depotId,
                                                      @RequestParam("barCode") String barCode,
                                                      @RequestParam("page") Integer currentPage,
                                                      @RequestParam("rows") Integer pageSize,
                                                      HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            List<SerialNumber> list = serialNumberService.getEnableSerialNumberList(name, depotId, barCode, (currentPage-1)*pageSize, pageSize);
            Long total = serialNumberService.getEnableSerialNumberCount(name, depotId, barCode);
            map.put("rows", list);
            map.put("total", total);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
