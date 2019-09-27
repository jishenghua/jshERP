package com.jsh.erp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.Constants.CURRENT_PAGE;
import static com.jsh.erp.utils.Constants.PAGE_SIZE;

/**
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
public class QueryUtils {
    public static String filterSqlSpecialChar(String search) {
        return search != null ? search
                .replaceAll("_", "\\\\_")
                .replaceAll("!", "\\\\!")
                .replaceAll("\\[", "\\\\[")
                .replaceAll("\\]", "\\\\]")
                .replaceAll("\\^", "\\\\^") : null;
    }

    public static <T> T list2One(List<T> list, String label) {
        Assert.notNull(label);
        Assert.notEmpty(list, label + "对应的记录不存在");
        Assert.isTrue(list.size() == 1, label + "对应的记录不止一个");
        return list.get(0);
    }

    public static <T> T list2One(List<T> list, String label, T defaultValue) {
        Assert.notNull(list);
        Assert.notNull(label);
        if (list.isEmpty())
            return defaultValue;
        else {
            Assert.isTrue(list.size() == 1, label + "对应的记录不止一个");
            return list.get(0);
        }
    }

    public static List<String> search(Map<String, String> map) {
        List<String> search = null;

        String str = map.get(Constants.SEARCH);
        if (StringUtil.isNotEmpty(str)) {
            search = StringUtil.searchCondition(str);
        }
        return search;
    }

    public static int rows(Map<String, String> map) {
        return Integer.parseInt(map.get(PAGE_SIZE));
    }

    public static int offset(Map<String, String> map) {
        return (currentPage(map) - 1) * pageSize(map);
    }

    public static int pageSize(Map<String, String> map) {
        return Integer.parseInt(map.get(PAGE_SIZE));
    }

    public static int currentPage(Map<String, String> map) {
        int val = Integer.parseInt(map.get(CURRENT_PAGE));
        if (val < 1)
            throw new RuntimeException("当前页数目:" + val + " 必须大于0");
        return val;
    }

    public static String order(Map<String, String> map) {
        String orderString = OrderUtils.getOrderString(map.get(Constants.ORDER));
        return orderString.trim().isEmpty() ? null : orderString;
    }

    public static Integer level(Map<String, String> map) {
        String levelString = map.get(Constants.LEVEL);
        return StringUtil.isEmpty(levelString) ? null : Integer.parseInt(levelString);
    }

    public static boolean isRecursion(Map<String, String> map) {
        String isRecursion = map.get(Constants.IS_RECURSION);
        return StringUtil.isNotEmpty(isRecursion) && Constants.IS_RECURSION_VALUE.equals(isRecursion);
    }

    public static int type(Map<String, String> map) {
        return Integer.parseInt(map.get(Constants.TYPE));
    }

    public static String filter(Map<String, String> map) {
        if (map.containsKey(Constants.FILTER)) {
            JSONArray array = JSON.parseArray(map.get(Constants.FILTER));
            if (array.isEmpty()) {
                return null;
            } else {
                boolean first = true;
                StringBuilder builder = new StringBuilder();
                for (int idx = 0; idx < array.size(); ++idx) {
                    JSONObject object = array.getJSONObject(idx);
                    if (object.get("value") instanceof JSONArray) {

                        JSONArray value = object.getJSONArray("value");

                        if (!value.isEmpty()) {
                            if (!first) {
                                builder.append(" AND ");
                            } else {
                                first = false;
                            }

                            String key = object.getString("name");

                            builder.append("(");

                            builder.append("`").append(key).append("`");

                            builder.append(" IN ");

                            builder.append("(");

                            for (int vidx = 0; vidx < value.size(); ++vidx) {
                                if (vidx != 0) {
                                    builder.append(",");
                                }
                                builder.append(value.getString(vidx));
                            }
                            builder.append(")");

                            builder.append(")");
                        }
                    }
                }
                return builder.toString();
            }
        } else {
            return null;
        }
    }
}
