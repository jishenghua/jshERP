package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.DepotItem;
import com.jsh.erp.datasource.entities.SerialNumberEx;
import com.jsh.erp.service.DepotHeadService;
import com.jsh.erp.service.DepotItemService;
import com.jsh.erp.service.SerialNumberService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
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

import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

/**
 * Description
 * @Author: jsh
 * @Date: 2019/1/22 10:29
 */
@RestController
@RequestMapping(value = "/serialNumber")
@Api(tags = {"序列号管理"})
public class SerialNumberController {
    private Logger logger = LoggerFactory.getLogger(SerialNumberController.class);

    @Resource
    private SerialNumberService serialNumberService;
    @Resource
    private DepotHeadService depotHeadService;
    @Resource
    private DepotItemService depotItemService;

    /**
     * create by: jsh
     * @return java.lang.Object
     */
    @PostMapping("/batAddSerialNumber")
    @ApiOperation(value = "批量添加序列号")
    public String batAddSerialNumber(@RequestBody JSONObject jsonObject, HttpServletRequest request)throws Exception{
        Map<String, Object> objectMap = new HashMap<>();
        String materialCode = jsonObject.getString("materialCode");
        String serialNumberPrefix = jsonObject.getString("serialNumberPrefix");
        Integer batAddTotal = jsonObject.getInteger("batAddTotal");
        String remark = jsonObject.getString("remark");
        int insert = serialNumberService.batAddSerialNumber(materialCode,serialNumberPrefix,batAddTotal,remark);
        return returnStr(objectMap, insert);
    }

    /**
     * 获取序列号商品
     * @param jsonObject
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/getEnableSerialNumberList")
    @ApiOperation(value = "获取序列号商品")
    public BaseResponseInfo getEnableSerialNumberList(@RequestBody JSONObject jsonObject, HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            String name = jsonObject.getString("name");
            Long depotItemId = jsonObject.getLong("depotItemId");
            Long depotId = jsonObject.getLong("depotId");
            String barCode = jsonObject.getString("barCode");
            Integer currentPage = jsonObject.getInteger("page");
            Integer pageSize = jsonObject.getInteger("rows");
            String number = "";
            String [] nameArray = null;
            if(depotItemId != null) {
                DepotItem depotItem = depotItemService.getDepotItem(depotItemId);
                number = depotHeadService.getDepotHead(depotItem.getHeaderId()).getNumber();
            }
            // 批量查询序列号时，name可能为多个
            if(StringUtil.isNotEmpty(name)) {
                name = name.replace("，",",");
                if(name.contains(",")) {
                    nameArray = name.split(",");
                    name = null;
                }
            }
            List<SerialNumberEx> list = serialNumberService.getEnableSerialNumberList(number, name, nameArray, depotId, barCode, (currentPage-1)*pageSize, pageSize);
            for(SerialNumberEx serialNumberEx: list) {
                serialNumberEx.setCreateTimeStr(Tools.getCenternTime(serialNumberEx.getCreateTime()));
            }
            Long total = serialNumberService.getEnableSerialNumberCount(number, name, nameArray, depotId, barCode);
            List<String> missList = new ArrayList<>();
            if(nameArray!=null && nameArray.length>0) {
                List<SerialNumberEx> allList = serialNumberService.getEnableSerialNumberList(number, name, nameArray, depotId, barCode, null, null);
                if(allList.size() < nameArray.length) {
                    //说明查出的比查询条件里面的序列号少，此时需要寻找出缺少的序列号
                    for (String item : nameArray) {
                        boolean isHave = false;
                        for (SerialNumberEx serialNumberEx : allList) {
                            if (item.equals(serialNumberEx.getSerialNumber())) {
                                isHave = true;
                                break;
                            }
                        }
                        if (!isHave) {
                            missList.add(item);
                        }
                    }
                }
            }
            map.put("rows", list);
            map.put("total", total);
            if(!missList.isEmpty()) {
                //列出未查询到的序列号
                map.put("missInfo", String.join(",", missList));
            }
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
