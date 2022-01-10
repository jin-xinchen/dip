package com.jin.http.pro;

import java.util.Properties;

public class ProperUtil {
    public static String get(String maxTotal) {
        String i = null;
        Properties pro = new Properties();
        i = pro.getProperty(maxTotal);
        return i;
    }
}
