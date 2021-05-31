package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.DepotHeadVo4Body;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.exception.BusinessParamCheckingException;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.redis.RedisService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import com.jsh.erp.utils.Tools;
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
            int j = 1;
            if (supType.equals("客户")) { //客户
                j = 1;
            } else if (supType.equals("供应商")) { //供应商
                j = -1;
            }
            List<DepotHeadVo4StatementAccount> resList = new ArrayList<DepotHeadVo4StatementAccount>();
            List<DepotHeadVo4StatementAccount> list = depotHeadService.findStatementAccount(beginTime, endTime, organId, supType, (currentPage-1)*pageSize, pageSize);
            int total = depotHeadService.findStatementAccountCount(beginTime, endTime, organId, supType);
            map.put("total", total);
            //存放数据json数组
            if (null != list) {
                for (DepotHeadVo4StatementAccount dha : list) {
                    dha.setNumber(dha.getNumber()); //单据编号
                    dha.setType(dha.getType()); //类型
                    String type = dha.getType();
                    BigDecimal p1 = BigDecimal.ZERO ;
                    BigDecimal p2 = BigDecimal.ZERO;
                    if (dha.getDiscountLastMoney() != null) {
                        p1 = dha.getDiscountLastMoney();
                    }
                    if (dha.getChangeAmount() != null) {
                        p2 = dha.getChangeAmount();
                    }
                    BigDecimal allPrice = BigDecimal.ZERO;
                    if ((p1.compareTo(BigDecimal.ZERO))==-1) {
                        p1 = p1.abs();
                    }
                    if(dha.getOtherMoney()!=null) {
                        p1 = p1.add(dha.getOtherMoney()); //与其它费用相加
                    }
                    if ((p2 .compareTo(BigDecimal.ZERO))==-1) {
                        p2 = p2.abs();
                    }
                    if (type.equals("采购入库")) {
                        allPrice = p2.subtract(p1);
                    } else if (type.equals("销售退货入库")) {
                        allPrice = p2.subtract(p1);
                    } else if (type.equals("销售出库")) {
                        allPrice = p1.subtract(p2);
                    } else if (type.equals("采购退货出库")) {
                        allPrice = p1.subtract(p2);
                    } else if (type.equals("付款")) {
                        allPrice = p1.add(p2);
                    } else if (type.equals("收款")) {
                        allPrice = BigDecimal.ZERO.subtract(p1.add(p2));
                    } else if (type.equals("收入")) {
                        allPrice =  p1.subtract(p2);
                    } else if (type.equals("支出")) {
                        allPrice = p2.subtract(p1);
                    }
                    dha.setBillMoney(p1); //单据金额
                    dha.setChangeAmount(p2); //实际支付
                    DecimalFormat df = new DecimalFormat(".##");
                    dha.setAllPrice(new BigDecimal(df.format(allPrice.multiply(new BigDecimal(j))))); //本期变化
                    dha.setSupplierName(dha.getSupplierName()); //单位名称
                    dha.setoTime(dha.getoTime()); //单据日期
                    resList.add(dha);
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
     * 查询单位的累计应收和累计应付，零售不能计入
     * @param supplierId
     * @param endTime
     * @param supType
     * @param request
     * @return
     */
    @GetMapping(value = "/findTotalPay")
    public BaseResponseInfo findTotalPay(@RequestParam("supplierId") Integer supplierId,
                                                 @RequestParam("endTime") String endTime,
                                                 @RequestParam("supType") String supType,
                                                 HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject outer = new JSONObject();
            BigDecimal sum = depotHeadService.findTotalPay(supplierId, endTime, supType);
            outer.put("getAllMoney", sum);
            map.put("rows", outer);
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
        BigDecimal preTotalPrice = body.getPreTotalPrice();
        depotHeadService.updateDepotHeadAndDetail(beanJson,rows,preTotalPrice,tenantId,request);
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
            String today = Tools.getNow() + " 00:00:00";
            String firstDay = Tools.getCurrentMonth() + "-01 00:00:00";
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
}
