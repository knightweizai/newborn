package com.zaozhuang.newborn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

//import com.blankj.utilcode.util.Utils;
//import com.ctb.opencar.config.Const;
//import com.ctb.opencar.config.StorageSchema;
//import com.ctb.opencar.db.DaoManager;
//import com.ctb.opencar.dialog.CommonDialog;
//import com.ctb.opencar.manage.ConfigManager;
//import com.ctb.opencar.manage.DirectionManager;
//import com.ctb.opencar.manage.HistoryManager;
//import com.ctb.opencar.manage.MapConfigManager;
//import com.ctb.opencar.manage.PrizeManager;
//import com.ctb.opencar.manage.SoundPoolManager;
//import com.ctb.opencar.manage.TimeManager;
//import com.ctb.opencar.manage.UpgradeManager;
//import com.ctb.opencar.manage.UserManager;
//import com.ctb.opencar.manage.VoiceManager;
//import com.ctb.opencar.ui.base.BaseActivity;
//import com.ctb.opencar.util.AddressUtils;
//import com.ctb.opencar.util.CrashHandler;
//import com.ctb.opencar.util.FileUtils;
//import com.ctb.opencar.util.LocationUtils;
//import com.ctb.opencar.util.RegisterHx;
//import com.ctb.opencar.util.ThreadUtils;
//import com.ctb.opencar.util.TypefaceUtils;
//import com.ctb.opencar.util.download.DownLoadHelper;
//import com.ctb.opencar.util.thrid.QQUtils;
//import com.ctb.opencar.util.thrid.WXUtils;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.google.gson.Gson;
//import com.meituan.android.walle.WalleChannelReader;
//import com.opensource.svgaplayer.SVGAParser;
//import com.squareup.leakcanary.RefWatcher;
//import com.tencent.bugly.Bugly;
//import com.tencent.bugly.beta.Beta;
//import com.tencent.bugly.beta.interfaces.BetaPatchListener;
//import com.umeng.analytics.MobclickAgent;
//import com.umeng.commonsdk.UMConfigure;
//import com.zlw.main.recorderlib.RecordManager;

import com.github.gzuliyujiang.dialog.DialogColor;
import com.github.gzuliyujiang.dialog.DialogConfig;
import com.github.gzuliyujiang.dialog.DialogStyle;
import com.google.gson.Gson;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.zaozhuang.newborn.config.Const;
import com.zaozhuang.newborn.config.StorageSchema;
import com.zaozhuang.newborn.dialog.CommonDialog;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.manage.ConfigManager;
import com.zaozhuang.newborn.manage.UserManager;
import com.zaozhuang.newborn.ui.base.BaseActivity;
import com.zaozhuang.newborn.util.ThreadUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

//import cn.jpush.android.api.JPushInterface;
import mangogo.appbase.BaseApplication;
//import mangogo.appbase.Config;
//import mangogo.appbase.autolayout.AutoLayout;
//import mangogo.appbase.autolayout.AutoUtils;
//import mangogo.appbase.autolayout.core.AutoLayoutConfig;
import mangogo.appbase.autolayout.AutoLayout;
import mangogo.appbase.util.AppUtils;
import mangogo.appbase.util.DeviceUtils;
import mangogo.appbase.util.LogUtil;
import mangogo.appbase.util.OsUtils;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
//import me.jessyan.autosize.AutoSizeConfig;
//import me.jessyan.autosize.onAdaptListener;
//import me.jessyan.autosize.unit.Subunits;
//import me.jessyan.autosize.utils.LogUtils;


public class GlobalApplication extends BaseApplication {

    public static String CHANNEL_NAME;
    final static List<Activity> ACTIVITY_LIST = new ArrayList<>();
//    private static RefWatcher mRefWatcher = null;

    public static boolean sAppLocation = false; //app内第一次定位
    public static double sLat = 0;
    public static double sLng = 0;

    public static String sYangPsd = null;

//    public GlobalApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
//        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
//    }


//    public static void watchRef(Object watchedReference) {
//        if (mRefWatcher != null) {
//            mRefWatcher.watch(watchedReference);
//        }
//    }

//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    @Override
//    public void onBaseContextAttached(Context base) {
//        super.onBaseContextAttached(base);
//        // you must install multiDex whatever tinker is installed!
//        //todo wei MultiDex。。。
////        MultiDex.install(base);
//
//        // 安装tinker
//        Beta.installTinker(this);
//    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(ActivityLifecycleCallbacks callbacks) {
        getGlobalContext().registerActivityLifecycleCallbacks(callbacks);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();

//        CHANNEL_NAME = getChannelName();
//        TypefaceUtils.init(getGlobalContext());
//        initLayoutInflater();
//        AppConfig.init();
        AutoLayout.init(getGlobalContext());
        ConfigManager.init();
//        Config.isAgreePrivacy = ConfigManager.getAgree();
        ThreadUtils.init();
        UserManager.init();
        BabyManager.init();

        registerActivityLifecycleCallback(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });


//        PlayerFactory.setPlayManager(Exo2PlayerManager.class);
//        CacheFactory.setCacheManager(ExoPlayerCacheManager.class);


//        VideoOptionModel videoOptionModel =
//                new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1);
//        List<VideoOptionModel> list = new ArrayList<>();
//        list.add(videoOptionModel);
//        VideoOptionModel videoOptionModel2 = new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_timeout", -1);
//        list.add(videoOptionModel2);
//        GSYVideoManager.instance().setOptionModelList(list);





//        if(Config.isAgreePrivacy){
            initApplication();
//        }

//        DialogConfig.setDialogStyle(DialogStyle.Default);
//        DialogConfig.setDialogColor(new DialogColor()
//                .cancelTextColor(0xFF999999)
//                .okTextColor(0xFF0099CC));

    }

    @Override
    public void initApplication() {

        try {
//            String processName = AppUtils.getProcessName();
            String processName = AppUtils.getCurrentProcessNameByFile();
            if (processName.contains(":")) {
                return;
            }
//            AddressUtils.init();
//            if (Config.isAgreePrivacy) {
//                UpgradeManager.startUp();
//            }
//            CrashHandler.init(getGlobalContext());
            CommonDialog.init();
            //数据库管理
//            DaoManager.init(getGlobalContext());

//            initRecordManager();
//            initThirdParty();
//            initSvga();
//            initJPush();


//            HistoryManager.init();
//            VoiceManager.init();
//            MapConfigManager.init();
//            SoundPoolManager.init(getGlobalContext());
//        UuidManager.init();
//        MessageManager.init();
//        PlayerLibrary.init(this);
//            TimeManager.init();
//            IntegralManager.init();
//            UpLoadIntegralManager.init();
//            DirectionManager.init();
//            PrizeManager.init();
            /**
             * 关闭严苛模式
             if (BuildConfig.DEBUG) {
             StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
             StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
             }
             */

        } catch (Exception e) {
            LogUtil.e("GlobalApplication", "exception = " + e.getMessage());
        }
    }

//    private void initLog(){
////        LogUtils.setDebug(true);
//        LogUtils.setDebug(BuildConfig.DEBUG);
//    }

    //初始化推送服务
//    private void initJPush() {
//        super.onCreate();
//        JPushInterface.setDebugMode(BuildConfig.DEBUG);
//        JPushInterface.init(getGlobalContext());
//        LogUtil.e("JPush", "pushId:"+JPushInterface.getRegistrationID(getGlobalContext()));
//    }

//    private void initSvga() throws IOException {
//        //初始化svga动画缓存
//        File cacheDir = new File(getGlobalContext().getCacheDir(), Const.GIFT_SVGA_PATH);
//        HttpResponseCache.install(cacheDir, 1024 * 1024 * 128);
//        LogUtil.e("GlobalApplication", "初始化svga动画缓存");
////      SVGACache.INSTANCE.onCreate(getGlobalContext(), SVGACache.Type.FILE);
//        SVGAParser.Companion.shareParser().init(getGlobalContext());
//
//        Fresco.initialize(getGlobalContext());
//    }

//    private void initRecordManager() {
//        RecordManager.getInstance().init(getGlobalContext(), BuildConfig.DEBUG);
//        RecordManager.getInstance().changeRecordDir(FileUtils.getRecordDir());
//    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        // 在这里可以断开连接
        super.onTerminate();
    }

    @Override
    public Gson getGson() {
        return Const.GSON;
    }

    @Override
    public boolean isPrintLog() {
//        return BuildConfig.DEBUG;
        return true;
    }

    /**
     * activity list
     */
    public void exit() {
        finishAllActivities();
    }

    public static void addActivity(final Activity activity) {
        removeInvalidActivity();
        ACTIVITY_LIST.add(activity);
    }

    public static void removeInvalidActivity() {
        for (int i = ACTIVITY_LIST.size() - 1; i >= 0; i--) {
            Activity activity = getActivity(i);
            if (activity instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) activity;
                if (baseActivity.isActivityDestroyed()) {
                    ACTIVITY_LIST.remove(i);
                }
            }
        }
    }


    public static void removeActivity(final Activity activity) {
        ACTIVITY_LIST.remove(activity);
    }

    public static Activity getLatestActivity() {
        return getActivity(ACTIVITY_LIST.size() - 1);
    }


    public static Activity getActivity(int index) {
        if (index >= 0 && index < ACTIVITY_LIST.size()) {
            return ACTIVITY_LIST.get(index);
        }
        return null;
    }

    public static int getActivitiesSize() {
        return ACTIVITY_LIST.size();
    }

    public static boolean hasActivities() {
        return !ACTIVITY_LIST.isEmpty();
    }

    public static void finishAllActivities() {
        try {
            for (int i = ACTIVITY_LIST.size() - 1; i >= 0; i--) {
                Activity activity = getActivity(i);
                if (activity instanceof BaseActivity) {
                    if (((BaseActivity) activity).isAlive()) {
                        activity.finish();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ACTIVITY_LIST.clear();
    }

    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    public static boolean isOpenActivity(Class<?> cls) {
        if (ACTIVITY_LIST.size() > 0) {
            for (int i = 0, size = ACTIVITY_LIST.size(); i < size; i++) {
                if (cls == ACTIVITY_LIST.get(i).getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Activity getActivity(Class<?> cls) {
        if (ACTIVITY_LIST.size() > 0) {
            for (int i = 0, size = ACTIVITY_LIST.size(); i < size; i++) {
                if (cls == ACTIVITY_LIST.get(i).getClass()) {
                    return ACTIVITY_LIST.get(i);
                }
            }
        }
        return getLatestActivity();
    }

    public static void finishActivity(Class<?> cls) {
        for (int i = ACTIVITY_LIST.size() - 1; i >= 0; i--) {
            Activity activity = getActivity(i);
            if (activity instanceof BaseActivity) {
                if (((BaseActivity) activity).isAlive() && activity.getClass().equals(cls)) {
                    activity.finish();
                }
            }
        }
    }


    public static List<Activity> getActivityList() {
        return ACTIVITY_LIST;
    }

    /**
     *
     */
    private static boolean mBoldText = false;
    private static float mTextSize = 0;

//    private void initLayoutInflater() {
//        AutoLayout.init(getGlobalContext());
//        LayoutInflater.from(getGlobalContext()).setFactory(GlobalApplication::onMyCreateView);
//
//        int designWidth = AutoLayoutConfig.getDesignWidth();
//        int screenWidth = AutoLayoutConfig.getScreenWidth();
//        float screenPpi = AutoLayoutConfig.getScreenPpi();
//        float ppi = screenPpi * designWidth / screenWidth;
//        mBoldText = !OsUtils.isEmui() && (ppi > 0 && ppi <= 423);
//        mTextSize = AutoUtils.getValueBaseWidth(mBoldText ? 49f : 51f);
//        AutoSizeConfig.getInstance().setBaseOnWidth(true).setDesignWidthInDp(360)
//                //屏幕适配监听器
//                .setOnAdaptListener(new onAdaptListener() {
//                    @Override
//                    public void onAdaptBefore(Object target, Activity activity) {
//                        AutoSizeConfig.getInstance().setScreenWidth(AutoLayoutConfig.getScreenWidth());
//                    }
//
//                    @Override
//                    public void onAdaptAfter(Object target, Activity activity) {
//                    }
//                });
//        AutoSizeConfig.getInstance().getUnitsManager()
//                .setSupportDP(false)
//                .setSupportSP(false)
//                .setSupportSubunits(Subunits.PT)
//                .setDesignSize(1080, 1920);
//    }

//    public static View onMyCreateView(String name, Context context, AttributeSet attrs) {
//        View view = TypefaceUtils.onCreateView(name, context, attrs);
//        return (view != null) ? view : AutoLayout.onCreateView(name, context, attrs);
//    }

    public static boolean isBoldText() {
        return mBoldText;
    }

    public static float getTextSize() {
        return mTextSize;
    }

    /**
     * 第三方
     */
//    private void initThirdParty() {
//        if (BuildConfig.DEBUG) {
//            mRefWatcher = LeakCanary.install(getGlobalContext());
//        }
//        initCrashReport();
//        initUMConfigure();
//
//        if (Config.isAgreePrivacy) {
//            Utils.init(getApplication());
//            RegisterHx.initHX();
//            // 微信
//            WXUtils.init(getGlobalContext());
//            // QQ
//            QQUtils.init(getGlobalContext());
//            LocationUtils.init();
//        }
//        //
//        DownLoadHelper.init();
//    }

//    public static void initCrashReport() {
//        if (Config.isAgreePrivacy) {
//            // 腾讯bugly
//            // 设置是否开启热更新能力，默认为true
//            Beta.tipsDialogLayoutId = R.layout.hot_repair_dialog;
//            Beta.enableHotfix = true;
//            // 设置是否自动下载补丁
//            Beta.canAutoDownloadPatch = true;
//            // 设置是否提示用户重启
//            Beta.canNotifyUserRestart = true;
//            // 设置是否自动合成补丁
//            Beta.canAutoPatch = true;
//
//            /**
//             * 补丁回调接口，可以监听补丁接收、下载、合成的回调
//             */
//            Beta.betaPatchListener = new BetaPatchListener() {
//                @Override
//                public void onPatchReceived(String patchFileUrl) {
//                }
//
//                @Override
//                public void onDownloadReceived(long savedLength, long totalLength) {
//                }
//
//                @Override
//                public void onDownloadSuccess(String patchFilePath) {
//                    Beta.applyDownloadedPatch();
//                }
//
//                @Override
//                public void onDownloadFailure(String msg) {
//                }
//
//                @Override
//                public void onApplySuccess(String msg) {
//                }
//
//                @Override
//                public void onApplyFailure(String msg) {
//                }
//
//                @Override
//                public void onPatchRollback() {
//                }
//            };
//            Bugly.setAppChannel(getGlobalContext(), CHANNEL_NAME);
//            Bugly.init(getGlobalContext(), "15a96dbd65", BuildConfig.DEBUG);
//        }
//    }

//    public static void initUMConfigurePre() {
//        if (!Config.isAgreePrivacy) {
//            UMConfigure.preInit(getGlobalContext(), Const.UM_KEY, CHANNEL_NAME);
//        }
//    }

//    public static void initUMConfigure() {
//        if (Config.isAgreePrivacy) {
//            //初始化友盟
//            Runnable runnable = () -> {
//                UMConfigure.init(getGlobalContext(), Const.UM_KEY, CHANNEL_NAME, UMConfigure.DEVICE_TYPE_PHONE, "");
//                MobclickAgent.onProfileSignIn(DeviceUtils.getDeviceId());
//                MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
//            };
//            ThreadPoolExecutor threadPoolExecutor = ThreadUtils.getThreadPoolExecutor();
//            threadPoolExecutor.execute(runnable);
//        }
//
//    }

//    private String getChannelName() {
//        String channelName = StorageSchema.CHANNEL_NAME.get();
//        if (TextUtils.isEmpty(channelName)) {
//            String channel = WalleChannelReader.getChannel(getGlobalContext());
//            if (TextUtils.isEmpty(channelName) && TextUtils.isEmpty(channel)) {
//                return "Dandelion";
//            }
//            channelName = channel.trim();
//            StorageSchema.CHANNEL_NAME.save(channelName);
//        } else {
//            return channelName;
//        }
//        return channelName;
//    }

    /**
     * 初始化极光推送的别名
     */
//    public void initJgAlias() {
//        //在jpush上设置别名,第一个参数就是applicationcontext，第二个随意，别重复就行，第三个就是你的别名
//        JPushInterface.setAlias(getGlobalContext(), 57, "testAlias");
//    }

}
