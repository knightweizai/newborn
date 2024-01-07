package com.zaozhuang.newborn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

//import com.ctb.opencar.R;
//import com.ctb.opencar.listener.TabClickListener;
//import com.ctb.opencar.view.text.ColorTextView;

//import mangogo.appbase.autolayout.AutoUtils;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.listener.TabClickListener;
import com.zaozhuang.newborn.view.text.ColorTextView;

import mangogo.appbase.autolayout.AutoUtils;
import mangogo.appbase.util.ViewUtils;


public class PagerSlidingTabStrip extends HorizontalScrollView {

    TabClickListener mTabClickListener;
    private int mEmptyPosition=-1;

    public void setTabClickListener(TabClickListener tabClickListener) {
        mTabClickListener = tabClickListener;
    }

    public void setEmptyPosition(int emptyPosition) {
        mEmptyPosition = emptyPosition;
    }

    public static abstract class OnPageSelectedListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

    }

    public interface TabViewProvider {
        View getTabView(int position);
    }

    private LinearLayout.LayoutParams mDefaultTabLayoutParams;
    private LinearLayout.LayoutParams mExpandedTabLayoutParams;
    private LinearLayout.LayoutParams mDividerTabLayoutParams;

    private final PageListener mPageListener = new PageListener();
    public OnPageChangeListener mDelegatePageListener;

    public LinearLayout mTabsContainer;
    private ViewPager mViewPager;

    private Paint mRectPaint;
    private int mTabCount;

    private int mIndicatorWidth = 0;
    private int mIndicatorHeight = 0;
    private int mIndicatorPadding = 65536;
    private int mIndicatorMarginBottom = 0;
    private int mIndicatorColor = 0;
    private Drawable mIndicatorDrawable = null;

    private int mUnderlineHeight = 0;
    private int mUnderlineColor = 0;

    private int mDividerColor = 0;
    private int mDividerWidth = 0;
    private int mDividerHeight = 0;

    private boolean mShouldExpand = false;
    private boolean mTextColorGradient = false;

    private int mPaddingLeft = -1;
    private int mPaddingRight = -1;

    private int mTabPadding = 0;
    private float mTabTextSize = 0;
    private float mTabTextSelectSize = 0;
    private int mTabTextColor = 0;
    private int mTabSelectedTextColor = 0;
    private int mTabTextStyle = Typeface.NORMAL;
    private int mTabSelectedTextStyle = Typeface.NORMAL;

    private int mScrollOffset = 0;
    private int mLastScrollX = 0;
    private int mCurrentPosition = 0;
    private float mCurrentPositionOffset = 0f;

    private Interpolator mLeftInterpolator = new LinearInterpolator();
    private Interpolator mRightInterpolator = new LinearInterpolator();

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);
        setOverScrollMode(OVER_SCROLL_NEVER);

        mTabsContainer = new LinearLayout(context);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        mTabsContainer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        addView(mTabsContainer);

        // get system attrs (android:textSize and android:textColor)
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);

        mShouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, mShouldExpand);
        mScrollOffset = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsScrollOffset, mScrollOffset);

        mPaddingLeft = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsPaddingLeft, mPaddingLeft);
        mPaddingRight = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsPaddingRight, mPaddingRight);
        mTabPadding = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsTabPadding, mTabPadding);
        mTabTextSize = AutoUtils.getAutoLayoutSizeFloat(context, a, R.styleable.PagerSlidingTabStrip_pstsTabTextSize, mTabTextSize);
        mTabTextSelectSize = AutoUtils.getAutoLayoutSizeFloat(context, a, R.styleable.PagerSlidingTabStrip_pstsTabSelectedTextSize, mTabTextSelectSize);
        mTabTextColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTabTextColor, mTabTextColor);
        mTabTextStyle = a.getInt(R.styleable.PagerSlidingTabStrip_pstsTabTextStyle, mTabTextStyle);
        mTabSelectedTextStyle = a.getInt(R.styleable.PagerSlidingTabStrip_pstsTabSelectedTextStyle, mTabSelectedTextStyle);
        mTabSelectedTextColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTabSelectedTextColor, mTabSelectedTextColor);
        mTextColorGradient = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTabTextColorGradient, mTextColorGradient);
        int tabWidth = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsTabWidth, LayoutParams.WRAP_CONTENT);
        int tabHeight = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsTabHeight, LayoutParams.MATCH_PARENT);

        mDividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, mDividerColor);
        mDividerWidth = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsDividerWidth, mDividerWidth);
        mDividerHeight = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsDividerHeight, mDividerHeight);

        mIndicatorWidth = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsIndicatorWidth, mIndicatorWidth);
        mIndicatorPadding = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsIndicatorPadding, mIndicatorPadding);
        mIndicatorHeight = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, mIndicatorHeight);
        mIndicatorMarginBottom = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsIndicatorMarginBottom, mIndicatorMarginBottom);
        mIndicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, mIndicatorColor);
        mIndicatorDrawable = a.getDrawable(R.styleable.PagerSlidingTabStrip_pstsIndicatorDrawable);

        mUnderlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, mUnderlineColor);
        mUnderlineHeight = AutoUtils.getAutoLayoutSize(context, a, R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, mUnderlineHeight);

        a.recycle();

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Style.FILL);

        mDefaultTabLayoutParams = new LinearLayout.LayoutParams(tabWidth, tabHeight);
        mExpandedTabLayoutParams = new LinearLayout.LayoutParams(0, tabHeight, 1.0f);
        if (mTabTextSelectSize==0){
            mTabTextSelectSize = mTabTextSize;
        }
        if (hasDivider()) {
            mDividerTabLayoutParams = new LinearLayout.LayoutParams(mDividerWidth, mDividerHeight);
        }
    }

    public void setViewPager(ViewPager pager) {
        mViewPager = pager;
        pager.setOnPageChangeListener(mPageListener);
        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mDelegatePageListener = listener;
    }

    public View getTabView(int index) {
        return mTabsContainer.getChildAt(hasDivider() ? index * 2 : index);
    }

    public void setLeftInterpolator(Interpolator interpolator) {
        if (interpolator != null) {
            mLeftInterpolator = interpolator;
        }
    }

    public void setRightInterpolator(Interpolator interpolator) {
        if (interpolator != null) {
            mRightInterpolator = interpolator;
        }
    }

    public void setSelectedTextStyle(int tabSelectedTextStyle) {
        mTabSelectedTextStyle = tabSelectedTextStyle;
    }

    public void notifyDataSetChanged() {
        if(mViewPager==null) return;
        mTabsContainer.removeAllViews();


        PagerAdapter pagerAdapter = mViewPager.getAdapter();
        if (pagerAdapter == null) {
            mTabCount = 0;
            return;
        }

        mTabCount = pagerAdapter.getCount();
        if (pagerAdapter instanceof TabViewProvider) {
            for (int i = 0; i < mTabCount; i++) {
                addTabView(i, ((TabViewProvider) pagerAdapter).getTabView(i));
            }
        } else {
            for (int i = 0; i < mTabCount; i++) {
                addTextTabView(i, pagerAdapter.getPageTitle(i));
            }
            updateTabStyles();
        }

        if (mTabCount > 0) {
            getViewTreeObserver().removeOnGlobalLayoutListener(mGlobalLayoutListener);
            getViewTreeObserver().addOnGlobalLayoutListener(mGlobalLayoutListener);
        }
    }

    private OnGlobalLayoutListener mGlobalLayoutListener = new OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
            mCurrentPosition = mViewPager.getCurrentItem();
            scrollToChild(mCurrentPosition, 0);
        }
    };

    private void addTextTabView(final int position, CharSequence title) {
        ColorTextView tabView = new ColorTextView(getContext());
        tabView.setTextSize(mTabTextSize);
        tabView.setTextColor(mTabTextColor, mTabSelectedTextColor);
        tabView.setText(title);
        tabView.refresh();

        tabView.setPadding(mTabPadding, 0, mTabPadding, 0);
        addTabView(position, tabView);
    }

    public void setTabSelectedTextColor(int selectedTextColor) {
        mTabSelectedTextColor = selectedTextColor;
        if (mViewPager.getAdapter() instanceof TabViewProvider) {
            return;
        }

        int currentPosition = mViewPager.getCurrentItem();
        for (int i = 0; i < mTabCount; i++) {
            ColorTextView tabView = (ColorTextView) getTabView(i);
            tabView.setTextColor(mTabSelectedTextColor, mTabSelectedTextColor);
            tabView.refresh();
        }
    }

    private void addTabView(final int position, View tabView) {
        tabView.setFocusable(true);
        tabView.setOnClickListener(v -> {
            int currentPosition = mViewPager.getCurrentItem();
            boolean smoothScroll = (currentPosition >= 0 && position >= 0 && Math.abs(currentPosition - position) <= 1);
            if (mTabClickListener != null) {
                mTabClickListener.onClick(position);
            }
            if (position != mEmptyPosition) {
                mViewPager.setCurrentItem(position, smoothScroll);
            }
        });

        if (mPaddingLeft >= 0 && position == 0) {
            ViewUtils.setViewPaddingLeft(tabView, mPaddingLeft);
        }

        if (mPaddingRight >= 0 && position == mTabCount - 1) {
            ViewUtils.setViewPaddingRight(tabView, mPaddingRight);
        }

        LinearLayout.LayoutParams lp = mShouldExpand ? mExpandedTabLayoutParams : mDefaultTabLayoutParams;
        if (hasDivider()) {
            if (position > 0) {
                int pos = position * 2 - 1;
                mTabsContainer.addView(makeDividerView(), pos, mDividerTabLayoutParams);
            }
            mTabsContainer.addView(tabView, position * 2, lp);
        } else {
            mTabsContainer.addView(tabView, position, lp);
        }
    }

    private void scrollToChild(int position, int offset) {
        if (mTabCount == 0) {
            return;
        }

        int newScrollX = Math.max(getTabView(position).getLeft() + offset - mScrollOffset, 0);
        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }

    private void updateTabStyles() {
        if (mViewPager.getAdapter() instanceof TabViewProvider) {
            return;
        }

        int currentPosition = mViewPager.getCurrentItem();
        for (int i = 0; i < mTabCount; i++) {
            ColorTextView tabView = (ColorTextView) getTabView(i);
            if (i == currentPosition) {
                setTabStyle(tabView, 1);
                tabView.setTextStyle(mTabSelectedTextStyle);
                tabView.setTextSize(mTabTextSelectSize);
            } else {
                setTabStyle(tabView, 0);
                tabView.setTextStyle(mTabTextStyle);
                tabView.setTextSize(mTabTextSize);
            }
            tabView.refresh();
        }
    }

    private void updateTabStyle(int position, float positionOffset) {
        if (mViewPager.getAdapter() instanceof TabViewProvider) {
            return;
        }

        if (positionOffset == 0) {
            return;
        }

        View leftView = getTabView(position);
        View rightView = getTabView(position + 1);
        if (leftView instanceof ColorTextView && rightView instanceof ColorTextView) {
            ColorTextView leftTabView = (ColorTextView) leftView;
            ColorTextView rightTabView = (ColorTextView) rightView;

            leftTabView.setDirection(ColorTextView.DIRECTION_RIGHT);
            rightTabView.setDirection(ColorTextView.DIRECTION_LEFT);

            setTabStyle(leftTabView, 1 - positionOffset);
            setTabStyle(rightTabView, positionOffset);

            leftTabView.refresh();
            rightTabView.refresh();
        }
    }

    private void setTabStyle(ColorTextView tabTextView, float progress) {
        tabTextView.setProgress(progress);
    }

    private boolean hasDivider() {
        return mDividerWidth > 0 && mDividerHeight > 0;
    }

    private View makeDividerView() {
        View view = new View(mTabsContainer.getContext());
        view.setBackgroundColor(mDividerColor);
        return view;
    }

    private boolean isLastTab(int index) {
        return mTabCount == 0 || index >= mTabCount - 1;
    }

    private float getLineLeft(View tabView) {
        float lineLeft;
        if (mIndicatorWidth > 0) {
            int width = (tabView.getWidth() - tabView.getPaddingLeft() - tabView.getPaddingRight());
            lineLeft = tabView.getLeft() + tabView.getPaddingLeft() + (width - mIndicatorWidth) / 2.0f;
        } else {
            lineLeft = tabView.getLeft() + tabView.getPaddingLeft() - mIndicatorPadding;
        }
        return Math.max(lineLeft, tabView.getLeft());
    }

    private float getLineRight(View tabView) {
        float lineRight;
        if (mIndicatorWidth > 0) {
            int width = tabView.getWidth() - tabView.getPaddingLeft() - tabView.getPaddingRight();
            lineRight = tabView.getLeft() + tabView.getPaddingLeft() + (width + mIndicatorWidth) / 2.0f;
        } else {
            lineRight = tabView.getRight() - tabView.getPaddingRight() + mIndicatorPadding;
        }
        return Math.min(lineRight, tabView.getRight());
    }

    private void drawIndicator(Canvas canvas) {
        if (mIndicatorHeight <= 0) {
            return;
        }

        View currentTabView = getTabView(mCurrentPosition);
        float left = getLineLeft(currentTabView);
        float right = getLineRight(currentTabView);

        if (mCurrentPositionOffset > 0f && !isLastTab(mCurrentPosition)) {
            View nextTabView = getTabView(mCurrentPosition + 1);
            float nextLeft = getLineLeft(nextTabView);
            float nextRight = getLineRight(nextTabView);

            float rangLeft = (nextLeft - left);
            left = left + rangLeft * mLeftInterpolator.getInterpolation(mCurrentPositionOffset);

            float rangRight = (nextRight - right);
            right = right + rangRight * mRightInterpolator.getInterpolation(mCurrentPositionOffset);
        }

        int bottom = getHeight() - mIndicatorMarginBottom;
        int top = bottom - mIndicatorHeight;
        if (mIndicatorDrawable != null) {
            mIndicatorDrawable.setBounds(Math.round(left), top, Math.round(right), bottom);
            mIndicatorDrawable.draw(canvas);
        } else {
            mRectPaint.setColor(mIndicatorColor);
            canvas.drawRect(left, top, right, bottom, mRectPaint);
        }
    }

    public void setIndicatorDrawable(Drawable indicatorDrawable) {
        mIndicatorDrawable = indicatorDrawable;
        invalidate();
    }

    private void drawUnderline(Canvas canvas) {
        if (mUnderlineHeight > 0) {
            int bottom = getHeight();
            int top = bottom - mUnderlineHeight;
            mRectPaint.setColor(mUnderlineColor);
            canvas.drawRect(0, top, mTabsContainer.getWidth(), bottom, mRectPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || mTabCount == 0) {
            return;
        }

        drawIndicator(canvas);
        drawUnderline(canvas);
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mCurrentPosition = position;
            mCurrentPositionOffset = positionOffset;

            if (mTabCount == 0) {
                return;
            }

            scrollToChild(position, (int) (positionOffset * getTabView(position).getWidth()));
            if (mTextColorGradient) {
                if (mCurrentPosition == mViewPager.getCurrentItem() && positionOffset < 1.0E-5f) {
                    updateTabStyles();
                } else {
                    updateTabStyle(position, positionOffset);
                }
            }

            if (mIndicatorHeight > 0 || mUnderlineHeight > 0) {
                invalidate();
            }

            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (!mTextColorGradient) {
                updateTabStyles();
            }
            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageSelected(position);
            }
        }
    }
}
