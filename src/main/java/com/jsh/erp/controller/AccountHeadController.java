package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.AccountHeadVo4ListEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.accountHead.AccountHeadService;
import com.jsh.erp.utils.BaseResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public BaseResponseInfo getMaxId(HttpServletRequest request)throws Exception {
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
                                         HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject outer = new JSONObject();
            BigDecimal sum = accountHeadService.findTotalPay(supplierId, endTime, supType);
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
                                              HttpServletRequest request)throws Exception {
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
     *  批量删除账户信息
     * create time: 2019/3/29 10:49
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteAccountHeadByIds")
    public Object batchDeleteAccountHeadByIds(@RequestParam("ids") String ids,@RequestParam(value="deleteType",
            required =false,defaultValue= BusinessConstants.DELETE_TYPE_NORMAL)String deleteType) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        accountHeadService.batchDeleteAccountHeadByIds(ids);
        return result;
    }

}
