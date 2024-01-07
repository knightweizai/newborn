package com.zaozhuang.newborn.view.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

class TransformationUtils {

    static Bitmap fitCenter(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight, ITransformation transformation) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.FILL);

        Bitmap target = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        if (target == null) {
            target = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        }
        target.eraseColor(Color.TRANSPARENT);

        Rect srcRect = new Rect(0, 0, toTransform.getWidth(), toTransform.getHeight());
        RectF dstRect = new RectF(0, 0, outWidth, outHeight);
        calcFitCenterRect(srcRect, dstRect);

        Canvas canvas = new Canvas(target);
        transformation.transform(canvas, dstRect, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawBitmap(toTransform, srcRect, dstRect, paint);
        return target;
    }

    private static void calcFitCenterRect(Rect srcRect, RectF dstRect) {
        int srcWidth = srcRect.width();
        int srcHeight = srcRect.height();
        float dstWidth = dstRect.width();
        float dstHeight = dstRect.height();

        if (check(srcWidth, srcHeight, (int)dstWidth, (int)dstHeight)) {
            return;
        }

        if (srcWidth * dstHeight > srcHeight * dstWidth) {
            float height = Math.round(srcHeight * dstWidth / srcWidth);
            dstRect.top = Math.round((dstHeight - height) * 0.5f);
            dstRect.bottom = dstRect.top + height;
        } else {
            float width = Math.round(srcWidth * dstHeight / srcHeight);
            dstRect.left = Math.round((dstWidth - width) * 0.5f);
            dstRect.right = dstRect.left + width;
        }
    }

    static Bitmap centerCrop(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight, ITransformation transformation) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.FILL);

        Bitmap target = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        if (target == null) {
            target = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        }
        target.eraseColor(Color.TRANSPARENT);

        Canvas canvas = new Canvas(target);
        transformation.transform(canvas, new RectF(0, 0, outWidth, outHeight), paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

        Matrix matrix = createCenterCropMatrix(toTransform.getWidth(), toTransform.getHeight(), outWidth, outHeight);
        canvas.drawBitmap(toTransform, matrix, paint);
        return target;
    }

    private static Matrix createCenterCropMatrix(int sourceWidth, int sourceHeight, int destWidth, int destHeight) {
        Matrix matrix = new Matrix();
        if (check(sourceWidth, sourceHeight, destWidth, destHeight)) {
            return matrix;
        }

        float scale;
        float dx = 0, dy = 0;

        if (sourceWidth * destHeight > destWidth * sourceHeight) {
            scale = (float) destHeight / (float) sourceHeight;
            dx = (destWidth - sourceWidth * scale) * 0.5f;
        } else {
            scale = (float) destWidth / (float) sourceWidth;
            dy = (destHeight - sourceHeight * scale) * 0.5f;
        }

        matrix.setScale(scale, scale);
        matrix.postTranslate(Math.round(dx), Math.round(dy));
        return matrix;
    }

    private static boolean check(int sourceWidth, int sourceHeight, int destWidth, int destHeight) {
        if (sourceWidth == 0 || sourceHeight == 0 || destWidth == 0 || destHeight == 0) {
            return true;
        }
        if (sourceWidth == destWidth && sourceHeight == destHeight) {
            return true;
        }
        return false;
    }
}
