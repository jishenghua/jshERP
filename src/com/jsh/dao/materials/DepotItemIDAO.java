package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.DepotItem;
import com.jsh.util.PageUtil;

public interface DepotItemIDAO extends BaseIDAO<DepotItem>
{
    void findByType(PageUtil<DepotItem> pageUtil,String type,Long MId, String MonthTime,Boolean isPrev) throws JshException; 
}
