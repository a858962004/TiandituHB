package com.gangbeng.tiandituhb.utils;

import android.util.Log;

public class MyLogUtil {
    public static void showLog(Object log) {
        Log.e("log======", String.valueOf(log));
    }

    public static void showLog(String tag, Object log) {
        Log.e(tag, String.valueOf(log));
    }

}
