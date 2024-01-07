package com.zaozhuang.newborn.ui.base;


import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

//import com.ctb.opencar.R;
//import com.ctb.opencar.util.ClickUtils;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.util.ClickUtils;

import mangogo.appbase.util.NetStatusUtils;
import mangogo.appbase.util.ToastUtil;

class LoadingView {

    private View mNormalView;
    private View mLoadingView;
    private View mFailedView;
    private View mNoDataView;
    private View mNoNetView;
    private ILoadingView mLoadingItemView;
    private Handler mHandler;

    LoadingView(Handler handler, View view, View.OnClickListener failedListener) {
        mHandler = handler;
        mNormalView = view.findViewById(R.id.normal_view);
        mLoadingView = view.findViewById(R.id.loading_view);
        mFailedView = view.findViewById(R.id.failed_view);
        mNoDataView = view.findViewById(R.id.no_data_view);
        mNoNetView = view.findViewById(R.id.no_net_view);
        if (mLoadingView != null) {
            mLoadingView.setOnClickListener(v -> {
            });
            View v = mLoadingView.findViewById(R.id.loading_item_view);
            if (v instanceof ILoadingView) {
                mLoadingItemView = (ILoadingView) v;
            }
        }
        if (mFailedView != null) {
            mFailedView.setOnClickListener(v -> {
            });
            if (failedListener != null) {
                View v = mFailedView.findViewById(R.id.failed_click_view);
                if (v != null) {
                    v.setOnClickListener(v1 -> {
                        if (v1!=null && ClickUtils.check(v1)) {
                            if (NetStatusUtils.isConnected(v1.getContext())) {
                                failedListener.onClick(v1);
                            } else {
                                ToastUtil.showMessage("网络未连接，请检查网络设置后重试！");
                            }
                        }
                    });
                }
            }
        }
        if (mNoDataView != null) {
            mNoDataView.setOnClickListener(v -> {
            });
        }

        if (mNoNetView != null) {
            mNoNetView.setOnClickListener(v -> {
            });
//            if (failedListener != null) {
//                View v = mNoNetView.findViewById(R.id.no_net_image);
//                if (v != null) {
//                    v.setOnClickListener(v1 -> {
//                        if (ClickUtils.check(v1)) {
//                            if (NetStatusUtils.isConnected(v1.getContext())) {
//                                failedListener.onClick(v1);
//                            } else {
//                                ToastUtil.showMessage("网络未连接，请检查网络设置后重试！");
//                            }
//                        }
//                    });
//                }
//            }
        }
    }

    void setBackgroundColor(int color) {
        if (mLoadingView != null) {
            if (color == Color.TRANSPARENT) {
                mLoadingView.setBackground(null);
            } else {
                mLoadingView.setBackgroundColor(color);
            }
        }
        if (mFailedView != null) {
            if (color == Color.TRANSPARENT) {
                mFailedView.setBackground(null);
            } else {
                mFailedView.setBackgroundColor(color);
            }
        }
        if (mNoDataView != null) {
            if (color == Color.TRANSPARENT) {
                mNoDataView.setBackground(null);
            } else {
                mNoDataView.setBackgroundColor(color);
            }
        }
        if (mNoNetView != null) {
            if (color == Color.TRANSPARENT) {
                mNoNetView.setBackground(null);
            } else {
                mNoNetView.setBackgroundColor(color);
            }
        }
    }

    void setNoDataTitle(String noDataStr, View.OnClickListener listener) {
    }

    boolean isShowingNormal() {
        return !isShowingLoading() && !isShowingFailed() && !isShowingNoData();
    }

    boolean isShowingLoading() {
        return mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE;
    }

    boolean isShowingFailed() {
        return mFailedView != null && mFailedView.getVisibility() == View.VISIBLE;
    }

    boolean isShowingNoData() {
        return mNoDataView != null && mNoDataView.getVisibility() == View.VISIBLE;
    }

    boolean isShowingNoNet() {
        return mNoNetView != null && mNoNetView.getVisibility() == View.VISIBLE;
    }

    void showLoading(int type) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            mHandler.post(() -> showLoadingPrivate(type));
        } else {
            showLoadingPrivate(type);
        }
    }

    private void showLoadingPrivate(int type) {
        if (mNormalView != null) {
            mNormalView.setVisibility(type == 0 ? View.VISIBLE : View.INVISIBLE);
        }
        if (mLoadingView != null) {
            if (mLoadingItemView != null) {
                if (type == 1) {
                    mLoadingItemView.start();
                    mLoadingView.setVisibility(View.VISIBLE);
                } else {
                    mLoadingView.setVisibility(View.INVISIBLE);
                    mLoadingItemView.stop();
                }
            } else {
                mLoadingView.setVisibility(type == 1 ? View.VISIBLE : View.INVISIBLE);
            }
        }
        if (mFailedView != null) {
            mFailedView.setVisibility(type == 2 ? View.VISIBLE : View.INVISIBLE);
        }
        if (mNoDataView != null) {
            mNoDataView.setVisibility(type == 3 ? View.VISIBLE : View.INVISIBLE);
        }
        if (mNoNetView != null) {
            mNoNetView.setVisibility(type == 4 ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
