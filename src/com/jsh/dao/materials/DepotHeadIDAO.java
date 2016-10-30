package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.exception.AmsException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.common.PageUtil;

public interface DepotHeadIDAO extends BaseIDAO<DepotHead>
{
	/*
	 * 获取MaxId
	 */
    void find(PageUtil<DepotHead> pageUtil,String maxid) throws AmsException;
}
