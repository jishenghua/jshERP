package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.model.po.DepotHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface DepotHeadIDAO extends BaseIDAO<DepotHead> {
    /*
     * 获取MaxId
     */
    void find(PageUtil<DepotHead> pageUtil, String maxid) throws JshException;

    void findAllMoney(PageUtil<DepotHead> pageUtil, Integer supplierId, String type, String subType, String mode) throws JshException;

    void batchSetStatus(Boolean status, String depotHeadIDs);

    void findInDetail(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException;

    void findInOutMaterialCount(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException;

    void findMaterialsListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException;

    void findStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId, String supType) throws JshException;

    void getHeaderIdByMaterial(PageUtil pageUtil, String materialParam, String depotIds) throws JshException;

}
