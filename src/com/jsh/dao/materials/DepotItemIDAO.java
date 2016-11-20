package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.exception.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.util.common.PageUtil;

public interface DepotItemIDAO extends BaseIDAO<DepotItem>
{
    void findByType(PageUtil<DepotItem> pageUtil,String type,Long MId, String MonthTime,Boolean isPrev) throws JshException;
    
    void findOrderByMaterial(PageUtil<DepotItem> pageUtil) throws JshException;
}
