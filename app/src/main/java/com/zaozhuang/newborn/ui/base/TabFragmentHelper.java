package com.zaozhuang.newborn.ui.base;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

public class TabFragmentHelper {

    private Map<View, Fragment> mFragments = new HashMap<>();
    private FragmentManager mFragmentManager = null;
    private int mTabFrameLayoutId = -1;

    private View mCurrentTabView = null;
    private TabListener mTabListener = null;

    public TabFragmentHelper(FragmentManager fm, int tabFrameLayoutId, TabListener tabListener) {
        mFragmentManager = fm;
        mTabFrameLayoutId = tabFrameLayoutId;
        mTabListener = tabListener;
    }

    public void setTabListener(TabListener tabListener) {
        mTabListener = tabListener;
    }

    public void putTab(View tabView, Fragment tabFragment) {
        mFragments.put(tabView, tabFragment);
        dispatchTabListener(tabView, null);
    }

    public void putTab(View[] tabViews, Fragment tabFragment) {
        for (View tabView : tabViews) {
            mFragments.put(tabView, tabFragment);
            dispatchTabListener(tabView, null);
        }
    }

    public void setTab(View tabView, Fragment tabFragment) {
        mFragmentManager.beginTransaction()
                .add(mTabFrameLayoutId, tabFragment)
                .hide(tabFragment)
                .commit();
        mFragments.put(tabView, tabFragment);
        dispatchTabListener(tabView, null);
    }

    public void setTab(View[] tabViews, Fragment tabFragment) {
        mFragmentManager.beginTransaction()
                .add(mTabFrameLayoutId, tabFragment)
                .hide(tabFragment)
                .commit();
        for (View tabView : tabViews) {
            mFragments.put(tabView, tabFragment);
            dispatchTabListener(tabView, null);
        }
    }

    public void clear() {
        mFragments.clear();
        mFragmentManager = null;
        mCurrentTabView = null;
        mTabFrameLayoutId = -1;
    }

    public View getCurrentTabView() {
        return mCurrentTabView;
    }

    public Fragment getCurrentFragment() {
        return getFragment(mCurrentTabView);
    }

    public Fragment getFragment(View tabView) {
        return tabView != null ? mFragments.get(tabView) : null;
    }

    public void switchFragment(View tabView) {
        Fragment fragment = getFragment(tabView);
        if (fragment == null || mCurrentTabView == tabView) {
            return;
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment lastFragment = getCurrentFragment();
        if (fragment == lastFragment) {
            View oldTabView = mCurrentTabView;
            mCurrentTabView = tabView;
            dispatchTabListener(oldTabView, tabView);
            return;
        }

        if (lastFragment != null) {
            ft.hide(lastFragment);
        }

        if (!fragment.isAdded()) {
            ft.add(mTabFrameLayoutId, fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();

        View oldTabView = mCurrentTabView;
        mCurrentTabView = tabView;
        dispatchTabListener(oldTabView, tabView);
    }

    private void dispatchTabListener(View oldTabView, View tabView) {
        if (mTabListener != null) {
            if (oldTabView != null) {
                mTabListener.onTabUnselected(oldTabView);
            }
            if (tabView != null) {
                mTabListener.onTabSelected(tabView);
            }
        }
    }

    public interface TabListener {
        void onTabSelected(View tabView);
        void onTabUnselected(View tabView);
    }
}
