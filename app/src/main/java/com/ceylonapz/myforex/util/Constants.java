package com.ceylonapz.myforex.util;

public class Constants {

    static {
        System.loadLibrary("native-lib");
    }


    public static native String getBaseUrl();

    public static String BASE_URL = getBaseUrl();
}
