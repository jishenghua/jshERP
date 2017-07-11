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
import com.jsh.model.po.Supplier;
import com.jsh.model.vo.basic.SupplierModel;
import com.jsh.service.basic.SupplierIService;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
/*
 * 单位管理
 * @author jishenghua  qq:752718920
*/
@SuppressWarnings("serial")
public class SupplierAction extends BaseAction<SupplierModel>
{
	private SupplierIService supplierService;
	private SupplierModel model = new SupplierModel();
	private final static Integer ISYSTEM = 1;
	/**
	 * 增加供应商
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加供应商方法===================");
	    Boolean flag = false;
		try
		{
		    Supplier supplier = new Supplier();
		    supplier.setContacts(model.getContacts());
		    supplier.setType(model.getType());
		    supplier.setDescription(model.getDescription());
		    supplier.setEmail(model.getEmail());
            supplier.setAdvanceIn(0.0);
            supplier.setBeginNeedGet(model.getBeginNeedGet());
            supplier.setBeginNeedPay(model.getBeginNeedPay());
		    supplier.setIsystem((short)1);
		    supplier.setPhonenum(model.getPhonenum());
		    supplier.setSupplier(model.getSupplier());
		    supplier.setEnabled(model.getEnabled());
			supplierService.create(supplier);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加供应商异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加供应商回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加供应商", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加供应商名称为  "+ model.getSupplier() + " " + tipMsg + "！", "增加供应商" + tipMsg));
		Log.infoFileSync("==================结束调用增加供应商方法===================");
	}
	
	/**
	 * 删除供应商
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除供应商信息方法delete()================");
	    try 
	    {
            supplierService.delete(model.getSupplierID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getSupplierID() + "  的供应商异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除供应商", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除供应商ID为  "+ model.getSupplierID() + ",名称为  " + model.getSupplier() + tipMsg + "！", "删除供应商" + tipMsg));
	    Log.infoFileSync("====================结束调用删除供应商信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新供应商
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
            Supplier supplier = supplierService.get(model.getSupplierID());
            supplier.setContacts(model.getContacts());
            supplier.setType(model.getType());
            supplier.setDescription(model.getDescription());
            supplier.setEmail(model.getEmail());
            supplier.setAdvanceIn(supplier.getAdvanceIn());
            supplier.setBeginNeedGet(model.getBeginNeedGet());
            supplier.setBeginNeedPay(model.getBeginNeedPay());
            supplier.setIsystem((short)1);
            supplier.setPhonenum(model.getPhonenum());
            supplier.setSupplier(model.getSupplier());
            supplier.setEnabled(model.getEnabled());
            supplierService.update(supplier);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改供应商ID为 ： " + model.getSupplierID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改供应商回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新供应商", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新供应商ID为  "+ model.getSupplierID() + " " + tipMsg + "！", "更新供应商" + tipMsg));
	}

    /**
     * 更新供应商-只更新预付款，其余用原来的值
     * @return
     */
    public void updateAdvanceIn()
    {
        Boolean flag = false;
        try
        {
            Supplier supplier = supplierService.get(model.getSupplierID());
            supplier.setContacts(supplier.getContacts());
            supplier.setType(supplier.getType());
            supplier.setDescription(supplier.getDescription());
            supplier.setEmail(supplier.getEmail());
            supplier.setAdvanceIn(supplier.getAdvanceIn() + model.getAdvanceIn()); //增加预收款的金额，可能增加的是负值
            supplier.setBeginNeedGet(supplier.getBeginNeedGet());
            supplier.setBeginNeedPay(supplier.getBeginNeedPay());
            supplier.setIsystem((short)1);
            supplier.setPhonenum(supplier.getPhonenum());
            supplier.setSupplier(supplier.getSupplier());
            supplier.setEnabled(supplier.getEnabled());
            supplierService.update(supplier);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改供应商ID为 ： " + model.getSupplierID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改供应商回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新供应商预付款", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新供应商ID为  "+ model.getSupplierID() + " " + tipMsg + "！", "更新供应商" + tipMsg));
    }
	
	/**
	 * 批量删除指定ID供应商
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
            supplierService.batchDelete(model.getSupplierIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除供应商ID为：" + model.getSupplierIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除供应商", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除供应商ID为  "+ model.getSupplierIDs() + " " + tipMsg + "！", "批量删除供应商" + tipMsg));
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
	        flag = supplierService.checkIsNameExist("supplier",model.getSupplier(),"id", model.getSupplierID());
        } 
	    catch (DataAccessException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查供应商名称为：" + model.getSupplier() + " ID为： " + model.getSupplierID() + " 是否存在异常！");
        }
	    finally
	    {
	        try 
	        {
                toClient(flag.toString());
            }
	        catch (IOException e) 
	        {
                Log.errorFileSync(">>>>>>>>>>>>回写检查供应商名称为：" + model.getSupplier() + " ID为： " + model.getSupplierID() + " 是否存在异常！",e);
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
	        PageUtil<Supplier> pageUtil = new  PageUtil<Supplier>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Supplier supplier:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //供应商名称
                    item.put("supplier", supplier.getSupplier());
                    item.put("type", supplier.getType());
                    item.put("contacts",supplier.getContacts());
                    item.put("phonenum", supplier.getPhonenum());
                    item.put("email", supplier.getEmail());
                    item.put("AdvanceIn",supplier.getAdvanceIn());
                    item.put("BeginNeedGet",supplier.getBeginNeedGet());
                    item.put("BeginNeedPay",supplier.getBeginNeedPay());
                    item.put("isystem", supplier.getIsystem() == (short)0?"是":"否");
                    item.put("description", supplier.getDescription());
                    item.put("enabled", supplier.getEnabled());
                    item.put("op", supplier.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>查找供应商信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>回写查询供应商信息结果异常", e);
        }
	}
    
	/**
	 * 查找供应商信息-下拉框
	 * @return
	 */
    public void findBySelect_sup()
	{
	    try 
	    {
	        PageUtil<Supplier> pageUtil = new  PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select_sup());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Supplier supplier:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //供应商名称
                    item.put("supplier", supplier.getSupplier());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>查找供应商信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>回写查询供应商信息结果异常", e);
        }
	}
	/**
	 * 查找客户信息-下拉框
	 * @return
	 */
    public void findBySelect_cus()
	{
	    try 
	    {
	        PageUtil<Supplier> pageUtil = new  PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select_cus());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Supplier supplier:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //客户名称
                    item.put("supplier", supplier.getSupplier());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>查找客户信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息结果异常", e);
        }
	}

    /**
     * 查找散户信息-下拉框
     * @return
     */
    public void findBySelect_retail()
    {
        try
        {
            PageUtil<Supplier> pageUtil = new  PageUtil<Supplier>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select_retail());
            supplierService.find(pageUtil);
            List<Supplier> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Supplier supplier:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("id", supplier.getId());
                    //客户名称
                    item.put("supplier", supplier.getSupplier());
                    item.put("advanceIn", supplier.getAdvanceIn()); //预付款金额
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>查找客户信息异常", e);
        }
        catch (IOException e)
        {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息结果异常", e);
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
        condition.put("supplier_s_like", model.getSupplier());
        condition.put("type_s_like", model.getType());
        condition.put("contacts_s_like", model.getContacts());
        condition.put("phonenum_s_like", model.getPhonenum());
        condition.put("email_s_like", model.getEmail());
        condition.put("description_s_like", model.getDescription());
        condition.put("isystem_n_eq", ISYSTEM);
        condition.put("id_s_order", "desc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-下拉框-供应商
	 * @return
	 */
	private Map<String,Object> getCondition_Select_sup()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("type_s_like", "供应商");
        condition.put("id_s_order", "desc");
        return condition;
    }

	/**
	 * 拼接搜索条件-下拉框-客户
	 * @return
	 */
	private Map<String,Object> getCondition_Select_cus()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("type_s_like", "客户");
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 拼接搜索条件-下拉框-散户
     * @return
     */
    private Map<String,Object> getCondition_Select_retail()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("type_s_like", "散户");
        condition.put("id_s_order", "desc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public SupplierModel getModel()
	{
		return model;
	}
	public void setSupplierService(SupplierIService supplierService)
	{
		this.supplierService = supplierService;
	}
}
