package com.vicky.android.baselib.utils;

import java.util.Date;
import java.util.Random;

/**
 * Created by vicky on 2017/8/29.
 */
public class StringUtils {

    static final int c_second = 1 * 1000;
    static final int c_minute = 60 * c_second;

    public static boolean hasHttpPrefix(String src){
        return src.startsWith("http://");
    }

    public static String addHttpPrefix(String src){
        if (!src.startsWith("http://")){
            return "http://" + src;
        }
        return src;
    }

    public static String getRandomString(int length){
        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int ii = 0 ; ii < length;ii++){
            int number = random.nextInt(base.length());
            builder.append(base.charAt(number));
        }
        return builder.toString();
    }


    public static String getMinuteSecond(int millsecond){
        int temp = millsecond % c_minute;
        int second = temp / c_second;
        int minute = ( millsecond - temp) / c_minute;
        String s_second = second+"";
        String s_minute = minute+"";
        if(second < 10)
            s_second = "0"+s_second;
        if(minute < 10)
            s_minute = "0"+s_minute;
        return s_minute+":"+s_second;
    }

}
