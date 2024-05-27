package com.jsh.erp.utils;

import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author jishenghua  qq:7-5-2-7-1-8-9-2-0
 */
public class Tools {
    /**
     * 获得32位唯一序列号
     *
     * @return 32为ID字符串
     */
    public static String getUUID_32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得当天时间，格式为yyyy-MM-dd
     *
     * @return 格式化后的日期格式
     */
    public static String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 获取昨天的日期字符串
     * @return
     */
    public static String getYesterday(){
        Date date=new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动(1:表示明天、-1：表示昨天，0：表示今天)
        calendar.add(Calendar.DATE,-1);
        //这个时间就是日期往前推一天的结果
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static String getYearBegin(){
        String yearStr = new SimpleDateFormat("yyyy").format(new Date());
        return yearStr + "-01-01";
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static String getYearEnd(){
        String yearStr = new SimpleDateFormat("yyyy").format(new Date());
        return yearStr + "-12-31";
    }

    /**
     * 获取当前月 yyyy-MM
     *
     * @return
     */
    public static String getCurrentMonth() {
        return new SimpleDateFormat("yyyy-MM").format(new Date());
    }

    /**
     * 获得指定时间，格式为yyyy-MM-dd HH:mm:ss或yyyy-MM-dd
     *
     * @return 格式化后的日期格式
     */
    public static String dateToStr(Date date, String format) {
        if(date!=null) {
            return new SimpleDateFormat(format).format(date);
        } else {
            return "";
        }
    }

    /**
     * 将日期的字符串格式转为时间格式
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 日期格式
        return formatter.parse(dateString); // 字符串转换为Date
    }
    /**
     * 获取指定日期格式 yyyy-MM-dd
     *
     * @return
     */
    public static String parseDateToStr(Date date) {
        if(date!=null) {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } else {
            return "";
        }
    }

    /**
     * 获得当天时间，格式为yyyyMMddHHmmss
     *
     * @return 格式化后的日期格式
     */
    public static String getNow2(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    /**
     * 获得当天时间，格式为yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的日期格式
     */
    public static String getNow3() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获得指定时间，格式为yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的日期格式
     */
    public static String getCenternTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String parseDayToTime(String day, String timeStr) {
        if(StringUtil.isNotEmpty(day)){
            return day + timeStr;
        } else {
            return null;
        }
    }

    /**
     * 获得指定时间，格式为mm:ss
     *
     * @return 格式化后的日期格式
     */
    public static String getTimeInfo(Date date) {
        return new SimpleDateFormat("mm:ss").format(date);
    }

    /**
     * 获取当前日期是星期几
     * return 星期几
     */
    public static String getWeekDay() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(new Date());
        int day = c.get(Calendar.DAY_OF_WEEK);
        String weekDay = "";
        switch (day) {
            case 1:
                weekDay = "星期日";
                break;
            case 2:
                weekDay = "星期一";
                break;
            case 3:
                weekDay = "星期二";
                break;
            case 4:
                weekDay = "星期三";
                break;
            case 5:
                weekDay = "星期四";
                break;
            case 6:
                weekDay = "星期五";
                break;
            case 7:
                weekDay = "星期六";
                break;
            default:
                break;
        }
        return weekDay;
    }

    /**
     * 判断字符串是否全部为数字
     *
     * @param checkStr
     * @return boolean值
     */
    public static boolean checkStrIsNum(String checkStr) {
        if (checkStr == null || checkStr.length() == 0)
            return false;
        return Pattern.compile("^[0-9]*.{1}[0-9]*$").matcher(checkStr).matches();
//		 return Pattern.compile("：^[0-9]+(.[0-9])*$").matcher(checkStr).matches();
    }

    /**
     * 获得前一天的时间
     *
     * @return 前一天日期
     */
    public static String getPreviousDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM").format(cal.getTime());
    }

    /**
     * 获取当前月份的前6个月(含当前月)
     * @param size  月数
     * @return
     */
    public static List<String> getLastMonths(int size) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        List<String> list = new ArrayList(size);
        for (int i=0;i<size;i++) {
            c.setTime(new Date());
            c.add(Calendar.MONTH, -i);
            Date m = c.getTime();
            list.add(sdf.format(m));
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 截取字符串长度
     *
     * @param beforeStr
     * @param cutLeng
     * @return 截取后的字符串
     */
    public static String subStr(String beforeStr, int cutLeng) {
        if (beforeStr.length() > cutLeng)
            return beforeStr.substring(0, cutLeng) + "...";
        return beforeStr;
    }

    /**
     * 生成随机字符串，字母和数字混合
     *
     * @return 组合后的字符串 ^[0-9a-zA-Z]
     */
    public static String getRandomChar() {
        //生成一个0、1、2的随机数字
        int rand = (int) Math.round(Math.random() * 1);
        long itmp = 0;
        char ctmp = '\u0000';
        switch (rand) {
            //生成大写字母 + 1000以内数字
            case 1:
                itmp = Math.round(Math.random() * 25 + 65);
                ctmp = (char) itmp;
                return String.valueOf(ctmp) + (int) Math.random() * 1000;
            //生成小写字母
            case 2:
                itmp = Math.round(Math.random() * 25 + 97);
                ctmp = (char) itmp;
                return String.valueOf(ctmp) + (int) Math.random() * 1000;
            //生成数字
            default:
                itmp = Math.round(Math.random() * 1000);
                return itmp + "";
        }
    }

    /**
     * 判断首字母以数字开头,字符串包括数字、字母%以及空格
     *
     * @param str 检查字符串
     * @return 是否以数字开头
     */
    public static boolean CheckIsStartWithNum(String str) {
        return Pattern.compile("^[0-9][a-zA-Z0-9%,\\s]*$").matcher(str).matches();
    }

    /**
     * 判断首字母以","开头,字符串包括数字、字母%以及空格
     *
     * @param str 检查字符串
     * @return 是否以数字开头
     */
    public static boolean CheckIsStartWithSpec(String str) {
        return Pattern.compile("^[,][a-zA-Z0-9%,\\s]*$").matcher(str).matches();
    }

    /**
     * 字符转码
     *
     * @param aValue
     * @return
     * @see 转码后的字符串
     */
    public static String encodeValue(String aValue) {
        if (aValue.trim().length() == 0) {
            return "";
        }
        String valueAfterTransCode = null;
        try {
            valueAfterTransCode = URLEncoder.encode(aValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        return valueAfterTransCode;
    }

    /**
     * 字符转码
     *
     * @param aValue
     * @return
     * @see 转码后的字符串
     */
    public static String decodeValue(String aValue) {
        if (aValue.trim().length() == 0) {
            return "";
        }
        String valueAfterTransCode = null;
        try {
            valueAfterTransCode = URLDecoder.decode(aValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        return valueAfterTransCode;
    }

    /**
     * 去除str中的'
     *
     * @param str
     * @return 除去'后的字符串
     * @see [类、类#方法、类#成员]
     */
    public static String afterDealStr(String str) {
        return str.replace("'", "");
    }

    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     *
     * @param request
     * @return ip
     */
    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = getIpAddr(request);
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }
    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 转化前台批量传入的ID值
     *
     * @param data
     * @return 转化后的ID值数组
     */
    public static int[] changeDataForm(String data) {
        String[] dataStr = data.split(",");
        int[] dataInt = new int[dataStr.length];
        for (int i = 0; i < dataStr.length; i++)
            dataInt[i] = Integer.parseInt(dataStr[i]);
        return dataInt;
    }

    /**
     * 写理财日志内容转化特殊字符
     *
     * @param str 需要转化的字符
     * @return 转化后的字符
     */
    public static String htmlspecialchars(String str) {
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\"", "&quot;");
        return str;
    }

    /**
     * 根据消费日期获取消费月
     *
     * @param consumeDate 消费日期
     * @return 返回消费月信息
     */
    public static String getConsumeMonth(String consumeDate) {
        return consumeDate.substring(0, 7);
    }

    /**
     * 获取当前日期的前XX个月
     *
     * @param beforeMonth
     * @return 前XX个月字符串
     */
    public static String getBeforeMonth(int beforeMonth) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -beforeMonth);
        return new SimpleDateFormat("yyyy-MM").format(c.getTime());
    }

    /**
     * 根据月份获取当月第一天
     * @param monthTime
     * @return
     * @throws ParseException
     */
    public static String firstDayOfMonth(String monthTime) throws ParseException {
        return monthTime + "-01";
    }

    /**
     * 根据月份获取当月最后一天
     * @param monthTime
     * @return
     * @throws ParseException
     */
    public static String lastDayOfMonth(String monthTime) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM").parse(monthTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 获取email用户姓名
     *
     * @param emailAddress
     */
    public static String getEmailUserName(String emailAddress) {
        return emailAddress.substring(0, emailAddress.lastIndexOf("@"));
    }

    /**
     * 判断userTel是否合法，userTel只能是数字
     *
     * @param userTel
     * @return true 合法 false不合法
     */
    public static boolean isTelNumber(String userTel) {
        String reg_phone = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}$";
        String reg_tel = "^(1[0-9][0-9]|1[0-9][0|3|6|8|9])\\d{8}$";
        boolean b_phpne = Pattern.compile(reg_phone).matcher(userTel).matches();
        boolean b_tel = Pattern.compile(reg_tel).matcher(userTel).matches();
        return (b_phpne || b_tel);
    }

    /**
     * 模糊判断电话号码是否合法，只能是数字
     *
     * @param userTel
     * @return
     */
    public static boolean isTelNumberBySlur(String userTel) {
        return Pattern.compile("^([\\s0-9]{0,12}$)").matcher(userTel).matches();
    }

    /**
     * 获取当前时间的字符串类型
     *
     * @return 处理后的字符串类型
     */
    public static String getNowTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    /**
     * 判断字符串中是否含有中文
     *
     * @param str
     * @return
     * @author jishenghua
     */
    public static boolean isContainsChinese(String str) {
        return Pattern.compile("[\u4e00-\u9fa5]").matcher(str).matches();
    }

    /**
     * 过滤html文件中的文本
     *
     * @param content
     * @return过滤后的文本
     */
    public static String filterText(String content) {
        return content.replace("/<(?:.|\\s)*?>/g", "");
    }

    /**
     * 去掉字符串中所有符号，不论是全角，还是半角的，或是货币符号或者空格等
     *
     * @param s
     * @return
     * @author jishenghua
     */
    public static String removeSymbolForString(String s) {
        StringBuffer buffer = new StringBuffer();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= 19968 && chars[i] <= 40869) || (chars[i] >= 97 && chars[i] <= 122) || (chars[i] >= 65 && chars[i] <= 90)) {
                buffer.append(chars[i]);
            }
        }
        return buffer.toString();
    }

    /**
     * 获取一个字符串的MD5
     *
     * @param msg
     * @return 加密后的MD5字符串
     * @throws NoSuchAlgorithmException
     */
    public static String md5Encryp(String msg) throws NoSuchAlgorithmException {
        // 生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(msg.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**
     * 判断是否插件URL 
     *
     * @return
     */
    public static boolean isPluginUrl(String url) {
        if (url != null && (url.startsWith("/plugin"))) {
            return true;
        }
        return false;
    }

    /**
     * 处理字符串null值
     *
     * @param beforeStr 处理前字符串
     * @return 处理后的字符串
     */
    public static String dealNullStr(String beforeStr) {
        if (null == beforeStr || beforeStr.length() == 0)
            return "";
        return beforeStr;
    }

    /**
     * 根据token截取租户id
     * @param token
     * @return
     */
    public static Long getTenantIdByToken(String token) {
        Long tenantId = 0L;
        if(StringUtil.isNotEmpty(token) && token.indexOf("_")>-1) {
            String[] tokenArr = token.split("_");
            if (tokenArr.length == 2) {
                tenantId = Long.parseLong(tokenArr[1]);
            }
        }
        return tenantId;
    }

    /**
     * 使用参数Format将字符串转为Date
     *
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException
     * @author jishenghua
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException {
        return new SimpleDateFormat(pattern).parse(strDate);
    }

    public static Date addDays(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); //需要将date数据转移到Calender对象中操作
        calendar.add(calendar.DATE, num);//把日期往后增加n天.正数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return date;
    }

    /**
     * 生成随机数字和字母组合
     * @param length
     * @return
     */
    public static String getCharAndNum(int length) {
        Random random = new Random();
        StringBuffer valSb = new StringBuffer();
        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        int charLength = charStr.length();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        return valSb.toString();
    }

    public static void main(String[] args) {
        String aa = "的付的反对法的发的说法";
        char[] bb = aa.toCharArray();
        for (char c : bb) {
            System.out.println(c);
        }
        System.out.println(getBeforeMonth(1));

        try {
            System.out.println(md5Encryp("guest"));
            System.out.println(md5Encryp("admin"));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
        }

        String value = "2333";
        System.out.println(checkStrIsNum(value));

        for (int i = 0; i < 100; i++) {
            System.out.print(getRandomChar() + "  ||    ");
        }
    }
}
