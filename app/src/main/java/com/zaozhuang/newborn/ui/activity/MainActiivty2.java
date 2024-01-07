package com.zaozhuang.newborn.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.huantansheng.easyphotos.EasyPhotos;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.event.BabyRefreshEvent;
import com.zaozhuang.newborn.event.BabySelEvent;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.ui.base.BaseActivity;
import com.zaozhuang.newborn.ui.base.TabFragmentHelper;
import com.zaozhuang.newborn.ui.base.fragment.IChildFragment;
import com.zaozhuang.newborn.ui.fragment.main.FragmentHomeV1;
import com.zaozhuang.newborn.ui.fragment.main.FragmentMe;

import mangogo.appbase.eventbus.Event;
import mangogo.appbase.eventbus.ThreadType;
import mangogo.appbase.util.LogUtil;
import mangogo.appbase.util.ScreenUtils;
import mangogo.appbase.viewmapping.ViewMapping;

public class MainActiivty2 extends BaseActivity
        implements TabFragmentHelper.TabListener {

    View mHomeLayout;
    View mMeLayout;
    private TabFragmentHelper mTabFragmentHelper;

    private FragmentHomeV1 mFragmentHome;
    //    private FragmentEntertainment mFragmentEntertainment;
    private FragmentMe mFragmentMe;

    int mSelectedColor;
    int mUnselectedColor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mHomeLayout = findViewById(R.id.menu_bar_home_layout);
        mMeLayout = findViewById(R.id.menu_bar_me_layout);
        mSelectedColor = getResources().getColor(R.color.bottom_bar_text_color);
        mUnselectedColor = getResources().getColor(R.color.bottom_bar_un_text_color);

        initTabFragmentHelper();

        EasyPhotos.preLoad(this);


    }

    @Override
    protected void initListener() {
        mHomeLayout.setOnClickListener(v -> {
            ScreenUtils.setStatusBarFontColor(this, true);
            ((IChildFragment) mFragmentHome).onSelected();
            mTabFragmentHelper.switchFragment(mHomeLayout);
        });

        mMeLayout.setOnClickListener(v -> {
            ScreenUtils.setStatusBarFontColor(this, true);
            ((IChildFragment) mFragmentMe).onSelected();
            mTabFragmentHelper.switchFragment(mMeLayout);
        });
    }


    /**
     * TabFragmentHelperinitTabFragmentHelper();
     */
    private void initTabFragmentHelper() {
        mTabFragmentHelper = new TabFragmentHelper(getSupportFragmentManager(), R.id.main_frame_layout, this);
//        mTabFragmentHelper.putTab(mMapLayout, (mFragmentMap = FragmentMap.newInstance()));
        mTabFragmentHelper.putTab(mHomeLayout, (mFragmentHome = FragmentHomeV1.newInstance()));
        mTabFragmentHelper.putTab(mMeLayout, (mFragmentMe = FragmentMe.newInstance()));
        mTabFragmentHelper.switchFragment(mHomeLayout);

//        setTabTitle(mMapLayout, getString(R.string.main_tab_map));
        setTabTitle(mHomeLayout, getString(R.string.main_tab_home));
        setTabTitle(mMeLayout, getString(R.string.main_tab_me));
    }


    private void setTabTitle(View tabView, String title) {
        TextView titleText = tabView.findViewById(R.id.menu_bar_title_text);
        titleText.setText(title);
    }

    @Override
    protected String getTag() {
        return null;
    }

    @Override
    protected void clear() {

    }

    @Override
    protected boolean isFullScreen() {
        return false;
    }

    @Event(runOn = ThreadType.MAIN)
    void onBabySelLogic(BabySelEvent event) {
        setAttr();
    }


    @Event(runOn = ThreadType.MAIN)
    void onBabyLogic(BabyRefreshEvent event) {
        setAttr();
    }

    private void setAttr() {
        if (BabyManager.isBoy()) {
            setTabView(mMeLayout, mSelectedColor, R.mipmap.main_menu_bar_me_icon_selected_boy, true, 1);
        } else {
            setTabView(mMeLayout, mSelectedColor, R.mipmap.main_menu_bar_me_icon_selected_girl, true, 1);
        }
    }

    @Override
    public void onTabSelected(View tabView) {
        if (tabView == mHomeLayout) {
            if (BabyManager.isBoy()) {
                setTabView(tabView, mSelectedColor, R.mipmap.main_menu_bar_home_icon_selected_boy, true, 0);
            } else {
                setTabView(tabView, mSelectedColor, R.mipmap.main_menu_bar_home_icon_selected_girl, true, 0);
            }
        } else {
            if (BabyManager.isBoy()) {
                setTabView(tabView, mSelectedColor, R.mipmap.main_menu_bar_me_icon_selected_boy, true, 1);
            } else {
                setTabView(tabView, mSelectedColor, R.mipmap.main_menu_bar_me_icon_selected_girl, true, 1);
            }
        }
    }

    @Override
    public void onTabUnselected(View tabView) {
        if (tabView == mHomeLayout) {
            setTabView(tabView, mUnselectedColor, R.mipmap.main_menu_bar_home_icon_unselected, false, 0);
        } else {
            setTabView(tabView, mUnselectedColor, R.mipmap.main_menu_bar_me_icon_unselected, false, 1);
        }
    }

    private void setTabView(View tabView, int textColor, int resId, boolean isSelect, int position) {
        View iconView = tabView.findViewById(R.id.menu_bar_icon);
        iconView.setBackgroundResource(resId);

        TextView titleText = tabView.findViewById(R.id.menu_bar_title_text);
        titleText.setTextColor(textColor);

        if (isSelect) {
            titleText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            if (BabyManager.isBoy()) {
                titleText.setTextColor(getResources().getColor(R.color.primary_color_boy));
            }else {
                titleText.setTextColor(getResources().getColor(R.color.primary_color_girl));
            }

//            if (position == 0) {
//                //首页选中的时候， 修改iconview的宽高
//                iconView.getLayoutParams().width = AutoUtils.getValue(98);
//                iconView.getLayoutParams().height = AutoUtils.getValue(69);
//            } else {
            iconView.getLayoutParams().width = 69;
            iconView.getLayoutParams().height = 69;
//            }
        } else {
            titleText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            titleText.setTextColor(getResources().getColor(R.color.main_tab_nor));

            iconView.getLayoutParams().width = 69;
            iconView.getLayoutParams().height = 69;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("jinyan","main oncreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("jinyan","main onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i("jinyan","main onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("jinyan","main onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("jinyan","main onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("jinyan","main onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("jinyan","main onDestroy");
    }
}
