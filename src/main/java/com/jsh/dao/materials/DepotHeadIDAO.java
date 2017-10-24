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

    public void batchSetStatus(Boolean status,String depotHeadIDs);

    public void findInDetail(PageUtil pageUtil,String beginTime,String endTime,String type,Long pid,String dids) throws JshException;

    public void findInOutMaterialCount(PageUtil pageUtil,String beginTime,String endTime,String type,Long pid,String dids) throws JshException;

    public void findMaterialsListByHeaderId(PageUtil pageUtil,Long headerId) throws JshException;

    public void findStatementAccount(PageUtil pageUtil,String beginTime,String endTime,Long organId) throws JshException;

    public void getHeaderIdByMaterial(PageUtil pageUtil,String materialParam) throws JshException;
    
}
