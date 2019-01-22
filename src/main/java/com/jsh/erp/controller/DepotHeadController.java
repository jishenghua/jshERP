package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.vo.DepotHeadVo4InDetail;
import com.jsh.erp.datasource.vo.DepotHeadVo4InOutMCount;
import com.jsh.erp.datasource.vo.DepotHeadVo4List;
import com.jsh.erp.datasource.vo.DepotHeadVo4StatementAccount;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @author ji-sheng-hua 752*718*920
 */
@RestController
@RequestMapping(value = "/depotHead")
public class DepotHeadController {
    private Logger logger = LoggerFactory.getLogger(DepotHeadController.class);

    @Resource
    private DepotHeadService depotHeadService;

    /**
     * 批量设置状态-审核或者反审核
     * @param status
     * @param depotHeadIDs
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    public String batchSetStatus(@RequestParam("status") Boolean status,
                                 @RequestParam("depotHeadIDs") String depotHeadIDs,
                                 HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int res = depotHeadService.batchSetStatus(status, depotHeadIDs);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 单据编号生成接口，规则：查找当前类型单据下的当天最大的单据号，并加1
     * @param type
     * @param subType
     * @param beginTime
     * @param endTime
     * @param request
     * @return
     */
    @GetMapping(value = "/buildNumber")
    public BaseResponseInfo buildNumber(@RequestParam("type") String type,
                              @RequestParam("subType") String subType,
                              @RequestParam("beginTime") String beginTime,
                              @RequestParam("endTime") String endTime,
                              HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String number = depotHeadService.buildNumber(type, subType, beginTime, endTime);
            map.put("DefaultNumber", number);
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
     * 获取最大的id
     * @param request
     * @return
     */
    @GetMapping(value = "/getMaxId")
    public BaseResponseInfo getMaxId(HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Long maxId = depotHeadService.getMaxId();
            map.put("maxId", maxId);
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
     * 查找单据_根据月份(报表)
     * @param monthTime
     * @param request
     * @return
     */
    @GetMapping(value = "/findByMonth")
    public BaseResponseInfo findByMonth(@RequestParam("monthTime") String monthTime,
                                        HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotHead> dataList = depotHeadService.findByMonth(monthTime);
            String headId = "";
            if (null != dataList) {
                for (DepotHead depotHead : dataList) {
                    headId = headId + depotHead.getId() + ",";
                }
            }
            if (headId != "") {
                headId = headId.substring(0, headId.lastIndexOf(","));
            }
            map.put("HeadIds", headId);
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
     * 查找统计信息_根据礼品卡(报表)
     * @param projectId
     * @param request
     * @return
     */
    @GetMapping(value = "/findGiftReport")
    public BaseResponseInfo findGiftReport(@RequestParam("projectId") String projectId,
                                        HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotHead> dataList_in = depotHeadService.getDepotHead();
            String headId = "";
            if (null != dataList_in) {
                for (DepotHead depotHead : dataList_in) {
                    headId = headId + depotHead.getId() + ",";
                }
                List<DepotHead> dataList_out = depotHeadService.getDepotHeadGiftOut(projectId);
                if (null != dataList_out) {
                    for (DepotHead depotHead : dataList_out) {
                        headId = headId + depotHead.getId() + ",";
                    }
                }
            }
            if (headId != "") {
                headId = headId.substring(0, headId.lastIndexOf(","));
            }
            map.put("HeadIds", headId);
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
     * 入库出库明细接口
     * @param currentPage
     * @param pageSize
     * @param oId
     * @param pid
     * @param dids
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
                                        @RequestParam("projectId") Integer pid,
                                        @RequestParam("depotIds") String dids,
                                        @RequestParam("beginTime") String beginTime,
                                        @RequestParam("endTime") String endTime,
                                        @RequestParam("type") String type,
                                        HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotHeadVo4InDetail> resList = new ArrayList<DepotHeadVo4InDetail>();
            Integer limitStart = (currentPage-1)*pageSize;
            Integer limitEnd = pageSize;
            List<DepotHeadVo4InDetail> list = depotHeadService.findByAll(beginTime, endTime, type, pid, dids, oId, limitStart, limitEnd);
            int total = depotHeadService.findByAllCount(beginTime, endTime, type, pid, dids, oId);
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
     * @param pid
     * @param dids
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
                                         @RequestParam("projectId") Integer pid,
                                         @RequestParam("depotIds") String dids,
                                         @RequestParam("beginTime") String beginTime,
                                         @RequestParam("endTime") String endTime,
                                         @RequestParam("type") String type,
                                         HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<DepotHeadVo4InOutMCount> resList = new ArrayList<DepotHeadVo4InOutMCount>();
            List<DepotHeadVo4InOutMCount> list = depotHeadService.findInOutMaterialCount(beginTime, endTime, type, pid, dids, oId, currentPage, pageSize);
            int total = depotHeadService.findInOutMaterialCountTotal(beginTime, endTime, type, pid, dids, oId);
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
                                                   HttpServletRequest request) {
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
                    if ((p2 .compareTo(BigDecimal.ZERO))==-1) {
                        p2 = p2.abs();
                    }
                    if (type.equals("采购入库")) {
                        allPrice = p2 .subtract(p1);
                    } else if (type.equals("销售退货入库")) {
                        allPrice = p2 .subtract(p1);
                    } else if (type.equals("销售出库")) {
                        allPrice = p1 .subtract(p2);
                    } else if (type.equals("采购退货出库")) {
                        allPrice = p1 .subtract(p2);
                    } else if (type.equals("付款")) {
                        allPrice = p1.add(p2);
                    } else if (type.equals("收款")) {
                        allPrice = BigDecimal.ZERO.subtract(p1.add(p2));
                    } else if (type.equals("收入")) {
                        allPrice =  p1 .subtract(p2);
                    } else if (type.equals("支出")) {
                        allPrice = p2 .subtract(p1);
                    }
                    dha.setDiscountLastMoney(p1); //金额
                    dha.setChangeAmount(p2); //金额
                    DecimalFormat df = new DecimalFormat(".##");
                    dha.setAllPrice(new BigDecimal(df.format(allPrice .multiply(new BigDecimal(j))))); //计算后的金额
                    dha.setSupplierName(dha.getSupplierName()); //供应商
                    dha.setoTime(dha.getoTime()); //入库出库日期
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
                                                 HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject outer = new JSONObject();
            BigDecimal sum = BigDecimal.ZERO;
            String getS = supplierId.toString();
            int i = 1;
            if (supType.equals("customer")) { //客户
                i = 1;
            } else if (supType.equals("vendor")) { //供应商
                i = -1;
            }
            //进销部分
//            sum = sum - (allMoney(getS, "入库", "采购", "合计",endTime) - allMoney(getS, "入库", "采购", "实际",endTime)) * i;
            sum = sum.subtract((allMoney(getS, "入库", "采购", "合计",endTime).subtract(allMoney(getS, "入库", "采购", "实际",endTime))).multiply(new BigDecimal(i)));
//            sum = sum - (allMoney(getS, "入库", "销售退货", "合计",endTime) - allMoney(getS, "入库", "销售退货", "实际",endTime)) * i;
            sum = sum.subtract((allMoney(getS, "入库", "销售退货", "合计",endTime).subtract(allMoney(getS, "入库", "销售退货", "实际",endTime))).multiply(new BigDecimal(i)));
//            sum = sum + (allMoney(getS, "出库", "销售", "合计",endTime) - allMoney(getS, "出库", "销售", "实际",endTime)) * i;
            sum = sum.add((allMoney(getS, "出库", "销售", "合计",endTime).subtract(allMoney(getS, "出库", "销售", "实际",endTime))).multiply(new BigDecimal(i)));
//            sum = sum + (allMoney(getS, "出库", "采购退货", "合计",endTime) - allMoney(getS, "出库", "采购退货", "实际",endTime)) * i;
            sum = sum.add((allMoney(getS, "出库", "采购退货", "合计",endTime).subtract(allMoney(getS, "出库", "采购退货", "实际",endTime))).multiply(new BigDecimal(i)));
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
                                         HttpServletRequest request) {
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
     * 统计总金额
     * @param getS
     * @param type
     * @param subType
     * @param mode 合计或者金额
     * @return
     */
    public BigDecimal allMoney(String getS, String type, String subType, String mode, String endTime) {
        BigDecimal allMoney = BigDecimal.ZERO;
        try {
            Integer supplierId = Integer.valueOf(getS);
            BigDecimal sum = depotHeadService.findAllMoney(supplierId, type, subType, mode, endTime);
            if(sum != null) {
                allMoney = sum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回正数，如果负数也转为正数
        if ((allMoney.compareTo(BigDecimal.ZERO))==-1) {
            allMoney = allMoney.abs();
        }
        return allMoney;
    }

}
