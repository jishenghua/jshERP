package com.jsh.erp.utils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author jishenghua qq752718920  2018-10-7 15:26:27
 */
public class StringUtil {

    private StringUtil() {

    }

    private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String filterNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str.trim();
        }
    }

    public static boolean stringEquels(String source,String target) {
        if(isEmpty(source)||isEmpty(target)){
            return false;
        }else{
            return source.equals(target);
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getSysDate(String format) {
        if (StringUtil.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static Date getDateByString(String date, String format) {
        if (StringUtil.isEmpty(format)) {
            format = DEFAULT_FORMAT;
        }
        if (StringUtil.isNotEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException("转换为日期类型错误：DATE：" + date + "  FORMAT:" + format);
            }
        } else {
            return null;
        }
    }

    public static Date getDateByLongDate(Long millis) {
        if (millis == null) {
            return new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.getTime();

    }

    public static UUID stringToUUID(String id) {
        if (StringUtil.isNotEmpty(id)) {
            return UUID.fromString(id);
        } else {
            return null;
        }
    }

    public static Integer parseInteger(String str) {
        if (StringUtil.isNotEmpty(str)) {
            return Integer.parseInt(str);
        } else {
            return null;
        }
    }

    public static List<UUID> listToUUID(List<String> listStrs) {
        if (listStrs != null && listStrs.size() > 0) {
            List<UUID> uuidList = new ArrayList<UUID>();
            for (String str : listStrs) {
                uuidList.add(UUID.fromString(str));
            }
            return uuidList;
        } else {
            return null;
        }
    }

    public static List<UUID> arrayToUUIDList(String[] uuids) {
        if (uuids != null && uuids.length > 0) {
            List<UUID> uuidList = new ArrayList<UUID>();
            for (String str : uuids) {
                uuidList.add(UUID.fromString(str));
            }
            return uuidList;
        } else {
            return null;
        }
    }

    //是否是JSON
    public static boolean containsAny(String str, String... flag) {
        if (str != null) {
            if (flag == null || flag.length == 0) {
                flag = "[-{-}-]-,".split("-");
            }
            for (String s : flag) {
                if (str.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getModifyOrgOperateData(UUID resourceId, UUID orgId) {
        if (resourceId != null && orgId != null) {
            Map<UUID, UUID> map = new HashMap<UUID, UUID>();
            map.put(resourceId, orgId);
            return JSON.toJSONString(map);
        }
        return "";
    }

    public static String[] listToStringArray(List<String> list) {
        if (list != null && !list.isEmpty()) {
            return list.toArray(new String[list.size()]);
        }
        return new String[0];
    }

    public static List<String> stringToListArray(String[] strings) {
        if (strings != null && strings.length > 0) {
            return Arrays.asList(strings);
        }
        return new ArrayList<String>();
    }

    /**
     * String字符串转成List<Long>数据格式
     * String str = "1,2,3,4,5,6" -> List<Long> listLong [1,2,3,4,5,6];
     *
     * @param strArr
     * @return
     */
    public static List<Long> strToLongList(String strArr) {
        List<Long> idList=new ArrayList<Long>();
        String[] d=strArr.split(",");
        for (int i = 0, size = d.length; i < size; i++) {
            if(d[i]!=null) {
                idList.add(Long.parseLong(d[i]));
            }
        }
        return idList;
    }

    /**
     * String字符串转成List<String>数据格式
     * String str = "1,2,3,4,5,6" -> List<Long> listLong [1,2,3,4,5,6];
     *
     * @param strArr
     * @return
     */
    public static List<String> strToStringList(String strArr) {
        if(StringUtils.isEmpty(strArr)){
            return null;
        }
        List<String> idList=new ArrayList<String>();
        String[] d=strArr.split(",");
        for (int i = 0, size = d.length; i < size; i++) {
            if(d[i]!=null) {
                idList.add(d[i].toString());
            }
        }
        return idList;
    }

    public static List<String> searchCondition(String search) {
        if (isEmpty(search)) {
            return new ArrayList<String>();
        }else{
            //String[] split = search.split(" ");
			String[] split = search.split("#");
            return stringToListArray(split);
        }
    }

    public static String getInfo(String search, String key){
        String value = "";
        if(search!=null) {
            JSONObject obj = JSONObject.parseObject(search);
            value = obj.getString(key);
            if(value.equals("")) {
                value = null;
            }
        }
        return value;
    }

    public static String toNull(String value) {
        if(("").equals(value)) {
            value = null;
        }
        return value;
    }

    public static void main(String[] args) {
        int i = 10/3;
        System.out.println(i);
    }
}
