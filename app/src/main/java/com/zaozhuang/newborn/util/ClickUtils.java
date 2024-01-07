package com.zaozhuang.newborn.util;

import android.view.View;

public class ClickUtils {

    private static long sTime = 0;
    private static long fTime = 0;

    public static boolean isDoubleClick(View v) {
        return isDoubleClick(v, 500);
    }

    public static boolean isDoubleClick() {
        return isDoubleClick(500);
    }

    public static boolean check(View v) {
        return !isDoubleClick(v);
    }


    public static boolean isDoubleClick(View v, long interval) {
        long time = System.currentTimeMillis();
        long pastTime = time - sTime;
        if (pastTime > interval) {
            sTime = time;
            return false;
        }
        return true;
    }

    public static boolean isDoubleClick(long interval) {
        long time = System.currentTimeMillis();
        long pastTime = time - sTime;
        if (pastTime > interval) {
            sTime = time;
            return false;
        }
        return true;
    }
}
