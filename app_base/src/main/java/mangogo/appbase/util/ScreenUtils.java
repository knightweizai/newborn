package mangogo.appbase.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mangogo.appbase.BaseApplication;


public class ScreenUtils {

    public static boolean hasNotchInScreen(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int limit = dip2px(25);
            int statusBarHeight = getStatusBarHeight(context);
            return (statusBarHeight > limit);
        }
        return false;
    }

    public static void setFitsSystemWindows(View view, boolean fitSystemWindows) {
        if (view != null && view.getFitsSystemWindows() != fitSystemWindows) {
            view.setFitsSystemWindows(fitSystemWindows);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                view.requestApplyInsets();
            } else {
                view.requestFitSystemWindows();
            }
        }
    }

    public static int getScreenWidth() {
        return getScreenSize(BaseApplication.getGlobalContext())[0];
    }

    public static int getScreenHeight() {
        return getScreenSize(BaseApplication.getGlobalContext())[1];
    }

    public static int getStatusBarHeight(Context context) {
        try {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return dip2px(24);
    }

    public static void initTitleBar(View titleBar) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(titleBar.getContext());
            if (statusBarHeight < 20) {
                statusBarHeight = dip2px(32);
            }
            ViewUtils.setViewPaddingTop(titleBar, statusBarHeight);
        }
    }

    public static void initTitleBar(View titleBar, int addHeight) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(titleBar.getContext());
            ViewUtils.setViewPaddingTop(titleBar, statusBarHeight + addHeight);
        }
    }

    public static void setStatusBarFontColor(Activity activity, boolean black) {
        if (activity == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            if (black) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= (~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }

        if (OsUtils.isMiui()) {
            setMiuiUI(activity, black);
            return;
        }

        if (OsUtils.isFlyme()) {
            setFlymeUI(activity, black);
        }
    }

    public static void setLightNavigationBar(Activity activity, boolean isWechat) {
        if (isWechat) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setNavigationBarColor(0xff242633);
                int vis = activity.getWindow().getDecorView().getSystemUiVisibility();
                if (isWechat) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
                activity.getWindow().getDecorView().setSystemUiVisibility(vis);
            }
        }
    }


    //设置Flyme的字体
    private static void setFlymeUI(Activity activity, boolean black) {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (black) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置MIUI字体
    private static void setMiuiUI(Activity activity, boolean black) {
        try {
            Window window = activity.getWindow();
            Class clazz = activity.getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (black) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] getScreenSize(Context context) {
        int[] size = new int[2];
        Point point = new Point();

        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(point);

        size[0] = point.x;
        size[1] = point.y;
        return size;
    }

    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dipValue * scale);
    }

    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(pxValue / scale);
    }

    public static int sp2px(float sp) {
        float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f);
    }

    public static float getXPpi(Context context) {
        if (context.getResources().getDisplayMetrics().xdpi > 0) {
            if (OsUtils.isFlyme()) {
                // 魅族PPI不准确
                return context.getResources().getDisplayMetrics().xdpi * 404 / 480.0f;
            }
            return context.getResources().getDisplayMetrics().xdpi;
        }
        return 0;
    }
}
