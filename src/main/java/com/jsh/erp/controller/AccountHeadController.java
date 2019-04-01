package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.AccountHead;
import com.jsh.erp.datasource.entities.AccountHeadVo4ListEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.accountHead.AccountHeadService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author jishenghua 752*718*920
 */
@RestController
@RequestMapping(value = "/accountHead")
public class AccountHeadController {
    private Logger logger = LoggerFactory.getLogger(AccountHeadController.class);

    @Resource
    private AccountHeadService accountHeadService;

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
            Long maxId = accountHeadService.getMaxId();
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
     * 查询单位的累计应收和累计应付，收预付款不计入此处
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
            //收付款部分
            sum = sum.add((allMoney(getS, "付款", "合计",endTime).add(allMoney(getS, "付款", "实际",endTime))).multiply(new BigDecimal(i)));
            sum = sum.subtract((allMoney(getS, "收款", "合计",endTime).add(allMoney(getS, "收款", "实际",endTime))).multiply(new BigDecimal(i)));
            sum = sum.add((allMoney(getS, "收入", "合计",endTime).subtract(allMoney(getS, "收入", "实际",endTime))).multiply(new BigDecimal(i)));
            sum = sum.subtract((allMoney(getS, "支出", "合计",endTime).subtract(allMoney(getS, "支出", "实际",endTime))).multiply(new BigDecimal(i)));
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
     * @param billNo
     * @param request
     * @return
     */
    @GetMapping(value = "/getDetailByNumber")
    public BaseResponseInfo getDetailByNumber(@RequestParam("billNo") String billNo,
                                              HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        AccountHeadVo4ListEx ahl = new AccountHeadVo4ListEx();
        try {
            List<AccountHeadVo4ListEx> list = accountHeadService.getDetailByNumber(billNo);
            if(list.size() == 1) {
                ahl = list.get(0);
            }
            res.code = 200;
            res.data = ahl;
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
     * @param mode 合计或者金额
     * @param endTime
     * @return
     */
    public BigDecimal allMoney(String getS, String type, String mode, String endTime) {
        BigDecimal allMoney = BigDecimal.ZERO;
        try {
            Integer supplierId = Integer.valueOf(getS);
            BigDecimal sum = accountHeadService.findAllMoney(supplierId, type, mode, endTime);
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
    /**
     * create by: qiankunpingtai
     * website：http://39.105.146.63/symphony/
     * description:
     *  批量删除账户信息
     * create time: 2019/3/29 10:49
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteAccountHeadByIds")
    public Object batchDeleteAccountHeadByIds(@RequestParam("ids") String ids) throws Exception {

        JSONObject result = ExceptionConstants.standardSuccess();
        int i= accountHeadService.batchDeleteAccountHeadByIds(ids);
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.ACCOUNT_HEAD_DELETE_FAILED_CODE,ExceptionConstants.ACCOUNT_HEAD_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.ACCOUNT_HEAD_DELETE_FAILED_CODE,
                    ExceptionConstants.ACCOUNT_HEAD_DELETE_FAILED_MSG);
        }
        return result;
    }

}
