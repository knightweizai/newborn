package mangogo.appbase.util;

import android.util.Log;

import mangogo.appbase.BaseApplication;
import mangogo.appbase.BuildConfig;


public class LogUtil {

    private static saveLogListener mListener = null;

    public static void setListener(saveLogListener listener){
        mListener = listener;
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (BaseApplication.getGlobalContext().isPrintLog()) {
                Log.v(tag, msg);
            }

            if (mListener != null) {
                String strLogString = "verbose: " + tag + " : " + msg;
                mListener.saveLog(strLogString);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (BaseApplication.getGlobalContext().isPrintLog()) {
                Log.d(tag, msg);
            }

            if (mListener != null) {
                String strLogString = "debug: " + tag + " : " + msg;
                mListener.saveLog(strLogString);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (BaseApplication.getGlobalContext().isPrintLog()) {
                Log.i(tag, msg);
            }

            if (mListener != null) {
                String strLogString = "info: " + tag + " : " + msg;
                mListener.saveLog(strLogString);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            if (BaseApplication.getGlobalContext().isPrintLog()) {
                Log.w(tag, msg);
            }

            if (mListener != null) {
                String strLogString = "warn: " + tag + " : " + msg;
                mListener.saveLog(strLogString);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (BaseApplication.getGlobalContext().isPrintLog()) {
            Log.e(tag, msg);
        }

        if (mListener != null) {
            String strLogString = "error: " + tag + " : " + msg;
            mListener.saveLog(strLogString);
        }
    }

    public interface saveLogListener {
        void saveLog(String log);
    }
}
