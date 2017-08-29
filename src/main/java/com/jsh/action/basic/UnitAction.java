package com.jsh.action.basic;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Unit;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.basic.UnitModel;
import com.jsh.service.basic.UnitIService;
import com.jsh.util.PageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计量单位
 * @author ji shenghua  qq:752 718 920
 */
@SuppressWarnings("serial")
public class UnitAction extends BaseAction<UnitModel>
{
	private UnitIService unitService;
	private UnitModel model = new UnitModel();


	/**
	 * 增加计量单位
	 * @return
	 */
	public void create()
	{
		Log.infoFileSync("==================开始调用增加计量单位方法create()===================");
		Boolean flag = false;
		try
		{
			Unit unit = new Unit();
			unit.setUName(model.getUName());
			unitService.create(unit);

			//========标识位===========
			flag = true;
			//记录操作日志使用
			tipMsg = "成功";
			tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加计量单位异常", e);
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
				Log.errorFileSync(">>>>>>>>>>>>增加计量单位回写客户端结果异常", e);
			}
		}

		logService.create(new Logdetails(getUser(), "增加计量单位", model.getClientIp(),
				new Timestamp(System.currentTimeMillis())
				, tipType, "增加计量单位名称为  "+ model.getUName() + " " + tipMsg + "！", "增加计量单位" + tipMsg));
		Log.infoFileSync("==================结束调用增加计量单位方法create()===================");
	}

	/**
	 * 删除计量单位
	 * @return
	 */
	public String delete() {
		Log.infoFileSync("====================开始调用删除计量单位方法delete()================");
		try {
			unitService.delete(model.getUnitID());
			tipMsg = "成功";
			tipType = 0;
		}
		catch (DataAccessException e) {
			Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getUnitID() + "  的计量单位异常", e);
			tipMsg = "失败";
			tipType = 1;
		}
		model.getShowModel().setMsgTip(tipMsg);
		logService.create(new Logdetails(getUser(), "删除计量单位", model.getClientIp(),
				new Timestamp(System.currentTimeMillis())
				, tipType, "删除计量单位ID为  "+ model.getUnitID() + " " + tipMsg + "！", "删除计量单位" + tipMsg));
		Log.infoFileSync("====================结束调用删除计量单位方法delete()================");
		return SUCCESS;
	}

	/**
	 * 更新计量单位
	 * @return
	 */
	public void update() {
		Boolean flag = false;
		try {
			Unit unit = unitService.get(model.getUnitID());
			unit.setUName(model.getUName());
			unitService.update(unit);

			flag = true;
			tipMsg = "成功";
			tipType = 0;
		}
		catch (DataAccessException e) {
			Log.errorFileSync(">>>>>>>>>>>>>修改计量单位ID为 ： " + model.getUnitID() + "信息失败", e);
			flag = false;
			tipMsg = "失败";
			tipType = 1;
		}
		finally {
			try {
				toClient(flag.toString());
			}
			catch (IOException e) {
				Log.errorFileSync(">>>>>>>>>>>>修改计量单位回写客户端结果异常", e);
			}
		}
		logService.create(new Logdetails(getUser(), "更新计量单位", model.getClientIp(),
				new Timestamp(System.currentTimeMillis())
				, tipType, "更新计量单位ID为  "+ model.getUnitID() + " " + tipMsg + "！", "更新计量单位" + tipMsg));
	}

	/**
	 * 批量删除指定ID计量单位
	 * @return
	 */
	public String batchDelete()
	{
		try
		{
			unitService.batchDelete(model.getUnitIDs());
			model.getShowModel().setMsgTip("成功");
			//记录操作日志使用
			tipMsg = "成功";
			tipType = 0;
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>批量删除计量单位ID为：" + model.getUnitIDs() + "信息异常", e);
			tipMsg = "失败";
			tipType = 1;
		}

		logService.create(new Logdetails(getUser(), "批量删除计量单位", model.getClientIp(),
				new Timestamp(System.currentTimeMillis())
				, tipType, "批量删除计量单位ID为  "+ model.getUnitIDs() + " " + tipMsg + "！", "批量删除计量单位" + tipMsg));
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
			flag = unitService.checkIsNameExist("UName",model.getUName(),"id", model.getUnitID());
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>检查计量单位名称为：" + model.getUName() + " ID为： " + model.getUnitID() + " 是否存在异常！");
		}
		finally
		{
			try
			{
				toClient(flag.toString());
			}
			catch (IOException e)
			{
				Log.errorFileSync(">>>>>>>>>>>>回写检查计量单位名称为：" + model.getUName() + " ID为： " + model.getUnitID() + " 是否存在异常！",e);
			}
		}
	}

	/**
	 * 查找计量单位信息
	 * @return
	 */
	public void findBy()
	{
		try
		{
			PageUtil<Unit> pageUtil = new  PageUtil<Unit>();
			pageUtil.setPageSize(model.getPageSize());
			pageUtil.setCurPage(model.getPageNo());
			pageUtil.setAdvSearch(getCondition());
			unitService.find(pageUtil);
			List<Unit> dataList = pageUtil.getPageList();

			JSONObject outer = new JSONObject();
			outer.put("total", pageUtil.getTotalCount());
			//存放数据json数组
			JSONArray dataArray = new JSONArray();
			if(null != dataList)
			{
				for(Unit unit:dataList)
				{
					JSONObject item = new JSONObject();
					item.put("id", unit.getId());
					//名称
					item.put("UName", unit.getUName());
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
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找计量单位异常", e);
		}
		catch (IOException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询计量单位结果异常", e);
		}
	}

	/**
	 * 查找计量单位信息-下拉框
	 * @return
	 */
	public void findUnitDownList() {
		try {
			PageUtil<Unit> pageUtil = new  PageUtil<Unit>();
			pageUtil.setPageSize(0);
			pageUtil.setCurPage(0);
			pageUtil.setAdvSearch(getCondition());
			unitService.find(pageUtil);
			List<Unit> dataList = pageUtil.getPageList();

			//存放数据json数组
			JSONArray dataArray = new JSONArray();
			if(null != dataList) {
				for(Unit unit:dataList) {
					JSONObject item = new JSONObject();
					item.put("id", unit.getId());
					//名称
					item.put("UName", unit.getUName());
					dataArray.add(item);
				}
			}
			//回写查询结果
			toClient(dataArray.toString());
		}
		catch (DataAccessException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找计量单位异常", e);
		}
		catch (IOException e)
		{
			Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询计量单位结果异常", e);
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
		condition.put("UName_s_like", model.getUName());
		condition.put("id_s_order", "asc");
		return condition;
	}

	//=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
	@Override
	public UnitModel getModel()
	{
		return model;
	}

	public void setUnitService(UnitIService unitService) {
		this.unitService = unitService;
	}
}
