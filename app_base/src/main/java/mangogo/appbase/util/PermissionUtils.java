package mangogo.appbase.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;

import androidx.core.content.PermissionChecker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mangogo.appbase.BaseApplication;

@SuppressLint("WrongConstant")
public class PermissionUtils {

    public static boolean checkPermission(Context context, String packageName) {
        return PermissionChecker.checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED && PermissionChecker.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED && PermissionChecker.checkPermission(context, Manifest.permission.CAMERA, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkReadWriteContacts() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.READ_CONTACTS, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED && PermissionChecker.checkPermission(context, Manifest.permission.WRITE_CONTACTS, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkSendSms() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.SEND_SMS, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.READ_PHONE_STATE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkReadWritePermission() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkWritePermission() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkVideoPermission() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.CAMERA, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.RECORD_AUDIO, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkAllPermission() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.CAMERA, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.RECORD_AUDIO, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkLocationPermission() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkReadPhoneStatePermission() {
        Context context = BaseApplication.getGlobalContext();
        String packageName = context.getPackageName();
        return PermissionChecker.checkPermission(context, Manifest.permission.READ_PHONE_STATE, Process.myPid(), Process.myUid(), packageName) == PackageManager.PERMISSION_GRANTED;
    }

    public static String[] checkPermission(String... permissions) {
        if (permissions != null && permissions.length > 0) {
            List<String> permissionList = new ArrayList<>();
            Context context = BaseApplication.getGlobalContext();
            String packageName = context.getPackageName();
            for (String permission : permissions) {
                if (PermissionChecker.checkPermission(context, permission, Process.myPid(), Process.myUid(), packageName) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }
            if (permissionList.size() > 0) {
                String[] requestPermissions = new String[permissionList.size()];
                return permissionList.toArray(requestPermissions);
            }
        }
        return null;
    }

    /***
     * 检查悬浮窗开启权限
     * @param context
     * @return
     */
    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", Process.myUid(), context
                        .getPackageName());
                return Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }

    /**
     * 悬浮窗开启权限
     *
     * @param context
     * @param requestCode
     */
    public static void requestFloatPermission(Activity context, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivityForResult(intent, requestCode);
    }
}
