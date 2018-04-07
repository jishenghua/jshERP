package com.jsh.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据搜索条件拼装成查询hql语句
 *
 * @author jishenghua  qq:752718920
 */
public class SearchConditionUtil {
    //拼接字符串的前缀空格字符串
    private static final String emptyPrefix = " and ";

    /**
     * 根据搜索条件自动拼接成hql搜索语句
     *
     * @param condition 搜索条件 规则：
     *                  1、类型 n--数字 s--字符串
     *                  2、属性 eq--等于 neq--不等于 like--像'%XX%' llike--左像'%XX' rlike--右像'XX%' in--包含  gt--大于 gteq--大于等于 lt--小于 lteq--小于等于
     *                  order--value desc asc  gy-- group by
     *                  示例:
     *                  Map<String,Object> condition = new HashMap<String,Object>();
     *                  condition.put("supplier_s_like", "aaa");
     *                  condition.put("contacts_s_llike", "186");
     *                  condition.put("contacts_s_rlike", "186");
     *                  condition.put("phonenum_s_eq", null);
     *                  condition.put("email_n_neq", 23);
     *                  condition.put("description_s_order", "desc");
     * @return 封装后的字符串
     */
    public static String getCondition(Map<String, Object> condition) {
        StringBuffer hql = new StringBuffer();
        Set<String> key = condition.keySet();
        String groupbyInfo = "";
        String orderInfo = "";
        for (String keyInfo : key) {
            /*
             * 1、数组为三个 第一个为对象实例的字段 第二个为字段类型 第三个为属性
             * 2、根据分解后的数组拼接搜索条件
             */
            Object valueInfo = condition.get(keyInfo);
            if (null != valueInfo && valueInfo.toString().length() > 0) {
                String[] searchCondition = keyInfo.split("_");
                if (searchCondition[1].equals("n"))
                    hql.append(emptyPrefix + searchCondition[0] + getType(searchCondition[2]) + valueInfo);
                else if (searchCondition[1].equals("s")) {
                    if (searchCondition[2].equals("like"))
                        hql.append(emptyPrefix + searchCondition[0] + getType(searchCondition[2]) + "'%" + valueInfo + "%'");
                    else if (searchCondition[2].equals("llike"))
                        hql.append(emptyPrefix + searchCondition[0] + getType(searchCondition[2]) + "'%" + valueInfo + "'");
                    else if (searchCondition[2].equals("rlike"))
                        hql.append(emptyPrefix + searchCondition[0] + getType(searchCondition[2]) + "'" + valueInfo + "%'");
                    else if (searchCondition[2].equals("in"))
                        hql.append(emptyPrefix + searchCondition[0] + getType(searchCondition[2]) + "(" + valueInfo + ")");
                    else if (searchCondition[2].equals("order"))
                        orderInfo = " order by " + searchCondition[0] + " " + valueInfo;
                    else if (searchCondition[2].equals("gb"))
                        groupbyInfo = " group by " + searchCondition[0];
                    else
                        hql.append(emptyPrefix + searchCondition[0] + getType(searchCondition[2]) + "'" + valueInfo + "'");
                }
            }
        }
        return hql.append(groupbyInfo).append(orderInfo).toString();
    }

    /**
     * 获取指定类型的符号
     * 属性 eq--等于 neq--不等于 like--像  in--包含  gt--大于 gteq--大于等于 lt--小于 lteq--小于等于  order--value desc asc
     *
     * @param type
     * @return 类型字符串
     */
    private static String getType(String type) {
        String typeStr = "";
        if (type.equals("eq"))
            typeStr = " = ";
        else if (type.equals("neq"))
            typeStr = " != ";
        else if (type.equals("like"))
            typeStr = " like ";
        else if (type.equals("llike"))
            typeStr = " like ";
        else if (type.equals("rlike"))
            typeStr = " like ";
        else if (type.equals("in"))
            typeStr = " in ";
        else if (type.equals("gt"))
            typeStr = " > ";
        else if (type.equals("gteq"))
            typeStr = " >= ";
        else if (type.equals("lt"))
            typeStr = " < ";
        else if (type.equals("lteq"))
            typeStr = " <= ";
        else if (type.equals("order"))
            typeStr = " order ";
        else if (type.equals("gy"))
            typeStr = " group by ";
        else
            typeStr = "unknown";
        return typeStr;
    }

    public static void main(String[] args) {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("supplier_s_like", "aaa");
        condition.put("contacts_s_llike", "186");
        condition.put("contacts_s_rlike", "186");
        condition.put("phonenum_s_eq", null);
        condition.put("email_n_neq", 23);
        condition.put("description_s_order", "desc");
        condition.put("description_s_gb", "aaa");

        //获取搜索条件拼接
        System.out.println(getCondition(condition));
    }
}
