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
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.service.role.RoleService;
import com.jsh.erp.service.systemConfig.SystemConfigService;
import com.jsh.erp.service.unit.UnitService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

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

    @Resource
    private UserService userService;

    @Resource
    private SystemConfigService systemConfigService;

    @Value(value="${file.uploadType}")
    private Long fileUploadType;

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
            @RequestParam(value = "batchNumber",required = false) String batchNumber,
            @RequestParam(value = "number",required = false) String number,
            @RequestParam(value = "beginTime",required = false) String beginTime,
            @RequestParam(value = "endTime",required = false) String endTime,
            @RequestParam("materialId") Long mId,
            HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        if(StringUtil.isNotEmpty(beginTime)) {
            beginTime = beginTime + BusinessConstants.DAY_FIRST_TIME;
        }
        if(StringUtil.isNotEmpty(endTime)) {
            endTime = endTime + BusinessConstants.DAY_LAST_TIME;
        }
        Boolean forceFlag = systemConfigService.getForceApprovalFlag();
        Boolean inOutManageFlag = systemConfigService.getInOutManageFlag();
        List<DepotItemVo4DetailByTypeAndMId> list = depotItemService.findDetailByDepotIdsAndMaterialIdList(depotIds, forceFlag, inOutManageFlag, sku,
                batchNumber, StringUtil.toNull(number), beginTime, endTime, mId, (currentPage-1)*pageSize, pageSize);
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
        objectMap.put("total", depotItemService.findDetailByDepotIdsAndMaterialIdCount(depotIds, forceFlag, inOutManageFlag, sku,
                batchNumber, StringUtil.toNull(number), beginTime, endTime, mId));
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
                    stock = depotItemService.getCurrentStockByParam(depotId, materialVo4Unit.getId());
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
            Long userId = userService.getUserId(request);
            String priceLimit = userService.getRoleTypeByUserId(userId).getPriceLimit();
            List<DepotItemVo4WithInfoEx> dataList = new ArrayList<>();
            String billCategory = depotHeadService.getBillCategory(depotHeadService.getDepotHead(headerId).getSubType());
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
                BigDecimal totalWeight = BigDecimal.ZERO;
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", diEx.getId());
                    item.put("materialExtendId", diEx.getMaterialExtendId() == null ? "" : diEx.getMaterialExtendId());
                    item.put("barCode", diEx.getBarCode());
                    item.put("name", diEx.getMName());
                    item.put("standard", diEx.getMStandard());
                    item.put("model", diEx.getMModel());
                    item.put("color", diEx.getMColor());
                    item.put("materialOther", depotItemService.getOtherInfo(mpArr, diEx));
                    BigDecimal stock;
                    Unit unitInfo = materialService.findUnit(diEx.getMaterialId()); //查询计量单位信息
                    String materialUnit = diEx.getMaterialUnit();
                    if(StringUtil.isNotEmpty(diEx.getSku())){
                        stock = depotItemService.getSkuStockByParam(diEx.getDepotId(),diEx.getMaterialExtendId(),null,null);
                    } else {
                        stock = depotItemService.getCurrentStockByParam(diEx.getDepotId(),diEx.getMaterialId());
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
                    item.put("purchaseDecimal", roleService.parseBillPriceByLimit(diEx.getPurchaseDecimal(), billCategory, priceLimit, request));  //采购价
                    if("basic".equals(linkType) || "1".equals(isReadOnly)) {
                        //正常情况显示金额，而以销定购的情况不能显示金额
                        item.put("unitPrice", roleService.parseBillPriceByLimit(diEx.getUnitPrice(), billCategory, priceLimit, request));
                        item.put("taxUnitPrice", roleService.parseBillPriceByLimit(diEx.getTaxUnitPrice(), billCategory, priceLimit, request));
                        item.put("allPrice", roleService.parseBillPriceByLimit(diEx.getAllPrice(), billCategory, priceLimit, request));
                        item.put("taxRate", roleService.parseBillPriceByLimit(diEx.getTaxRate(), billCategory, priceLimit, request));
                        item.put("taxMoney", roleService.parseBillPriceByLimit(diEx.getTaxMoney(), billCategory, priceLimit, request));
                        item.put("taxLastMoney", roleService.parseBillPriceByLimit(diEx.getTaxLastMoney(), billCategory, priceLimit, request));
                    }
                    BigDecimal allWeight = diEx.getBasicNumber()==null||diEx.getWeight()==null?BigDecimal.ZERO:diEx.getBasicNumber().multiply(diEx.getWeight());
                    item.put("weight", allWeight);
                    item.put("remark", diEx.getRemark());
                    item.put("imgName", diEx.getImgName());
                    if(fileUploadType == 2) {
                        item.put("imgSmall", "small");
                        item.put("imgLarge", "large");
                    } else {
                        item.put("imgSmall", "");
                        item.put("imgLarge", "");
                    }
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
                    totalWeight = totalWeight.add(allWeight);
                }
                if(StringUtil.isNotEmpty(isReadOnly) && "1".equals(isReadOnly)) {
                    JSONObject footItem = new JSONObject();
                    footItem.put("operNumber", totalOperNumber);
                    footItem.put("allPrice", roleService.parseBillPriceByLimit(totalAllPrice, billCategory, priceLimit, request));
                    footItem.put("taxMoney", roleService.parseBillPriceByLimit(totalTaxMoney, billCategory, priceLimit, request));
                    footItem.put("taxLastMoney", roleService.parseBillPriceByLimit(totalTaxLastMoney, billCategory, priceLimit, request));
                    footItem.put("weight", totalWeight);
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
                                      @RequestParam(value = "categoryId", required = false) Long categoryId,
                                      @RequestParam("monthTime") String monthTime,
                                      @RequestParam("materialParam") String materialParam,
                                      @RequestParam("mpList") String mpList,
                                      HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Long> categoryIdList = new ArrayList<>();
            if(categoryId != null){
                categoryIdList = materialService.getListByParentId(categoryId);
            }
            String timeA = Tools.firstDayOfMonth(monthTime) + BusinessConstants.DAY_FIRST_TIME;
            String timeB = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
            List<Long> depotList = parseListByDepotIds(depotIds);
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    categoryIdList, timeB,(currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(StringUtil.toNull(materialParam), categoryIdList, timeB);
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
                    String materialOther = depotItemService.getOtherInfo(mpArr, diEx);
                    item.put("materialOther", materialOther);
                    item.put("materialColor", diEx.getMColor());
                    item.put("unitId", diEx.getUnitId());
                    item.put("unitName", null!=diEx.getUnitId() ? diEx.getMaterialUnit()+"[多单位]" : diEx.getMaterialUnit());
                    BigDecimal prevSum = depotItemService.getStockByParamWithDepotList(depotList,mId,null,timeA);
                    Map<String,BigDecimal> intervalMap = depotItemService.getIntervalMapByParamWithDepotList(depotList,mId,timeA,timeB);
                    BigDecimal inSum = intervalMap.get("inSum");
                    BigDecimal outSum = intervalMap.get("outSum");
                    BigDecimal thisSum = prevSum.add(inSum).subtract(outSum);
                    item.put("prevSum", prevSum);
                    item.put("inSum", inSum);
                    item.put("outSum", outSum);
                    item.put("thisSum", thisSum);
                    //将小单位的库存换算为大单位的库存
                    item.put("bigUnitStock", materialService.getBigUnitStock(thisSum, diEx.getUnitId()));
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
                                            @RequestParam(value = "categoryId", required = false) Long categoryId,
                                            @RequestParam("monthTime") String monthTime,
                                            @RequestParam("materialParam") String materialParam,
                                            HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Long> categoryIdList = new ArrayList<>();
            if(categoryId != null){
                categoryIdList = materialService.getListByParentId(categoryId);
            }
            String endTime = Tools.lastDayOfMonth(monthTime) + BusinessConstants.DAY_LAST_TIME;
            List<Long> depotList = parseListByDepotIds(depotIds);
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(StringUtil.toNull(materialParam),
                    categoryIdList, endTime, null, null);
            BigDecimal thisAllStock = BigDecimal.ZERO;
            BigDecimal thisAllPrice = BigDecimal.ZERO;
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    Long mId = diEx.getMId();
                    BigDecimal thisSum = depotItemService.getStockByParamWithDepotList(depotList,mId,null,endTime);
                    thisAllStock = thisAllStock.add(thisSum);
                    BigDecimal unitPrice = diEx.getPurchaseDecimal();
                    if(unitPrice == null) {
                        unitPrice = BigDecimal.ZERO;
                    }
                    thisAllPrice = thisAllPrice.add(thisSum.multiply(unitPrice));
                }
            }
            map.put("totalStock", thisAllStock);
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
     * 采购统计
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
    @ApiOperation(value = "采购统计")
    public BaseResponseInfo buyIn(@RequestParam("currentPage") Integer currentPage,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("beginTime") String beginTime,
                                  @RequestParam("endTime") String endTime,
                                  @RequestParam(value = "organId", required = false) Long organId,
                                  @RequestParam(value = "depotId", required = false) Long depotId,
                                  @RequestParam("materialParam") String materialParam,
                                  @RequestParam("mpList") String mpList,
                                  HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        beginTime = Tools.parseDayToTime(beginTime, BusinessConstants.DAY_FIRST_TIME);
        endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
        try {
            String [] creatorArray = depotHeadService.getCreatorArray();
            String [] organArray = null;
            List<Long> depotList = depotService.parseDepotList(depotId);
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.getListWithBugOrSale(StringUtil.toNull(materialParam),
                    "buy", beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, (currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.getListWithBugOrSaleCount(StringUtil.toNull(materialParam),
                    "buy", beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    BigDecimal InSum = depotItemService.buyOrSale("入库", "采购", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "number");
                    BigDecimal OutSum = depotItemService.buyOrSale("出库", "采购退货", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "number");
                    BigDecimal InSumPrice = depotItemService.buyOrSale("入库", "采购", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
                    BigDecimal OutSumPrice = depotItemService.buyOrSale("出库", "采购退货", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
                    BigDecimal InOutSumPrice = InSumPrice.subtract(OutSumPrice);
                    item.put("barCode", diEx.getBarCode());
                    item.put("materialName", diEx.getMName());
                    item.put("materialModel", diEx.getMModel());
                    item.put("materialStandard", diEx.getMStandard());
                    //扩展信息
                    String materialOther = depotItemService.getOtherInfo(mpArr, diEx);
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
            BigDecimal inSumPriceTotal = depotItemService.buyOrSale("入库", "采购", null, beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
            BigDecimal outSumPriceTotal = depotItemService.buyOrSale("出库", "采购退货", null, beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
            BigDecimal realityPriceTotal = inSumPriceTotal.subtract(outSumPriceTotal);
            map.put("rows", dataArray);
            map.put("realityPriceTotal", realityPriceTotal);
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
     * 零售统计
     * @param currentPage
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param materialParam
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/retailOut")
    @ApiOperation(value = "零售统计")
    public BaseResponseInfo retailOut(@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("beginTime") String beginTime,
                                      @RequestParam("endTime") String endTime,
                                      @RequestParam(value = "organId", required = false) Long organId,
                                      @RequestParam(value = "depotId", required = false) Long depotId,
                                      @RequestParam(value = "organizationId", required = false) Long organizationId,
                                      @RequestParam("materialParam") String materialParam,
                                      @RequestParam("mpList") String mpList,
                                      HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        beginTime = Tools.parseDayToTime(beginTime, BusinessConstants.DAY_FIRST_TIME);
        endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
        try {
            String [] creatorArray = depotHeadService.getCreatorArray();
            if(creatorArray == null && organizationId != null) {
                creatorArray = depotHeadService.getCreatorArrayByOrg(organizationId);
            }
            String [] organArray = null;
            List<Long> depotList = depotService.parseDepotList(depotId);
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.getListWithBugOrSale(StringUtil.toNull(materialParam),
                    "sale", beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, (currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.getListWithBugOrSaleCount(StringUtil.toNull(materialParam),
                    "sale", beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    BigDecimal OutSumRetail = depotItemService.buyOrSale("出库", "零售", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "number");
                    BigDecimal InSumRetail = depotItemService.buyOrSale("入库", "零售退货", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "number");
                    BigDecimal OutSumRetailPrice = depotItemService.buyOrSale("出库", "零售", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
                    BigDecimal InSumRetailPrice = depotItemService.buyOrSale("入库", "零售退货", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
                    BigDecimal OutInSumPrice = OutSumRetailPrice.subtract(InSumRetailPrice);
                    item.put("barCode", diEx.getBarCode());
                    item.put("materialName", diEx.getMName());
                    item.put("materialModel", diEx.getMModel());
                    item.put("materialStandard", diEx.getMStandard());
                    //扩展信息
                    String materialOther = depotItemService.getOtherInfo(mpArr, diEx);
                    item.put("materialOther", materialOther);
                    item.put("materialColor", diEx.getMColor());
                    item.put("materialUnit", diEx.getMaterialUnit());
                    item.put("unitName", diEx.getUnitName());
                    item.put("outSum", OutSumRetail);
                    item.put("inSum", InSumRetail);
                    item.put("outSumPrice", OutSumRetailPrice);
                    item.put("inSumPrice", InSumRetailPrice);
                    item.put("outInSumPrice",OutInSumPrice);//实际销售金额
                    dataArray.add(item);
                }
            }
            BigDecimal outSumPriceTotal = depotItemService.buyOrSale("出库", "零售", null, beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
            BigDecimal inSumPriceTotal = depotItemService.buyOrSale("入库", "零售退货", null, beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
            BigDecimal realityPriceTotal = outSumPriceTotal.subtract(inSumPriceTotal);
            map.put("rows", dataArray);
            map.put("realityPriceTotal", realityPriceTotal);
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
                                    @RequestParam(value = "organId", required = false) Long organId,
                                    @RequestParam(value = "depotId", required = false) Long depotId,
                                    @RequestParam("materialParam") String materialParam,
                                    @RequestParam("mpList") String mpList,
                                    HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        beginTime = Tools.parseDayToTime(beginTime, BusinessConstants.DAY_FIRST_TIME);
        endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
        try {
            String [] creatorArray = depotHeadService.getCreatorArray();
            String [] organArray = depotHeadService.getOrganArray("销售", "");
            List<Long> depotList = depotService.parseDepotList(depotId);
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.getListWithBugOrSale(StringUtil.toNull(materialParam),
                    "sale", beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, (currentPage-1)*pageSize, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.getListWithBugOrSaleCount(StringUtil.toNull(materialParam),
                    "sale", beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    BigDecimal OutSum = depotItemService.buyOrSale("出库", "销售", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "number");
                    BigDecimal InSum = depotItemService.buyOrSale("入库", "销售退货", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "number");
                    BigDecimal OutSumPrice = depotItemService.buyOrSale("出库", "销售", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
                    BigDecimal InSumPrice = depotItemService.buyOrSale("入库", "销售退货", diEx.getMId(), beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
                    BigDecimal OutInSumPrice = OutSumPrice.subtract(InSumPrice);
                    item.put("barCode", diEx.getBarCode());
                    item.put("materialName", diEx.getMName());
                    item.put("materialModel", diEx.getMModel());
                    item.put("materialStandard", diEx.getMStandard());
                    //扩展信息
                    String materialOther = depotItemService.getOtherInfo(mpArr, diEx);
                    item.put("materialOther", materialOther);
                    item.put("materialColor", diEx.getMColor());
                    item.put("materialUnit", diEx.getMaterialUnit());
                    item.put("unitName", diEx.getUnitName());
                    item.put("outSum", OutSum);
                    item.put("inSum", InSum);
                    item.put("outSumPrice", OutSumPrice);
                    item.put("inSumPrice", InSumPrice);
                    item.put("outInSumPrice",OutInSumPrice);//实际销售金额
                    dataArray.add(item);
                }
            }
            BigDecimal outSumPriceTotal = depotItemService.buyOrSale("出库", "销售", null, beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
            BigDecimal inSumPriceTotal = depotItemService.buyOrSale("入库", "销售退货", null, beginTime, endTime, creatorArray, organId, organArray, depotList, forceFlag, "price");
            BigDecimal realityPriceTotal = outSumPriceTotal.subtract(inSumPriceTotal);
            map.put("rows", dataArray);
            map.put("realityPriceTotal", realityPriceTotal);
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
                    disw.setMaterialOther(depotItemService.getOtherInfo(mpArr, diEx));
                    disw.setMaterialUnit(getUName(disw.getMaterialUnit(), disw.getUnitName()));
                    if(null!=disw.getLowSafeStock() && disw.getCurrentNumber().compareTo(disw.getLowSafeStock())<0) {
                        disw.setLowCritical(disw.getLowSafeStock().subtract(disw.getCurrentNumber()));
                    }
                    if(null!=disw.getHighSafeStock() && disw.getCurrentNumber().compareTo(disw.getHighSafeStock())>0) {
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
    public BaseResponseInfo buyOrSalePrice(HttpServletRequest request,
                                           HttpServletResponse response)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long userId = userService.getUserId(request);
            String priceLimit = userService.getRoleTypeByUserId(userId).getPriceLimit();
            List<String> list = Tools.getLastMonths(6);
            JSONArray buyPriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutPrice("入库", "采购", month);
                BigDecimal inPrice = depotItemService.inOrOutPrice("出库", "采购退货", month);
                obj.put("x", month);
                obj.put("y", roleService.parseHomePriceByLimit(outPrice.subtract(inPrice), "buy", priceLimit, "***", request));
                buyPriceList.add(obj);
            }
            map.put("buyPriceList", buyPriceList);
            JSONArray salePriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutPrice("出库", "销售", month);
                BigDecimal inPrice = depotItemService.inOrOutPrice("入库", "销售退货", month);
                obj.put("x", month);
                obj.put("y", roleService.parseHomePriceByLimit(outPrice.subtract(inPrice), "sale", priceLimit, "***", request));
                salePriceList.add(obj);
            }
            map.put("salePriceList", salePriceList);
            JSONArray retailPriceList = new JSONArray();
            for(String month: list) {
                JSONObject obj = new JSONObject();
                BigDecimal outPrice = depotItemService.inOrOutRetailPrice("出库", "零售", month);
                BigDecimal inPrice = depotItemService.inOrOutRetailPrice("入库", "零售退货", month);
                obj.put("x", month);
                obj.put("y", roleService.parseHomePriceByLimit(outPrice.subtract(inPrice), "retail", priceLimit, "***", request));
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
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            Boolean inOutManageFlag = systemConfigService.getInOutManageFlag();
            List<DepotItemVoBatchNumberList> list = depotItemService.getBatchNumberList(number, name, depotId, barCode,
                    batchNumber, forceFlag, inOutManageFlag);
            map.put("rows", list);
            map.put("total", list.size());
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
     * Excel导入明细
     * @param file
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importItemExcel")
    public BaseResponseInfo importItemExcel(MultipartFile file,
                                            @RequestParam(required = false, value = "prefixNo") String prefixNo,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> data = new HashMap<>();
        String message = "";
        try {
            String barCodes = "";
            //文件合法性校验
            Sheet src = null;
            try {
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                src = workbook.getSheet(0);
            } catch (Exception e) {
                message = "导入文件不合法，请检查";
                data.put("message", message);
                res.code = 400;
                res.data = data;
            }
            if(src.getRows()>1000) {
                message = "导入失败，明细不能超出1000条";
                res.code = 500;
                data.put("message", message);
                res.data = data;
            } else {
                List<Map<String, String>> detailList = new ArrayList<>();
                for (int i = 2; i < src.getRows(); i++) {
                    String depotName = "", barCode = "", num = "", unitPrice = "", taxRate = "", remark = "";
                    if("CGDD".equals(prefixNo) || "XSDD".equals(prefixNo)) {
                        barCode = ExcelUtils.getContent(src, i, 0);
                        num = ExcelUtils.getContent(src, i, 2);
                        unitPrice = ExcelUtils.getContent(src, i, 3);
                        taxRate = ExcelUtils.getContent(src, i, 4);
                        remark = ExcelUtils.getContent(src, i, 5);
                    }
                    if("CGRK".equals(prefixNo) || "XSCK".equals(prefixNo)) {
                        depotName = ExcelUtils.getContent(src, i, 0);
                        barCode = ExcelUtils.getContent(src, i, 1);
                        num = ExcelUtils.getContent(src, i, 3);
                        unitPrice = ExcelUtils.getContent(src, i, 4);
                        taxRate = ExcelUtils.getContent(src, i, 5);
                        remark = ExcelUtils.getContent(src, i, 6);
                    }
                    if("QTRK".equals(prefixNo) || "QTCK".equals(prefixNo)) {
                        depotName = ExcelUtils.getContent(src, i, 0);
                        barCode = ExcelUtils.getContent(src, i, 1);
                        num = ExcelUtils.getContent(src, i, 3);
                        unitPrice = ExcelUtils.getContent(src, i, 4);
                        remark = ExcelUtils.getContent(src, i, 5);
                    }
                    Map<String, String> materialMap = new HashMap<>();
                    materialMap.put("depotName", depotName);
                    materialMap.put("barCode", barCode);
                    materialMap.put("num", num);
                    materialMap.put("unitPrice", unitPrice);
                    materialMap.put("taxRate", taxRate);
                    materialMap.put("remark", remark);
                    detailList.add(materialMap);
                    barCodes += "'" + barCode + "',";
                }
                if (StringUtil.isNotEmpty(barCodes)) {
                    barCodes = barCodes.substring(0, barCodes.length() - 1);
                }
                JSONObject map = depotItemService.parseMapByExcelData(barCodes, detailList, prefixNo);
                if (map != null) {
                    res.code = 200;
                } else {
                    res.code = 500;
                }
                res.data = map;
            }
        } catch (BusinessRunTimeException e) {
            res.code = 500;
            data.put("message", e.getData().get("message"));
            res.data = data;
        } catch (Exception e) {
            e.printStackTrace();
            message = "导入失败，请检查表格内容";
            res.code = 500;
            data.put("message", message);
            res.data = data;
        }
        return res;
    }
}
