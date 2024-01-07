package com.zaozhuang.newborn.ui.chart;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SizeUtils;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.adapter.SimpleTabAdapter;
import com.zaozhuang.newborn.consts.JumperParam;
import com.zaozhuang.newborn.ui.base.BaseActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 生长曲线
 */
public class ChartActivity extends BaseActivity {

//    private LineChart lineChart;

    View mBackView;
    TextView mTitleView;
    ViewPager mViewPager;
    MagicIndicator magicIndicator;
    private ArrayList<String> mChannelList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    private int tabIndicatorHeight = SizeUtils.dp2px(4f);
    private int tabIndicatorWidth = SizeUtils.dp2px(12f);
    private int tabIndicatorColor;
    private int tabTextSelectColor;
    private int tabTextDefaultColor;

    int chartIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chart;
    }

    @Override
    protected void initView() {
//        lineChart = findViewById(R.id.lineChart);

        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        magicIndicator = findViewById(R.id.magicIndicator);
        mViewPager = findViewById(R.id.view_pager);

        mTitleView.setText("生长曲线");
        initChannel();
        initViewPager();
        initMagicIndicator();

    }


    private void initChannel() {
        tabIndicatorColor = getResources().getColor(R.color.primary_color_girl);
        tabTextSelectColor = getResources().getColor(R.color.color_111111);
        tabTextDefaultColor = getResources().getColor(R.color.color_111111);
        mChannelList.clear();
        mChannelList.add("身长");
        mChannelList.add("体重");
        mChannelList.add("黄疸");

        mFragmentList.clear();
        mFragmentList.add(ChartFragment.newInstance(0));
        mFragmentList.add(ChartFragment.newInstance(1));
        mFragmentList.add(ChartFragment.newInstance(2));

    }

    private void initViewPager() {
//        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
//        mViewPager.setOffscreenPageLimit(3);
//        mViewPager.setAdapter(mPagerAdapter);
//        mPagerSlidingTabStrip.setViewPager(mViewPager);
//        mPagerSlidingTabStrip.setLeftInterpolator(new AccelerateInterpolator(1.2f));
//        mPagerSlidingTabStrip.setRightInterpolator(new DecelerateInterpolator(1.2f));

        SimpleTabAdapter tabAdapter = new SimpleTabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(mFragmentList.get(0), mChannelList.get(0));
        tabAdapter.addFragment(mFragmentList.get(1), mChannelList.get(1));
        tabAdapter.addFragment(mFragmentList.get(2), mChannelList.get(2));
        mViewPager.setAdapter(tabAdapter);
    }


    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mChannelList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(15f);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                textView.setText(mChannelList.get(index));
                commonPagerTitleView.setContentView(textView);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        textView.setTextColor(tabTextSelectColor);
                        textView.setTextSize(20f);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        textView.setTextColor(tabTextDefaultColor);
                        textView.setTextSize(15f);

                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                commonPagerTitleView.setOnClickListener(v -> {
                    mViewPager.setCurrentItem(index);
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);

                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(tabIndicatorHeight);
                indicator.setLineWidth(tabIndicatorWidth);
                indicator.setRoundRadius(tabIndicatorHeight);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(tabIndicatorColor);
                return indicator;
            }
        });

        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        chartIndex = getIntent().getIntExtra(JumperParam.CHART_INDEX, 0);
        mViewPager.setCurrentItem(chartIndex);
    }

    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());
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
}
