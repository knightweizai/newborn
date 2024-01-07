package com.zaozhuang.newborn.view.transformation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


public interface ITransformation {
    void transform(Canvas canvas, RectF dstRect, Paint paint);
}
