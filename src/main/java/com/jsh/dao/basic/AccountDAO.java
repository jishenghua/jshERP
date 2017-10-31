package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Account;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;
import org.hibernate.Query;

public class AccountDAO extends BaseDAO<Account> implements AccountIDAO
{
    /**
     * 设置dao映射基类
     * @return
     */
    @Override
    public Class<Account> getEntityClass()
    {
        return Account.class;
    }

    @SuppressWarnings("unchecked")
    public void findAccountInOutList(PageUtil<Account> pageUtil, Long accountId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dh.Number,concat(dh.SubType,dh.Type) as newType,s.supplier,dh.ChangeAmount,date_format(dh.OperTime,'%Y-%m-%d %H:%i:%S') as oTime " +
                " from jsh_depothead dh inner join jsh_supplier s on dh.OrganId = s.id where 1=1 ");
        if(accountId!=null && !accountId.equals("")) {
            queryString.append(" and dh.AccountId='"+ accountId +"' ");
        }
        queryString.append("UNION ALL " +
                "select ah.BillNo,ah.Type as newType,s.supplier,ah.ChangeAmount,date_format(ah.BillTime,'%Y-%m-%d %H:%i:%S') as oTime " +
                "  from jsh_accounthead ah inner join jsh_supplier s on ah.OrganId=s.id where 1=1 ");
        if(accountId!=null && !accountId.equals("")) {
            queryString.append(" and ah.AccountId='"+ accountId +"' ");
        }
        queryString.append("UNION ALL " +
                "select ah.BillNo,ah.Type as newType,s.supplier,ai.EachAmount,date_format(ah.BillTime,'%Y-%m-%d %H:%i:%S') as oTime " +
                " from jsh_accounthead ah inner join jsh_supplier s on ah.OrganId=s.id " +
                " inner join jsh_accountitem ai on ai.HeaderId=ah.Id " +
                " where ah.Type in ('收款','付款','收预付款') ");
        if(accountId!=null && !accountId.equals("")) {
            queryString.append(" and ai.AccountId='"+ accountId +"' ");
        }
        queryString.append("UNION ALL " +
                "select ah.BillNo,ah.Type as newType, '' as sName,ah.ChangeAmount,date_format(ah.BillTime,'%Y-%m-%d %H:%i:%S') as oTime " +
                " from jsh_accounthead ah inner join jsh_accountitem ai on ai.HeaderId=ah.Id " +
                " where ah.Type='转账' ");
        if(accountId!=null && !accountId.equals("")) {
            queryString.append(" and ah.AccountId='"+ accountId +"' ");
        }
        queryString.append("UNION ALL " +
                "select ah.BillNo,ah.Type as newType, '' as sName,ai.EachAmount,date_format(ah.BillTime,'%Y-%m-%d %H:%i:%S') as oTime " +
                " from jsh_accounthead ah inner join jsh_accountitem ai on ai.HeaderId=ah.Id " +
                " where ah.Type='转账' ");
        if(accountId!=null && !accountId.equals("")) {
            queryString.append(" and ai.AccountId='"+ accountId +"' ");
        }
        queryString.append(" ORDER BY oTime desc");
        Query query;
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        // 分页查询
        int pageNo = pageUtil.getCurPage();
        int pageSize = pageUtil.getPageSize();
        if (0 != pageNo && 0 != pageSize) {
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        pageUtil.setPageList(query.list());
    }
}
