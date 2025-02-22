package com.jsh.erp.utils;

import com.github.pagehelper.PageHelper;
import com.jsh.erp.base.PageDomain;
import com.jsh.erp.base.TableSupport;

/**
 * 分页工具类
 * 
 * @author ji-sheng-hua
 */
public class PageUtils extends PageHelper
{
    /**
     * 设置请求分页数据
     */
    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer currentPage = pageDomain.getCurrentPage();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(currentPage, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
