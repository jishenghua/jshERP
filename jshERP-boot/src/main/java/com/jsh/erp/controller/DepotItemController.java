package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.vo.DepotItemStockWarningCount;
import com.jsh.erp.datasource.vo.DepotItemVoBatchNumberList;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.depot.DepotService;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.materialExtend.MaterialExtendService;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.redis.RedisService;
import com.jsh.erp.service.role.RoleService;
import com.jsh.erp.service.unit.UnitService;
import com.jsh.erp.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import static com.jsh.erp.utils.Tools.getCenternTime;

/**
 * @author ji-sheng-hua 华夏erp
 */
@RestController
@RequestMapping(value = "/depotItem")
@Api(tags = {"单据明细"})
public class DepotItemController {
    private Logger logger = LoggerFactory.getLogger(DepotItemController.class);

    @Resource
    private DepotHeadService depotHeadService;

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private MaterialService materialService;

    @Resource
    private UnitService unitService;

    @Resource
    private DepotService depotService;

    @Resource
    private RoleService roleService;

    /**
     * 根据仓库和商品查询单据列表
     * @param mId
     * @param request
     * @return
     */
    @GetMapping(value = "/findDetailByDepotIdsAndMaterialId")
    @ApiOperation(value = "根据仓库和商品查询单据列表")
    public String findDetailByDepotIdsAndMaterialId(
            @RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
            @RequestParam(value = "depotIds",required = false) String depotIds,
            @RequestParam(value = "sku",required = false) String sku,
            @RequestParam("materialId") Long mId,
            HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        List<DepotItemVo4DetailByTypeAndMId> list = depotItemService.findDetailByDepotIdsAndMaterialIdList(depotIds, sku, mId, (currentPage-1)*pageSize, pageSize);
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
        objectMap.put("total", depotItemService.findDetailByDepotIdsAndMaterialIdCount(depotIds, sku, mId));
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
    @ApiOperation(value = "根据商品条码和仓库id查询库存数量")
    public BaseResponseInfo findStockByDepotAndBarCode(
            @RequestParam(value = "depotId",required = false) Long depotId,
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
                    if(materialVo4Unit.getUnitId()!=null) {
                        Unit unit = unitService.getUnit(materialVo4Unit.getUnitId());
                        String commodityUnit = materialVo4Unit.getCommodityUnit();
                        stock = unitService.parseStockByUnit(stock, unit, commodityUnit);
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

    /**
     * 单据明细列表
     * @param headerId
     * @param mpList
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/getDetailList")
    @ApiOperation(value = "单据明细列表")
    public BaseResponseInfo getDetailList(@RequestParam("headerId") Long headerId,
                              @RequestParam("mpList") String mpList,
                              @RequestParam(value = "linkType", required = false) String linkType,
                              @RequestParam(value = "isReadOnly", required = false) String isReadOnly,
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
                BigDecimal totalOperNumber = BigDecimal.ZERO;
                BigDecimal totalAllPrice = BigDecimal.ZERO;
                BigDecimal totalTaxMoney = BigDecimal.ZERO;
                BigDecimal totalTaxLastMoney = BigDecimal.ZERO;
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", diEx.getId());
                    item.put("materialExtendId", diEx.getMaterialExtendId() == null ? "" : diEx.getMaterialExtendId());
                    item.put("barCode", diEx.getBarCode());
                    item.put("name", diEx.getMName());
                    item.put("standard", diEx.getMStandard());
                    item.put("model", diEx.getMModel());
                    item.put("color", diEx.getMColor());
                    item.put("materialOther", getOtherInfo(mpArr, diEx));
                    BigDecimal stock;
                    Unit unitInfo = materialService.findUnit(diEx.getMaterialId()); //查询计量单位信息
                    String materialUnit = diEx.getMaterialUnit();
                    if(StringUtil.isNotEmpty(diEx.getSku())){
                        stock = depotItemService.getSkuStockByParam(diEx.getDepotId(),diEx.getMaterialExtendId(),null,null);
                    } else {
                        stock = depotItemService.getStockByParam(diEx.getDepotId(),diEx.getMaterialId(),null,null);
                        if (StringUtil.isNotEmpty(unitInfo.getName())) {
                            stock = unitService.parseStockByUnit(stock, unitInfo, materialUnit);
                        }
                    }
                    item.put("stock", stock);
                    item.put("unit", diEx.getMaterialUnit());
                    item.put("snList", diEx.getSnList());
                    item.put("batchNumber", diEx.getBatchNumber());
                    item.put("expirationDate", Tools.parseDateToStr(diEx.getExpirationDate()));
                    item.put("sku", diEx.getSku());
                    item.put("enableSerialNumber", diEx.getEnableSerialNumber());
                    item.put("enableBatchNumber", diEx.getEnableBatchNumber());
                    item.put("operNumber", diEx.getOperNumber());
                    item.put("basicNumber", diEx.getBasicNumber());
                    item.put("preNumber", diEx.getOperNumber()); //原数量
                    item.put("finishNumber", depotItemService.getFinishNumber(diEx.getMaterialExtendId(), diEx.getId(), diEx.getHeaderId(), unitInfo, materialUnit, linkType)); //已入库|已出库
                    item.put("purchaseDecimal", diEx.getPurchaseDecimal());  //采购价
                    if("basic".equals(linkType)) {
                        //正常情况显示金额，而以销定购的情况不能显示金额
                        item.put("unitPrice", diEx.getUnitPrice());
                        item.put("taxUnitPrice", diEx.getTaxUnitPrice());
                        item.put("allPrice", diEx.getAllPrice());
                        item.put("taxRate", diEx.getTaxRate());
                        item.put("taxMoney", diEx.getTaxMoney());
                        item.put("taxLastMoney", diEx.getTaxLastMoney());
                    }
                    item.put("remark", diEx.getRemark());
                    item.put("linkId", diEx.getLinkId());
                    item.put("depotId", diEx.getDepotId() == null ? "" : diEx.getDepotId());
                    item.put("depotName", diEx.getDepotId() == null ? "" : diEx.getDepotName());
                    item.put("anotherDepotId", diEx.getAnotherDepotId() == null ? "" : diEx.getAnotherDepotId());
                    item.put("anotherDepotName", diEx.getAnotherDepotId() == null ? "" : diEx.getAnotherDepotName());
                    item.put("mType", diEx.getMaterialType());
                    item.put("op", 1);
                    dataArray.add(item);
                    //合计数据汇总
                    totalOperNumber = totalOperNumber.add(diEx.getOperNumber()==null?BigDecimal.ZERO:diEx.getOperNumber());
                    totalAllPrice = totalAllPrice.add(diEx.getAllPrice()==null?BigDecimal.ZERO:diEx.getAllPrice());
                    totalTaxMoney = totalTaxMoney.add(diEx.getTaxMoney()==null?BigDecimal.ZERO:diEx.getTaxMoney());
                    totalTaxLastMoney = totalTaxLastMoney.add(diEx.getTaxLastMoney()==null?BigDecimal.ZERO:diEx.getTaxLastMoney());
                }
                if(StringUtil.isNotEmpty(isReadOnly) && "1".equals(isReadOnly)) {
                    JSONObject footItem = new JSONObject();
                    footItem.put("operNumber", totalOperNumber);
                    footItem.put("allPrice", totalAllPrice);
                    footItem.put("taxMoney", totalTaxMoney);
                    footItem.put("taxLastMoney", totalTaxLastMoney);
                    dataArray.add(footItem);
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
     * 进销存统计
     * @param currentPage
     * @param pageSize
     * @param depotIds
     * @param monthTime
     * @param materialParam
     * @param mpList
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/findByAll")
    @ApiOperation(value = "查找所有的明细")
    public BaseResponseInfo findByAll(@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "depotIds",required = false) String depotIds,
                                      @RequestParam("monthTime") String monthTime,
                                      @RequestParam("materialParam") String materialParam,
                                      @RequestParam("mpList") String mpList,
                                      HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            String timeA = Tools.firstDayOfMonth(monthTime) + BusinessConstants.DAY_FIRST_TIME;
            String timeB = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
            List<Long> depotList = parseListByDepotIds(depotIds);
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    timeB,(currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(StringUtil.toNull(materialParam), timeB);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
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
                    BigDecimal prevSum = depotItemService.getStockByParamWithDepotList(depotList,mId,null,timeA);
                    Map<String,BigDecimal> intervalMap = depotItemService.getIntervalMapByParamWithDepotList(depotList,mId,timeA,timeB);
                    BigDecimal inSum = intervalMap.get("inSum");
                    BigDecimal outSum = intervalMap.get("outSum");
                    BigDecimal thisSum = prevSum.add(inSum).subtract(outSum);
                    item.put("prevSum", prevSum);
                    item.put("inSum", inSum);
                    item.put("outSum", outSum);
                    item.put("thisSum", thisSum);
                    item.put("unitPrice", diEx.getPurchaseDecimal());
                    item.put("thisAllPrice", thisSum.multiply(diEx.getPurchaseDecimal()));
                    dataArray.add(item);
                }
            }
            map.put("rows", dataArray);
            res.code = 200;
            res.data = map;
        } catch (BusinessRunTimeException e) {
            res.code = e.getCode();
            res.data = e.getData().get("message");
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 进销存统计总计金额
     * @param depotIds
     * @param monthTime
     * @param materialParam
     * @param request
     * @return
     */
    @GetMapping(value = "/totalCountMoney")
    @ApiOperation(value = "统计总计金额")
    public BaseResponseInfo totalCountMoney(@RequestParam(value = "depotIds",required = false) String depotIds,
                                            @RequestParam("monthTime") String monthTime,
                                            @RequestParam("materialParam") String materialParam,
                                            HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            String endTime = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
            List<Long> depotList = parseListByDepotIds(depotIds);
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    endTime, null, null);
            BigDecimal thisAllPrice = BigDecimal.ZERO;
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    Long mId = diEx.getMId();
                    BigDecimal thisSum = depotItemService.getStockByParamWithDepotList(depotList,mId,null,endTime);
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
        } catch (BusinessRunTimeException e) {
            res.code = e.getCode();
            res.data = e.getData().get("message");
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    private List<Long> parseListByDepotIds(@RequestParam("depotIds") String depotIds) throws Exception {
        List<Long> depotList = new ArrayList<>();
        if(StringUtil.isNotEmpty(depotIds)) {
            depotList = StringUtil.strToLongList(depotIds);
        } else {
            //未选择仓库时默认为当前用户有权限的仓库
            JSONArray depotArr = depotService.findDepotByCurrentUser();
            for(Object obj: depotArr) {
                JSONObject object = JSONObject.parseObject(obj.toString());
                depotList.add(object.getLong("id"));
            }
            //如果有权限的仓库数量太多则提示要选择仓库
            if(depotList.size()>20) {
                throw new BusinessRunTimeException(ExceptionConstants.REPORT_TWO_MANY_DEPOT_FAILED_CODE,
                        ExceptionConstants.REPORT_TWO_MANY_DEPOT_FAILED_MSG);
            }
        }
        return depotList;
    }

    /**
     * 进货统计
     * @param currentPage
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param materialParam
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/buyIn")
    @ApiOperation(value = "进货统计")
    public BaseResponseInfo buyIn(@RequestParam("currentPage") Integer currentPage,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("beginTime") String beginTime,
                                  @RequestParam("endTime") String endTime,
                                  @RequestParam("materialParam") String materialParam,
                                  @RequestParam("mpList") String mpList,
                                  @RequestParam(value = "roleType", required = false) String roleType,
                                  HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        beginTime = Tools.parseDayToTime(beginTime, BusinessConstants.DAY_FIRST_TIME);
        endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
        try {
            String [] creatorArray = depotHeadService.getCreatorArray(roleType);
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.getListWithBugOrSale(StringUtil.toNull(materialParam),
                    "buy", beginTime, endTime, creatorArray, (currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.getListWithBugOrSaleCount(StringUtil.toNull(materialParam),
                    "buy", beginTime, endTime, creatorArray);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    BigDecimal InSum = depotItemService.buyOrSale("入库", "采购", diEx.getMId(), beginTime, endTime, creatorArray, "number");
                    BigDecimal OutSum = depotItemService.buyOrSale("出库", "采购退货", diEx.getMId(), beginTime, endTime, creatorArray, "number");
                    BigDecimal InSumPrice = depotItemService.buyOrSale("入库", "采购", diEx.getMId(), beginTime, endTime, creatorArray, "price");
                    BigDecimal OutSumPrice = depotItemService.buyOrSale("出库", "采购退货", diEx.getMId(), beginTime, endTime, creatorArray, "price");
                    BigDecimal InOutSumPrice = InSumPrice.subtract(OutSumPrice);
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
                    item.put("inOutSumPrice",InOutSumPrice);//实际采购金额
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
     * @param beginTime
     * @param endTime
     * @param materialParam
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/saleOut")
    @ApiOperation(value = "销售统计")
    public BaseResponseInfo saleOut(@RequestParam("currentPage") Integer currentPage,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("beginTime") String beginTime,
                                  @RequestParam("endTime") String endTime,
                                  @RequestParam("materialParam") String materialParam,
                                  @RequestParam("mpList") String mpList,
                                  @RequestParam(value = "roleType", required = false) String roleType,
                                  HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        beginTime = Tools.parseDayToTime(beginTime, BusinessConstants.DAY_FIRST_TIME);
        endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
        try {
            String [] creatorArray = depotHeadService.getCreatorArray(roleType);
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.getListWithBugOrSale(StringUtil.toNull(materialParam),
                    "sale", beginTime, endTime, creatorArray, (currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.getListWithBugOrSaleCount(StringUtil.toNull(materialParam),
                    "sale", beginTime, endTime, creatorArray);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    BigDecimal OutSumRetail = depotItemService.buyOrSale("出库", "零售", diEx.getMId(), beginTime, endTime, creatorArray,"number");
                    BigDecimal OutSum = depotItemService.buyOrSale("出库", "销售", diEx.getMId(), beginTime, endTime, creatorArray,"number");
                    BigDecimal InSumRetail = depotItemService.buyOrSale("入库", "零售退货", diEx.getMId(), beginTime, endTime, creatorArray,"number");
                    BigDecimal InSum = depotItemService.buyOrSale("入库", "销售退货", diEx.getMId(), beginTime, endTime, creatorArray,"number");
                    BigDecimal OutSumRetailPrice = depotItemService.buyOrSale("出库", "零售", diEx.getMId(), beginTime, endTime, creatorArray,"price");
                    BigDecimal OutSumPrice = depotItemService.buyOrSale("出库", "销售", diEx.getMId(), beginTime, endTime, creatorArray,"price");
                    BigDecimal InSumRetailPrice = depotItemService.buyOrSale("入库", "零售退货", diEx.getMId(), beginTime, endTime, creatorArray,"price");
                    BigDecimal InSumPrice = depotItemService.buyOrSale("入库", "销售退货", diEx.getMId(), beginTime, endTime, creatorArray,"price");
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
        if(StringUtil.isNotEmpty(materialUnit)) {
            unitName = materialUnit;
        } else if(StringUtil.isNotEmpty(uName)) {
            unitName = uName;
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
    @ApiOperation(value = "库存预警报表")
    public BaseResponseInfo findStockWarningCount(@RequestParam("currentPage") Integer currentPage,
                                                  @RequestParam("pageSize") Integer pageSize,
                                                  @RequestParam("materialParam") String materialParam,
                                                  @RequestParam(value = "depotId", required = false) Long depotId,
                                                  @RequestParam("mpList") String mpList)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Long> depotList = new ArrayList<>();
            if(depotId != null) {
                depotList.add(depotId);
            } else {
                //未选择仓库时默认为当前用户有权限的仓库
                JSONArray depotArr = depotService.findDepotByCurrentUser();
                for(Object obj: depotArr) {
                    JSONObject object = JSONObject.parseObject(obj.toString());
                    depotList.add(object.getLong("id"));
                }
            }
            String[] mpArr = mpList.split(",");
            List<DepotItemStockWarningCount> list = depotItemService.findStockWarningCount((currentPage-1)*pageSize, pageSize, materialParam, depotList);
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
                    if(disw.getCurrentNumber().compareTo(disw.getLowSafeStock())<0) {
                        disw.setLowCritical(disw.getLowSafeStock().subtract(disw.getCurrentNumber()));
                        disw.setHighCritical(BigDecimal.ZERO);
                    }
                    if(disw.getCurrentNumber().compareTo(disw.getHighSafeStock())>0) {
                        disw.setLowCritical(BigDecimal.ZERO);
                        disw.setHighCritical(disw.getCurrentNumber().subtract(disw.getHighSafeStock()));
                    }
                }
            }
            int total = depotItemService.findStockWarningCountTotal(materialParam, depotList);
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
     * 统计采购、销售、零售的总金额
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/buyOrSalePrice")
    @ApiOperation(value = "统计采购、销售、零售的总金额")
    public BaseResponseInfo buyOrSalePrice(@RequestParam(value = "roleType", required = false) String roleType,
                                           HttpServletRequest request, HttpServletResponse response)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<String> list = Tools.getLastMonths(6);
            JSONArray buyPriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutPrice("入库", "采购", month, roleType);
                BigDecimal inPrice = depotItemService.inOrOutPrice("出库", "采购退货", month, roleType);
                obj.put("x", month);
                obj.put("y", roleService.parsePriceByLimit(outPrice.subtract(inPrice), "buy", "***", request));
                buyPriceList.add(obj);
            }
            map.put("buyPriceList", buyPriceList);
            JSONArray salePriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutPrice("出库", "销售", month, roleType);
                BigDecimal inPrice = depotItemService.inOrOutPrice("入库", "销售退货", month, roleType);
                obj.put("x", month);
                obj.put("y", roleService.parsePriceByLimit(outPrice.subtract(inPrice), "sale", "***", request));
                salePriceList.add(obj);
            }
            map.put("salePriceList", salePriceList);
            JSONArray retailPriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutRetailPrice("出库", "零售", month, roleType);
                BigDecimal inPrice = depotItemService.inOrOutRetailPrice("入库", "零售退货", month, roleType);
                obj.put("x", month);
                obj.put("y", roleService.parsePriceByLimit(outPrice.subtract(inPrice), "retail", "***", request));
                retailPriceList.add(obj);
            }
            map.put("retailPriceList", retailPriceList);
            res.code = 200;
            res.data = map;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "统计失败";
        }
        return res;
    }

    /**
     * 获取批次商品列表信息
     * @param request
     * @return
     */
    @GetMapping(value = "/getBatchNumberList")
    @ApiOperation(value = "获取批次商品列表信息")
    public BaseResponseInfo getBatchNumberList(@RequestParam("name") String name,
                                               @RequestParam("depotItemId") Long depotItemId,
                                               @RequestParam("depotId") Long depotId,
                                               @RequestParam("barCode") String barCode,
                                               @RequestParam(value = "batchNumber", required = false) String batchNumber,
                                               HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            String number = "";
            if(depotItemId != null) {
                DepotItem depotItem = depotItemService.getDepotItem(depotItemId);
                number = depotHeadService.getDepotHead(depotItem.getHeaderId()).getNumber();
            }
            List<DepotItemVoBatchNumberList> reslist = new ArrayList<>();
            List<DepotItemVoBatchNumberList> list = depotItemService.getBatchNumberList(number, name, depotId, barCode, batchNumber);
            for(DepotItemVoBatchNumberList bn: list) {
                if(bn.getTotalNum()!=null && bn.getTotalNum().compareTo(BigDecimal.ZERO)>0) {
                    reslist.add(bn);
                }
                bn.setExpirationDateStr(Tools.parseDateToStr(bn.getExpirationDate()));
            }
            map.put("rows", reslist);
            map.put("total", reslist.size());
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
