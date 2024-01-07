package com.zaozhuang.newborn.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
//
//import com.bumptech.glide.Glide;
//import com.ctb.opencar.GlobalApplication;
//import com.ctb.opencar.R;
//import com.ctb.opencar.config.Const;
//import com.ctb.opencar.manage.UpgradeManager;
//import com.ctb.opencar.manage.UserManager;
//import com.ctb.opencar.util.ActivityHook;
//import com.ctb.opencar.util.MyLogs;
//import com.ctb.opencar.util.TypefaceUtils;
//import com.ctb.opencar.util.chatroom.ActivityUtils;
//import com.tendcloud.tenddata.TCAgent;
//import com.umeng.analytics.MobclickAgent;

import com.bumptech.glide.Glide;
import com.zaozhuang.newborn.GlobalApplication;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.config.Const;
import com.zaozhuang.newborn.manage.UserManager;
import com.zaozhuang.newborn.util.ActivityUtils;
import com.zaozhuang.newborn.util.MyLogs;

import java.lang.ref.WeakReference;

//import butterknife.ButterKnife;
//import io.reactivex.disposables.Disposable;
//import mangogo.appbase.net.IContext;
//import butterknife.ButterKnife;
import mangogo.appbase.net.IContext;
import mangogo.appbase.util.ScreenUtils;
import mangogo.appbase.viewmapping.ViewMapUtil;
//import mangogo.appbase.viewmapping.ViewMapUtil;
//import me.jessyan.autosize.AutoSizeCompat;


public abstract class BaseActivity extends FragmentActivity implements Const, IContext {
    public static final String TAG = "jinyan";

    protected Context mContext;
//    protected Disposable mDisposable = null;
    private ActivityHandler mActivityHandler;
    private boolean mActivityDestroyed = true;
    private View mStatusBarView;
    private boolean mResumed = false;
//    protected String TAG = this.getClass().getCanonicalName();


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mActivityDestroyed = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        ActivityHook.hookOrientation(this);//hook，绕过检查
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT == 26) {
//            ActivityHook.convertActivityFromTranslucent(this);
//        }
        mActivityDestroyed = false;
        mContext = this;
        mActivityHandler = new ActivityHandler(this);
        BUS.register(this);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        if (isFullScreen()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

//        View view = ViewMapUtil.inflate(this, getLayoutInflater(), null);
        View view = ViewMapUtil.inflate(getLayoutId(), getLayoutInflater(), null);
        View contentView = fitsStatusBar(view);
        setContentView(contentView);
        ActivityUtils.addActivity(this);
//        ButterKnife.bind(this, contentView);

        ((GlobalApplication) GlobalApplication.getGlobalContext()).addActivity(this);
        initLoadingView(view);
        initView();
        initListener();
        initData();
        MyLogs.d("BaseActivity",getClass().getSimpleName());
    }


    @Override
    protected void onResume() {
        super.onResume();
        mResumed = true;
        if (!UserManager.isLogin()) {
            return;
        }
        if (getTag() != null) {
//            MobclickAgent.onPageStart(getTag());
//            TCAgent.onPageStart(this, getTag());
        }
//        MobclickAgent.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
        if (UserManager.isLogin()) {
//            MobclickAgent.onPause(this);
            if (getTag() != null) {
//                MobclickAgent.onPageEnd(getTag());
//                TCAgent.onPageEnd(this, getTag());
            }
        }
        //https://blog.csdn.net/z1074971432/article/details/10517449
        if (isFinishing()) {
            doDestroy();
        }
    }

//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        View view = GlobalApplication.onMyCreateView(name, context, attrs);
//        return (view != null) ? view : super.onCreateView(name, context, attrs);
//    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        hideSoftInput();
        super.startActivityForResult(intent, requestCode, options);
        setActivityAnimation(true);
    }

    @Override
    public void finish() {
        if (isAlive()) {
            hideSoftInput();
            super.finish();
        }
    }

    @Override
    protected void onDestroy() {
        doDestroy();
        super.onDestroy();
        ActivityUtils.removeActivity(this);
    }

    private void doDestroy() {
        if (!mActivityDestroyed) {
            BUS.unregister(this);
            ((GlobalApplication) GlobalApplication.getGlobalContext()).removeActivity(this);
            Glide.get(this).clearMemory();
//            clearSubscription();
            clear();
            if (isShowingLoading()) {
                showNormal();
            }
            getHandler().removeCallbacksAndMessages(null);
            mActivityDestroyed = true;
        }
    }


    public boolean hasResumed() {
        return mResumed;
    }

    public boolean isActivityDestroyed() {
        return mActivityDestroyed;
    }

    public boolean isActivityAlive() {
        return !isActivityDestroyed();
    }

    @Override
    public boolean isAlive() {
        return isActivityAlive();
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
        // activity销毁时不保存fragment和view树信息,新建时直接创建新的fragment和view树
//        super.onSaveInstanceState(outState);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == UpgradeManager.MANAGE_UNKNOWN_APP_RESULT_CODE) {
//            UpgradeManager.onActivityResult(requestCode, resultCode, data);
//        }
//    }

    /**
     * 设置activity启动动画和退出动画
     */
    protected void setActivityAnimation(boolean startActivity) {
        if (startActivity) {
            overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
        } else {
            overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected void initData() {
    }

    protected void refreshData() {
    }

    protected void netContent(boolean hasContent) {
    }

    protected  String getTag(){
        return "";
    }

    protected  void clear(){

    }


//    protected void clearSubscription() {
//        if (mDisposable != null) {
//            if (!mDisposable.isDisposed()) {
//                mDisposable.dispose();
//            }
//            mDisposable = null;
//        }
//    }

    /**
     * 窗口是否全屏
     */
    protected  boolean isFullScreen(){
        return false;
    }

    protected boolean needSetStatusBarColor() {
        return false;
    }

    /**
     * 返回需要设置的StatusBar的Color值
     */
    protected int getStatusBarColor() {
        return getResources().getColor(R.color.default_status_bar_color);
    }

    protected int getBackgroundDrawableResource() {
        return R.color.default_background_color;
    }

    protected boolean isBlackStatusBarFontColor() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    public void setStatusBarColor(int color) {
        if (mStatusBarView != null) {
            mStatusBarView.setBackgroundColor(color);
        }
    }

    protected boolean isUseWechatLightNavigationBar() {
        return true;
    }


    private View fitsStatusBar(View contentView) {
        mStatusBarView = null;
        int backgroundResource = getBackgroundDrawableResource();
        if (backgroundResource != 0) {
            getWindow().setBackgroundDrawableResource(backgroundResource);
        }

        if (isFullScreen()
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return contentView;
        }

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            if (needSetStatusBarColor()) {
                mStatusBarView = new View(this);
                mStatusBarView.setBackgroundColor(getStatusBarColor());

                FrameLayout frameLayout = new FrameLayout(this);
                frameLayout.addView(
                        contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                frameLayout.addView(mStatusBarView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getStatusBarHeight(this));
                return frameLayout;
            }
        }
        ScreenUtils.setStatusBarFontColor(this, isBlackStatusBarFontColor());
        return contentView;
    }


    ////////////////////////////loading view 相关///////////////////////////////////////////////////////////////
    private LoadingView mLoadingView;

    private void initLoadingView(View view) {
        mLoadingView = new LoadingView(getHandler(), view, v -> refreshData());
        showNormal();
    }

    protected void initNoDataView(String noDataStr, boolean clickable) {
        if (clickable) {
            mLoadingView.setNoDataTitle(noDataStr, v -> refreshData());
        } else {
            mLoadingView.setNoDataTitle(noDataStr, null);
        }
    }

    /**
     * 显示原有视图
     */
    public void showNormal() {
        if (isAlive()) {
            mLoadingView.showLoading(0);
        }
    }

    /**
     * 显示加载中
     */
    public void showLoading() {
        if (isAlive()) {
            mLoadingView.showLoading(1);
        }
    }

    /**
     * 显示加载失败
     */
    public void showFailed() {
        if (isAlive()) {
            mLoadingView.showLoading(2);
        }
    }

    /**
     * 显示无内容
     */
    public void showNoData() {
        if (isAlive()) {
            mLoadingView.showLoading(3);
        }
    }

    /**
     * 显示无网络
     */
    public void showNoNet() {
        if (isAlive()) {
            mLoadingView.showLoading(4);
        }
    }

    public boolean isShowingNormal() {
        return mLoadingView.isShowingNormal();
    }

    public boolean isShowingLoading() {
        return mLoadingView.isShowingLoading();
    }

    public boolean isShowingFailed() {
        return mLoadingView.isShowingFailed();
    }

    public boolean isShowingNoData() {
        return mLoadingView.isShowingNoData();
    }

    public boolean isShowingNoNet() {
        return mLoadingView.isShowingNoNet();
    }

    ///////  网络相关 ///////

    //自定义广播类
    public class NetBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mobNetInfo != null && wifiNetInfo != null && !mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                //WIFI和移动网络均未连接
                netContent(false);

            } else {
                //WIFI连接或者移动网络连接
                netContent(true);
            }

        }
    }

    ////////////////////////////Handler 相关///////////////////////////////////////////////////////////////

    /**
     * 获取handler
     */
    protected Handler getHandler() {
        return mActivityHandler;
    }

    protected void handleMessage(Message msg) {
    }

    protected static class ActivityHandler extends Handler {

        private WeakReference<BaseActivity> mActivityWeakReference;

        ActivityHandler(BaseActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void dispatchMessage(Message msg) {
            BaseActivity activity = mActivityWeakReference.get();
            if (activity != null && activity.isActivityAlive()) {
                super.dispatchMessage(msg);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = mActivityWeakReference.get();
            if (activity != null && activity.isActivityAlive()) {
                activity.handleMessage(msg);
            }
        }
    }


    protected void hideKeyboard(EditText editText) {
        if (isSHowKeyboard(this, editText)) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    private boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
        } else {
            return false;
        }
    }

    protected void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 采用此方式处理适配异常的情况， 后续看反馈情况
     *
     * @return
     */
//    @Override
//    public Resources getResources() {
//        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
//        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());//如果没有自定义需求用这个方法
//        // AutoSizeCompat.autoConvertDensity(super.getResources(), 667, false);//如果有自定义需求就用这个方法
//        return super.getResources();
//    }
//
//    public void setTitle(TextView tv, String title) {
////        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/titlebar.otf"));
////        tv.setText(title);
//        TypefaceUtils.setTitleTypeface(this, tv, title);
//    }

}
