package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.AccountItem;
import com.jsh.erp.datasource.vo.AccountItemVo4List;
import com.jsh.erp.service.accountItem.AccountItemService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

@RestController
@RequestMapping(value = "/accountItem")
public class AccountItemController {
    private Logger logger = LoggerFactory.getLogger(AccountItemController.class);

    @Resource
    private AccountItemService accountItemService;

    @PostMapping(value = "/saveDetials")
    public String saveDetials(@RequestParam("inserted") String inserted,
                              @RequestParam("deleted") String deleted,
                              @RequestParam("updated") String updated,
                              @RequestParam("headerId") Long headerId,
                              @RequestParam("listType") String listType,
                              HttpServletRequest request) {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        try {
            //转为json
            JSONArray insertedJson = JSONArray.parseArray(inserted);
            JSONArray deletedJson = JSONArray.parseArray(deleted);
            JSONArray updatedJson = JSONArray.parseArray(updated);
            if (null != insertedJson) {
                for (int i = 0; i < insertedJson.size(); i++) {
                    AccountItem accountItem = new AccountItem();
                    JSONObject tempInsertedJson = JSONObject.parseObject(insertedJson.getString(i));
                    accountItem.setHeaderid(headerId);
                    if (tempInsertedJson.get("AccountId") != null && !tempInsertedJson.get("AccountId").equals("")) {
                        accountItem.setAccountid(tempInsertedJson.getLong("AccountId"));
                    }
                    if (tempInsertedJson.get("InOutItemId") != null && !tempInsertedJson.get("InOutItemId").equals("")) {
                        accountItem.setInoutitemid(tempInsertedJson.getLong("InOutItemId"));
                    }
                    if (tempInsertedJson.get("EachAmount") != null && !tempInsertedJson.get("EachAmount").equals("")) {
                        Double eachAmount = tempInsertedJson.getDouble("EachAmount");
                        if (listType.equals("付款")) {
                            eachAmount = 0 - eachAmount;
                        }
                        accountItem.setEachamount(eachAmount);
                    } else {
                        accountItem.setEachamount(0.0);
                    }
                    accountItem.setRemark(tempInsertedJson.getString("Remark"));
                    accountItemService.insertAccountItemWithObj(accountItem);
                }
            }
            if (null != deletedJson) {
                for (int i = 0; i < deletedJson.size(); i++) {
                    JSONObject tempDeletedJson = JSONObject.parseObject(deletedJson.getString(i));
                    accountItemService.deleteAccountItem(tempDeletedJson.getLong("Id"));
                }
            }
            if (null != updatedJson) {
                for (int i = 0; i < updatedJson.size(); i++) {
                    JSONObject tempUpdatedJson = JSONObject.parseObject(updatedJson.getString(i));
                    AccountItem accountItem = accountItemService.getAccountItem(tempUpdatedJson.getLong("Id"));
                    accountItem.setId(tempUpdatedJson.getLong("Id"));
                    accountItem.setHeaderid(headerId);
                    if (tempUpdatedJson.get("AccountId") != null && !tempUpdatedJson.get("AccountId").equals("")) {
                        accountItem.setAccountid(tempUpdatedJson.getLong("AccountId"));
                    }
                    if (tempUpdatedJson.get("InOutItemId") != null && !tempUpdatedJson.get("InOutItemId").equals("")) {
                        accountItem.setInoutitemid(tempUpdatedJson.getLong("InOutItemId"));
                    }
                    if (tempUpdatedJson.get("EachAmount") != null && !tempUpdatedJson.get("EachAmount").equals("")) {
                        Double eachAmount = tempUpdatedJson.getDouble("EachAmount");
                        if (listType.equals("付款")) {
                            eachAmount = 0 - eachAmount;
                        }
                        accountItem.setEachamount(eachAmount);
                    } else {
                        accountItem.setEachamount(0.0);
                    }
                    accountItem.setRemark(tempUpdatedJson.getString("Remark"));
                    accountItemService.updateAccountItemWithObj(accountItem);
                }
            }
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>>>>>>>>>>保存明细信息异常", e);
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/getDetailList")
    public BaseResponseInfo getDetailList(@RequestParam("headerId") Long headerId,
                                          HttpServletRequest request) {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<AccountItemVo4List> dataList = new ArrayList<AccountItemVo4List>();
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
                    item.put("Id", ai.getId());
                    item.put("AccountId", ai.getAccountid());
                    item.put("AccountName", ai.getAccountName());
                    item.put("InOutItemId", ai.getInoutitemid());
                    item.put("InOutItemName", ai.getInOutItemName());
                    Double eachAmount = ai.getEachamount();
                    item.put("EachAmount", eachAmount < 0 ? 0 - eachAmount : eachAmount);
                    item.put("Remark", ai.getRemark());
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
