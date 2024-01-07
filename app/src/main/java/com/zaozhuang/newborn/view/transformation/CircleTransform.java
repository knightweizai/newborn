package com.zaozhuang.newborn.view.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;


public class CircleTransform extends BitmapTransformation implements ITransformation{

    private int mViewWidth;
    private int mViewHeight;

    private final String ID = "com. bumptech.glide.transformations.FillSpace";
    private final byte[] ID_ByTES= ID.getBytes(CHARSET);

    public CircleTransform(Context context, int viewSize) {
        mViewWidth = viewSize;
        mViewHeight = viewSize;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return TransformationUtils.centerCrop(pool, toTransform, getOutWidth(outWidth), getOutHeight(outHeight), this);
    }

    @Override
    public void transform(Canvas canvas, RectF dstRect, Paint paint) {
        canvas.drawOval(dstRect, paint);
    }

    private int getOutWidth(int outWidth) {
        return mViewWidth != 0 ? mViewWidth :outWidth;
    }

    private int getOutHeight(int outHeight) {
        return mViewHeight != 0 ? mViewHeight : outHeight;
    }


    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(),
                Util.hashCode(mViewWidth));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest){
        messageDigest.update(ID_ByTES);
        byte[] radiusData = ByteBuffer.allocate(4).putInt((int) mViewWidth).array();messageDigest.update(radiusData);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof CircleTransform){
            CircleTransform other = (CircleTransform) o;
            return mViewWidth == other.mViewWidth;
        }
        return false;
    }
}
