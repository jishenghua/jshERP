package com.jsh.service.materials;

import java.io.InputStream;
import java.util.List;

import net.sf.json.JSONArray;

import com.jsh.base.BaseIService;
import com.jsh.exception.JshException;
import com.jsh.model.po.Asset;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.util.common.PageUtil;

public interface DepotItemIService extends BaseIService<DepotItem>
{
	void findByType(PageUtil<DepotItem> depotItem, String type, Long MId, String MonthTime,Boolean isPrev)throws JshException;
	
	void findOrderByMaterial(PageUtil<DepotItem> depotItem)throws JshException;
	
	/**
	 * 导出信息
	 * @return
	 */
	InputStream exmportExcel(String isAllPage,JSONArray dataArray)throws JshException;
}
