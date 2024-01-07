package com.zaozhuang.newborn.view.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;


public class CornersTransform extends BitmapTransformation implements ITransformation {

    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = (CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT);
    public static final int CORNER_TOP = (CORNER_TOP_LEFT | CORNER_TOP_RIGHT);

    private float mRoundRadius;
    private int mViewWidth;
    private int mViewHeight;
    private ImageView.ScaleType mScaleType;
    private int mCornerType = CORNER_ALL;
    private final String ID = "com. bumptech.glide.transformations.FillSpace";
    private final byte[] ID_ByTES= ID.getBytes(CHARSET);

    public CornersTransform(Context context, float roundRadius) {
        this(context, ImageView.ScaleType.CENTER_CROP, 0, 0, roundRadius);
    }

    public CornersTransform(Context context, ImageView.ScaleType scaleType, float roundRadius) {
        this(context, scaleType, 0, 0, roundRadius);
    }

    public CornersTransform(Context context, ImageView.ScaleType scaleType, int viewWidth, int viewHeight, float roundRadius) {
        this(context, scaleType, CORNER_ALL, 0, 0, roundRadius);
    }

    public CornersTransform(Context context, ImageView.ScaleType scaleType, int cornerType, int viewWidth, int viewHeight, float roundRadius) {
        mScaleType = scaleType;
        mCornerType = cornerType;
        mRoundRadius = roundRadius;
        mViewWidth = viewWidth;
        mViewHeight = viewHeight;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if (mScaleType == ImageView.ScaleType.FIT_CENTER) {
            return TransformationUtils.fitCenter(pool, toTransform, getOutWidth(outWidth), getOutHeight(outHeight), this);
        }
        return TransformationUtils.centerCrop(pool, toTransform, getOutWidth(outWidth), getOutHeight(outHeight), this);
    }

    @Override
    public void transform(Canvas canvas, RectF dstRect, Paint paint) {
        canvas.drawRoundRect(dstRect, mRoundRadius, mRoundRadius, paint);

        if ((mCornerType & CORNER_TOP_LEFT) == 0) {
            canvas.drawRect(0, 0, mRoundRadius, mRoundRadius, paint);
        }
        if ((mCornerType & CORNER_TOP_RIGHT) == 0) {
            canvas.drawRect(dstRect.right - mRoundRadius, 0, dstRect.right, mRoundRadius, paint);
        }
        if ((mCornerType & CORNER_BOTTOM_LEFT) == 0) {
            canvas.drawRect(0, dstRect.bottom - mRoundRadius, mRoundRadius, dstRect.bottom, paint);
        }
        if ((mCornerType & CORNER_BOTTOM_RIGHT) == 0) {
            canvas.drawRect(dstRect.right - mRoundRadius, dstRect.bottom - mRoundRadius, dstRect.right, dstRect.bottom, paint);
        }
    }

    private int getOutWidth(int outWidth) {
        return mViewWidth != 0 ? mViewWidth : outWidth;
    }

    private int getOutHeight(int outHeight) {
        return mViewHeight != 0 ? mViewHeight : outHeight;
    }


    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(),
                Util.hashCode(mRoundRadius));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest){
        messageDigest.update(ID_ByTES);
        byte[] radiusData = ByteBuffer.allocate(4).putInt((int) mRoundRadius).array();messageDigest.update(radiusData);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof CornersTransform){
            CornersTransform other = (CornersTransform) o;
            return mRoundRadius == other.mRoundRadius;
        }
        return false;
    }

}
