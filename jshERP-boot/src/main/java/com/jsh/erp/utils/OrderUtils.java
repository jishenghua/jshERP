package com.jsh.erp.utils;

/**
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
public class OrderUtils {

    /**
     * 将指定字段排序
     *
     * @param orders 格式 属性名,排序方式 例如( name,asc或ip,desc)
     * @return 排序字符串 例如：（name asc 或 ip desc）
     */
    public static String getOrderString(String orders) {
        if (StringUtil.isNotEmpty(orders)) {
            String[] splits = orders.split(Constants.SPLIT);
            if (splits.length == 2) {
                String column = ColumnPropertyUtil.propertyToColumn(splits[0]);
                if (column.equals("audit_status")) {
                    // TODO: 2015/12/24 这么处理不好，得相伴办法调整
                    return "IF(`audit_status`=3,-1,`audit_status`) " + splits[1];
                } else if (column.equals("create_time") || column.equals("modify_time")) {
                    // TODO: 2015/12/24 这么处理不好，得相伴办法调整
                    return column + " " + splits[1];
                } else {
                    return "convert(" + column + " using gbk) " + splits[1];
                }
            }
        }
        return "";
    }

    public static String getJoinTablesOrderString(String orders, String tableName) {
        if (StringUtil.isNotEmpty(orders)) {
            String[] splits = orders.split(Constants.SPLIT);
            if (splits.length == 2) {
                return "convert(" + tableName + "." + ColumnPropertyUtil.propertyToColumn(splits[0]) + " using gbk) " + splits[1];
            }
        }
        return "";
    }


    /**
     * 将指定字段排序
     * inet_aton：mysql将IP 转成 long类别函数
     *
     * @param orders         格式 属性名,排序方式 例如( name,asc或ip,desc)
     * @param ipPropertyName 如果需要按IP属性排序，需要将属性名传入（可不传）
     * @return 排序字符串 例如：（name asc 或 ip desc）
     */
    public static String getOrderString(String orders, String... ipPropertyName) {
        if (StringUtil.isNotEmpty(orders)) {
            String[] splits = orders.split(Constants.SPLIT);
            if (splits.length == 2) {
                String column = ColumnPropertyUtil.propertyToColumn(splits[0]);
                if (ipPropertyName != null && ipPropertyName.length > 0) {
                    for (String ip : ipPropertyName) {
                        if (ip.equals(column)) {
                            return "inet_aton(" + column + ") " + splits[1];
                        }
                    }
                }
                return column + " " + splits[1];
            }
        }
        return "";
    }
}
