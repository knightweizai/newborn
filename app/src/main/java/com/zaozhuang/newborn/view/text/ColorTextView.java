package com.zaozhuang.newborn.view.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

import com.zaozhuang.newborn.util.TypefaceUtils;

//import com.ctb.opencar.util.TypefaceUtils;


public class ColorTextView extends View {

    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;

    private int mDirection = DIRECTION_LEFT;

    private String mText = "";
    private Paint mTextPaint;

    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;

    private Rect mTextBound = new Rect();
    private float mTextWidth;
    private float mTextStartX;

    private float mProgress;
    private boolean mChanged = true;

    public ColorTextView(Context context) {
        super(context);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Typeface typeface = TypefaceUtils.getTypeface(mTextPaint, Typeface.NORMAL);
        if (typeface != null) {
            mTextPaint.setTypeface(typeface);
        }
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    public void setTextSize(float size) {
        if (size != mTextPaint.getTextSize()) {
            mTextPaint.setTextSize(size);
            mChanged = true;
        }
    }

    public void setTextStyle(int style) {
        TypefaceUtils.setTextBold(mTextPaint, (style & Typeface.BOLD) != 0);
        mTextPaint.setTextSkewX((style & Typeface.ITALIC) != 0 ? -0.25f : 0);
    }

    public void setTextColor(int color, int changeColor) {
        mTextOriginColor = color;
        mTextChangeColor = changeColor;
    }

    public final void setText(CharSequence text) {
        mText = text.toString();
        mChanged = true;
    }

    public String getText() {
        if (mText == null) {
            return "";
        }
        return mText;
    }

    public void refresh() {
        if (mChanged) {
            measureText();
            if (getParent() != null) {
                requestLayout();
                invalidate();
                mChanged = false;
            }
        } else {
            if (getParent() != null) {
                invalidate();
            }
        }
    }

    public void setProgress(float progress) {
        mProgress = progress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        int realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mTextStartX = (realWidth - mTextWidth) / 2.0f + getPaddingLeft();
    }

    private void measureText() {
        mTextWidth = mTextPaint.measureText(mText);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextBound.height();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result + getPaddingTop() + getPaddingBottom();
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = Math.round(mTextWidth);
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result + getPaddingLeft() + getPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDirection == DIRECTION_LEFT) {
            drawChangeLeft(canvas);
            drawOriginLeft(canvas);
        } else {
            drawOriginRight(canvas);
            drawChangeRight(canvas);
        }
    }

    private void drawChangeRight(Canvas canvas) {
        drawText(canvas, mTextChangeColor, mTextStartX + (1 - mProgress) * mTextWidth, mTextStartX + mTextWidth);
    }

    private void drawOriginRight(Canvas canvas) {
        drawText(canvas, mTextOriginColor, mTextStartX, mTextStartX + (1 - mProgress) * mTextWidth);
    }

    private void drawChangeLeft(Canvas canvas) {
        drawText(canvas, mTextChangeColor, mTextStartX, mTextStartX + mProgress * mTextWidth);
    }

    private void drawOriginLeft(Canvas canvas) {
        drawText(canvas, mTextOriginColor, mTextStartX + mProgress * mTextWidth, mTextStartX + mTextWidth);
    }

    private void drawText(Canvas canvas, int color, float startX, float endX) {
        mTextPaint.setColor(color);
        canvas.save();
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(mText, mTextStartX, baseline, mTextPaint);
        canvas.restore();
    }
}
