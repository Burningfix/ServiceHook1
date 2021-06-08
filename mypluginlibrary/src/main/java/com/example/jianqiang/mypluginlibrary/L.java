package com.example.jianqiang.mypluginlibrary;

import android.util.Log;

public class L {

    private static final String TAG = "sanbo";


    public static void d(Throwable e) {
        d(Log.getStackTraceString(e));
    }

    public static void d(String info) {
        d(TAG, info);
    }

    private static void d(String tag, String info) {
        print(Log.DEBUG, tag, info);
    }

    public static void i(Throwable e) {
        i(Log.getStackTraceString(e));
    }

    public static void i(String info) {
        i(TAG, info);
    }

    private static void i(String tag, String info) {
        print(Log.INFO, tag, info);
    }

    public static void print(int priority, String tag, String info) {
        Log.println(priority, tag, info);
    }
}
