package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.AccountItem;
import com.jsh.erp.datasource.vo.AccountItemVo4List;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.accountItem.AccountItemService;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @author ji sheng hua 752*718*920
 */
@RestController
@RequestMapping(value = "/accountItem")
public class AccountItemController {
    private Logger logger = LoggerFactory.getLogger(AccountItemController.class);

    @Resource
    private AccountItemService accountItemService;
    /**
     * create by: cjl
     * description:
     *  业务逻辑操作放在service层，controller只做参数解析和视图封装
     * create time: 2019/1/11 15:08
     * @Param: inserted
     * @Param: deleted
     * @Param: updated
     * @Param: headerId
     * @Param: listType
     * @Param: request
     * @return java.lang.String
     */
    @PostMapping(value = "/saveDetials")
    public String saveDetials(@RequestParam("inserted") String inserted,
                              @RequestParam("deleted") String deleted,
                              @RequestParam("updated") String updated,
                              @RequestParam("headerId") Long headerId,
                              @RequestParam("listType") String listType,
                              HttpServletRequest request) throws Exception{

        Map<String, Object> objectMap = new HashMap<String, Object>();
        try {
            accountItemService.saveDetials(inserted,deleted,updated,headerId,listType);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error(">>>>>>>>>>>>>>>>>>>保存明细信息异常", e);
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/getDetailList")
    public BaseResponseInfo getDetailList(@RequestParam("headerId") Long headerId,
                                          HttpServletRequest request)throws Exception {
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
                    BigDecimal eachAmount = ai.getEachamount();
                    item.put("EachAmount", (eachAmount.compareTo(BigDecimal.ZERO))==-1 ? BigDecimal.ZERO.subtract(eachAmount): eachAmount);
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
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  批量删除财务明细信息
     * create time: 2019/3/29 10:56
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeleteAccountItemByIds")
    public Object batchDeleteAccountItemByIds(@RequestParam("ids") String ids) throws Exception {

        JSONObject result = ExceptionConstants.standardSuccess();
        int i= accountItemService.batchDeleteAccountItemByIds(ids);
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.ACCOUNT_ITEM_DELETE_FAILED_CODE,ExceptionConstants.ACCOUNT_ITEM_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.ACCOUNT_ITEM_DELETE_FAILED_CODE,
                    ExceptionConstants.ACCOUNT_ITEM_DELETE_FAILED_MSG);
        }
        return result;
    }

}
