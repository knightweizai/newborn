package com.zaozhuang.newborn.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.ctb.opencar.GlobalApplication;
//import com.ctb.opencar.config.Const;
//import com.ctb.opencar.manage.UserManager;
//import com.tendcloud.tenddata.TCAgent;
//import com.umeng.analytics.MobclickAgent;

import com.zaozhuang.newborn.GlobalApplication;
import com.zaozhuang.newborn.config.Const;
import com.zaozhuang.newborn.manage.UserManager;

import java.lang.ref.WeakReference;

//import butterknife.ButterKnife;
//import io.reactivex.disposables.Disposable;
import mangogo.appbase.net.IContext;
import mangogo.appbase.viewmapping.ViewMapUtil;


public abstract class BaseFragment extends Fragment implements Const, IContext {

    protected Context mContext;
//    protected Disposable mDisposable = null;
    private FragmentHandler mFragmentHandler;
    private boolean mFragmentDestroyed = true;
    private View view;

    public BaseFragment() {
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentHandler = new FragmentHandler(this);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentDestroyed = false;
        BUS.register(this);
//        View view = ViewMapUtil.inflate(this, inflater, container);
        view = ViewMapUtil.inflate(getLayoutId(), inflater, container);
//        ButterKnife.bind(this, view);
        initLoadingView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!UserManager.isLogin()) {
            return;
        }
            if (getFragmentTag() != null) {
//            MobclickAgent.onPageStart(getFragmentTag());
//            TCAgent.onPageStart(mContext, getFragmentTag());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!UserManager.isLogin()) {
            return;
        }
        if (getFragmentTag() != null) {
//            MobclickAgent.onPageEnd(getFragmentTag());
//            TCAgent.onPageEnd(mContext, getFragmentTag());
        }
    }

    @Override
    public void onDestroyView() {
        BUS.unregister(this);
        mFragmentDestroyed = true;
        clear();
//        clearSubscription();
        getHandler().removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        GlobalApplication.watchRef(this);
    }

    public boolean isFragmentDestroyed() {
        return mFragmentDestroyed;
    }

    public boolean isFragmentAlive() {
        return !isFragmentDestroyed();
    }

    @Override
    public boolean isAlive() {
        return isFragmentAlive();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initListener();

    protected void initData() {
    }

    protected void refreshData() {
    }

    protected abstract String getFragmentTag();

    protected abstract void clear();

//    public void clearSubscription() {
//        if (mDisposable != null) {
//            if (!mDisposable.isDisposed()) {
//                mDisposable.dispose();
//            }
//            mDisposable = null;
//        }
//    }

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

    protected void setLoadingBackgroundColor(int color) {
        mLoadingView.setBackgroundColor(color);
    }

    /**
     * 显示原有视图
     */
    protected void showNormal() {
        if (isAlive()) {
            mLoadingView.showLoading(0);
        }
    }

    /**
     * 显示加载中
     */
    protected void showLoading() {
        if (isAlive()) {
            mLoadingView.showLoading(1);
        }
    }

    /**
     * 显示加载失败
     */
    protected void showFailed() {
        if (isAlive()) {
            mLoadingView.showLoading(2);
        }
    }

    /**
     * 显示无内容
     */
    protected void showNoData() {
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

    public boolean isShowingNoNet() { return mLoadingView.isShowingNoNet(); }

    protected boolean isShowingNormal() {
        return mLoadingView.isShowingNormal();
    }

    protected boolean isShowingLoading() {
        return mLoadingView.isShowingLoading();
    }

    protected boolean isShowingFailed() {
        return mLoadingView.isShowingFailed();
    }

    protected boolean isShowingNoData() {
        return mLoadingView.isShowingNoData();
    }

    ////////////////////////////Handler 相关///////////////////////////////////////////////////////////////

    /**
     * 获取handler
     */
    protected Handler getHandler() {
        return mFragmentHandler;
    }

    protected void handleMessage(Message msg) {
    }

    protected static class FragmentHandler extends Handler {

        private WeakReference<BaseFragment> mFragmentWeakReference;

        FragmentHandler(BaseFragment fragment) {
            mFragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void dispatchMessage(Message msg) {
            BaseFragment fragment = mFragmentWeakReference.get();
            if (fragment != null && fragment.isFragmentAlive()) {
                super.dispatchMessage(msg);
            }
        }

        @Override
        public void handleMessage(Message msg) {
            BaseFragment fragment = mFragmentWeakReference.get();
            if (fragment != null && fragment.isFragmentAlive()) {
                fragment.handleMessage(msg);
            }
        }
    }
}
