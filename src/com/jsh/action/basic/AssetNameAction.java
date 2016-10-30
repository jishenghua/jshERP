package com.jsh.action.basic;

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
import com.jsh.model.po.Assetname;
import com.jsh.model.po.Category;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.AssetNameModel;
import com.jsh.service.basic.AssetNameIService;
import com.jsh.util.common.PageUtil;

@SuppressWarnings("serial")
public class AssetNameAction extends BaseAction<AssetNameModel>
{
    private AssetNameModel model = new AssetNameModel();
    
    private AssetNameIService assetnameService;
    /**
     * 增加资产名称
     * @return
     */
    public void create()
    {
        Log.infoFileSync("==================开始调用增加资产名称方法create()===================");
        Boolean flag = false;
        try
        {
            Assetname assetname = new Assetname();
            assetname.setAssetname(model.getAssetName());
            //增加资产类型
            assetname.setCategory(new Category(model.getCategoryID()));
            
            assetname.setIsystem((short)1);
            assetname.setIsconsumables(model.getConsumable());
            assetname.setDescription(model.getDescription());
            assetnameService.create(assetname);
            
            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加资产名称异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加资产名称回写客户端结果异常", e);
            }
        }
        
        logService.create(new Logdetails(getUser(), "增加资产名称", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加资产名称名称为  "+ model.getAssetName() + " " + tipMsg + "！", "增加资产名称" + tipMsg));
        Log.infoFileSync("==================结束调用增加资产名称方法create()===================");
    }
    
    /**
     * 删除资产名称
     * @return
     */
    public String delete()
    {
        Log.infoFileSync("====================开始调用删除资产名称信息方法delete()================");
        try 
        {
            assetnameService.delete(model.getAssetNameID());
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getAssetNameID() + "  的资产名称异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除资产名称", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "删除资产名称ID为  "+ model.getAssetNameID() + " " + tipMsg + "！", "删除资产名称" + tipMsg));
        Log.infoFileSync("====================结束调用删除资产名称信息方法delete()================");
        return SUCCESS;
    }
    
    /**
     * 更新资产名称
     * @return
     */
    public void update()
    {
        Boolean flag = false;
        try
        {
            Assetname assetname = assetnameService.get(model.getAssetNameID());
            //增加资产类型
            assetname.setCategory(new Category(model.getCategoryID()));
            assetname.setAssetname(model.getAssetName());
            assetname.setIsconsumables(model.getConsumable());
            assetname.setDescription(model.getDescription());
            assetnameService.update(assetname);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改资产名称ID为 ： " + model.getAssetNameID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改资产名称回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新资产名称", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新资产名称ID为  "+ model.getAssetNameID() + " " + tipMsg + "！", "更新资产名称" + tipMsg));
    }
    
    /**
     * 批量删除指定ID资产名称
     * @return
     */
    public String batchDelete()
    {
        try
        {
            assetnameService.batchDelete(model.getAssetNameIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>批量删除资产名称ID为：" + model.getAssetNameIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        
        logService.create(new Logdetails(getUser(), "批量删除资产名称", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除资产名称ID为  "+ model.getAssetNameIDs() + " " + tipMsg + "！", "批量删除资产名称" + tipMsg));
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
            flag = assetnameService.checkIsNameExist("assetname",model.getAssetName(),"id", model.getAssetNameID());
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查资产名称名称为：" + model.getAssetName() + " ID为： " + model.getAssetNameID() + " 是否存在异常！");
        }
        finally
        {
            try 
            {
                toClient(flag.toString());
            }
            catch (IOException e) 
            {
                Log.errorFileSync(">>>>>>>>>>>>回写检查资产名称名称为：" + model.getAssetName() + " ID为： " + model.getAssetNameID() + " 是否存在异常！",e);
            }
        }
    }
    
    /**
     * 查找供应商信息
     * @return
     */
    public void findBy()
    {
        try 
        {
            PageUtil<Assetname> pageUtil = new  PageUtil<Assetname>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            assetnameService.find(pageUtil);
            List<Assetname> dataList = pageUtil.getPageList();
            
            //开始拼接json数据
//            {"total":28,"rows":[
//                {"productid":"AV-CB-01","attr1":"Adult Male","itemid":"EST-18"}
//            ]}
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Assetname assetname:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", assetname.getId());
                    //供应商名称
                    item.put("assetname", assetname.getAssetname());
                    item.put("isystem", assetname.getIsystem() == (short)0?"是":"否");
                    item.put("consumable", assetname.getIsconsumables() == (short)0?"是":"否");
                    item.put("consumableStatus", assetname.getIsconsumables());
                    item.put("description", assetname.getDescription());
                    item.put("categoryID", assetname.getCategory().getId());
                    item.put("category", assetname.getCategory().getAssetname());
                    item.put("op", assetname.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找资产名称信息异常", e);
        } 
        catch (IOException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询资产名称信息结果异常", e);
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
        condition.put("assetname_s_like", model.getAssetName());
        condition.put("isconsumables_n_eq", model.getConsumable());
        condition.put("description_s_like", model.getDescription());
        condition.put("category.id_n_eq", model.getCategoryID());
        condition.put("id_s_order", "desc");
        return condition;
    }
    
    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public AssetNameModel getModel()
    {
        return model;
    }

    public void setAssetnameService(AssetNameIService assetnameService)
    {
        this.assetnameService = assetnameService;
    }
}
