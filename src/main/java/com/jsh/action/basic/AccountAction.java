package com.jsh.action.basic;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.*;

import com.jsh.model.po.AccountHead;
import com.jsh.model.po.AccountItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;
import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Account;
import com.jsh.model.vo.basic.AccountModel;
import com.jsh.service.basic.AccountIService;
import com.jsh.service.materials.DepotHeadIService;
import com.jsh.service.materials.AccountHeadIService;
import com.jsh.service.materials.AccountItemIService;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
/**
 * 结算账户
 * @author ji sheng hua qq7527-18920
 */
@SuppressWarnings("serial")
public class AccountAction extends BaseAction<AccountModel>
{
    private AccountIService accountService;
    private DepotHeadIService depotHeadService;
    private AccountHeadIService accountHeadService;
    private AccountItemIService accountItemService;
	private AccountModel model = new AccountModel();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getAccount()
    {
        Map<String,List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try
        {
            Map<String,Object> condition = pageUtil.getAdvSearch();
            condition.put("Id_s_order", "asc");
            accountService.find(pageUtil);
            mapData.put("accountList", pageUtil.getPageList());
        }
        catch (Exception e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>查找账户信息异常", e);
            model.getShowModel().setMsgTip("exception");
        }
        return SUCCESS;
    }
	
    /**
     * 增加结算账户
     * @return
     */
    public void create()
    {
        Log.infoFileSync("==================开始调用增加结算账户方法===================");
        Boolean flag = false;
        try
        {
            Account Account = new Account();
            Account.setName(model.getName());
            Account.setSerialNo(model.getSerialNo());
            Account.setInitialAmount(model.getInitialAmount()!=null ? model.getInitialAmount() : 0);
            Account.setCurrentAmount(model.getCurrentAmount());
            Account.setRemark(model.getRemark());
            accountService.create(Account);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加结算账户异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        }
        finally
        {
            try 
            {
                toClient(flag.toString());
            } 
            catch (IOException e) 
            {
                Log.errorFileSync(">>>>>>>>>>>>增加结算账户回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "增加结算账户", model.getClientIp(),
                                         new Timestamp(System.currentTimeMillis())
                                         , tipType, "增加结算账户名称为  "+ model.getName() + " " + tipMsg + "！", "增加结算账户" + tipMsg));
        Log.infoFileSync("==================结束调用增加结算账户方法===================");
    }

    /**
     * 删除结算账户
     * @return
     */
    public String delete()
    {
        Log.infoFileSync("====================开始调用删除结算账户信息方法delete()================");
        try 
        {
            accountService.delete(model.getAccountID());
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getAccountID() + "  的结算账户异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除结算账户", model.getClientIp(),
                                         new Timestamp(System.currentTimeMillis())
                                         , tipType, "删除结算账户ID为  "+ model.getAccountID() + ",名称为  " + model.getName() + tipMsg + "！", "删除结算账户" + tipMsg));
        Log.infoFileSync("====================结束调用删除结算账户信息方法delete()================");
        return SUCCESS;
    }

    /**
     * 更新结算账户
     * @return
     */
    public void update()
    {
        Boolean flag = false;
        try
        {
            Account Account = accountService.get(model.getAccountID());
            Account.setName(model.getName());
            Account.setSerialNo(model.getSerialNo());
            Account.setInitialAmount(model.getInitialAmount()!=null ? model.getInitialAmount() : 0);
            Account.setCurrentAmount(model.getCurrentAmount());
            Account.setRemark(model.getRemark());
            accountService.update(Account);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改结算账户ID为 ： " + model.getAccountID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        }
        finally
        {
            try 
            {
                toClient(flag.toString());
            } 
            catch (IOException e) 
            {
                Log.errorFileSync(">>>>>>>>>>>>修改结算账户回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新结算账户", model.getClientIp(),
                                         new Timestamp(System.currentTimeMillis())
                                         , tipType, "更新结算账户ID为  "+ model.getAccountID() + " " + tipMsg + "！", "更新结算账户" + tipMsg));
    }
    
    /**
     * 更新结算账户金额
     * @return
     */
    public void updateAmount()
    {
        Boolean flag = false;
        try
        {
            Account Account = accountService.get(model.getAccountID());
            Account.setCurrentAmount(model.getCurrentAmount());
            accountService.update(Account);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改结算账户ID为 ： " + model.getAccountID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        }
        finally
        {
            try 
            {
                toClient(flag.toString());
            } 
            catch (IOException e) 
            {
                Log.errorFileSync(">>>>>>>>>>>>修改结算账户回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新结算账户", model.getClientIp(),
                                         new Timestamp(System.currentTimeMillis())
                                         , tipType, "更新结算账户ID为  "+ model.getAccountID() + " " + tipMsg + "！", "更新结算账户" + tipMsg));
    }

    /**
     * 批量删除指定ID结算账户
     * @return
     */
    public String batchDelete()
    {
        try
        {
            accountService.batchDelete(model.getAccountIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>批量删除结算账户ID为：" + model.getAccountIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除结算账户", model.getClientIp(),
                                         new Timestamp(System.currentTimeMillis())
                                         , tipType, "批量删除结算账户ID为  "+ model.getAccountIDs() + " " + tipMsg + "！", "批量删除结算账户" + tipMsg));
        return SUCCESS;
    }

    /**
     * 检查输入名称是否存在
     */
    public void checkIsNameExist()
    {
        Boolean flag = false;
        try 
        {
            flag = accountService.checkIsNameExist("name", model.getName(), "id", model.getAccountID());
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查结算账户名称为：" + model.getName() + " ID为： " + model.getAccountID() + " 是否存在异常！");
        }
        finally
        {
            try 
            {
                toClient(flag.toString());
            }
            catch (IOException e) 
            {
                Log.errorFileSync(">>>>>>>>>>>>回写检查结算账户名称为：" + model.getName() + " ID为： " + model.getAccountID() + " 是否存在异常！",e);
            }
        }
    }

    /**
     * 查找结算账户信息
     * @return
     */
    public void findBy()
    {
        try 
        {
            PageUtil<Account> pageUtil = new  PageUtil<Account>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            accountService.find(pageUtil);
            List<Account> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Account account:dataList)
                {
                    DecimalFormat df =new DecimalFormat(".##");
                    JSONObject item = new JSONObject();
                    item.put("id", account.getId());
                    //结算账户名称
                    item.put("name", account.getName());
                    item.put("serialNo", account.getSerialNo());
                    item.put("initialAmount", account.getInitialAmount());
                    String monthTime = Tools.getCurrentMonth();
                    Double thisMonthAmount = getAccountSum(account.getId(), monthTime) + getAccountSumByHead(account.getId(), monthTime) +getAccountSumByDetail(account.getId(), monthTime);
                    String thisMonthAmountFmt=df.format(thisMonthAmount);
                    item.put("thisMonthAmount", thisMonthAmountFmt);  //本月发生额
                    Double currentAmount = getAccountSum(account.getId(),"") + getAccountSumByHead(account.getId(), "") + getAccountSumByDetail(account.getId(), "") + account.getInitialAmount();
                    String currentAmountFmt=df.format(currentAmount);
                    item.put("currentAmount", currentAmountFmt);  //当前余额
                    item.put("remark", account.getRemark());
                    item.put("op", 1);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>查找结算账户信息异常", e);
        } 
        catch (IOException e) 
        {
            Log.errorFileSync(">>>>>>>>>回写查询结算账户信息结果异常", e);
        }
    }
    /**
     * 单个账户的金额求和-入库和出库
     * @param id
     * @return
     */
    public Double getAccountSum(Long id,String monthTime){
    	Double accountSum = 0.0;
    	try{	    	
	    	PageUtil<DepotHead> pageUtil = new PageUtil<DepotHead>();
	    	pageUtil.setPageSize(0);
	        pageUtil.setCurPage(0);
	        pageUtil.setAdvSearch(getCondition_getSum(id,monthTime));      
			depotHeadService.find(pageUtil);
			List<DepotHead> dataList = pageUtil.getPageList();
	        if(dataList!= null){
	            for(DepotHead depotHead:dataList){
	                accountSum = accountSum + depotHead.getChangeAmount();
	            }
	        }
        }
        catch (DataAccessException e){
            Log.errorFileSync(">>>>>>>>>查找进销存信息异常", e);
        }
        return accountSum;
    }

    /**
     * 单个账户的金额求和-收入、支出、转账的单据表头的合计
     * @param id
     * @return
     */
    public Double getAccountSumByHead(Long id,String monthTime){
        Double accountSum = 0.0;
        try{
            PageUtil<AccountHead> pageUtil = new PageUtil<AccountHead>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_getSumByHead(id, monthTime));
            accountHeadService.find(pageUtil);
            List<AccountHead> dataList = pageUtil.getPageList();
            if(dataList!= null){
                for(AccountHead accountHead:dataList){
                    accountSum = accountSum + accountHead.getChangeAmount();
                }
            }
        }
        catch (DataAccessException e){
            Log.errorFileSync(">>>>>>>>>查找进销存信息异常", e);
        }
        return accountSum;
    }

    /**
     * 单个账户的金额求和-收款、付款、转账、收预付款的单据明细的合计
     * @param id
     * @return
     */
    public Double getAccountSumByDetail(Long id,String monthTime){
        Double accountSum = 0.0;
        try{
            PageUtil<AccountHead> pageUtil = new PageUtil<AccountHead>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_getSumByHead(monthTime));
            accountHeadService.find(pageUtil);
            List<AccountHead> dataList = pageUtil.getPageList();
            if(dataList!= null){
                String ids = "";
                for(AccountHead accountHead:dataList){
                    ids = ids + accountHead.getId() +",";
                }
                ids = ids.substring(0,ids.length() -1);

                System.out.println(">>>>>>>>>>>>>>>>>" + ids);

                PageUtil<AccountItem> pageUtilOne = new PageUtil<AccountItem>();
                pageUtilOne.setPageSize(0);
                pageUtilOne.setCurPage(0);
                pageUtilOne.setAdvSearch(getCondition_getSumByDetail(id, ids));
                accountItemService.find(pageUtilOne);
                List<AccountItem> dataListOne = pageUtilOne.getPageList();
                if(dataListOne!= null){
                    for(AccountItem accountItem:dataListOne){
                        accountSum = accountSum + accountItem.getEachAmount();
                    }
                }

                System.out.println(">>>>>>>>>>>>>>>>>accountSum：" + accountSum);
            }
        }
        catch (DataAccessException e){
            Log.errorFileSync(">>>>>>>>>查找进销存信息异常", e);
        }
        catch (Exception e){
            Log.errorFileSync(">>>>>>>>>异常信息：", e);
        }
        return accountSum;
    }

    /**
     * 查找结算账户信息-下拉框
     * @return
     */
    public void findBySelect()
    {
        try 
        {
            PageUtil<Account> pageUtil = new  PageUtil<Account>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_select());
            accountService.find(pageUtil);
            List<Account> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Account account:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", account.getId());
                    //结算账户名称
                    item.put("AccountName", account.getName());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>查找结算账户信息异常", e);
        } 
        catch (IOException e) 
        {
            Log.errorFileSync(">>>>>>>>>回写查询结算账户信息结果异常", e);
        }
    }

    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getCondition()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("name_s_like", model.getName());
        condition.put("serialNo_s_like", model.getSerialNo());
        condition.put("remark_s_like", model.getRemark());
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 拼接搜索条件-下拉框-结算账户
     * @return
     */
    private Map<String,Object> getCondition_select()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("id_s_order", "desc");
        return condition;
    }
    
    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getCondition_getSum(Long id,String monthTime)
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("AccountId_n_eq", id);
        condition.put("PayType_s_neq", "预付款");
        if(!monthTime.equals("")){
        	condition.put("OperTime_s_gteq", monthTime + "-01 00:00:00");
            condition.put("OperTime_s_lteq", monthTime + "-31 00:00:00");
        }        
        return condition;
    }

    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getCondition_getSumByHead(Long id,String monthTime)
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("AccountId_n_eq", id);
        if(!monthTime.equals("")){
            condition.put("BillTime_s_gteq", monthTime + "-01 00:00:00");
            condition.put("BillTime_s_lteq", monthTime + "-31 00:00:00");
        }
        return condition;
    }

    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getCondition_getSumByHead(String monthTime)
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        if(!monthTime.equals("")){
            condition.put("BillTime_s_gteq", monthTime + "-01 00:00:00");
            condition.put("BillTime_s_lteq", monthTime + "-31 00:00:00");
        }
        return condition;
    }

    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getCondition_getSumByDetail(Long id, String ids)
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("AccountId_n_eq", id);
        if(!ids.equals("")){
            condition.put("HeaderId_s_in", ids);
        }
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public AccountModel getModel()
    {
        return model;
    }
    public void setAccountService(AccountIService accountService)
    {
        this.accountService = accountService;
    }
    public void setDepotHeadService(DepotHeadIService depotHeadService) {
		this.depotHeadService = depotHeadService;
	}
    public void setAccountHeadService(AccountHeadIService accountHeadService) {
        this.accountHeadService = accountHeadService;
    }
    public void setAccountItemService(AccountItemIService accountItemService) {
        this.accountItemService = accountItemService;
    }
}
