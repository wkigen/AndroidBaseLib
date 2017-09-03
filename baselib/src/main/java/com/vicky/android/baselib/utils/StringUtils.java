package com.vicky.android.baselib.utils;

import java.util.Random;

/**
 * Created by vicky on 2017/8/29.
 */
public class StringUtils {

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
}
