package mangogo.appbase.autolayout.core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.Window;

import mangogo.appbase.util.ScreenUtils;

public class AutoLayoutConfig {

    private static final String TAG = "AutoLayout";

    private static final String KEY_DESIGN_WIDTH = "design_width";
    private static final String KEY_DESIGN_HEIGHT = "design_height";
    private static final String KEY_DESIGN_PPI = "design_ppi";
    private static final String KEY_DESIGN_STATUS_BAR_HEIGHT = "design_status_bar_height";

    private static int mDesignWidth;
    private static int mDesignHeight;
    private static int mDesignStatusBarHeight;
    private static float mDesignPpi;

    private static int mScreenWidth;
    private static int mScreenHeight;
    private static int mAdjustedScreenHeight;
    private static float mScreenPpi;
    private static int mStatusBarHeight;

    private static float mWidthRate;
    private static float mHeightRate;
    private static float mPhysicalRate;
    private static float mDefaultRate;

    public static void setDesignPpi(float designPpi) {
        mDesignPpi = designPpi;
    }

    public static void init(Context context) {
        int[] screenSize = ScreenUtils.getScreenSize(context);
        mScreenWidth = screenSize[0];
        mScreenHeight = screenSize[1];
        mStatusBarHeight = ScreenUtils.getStatusBarHeight(context);
        mScreenPpi = ScreenUtils.getXPpi(context);
        Log.e(TAG, String.format("ScreenWidth=%d,ScreenHeight=%d,StatusBarHeight=%d,ScreenPpi=%f",
                mScreenWidth, mScreenHeight, mStatusBarHeight, mScreenPpi));

        readMetaData(context);
        resetConfig();
    }

    public static int getDesignWidth() {
        return mDesignWidth;
    }

    public static int getScreenWidth() {
        return mScreenWidth;
    }

    public static float getScreenPpi() {
        return mScreenPpi;
    }

    public static float getWidthRate() {
        return mWidthRate;
    }

    public static float getHeightRate() {
        return mHeightRate;
    }

    public static float getPhysicalRate() {
        return mPhysicalRate;
    }

    public static float getDefaultRate() {
        return mDefaultRate;
    }

    public static void adjustScreenHeight(View view, int widthMeasureSpec, int heightMeasureSpec) {
        if (mAdjustedScreenHeight > 0) {
            return;
        }

        if (view == null) {
            return;
        }

        Context context = view.getContext();
        if (!(context instanceof Activity)) {
            return;
        }

        Window window = ((Activity)context).getWindow();
        if (window == null) {
            return;
        }

        View decorView = window.getDecorView();
        if (decorView == null || decorView != view.getParent()) {
            return;
        }

        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        if (width >= mScreenHeight && width != mAdjustedScreenHeight) {      // 横屏
            Log.e(TAG, "AdjustedScreenHeight=" + width);
            mAdjustedScreenHeight = width;
            resetConfig();
            return;
        }

        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        if (height >= mScreenHeight && height != mAdjustedScreenHeight) {    // 竖屏
            Log.e(TAG, "AdjustedScreenHeight=" + height);
            mAdjustedScreenHeight = height;
            resetConfig();
        }
    }

    private static void readMetaData(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                Object o = applicationInfo.metaData.get(KEY_DESIGN_WIDTH);
                try {
                    mDesignWidth = Integer.valueOf(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                o = applicationInfo.metaData.get(KEY_DESIGN_HEIGHT);
                try {
                    mDesignHeight = Integer.valueOf(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                o = applicationInfo.metaData.get(KEY_DESIGN_STATUS_BAR_HEIGHT);
                try {
                    mDesignStatusBarHeight = Integer.valueOf(o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mDesignPpi <= 0) {
                    o = applicationInfo.metaData.get(KEY_DESIGN_PPI);
                    try {
                        mDesignPpi = Double.valueOf(o.toString()).floatValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        Log.e(TAG, String.format("DesignWidth=%d,DesignHeight=%d,DesignStatusBarHeight=%d,DesignPpi=%f",
                mDesignWidth, mDesignHeight, mDesignStatusBarHeight, mDesignPpi));
    }

    private static void resetConfig() {
        float screenHeight = getScreenHeight();
        float designHeight = getDesignHeight();

        mWidthRate = mScreenWidth / (float) mDesignWidth;
        mHeightRate = screenHeight / designHeight;
        mDefaultRate = Math.min(mWidthRate, mHeightRate);
        if (mScreenPpi > 0 && mDesignPpi > 0) {
            mPhysicalRate = mScreenPpi / mDesignPpi;
        } else {
            mPhysicalRate = mWidthRate;
        }
        Log.e(TAG, String.format("RealScreenHeight=%f, WidthRate=%f, HeightRate=%f, DefaultRate=%f, PhysicalRate=%f",
                screenHeight, mWidthRate, mHeightRate, mDefaultRate, mPhysicalRate));
    }

    private static int getDesignHeight() {
        return mDesignHeight - mDesignStatusBarHeight;
    }

    private static int getScreenHeight() {
        int screenHeight = mAdjustedScreenHeight > 0 ? mAdjustedScreenHeight : mScreenHeight;
        return (mDesignStatusBarHeight > 0) ? (screenHeight - mStatusBarHeight) : screenHeight;
    }
}
