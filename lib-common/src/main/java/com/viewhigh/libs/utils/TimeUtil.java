package com.viewhigh.libs.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getTimeBetweenTwoDate(String dateString1,String dateString2){
        if(TextUtils.isEmpty(dateString1) || TextUtils.isEmpty(dateString2)){
            return null;
        }
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(dateString1);
            date2 = df.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Long diff = Math.abs(date2.getTime() - date1.getTime());
        int days= (int) Math.floor(diff/(24*3600*1000));
        Long diff1=diff%(24*3600*1000);
        int hours= (int) Math.floor(diff1/(3600*1000));
        Long diff2=diff1%(3600*1000);
        int minutes= (int) Math.floor(diff2/(60*1000));
        Long diff3=diff2%((60*1000));
        int seconds=Math.round(diff3/1000);
        if(days == 0) {
            if(hours == 0) {
                if(minutes == 0) {
                    return seconds+"s";
                }else{
                    return minutes+"m"+seconds+"s";
                }
            }else{
                return hours+"h"+minutes+"m";
            }
        }else{
            return days+"d"+hours+"h";
        }
    }

    public static String getTimeBetweenToNow(String dateString) {
        if (TextUtils.isEmpty(dateString)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(System.currentTimeMillis());
        String nowString = format.format(now);
        return getTimeBetweenTwoDate(nowString, dateString);
    }

    /**
     * 两个日期之间的差值 ，第一个参数减去第二个参数，来判断大小
     * @param dateString1
     * @param dateString2
     * @return
     */
    public static long getDurationBetween2Date(String dateString1,String dateString2) {
        if (TextUtils.isEmpty(dateString1) || TextUtils.isEmpty(dateString2)) {
            return 0l;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(dateString1);
            date2 = df.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0l;
        }
        return date1.getTime() - date2.getTime();
    }


    /**
     * 两个日期之间的差值 ，第一个参数减去第二个参数，来判断大小
     * @param dateString1
     * @param dateString2
     * @return
     */
    public static long getDurationBetween2Time(String dateString1,String dateString2) {
        if (TextUtils.isEmpty(dateString1) || TextUtils.isEmpty(dateString2)) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(dateString1);
            date2 = df.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date1.getTime() - date2.getTime();
    }

    /**
     * 通过年月日拼接为YYYY-MM-DD;
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static String formatToDate(int year, int month, int dayOfMonth){
        return new StringBuffer().append(year).append("-")
                .append(String.format("%02d", month + 1)).append("-").append(String.format("%02d", dayOfMonth)).toString();
    }

    public static String timeStringToStamp(String date1){
        if(date1 == null){
            return null;
        }
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        try {
            Date dt1 = sdf.parse(date1);
            return String.valueOf(dt1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stampToString(String stamp){
        Date date = new Date(Long.valueOf(stamp));
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        return sdf.format(date);
    }


    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 获得YYYY年MM月DD日的日期
     * @return
     */
    public static String getNowDataYYYYMMDD_China(){
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    /**
     * 获得YYYY-MM-DD的日期
     * @return
     */
    public static String getNowDataYYYYMMDD(){
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    public static String getNowTimeHHmm(){
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

}