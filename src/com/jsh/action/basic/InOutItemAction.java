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
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.InOutItem;
import com.jsh.model.vo.basic.InOutItemModel;
import com.jsh.service.basic.InOutItemIService;
import com.jsh.util.common.PageUtil;
/**
 * 收支项目
 * @author ji*sheng*hua
 */
@SuppressWarnings("serial")
public class InOutItemAction extends BaseAction<InOutItemModel>
{
    private InOutItemIService inOutItemService;
    private InOutItemModel model = new InOutItemModel();

    /**
     * 增加收支项目
     * @return
     */
    public void create()
    {
        Log.infoFileSync("==================开始调用增加收支项目方法===================");
        Boolean flag = false;
        try
        {
            InOutItem inOutItem = new InOutItem();
            inOutItem.setName(model.getName());
            inOutItem.setType(model.getType());
            inOutItem.setRemark(model.getRemark());
            inOutItemService.create(inOutItem);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加收支项目异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加收支项目回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "增加收支项目", model.getClientIp(),
                 new Timestamp(System.currentTimeMillis())
        , tipType, "增加收支项目名称为  "+ model.getName() + " " + tipMsg + "！", "增加收支项目" + tipMsg));
        Log.infoFileSync("==================结束调用增加收支项目方法===================");
    }

    /**
     * 删除收支项目
     * @return
     */
    public String delete()
    {
        Log.infoFileSync("====================开始调用删除收支项目信息方法delete()================");
        try 
        {
            inOutItemService.delete(model.getInOutItemID());
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getInOutItemID() + "  的收支项目异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除收支项目", model.getClientIp(),
         new Timestamp(System.currentTimeMillis())
        , tipType, "删除收支项目ID为  "+ model.getInOutItemID() + ",名称为  " + model.getName() + tipMsg + "！", "删除收支项目" + tipMsg));
        Log.infoFileSync("====================结束调用删除收支项目信息方法delete()================");
        return SUCCESS;
    }

    /**
     * 更新收支项目
     * @return
     */
    public void update()
    {
        Boolean flag = false;
        try
        {
            InOutItem inOutItem = inOutItemService.get(model.getInOutItemID());
            inOutItem.setName(model.getName());
            inOutItem.setType(model.getType());
            inOutItem.setRemark(model.getRemark());
            inOutItemService.update(inOutItem);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改收支项目ID为 ： " + model.getInOutItemID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改收支项目回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新收支项目", model.getClientIp(),
        new Timestamp(System.currentTimeMillis())
        , tipType, "更新收支项目ID为  "+ model.getInOutItemID() + " " + tipMsg + "！", "更新收支项目" + tipMsg));
    }

    /**
     * 批量删除指定ID收支项目
     * @return
     */
    public String batchDelete()
    {
        try
        {
            inOutItemService.batchDelete(model.getInOutItemIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>批量删除收支项目ID为：" + model.getInOutItemIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除收支项目", model.getClientIp(),
         new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除收支项目ID为  "+ model.getInOutItemIDs() + " " + tipMsg + "！", "批量删除收支项目" + tipMsg));
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
            flag = inOutItemService.checkIsNameExist("name",model.getName(),"id", model.getInOutItemID());
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查收支项目名称为：" + model.getName() + " ID为： " + model.getInOutItemID() + " 是否存在异常！");
        }
        finally
        {
            try 
            {
                toClient(flag.toString());
            }
            catch (IOException e) 
            {
                Log.errorFileSync(">>>>>>>>>>>>回写检查收支项目名称为：" + model.getName() + " ID为： " + model.getInOutItemID() + " 是否存在异常！",e);
            }
        }
    }

    /**
     * 查找收支项目信息
     * @return
     */
    public void findBy()
    {
        try 
        {
            PageUtil<InOutItem> pageUtil = new  PageUtil<InOutItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            inOutItemService.find(pageUtil);
            List<InOutItem> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(InOutItem inOutItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", inOutItem.getId());
                    //收支项目名称
                    item.put("name", inOutItem.getName());
                    item.put("type", inOutItem.getType());
                    item.put("remark", inOutItem.getRemark());
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
            Log.errorFileSync(">>>>>>>>>查找收支项目信息异常", e);
        } 
        catch (IOException e) 
        {
            Log.errorFileSync(">>>>>>>>>回写查询收支项目信息结果异常", e);
        }
    }

    /**
     * 查找收支项目信息-下拉框
     * @return
     */
    public void findBySelect()
    {
        try 
        {
            PageUtil<InOutItem> pageUtil = new  PageUtil<InOutItem>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_select());
            inOutItemService.find(pageUtil);
            List<InOutItem> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(InOutItem inOutItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", inOutItem.getId());
                    //收支项目名称
                    item.put("name", inOutItem.getName());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>查找收支项目信息异常", e);
        } 
        catch (IOException e) 
        {
            Log.errorFileSync(">>>>>>>>>回写查询收支项目信息结果异常", e);
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
        condition.put("remark_s_like", model.getRemark());
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 拼接搜索条件-下拉框-收支项目
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

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public InOutItemModel getModel()
    {
        return model;
    }
    public void setInOutItemService(InOutItemIService inOutItemService)
    {
        this.inOutItemService = inOutItemService;
    }
}
