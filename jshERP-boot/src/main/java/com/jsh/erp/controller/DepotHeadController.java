package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.DepotHeadVo4Body;
import com.jsh.erp.datasource.entities.Supplier;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.exception.BusinessParamCheckingException;
import com.jsh.erp.service.accountHead.AccountHeadService;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.redis.RedisService;
import com.jsh.erp.service.supplier.SupplierService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;
import static com.jsh.erp.utils.Tools.getNow3;

/**
 * @author ji-sheng-hua 752*718*920
 */
@RestController
@RequestMapping(value = "/depotHead")
public class DepotHeadController {
    private Logger logger = LoggerFactory.getLogger(DepotHeadController.class);

    @Resource
    private DepotHeadService depotHeadService;

    @Resource
    private AccountHeadService accountHeadService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private RedisService redisService;

    /**
     * 批量设置状态-审核或者反审核
     * @param jsonObject
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request) throws Exception{
        Map<String, Object> objectMap = new HashMap<>();
        String status = jsonObject.getString("status");
        String ids = jsonObject.getString("ids");
        int res = depotHeadService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 入库出库明细接口
     * @param currentPage
     * @param pageSize
     * @param oId
     * @param materialParam
     * @param depotId
     * @param beginTime
     * @param endTime
     * @param type
     * @param request
     * @return
     */
    @GetMapping(value = "/findInDetail")
    public BaseResponseInfo findInDetail(@RequestParam("currentPage") Integer currentPage,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("organId") Integer oId,
                                        @RequestParam("materialParam") String materialParam,
                                        @RequestParam("depotId") Integer depotId,
                                        @RequestParam("beginTime") String beginTime,
                                        @RequestParam("endTime") String endTime,
                                        @RequestParam("type") String type,
                                        HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotHeadVo4InDetail> resList = new ArrayList<DepotHeadVo4InDetail>();
            beginTime = Tools.parseDayToTime(beginTime, BusinessConstants.DAY_FIRST_TIME);
            endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
            List<DepotHeadVo4InDetail> list = depotHeadService.findByAll(beginTime, endTime, type, materialParam, depotId, oId, (currentPage-1)*pageSize, pageSize);
            int total = depotHeadService.findByAllCount(beginTime, endTime, type, materialParam, depotId, oId);
            map.put("total", total);
            //存放数据json数组
            if (null != list) {
                for (DepotHeadVo4InDetail dhd : list) {
                    resList.add(dhd);
                }
            }
            map.put("rows", resList);
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
     * 入库出库统计接口
     * @param currentPage
     * @param pageSize
     * @param oId
     * @param materialParam
     * @param depotId
     * @param beginTime
     * @param endTime
     * @param type
     * @param request
     * @return
     */
    @GetMapping(value = "/findInOutMaterialCount")
    public BaseResponseInfo findInOutMaterialCount(@RequestParam("currentPage") Integer currentPage,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam("organId") Integer oId,
                                         @RequestParam("materialParam") String materialParam,
                                         @RequestParam("depotId") Integer depotId,
                                         @RequestParam("beginTime") String beginTime,
                                         @RequestParam("endTime") String endTime,
                                         @RequestParam("type") String type,
                                         HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotHeadVo4InOutMCount> resList = new ArrayList<>();
            beginTime = Tools.parseDayToTime(beginTime,BusinessConstants.DAY_FIRST_TIME);
            endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
            List<DepotHeadVo4InOutMCount> list = depotHeadService.findInOutMaterialCount(beginTime, endTime, type, materialParam, depotId, oId, (currentPage-1)*pageSize, pageSize);
            int total = depotHeadService.findInOutMaterialCountTotal(beginTime, endTime, type, materialParam, depotId, oId);
            map.put("total", total);
            //存放数据json数组
            if (null != list) {
                for (DepotHeadVo4InOutMCount dhc : list) {
                    resList.add(dhc);
                }
            }
            map.put("rows", resList);
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
     * 对账单接口
     * @param currentPage
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @param organId
     * @param supType
     * @param request
     * @return
     */
    @GetMapping(value = "/findStatementAccount")
    public BaseResponseInfo findStatementAccount(@RequestParam("currentPage") Integer currentPage,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @RequestParam("beginTime") String beginTime,
                                                   @RequestParam("endTime") String endTime,
                                                   @RequestParam("organId") Integer organId,
                                                   @RequestParam("supType") String supType,
                                                   HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            beginTime = Tools.parseDayToTime(beginTime,BusinessConstants.DAY_FIRST_TIME);
            endTime = Tools.parseDayToTime(endTime,BusinessConstants.DAY_LAST_TIME);
            List<DepotHeadVo4StatementAccount> list = depotHeadService.findStatementAccount(beginTime, endTime, organId, supType, (currentPage-1)*pageSize, pageSize);
            int total = depotHeadService.findStatementAccountCount(beginTime, endTime, organId, supType);
            map.put("rows", list);
            map.put("total", total);
            if(null!=organId) {
                Supplier supplier = supplierService.getSupplier(organId);
                BigDecimal beginNeed = BigDecimal.ZERO;
                if (("客户").equals(supType)) {
                    beginNeed = supplier.getBeginNeedGet();
                } else if (("供应商").equals(supType)) {
                    beginNeed = supplier.getBeginNeedPay();
                }
                BigDecimal firstMoney = depotHeadService.findTotalPay(organId, beginTime, supType)
                        .add(accountHeadService.findTotalPay(organId, beginTime, supType)).add(beginNeed);
                BigDecimal lastMoney = depotHeadService.findTotalPay(organId, endTime, supType)
                        .add(accountHeadService.findTotalPay(organId, endTime, supType)).add(beginNeed);
                map.put("firstMoney", firstMoney); //期初
                map.put("lastMoney", lastMoney);  //期末
            }
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
     * 根据编号查询单据信息
     * @param number
     * @param request
     * @return
     */
    @GetMapping(value = "/getDetailByNumber")
    public BaseResponseInfo getDetailByNumber(@RequestParam("number") String number,
                                         HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        DepotHeadVo4List dhl = new DepotHeadVo4List();
        try {
            List<DepotHeadVo4List> list = depotHeadService.getDetailByNumber(number);
            if(list.size() == 1) {
                dhl = list.get(0);
            }
            res.code = 200;
            res.data = dhl;
        } catch(Exception e){
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 新增单据主表及单据子表信息
     * @param body
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/addDepotHeadAndDetail")
    public Object addDepotHeadAndDetail(@RequestBody DepotHeadVo4Body body, HttpServletRequest request) throws  Exception{
        JSONObject result = ExceptionConstants.standardSuccess();
        String beanJson = body.getInfo();
        String rows = body.getRows();
        Long billsNumLimit = Long.parseLong(redisService.getObjectFromSessionByKey(request,"billsNumLimit").toString());
        Long tenantId = redisService.getTenantId(request);
        Long count = depotHeadService.countDepotHead(null,null,null,null,null,null,null,null);
        if(count>= billsNumLimit) {
            throw new BusinessParamCheckingException(ExceptionConstants.DEPOT_HEAD_OVER_LIMIT_FAILED_CODE,
                    ExceptionConstants.DEPOT_HEAD_OVER_LIMIT_FAILED_MSG);
        } else {
            depotHeadService.addDepotHeadAndDetail(beanJson,rows,tenantId, request);
        }
        return result;
    }

    /**
     * 更新单据主表及单据子表信息
     * @param body
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/updateDepotHeadAndDetail")
    public Object updateDepotHeadAndDetail(@RequestBody DepotHeadVo4Body body, HttpServletRequest request) throws Exception{
        Long tenantId = redisService.getTenantId(request);
        JSONObject result = ExceptionConstants.standardSuccess();
        String beanJson = body.getInfo();
        String rows = body.getRows();
        depotHeadService.updateDepotHeadAndDetail(beanJson,rows,tenantId,request);
        return result;
    }

    /**
     * 统计今日销售额、今日进货额、本月销售额、本月进货额
     * @param request
     * @return
     */
    @GetMapping(value = "/getBuyAndSaleStatistics")
    public BaseResponseInfo getBuyAndSaleStatistics(HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String today = Tools.getNow() + BusinessConstants.DAY_FIRST_TIME;
            String firstDay = Tools.firstDayOfMonth(Tools.getCurrentMonth()) + BusinessConstants.DAY_FIRST_TIME;
            BigDecimal todaySale = depotHeadService.getBuyAndSaleStatistics("出库", "销售",
                    1, today, getNow3()); //今日销售出库
            BigDecimal todayRetailSale = depotHeadService.getBuyAndSaleRetailStatistics("出库", "零售",
                    0, today, getNow3()); //今日零售出库
            BigDecimal todayBuy = depotHeadService.getBuyAndSaleStatistics("入库", "采购",
                    1, today, getNow3()); //今日采购入库
            BigDecimal monthSale = depotHeadService.getBuyAndSaleStatistics("出库", "销售",
                    1,firstDay, getNow3()); //本月销售出库
            BigDecimal monthRetailSale = depotHeadService.getBuyAndSaleRetailStatistics("出库", "零售",
                    0,firstDay, getNow3()); //本月零售出库
            BigDecimal monthBuy = depotHeadService.getBuyAndSaleStatistics("入库", "采购",
                    1, firstDay, getNow3()); //本月采购入库
            map.put("todaySale", todaySale.add(todayRetailSale));
            map.put("todayBuy", todayBuy);
            map.put("thisMonthSale", monthSale.add(monthRetailSale));
            map.put("thisMonthBuy", monthBuy);
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
     * 根据当前用户获取操作员数组，用于控制当前用户的数据权限，限制可以看到的单据范围
     * 注意：该接口提供给部分插件使用，勿删
     * @param request
     * @return
     */
    @GetMapping(value = "/getCreatorByCurrentUser")
    public BaseResponseInfo getCreatorByRoleType(HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String creator = "";
            String roleType = redisService.getObjectFromSessionByKey(request,"roleType").toString();
            if(StringUtil.isNotEmpty(roleType)) {
                creator = depotHeadService.getCreatorByRoleType(roleType);
            }
            res.code = 200;
            res.data = creator;
        } catch (Exception e) {
            e.printStackTrace();
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 查询存在欠款的单据
     * @param search
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/debtList")
    public String debtList(@RequestParam(value = Constants.SEARCH, required = false) String search,
                           HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        String organIdStr = StringUtil.getInfo(search, "organId");
        Long organId = Long.parseLong(organIdStr);
        String materialParam = StringUtil.getInfo(search, "materialParam");
        String number = StringUtil.getInfo(search, "number");
        String beginTime = StringUtil.getInfo(search, "beginTime");
        String endTime = StringUtil.getInfo(search, "endTime");
        String type = StringUtil.getInfo(search, "type");
        String subType = StringUtil.getInfo(search, "subType");
        String roleType = StringUtil.getInfo(search, "roleType");
        String status = StringUtil.getInfo(search, "status");
        List<DepotHeadVo4List> list = depotHeadService.debtList(organId, materialParam, number, beginTime, endTime, type, subType, roleType, status);
        if (list != null) {
            objectMap.put("rows", list);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            objectMap.put("rows", new ArrayList<>());
            return returnJson(objectMap, "查找不到数据", ErpInfo.OK.code);
        }
    }
}
