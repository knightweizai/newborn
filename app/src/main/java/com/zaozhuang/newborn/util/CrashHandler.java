package com.zaozhuang.newborn.util;

import android.content.Context;

import com.zaozhuang.newborn.BuildConfig;
import com.zaozhuang.newborn.GlobalApplication;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import mangogo.appbase.util.AppUtils;
import mangogo.appbase.util.LogUtil;
import mangogo.appbase.util.TimeUtils;


public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private static final String TAG = "CrashHandler";
    public static Thread.UncaughtExceptionHandler mDefaultHandler;


    private CrashHandler() {

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    public static void init(Context context) {
        if (mDefaultHandler == null) {
            new CrashHandler();
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex)){
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
            if(BuildConfig.DEBUG){
                mDefaultHandler.uncaughtException(thread, ex);
            }else{
                ((GlobalApplication) GlobalApplication.getGlobalContext()).exit();
            }
        }
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            LogUtil.d(TAG, "no crash ?");
            return true;
        }

//        if(!BuildConfig.DEBUG){
//            return true;
//        }

        ex.printStackTrace();

        // 保存本地Crash日志
        String strError = createCrashMessage(ex);
        String path  = FileUtils.saveCrashLog(strError);
        File file = new File(path);
        if(file.exists()){
            LogUtil.d(TAG, "zip crash file " + file.getPath());
            try {
                String crashfilepath = FileUtils.getCrashFileDir() + "crash-" +".zip";
                FileUtils.zipFile(file,crashfilepath);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
                return  false;
            }
        }
//        // 等待1s
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 关闭进程
//        ((GLobalApplication)GLobalApplication.getGlobalContext()).exit();

        return true;
    }

    private String createCrashMessage(Throwable ex) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("==================crash begin==================").append("\n");
        buffer.append("crashtime: ").append(TimeUtils.getCurTime()).append("\r\n");
        buffer.append("device: ").append(AppUtils.getDeviceName()).append("\n");
        buffer.append("app_ver: ").append(AppUtils.getAppVersion()).append("\n");
        buffer.append(getCrashInfo(ex));
        return buffer.toString();
    }

    private String getCrashInfo(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        return info.toString();
    }


}
