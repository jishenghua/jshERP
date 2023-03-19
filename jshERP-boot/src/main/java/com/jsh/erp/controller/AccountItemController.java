package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.vo.AccountItemVo4List;
import com.jsh.erp.service.accountItem.AccountItemService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ji sheng hua 752*718*920
 */
@RestController
@RequestMapping(value = "/accountItem")
@Api(tags = {"财务明细"})
public class AccountItemController {
    private Logger logger = LoggerFactory.getLogger(AccountItemController.class);

    @Resource
    private AccountItemService accountItemService;

    @GetMapping(value = "/getDetailList")
    @ApiOperation(value = "明细列表")
    public BaseResponseInfo getDetailList(@RequestParam("headerId") Long headerId,
                                          HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            List<AccountItemVo4List> dataList = new ArrayList<>();
            if(headerId != 0) {
                dataList = accountItemService.getDetailList(headerId);
            }
            JSONObject outer = new JSONObject();
            outer.put("total", dataList.size());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (AccountItemVo4List ai : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("accountId", ai.getAccountId());
                    item.put("accountName", ai.getAccountName());
                    item.put("inOutItemId", ai.getInOutItemId());
                    item.put("inOutItemName", ai.getInOutItemName());
                    if(StringUtil.isNotEmpty(ai.getBillNumber())) {
                        item.put("billNumber", ai.getBillNumber());
                    } else {
                        item.put("billNumber", "QiChu");
                    }
                    item.put("needDebt", ai.getNeedDebt());
                    item.put("finishDebt", ai.getFinishDebt());
                    BigDecimal eachAmount = ai.getEachAmount();
                    item.put("eachAmount", (eachAmount.compareTo(BigDecimal.ZERO))==-1 ? BigDecimal.ZERO.subtract(eachAmount): eachAmount);
                    item.put("remark", ai.getRemark());
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
}
