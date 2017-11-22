package com.viewhigh.libs.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by huntero on 17-3-23.
 */

public class StringUtils {
    public static String ifNull(String value, String def) {
        return TextUtils.isEmpty(value) ? def : value;
    }

    public static String ifNull(Object value, String def) {
        return value == null ? def : value.toString();
    }

    /**
     * 四舍五入到小数点后两位
     *
     * @param d
     * @return
     */
    public static String floatLeftTwoUp(double d) {
        return twoDecimalPlaces(d, 2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String floatLeftTwoUp(String s) {
        double d;
        try {
           d = Double.parseDouble(s);
        }catch (ClassCastException e){
            d= 0d;
        }
        return floatLeftTwoUp(d);
    }

    public static BigDecimal twoDecimalPlaces(double d, int num, int roundingMode) {
        BigDecimal b = new BigDecimal(d);
        BigDecimal db = b.setScale(num, roundingMode);
        return db;
    }

    /**
     * 去掉日期时间最后的秒数
     * @param time
     * @return
     */
    public static String removeTimeSeconds(String time){
        if(TextUtils.isEmpty(time)){
            return null;
        }
        if(time.contains(":")){
            int index = time.lastIndexOf(":");
            return time.substring(0,index);
        }
        return time;
    }

}
