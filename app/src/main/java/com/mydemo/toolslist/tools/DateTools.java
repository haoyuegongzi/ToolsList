package com.mydemo.toolslist.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.DataFormatException;

public class DateTools {
    /**
     *   获取当前时间——年月日 时分秒 / 年月日 / 时分秒
     * @Name getNowDate
     * @Description TODO:
     * @param dateFormat
     * @return String:返回字符串格式 yyyy-MM-dd HH:mm:ss/  yyyy//MM/dd HH时:mm分:ss秒/  yyyy//MM/dd等等
     * @Author chen1
     * @Date 2019年8月1日 上午11:06:19
     */
    public static String getNowDate(String dateFormat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 时间 hh:mm:ss格式和hh时mm分ss秒格式之间的相互转化
     * @Name getNewTimeFormat
     * @Description TODO
     * @param oldFormatTime：旧的时间格式
     * @param newFormatTime：新的时间格式
     * @param sDateTime：原本要转化的时间
     * @return String
     * @Author chen1
     * @Date 2019年7月14日 下午2:56:04
     */
    public static String getNewTimeFormat(String oldFormatTime, String newFormatTime, String sDateTime) {
        if (null == oldFormatTime || oldFormatTime.isEmpty() ||
                null == newFormatTime || newFormatTime.isEmpty() ||
                null == sDateTime || sDateTime.isEmpty()) {
            return "";
        }
        SimpleDateFormat sFormat01 = new SimpleDateFormat(oldFormatTime);
        SimpleDateFormat sFormat02 = new SimpleDateFormat(newFormatTime);
        try {
            Date date = sFormat01.parse(sDateTime);
            return sFormat02.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * TODO：四种日期格式yyyy/MM/dd、yyyy年MM月dd日、yyyy-MM-dd、yyyyMMdd后面跟随两种时间的的任意互转
     * @Name getDateAndTimeFormat
     * @Description TODO
     * @param oldFormatTime：旧的日期时间格式→→类似于yyyy-MM-dd hh:mm:ss
     * @param newFormatTime：新的日期时间格式→→类似于yyyy年MM月dd日 hh时mm分ss秒
     * @param sDateTime：原本要转化的日期和时间→→类似于2019-7-14 14:17:35
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年7月14日 下午2:57:39
     */
    public static String dateAndTimeFormat(String oldFormatTime, String newFormatTime, String sDateTime) {
        if (null == oldFormatTime || oldFormatTime.isEmpty() ||
                null == newFormatTime || newFormatTime.isEmpty() ||
                null == sDateTime || sDateTime.isEmpty()) {
            return "";
        }
        SimpleDateFormat sFormat01 = new SimpleDateFormat(oldFormatTime);
        SimpleDateFormat sFormat02 = new SimpleDateFormat(newFormatTime);
        try {
            Date date = sFormat01.parse(sDateTime);
            return sFormat02.format(date);
        } catch (Exception e) {
        }
        return "";
    }


    /**
     * TODO：yyyy/MM/dd、yyyy年MM月dd日、yyyy-MM-dd、yyyyMMdd四种日期格式的任意互转
     * @Name dateAndTimeFormatConversion
     * @Description TODO oldFormat旧的日期格式与sDate原本要转化的日期在格式上要一直
     * @param oldFormat：旧的日期格式
     * @param newFormat：新的日期格式
     * @param sDate ：原本要转化的日期
     * @return String
     * @Author chen1
     * @Date 2019年7月14日 下午2:48:10
     */
    public static String dateAndTimeFormatConversion(String oldFormat, String newFormat, String sDate) {
        if (null == oldFormat || oldFormat.isEmpty() ||
                null == newFormat || newFormat.isEmpty() ||
                null == sDate || sDate.isEmpty()) {
            return "";
        }
        String newDateString = "";
        SimpleDateFormat sFormat01 = new SimpleDateFormat(oldFormat);
        SimpleDateFormat sFormat02 = new SimpleDateFormat(newFormat);
        try {
            Date date = sFormat01.parse(sDate);
            newDateString = sFormat02.format(date);
            return newDateString;
        } catch (Exception e) {
        }
        return newDateString;
    }

    /**
     * 将指定时间转换为long
     * @param strTime
     * @param formatType
     * @return long
     */
    public static long stringToLong(String strTime, String formatType) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatType);
            Date date = formatter.parse(strTime); // String类型转成date类型
            if (date == null) {
                return System.currentTimeMillis();
            } else {
                long currentTime = date.getTime(); // date类型转成long类型
                return currentTime;
            }
        } catch (ParseException e) {
            return System.currentTimeMillis();
        }catch (Exception e) {
            return System.currentTimeMillis();
        }
    }

    /**
     * 指定时间转换为long
     * @Name getTimeInMillis
     * @param year：年
     * @param month：月
     * @param day：日
     * @param hours：时
     * @param minutes：分
     * @param seconds：秒
     * @param formatType：时间格式"yyyy-MM-dd HH:mm:ss"、"yyyyMMdd HHmmss"等等
     * @return
     * @return long
     * @Author chen1
     * @Date 2019年8月1日 上午11:22:57
     */
    public static long getTimeInMillis(int year, int month, int day, int hours, int minutes, int seconds, String formatType) {
        TimeZone DEFAULT_SERVER_TIME_ZONE = TimeZone.getTimeZone("GMT+08:00");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(DEFAULT_SERVER_TIME_ZONE);
        calendar.set(year, month - 1, day, hours, minutes, seconds);
        SimpleDateFormat format = new SimpleDateFormat(formatType, Locale.getDefault());
        format.setTimeZone(DEFAULT_SERVER_TIME_ZONE);
        try {
            return format.parse(format.format(calendar.getTime())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取到当天结束还有多少秒
     * @Name getEndTime
     * @Description TODO
     * @return
     * @return long
     * @Author chen1
     * @Date 2019年8月1日 上午11:25:54
     */
    private static long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        // HOUR_OF_DAY 24小时制
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTimeInMillis() / 1000;
    }

    /**
     * 获取day天前或者后的日期  day为负表示之前  为正表示之后
     * @Name getLastDay
     * @Description TODO
     * @param day：day天前或者day天后
     * @param dateFormat：返回日期的格式，yyyy/MM/dd、yyyy年MM月dd日、yyyy-MM-dd、yyyyMMdd等等
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 上午11:36:41
     *
     */
    public static String getLastDay(int day, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String dateString = formatter.format(calendar.getTime());
        return dateString;
    }

    /**
     * 获得allMonth月前的日期，allMonth可以为小于12的任意值
     * @Name lastMonth
     * @Description TODO
     * @param allMonth
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 上午11:39:00
     *
     */
    public static String lastMonth(int allMonth) {
        Date date = new Date();
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date)) - allMonth;
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
        if (month <= 0) {
            int yearFlag = (month * (-1)) / 12 + 1;
            int monthFlag = (month * (-1)) % 12;
            year -= yearFlag;
            month = monthFlag * (-1) + 12;
        } else if (day > 28) {
            if (month == 2) {
                if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                    day = 29;
                } else {
                    day = 28;
                }
            } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
                day = 30;
            }
        }
        String y = year + "";
        String m = "";
        String d = "";
        if (month < 10) {
            m = "0" + month;
        } else {
            m = month + "";
        }
        if (day < 10) {
            d = "0" + day;
        } else {
            d = day + "";
        }
        return y + m + d;
    }

    /**
     * 两个日期之间相隔天数
     * @Name getDaysBetweenTwoDates
     * @Description TODO
     * @param dateFrom：开始日期
     * @param dateEnd：结束日期
     * @param pattrn：日期格式
     * @return
     * @return int
     * @Author chen1
     * @Date 2019年8月1日 上午11:46:20
     *
     */
    public static int getDaysBetweenTwoDates(String dateFrom, String dateEnd,String pattrn) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat(pattrn);
        dateFormat.setLenient(false);

        Date dtFrom = dateFormat.parse(dateFrom);
        Date dtEnd = dateFormat.parse(dateEnd);
        if(dtFrom==null){
            throw new DataFormatException("开始日期不能为空！");
        }
        if(dtEnd==null){
            throw new DataFormatException("结束日期不能为空！");
        }
        return 0;
    }

    /**
     * 获得指定日期的前后几天
     * @Name getDateBefore
     * @Description TODO
     * @param dateFrom:指定的日期
     * @param pattrn：日期格式
     * @param days：指定的前后几天，为正，表示向后，为负，表示向前
     * @return
     * @return Date
     * @throws ParseException
     * @Author chen1
     * @Date 2019年8月1日 上午11:52:20
     *
     */
    public static Date getDateBefore(String dateFrom, String pattrn, int days) throws ParseException{
        DateFormat dateFormat = new SimpleDateFormat(pattrn);
        dateFormat.setLenient(false);
        Date origDate = dateFormat.parse(dateFrom);
        Calendar cal = Calendar.getInstance();
        cal.setTime(origDate);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + days);
        return cal.getTime();
    }

    /**
     * 取得一天的最后时间点
     * @Name getDayOfLastTime
     * @Description TODO
     * @return
     * @throws DataFormatException
     * @return Date
     * @Author chen1
     * @Date 2019年8月1日 下午2:07:19
     */
    public static Date getDayOfLastTime() throws DataFormatException{
        Date date = new Date();
        Calendar cal =  Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
        return new Date(cal.getTimeInMillis() -1);
    }

    /**
     * 取得一天的最早时间点
     * @Name getDayOfFirstTime
     * @Description TODO
     * @return
     * @throws DataFormatException
     * @return Date
     * @Author chen1
     * @Date 2019年8月1日 下午2:09:20
     */
    public static Date getDayOfFirstTime() throws DataFormatException{
        Date date = new Date();
        Calendar cal =  Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE));
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 当前日期加上天数后的日期
     * @Name plusDay
     * @Description TODO
     * @param num:指定的天数
     * @param formatType：返回的日期格式yyyy/MM/dd、yyyy年MM月dd日、yyyy-MM-dd、yyyyMMdd等等
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:10:37
     *
     */
    public static String plusDay(int num, String formatType){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        Calendar ca = Calendar.getInstance();
        // num为增加的天数，可以改变的
        ca.add(Calendar.DATE, num);
        d = ca.getTime();
        String enddate = format.format(d);
        return enddate;
    }

    /**
     * 指定日期加上天数后的日期
     * @Name plusDay
     * @Description TODO
     * @param num：指定的天数
     * @param newDate：指定的日期
     * @param formatType：：返回的日期格式yyyy/MM/dd、yyyy年MM月dd日、yyyy-MM-dd、yyyyMMdd等等
     * @return
     * @throws ParseException
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:12:38
     *
     */
    public static String plusDay(int num, String newDate, String formatType) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;
    }

    /**
     * 过去的几个月
     * @Name getDistanceNowMonthBofore
     * @Description TODO
     * @param num：往前推几个月的数量
     * @param formatType：日期格式yyyy/MM/dd、yyyy年MM月dd日、yyyy-MM-dd、yyyyMMdd等等
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:13:54
     *
     */
    public static String getDistanceNowMonths(int num, String formatType){
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -num);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    /**
     * 过去的num年的日期
     * @Name getDistanceNowOneYearBofore
     * @Description TODO
     * @param num：往前推几年的数量
     * @param formatType：日期格式yyyy/MM/dd、yyyy年MM月dd日、yyyy-MM-dd、yyyyMMdd等等
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:15:15
     *
     */
    public static String getDistanceNowYears(int num, String formatType){
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -num);
        Date y = c.getTime();
        String year = format.format(y);
        return year;
    }
}
