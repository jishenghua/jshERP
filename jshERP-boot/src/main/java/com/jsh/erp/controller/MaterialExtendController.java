package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.MaterialExtend;
import com.jsh.erp.datasource.vo.MaterialExtendVo4List;
import com.jsh.erp.service.materialExtend.MaterialExtendService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
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

/**
 * @author jijiaqing
 */
@RestController
@RequestMapping(value = "/materialsExtend")
@Api(tags = {"商品价格扩展"})
public class MaterialExtendController {
    private Logger logger = LoggerFactory.getLogger(MaterialExtendController.class);
    @Resource
    private MaterialExtendService materialExtendService;

    @GetMapping(value = "/getDetailList")
    @ApiOperation(value = "价格信息列表")
    public BaseResponseInfo getDetailList(@RequestParam("materialId") Long materialId,
                                          HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<MaterialExtendVo4List> dataList = new ArrayList<MaterialExtendVo4List>();
            if(materialId!=0) {
                dataList = materialExtendService.getDetailList(materialId);
            }
            JSONObject outer = new JSONObject();
            outer.put("total", dataList.size());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (MaterialExtendVo4List md : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", md.getId());
                    item.put("barCode", md.getBarCode());
                    item.put("commodityUnit", md.getCommodityUnit());
                    if(StringUtil.isNotEmpty(md.getSku())){
                        item.put("sku", md.getSku());
                    }
                    item.put("purchaseDecimal", md.getPurchaseDecimal());
                    item.put("commodityDecimal", md.getCommodityDecimal());
                    item.put("wholesaleDecimal", md.getWholesaleDecimal());
                    item.put("lowDecimal", md.getLowDecimal());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            res.code = 200;
            res.data = outer;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 根据条码查询商品信息
     * @param barCode
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getInfoByBarCode")
    @ApiOperation(value = "根据条码查询商品信息")
    public BaseResponseInfo getInfoByBarCode(@RequestParam("barCode") String barCode,
                                          HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            MaterialExtend materialExtend = materialExtendService.getInfoByBarCode(barCode);
            res.code = 200;
            res.data = materialExtend;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 校验条码是否存在
     * @param id
     * @param barCode
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/checkIsBarCodeExist")
    @ApiOperation(value = "校验条码是否存在")
    public BaseResponseInfo checkIsBarCodeExist(@RequestParam("id") Long id,
                                                @RequestParam("barCode") String barCode,
                                             HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            int exist = materialExtendService.checkIsBarCodeExist(id, barCode);
            if(exist > 0) {
                map.put("status", true);
            } else {
                map.put("status", false);
            }
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }
}
