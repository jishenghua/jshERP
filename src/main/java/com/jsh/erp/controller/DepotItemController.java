package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.service.depotItem.DepotItemService;
import com.jsh.erp.service.material.MaterialService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
public class DepotItemController {
    private Logger logger = LoggerFactory.getLogger(DepotItemController.class);

    @Resource
    private DepotItemService depotItemService;

    @Resource
    private MaterialService materialService;

    /**
     * 根据材料信息获取
     * @param materialParam  商品参数
     * @param depotIds  拥有的仓库信息
     * @param request
     * @return
     */
    @GetMapping(value = "/getHeaderIdByMaterial")
    public BaseResponseInfo getHeaderIdByMaterial(@RequestParam("materialParam") String materialParam,
                                                  @RequestParam("depotIds") String depotIds,
                                                  HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<DepotItemVo4HeaderId> depotItemList = depotItemService.getHeaderIdByMaterial(materialParam, depotIds);
            String allReturn = "";
            if (depotItemList != null) {
                for (DepotItemVo4HeaderId d : depotItemList) {
                    Long dl = d.getHeaderid(); //获取对象
                    allReturn = allReturn + dl.toString() + ",";
                }
            }
            allReturn = allReturn.substring(0, allReturn.length() - 1);
            if (allReturn.equals("null")) {
                allReturn = "";
            }
            res.code = 200;
            res.data = allReturn;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

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
            @RequestParam("materialId") String mId, HttpServletRequest request) {
        Map<String, String> parameterMap = ParamUtils.requestToMap(request);
        parameterMap.put("mId", mId);
        PageQueryInfo queryInfo = new PageQueryInfo();
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
                item.put("Number", d.getNumber()); //商品编号
                item.put("Type", d.getNewtype()); //进出类型
                item.put("BasicNumber", d.getBnum()); //数量
                item.put("OperTime", d.getOtime()); //时间
                dataArray.add(item);
            }
        }
        objectMap.put("page", queryInfo);
        if (list == null) {
            queryInfo.setRows(new ArrayList<Object>());
            queryInfo.setTotal(0);
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
        queryInfo.setRows(dataArray);
        queryInfo.setTotal(depotItemService.findDetailByTypeAndMaterialIdCounts(parameterMap));
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 根据商品id和仓库id查询库存数量
     * @param pageSize
     * @param currentPage
     * @param mId
     * @param request
     * @return
     */
    @GetMapping(value = "/findStockNumById")
    public String findStockNumById(
            @RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
            @RequestParam("projectId") Integer pid,
            @RequestParam("materialId") String mId,
            @RequestParam("monthTime") String monthTime,
            HttpServletRequest request) {
        Map<String, String> parameterMap = ParamUtils.requestToMap(request);
        parameterMap.put("mId", mId);
        parameterMap.put("monthTime", monthTime);
        PageQueryInfo queryInfo = new PageQueryInfo();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (pageSize != null && pageSize <= 0) {
            pageSize = 10;
        }
        String offset = ParamUtils.getPageOffset(currentPage, pageSize);
        if (StringUtil.isNotEmpty(offset)) {
            parameterMap.put(Constants.OFFSET, offset);
        }
        List<DepotItemVo4Material> list = depotItemService.findStockNumByMaterialIdList(parameterMap);
        //存放数据json数组
        Long materialId = Long.parseLong(mId);
        JSONArray dataArray = new JSONArray();
        if (null != list) {
            for (DepotItemVo4Material di : list) {
                JSONObject item = new JSONObject();
                double prevSum = sumNumber("入库", pid, materialId, monthTime, true) - sumNumber("出库", pid, materialId, monthTime, true);
                double InSum = sumNumber("入库", pid, materialId, monthTime, false);
                double OutSum = sumNumber("出库", pid, materialId, monthTime, false);
                item.put("MaterialId", di.getMaterialid() == null ? "" : di.getMaterialid());
                item.put("MaterialName", di.getMname());
                item.put("MaterialModel", di.getMmodel());
                item.put("thisSum", prevSum + InSum - OutSum);
                dataArray.add(item);
            }
        }
        objectMap.put("page", dataArray);
        if (list == null) {
            queryInfo.setRows(new ArrayList<Object>());
            queryInfo.setTotal(0);
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
        queryInfo.setRows(list);
        queryInfo.setTotal(depotItemService.findStockNumByMaterialIdCounts(parameterMap));
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 只根据商品id查询库存数量
     * @param pageSize
     * @param currentPage
     * @param mId
     * @param request
     * @return
     */
    @GetMapping(value = "/findStockNumByMaterialId")
    public String findStockNumByMaterialId(
            @RequestParam(value = Constants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = Constants.CURRENT_PAGE, required = false) Integer currentPage,
            @RequestParam("materialId") String mId,
            @RequestParam("monthTime") String monthTime,
            HttpServletRequest request) {
        Map<String, String> parameterMap = ParamUtils.requestToMap(request);
        parameterMap.put("mId", mId);
        parameterMap.put("monthTime", monthTime);
        PageQueryInfo queryInfo = new PageQueryInfo();
        Map<String, Object> objectMap = new HashMap<String, Object>();
        if (pageSize != null && pageSize <= 0) {
            pageSize = 10;
        }
        String offset = ParamUtils.getPageOffset(currentPage, pageSize);
        if (StringUtil.isNotEmpty(offset)) {
            parameterMap.put(Constants.OFFSET, offset);
        }
        List<DepotItemVo4Material> list = depotItemService.findStockNumByMaterialIdList(parameterMap);

        //存放数据json数组
        JSONArray dataArray = new JSONArray();
        if (null != list) {
            for (DepotItemVo4Material di : list) {
                JSONObject item = new JSONObject();
                int InSum = sumNumberByMaterialId("入库", di.getMaterialid());
                int OutSum = sumNumberByMaterialId("出库", di.getMaterialid());
                item.put("MaterialId", di.getMaterialid() == null ? "" : di.getMaterialid());
                item.put("MaterialName", di.getMname());
                item.put("MaterialModel", di.getMmodel());
                item.put("thisSum", InSum - OutSum);
                dataArray.add(item);
            }
        }
        objectMap.put("page", dataArray);
        if (list == null) {
            queryInfo.setRows(new ArrayList<Object>());
            queryInfo.setTotal(0);
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
        queryInfo.setRows(list);
        queryInfo.setTotal(depotItemService.findStockNumByMaterialIdCounts(parameterMap));
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 仅根据商品Id进行数量合计
     *
     * @param type
     * @param mId
     * @return
     */
    public int sumNumberByMaterialId(String type, Long mId) {
        int allNumber = 0;
        try {
            allNumber = depotItemService.findByTypeAndMaterialId(type, mId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allNumber;
    }

    /**
     * 保存明细
     * @param inserted
     * @param deleted
     * @param updated
     * @param headerId
     * @param request
     * @return
     */
    @PostMapping(value = "/saveDetials")
    public String saveDetials(@RequestParam("inserted") String inserted,
                              @RequestParam("deleted") String deleted,
                              @RequestParam("updated") String updated,
                              @RequestParam("headerId") Long headerId,
                              HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        try {
            //转为json
            JSONArray insertedJson = JSONArray.parseArray(inserted);
            JSONArray deletedJson = JSONArray.parseArray(deleted);
            JSONArray updatedJson = JSONArray.parseArray(updated);
            if (null != insertedJson) {
                for (int i = 0; i < insertedJson.size(); i++) {
                    DepotItem depotItem = new DepotItem();
                    JSONObject tempInsertedJson = JSONObject.parseObject(insertedJson.getString(i));
                    depotItem.setHeaderid(headerId);
                    depotItem.setMaterialid(tempInsertedJson.getLong("MaterialId"));
                    depotItem.setMunit(tempInsertedJson.getString("Unit"));
                    if (!StringUtil.isEmpty(tempInsertedJson.get("OperNumber").toString())) {
                        depotItem.setOpernumber(tempInsertedJson.getDouble("OperNumber"));
                        try {
                            String Unit = tempInsertedJson.get("Unit").toString();
                            Double oNumber = tempInsertedJson.getDouble("OperNumber");
                            Long mId = Long.parseLong(tempInsertedJson.get("MaterialId").toString());
                            //以下进行单位换算
                            String UnitName = findUnitName(mId); //查询计量单位名称
                            if (!StringUtil.isEmpty(UnitName)) {
                                String UnitList = UnitName.substring(0, UnitName.indexOf("("));
                                String RatioList = UnitName.substring(UnitName.indexOf("("));
                                String basicUnit = UnitList.substring(0, UnitList.indexOf(",")); //基本单位
                                String otherUnit = UnitList.substring(UnitList.indexOf(",") + 1); //副单位
                                Integer ratio = Integer.parseInt(RatioList.substring(RatioList.indexOf(":") + 1).replace(")", "")); //比例
                                if (Unit.equals(basicUnit)) { //如果等于基础单位
                                    depotItem.setBasicnumber(oNumber); //数量一致
                                } else if (Unit.equals(otherUnit)) { //如果等于副单位
                                    depotItem.setBasicnumber(oNumber * ratio); //数量乘以比例
                                }
                            } else {
                                depotItem.setBasicnumber(oNumber); //其他情况
                            }
                        } catch (Exception e) {
                            logger.error(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("UnitPrice").toString())) {
                        depotItem.setUnitprice(tempInsertedJson.getDouble("UnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxUnitPrice").toString())) {
                        depotItem.setTaxunitprice(tempInsertedJson.getDouble("TaxUnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("AllPrice").toString())) {
                        depotItem.setAllprice(tempInsertedJson.getDouble("AllPrice"));
                    }
                    depotItem.setRemark(tempInsertedJson.getString("Remark"));
                    if (tempInsertedJson.get("DepotId") != null && !StringUtil.isEmpty(tempInsertedJson.get("DepotId").toString())) {
                        depotItem.setDepotid(tempInsertedJson.getLong("DepotId"));
                    }
                    if (tempInsertedJson.get("AnotherDepotId") != null && !StringUtil.isEmpty(tempInsertedJson.get("AnotherDepotId").toString())) {
                        depotItem.setAnotherdepotid(tempInsertedJson.getLong("AnotherDepotId"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxRate").toString())) {
                        depotItem.setTaxrate(tempInsertedJson.getDouble("TaxRate"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxMoney").toString())) {
                        depotItem.setTaxmoney(tempInsertedJson.getDouble("TaxMoney"));
                    }
                    if (!StringUtil.isEmpty(tempInsertedJson.get("TaxLastMoney").toString())) {
                        depotItem.setTaxlastmoney(tempInsertedJson.getDouble("TaxLastMoney"));
                    }
                    if (tempInsertedJson.get("OtherField1") != null) {
                        depotItem.setOtherfield1(tempInsertedJson.getString("OtherField1"));
                    }
                    if (tempInsertedJson.get("OtherField2") != null) {
                        depotItem.setOtherfield2(tempInsertedJson.getString("OtherField2"));
                    }
                    if (tempInsertedJson.get("OtherField3") != null) {
                        depotItem.setOtherfield3(tempInsertedJson.getString("OtherField3"));
                    }
                    if (tempInsertedJson.get("OtherField4") != null) {
                        depotItem.setOtherfield4(tempInsertedJson.getString("OtherField4"));
                    }
                    if (tempInsertedJson.get("OtherField5") != null) {
                        depotItem.setOtherfield5(tempInsertedJson.getString("OtherField5"));
                    }
                    if (tempInsertedJson.get("MType") != null) {
                        depotItem.setMtype(tempInsertedJson.getString("MType"));
                    }
                    depotItemService.insertDepotItemWithObj(depotItem);
                }
            }
            if (null != deletedJson) {
                for (int i = 0; i < deletedJson.size(); i++) {
                    JSONObject tempDeletedJson = JSONObject.parseObject(deletedJson.getString(i));
                    depotItemService.deleteDepotItem(tempDeletedJson.getLong("Id"));
                }
            }
            if (null != updatedJson) {
                for (int i = 0; i < updatedJson.size(); i++) {
                    JSONObject tempUpdatedJson = JSONObject.parseObject(updatedJson.getString(i));
                    DepotItem depotItem = depotItemService.getDepotItem(tempUpdatedJson.getLong("Id"));
                    depotItem.setId(tempUpdatedJson.getLong("Id"));
                    depotItem.setMaterialid(tempUpdatedJson.getLong("MaterialId"));
                    depotItem.setMunit(tempUpdatedJson.getString("Unit"));
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("OperNumber").toString())) {
                        depotItem.setOpernumber(tempUpdatedJson.getDouble("OperNumber"));
                        try {
                            String Unit = tempUpdatedJson.get("Unit").toString();
                            Double oNumber = tempUpdatedJson.getDouble("OperNumber");
                            Long mId = Long.parseLong(tempUpdatedJson.get("MaterialId").toString());
                            //以下进行单位换算
                            String UnitName = findUnitName(mId); //查询计量单位名称
                            if (!StringUtil.isEmpty(UnitName)) {
                                String UnitList = UnitName.substring(0, UnitName.indexOf("("));
                                String RatioList = UnitName.substring(UnitName.indexOf("("));
                                String basicUnit = UnitList.substring(0, UnitList.indexOf(",")); //基本单位
                                String otherUnit = UnitList.substring(UnitList.indexOf(",") + 1); //副单位
                                Integer ratio = Integer.parseInt(RatioList.substring(RatioList.indexOf(":") + 1).replace(")", "")); //比例
                                if (Unit.equals(basicUnit)) { //如果等于基础单位
                                    depotItem.setBasicnumber(oNumber); //数量一致
                                } else if (Unit.equals(otherUnit)) { //如果等于副单位
                                    depotItem.setBasicnumber(oNumber * ratio); //数量乘以比例
                                }
                            } else {
                                depotItem.setBasicnumber(oNumber); //其他情况
                            }
                        } catch (Exception e) {
                            logger.error(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("UnitPrice").toString())) {
                        depotItem.setUnitprice(tempUpdatedJson.getDouble("UnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxUnitPrice").toString())) {
                        depotItem.setTaxunitprice(tempUpdatedJson.getDouble("TaxUnitPrice"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("AllPrice").toString())) {
                        depotItem.setAllprice(tempUpdatedJson.getDouble("AllPrice"));
                    }
                    depotItem.setRemark(tempUpdatedJson.getString("Remark"));
                    if (tempUpdatedJson.get("DepotId") != null && !StringUtil.isEmpty(tempUpdatedJson.get("DepotId").toString())) {
                        depotItem.setDepotid(tempUpdatedJson.getLong("DepotId"));
                    }
                    if (tempUpdatedJson.get("AnotherDepotId") != null && !StringUtil.isEmpty(tempUpdatedJson.get("AnotherDepotId").toString())) {
                        depotItem.setAnotherdepotid(tempUpdatedJson.getLong("AnotherDepotId"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxRate").toString())) {
                        depotItem.setTaxrate(tempUpdatedJson.getDouble("TaxRate"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxMoney").toString())) {
                        depotItem.setTaxmoney(tempUpdatedJson.getDouble("TaxMoney"));
                    }
                    if (!StringUtil.isEmpty(tempUpdatedJson.get("TaxLastMoney").toString())) {
                        depotItem.setTaxlastmoney(tempUpdatedJson.getDouble("TaxLastMoney"));
                    }
                    depotItem.setOtherfield1(tempUpdatedJson.getString("OtherField1"));
                    depotItem.setOtherfield2(tempUpdatedJson.getString("OtherField2"));
                    depotItem.setOtherfield3(tempUpdatedJson.getString("OtherField3"));
                    depotItem.setOtherfield4(tempUpdatedJson.getString("OtherField4"));
                    depotItem.setOtherfield5(tempUpdatedJson.getString("OtherField5"));
                    depotItem.setMtype(tempUpdatedJson.getString("MType"));
                    depotItemService.updateDepotItemWithObj(depotItem);
                }
            }

            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>>>>>>>>>>保存明细信息异常", e);
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 查询计量单位信息
     *
     * @return
     */
    public String findUnitName(Long mId) {
        String unitName = "";
        try {
            unitName = materialService.findUnitName(mId);
            if (unitName != null) {
                unitName = unitName.substring(1, unitName.length() - 1);
                if (unitName.equals("null")) {
                    unitName = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitName;
    }

    @GetMapping(value = "/getDetailList")
    public BaseResponseInfo getDetailList(@RequestParam("headerId") Long headerId,
                              @RequestParam("mpList") String mpList,
                              HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
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
                    item.put("Id", diEx.getId());
                    item.put("MaterialId", diEx.getMaterialid() == null ? "" : diEx.getMaterialid());
                    String ratio; //比例
                    if (diEx.getUnitId() == null || diEx.getUnitId().equals("")) {
                        ratio = "";
                    } else {
                        ratio = diEx.getUName();
                        ratio = ratio.substring(ratio.indexOf("("));
                    }
                    //品名/型号/扩展信息/包装
                    String MaterialName = diEx.getMName() + ((diEx.getMModel() == null || diEx.getMModel().equals("")) ? "" : "(" + diEx.getMModel() + ")");
                    String materialOther = getOtherInfo(mpArr, diEx);
                    MaterialName = MaterialName + materialOther + ((diEx.getUName() == null || diEx.getUName().equals("")) ? "" : "(" + diEx.getUName() + ")") + ratio;
                    item.put("MaterialName", MaterialName);
                    item.put("Unit", diEx.getMunit());
                    item.put("OperNumber", diEx.getOpernumber());
                    item.put("BasicNumber", diEx.getBasicnumber());
                    item.put("UnitPrice", diEx.getUnitprice());
                    item.put("TaxUnitPrice", diEx.getTaxunitprice());
                    item.put("AllPrice", diEx.getAllprice());
                    item.put("Remark", diEx.getRemark());
                    item.put("Img", diEx.getImg());
                    item.put("DepotId", diEx.getDepotid() == null ? "" : diEx.getDepotid());
                    item.put("DepotName", diEx.getDepotid() == null ? "" : diEx.getDepotName());
                    item.put("AnotherDepotId", diEx.getAnotherdepotid() == null ? "" : diEx.getAnotherdepotid());
                    item.put("AnotherDepotName", diEx.getAnotherdepotid() == null ? "" : diEx.getAnotherDepotName());
                    item.put("TaxRate", diEx.getTaxrate());
                    item.put("TaxMoney", diEx.getTaxmoney());
                    item.put("TaxLastMoney", diEx.getTaxlastmoney());
                    item.put("OtherField1", diEx.getOtherfield1());
                    item.put("OtherField2", diEx.getOtherfield2());
                    item.put("OtherField3", diEx.getOtherfield3());
                    item.put("OtherField4", diEx.getOtherfield4());
                    item.put("OtherField5", diEx.getOtherfield5());
                    item.put("MType", diEx.getMtype());
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
    public String getOtherInfo(String[] mpArr, DepotItemVo4WithInfoEx diEx) {
        String materialOther = "";
        for (int i = 0; i < mpArr.length; i++) {
            if (mpArr[i].equals("颜色")) {
                materialOther = materialOther + ((diEx.getMColor() == null || diEx.getMColor().equals("")) ? "" : "(" + diEx.getMColor() + ")");
            }
            if (mpArr[i].equals("规格")) {
                materialOther = materialOther + ((diEx.getMStandard() == null || diEx.getMStandard().equals("")) ? "" : "(" + diEx.getMStandard() + ")");
            }
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
     * @param projectId
     * @param monthTime
     * @param headIds
     * @param materialIds
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/findByAll")
    public BaseResponseInfo findByAll(@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("projectId") Integer projectId,
                                      @RequestParam("monthTime") String monthTime,
                                      @RequestParam("headIds") String headIds,
                                      @RequestParam("materialIds") String materialIds,
                                      @RequestParam("mpList") String mpList,
                                      HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(headIds, materialIds, currentPage, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(headIds, materialIds);
            map.put("total", total);
            //存放数据json数组
            Integer pid = projectId;
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    Double prevSum = sumNumber("入库", pid, diEx.getMaterialid(), monthTime, true) - sumNumber("出库", pid, diEx.getMaterialid(), monthTime, true);
                    Double InSum = sumNumber("入库", pid, diEx.getMaterialid(), monthTime, false);
                    Double OutSum = sumNumber("出库", pid, diEx.getMaterialid(), monthTime, false);
                    Double prevPrice = sumPrice("入库", pid, diEx.getMaterialid(), monthTime, true) - sumPrice("出库", pid, diEx.getMaterialid(), monthTime, true);
                    Double InPrice = sumPrice("入库", pid, diEx.getMaterialid(), monthTime, false);
                    Double OutPrice = sumPrice("出库", pid, diEx.getMaterialid(), monthTime, false);
                    item.put("Id", diEx.getId());
                    item.put("MaterialId", diEx.getMaterialid());
                    item.put("MaterialName", diEx.getMName());
                    item.put("MaterialModel", diEx.getMColor());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, diEx);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", diEx.getMColor());
                    item.put("MaterialUnit", diEx.getMaterialUnit());
                    Double unitPrice = 0.0;
                    if (prevSum + InSum - OutSum != 0.0) {
                        unitPrice = (prevPrice + InPrice - OutPrice) / (prevSum + InSum - OutSum);
                    }
                    item.put("UnitPrice", unitPrice);
                    item.put("prevSum", prevSum);
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("thisSum", prevSum + InSum - OutSum);
                    item.put("thisAllPrice", prevPrice + InPrice - OutPrice);
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
     * @param pid
     * @param monthTime
     * @param headIds
     * @param materialIds
     * @param request
     * @return
     */
    @GetMapping(value = "/totalCountMoney")
    public BaseResponseInfo totalCountMoney(@RequestParam("projectId") Integer pid,
                                                        @RequestParam("monthTime") String monthTime,
                                                        @RequestParam("headIds") String headIds,
                                                        @RequestParam("materialIds") String materialIds,
                                                        HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(headIds, materialIds, null, null);
            Double thisAllPrice = 0.0;
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    Double prevPrice = sumPrice("入库", pid, diEx.getMaterialid(), monthTime, true) - sumPrice("出库", pid, diEx.getMaterialid(), monthTime, true);
                    Double InPrice = sumPrice("入库", pid, diEx.getMaterialid(), monthTime, false);
                    Double OutPrice = sumPrice("出库", pid, diEx.getMaterialid(), monthTime, false);
                    thisAllPrice = thisAllPrice + (prevPrice + InPrice - OutPrice);
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
     * @param headIds
     * @param materialIds
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/buyIn")
    public BaseResponseInfo buyIn(@RequestParam("currentPage") Integer currentPage,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("monthTime") String monthTime,
                                      @RequestParam("headIds") String headIds,
                                      @RequestParam("materialIds") String materialIds,
                                      @RequestParam("mpList") String mpList,
                                      HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(headIds, materialIds, currentPage, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(headIds, materialIds);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    Double InSum = sumNumberBuyOrSale("入库", "采购", diEx.getMaterialid(), monthTime);
                    Double OutSum = sumNumberBuyOrSale("出库", "采购退货", diEx.getMaterialid(), monthTime);
                    Double InSumPrice = sumPriceBuyOrSale("入库", "采购", diEx.getMaterialid(), monthTime);
                    Double OutSumPrice = sumPriceBuyOrSale("出库", "采购退货", diEx.getMaterialid(), monthTime);
                    item.put("Id", diEx.getId());
                    item.put("MaterialId", diEx.getMaterialid());
                    item.put("MaterialName", diEx.getMName());
                    item.put("MaterialModel", diEx.getMModel());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, diEx);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", diEx.getMColor());
                    item.put("MaterialUnit", diEx.getMaterialUnit());
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("InSumPrice", InSumPrice);
                    item.put("OutSumPrice", OutSumPrice);
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
     * @param headIds
     * @param materialIds
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/saleOut")
    public BaseResponseInfo saleOut(@RequestParam("currentPage") Integer currentPage,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("monthTime") String monthTime,
                                  @RequestParam("headIds") String headIds,
                                  @RequestParam("materialIds") String materialIds,
                                  @RequestParam("mpList") String mpList,
                                  HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(headIds, materialIds, currentPage, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(headIds, materialIds);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    Double OutSumRetail = sumNumberBuyOrSale("出库", "零售", diEx.getMaterialid(), monthTime);
                    Double OutSum = sumNumberBuyOrSale("出库", "销售", diEx.getMaterialid(), monthTime);
                    Double InSumRetail = sumNumberBuyOrSale("入库", "零售退货", diEx.getMaterialid(), monthTime);
                    Double InSum = sumNumberBuyOrSale("入库", "销售退货", diEx.getMaterialid(), monthTime);
                    Double OutSumRetailPrice = sumPriceBuyOrSale("出库", "零售", diEx.getMaterialid(), monthTime);
                    Double OutSumPrice = sumPriceBuyOrSale("出库", "销售", diEx.getMaterialid(), monthTime);
                    Double InSumRetailPrice = sumPriceBuyOrSale("入库", "零售退货", diEx.getMaterialid(), monthTime);
                    Double InSumPrice = sumPriceBuyOrSale("入库", "销售退货", diEx.getMaterialid(), monthTime);
                    item.put("Id", diEx.getId());
                    item.put("MaterialId", diEx.getMaterialid());
                    item.put("MaterialName", diEx.getMName());
                    item.put("MaterialModel", diEx.getMModel());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, diEx);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", diEx.getMColor());
                    item.put("MaterialUnit", diEx.getMaterialUnit());
                    item.put("OutSum", OutSumRetail + OutSum);
                    item.put("InSum", InSumRetail + InSum);
                    item.put("OutSumPrice", OutSumRetailPrice + OutSumPrice);
                    item.put("InSumPrice", InSumRetailPrice + InSumPrice);
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
     * 查找礼品卡信息
     * @param currentPage
     * @param pageSize
     * @param projectId
     * @param headIds
     * @param materialIds
     * @param mpList
     * @param request
     * @return
     */
    @GetMapping(value = "/findGiftByAll")
    public BaseResponseInfo findGiftByAll(@RequestParam("currentPage") Integer currentPage,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("projectId") Integer projectId,
                                          @RequestParam("headIds") String headIds,
                                          @RequestParam("materialIds") String materialIds,
                                          @RequestParam("mpList") String mpList,
                                          HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(headIds, materialIds, currentPage, pageSize);
            String[] mpArr = mpList.split(",");
            int total = depotItemService.findByAllCount(headIds, materialIds);
            map.put("total", total);
            Integer pid = projectId;
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    JSONObject item = new JSONObject();
                    Double InSum = sumNumberGift("礼品充值", pid, diEx.getMaterialid(), "in");
                    Double OutSum = sumNumberGift("礼品销售", pid, diEx.getMaterialid(), "out");
                    item.put("Id", diEx.getId());
                    item.put("MaterialId", diEx.getMaterialid());
                    item.put("MaterialName", diEx.getMName());
                    item.put("MaterialModel", diEx.getMModel());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, diEx);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", diEx.getMColor());
                    item.put("MaterialUnit", diEx.getMaterialUnit());
                    item.put("thisSum", InSum - OutSum);
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
     * 导出excel表格
     * @param currentPage
     * @param pageSize
     * @param projectId
     * @param monthTime
     * @param headIds
     * @param materialIds
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/exportExcel")
    public BaseResponseInfo exportExcel(@RequestParam("currentPage") Integer currentPage,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("projectId") Integer projectId,
                                        @RequestParam("monthTime") String monthTime,
                                        @RequestParam("headIds") String headIds,
                                        @RequestParam("materialIds") String materialIds,
                                        HttpServletRequest request, HttpServletResponse response) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        String message = "成功";
        try {
            List<DepotItemVo4WithInfoEx> dataList = depotItemService.findByAll(headIds, materialIds, currentPage, pageSize);
            //存放数据json数组
            Integer pid = projectId;
            String[] names = {"名称", "型号", "单位", "单价", "上月结存数量", "入库数量", "出库数量", "本月结存数量", "结存金额"};
            String title = "库存报表";
            List<String[]> objects = new ArrayList<String[]>();
            if (null != dataList) {
                for (DepotItemVo4WithInfoEx diEx : dataList) {
                    String[] objs = new String[9];
                    Double prevSum = sumNumber("入库", pid, diEx.getMaterialid(), monthTime, true) - sumNumber("出库", pid, diEx.getMaterialid(), monthTime, true);
                    Double InSum = sumNumber("入库", pid, diEx.getMaterialid(), monthTime, false);
                    Double OutSum = sumNumber("出库", pid, diEx.getMaterialid(), monthTime, false);
                    Double prevPrice = sumPrice("入库", pid, diEx.getMaterialid(), monthTime, true) - sumPrice("出库", pid, diEx.getMaterialid(), monthTime, true);
                    Double InPrice = sumPrice("入库", pid, diEx.getMaterialid(), monthTime, false);
                    Double OutPrice = sumPrice("出库", pid, diEx.getMaterialid(), monthTime, false);
                    Double unitPrice = 0.0;
                    if (prevSum + InSum - OutSum != 0.0) {
                        unitPrice = (prevPrice + InPrice - OutPrice) / (prevSum + InSum - OutSum);
                    }
                    Double thisSum = prevSum + InSum - OutSum;
                    Double thisAllPrice = prevPrice + InPrice - OutPrice;
                    objs[0] = diEx.getMName().toString();
                    objs[1] = diEx.getMModel().toString();
                    objs[2] = diEx.getMaterialUnit().toString();
                    objs[3] = unitPrice.toString();
                    objs[4] = prevSum.toString();
                    objs[5] = InSum.toString();
                    objs[6] = OutSum.toString();
                    objs[7] = thisSum.toString();
                    objs[8] = thisAllPrice.toString();
                    objects.add(objs);
                }
            }
            File file = ExcelUtils.exportObjectsWithoutTitle(title, names, title, objects);
            ExportExecUtil.showExec(file, file.getName() + "-" + monthTime, response);
            res.code = 200;
        } catch (Exception e) {
            e.printStackTrace();
            message = "导出失败";
            res.code = 500;
        } finally {
            map.put("message", message);
            res.data = map;
        }
        return res;
    }

    /**
     * 数量合计
     *
     * @param type
     * @param MId
     * @param MonthTime
     * @param isPrev
     * @return
     */
    public Double sumNumber(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        Double sumNumber = 0.0;
        try {
            Double sum = depotItemService.findByType(type, ProjectId, MId, MonthTime, isPrev);
            if(sum != null) {
                sumNumber = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumNumber;
    }

    /**
     * 价格合计
     *
     * @param type
     * @param MId
     * @param MonthTime
     * @param isPrev
     * @return
     */
    public Double sumPrice(String type, Integer ProjectId, Long MId, String MonthTime, Boolean isPrev) {
        Double sumPrice = 0.0;
        try {
            Double sum = depotItemService.findPriceByType(type, ProjectId, MId, MonthTime, isPrev);
            if(sum != null) {
                sumPrice = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumPrice;
    }

    public Double sumNumberBuyOrSale(String type, String subType, Long MId, String MonthTime) {
        Double sumNumber = 0.0;
        String sumType = "Number";
        try {
            Double sum = depotItemService.buyOrSale(type, subType, MId, MonthTime, sumType);
            if(sum != null) {
                sumNumber = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumNumber;
    }

    public Double sumPriceBuyOrSale(String type, String subType, Long MId, String MonthTime) {
        Double sumPrice = 0.0;
        String sumType = "Price";
        try {
            Double sum = depotItemService.buyOrSale(type, subType, MId, MonthTime, sumType);
            if(sum != null) {
                sumPrice = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumPrice;
    }

    /**
     * 数量合计-礼品卡
     * @param subType
     * @param ProjectId
     * @param MId
     * @param type
     * @return
     */
    public Double sumNumberGift(String subType, Integer ProjectId, Long MId, String type) {
        Double sumNumber = 0.0;
        String allNumber = "";
        try {
            if (ProjectId != null) {
                Double sum = depotItemService.findGiftByType(subType, ProjectId, MId, type);
                if(sum != null) {
                    sumNumber = sum;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumNumber;
    }
}
