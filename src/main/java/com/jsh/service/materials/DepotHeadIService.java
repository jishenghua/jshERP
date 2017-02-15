package com.jsh.service.materials;

import com.jsh.base.BaseIService;
import com.jsh.util.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.PageUtil;

public interface DepotHeadIService extends BaseIService<DepotHead>
{
	/*
	 * 获取MaxId
	 */
	void find(PageUtil<DepotHead> depotHead,String maxid)throws JshException;
}
