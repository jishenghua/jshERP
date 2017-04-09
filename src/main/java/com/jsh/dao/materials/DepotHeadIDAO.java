package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.PageUtil;

public interface DepotHeadIDAO extends BaseIDAO<DepotHead>
{
	/*
	 * 获取MaxId
	 */
    void find(PageUtil<DepotHead> pageUtil,String maxid) throws JshException;
    
    void findAllMoney(PageUtil<DepotHead> pageUtil, Integer supplierId, String type, String subType, String mode) throws JshException;
    
}
