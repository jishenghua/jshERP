package com.jsh.service.materials;

import com.jsh.base.BaseIService;
import com.jsh.util.JshException;
import com.jsh.model.po.AccountHead;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.UserBusiness;
import com.jsh.util.PageUtil;

public interface AccountHeadIService extends BaseIService<AccountHead>
{
    /*
     * 获取MaxId
     */
    void find(PageUtil<AccountHead> accountHead,String maxid)throws JshException;
    
    void findAllMoney(PageUtil<AccountHead> accountHead, Integer supplierId, String type, String mode)throws JshException;
}
