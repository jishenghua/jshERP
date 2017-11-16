package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.*;
import com.jsh.model.vo.materials.DepotItemModel;
import com.jsh.service.materials.DepotItemIService;
import com.jsh.service.materials.MaterialIService;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * 单据明细管理
 * @author jishenghua  qq:752718920
*/
@SuppressWarnings("serial")
public class DepotItemAction extends BaseAction<DepotItemModel>
{
    private MaterialIService materialService;
    private DepotItemIService depotItemService;
    private DepotItemModel model = new DepotItemModel();
	/**
	 * action返回excel结果
	 */
	public static final String EXCEL = "excel";
	
	/**
	 * 保存明细
	 * @return
	 */
	public void saveDetials() {
	    Log.infoFileSync("==================开始调用保存仓管通明细信息方法saveDetials()===================");
	    Boolean flag = false;
		try {
			Long headerId=model.getHeaderId();
			String inserted=model.getInserted();
			String deleted=model.getDeleted();
			String updated=model.getUpdated();
			//转为json
			JSONArray insertedJson = JSONArray.fromObject(inserted);
			JSONArray deletedJson = JSONArray.fromObject(deleted);
			JSONArray updatedJson = JSONArray.fromObject(updated);
			if(null != insertedJson) {
				for(int i = 0;i < insertedJson.size(); i++) {
					DepotItem depotItem = new DepotItem();
					JSONObject tempInsertedJson = JSONObject.fromObject(insertedJson.get(i));
					depotItem.setHeaderId(new DepotHead(headerId));
					depotItem.setMaterialId(new Material(tempInsertedJson.getLong("MaterialId")));
                    depotItem.setMUnit(tempInsertedJson.getString("Unit"));
                    if(!StringUtils.isEmpty(tempInsertedJson.get("OperNumber").toString())){
                        depotItem.setOperNumber(tempInsertedJson.getDouble("OperNumber"));
                        try {
                            String Unit = tempInsertedJson.get("Unit").toString();
                            Double oNumber = tempInsertedJson.getDouble("OperNumber");
                            Long mId = Long.parseLong(tempInsertedJson.get("MaterialId").toString());
                            //以下进行单位换算
                            String UnitName = findUnitName(mId); //查询计量单位名称
                            if(!UnitName.equals("")) {
                                String UnitList = UnitName.substring(0, UnitName.indexOf("("));
                                String RatioList = UnitName.substring(UnitName.indexOf("("));
                                String basicUnit = UnitList.substring(0, UnitList.indexOf(",")); //基本单位
                                String otherUnit = UnitList.substring(UnitList.indexOf(",")+1); //副单位
                                Integer ratio = Integer.parseInt(RatioList.substring(RatioList.indexOf(":") + 1).replace(")","")); //比例
                                if(Unit.equals(basicUnit)){ //如果等于基础单位
                                    depotItem.setBasicNumber(oNumber); //数量一致
                                }
                                else if(Unit.equals(otherUnit)){ //如果等于副单位
                                    depotItem.setBasicNumber(oNumber*ratio); //数量乘以比例
                                }
                            }
                            else {
                                depotItem.setBasicNumber(oNumber); //其他情况
                            }
                        }
                        catch(Exception e){
                            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if(!StringUtils.isEmpty(tempInsertedJson.get("UnitPrice").toString())){
                        depotItem.setUnitPrice(tempInsertedJson.getDouble("UnitPrice"));
                    }
                    if(!StringUtils.isEmpty(tempInsertedJson.get("TaxUnitPrice").toString())){
                        depotItem.setTaxUnitPrice(tempInsertedJson.getDouble("TaxUnitPrice"));
                    }
                    if(!StringUtils.isEmpty(tempInsertedJson.get("AllPrice").toString())){
                        depotItem.setAllPrice(tempInsertedJson.getDouble("AllPrice"));
                    }
					depotItem.setRemark(tempInsertedJson.getString("Remark"));
                    if(tempInsertedJson.get("DepotId")!=null && !StringUtils.isEmpty(tempInsertedJson.get("DepotId").toString())){
                        depotItem.setDepotId(new Depot(tempInsertedJson.getLong("DepotId")));
                    }
                    if(tempInsertedJson.get("AnotherDepotId")!=null && !StringUtils.isEmpty(tempInsertedJson.get("AnotherDepotId").toString())){
                        depotItem.setAnotherDepotId(new Depot(tempInsertedJson.getLong("AnotherDepotId")));
                    }
                    if(!StringUtils.isEmpty(tempInsertedJson.get("TaxRate").toString())){
                        depotItem.setTaxRate(tempInsertedJson.getDouble("TaxRate"));
                    }
                    if(!StringUtils.isEmpty(tempInsertedJson.get("TaxMoney").toString())){
                        depotItem.setTaxMoney(tempInsertedJson.getDouble("TaxMoney"));
                    }
                    if(!StringUtils.isEmpty(tempInsertedJson.get("TaxLastMoney").toString())){
                        depotItem.setTaxLastMoney(tempInsertedJson.getDouble("TaxLastMoney"));
                    }
                    if(tempInsertedJson.get("OtherField1")!=null){depotItem.setOtherField1(tempInsertedJson.getString("OtherField1"));}
                    if(tempInsertedJson.get("OtherField2")!=null){depotItem.setOtherField2(tempInsertedJson.getString("OtherField2"));}
                    if(tempInsertedJson.get("OtherField3")!=null){depotItem.setOtherField3(tempInsertedJson.getString("OtherField3"));}
                    if(tempInsertedJson.get("OtherField4")!=null){depotItem.setOtherField4(tempInsertedJson.getString("OtherField4"));}
                    if(tempInsertedJson.get("OtherField5")!=null){depotItem.setOtherField5(tempInsertedJson.getString("OtherField5"));}
                    if(tempInsertedJson.get("MType")!=null){depotItem.setMType(tempInsertedJson.getString("MType"));}
					depotItemService.create(depotItem);
				}
			}
			if(null != deletedJson) {
				for(int i = 0;i < deletedJson.size(); i++) {
					JSONObject tempDeletedJson = JSONObject.fromObject(deletedJson.get(i));
					depotItemService.delete(tempDeletedJson.getLong("Id"));
				}
			}
			if(null != updatedJson) {
				for(int i = 0;i < updatedJson.size(); i++) {
					JSONObject tempUpdatedJson = JSONObject.fromObject(updatedJson.get(i));
					DepotItem depotItem = depotItemService.get(tempUpdatedJson.getLong("Id"));
					depotItem.setMaterialId(new Material(tempUpdatedJson.getLong("MaterialId")));
                    depotItem.setMUnit(tempUpdatedJson.getString("Unit"));
                    if(!StringUtils.isEmpty(tempUpdatedJson.get("OperNumber").toString())){
                        depotItem.setOperNumber(tempUpdatedJson.getDouble("OperNumber"));
                        try {
                            String Unit = tempUpdatedJson.get("Unit").toString();
                            Double oNumber = tempUpdatedJson.getDouble("OperNumber");
                            Long mId = Long.parseLong(tempUpdatedJson.get("MaterialId").toString());
                            //以下进行单位换算
                            String UnitName = findUnitName(mId); //查询计量单位名称
                            if(!UnitName.equals("")) {
                                String UnitList = UnitName.substring(0, UnitName.indexOf("("));
                                String RatioList = UnitName.substring(UnitName.indexOf("("));
                                String basicUnit = UnitList.substring(0, UnitList.indexOf(",")); //基本单位
                                String otherUnit = UnitList.substring(UnitList.indexOf(",")+1); //副单位
                                Integer ratio = Integer.parseInt(RatioList.substring(RatioList.indexOf(":") + 1).replace(")","")); //比例
                                if(Unit.equals(basicUnit)){ //如果等于基础单位
                                    depotItem.setBasicNumber(oNumber); //数量一致
                                }
                                else if(Unit.equals(otherUnit)){ //如果等于副单位
                                    depotItem.setBasicNumber(oNumber*ratio); //数量乘以比例
                                }
                            }
                            else {
                                depotItem.setBasicNumber(oNumber); //其他情况
                            }
                        }
                        catch(Exception e){
                            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>设置基础数量异常", e);
                        }
                    }
                    if(!StringUtils.isEmpty(tempUpdatedJson.get("UnitPrice").toString())){
                        depotItem.setUnitPrice(tempUpdatedJson.getDouble("UnitPrice"));
                    }
                    if(!StringUtils.isEmpty(tempUpdatedJson.get("TaxUnitPrice").toString())){
                        depotItem.setTaxUnitPrice(tempUpdatedJson.getDouble("TaxUnitPrice"));
                    }
                    if(!StringUtils.isEmpty(tempUpdatedJson.get("AllPrice").toString())){
                        depotItem.setAllPrice(tempUpdatedJson.getDouble("AllPrice"));
                    }
					depotItem.setRemark(tempUpdatedJson.getString("Remark"));
                    if(tempUpdatedJson.get("DepotId")!=null && !StringUtils.isEmpty(tempUpdatedJson.get("DepotId").toString())){
                        depotItem.setDepotId(new Depot(tempUpdatedJson.getLong("DepotId")));
                    }
                    if(tempUpdatedJson.get("AnotherDepotId")!=null && !StringUtils.isEmpty(tempUpdatedJson.get("AnotherDepotId").toString())){
                        depotItem.setAnotherDepotId(new Depot(tempUpdatedJson.getLong("AnotherDepotId")));
                    }
                    if(!StringUtils.isEmpty(tempUpdatedJson.get("TaxRate").toString())){
                        depotItem.setTaxRate(tempUpdatedJson.getDouble("TaxRate"));
                    }
                    if(!StringUtils.isEmpty(tempUpdatedJson.get("TaxMoney").toString())){
                        depotItem.setTaxMoney(tempUpdatedJson.getDouble("TaxMoney"));
                    }
                    if(!StringUtils.isEmpty(tempUpdatedJson.get("TaxLastMoney").toString())){
                        depotItem.setTaxLastMoney(tempUpdatedJson.getDouble("TaxLastMoney"));
                    }
                    depotItem.setOtherField1(tempUpdatedJson.getString("OtherField1"));
                    depotItem.setOtherField2(tempUpdatedJson.getString("OtherField2"));
                    depotItem.setOtherField3(tempUpdatedJson.getString("OtherField3"));
                    depotItem.setOtherField4(tempUpdatedJson.getString("OtherField4"));
                    depotItem.setOtherField5(tempUpdatedJson.getString("OtherField5"));
                    depotItem.setMType(tempUpdatedJson.getString("MType"));
					depotItemService.create(depotItem);
				}
			}
			
			//========标识位===========
		    flag = true;
			//记录操作日志使用
			tipMsg = "成功";
            tipType = 0;
		}
		catch (DataAccessException e) {
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>保存仓管通明细信息异常", e);
			flag = false;
			tipMsg = "失败";
            tipType = 1;
		}
		finally {
		    try {
                toClient(flag.toString());
            } 
		    catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>保存仓管通明细信息回写客户端结果异常", e);
            }
		}
		
		logService.create(new Logdetails(getUser(), "保存仓管通明细", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
        , tipType, "保存仓管通明细对应主表编号为  "+ model.getHeaderId() + " " + tipMsg + "！", "保存仓管通明细" + tipMsg));
		Log.infoFileSync("==================结束调用保存仓管通明细方法saveDetials()===================");
	}

    /**
     * 查询计量单位信息
     * @return
     */
    public String findUnitName(Long mId){
        String unitName ="";
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            materialService.findUnitName(pageUtil, mId);
            unitName = pageUtil.getPageList().toString();
            if(unitName!=null){
                unitName = unitName.substring(1,unitName.length()-1);
                if(unitName.equals("null")){
                    unitName = "";
                }
            }
        }
        catch (JshException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return unitName;
    }

	
	/**
	 * 查找明细信息
	 * @return
	 */
    public void findBy() {
	    try {
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            String mpList = model.getMpList(); //商品属性
            String[] mpArr = mpList.split(",");
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList) {
                for(DepotItem depotItem:dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    String ratio; //比例
                    if(depotItem.getMaterialId().getUnitId() == null || depotItem.getMaterialId().getUnitId().equals("")){
                        ratio = "";
                    }
                    else {
                        ratio = depotItem.getMaterialId().getUnitId().getUName();
                        ratio = ratio.substring(ratio.indexOf("("));
                    }
                    //品名/型号/扩展信息/包装
                    String MaterialName = depotItem.getMaterialId().getName() + ((depotItem.getMaterialId().getModel() == null || depotItem.getMaterialId().getModel().equals(""))?"":"("+depotItem.getMaterialId().getModel() + ")");
                    String materialOther = getOtherInfo(mpArr, depotItem);
                    MaterialName = MaterialName + materialOther  + ((depotItem.getMaterialId().getUnit() == null || depotItem.getMaterialId().getUnit().equals(""))?"":"("+depotItem.getMaterialId().getUnit() + ")") + ratio;
                    item.put("MaterialName", MaterialName);
                    item.put("Unit", depotItem.getMUnit());
                    item.put("OperNumber", depotItem.getOperNumber());
                    item.put("BasicNumber", depotItem.getBasicNumber());
                    item.put("UnitPrice", depotItem.getUnitPrice());
                    item.put("TaxUnitPrice", depotItem.getTaxUnitPrice());
                    item.put("AllPrice", depotItem.getAllPrice());
                    item.put("Remark", depotItem.getRemark());
                    item.put("Img", depotItem.getImg());
                    item.put("DepotId",  depotItem.getDepotId()==null?"":depotItem.getDepotId().getId());
                    item.put("DepotName",  depotItem.getDepotId()==null?"":depotItem.getDepotId().getName());
                    item.put("AnotherDepotId",  depotItem.getAnotherDepotId()==null?"":depotItem.getAnotherDepotId().getId());
                    item.put("AnotherDepotName",  depotItem.getAnotherDepotId()==null?"":depotItem.getAnotherDepotId().getName());
                    item.put("TaxRate", depotItem.getTaxRate());
                    item.put("TaxMoney", depotItem.getTaxMoney());
                    item.put("TaxLastMoney", depotItem.getTaxLastMoney());
                    item.put("OtherField1", depotItem.getOtherField1());
                    item.put("OtherField2", depotItem.getOtherField2());
                    item.put("OtherField3", depotItem.getOtherField3());
                    item.put("OtherField4", depotItem.getOtherField4());
                    item.put("OtherField5", depotItem.getOtherField5());
                    item.put("MType", depotItem.getMType());
                    item.put("op", 1);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找仓管通信息异常", e);
        } 
	    catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询仓管通信息结果异常", e);
        }
	}
    
	/**
	 * 查找所有的明细
	 * @return
	 */
    public void findByAll() {
	    try {
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            String mpList = model.getMpList(); //商品属性
            String[] mpArr = mpList.split(",");
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            Integer pid = model.getProjectId();
            JSONArray dataArray = new JSONArray();
            if(null != dataList) {
                for(DepotItem depotItem:dataList) {
                    JSONObject item = new JSONObject();
                    Integer prevSum = sumNumber("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumNumber("出库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true);
                    Integer InSum = sumNumber("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Integer OutSum = sumNumber("出库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Double prevPrice = sumPrice("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumPrice("出库",pid, depotItem.getMaterialId().getId(), model.getMonthTime(), true);
                    Double InPrice = sumPrice("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Double OutPrice = sumPrice("出库",pid, depotItem.getMaterialId().getId(), model.getMonthTime(), false);
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, depotItem);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("MaterialUnit", depotItem.getMaterialId().getUnit());
                    Double unitPrice = 0.0;
                    if(prevSum + InSum - OutSum != 0.0){
                        unitPrice = (prevPrice + InPrice - OutPrice)/(prevSum + InSum - OutSum);
                    }
                    item.put("UnitPrice", unitPrice);
                    item.put("prevSum", prevSum);
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("thisSum", prevSum + InSum - OutSum);
                    item.put("thisAllPrice", prevPrice + InPrice - OutPrice);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}

    /**
     * 根据商品id和仓库id查询库存数量
     * @return
     */
    public void findStockNumById() {
        try {
            PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionById());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            Integer pid = model.getProjectId();
            JSONArray dataArray = new JSONArray();
            if(null != dataList) {
                for(DepotItem depotItem:dataList) {
                    JSONObject item = new JSONObject();
                    Integer prevSum = sumNumber("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumNumber("出库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true);
                    Integer InSum = sumNumber("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Integer OutSum = sumNumber("出库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    item.put("thisSum", prevSum + InSum - OutSum);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        }
        catch (IOException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
    }

    /**
     * 只根据商品id查询库存数量
     * @return
     */
    public void findStockNumByMaterialId() {
        try {
            PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionById());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList) {
                for(DepotItem depotItem:dataList) {
                    JSONObject item = new JSONObject();
                    Integer InSum = sumNumberByMaterialId("入库", depotItem.getMaterialId().getId());
                    Integer OutSum = sumNumberByMaterialId("出库", depotItem.getMaterialId().getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    item.put("thisSum", InSum - OutSum);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        }
        catch (DataAccessException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        }
        catch (IOException e)
        {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
    }

    /**
     * 只根据商品id查询单据列表
     * @return
     */
    public void findDetailByTypeAndMaterialId() {
        try {
            PageUtil pageUtil = new  PageUtil();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            Long mId = model.getMaterialId();
            depotItemService.findDetailByTypeAndMaterialId(pageUtil, mId);
            List dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(dataList!=null){
                for(Integer i=0; i<dataList.size(); i++){
                    JSONObject item = new JSONObject();
                    Object dl = dataList.get(i); //获取对象
                    Object[] arr = (Object[]) dl; //转为数组
                    item.put("Number", arr[0]); //商品编号
                    item.put("Type", arr[1]); //进出类型
                    item.put("BasicNumber", arr[2]); //数量
                    item.put("OperTime", arr[3]); //时间
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        }
        catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
        catch (JshException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找礼品卡信息
     * @return
     */
    public void findGiftByAll() {
        try {
            PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            String mpList = model.getMpList(); //商品属性
            String[] mpArr = mpList.split(",");
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            Integer pid = model.getProjectId();
            JSONArray dataArray = new JSONArray();
            if(null != dataList) {
                for(DepotItem depotItem:dataList){
                    JSONObject item = new JSONObject();
                    Integer InSum = sumNumberGift("礼品充值", pid, depotItem.getMaterialId().getId(), "in");
                    Integer OutSum = sumNumberGift("礼品销售", pid, depotItem.getMaterialId().getId(), "out");
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, depotItem);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("MaterialUnit", depotItem.getMaterialId().getUnit());
                    item.put("thisSum",  InSum - OutSum);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        }
        catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        }
        catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
    }

	/**
	 * 进货统计
	 * @return
	 */
    public void buyIn()
	{
	    try 
	    {	    	
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            String mpList = model.getMpList(); //商品属性
            String[] mpArr = mpList.split(",");
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    Integer InSum = sumNumberBuyOrSale("入库","采购",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Integer OutSum = sumNumberBuyOrSale("出库","采购退货",depotItem.getMaterialId().getId(),model.getMonthTime());  
                    Double InSumPrice = sumPriceBuyOrSale("入库","采购",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Double OutSumPrice = sumPriceBuyOrSale("出库","采购退货",depotItem.getMaterialId().getId(),model.getMonthTime());  
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, depotItem);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("MaterialUnit", depotItem.getMaterialId().getUnit());
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("InSumPrice", InSumPrice);
                    item.put("OutSumPrice", OutSumPrice);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}
	/**
	 * 销售统计
	 * @return
	 */
    public void saleOut()
	{
	    try 
	    {	    	
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            String mpList = model.getMpList(); //商品属性
            String[] mpArr = mpList.split(",");
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    JSONObject item = new JSONObject();
                    Integer OutSumRetail = sumNumberBuyOrSale("出库","零售",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Integer OutSum = sumNumberBuyOrSale("出库","销售",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Integer InSumRetail = sumNumberBuyOrSale("入库","零售退货",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Integer InSum = sumNumberBuyOrSale("入库","销售退货",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Double OutSumRetailPrice = sumPriceBuyOrSale("出库","零售",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Double OutSumPrice = sumPriceBuyOrSale("出库","销售",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Double InSumRetailPrice = sumPriceBuyOrSale("入库","零售退货",depotItem.getMaterialId().getId(),model.getMonthTime());
                    Double InSumPrice = sumPriceBuyOrSale("入库","销售退货",depotItem.getMaterialId().getId(),model.getMonthTime());                    
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    //扩展信息
                    String materialOther = getOtherInfo(mpArr, depotItem);
                    item.put("MaterialOther", materialOther);
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("MaterialUnit", depotItem.getMaterialId().getUnit());
                    item.put("OutSum", OutSumRetail + OutSum);
                    item.put("InSum", InSumRetail + InSum);
                    item.put("OutSumPrice", OutSumRetailPrice + OutSumPrice);
                    item.put("InSumPrice", InSumRetailPrice + InSumPrice);
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}
	/**
	 * 统计总计金额
	 * @return
	 */
    public void totalCountMoney()
	{
	    try 
	    {	    	
	        PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
	        pageUtil.setPageSize(0);
	        pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            Integer pid = model.getProjectId();
            Double thisAllPrice = 0.0;
            if(null != dataList)
            {
                for(DepotItem depotItem:dataList)
                {
                    Double prevPrice = sumPrice("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumPrice("出库",pid, depotItem.getMaterialId().getId(), model.getMonthTime(), true);
                    Double InPrice = sumPrice("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Double OutPrice = sumPrice("出库",pid, depotItem.getMaterialId().getId(), model.getMonthTime(), false);
                    thisAllPrice = thisAllPrice + (prevPrice + InPrice - OutPrice);
                }
            }
            outer.put("totalCount", thisAllPrice);
            //回写查询结果
            toClient(outer.toString());
        } 
	    catch (DataAccessException e) 
	    {
	        Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } 
	    catch (IOException e) 
	    {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
	}
    
    /**
	 * 导出excel表格
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public String exportExcel() {
		Log.infoFileSync("===================调用导出信息action方法exportExcel开始=======================");
		try {
			PageUtil<DepotItem> pageUtil = new  PageUtil<DepotItem>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getConditionALL());
            depotItemService.find(pageUtil);
            List<DepotItem> dataList = pageUtil.getPageList();
            
            //存放数据json数组
            Integer pid = model.getProjectId();
            JSONArray dataArray = new JSONArray();
            if(null != dataList) {
                for(DepotItem depotItem:dataList) {
                    JSONObject item = new JSONObject();
                    Integer prevSum = sumNumber("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumNumber("出库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true);
                    Integer InSum = sumNumber("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Integer OutSum = sumNumber("出库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Double prevPrice = sumPrice("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),true) - sumPrice("出库", pid,depotItem.getMaterialId().getId(), model.getMonthTime(), true);
                    Double InPrice = sumPrice("入库",pid,depotItem.getMaterialId().getId(),model.getMonthTime(),false);
                    Double OutPrice = sumPrice("出库",pid, depotItem.getMaterialId().getId(), model.getMonthTime(), false);
                    item.put("Id", depotItem.getId());
                    item.put("MaterialId", depotItem.getMaterialId()==null?"":depotItem.getMaterialId().getId());
                    item.put("MaterialName", depotItem.getMaterialId().getName());
                    item.put("MaterialModel", depotItem.getMaterialId().getModel());
                    item.put("MaterialStandard", depotItem.getMaterialId().getStandard());
                    item.put("MaterialColor", depotItem.getMaterialId().getColor());
                    item.put("MaterialUnit", depotItem.getMaterialId().getUnit());
                    item.put("UnitPrice", (prevPrice + InPrice - OutPrice)/(prevSum + InSum - OutSum));
                    item.put("prevSum", prevSum);
                    item.put("InSum", InSum);
                    item.put("OutSum", OutSum);
                    item.put("thisSum", prevSum + InSum - OutSum);
                    item.put("thisAllPrice", prevPrice + InPrice - OutPrice);
                    dataArray.add(item);
                }
            }
			String isCurrentPage = "allPage"; 
			model.setFileName(Tools.changeUnicode("report.xls",model.getBrowserType()));
			model.setExcelStream(depotItemService.exmportExcel(isCurrentPage,dataArray));			
		}
		catch (Exception e) {
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>调用导出信息action方法exportExcel异常",e);
			model.getShowModel().setMsgTip("export excel exception");
		}
		Log.infoFileSync("===================调用导出信息action方法exportExcel结束==================");
		return EXCEL;
	}

    /**
     * 数量合计
     * @param type
     * @param MId
     * @param MonthTime
     * @param isPrev
     * @return
     */
    @SuppressWarnings("unchecked")
	public Integer sumNumber(String type,Integer ProjectId,Long MId,String MonthTime, Boolean isPrev) {
    	Integer sumNumber = 0;
    	String allNumber = "";
    	PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
			depotItemService.findByType(pageUtil, type, ProjectId, MId, MonthTime, isPrev);
			allNumber = pageUtil.getPageList().toString();
			allNumber = allNumber.substring(1,allNumber.length()-1);
			if(allNumber.equals("null")){
				allNumber = "0";
			}
			allNumber = allNumber.replace(".0", "");
		} catch (JshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumNumber = Integer.parseInt(allNumber);
		return sumNumber;    	     
    }

    /**
     * 仅根据商品Id进行数量合计
     * @param type
     * @param MId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Integer sumNumberByMaterialId(String type,Long MId) {
        Integer sumNumber = 0;
        String allNumber = "";
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            depotItemService.findByTypeAndMaterialId(pageUtil, type, MId);
            allNumber = pageUtil.getPageList().toString();
            allNumber = allNumber.substring(1,allNumber.length()-1);
            if(allNumber.equals("null")){
                allNumber = "0";
            }
            allNumber = allNumber.replace(".0", "");
        } catch (JshException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sumNumber = Integer.parseInt(allNumber);
        return sumNumber;
    }

    /**
     * 数量合计-礼品卡
     * @param type
     * @param MId
     * @param MonthTime
     * @param isPrev
     * @return
     */
    @SuppressWarnings("unchecked")
    public Integer sumNumberGift(String subType,Integer ProjectId,Long MId,String type) {
        Integer sumNumber = 0;
        String allNumber = "";
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            depotItemService.findGiftByType(pageUtil, subType, ProjectId, MId,type);
            allNumber = pageUtil.getPageList().toString();
            allNumber = allNumber.substring(1,allNumber.length()-1);
            if(allNumber.equals("null")){
                allNumber = "0";
            }
            allNumber = allNumber.replace(".0", "");
        } catch (JshException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sumNumber = Integer.parseInt(allNumber);
        return sumNumber;
    }

    /**
     * 价格合计
     * @param type
     * @param MId
     * @param MonthTime
     * @param isPrev
     * @return
     */
    @SuppressWarnings("unchecked")
    public Double sumPrice(String type,Integer ProjectId,Long MId,String MonthTime, Boolean isPrev) {
        Double sumPrice = 0.0;
        String allPrice = "";
        PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            depotItemService.findPriceByType(pageUtil, type, ProjectId, MId, MonthTime, isPrev);
            allPrice = pageUtil.getPageList().toString();
            allPrice = allPrice.substring(1,allPrice.length()-1);
            if(allPrice.equals("null")){
                allPrice = "0";
            }
            allPrice = allPrice.replace(".0", "");
        } catch (JshException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sumPrice = Double.parseDouble(allPrice);
        return sumPrice;
    }

    @SuppressWarnings("unchecked")
	public Integer sumNumberBuyOrSale(String type,String subType,Long MId,String MonthTime) {
    	Integer sumNumber = 0;
    	String allNumber = "";
    	String sumType = "Number";
    	PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
			depotItemService.buyOrSale(pageUtil, type, subType, MId, MonthTime, sumType);
			allNumber = pageUtil.getPageList().toString();
			allNumber = allNumber.substring(1,allNumber.length()-1);
			if(allNumber.equals("null")){
				allNumber = "0";
			}
			allNumber = allNumber.replace(".0", "");
		} catch (JshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumNumber = Integer.parseInt(allNumber);
		return sumNumber;    	     
    }
    @SuppressWarnings("unchecked")
	public Double sumPriceBuyOrSale(String type,String subType,Long MId,String MonthTime) {
    	Double sumPrice = 0.0;
    	String allPrice = "";
    	String sumType = "Price";
    	PageUtil pageUtil = new  PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
			depotItemService.buyOrSale(pageUtil, type, subType, MId, MonthTime, sumType);
			allPrice = pageUtil.getPageList().toString();
			allPrice = allPrice.substring(1,allPrice.length()-1);
			if(allPrice.equals("null")){
				allPrice = "0";
			}
			allPrice = allPrice.replace(".0", "");
		} catch (JshException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumPrice = Double.parseDouble(allPrice);
		return sumPrice;    	     
    }

    /**
     * 获取扩展信息
     * @return
     */
    public String getOtherInfo(String[] mpArr, DepotItem depotItem){
        String materialOther = "";
        for(int i=0; i< mpArr.length; i++) {
            if(mpArr[i].equals("颜色")) {
                materialOther = materialOther + ((depotItem.getMaterialId().getColor() == null || depotItem.getMaterialId().getColor().equals(""))?"":"("+depotItem.getMaterialId().getColor() + ")");
            }
            if(mpArr[i].equals("规格")) {
                materialOther = materialOther + ((depotItem.getMaterialId().getStandard() == null || depotItem.getMaterialId().getStandard().equals(""))?"":"("+depotItem.getMaterialId().getStandard() + ")");
            }
            if(mpArr[i].equals("制造商")) {
                materialOther = materialOther + ((depotItem.getMaterialId().getMfrs() == null || depotItem.getMaterialId().getMfrs().equals(""))?"":"("+depotItem.getMaterialId().getMfrs() + ")");
            }
            if(mpArr[i].equals("自定义1")) {
                materialOther = materialOther + ((depotItem.getMaterialId().getOtherField1() == null || depotItem.getMaterialId().getOtherField1().equals(""))?"":"("+depotItem.getMaterialId().getOtherField1() + ")");
            }
            if(mpArr[i].equals("自定义2")) {
                materialOther = materialOther + ((depotItem.getMaterialId().getOtherField2() == null || depotItem.getMaterialId().getOtherField2().equals(""))?"":"("+depotItem.getMaterialId().getOtherField2() + ")");
            }
            if(mpArr[i].equals("自定义3")) {
                materialOther = materialOther + ((depotItem.getMaterialId().getOtherField3() == null || depotItem.getMaterialId().getOtherField3().equals(""))?"":"("+depotItem.getMaterialId().getOtherField3() + ")");
            }
        }
        return  materialOther;
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
	
	private Map<String,Object> getConditionALL()
    {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("HeaderId_s_in", model.getHeadIds());
        condition.put("MaterialId_s_in", model.getMaterialIds());
        condition.put("MaterialId_s_gb","aaa");
        return condition;
    }

    private Map<String,Object> getConditionById() {
        /**
         * 拼接搜索条件
         */
        Map<String,Object> condition = new HashMap<String,Object>();
        condition.put("MaterialId_n_eq", model.getMaterialId());
        return condition;
    }
	
	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public DepotItemModel getModel()
	{
		return model;
	}
	public void setDepotItemService(DepotItemIService depotItemService)
    {
        this.depotItemService = depotItemService;
    }

    public void setMaterialService(MaterialIService materialService) {
        this.materialService = materialService;
    }
}
