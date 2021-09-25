package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.vo.DepotItemStockWarningCount;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.materialExtend.MaterialExtendService;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.redis.RedisService;
import com.jsh.erp.service.unit.UnitService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji-sheng-hua 华夏erp
 */
@RestController
@RequestMapping(value = "/depotItem")
public class DepotItemController {
    private Logger logger = LoggerFactory.getLogger(DepotItemController.class);

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private MaterialService materialService;

    @Resource
    private MaterialExtendService materialExtendService;

    @Resource
    private UnitService unitService;

    @Resource
    private RedisService redisService;

    /**
     * 只根据商品id查询单据列表
     * @param mId
     * @param request
     * @return
     */
    @GetMapping(value = "/findDetailByTypeAndMaterialId")
    public String findDetailByTypeAndMaterialId(
            @RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
            @RequestParam("materialId") String mId, HttpServletRequest request)throws Exception {
        Map<String, String> parameterMap = ParamUtils.requestToMap(request);
        parameterMap.put("mId", mId);
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (pageSize != null && pageSize <= 0) {
            pageSize = 10;
        }
        String offset = ParamUtils.getPageOffset(currentPage, pageSize);
        if (StringUtil.isNotEmpty(offset)) {
            parameterMap.put(Constants.OFFSET, offset);
        }
        List<DepotItemVo4DetailByTypeAndMId> list = depotItemService.findDetailByTypeAndMaterialIdList(parameterMap);
        JSONArray dataArray = new JSONArray();
        if (list != null) {
            for (DepotItemVo4DetailByTypeAndMId d: list) {
                JSONObject item = new JSONObject();
                item.put("number", d.getNumber()); //编号
                item.put("barCode", d.getBarCode()); //条码
                item.put("materialName", d.getMaterialName()); //名称
                String type = d.getType();
                String subType = d.getSubType();
                if(("其它").equals(type)) {
                    item.put("type", subType); //进出类型
                } else {
                    item.put("type", subType + type); //进出类型
                }
                item.put("depotName", d.getDepotName()); //仓库名称
                item.put("basicNumber", d.getBnum()); //数量
                item.put("operTime", Tools.getCenternTime(d.getOtime())); //时间
                dataArray.add(item);
            }
        }
        if (list == null) {
            objectMap.put("rows", new ArrayList<Object>());
            objectMap.put("total", BusinessConstants.DEFAULT_LIST_NULL_NUMBER);
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
        objectMap.put("rows", dataArray);
        objectMap.put("total", depotItemService.findDetailByTypeAndMaterialIdCounts(parameterMap));
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 根据商品条码和仓库id查询库存数量
     * @param depotId
     * @param barCode
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/findStockByDepotAndBarCode")
    public BaseResponseInfo findStockByDepotAndBarCode(
            @RequestParam("depotId") Long depotId,
            @RequestParam("barCode") String barCode,
            HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BigDecimal stock = BigDecimal.ZERO;
            List<MaterialVo4Unit> list = materialService.getMaterialByBarCode(barCode);
            if(list!=null && list.size()>0) {
                MaterialVo4Unit materialVo4Unit = list.get(0);
                if(StringUtil.isNotEmpty(materialVo4Unit.getSku())){
                    stock = depotItemService.getSkuStockByParam(depotId,materialVo4Unit.getMeId(),null,null);
                } else {
                    stock = depotItemService.getStockByParam(depotId,materialVo4Unit.getId(),null,null);
                    String commodityUnit = materialVo4Unit.getCommodityUnit();
                    Long unitId = materialVo4Unit.getUnitId();
                    if(unitId!=null) {
                        Integer ratio = 1;
                        Unit unit = unitService.getUnit(unitId);
                        if(commodityUnit.equals(unit.getOtherUnit())){
                            ratio = unit.getRatio();
                            if(ratio!=0) {
                                stock = stock.divide(BigDecimal.valueOf(ratio),2,BigDecimal.ROUND_HALF_UP); //两位小数
                            }
                        }
                    }
                }
            }
            map.put("stock", stock);
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    @GetMapping(value = "/getDetailList")
    public BaseResponseInfo getDetailList(@RequestParam("headerId") Long headerId,
                              @RequestParam("mpList") String mpList,
                              HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<DepotItemVo4WithInfoEx> dataList = new ArrayList<DepotItemVo4WithInfoEx>();
            if(headerId != 0) {
                dataList = depotItemService.getDetailList(headerId);
            }
            String[] mpArr = mpList.split(",");
            JSONObject outer = new JSONObject();
            outer.put("total", dataList.size());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("materialExtendId", diEx.getMaterialExtendId() == null ? "" : diEx.getMaterialExtendId());
                    item.put("barCode", diEx.getBarCode());
                    item.put("name", diEx.getMName());
                    item.put("standard", diEx.getMStandard());
                    item.put("model", diEx.getMModel());
                    item.put("materialOther", getOtherInfo(mpArr, diEx));
                    Integer ratio = diEx.getRatio();
                    BigDecimal stock;
                    if(StringUtil.isNotEmpty(diEx.getSku())){
                        stock = depotItemService.getSkuStockByParam(diEx.getDepotId(),diEx.getMaterialExtendId(),null,null);
                    } else {
                        stock = depotItemService.getStockByParam(diEx.getDepotId(),diEx.getMaterialId(),null,null);
                        if(ratio!=null){
                            BigDecimal ratioDecimal = new BigDecimal(ratio.toString());
                            if(ratioDecimal.compareTo(BigDecimal.ZERO)!=0){
                                String otherUnit = diEx.getOtherUnit();
                                if(otherUnit.equals(diEx.getMaterialUnit())) {
                                    stock = stock.divide(ratioDecimal,2,BigDecimal.ROUND_HALF_UP); //两位小数
                                }
                            }
                        }
                    }
                    item.put("stock", stock);
                    item.put("unit", diEx.getMaterialUnit());
                    item.put("sku", diEx.getSku());
                    item.put("operNumber", diEx.getOperNumber());
                    item.put("basicNumber", diEx.getBasicNumber());
                    item.put("preNumber", diEx.getOperNumber()); //原数量
                    item.put("finishNumber", depotItemService.getFinishNumber(diEx.getMaterialId(), diEx.getHeaderId())); //已入库|已出库
                    item.put("unitPrice", diEx.getUnitPrice());
                    item.put("taxUnitPrice", diEx.getTaxUnitPrice());
                    item.put("allPrice", diEx.getAllPrice());
                    item.put("remark", diEx.getRemark());
                    item.put("depotId", diEx.getDepotId() == null ? "" : diEx.getDepotId());
                    item.put("depotName", diEx.getDepotId() == null ? "" : diEx.getDepotName());
                    item.put("anotherDepotId", diEx.getAnotherDepotId() == null ? "" : diEx.getAnotherDepotId());
                    item.put("anotherDepotName", diEx.getAnotherDepotId() == null ? "" : diEx.getAnotherDepotName());
                    item.put("taxRate", diEx.getTaxRate());
                    item.put("taxMoney", diEx.getTaxMoney());
                    item.put("taxLastMoney", diEx.getTaxLastMoney());
                    item.put("mType", diEx.getMaterialType());
                    item.put("op", 1);
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
     * 获取扩展信息
     *
     * @return
     */
    public String getOtherInfo(String[] mpArr, DepotItemVo4WithInfoEx diEx)throws Exception {
        String materialOther = "";
        for (int i = 0; i < mpArr.length; i++) {
            if (mpArr[i].equals("制造商")) {
                materialOther = materialOther + ((diEx.getMMfrs() == null || diEx.getMMfrs().equals("")) ? "" : "(" + diEx.getMMfrs() + ")");
            }
            if (mpArr[i].equals("自定义1")) {
                materialOther = materialOther + ((diEx.getMOtherField1() == null || diEx.getMOtherField1().equals("")) ? "" : "(" + diEx.getMOtherField1() + ")");
            }
            if (mpArr[i].equals("自定义2")) {
                materialOther = materialOther + ((diEx.getMOtherField2() == null || diEx.getMOtherField2().equals("")) ? "" : "(" + diEx.getMOtherField2() + ")");
            }
            if (mpArr[i].equals("自定义3")) {
                materialOther = materialOther + ((diEx.getMOtherField3() == null || diEx.getMOtherField3().equals("")) ? "" : "(" + diEx.getMOtherField3() + ")");
            }
        }
        return materialOther;
    }

    /**
     * 查找所有的明细
     * @param currentPage
     * @param pageSize
     * @param depotId
     * @param monthTime
     * @param materialParam
     * @param mpList
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/findByAll")
    public BaseResponseInfo findByAll(@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("depotId") Long depotId,
                                      @RequestParam("monthTime") String monthTime,
                                      @RequestParam("materialParam") String materialParam,
                                      @RequestParam("mpList") String mpList,
                                      HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String timeA = Tools.firstDayOfMonth(monthTime) + BusinessConstants.DAY_FIRST_TIME;
        String timeB = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    timeB,(currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(StringUtil.toNull(materialParam), timeB);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                List<Long> idList = new ArrayList<Long>();
                for (DepotItemVo4WithInfoEx m : dataList) {
                    idList.add(m.getMId());
                }
                List<MaterialExtend> meList = materialExtendService.getListByMIds(idList);
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    Long mId = diEx.getMId();
                    item.put("barCode", diEx.getBarCode());
                    item.put("materialName", diEx.getMName());
                    item.put("materialModel", diEx.getMModel());
                    item.put("materialStandard", diEx.getMStandard());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, diEx);
                    item.put("materialOther", materialOther);
                    item.put("materialColor", diEx.getMColor());
                    item.put("unitName", diEx.getMaterialUnit());

                    item.put("prevSum", depotItemService.getStockByParam(depotId,mId,null,timeA));
                    item.put("inSum", depotItemService.getInNumByParam(depotId,mId,timeA,timeB));
                    item.put("outSum", depotItemService.getOutNumByParam(depotId,mId,timeA,timeB));
                    BigDecimal thisSum = depotItemService.getStockByParam(depotId,mId,null,timeB);
                    item.put("thisSum", thisSum);
                    for(MaterialExtend me:meList) {
                        if(me.getMaterialId().longValue() == diEx.getMId().longValue()) {
                            if(me.getPurchaseDecimal()!=null) {
                                item.put("unitPrice", me.getPurchaseDecimal());
                                item.put("thisAllPrice", thisSum.multiply(me.getPurchaseDecimal()));
                            }
                        }
                    }
                    dataArray.add(item);
                }
            }
            map.put("rows", dataArray);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 统计总计金额
     * @param depotId
     * @param monthTime
     * @param materialParam
     * @param request
     * @return
     */
    @GetMapping(value = "/totalCountMoney")
    public BaseResponseInfo totalCountMoney(@RequestParam("depotId") Long depotId,
                                            @RequestParam("monthTime") String monthTime,
                                            @RequestParam("materialParam") String materialParam,
                                            HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String endTime = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    endTime, null, null);
            BigDecimal thisAllPrice = BigDecimal.ZERO;
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    Long mId = diEx.getMId();
                    BigDecimal thisSum = depotItemService.getStockByParam(depotId,mId,null,endTime);
                    BigDecimal unitPrice = diEx.getPurchaseDecimal();
                    if(unitPrice == null) {
                        unitPrice = BigDecimal.ZERO;
                    }
                    thisAllPrice = thisAllPrice.add(thisSum.multiply(unitPrice));
                }
            }
            map.put("totalCount", thisAllPrice);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 进货统计
     * @param currentPage
     * @param pageSize
     * @param monthTime
     * @param materialParam
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/buyIn")
    public BaseResponseInfo buyIn(@RequestParam("currentPage") Integer currentPage,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("monthTime") String monthTime,
                                  @RequestParam("materialParam") String materialParam,
                                  @RequestParam("mpList") String mpList,
                                  HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String endTime = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    endTime, (currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(StringUtil.toNull(materialParam), endTime);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    BigDecimal InSum = depotItemService.buyOrSale("入库", "采购", diEx.getMId(), monthTime, "number");
                    BigDecimal OutSum = depotItemService.buyOrSale("出库", "采购退货", diEx.getMId(), monthTime, "number");
                    BigDecimal InSumPrice = depotItemService.buyOrSale("入库", "采购", diEx.getMId(), monthTime, "price");
                    BigDecimal OutSumPrice = depotItemService.buyOrSale("出库", "采购退货", diEx.getMId(), monthTime, "price");
                    item.put("barCode", diEx.getBarCode());
                    item.put("materialName", diEx.getMName());
                    item.put("materialModel", diEx.getMModel());
                    item.put("materialStandard", diEx.getMStandard());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, diEx);
                    item.put("materialOther", materialOther);
                    item.put("materialColor", diEx.getMColor());
                    item.put("materialUnit", diEx.getMaterialUnit());
                    item.put("unitName", diEx.getUnitName());
                    item.put("inSum", InSum);
                    item.put("outSum", OutSum);
                    item.put("inSumPrice", InSumPrice);
                    item.put("outSumPrice", OutSumPrice);
                    dataArray.add(item);
                }
            }
            map.put("rows", dataArray);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 销售统计
     * @param currentPage
     * @param pageSize
     * @param monthTime
     * @param materialParam
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/saleOut")
    public BaseResponseInfo saleOut(@RequestParam("currentPage") Integer currentPage,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("monthTime") String monthTime,
                                  @RequestParam("materialParam") String materialParam,
                                  @RequestParam("mpList") String mpList,
                                  HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String endTime = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    endTime,(currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(StringUtil.toNull(materialParam), endTime);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    BigDecimal OutSumRetail = depotItemService.buyOrSale("出库", "零售", diEx.getMId(), monthTime,"number");
                    BigDecimal OutSum = depotItemService.buyOrSale("出库", "销售", diEx.getMId(), monthTime,"number");
                    BigDecimal InSumRetail = depotItemService.buyOrSale("入库", "零售退货", diEx.getMId(), monthTime,"number");
                    BigDecimal InSum = depotItemService.buyOrSale("入库", "销售退货", diEx.getMId(), monthTime,"number");
                    BigDecimal OutSumRetailPrice = depotItemService.buyOrSale("出库", "零售", diEx.getMId(), monthTime,"price");
                    BigDecimal OutSumPrice = depotItemService.buyOrSale("出库", "销售", diEx.getMId(), monthTime,"price");
                    BigDecimal InSumRetailPrice = depotItemService.buyOrSale("入库", "零售退货", diEx.getMId(), monthTime,"price");
                    BigDecimal InSumPrice = depotItemService.buyOrSale("入库", "销售退货", diEx.getMId(), monthTime,"price");
                    BigDecimal OutInSumPrice = (OutSumRetailPrice.add(OutSumPrice)).subtract(InSumRetailPrice.add(InSumPrice));
                    item.put("barCode", diEx.getBarCode());
                    item.put("materialName", diEx.getMName());
                    item.put("materialModel", diEx.getMModel());
                    item.put("materialStandard", diEx.getMStandard());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, diEx);
                    item.put("materialOther", materialOther);
                    item.put("materialColor", diEx.getMColor());
                    item.put("materialUnit", diEx.getMaterialUnit());
                    item.put("unitName", diEx.getUnitName());
                    item.put("outSum", OutSumRetail.add(OutSum));
                    item.put("inSum", InSumRetail.add(InSum));
                    item.put("outSumPrice", OutSumRetailPrice.add(OutSumPrice));
                    item.put("inSumPrice", InSumRetailPrice.add(InSumPrice));
                    item.put("outInSumPrice",OutInSumPrice);//实际销售金额
                    dataArray.add(item);
                }
            }
            map.put("rows", dataArray);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 获取单位
     * @param materialUnit
     * @param uName
     * @return
     */
    public String getUName(String materialUnit, String uName) {
        String unitName = null;
        if(!StringUtil.isEmpty(materialUnit)) {
            unitName = materialUnit;
        } else if(!StringUtil.isEmpty(uName)) {
            unitName = uName.substring(0,uName.indexOf(","));
        }
        return unitName;
    }

    /**
     * 库存预警报表
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/findStockWarningCount")
    public BaseResponseInfo findStockWarningCount(@RequestParam("currentPage") Integer currentPage,
                                                  @RequestParam("pageSize") Integer pageSize,
                                                  @RequestParam("materialParam") String materialParam,
                                                  @RequestParam("depotId") Long depotId,
                                                  @RequestParam("mpList") String mpList)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String[] mpArr = mpList.split(",");
            List<DepotItemStockWarningCount> list = depotItemService.findStockWarningCount((currentPage-1)*pageSize, pageSize,materialParam,depotId);
            //存放数据json数组
            if (null != list) {
                for (DepotItemStockWarningCount disw : list) {
                    DepotItemVo4WithInfoEx diEx = new DepotItemVo4WithInfoEx();
                    diEx.setMMfrs(disw.getMMfrs());
                    diEx.setMOtherField1(disw.getMOtherField1());
                    diEx.setMOtherField2(disw.getMOtherField2());
                    diEx.setMOtherField3(disw.getMOtherField3());
                    disw.setMaterialOther(getOtherInfo(mpArr, diEx));
                    disw.setMaterialUnit(getUName(disw.getMaterialUnit(), disw.getUnitName()));
                }
            }
            int total = depotItemService.findStockWarningCountTotal(materialParam,depotId);
            map.put("total", total);
            map.put("rows", list);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 统计采购或销售的总金额
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/buyOrSalePrice")
    public BaseResponseInfo buyOrSalePrice(HttpServletRequest request, HttpServletResponse response)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String message = "成功";
        try {
            List<String> list = Tools.getLastMonths(6);
            JSONArray buyPriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutPrice("入库", "采购", month);
                BigDecimal inPrice = depotItemService.inOrOutPrice("出库", "采购退货", month);
                obj.put("x", month);
                obj.put("y", outPrice.subtract(inPrice));
                buyPriceList.add(obj);
            }
            map.put("buyPriceList", buyPriceList);
            JSONArray salePriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutPrice("出库", "销售", month);
                BigDecimal inPrice = depotItemService.inOrOutPrice("入库", "销售退货", month);
                obj.put("x", month);
                obj.put("y", outPrice.subtract(inPrice));
                salePriceList.add(obj);
            }
            map.put("salePriceList", salePriceList);
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            e.printStackTrace();
            message = "统计失败";
            res.code = 500;
        }
        return res;
    }
}
