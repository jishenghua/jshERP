package com.jsh.action.materials;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsh.model.po.*;
import com.jsh.util.JshException;
import com.jsh.util.MaterialConstants;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;
import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.vo.materials.MaterialModel;
import com.jsh.service.materials.MaterialIService;
import com.jsh.util.PageUtil;
/*
 * 商品管理
 * @author jishenghua  qq:752718920 
*/
@SuppressWarnings("serial")
public class MaterialAction extends BaseAction<MaterialModel>
{
    private MaterialIService materialService;
    private MaterialModel model = new MaterialModel();
    public static final String EXCEL = "excel";  //action返回excel结果
    
	/**
	 * 增加商品
	 * @return
	 */
	public void create()
	{
	    Log.infoFileSync("==================开始调用增加商品信息方法create()===================");
	    Boolean flag = false;
		try
		{
			Material material = new Material();
			material.setMaterialCategory(new MaterialCategory(model.getCategoryId()));
			
			material.setName(model.getName());
            material.setMfrs(model.getMfrs());
            material.setPacking(model.getPacking());
            material.setSafetyStock(model.getSafetyStock());
			material.setModel(model.getModel());
            material.setStandard(model.getStandard());
			material.setColor(model.getColor());
			material.setUnit(model.getUnit());
            material.setRetailPrice(model.getRetailPrice());
            material.setLowPrice(model.getLowPrice());
            material.setPresetPriceOne(model.getPresetPriceOne());
            material.setPresetPriceTwo(model.getPresetPriceTwo());
            if(model.getUnitId()!=null){
                material.setUnitId(new Unit(model.getUnitId()));
            }
            else {
                material.setUnitId(null);
            }
            material.setFirstOutUnit(model.getFirstOutUnit());
            material.setFirstInUnit(model.getFirstInUnit());
            material.setPriceStrategy(model.getPriceStrategy());
			material.setRemark(model.getRemark());
            material.setEnabled(model.getEnabled());
            material.setOtherField1(model.getOtherField1());
            material.setOtherField2(model.getOtherField2());
            material.setOtherField3(model.getOtherField3());
			materialService.create(material);
			
			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加商品信息异常", e);
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
                Log.errorFileSync(">>>>>>>>>>>>增加商品信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "增加商品", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "增加商品名称为  "+ model.getName() + " " + tipMsg + "！", "增加商品" + tipMsg));
		Log.infoFileSync("==================结束调用增加商品方法create()===================");
	}
	
	/**
	 * 删除商品
	 * @return
	 */
	public String delete()
	{
	    Log.infoFileSync("====================开始调用删除商品信息方法delete()================");
	    try 
	    {
	    	materialService.delete(model.getMaterialID());
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getMaterialID() + "  的商品异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    model.getShowModel().setMsgTip(tipMsg);
	    logService.create(new Logdetails(getUser(), "删除商品", model.getClientIp(),
	            new Timestamp(System.currentTimeMillis())
	    , tipType, "删除商品ID为  "+ model.getMaterialID() + " " + tipMsg + "！", "删除商品" + tipMsg));
	    Log.infoFileSync("====================结束调用删除商品信息方法delete()================");
	    return SUCCESS;
	}
	
	/**
	 * 更新商品
	 * @return
	 */
	public void update()
	{
	    Boolean flag = false;
        try
        {
        	Material material = materialService.get(model.getMaterialID());
        	material.setMaterialCategory(new MaterialCategory(model.getCategoryId()));
			
			material.setName(model.getName());
            material.setMfrs(model.getMfrs());
            material.setPacking(model.getPacking());
            material.setSafetyStock(model.getSafetyStock());
			material.setModel(model.getModel());
            material.setStandard(model.getStandard());
			material.setColor(model.getColor());
			material.setUnit(model.getUnit());
            material.setRetailPrice(model.getRetailPrice());
            material.setLowPrice(model.getLowPrice());
            material.setPresetPriceOne(model.getPresetPriceOne());
            material.setPresetPriceTwo(model.getPresetPriceTwo());
            if(model.getUnitId()!=null){
                material.setUnitId(new Unit(model.getUnitId()));
            }
            else {
                material.setUnitId(null);
            }
            material.setFirstOutUnit(model.getFirstOutUnit());
            material.setFirstInUnit(model.getFirstInUnit());
            material.setPriceStrategy(model.getPriceStrategy());
			material.setRemark(model.getRemark());
            material.setOtherField1(model.getOtherField1());
            material.setOtherField2(model.getOtherField2());
            material.setOtherField3(model.getOtherField3());
        	materialService.update(material);
            
            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } 
        catch (DataAccessException e) 
        {
            Log.errorFileSync(">>>>>>>>>>>>>修改商品ID为 ： " + model.getMaterialID() + "信息失败", e);
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
                Log.errorFileSync(">>>>>>>>>>>>修改商品回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新商品", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "更新商品ID为  "+ model.getMaterialID() + " " + tipMsg + "！", "更新商品" + tipMsg));
	}
	
	/**
	 * 批量删除指定ID商品
	 * @return
	 */
	public String batchDelete()
	{
	    try
	    {
	    	materialService.batchDelete(model.getMaterialIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>批量删除商品ID为：" + model.getMaterialIDs() + "信息异常", e);
	        tipMsg = "失败";
            tipType = 1;
        }
	    
	    logService.create(new Logdetails(getUser(), "批量删除商品", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "批量删除商品ID为  "+ model.getMaterialIDs() + " " + tipMsg + "！", "批量删除商品" + tipMsg));
	    return SUCCESS;
	}

    /**
     * 批量设置状态-启用或者禁用
     * @return
     */
    public String batchSetEnable()
    {
        try
        {
            materialService.batchSetEnable(model.getEnabled(),model.getMaterialIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>批量修改状态，商品ID为：" + model.getMaterialIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量修改商品状态", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量修改状态，商品ID为  "+ model.getMaterialIDs() + " " + tipMsg + "！", "批量修改商品状态" + tipMsg));
        return SUCCESS;
    }

    /**
     * 查找该商品是否存在
     * @return
     */
    public void checkIsExist() {
        try {
            Boolean flag = false;
            PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getConditionCheckIsExist());
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            if(null != dataList && dataList.size() > 0){
                flag = true;
            }
            else{
                flag = false;
            }
            //回写查询结果
            toClient(flag.toString());
        }
        catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找商品信息异常", e);
        }
        catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询商品信息结果异常", e);
        }
    }
	
	/**
	 * 查找商品信息
	 * @return
	 */
    public void findBy()
	{
	    try 
	    {
	        PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            Long lei=model.getCategoryId();
            if(1==lei) //判断值还真不能用String类型的判断
            {
            	pageUtil.setAdvSearch(getCondition_all());
            }
            else if(1!=lei)
            {
                pageUtil.setAdvSearch(getCondition());
            }
            materialService.find(pageUtil);
            getSession().put("pageUtilMaterial", pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            String mpList = model.getMpList(); //商品属性
            String[] mpArr = mpList.split(",");
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Material material:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    item.put("Name", material.getName());
                    item.put("CategoryId", material.getMaterialCategory().getId()); //类型Id
                    item.put("CategoryName", material.getMaterialCategory().getName()); //类型名称
                    item.put("Packing", material.getPacking()==null?"" : material.getPacking());
                    item.put("SafetyStock", material.getSafetyStock()==null?"" : material.getSafetyStock());
                    item.put("Model", material.getModel()==null?"" : material.getModel());
                    //扩展信息
                    String materialOther = "";
                    for(int i=0; i< mpArr.length; i++) {
                        if(mpArr[i].equals("颜色")) {
                            materialOther = materialOther + ((material.getColor() == null || material.getColor().equals(""))?"":"("+material.getColor() + ")");
                        }
                        if(mpArr[i].equals("规格")) {
                            materialOther = materialOther + ((material.getStandard() == null || material.getStandard().equals(""))?"":"("+material.getStandard() + ")");
                        }
                        if(mpArr[i].equals("制造商")) {
                            materialOther = materialOther + ((material.getMfrs() == null || material.getMfrs().equals(""))?"":"("+material.getMfrs() + ")");
                        }
                        if(mpArr[i].equals("自定义1")) {
                            materialOther = materialOther + ((material.getOtherField1() == null || material.getOtherField1().equals(""))?"":"("+material.getOtherField1() + ")");
                        }
                        if(mpArr[i].equals("自定义2")) {
                            materialOther = materialOther + ((material.getOtherField2() == null || material.getOtherField2().equals(""))?"":"("+material.getOtherField2() + ")");
                        }
                        if(mpArr[i].equals("自定义3")) {
                            materialOther = materialOther + ((material.getOtherField3() == null || material.getOtherField3().equals(""))?"":"("+material.getOtherField3() + ")");
                        }
                    }
                    item.put("MaterialOther", materialOther);
                    item.put("Unit", material.getUnit()==null?"": material.getUnit());
                    item.put("RetailPrice", material.getRetailPrice());
                    item.put("LowPrice", material.getLowPrice());
                    item.put("PresetPriceOne", material.getPresetPriceOne()==null? "":material.getPresetPriceOne());
                    item.put("PresetPriceTwo", material.getPresetPriceTwo()==null? "":material.getPresetPriceTwo());
                    item.put("UnitId",material.getUnitId()==null? "": material.getUnitId().getId()); //计量单位Id
                    item.put("UnitName", material.getUnitId()==null? "": material.getUnitId().getUName()); //计量单位名称
                    item.put("FirstOutUnit", material.getFirstOutUnit());
                    item.put("FirstInUnit", material.getFirstInUnit());
                    item.put("PriceStrategy", material.getPriceStrategy());
                    item.put("Enabled", material.getEnabled());
                    item.put("Remark", material.getRemark());
                    item.put("Color", material.getColor()==null?"" : material.getColor());
                    item.put("Standard", material.getStandard()==null?"" : material.getStandard());
                    item.put("Mfrs", material.getMfrs()==null?"" : material.getMfrs());
                    item.put("OtherField1", material.getOtherField1()==null?"" : material.getOtherField1());
                    item.put("OtherField2", material.getOtherField2()==null?"" : material.getOtherField2());
                    item.put("OtherField3", material.getOtherField3()==null?"" : material.getOtherField3());
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
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找商品信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询商品信息结果异常", e);
        }
	}

    /**
     * 根据id来查询商品名称
     * @return
     */
    public void findById() {
        try
        {
            PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setAdvSearch(getConditionById());
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(Material material:dataList)
                {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    item.put("Name", material.getName());
                    item.put("Mfrs", material.getMfrs()==null?"" : material.getMfrs());
                    item.put("Packing", material.getPacking()==null?"" : material.getPacking());
                    item.put("SafetyStock", material.getSafetyStock()==null?"" : material.getSafetyStock());
                    item.put("Model", material.getModel());
                    item.put("Standard", material.getStandard());
                    item.put("Color", material.getColor()==null?"": material.getColor());
                    item.put("Unit", material.getUnit());
                    item.put("RetailPrice", material.getRetailPrice());
                    item.put("LowPrice", material.getLowPrice());
                    item.put("PresetPriceOne", material.getPresetPriceOne());
                    item.put("PresetPriceTwo", material.getPresetPriceTwo());
                    item.put("UnitId",material.getUnitId()==null? "": material.getUnitId().getId()); //计量单位Id
                    item.put("UnitName", material.getUnitId()==null? "": material.getUnitId().getUName()); //计量单位名称
                    item.put("FirstOutUnit", material.getFirstOutUnit());
                    item.put("FirstInUnit", material.getFirstInUnit());
                    item.put("PriceStrategy", material.getPriceStrategy());
                    item.put("Remark", material.getRemark());
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
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找商品信息异常", e);
        }
        catch (IOException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询商品信息结果异常", e);
        }
    }

    /**
	 * 查找商品信息-下拉框
	 * @return
	 */
    public void findBySelect() {
	    try {
	        PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Select());
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            String mpList = model.getMpList(); //商品属性
            String[] mpArr = mpList.split(",");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList) {
                for(Material material:dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", material.getId());
                    String ratio; //比例
                    if(material.getUnitId() == null || material.getUnitId().equals("")){
                        ratio = "";
                    }
                    else {
                        ratio = material.getUnitId().getUName();
                        ratio = ratio.substring(ratio.indexOf("("));
                    }
                    //品名/型号/扩展信息/包装
                    String MaterialName = material.getName() + ((material.getModel() == null || material.getModel().equals(""))?"":"("+material.getModel() + ")");
                    for(int i=0; i< mpArr.length; i++) {
                        if(mpArr[i].equals("颜色")) {
                            MaterialName = MaterialName + ((material.getColor() == null || material.getColor().equals(""))?"":"("+material.getColor() + ")");
                        }
                        if(mpArr[i].equals("规格")) {
                            MaterialName = MaterialName + ((material.getStandard() == null || material.getStandard().equals(""))?"":"("+material.getStandard() + ")");
                        }
                        if(mpArr[i].equals("制造商")) {
                            MaterialName = MaterialName + ((material.getMfrs() == null || material.getMfrs().equals(""))?"":"("+material.getMfrs() + ")");
                        }
                        if(mpArr[i].equals("自定义1")) {
                            MaterialName = MaterialName + ((material.getOtherField1() == null || material.getOtherField1().equals(""))?"":"("+material.getOtherField1() + ")");
                        }
                        if(mpArr[i].equals("自定义2")) {
                            MaterialName = MaterialName + ((material.getOtherField2() == null || material.getOtherField2().equals(""))?"":"("+material.getOtherField2() + ")");
                        }
                        if(mpArr[i].equals("自定义3")) {
                            MaterialName = MaterialName + ((material.getOtherField3() == null || material.getOtherField3().equals(""))?"":"("+material.getOtherField3() + ")");
                        }
                    }
                    MaterialName = MaterialName + ((material.getUnit() == null || material.getUnit().equals(""))?"":"("+material.getUnit() + ")") + ratio;
                    item.put("MaterialName", MaterialName);
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } 
	    catch (DataAccessException e) {
	        Log.errorFileSync(">>>>>>>>>查找供应商信息异常", e);
        } 
	    catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询供应商信息结果异常", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * 查找商品信息-统计排序
	 * @return
	 */
    public void findByOrder()
	{
	    try 
	    {
	        PageUtil<Material> pageUtil = new  PageUtil<Material>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getCondition_Order());
            materialService.find(pageUtil);
            List<Material> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONObject outer = new JSONObject();             
            String mId = "";
	    	if(null != dataList)
            {
                for(Material material:dataList)
                {
                	mId = mId + material.getId() + ",";
                }
            }
	    	if(mId!="") {
	    		mId = mId.substring(0, mId.lastIndexOf(","));
	    	}
	    	outer.put("mIds", mId);
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
     * 导入excel表格-供应商
     * @return
     */
    @SuppressWarnings("unchecked")
    public String importExcel() {
        //excel表格file
        Boolean result = false;
        String returnStr = "";
        try {
            InputStream in = materialService.importExcel(model.getMaterialFile());

            if(null != in)
            {
                model.setFileName(Tools.getRandomChar() + Tools.getNow2(Calendar.getInstance().getTime()) + "_wrong.xls");
                model.setExcelStream(in);
                returnStr = MaterialConstants.BusinessForExcel.EXCEL;
            }
            else {
                result = true;
                try {
                    toClient(result.toString());
                } catch (IOException e) {
                    Log.errorFileSync(">>>>>>>>>回写导入信息结果异常", e);
                }
                //导入数据成功
                returnStr = SUCCESS;
            }

        }
        catch (JshException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>导入excel表格信息异常", e);
        }
        return returnStr;
    }

    /**
     * 导出excel表格
     * @return
     */
    @SuppressWarnings("unchecked")
    public String exportExcel() {
        Log.infoFileSync("===================调用导出信息action方法exportExcel开始=======================");
        try {
            String sName = "pageUtilMaterial";
            PageUtil<Material> pageUtil = (PageUtil<Material>)getSession().get(sName);
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            String isCurrentPage = "allPage";
            model.setFileName(Tools.changeUnicode("goods" + System.currentTimeMillis() + ".xls", model.getBrowserType()));
            model.setExcelStream(materialService.exmportExcel(isCurrentPage,pageUtil));
        }
        catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>调用导出信息action方法exportExcel异常",e);
            model.getShowModel().setMsgTip("export excel exception");
        }
        Log.infoFileSync("===================调用导出信息action方法exportExcel结束==================");
        return EXCEL;
    }
    
	/**
	 * 拼接搜索条件(查全部)
	 * @return
	 */
	private Map<String,Object> getCondition_all()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Name_s_like", model.getName());
        condition.put("Model_s_like", model.getModel());
        condition.put("Color_s_like", model.getColor());
        condition.put("Id_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件
	 * @return
	 */
	private Map<String,Object> getCondition() {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Name_s_like", model.getName());
        condition.put("Model_s_like", model.getModel());
        condition.put("Color_s_like", model.getColor());
        condition.put("CategoryId_s_in", model.getCategoryIds());
        condition.put("Id_s_order", "asc");
        return condition;
    }

    private Map<String,Object> getConditionCheckIsExist() {
        Map<String,Object> condition = new HashMap<String,Object>();
        if(model.getMaterialID()>0){
            condition.put("ID_n_neq", model.getMaterialID());
        }
        condition.put("Name_s_eq", model.getName());
        condition.put("Model_s_eq", model.getModel());
        condition.put("Color_s_eq", model.getColor());
        condition.put("Standard_s_eq", model.getStandard());
        condition.put("Mfrs_s_eq", model.getMfrs());
        condition.put("OtherField1_s_eq", model.getOtherField1());
        condition.put("OtherField2_s_eq", model.getOtherField2());
        condition.put("OtherField3_s_eq", model.getOtherField3());
        if(model.getUnit()!=null){
            condition.put("Unit_s_eq", model.getUnit());
        }
        if(model.getUnitId()!=null){
            condition.put("UnitId_n_eq", model.getUnitId());
        }
        return condition;
    }

    /**
     * 拼接搜索条件
     * @return
     */
    private Map<String,Object> getConditionById()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Id_n_eq", model.getMaterialID());
        return condition;
    }
	
	/**
	 * 拼接搜索条件-下拉框
	 * @return
	 */
	private Map<String,Object> getCondition_Select()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("enabled_s_eq",1);
        condition.put("Id_s_order", "asc");
        return condition;
    }
	
	/**
	 * 拼接搜索条件-下拉框
	 * @return
	 */
	private Map<String,Object> getCondition_Order()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("Name,Model_s_order", "asc");
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public MaterialModel getModel()
	{
		return model;
	}
	public void setMaterialService(MaterialIService materialService)
    {
        this.materialService = materialService;
    }
}
