package mangogo.appbase.util;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

//import com.ctb.app_base.BuildConfig;

import org.apaches.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import mangogo.appbase.BaseApplication;
import mangogo.appbase.BuildConfig;

public class DeviceUtils {

    private static final Object sLock = new Object();
    private static volatile String sImei = "";
    private static volatile String sDeviceId = "";
    private static volatile int sDangerous = 0;
    private static volatile int sDangerousFlag = 0;
    private static volatile String sDangerousStr = null;

    public static String getDeviceId() {
        if (Predictor.isNotEmpty(sDeviceId)) {
            return sDeviceId;
        }
        try {
            String androidId = Settings.System.getString(BaseApplication.getGlobalContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            String deviceId = Build.BOARD + Build.BRAND + Build.BOOTLOADER + Build.CPU_ABI +
                    Build.DEVICE + Build.DISPLAY + Build.HOST +
                    Build.ID + Build.MANUFACTURER + Build.MODEL +
                    Build.PRODUCT + Build.TAGS + Build.TYPE +
                    Build.USER + androidId + Build.SERIAL + Build.HARDWARE + Build.VERSION.SDK_INT;
            deviceId = DigestUtils.md5Hex(deviceId);
            if(!TextUtils.isEmpty(androidId)){
                deviceId = androidId;
            }
            synchronized (sLock) {
                if (Predictor.isEmpty(sDeviceId)) {
                    sDeviceId = deviceId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sDeviceId;
    }

    public static String getDeviceType() {
        return "phone";
    }

    public static String getDangerous() {
        if ((sDangerousFlag & 2) == 0) {
            synchronized (sLock) {
                if ((sDangerousFlag & 2) != 0) {
                    return sDangerousStr;
                }

                sDangerousFlag |= 2;
                if (!BuildConfig.DEBUG && (isEnableAdb() || isDeviceRoot())) {
                    sDangerous |= 2;
                }
                resetDangerousStr();
            }
        }
        return sDangerousStr;
    }

    public static String getAndroidVersion() {
        String androidVersion;
        switch (Build.VERSION.SDK_INT) {
            case 16:
                androidVersion = "Android 4.1";
                break;

            case 17:
                androidVersion = "Android 4.2";
                break;

            case 18:
                androidVersion = "Android 4.3";
                break;

            case 19:
                androidVersion = "Android 4.4";
                break;

            case 20:
                androidVersion = "Android 4.4W";
                break;

            case 21:
                androidVersion = "Android 5.0";
                break;

            case 22:
                androidVersion = "Android 5.1";
                break;

            case 23:
                androidVersion = "Android 6.0";
                break;

            case 24:
                androidVersion = "Android 7.0";
                break;

            case 25:
                androidVersion = "Android 7.1";
                break;

            case 26:
                androidVersion = "Android 8.0";
                break;
            case 27:
                androidVersion = "Android 8.1";
                break;

            case 28:
                androidVersion = "Android 9.0";
                break;
            case 29:
                androidVersion = "Android 10.0";
                break;
            case 30:
                androidVersion = "Android 11.0";
                break;
            default:
                androidVersion = String.valueOf(Build.VERSION.SDK_INT);
                break;
        }
        return androidVersion;
    }

    public static double getAndroidVersionFloat() {
        double androidVersion;
        switch (Build.VERSION.SDK_INT) {
            case 10:
                androidVersion = 2.3;
                break;

            case 11:
                androidVersion = 3.0;
                break;

            case 12:
                androidVersion = 3.1;
                break;

            case 13:
                androidVersion = 3.2;
                break;

            case 14:
                androidVersion = 4.0;
                break;

            case 15:
                androidVersion = 4.0;
                break;

            case 16:
                androidVersion = 4.1;
                break;

            case 17:
                androidVersion = 4.2;
                break;

            case 18:
                androidVersion = 4.3;
                break;

            case 19:
                androidVersion = 4.4;
                break;

            case 20:
                androidVersion = 4.4;
                break;

            case 21:
                androidVersion = 5.0;
                break;

            case 22:
                androidVersion = 5.1;
                break;

            case 23:
                androidVersion = 6.0;
                break;

            case 24:
                androidVersion = 7.0;
                break;

            case 25:
                androidVersion = 7.1;
                break;

            case 26:
                androidVersion = 8.0;
                break;

            case 27:
                androidVersion = 8.1;
                break;

            case 28:
                androidVersion = 9.0;
                break;

            default:
                androidVersion = 0;
                break;
        }
        return androidVersion;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getBoard() {
        return Build.BOARD;
    }

    public static String getAndroidId(Context context) {
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        return ANDROID_ID;
    }

    public static String getHardware() {
        return Build.HARDWARE;
    }

    public static String getDisPlay() {
        return Build.DISPLAY;
    }

    public static String getTotalRam(Context context) {
        //获取运行内存的信息
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        return String.valueOf(info.totalMem);//返回1GB/2GB/3GB/4GB
    }

    public static String getTotalRom() {
        File path = Environment.getDataDirectory();
        StatFs mStatFs = new StatFs(path.getPath());
        long blockSize = mStatFs.getBlockSize();
        long totalBlocks = mStatFs.getBlockCount();
        return String.valueOf(totalBlocks * blockSize);
    }

    public static String getImei() {
        if (Predictor.isNotEmpty(sImei)) {
            return sImei;
        }

        if (PermissionUtils.checkReadWritePermission() && PermissionUtils.checkReadPhoneStatePermission()) {
            synchronized (sLock) {
                if (Predictor.isNotEmpty(sImei)) {
                    return sImei;
                }

                if (!(PermissionUtils.checkReadWritePermission() && PermissionUtils.checkReadPhoneStatePermission())) {
                    return "";
                }

                String imei1 = readImeiFromFile();
                String imei2 = readImeiFromSystem();
                if (TextUtils.isEmpty(imei2)) {
                    return "";
                }

                if (Predictor.isNotEmpty(imei1)) {
                    sDangerousFlag |= 1;
                    if (!imei1.equalsIgnoreCase(imei2)) {
                        sDangerous |= 1;
                    }
                    sImei = imei1;
                } else {
                    sImei = imei2;
                    sDangerousFlag |= 4;
                    if (checkTencentExists() || checkTbsExists()) {
                        writeImeiToFile(imei2);
                    } else {
                        sDangerous |= 4;
                    }
                }
                resetDangerousStr();
                return sImei;
            }
        }
        return "";
    }

//    private static void resetDangerousStr() {
//        if ((sDangerous & 1) != 0) {
//            sDangerousStr = "1";
//        }
//        if ((sDangerous & 2) != 0) {
//            if (Predictor.isNotEmpty(sDangerousStr)) {
//                sDangerousStr = "2|" + sDangerousStr;
//            } else {
//                sDangerousStr = "2";
//            }
//        }
//        if ((sDangerous & 4) != 0) {
//            if (Predictor.isNotEmpty(sDangerousStr)) {
//                sDangerousStr = "3|" + sDangerousStr;
//            } else {
//                sDangerousStr = "3";
//            }
//        }
//    }

    private static void resetDangerousStr() {
        sDangerousStr = "";
        if ((sDangerous & 1) != 0) {
            sDangerousStr = "1";
        }
        if ((sDangerous & 2) != 0) {
            if (Predictor.isNotEmpty(sDangerousStr)) {
                sDangerousStr += "|2";
            } else {
                sDangerousStr = "2";
            }
        }
        if ((sDangerous & 4) != 0) {
            if (Predictor.isNotEmpty(sDangerousStr)) {
                sDangerousStr += "|3";
            } else {
                sDangerousStr = "3";
            }
        }
    }


    private static String readImeiFromSystem() {
        if (PermissionUtils.checkReadPhoneStatePermission()) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getGlobalContext().getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    String imei = telephonyManager.getDeviceId();
                    if (Predictor.isNotEmpty(imei) && !imei.equalsIgnoreCase("null")) {
                        return imei;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static int getNetState() {
        return NetStatusUtils.getNetworkStateDetail(BaseApplication.getGlobalContext());
    }

    private static String readImeiFromFile() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "systemlog");
            if (!file.exists()) {
                return "";
            }

            String imei;
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            try {
                imei = bufferedReader.readLine();
                if (imei == null) {
                    imei = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
                imei = "";
            }

            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imei.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static boolean checkTbsExists() {
        try {
            String path = BaseApplication.getGlobalContext().getExternalCacheDir().getCanonicalPath();
            File file = new File(path.substring(0, path.indexOf("com.ctb.opencar")));
            File[] files = file.listFiles();
            for (File subFile : files) {
                String f = subFile.getName();
                if (subFile.isDirectory() && subFile.getName().startsWith("com.tencent.")) {
                    String thslogPath = subFile.getCanonicalPath();
                    if (thslogPath.endsWith("/")) {
                        thslogPath += "files/tbslog/tbslog.txt";
                    } else {
                        thslogPath += "/files/tbslog/tbslog.txt";
                    }
                    if (checkFile(new File(thslogPath), 5120)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkTencentExists() {
        try {
            String path = Environment.getExternalStorageDirectory().getCanonicalPath();
            if (!path.endsWith("/")) {
                path += "/";
            }

            String path1 = path + "tencent/msflogs/com/tencent/mobileqq";
            File[] files = new File(path1).listFiles();
            for (File subFile : files) {
                if (checkFile(subFile, 2048)) {
                    return true;
                }
            }

            String path2 = path + "tencent/MicroMsg/xlog";
            files = new File(path2).listFiles();
            for (File subFile : files) {
                if (checkFile(subFile, 2048)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkFile(File file, int length) {
        return (file.exists() && file.isFile() && file.length() > length);
    }

    private static void writeImeiToFile(String imei) {
        File file = new File(Environment.getExternalStorageDirectory(), "systemlog");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            try {
                bufferedWriter.write(imei);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDeviceRoot() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    public static boolean isEnableAdb() {
        boolean enableAdb = false;
        try {
            enableAdb = (Settings.Secure.getInt(BaseApplication.getGlobalContext().getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enableAdb;
    }

    private static boolean checkRootMethod1() {
        try {
            String buildTags = Build.TAGS;
            return buildTags != null && buildTags.contains("test-keys");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkRootMethod2() {
        try {
            String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                    "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
            for (String path : paths) {
                if (new File(path).exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        boolean root = false;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            root = in.readLine() != null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (process != null) {
                process.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public static String getApplicationName() {
        try {
            PackageManager packageManager = BaseApplication.getGlobalContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(BaseApplication.getGlobalContext().getPackageName(), 0);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "开车吧OpenCar";
    }
}
