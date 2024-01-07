package com.zaozhuang.newborn.util;

import android.text.TextUtils;
import android.util.Log;

import com.zaozhuang.newborn.BuildConfig;

/**
 * Created by wavewave on 17/11/17.
 */

public class MyLogs {
    // TODO: 2020-03-06 打印log
    public static boolean isDebug = "debug".equals(BuildConfig.BUILD_TYPE);

    public static void LogSuccess(String url, Object message) {
        if (isDebug) {
            Log.e("-------------->", url + "成功:" + message);
        }
    }

    public static void LogError(String url, Object message) {
        if (isDebug) {
            Log.e("-------------->", url + "失败:" + message);
        }
    }

    public static void LogNormal(Object message) {
        if (isDebug) {
            Log.e("-------------->", message.toString());
        }
    }

    //log长度有限制，这样能打印全
    public static void longE(String tag, String msg) {
        if (!isDebug || TextUtils.isEmpty(msg)) {
            return;
        }
        int length = msg.length();
        int start = 0;
        int end = 0;
        while (msg.substring(start, length).length() > 2000) {
            end += 2000;
            Log.e("", "");
            Log.e(tag, msg.substring(start, end));
            start = end;
        }
        Log.e(tag, msg.substring(end, length));
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }
}
