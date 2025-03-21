package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.base.BaseController;
import com.jsh.erp.base.TableDataInfo;
import com.jsh.erp.datasource.entities.Account;
import com.jsh.erp.datasource.vo.AccountVo4InOutList;
import com.jsh.erp.datasource.vo.AccountVo4List;
import com.jsh.erp.service.AccountService;
import com.jsh.erp.service.SystemConfigService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;
import static com.jsh.erp.utils.ResponseJsonUtil.returnStr;

/**
 * @author jishenghua 75271*8920
 */
@RestController
@RequestMapping(value = "/account")
@Api(tags = {"账户管理"})
public class AccountController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AccountService accountService;

    @Resource
    private SystemConfigService systemConfigService;

    @GetMapping(value = "/info")
    @ApiOperation(value = "根据id获取信息")
    public String getList(@RequestParam("id") Long id,
                          HttpServletRequest request) throws Exception {
        Account account = accountService.getAccount(id);
        Map<String, Object> objectMap = new HashMap<>();
        if(account != null) {
            objectMap.put("info", account);
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取信息列表")
    public TableDataInfo getList(@RequestParam(value = Constants.SEARCH, required = false) String search,
                                 HttpServletRequest request)throws Exception {
        String name = StringUtil.getInfo(search, "name");
        String serialNo = StringUtil.getInfo(search, "serialNo");
        String remark = StringUtil.getInfo(search, "remark");
        List<AccountVo4List> list = accountService.select(name, serialNo, remark);
        return getDataTable(list);
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "新增")
    public String addResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int insert = accountService.insertAccount(obj, request);
        return returnStr(objectMap, insert);
    }

    @PutMapping(value = "/update")
    @ApiOperation(value = "修改")
    public String updateResource(@RequestBody JSONObject obj, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int update = accountService.updateAccount(obj, request);
        return returnStr(objectMap, update);
    }

    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除")
    public String deleteResource(@RequestParam("id") Long id, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = accountService.deleteAccount(id, request);
        return returnStr(objectMap, delete);
    }

    @DeleteMapping(value = "/deleteBatch")
    @ApiOperation(value = "批量删除")
    public String batchDeleteResource(@RequestParam("ids") String ids, HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int delete = accountService.batchDeleteAccount(ids, request);
        return returnStr(objectMap, delete);
    }

    @GetMapping(value = "/checkIsNameExist")
    @ApiOperation(value = "检查名称是否存在")
    public String checkIsNameExist(@RequestParam Long id, @RequestParam(value ="name", required = false) String name,
                                   HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<>();
        int exist = accountService.checkIsNameExist(id, name);
        if(exist > 0) {
            objectMap.put("status", true);
        } else {
            objectMap.put("status", false);
        }
        return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
    }

    /**
     * 查找结算账户信息-下拉框
     * @param request
     * @return
     */
    @GetMapping(value = "/findBySelect")
    @ApiOperation(value = "查找结算账户信息-下拉框")
    public String findBySelect(HttpServletRequest request) throws Exception {
        String res = null;
        try {
            List<Account> dataList = accountService.findBySelect();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Account account : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", account.getId());
                    //结算账户名称
                    item.put("AccountName", account.getName());
                    dataArray.add(item);
                }
            }
            res = dataArray.toJSONString();
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res = "获取数据失败";
        }
        return res;
    }

    /**
     * 获取所有结算账户
     * @param request
     * @return
     */
    @GetMapping(value = "/getAccount")
    @ApiOperation(value = "获取所有结算账户")
    public BaseResponseInfo getAccount(HttpServletRequest request) throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<Account> accountList = accountService.getAccount();
            map.put("accountList", accountList);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 账户流水信息
     * @param currentPage
     * @param pageSize
     * @param accountId
     * @param initialAmount
     * @param request
     * @return
     */
    @GetMapping(value = "/findAccountInOutList")
    @ApiOperation(value = "账户流水信息")
    public BaseResponseInfo findAccountInOutList(@RequestParam("currentPage") Integer currentPage,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam("accountId") Long accountId,
                                                 @RequestParam("initialAmount") BigDecimal initialAmount,
                                                 @RequestParam(value = "number",required = false) String number,
                                                 @RequestParam(value = "beginTime",required = false) String beginTime,
                                                 @RequestParam(value = "endTime",required = false) String endTime,
                                                 HttpServletRequest request) throws Exception{
        BaseResponseInfo res = new BaseResponseInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Boolean forceFlag = systemConfigService.getForceApprovalFlag();
            List<AccountVo4InOutList> dataList = accountService.findAccountInOutList(accountId, StringUtil.toNull(number),
                    beginTime, endTime, forceFlag, (currentPage-1)*pageSize, pageSize);
            int total = accountService.findAccountInOutListCount(accountId, StringUtil.toNull(number),
                    beginTime, endTime, forceFlag);
            map.put("total", total);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (AccountVo4InOutList aEx : dataList) {
                    String type = aEx.getType().replace("其它", "");
                    aEx.setType(type);
                    String operTime = aEx.getOperTime();
                    BigDecimal balance = accountService.getAccountSum(accountId, null, operTime, forceFlag)
                            .add(accountService.getAccountSumByHead(accountId, null, operTime, forceFlag))
                            .add(accountService.getAccountSumByDetail(accountId, null, operTime, forceFlag))
                            .add(accountService.getManyAccountSum(accountId, null, operTime, forceFlag)).add(initialAmount);
                    aEx.setBalance(balance);
                    aEx.setAccountId(accountId);
                    dataArray.add(aEx);
                }
            }
            map.put("rows", dataArray);
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 更新默认账户
     * @param object
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/updateIsDefault")
    @ApiOperation(value = "更新默认账户")
    public String updateIsDefault(@RequestBody JSONObject object,
                                       HttpServletRequest request) throws Exception{
        Long accountId = object.getLong("id");
        Map<String, Object> objectMap = new HashMap<>();
        int res = accountService.updateIsDefault(accountId);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    /**
     * 获取带余额的报表
     * @param request
     * @return
     */
    @GetMapping(value = "/listWithBalance")
    @ApiOperation(value = "获取带余额的报表")
    public TableDataInfo listWithBalance(@RequestParam("name") String name,
                                            @RequestParam("serialNo") String serialNo,
                                            HttpServletRequest request) throws Exception {
        List<AccountVo4List> list = accountService.listWithBalance(StringUtil.toNull(name), StringUtil.toNull(serialNo));
        return getDataTable(list);
    }

    /**
     * 结算账户的统计
     * @param request
     * @return
     */
    @GetMapping(value = "/getStatistics")
    @ApiOperation(value = "结算账户的统计")
    public BaseResponseInfo getStatistics(@RequestParam("name") String name,
                                          @RequestParam("serialNo") String serialNo,
                                          HttpServletRequest request) throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();
        try {
            Map<String, Object> map = accountService.getStatistics(StringUtil.toNull(name), StringUtil.toNull(serialNo));
            res.code = 200;
            res.data = map;
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            res.code = 500;
            res.data = "获取数据失败";
        }
        return res;
    }

    /**
     * 批量设置状态-启用或者禁用
     * @param jsonObject
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    @ApiOperation(value = "批量设置状态")
    public String batchSetStatus(@RequestBody JSONObject jsonObject,
                                 HttpServletRequest request)throws Exception {
        Boolean status = jsonObject.getBoolean("status");
        String ids = jsonObject.getString("ids");
        Map<String, Object> objectMap = new HashMap<>();
        int res = accountService.batchSetStatus(status, ids);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }
}
