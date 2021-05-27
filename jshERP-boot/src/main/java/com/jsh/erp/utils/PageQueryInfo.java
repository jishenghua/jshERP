package com.jsh.erp.utils;

import java.util.List;

/**
 * 分页查询结果
 *
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
public class PageQueryInfo {

    private Long total;
    private List<?> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
