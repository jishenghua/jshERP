package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.exception.AmsException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.util.common.PageUtil;

public interface DepotItemIDAO extends BaseIDAO<DepotItem>
{
    void findByType(PageUtil<DepotItem> pageUtil,String type,Long MId, String MonthTime,Boolean isPrev) throws AmsException;
    
    void findOrderByMaterial(PageUtil<DepotItem> pageUtil) throws AmsException;
}
