package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.util.PageUtil;

public interface DepotItemIDAO extends BaseIDAO<DepotItem>
{
    public void findByType(PageUtil<DepotItem> pageUtil,String type,Integer ProjectId,Long MId, String MonthTime,Boolean isPrev) throws JshException;

    public void findByTypeAndMaterialId(PageUtil<DepotItem> pageUtil,String type,Long MId) throws JshException;

    public void findDetailByTypeAndMaterialId(PageUtil<DepotItem> pageUtil,Long MId) throws JshException;

    public void findPriceByType(PageUtil<DepotItem> pageUtil,String type,Integer ProjectId,Long MId, String MonthTime,Boolean isPrev) throws JshException;

    public void buyOrSale(PageUtil<DepotItem> pageUtil,String type, String subType,Long MId, String MonthTime, String sumType) throws JshException;

    public void findGiftByType(PageUtil<DepotItem> pageUtil,String subType,Integer ProjectId,Long MId, String type) throws JshException;
}
