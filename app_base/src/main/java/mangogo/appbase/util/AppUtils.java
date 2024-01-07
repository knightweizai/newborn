package mangogo.appbase.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

//import com.ctb.app_base.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import mangogo.appbase.BaseApplication;

public class AppUtils {

    public static String sAppVersion;
    private static String sAndroidId;

    public static String getAppVersion() {
        return getAppVersion(BaseApplication.getGlobalContext());
    }

    public static String getAppVersion(Context context) {
        if (!TextUtils.isEmpty(sAppVersion)) {
            return sAppVersion;
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (packInfo != null && !TextUtils.isEmpty(packInfo.versionName)) {
                sAppVersion = packInfo.versionName;
                return sAppVersion;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    public static String getAppName() {
        try {
            PackageManager packageManager = BaseApplication.getGlobalContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    BaseApplication.getGlobalContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return BaseApplication.getGlobalContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager)
                BaseApplication.getGlobalContext().getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo :
                    activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == pid) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return null;
    }

    public static String getCurrentProcessNameByFile() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return "com.ctb.opencar";
        }
    }

    //todo wei application_id 如何获取
//    public static boolean isMainProcess() {
//        return BuildConfig.APPLICATION_ID.equals(getCurrentProcessNameByFile());
//    }




    public static String getDeviceName() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(android.os.Build.MANUFACTURER).append("-").append(android.os.Build.MODEL);
        buffer.append("(").append(getDeviceOSVersion()).append(")");
        return buffer.toString();
    }

    public static String getDeviceOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getOSCountry() {
        return BaseApplication.getGlobalContext().getResources().getConfiguration().locale.getCountry();
    }

    public static String getOSLanguage() {
        return BaseApplication.getGlobalContext().getResources().getConfiguration().locale.getLanguage();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground() {
        String packageName = BaseApplication.getGlobalContext().getPackageName();
        ActivityManager activityManager = (ActivityManager) BaseApplication.getGlobalContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && tasksInfo.size() > 0
                    && packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void startApp(String processName) {
        try {
            Context context = BaseApplication.getGlobalContext();
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(processName);
            if (intent != null) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
        }
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context,String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean checkAppInstalled(Context context,String pkgName) {
        if (pkgName== null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

}
