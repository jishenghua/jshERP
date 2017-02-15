package com.jsh.service.materials;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.util.AssetConstants;
import com.jsh.dao.materials.AccountItemIDAO;
import com.jsh.util.JshException;
import com.jsh.model.po.Asset;
import com.jsh.model.po.AccountHead;
import com.jsh.model.po.AccountItem;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;

public class AccountItemService extends BaseService<AccountItem> implements AccountItemIService
{
    @SuppressWarnings("unused")
    private AccountItemIDAO accoumtItemDao;


    public void setAccountItemDao(AccountItemIDAO accoumtItemDao) {
        this.accoumtItemDao = accoumtItemDao;
    }


    @Override
    protected Class<AccountItem> getEntityClass()
    {
        return AccountItem.class;
    }


}
