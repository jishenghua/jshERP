package com.jsh.erp.utils;

/**
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
public class ColumnPropertyUtil {

    /**
     * 将数据库字段转换成属性
     */
    public static String columnToProperty(String column) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (StringUtil.isEmpty(column)) {
            // 没必要转换
            return "";
        } else if (!column.contains("_")) {
            // 不做转换
            return column;
        } else {
            // 用下划线将原始字符串分割
            String[] columns = column.split("_");
            for (String columnSplit : columns) {
                // 跳过原始字符串中开头、结尾的下换线或双重下划线
                if (StringUtil.isEmpty(columnSplit)) {
                    continue;
                }
                // 处理真正的驼峰片段
                if (result.length() == 0) {
                    // 第一个驼峰片段，全部字母都小写
                    result.append(columnSplit.toLowerCase());
                } else {
                    // 其他的驼峰片段，首字母大写
                    result.append(columnSplit.substring(0, 1).toUpperCase()).append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }

    }


    /**
     * 驼峰转换下划线
     */
    public static String propertyToColumn(String property) {
        if (StringUtil.isEmpty(property)) {
            return "";
        }
        StringBuilder column = new StringBuilder();
        column.append(property.substring(0, 1).toLowerCase());
        for (int i = 1; i < property.length(); i++) {
            String s = property.substring(i, i + 1);
            // 在小写字母前添加下划线
            if (!Character.isDigit(s.charAt(0)) && s.equals(s.toUpperCase())) {
                column.append("_");
            }
            // 其他字符直接转成小写
            column.append(s.toLowerCase());
        }

        return column.toString();
    }

}
