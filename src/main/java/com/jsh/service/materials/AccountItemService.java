package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.AccountItemIDAO;
import com.jsh.model.po.AccountItem;

public class AccountItemService extends BaseService<AccountItem> implements AccountItemIService {
    @SuppressWarnings("unused")
    private AccountItemIDAO accoumtItemDao;


    public void setAccountItemDao(AccountItemIDAO accoumtItemDao) {
        this.accoumtItemDao = accoumtItemDao;
    }


    @Override
    protected Class<AccountItem> getEntityClass() {
        return AccountItem.class;
    }


}
