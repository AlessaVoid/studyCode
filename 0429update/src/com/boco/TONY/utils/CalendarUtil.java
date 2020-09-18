package com.boco.TONY.utils;

/**
 * @author tony
 * @describe CalendarUtil
 * @date 2019-11-08
 */
public class CalendarUtil {
    public static String getCalendarStringDateFormat(String dateString) {
        String year = dateString.substring(0, 4);
        String month = dateString.substring(4, 6);
        String day = dateString.substring(6, 8);
        return year + "-" + month + "-" + day;
    }

    public static String getCalendarStringTimeFormat(String timeString) {
        String hour = timeString.substring(0, 2);
        String minute = timeString.substring(2, 4);
        String second = timeString.substring(4, 6);
        return hour + ":" + minute + ":" + second;
    }

    public static void main(String[] args) {
        System.out.println( CalendarUtil.getCalendarStringDateFormat("20190103"));
        System.out.println( CalendarUtil.getCalendarStringTimeFormat("110305"));
    }
}
