package com.jsh.action.materials;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;
import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.AccountHead;
import com.jsh.model.po.AccountItem;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Account;
import com.jsh.model.po.InOutItem;
import com.jsh.model.vo.materials.AccountItemModel;
import com.jsh.service.materials.AccountItemIService;
import com.jsh.util.PageUtil;
/*
 * 财务明细管理(收支|收付款|转账)
 * @author jishenghua  qq:752718920
 */
@SuppressWarnings("serial")
public class AccountItemAction extends BaseAction<AccountItemModel>
{
    private AccountItemIService accountItemService;
    private AccountItemModel model = new AccountItemModel();

    /**
     * 保存明细
     * @return
     */
    public void saveDetials()
    {
        Log.infoFileSync("==================开始调用保存财务明细信息方法saveDetials()===================");
        Boolean flag = false;
        try
        {
            Long headerId=model.getHeaderId();
            String listType = model.getListType(); //单据类型
            String inserted=model.getInserted();
            String deleted=model.getDeleted();
            String updated=model.getUpdated();
            //转为json
            JSONArray insertedJson = JSONArray.fromObject(inserted);
            JSONArray deletedJson = JSONArray.fromObject(deleted);
            JSONArray updatedJson = JSONArray.fromObject(updated);
            if(null != insertedJson)
            {
                for(int i = 0;i < insertedJson.size(); i++)
                {
                    AccountItem accountItem = new AccountItem();
                    JSONObject tempInsertedJson = JSONObject.fromObject(insertedJson.get(i));
                    accountItem.setHeaderId(new AccountHead(headerId));
                    if(tempInsertedJson.get("AccountId")!=null&&!tempInsertedJson.get("AccountId").equals("")){accountItem.setAccountId(new Account(tempInsertedJson.getLong("AccountId")));}
                    if(tempInsertedJson.get("InOutItemId")!=null&&!tempInsertedJson.get("InOutItemId").equals("")){accountItem.setInOutItemId(new InOutItem(tempInsertedJson.getLong("InOutItemId")));}
                    if(tempInsertedJson.get("EachAmount")!=null){
                        Double eachAmount = tempInsertedJson.getDouble("EachAmount");
                        if(listType.equals("付款")) {
                            eachAmount = 0 - eachAmount;
                        }
                        accountItem.setEachAmount(eachAmount);
                    }
                    accountItem.setRemark(tempInsertedJson.getString("Remark"));                    
                    accountItemService.create(accountItem);
                }
            }
            if(null != deletedJson)
            {
                for(int i = 0;i < deletedJson.size(); i++)
                {
                    JSONObject tempDeletedJson = JSONObject.fromObject(deletedJson.get(i));
                    accountItemService.delete(tempDeletedJson.getLong("Id"));
                }
            }
            if(null != updatedJson)
            {
                for(int i = 0;i < updatedJson.size(); i++)
                {
                    JSONObject tempUpdatedJson = JSONObject.fromObject(updatedJson.get(i));                    
                    AccountItem accountItem = accountItemService.get(tempUpdatedJson.getLong("Id")); 
                    accountItem.setHeaderId(new AccountHead(headerId));                    
                    if(tempUpdatedJson.get("AccountId")!=null&&!tempUpdatedJson.get("AccountId").equals("")){accountItem.setAccountId(new Account(tempUpdatedJson.getLong("AccountId")));}
                    if(tempUpdatedJson.get("InOutItemId")!=null&&!tempUpdatedJson.get("InOutItemId").equals("")){accountItem.setInOutItemId(new InOutItem(tempUpdatedJson.getLong("InOutItemId")));}
                    if(tempUpdatedJson.get("EachAmount")!=null){
                        Double eachAmount = tempUpdatedJson.getDouble("EachAmount");
                        if(listType.equals("付款")) {
                            eachAmount = 0 - eachAmount;
                        }
                        accountItem.setEachAmount(eachAmount);
                    }
                    accountItem.setRemark(tempUpdatedJson.getString("Remark"));      
                    accountItemService.create(accountItem);
                }
            }

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>保存财务明细信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>保存财务明细信息回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "保存财务明细", model.getClientIp(),
                   new Timestamp(System.currentTimeMillis())
           , tipType, "保存财务明细对应主表编号为  "+ model.getHeaderId() + " " + tipMsg + "！", "保存财务明细" + tipMsg));
        Log.infoFileSync("==================结束调用保存财务明细方法saveDetials()===================");
    }


	/**
     * 查找财务信息
     * @return
     */
    public void findBy()
    {
        try 
        {
            PageUtil<AccountItem> pageUtil = new  PageUtil<AccountItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            accountItemService.find(pageUtil);
            List<AccountItem> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(AccountItem accountItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", accountItem.getId());
                    item.put("AccountId", accountItem.getAccountId()==null?"":accountItem.getAccountId().getId());
                    item.put("AccountName", accountItem.getAccountId()==null?"":accountItem.getAccountId().getName());
                    item.put("InOutItemId", accountItem.getInOutItemId()==null?"":accountItem.getInOutItemId().getId());
                    item.put("InOutItemName", accountItem.getInOutItemId()==null?"":accountItem.getInOutItemId().getName());
                    Double eachAmount = accountItem.getEachAmount();
                    item.put("EachAmount", eachAmount < 0 ? 0-eachAmount : eachAmount);
                    item.put("Remark", accountItem.getRemark());
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
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找财务信息异常", e);
        } 
        catch (IOException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询财务信息结果异常", e);
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
        condition.put("HeaderId_n_eq", model.getHeaderId());
        condition.put("Id_s_order","asc");
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public AccountItemModel getModel()
    {
        return model;
    }
    public void setAccountItemService(AccountItemIService accountItemService)
    {
        this.accountItemService = accountItemService;
    }
}
