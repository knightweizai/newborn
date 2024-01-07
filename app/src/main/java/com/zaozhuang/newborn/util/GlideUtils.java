package com.zaozhuang.newborn.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.ui.base.BaseActivity;
import com.zaozhuang.newborn.view.transformation.CircleTransform;
import com.zaozhuang.newborn.view.transformation.CornersTransform;

import java.io.File;
//import com.ctb.opencar.R;
//import com.ctb.opencar.config.OssConfig;
//import com.ctb.opencar.ui.base.BaseActivity;
//import com.ctb.opencar.view.transformation.BlurTransformation;
//import com.ctb.opencar.view.transformation.CircleTransform;
//import com.ctb.opencar.view.transformation.CornersTransform;
//
//import io.agora.rtm.RtmImageMessage;

public class GlideUtils {

    private static boolean checkContext(Context context) {
        if (context instanceof BaseActivity) {
            if (((BaseActivity) context).isActivityDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public enum ImageCompressType {
        _s200, _s260, _s360, _w200, _w260, _w360, _w720, _w1080, _w3600
    }

    /**
     * 后缀说明
     * 1. _s{变量}   等比缩放 按最短边缩放  变量单位px
     * 2. _w{变量}  等比缩放 按宽度缩放
     * <p>
     * 后缀定义
     * - _s200     ≈手机宽度1/4，oss参数 image/resize,m_mfit,w_200,h_200/quality,q_90/format,webp
     * - _s260     ≈手机宽度1/3，oss参数 image/resize,m_mfit,w_260,h_260/quality,q_90/format,webp
     * - _s360     ≈手机宽度1/2，oss参数 image/resize,m_mfit,w_360,h_360/quality,q_90/format,webp
     * - _w200    ≈手机宽度1/4，oss参数 image/resize,m_lfit,w_200/quality,q_90/format,webp
     * - _w260    ≈手机宽度1/3，oss参数 image/resize,m_lfit,w_260/quality,q_90/format,webp
     * - _w360    ≈手机宽度1/2，oss参数 image/resize,m_lfit,w_360/quality,q_90/format,webp
     * - _w720    ≈手机宽度1，   oss参数 image/resize,m_lfit,w_720/quality,q_90/format,webp
     * - _w1080   高清图，         oss参数 image/resize,m_lfit,w_1080/quality,q_90/format,webp
     * - _w3600   超超高清图，   oss参数 image/resize,m_lfit,w_3600/quality,q_90/format,webp
     *
     * @return
     */
    public static String warpUrl(String url, ImageCompressType compressType) {
        String suffix = "";
        switch (compressType) {
            case _s200:
                suffix = "_s200";
                break;
            case _s260:
                suffix = "_s260";
                break;
            case _s360:
                suffix = "_s360";
                break;
            case _w200:
                suffix = "_w200";
                break;
            case _w260:
                suffix = "_w260";
                break;
            case _w360:
                suffix = "_w360";
                break;
            case _w720:
                suffix = "_w720";
                break;
            case _w1080:
                suffix = "_w1080";
                break;
            case _w3600:
                suffix = "_w3600";
                break;
        }
        return url + suffix;
    }

    public static String warpUrlS200(String url) {
        return warpUrl(url, ImageCompressType._s200);
    }

    public static String warpUrlS360(String url) {
        return warpUrl(url, ImageCompressType._s360);
    }

    public static String warpUrlW1080(String url) {
        return warpUrl(url, ImageCompressType._w1080);
    }


    public static void loadImage(Context context, ImageView imageView, String url, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (errorResId != 0) {
            Drawable drawable = imageView.getContext().getResources().getDrawable(errorResId);
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(imageView.getDrawable())
                    .error(drawable)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(imageView.getDrawable())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(imageView);
        }
    }

    public static void loadImage(Context context, ImageView imageView, int resourceId, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (errorResId != 0) {
            Drawable drawable = imageView.getContext().getResources().getDrawable(errorResId);
            Glide.with(imageView.getContext())
                    .load(resourceId)
                    .placeholder(imageView.getDrawable())
                    .error(drawable)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(resourceId)
                    .placeholder(imageView.getDrawable())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .into(imageView);
        }
    }


    public static void loadImage(Context context, ImageView imageView, String url) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, int url) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .into(imageView);
    }

    public static void loadCentInsideImage(Context context, ImageView imageView, String url) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .into(imageView);
    }

    public static void loadCropImage(Context context, ImageView imageView, String url, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        Drawable drawable = imageView.getContext().getResources().getDrawable(errorResId);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(imageView.getContext())
                .load(url)
                .error(drawable)
                .placeholder(drawable)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .into(imageView);
    }

//    public static void loadBlurImage(Context context, ImageView imageView, String url, int radius, int errorResId) {
//        if (!checkContext(context)) {
//            return;
//        }
//        RequestOptions myOptions = new RequestOptions()
//                .centerCrop()
//                .transform(new BlurTransformation(imageView.getContext(), radius));
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Glide.with(imageView.getContext())
//                .load(url)
//                .placeholder(imageView.getDrawable() == null ? imageView.getResources().getDrawable(errorResId) : imageView.getDrawable())
//                .error(errorResId)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .apply(myOptions)
//                .into(imageView);
//    }


//    public static void loadCircleImage(Context context, ImageView imageView, String url, int imageSize, int errorResId) {
//        try{
//            if (!checkContext(context)) {
//                return;
//            }
//            if (!TextUtils.isEmpty(url) && url.contains(OssConfig.PUBLIC_BASE_URL)) {
//                url = ImageCompression.getCompressionUrl(url, imageSize);
//            }
//            RequestOptions myOptions = new RequestOptions()
//                    .centerCrop()
//                    .transform(new CircleTransform(imageView.getContext(), imageSize));
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(imageView.getContext())
//                    .load(url)
//                    .placeholder(imageView.getDrawable())
//                    .error(errorResId)
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                    .apply(myOptions)
//                    .into(imageView);
//        }catch (Exception e){
//
//        }
//    }
//
//    public static void loadCircleImage(Context context, ImageView imageView, String url, int imageSize, int errorResId,int placeholder) {
//        try{
//            if (!checkContext(context)) {
//                return;
//            }
//            if (!TextUtils.isEmpty(url) && url.contains(OssConfig.PUBLIC_BASE_URL)) {
//                url = ImageCompression.getCompressionUrl(url, imageSize);
//            }
//            RequestOptions myOptions = new RequestOptions()
//                    .centerCrop()
//                    .transform(new CircleTransform(imageView.getContext(), imageSize));
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(imageView.getContext())
//                    .load(url)
//                    .placeholder(placeholder)
//                    .error(errorResId)
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                    .apply(myOptions)
//                    .into(imageView);
//        }catch (Exception e){
//
//        }
//    }

    public static void loadCircleImage(Context context, ImageView imageView, int resourceId, int imageSize, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
//        if (!TextUtils.isEmpty(url) && url.contains(OssConfig.PUBLIC_BASE_URL)) {
//            url = ImageCompression.getCompressionUrl(url, imageSize);
//        }
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new CircleTransform(imageView.getContext(), imageSize));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView.getContext())
                .load(resourceId)
                .placeholder(imageView.getDrawable())
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(myOptions)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, ImageView imageView, File file, int imageSize, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
//        if (!TextUtils.isEmpty(url) && url.contains(OssConfig.PUBLIC_BASE_URL)) {
//            url = ImageCompression.getCompressionUrl(url, imageSize);
//        }
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new CircleTransform(imageView.getContext(), imageSize));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView.getContext())
                .load(file)
                .placeholder(imageView.getDrawable())
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(myOptions)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, ImageView imageView, File file, int placeHolder) {
        Glide.with(context).load(file)
                .placeholder(placeHolder)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    public static void loadCircleImage(Context context, ImageView imageView, File file) {
        Glide.with(context).load(file)
                .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
    }


//    public static void loadCornersImage(Context context, ImageView imageView, String url, int viewWidth, int viewHeight, float roundRadius, int errorResId) {
//        loadCornersImage(context, imageView, url, CornersTransform.CORNER_ALL, viewWidth, viewHeight, roundRadius, errorResId, ImageView.ScaleType.FIT_XY);
//    }


//    public static void loadCornersImage(Context context, ImageView imageView, String url, int cornerType, int viewWidth, int viewHeight, float roundRadius, int errorResId, ImageView.ScaleType scaleType) {
//        if (!checkContext(context)) {
//            return;
//        }
//        if (!TextUtils.isEmpty(url) && url.contains(OssConfig.PUBLIC_BASE_URL)) {
//            url = ImageCompression.getCompressionUrl(url, viewWidth, viewHeight);
//        }
//        RequestOptions myOptions = new RequestOptions()
//                .centerCrop()
//                .transform(new CornersTransform(imageView.getContext(), ImageView.ScaleType.CENTER_CROP, cornerType, viewWidth, viewHeight, roundRadius));
//
//        imageView.setScaleType(scaleType);
//        Glide.with(imageView.getContext())
//                .load(url)
//                .placeholder(R.drawable.default_image_bg)
//                .error(errorResId)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .apply(myOptions)
//                .into(imageView);
//    }

    public static void loadBanner(Context context, ImageView imageView, String url, int errorResId, ImageView.ScaleType scaleType) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(scaleType);
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.default_image_bg)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .into(imageView);
    }

    public static void loadVoiceBanner(Context context, ImageView imageView, String url, int errorResId, ImageView.ScaleType scaleType) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(scaleType);
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .into(imageView);
    }

    public static void loadCornersImage(Context context, ImageView imageView, int resourcesId, int viewWidth, int viewHeight, float roundRadius) {
        if (!checkContext(context)) {
            return;
        }
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new CornersTransform(imageView.getContext(), ImageView.ScaleType.CENTER_CROP, CornersTransform.CORNER_ALL, viewWidth, viewHeight, roundRadius));
        Drawable drawable = imageView.getContext().getResources().getDrawable(resourcesId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView.getContext())
                .load(drawable)
                .error(drawable)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(myOptions)
                .into(imageView);
    }

    public static void loadStaggeredImage(Context context, ImageView imageView, String url, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(imageView.getContext())
                .load(url)
                .error(errorResId)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

    public static void loadImageNol(Context context, ImageView imageView, String url) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void getBitmap(Context context, String url, BitmapImageViewTarget callback) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(callback);
    }

    public static void getBitmap(Context context, String url, SimpleTarget<Bitmap> callback) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(callback);
    }

    public static void getBitmap(Context context, int resourceId, SimpleTarget<Bitmap> callback) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(resourceId)
                .into(callback);
    }

    public static void getCircleBitmap(Context context, String url, SimpleTarget<Bitmap> callback) {
        if (!checkContext(context)) {
            return;
        }
//        if (!TextUtils.isEmpty(url) && url.contains(OssConfig.PUBLIC_BASE_URL)) {
//            url = ImageCompression.getCompressionUrl(url, imageSize);
//        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(callback);
    }


    public static void loadImageNol(Context context, ImageView imageView, String url, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .error(errorResId)
                .into(imageView);
    }

    public static void loadImageNol(Context context, ImageView imageView, int resourceId, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .load(resourceId)
                .error(errorResId)
                .into(imageView);
    }

    public static void loadImageNol(Context context, ImageView imageView, int resourceId, int width, int height, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .load(resourceId)
                .override(width, height)
                .error(errorResId)
                .into(imageView);
    }

    /**
     * 传入大小
     *
     * @param context
     * @param imageView
     * @param url
     * @param errorResId 加载失败展示
     */
//    public static void loadImageNol(Context context, ImageView imageView, String url, int errorResId, int width, int height) {
//        if (!checkContext(context)) {
//            return;
//        }
//        if (!TextUtils.isEmpty(url) && url.contains(OssConfig.PUBLIC_BASE_URL)) {
//            url = ImageCompression.getCompressionUrl(url, width, height);
//        }
//        Glide.with(context)
//                .load(url)
//                .error(errorResId)
//                .into(imageView);
//    }
    public static void loadImageNolDefault(Context context, ImageView imageView, String url, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(imageView.getDrawable())
                .error(errorResId)
                .into(imageView);
    }

//    public static void loadImageNol(Context context, ImageView imageView, RtmImageMessage rtmImageMessage, int errorResId) {
//        if (!checkContext(context)) {
//            return;
//        }
//        Glide.with(imageView.getContext())
//                .load(rtmImageMessage.getThumbnail())
//                .override(rtmImageMessage.getThumbnailWidth(), rtmImageMessage.getThumbnailHeight())
//                .error(errorResId)
//                .into(imageView);
//    }

    public static void loadImageNol(Context context, ImageView imageView, byte[] img, int errorResId) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(img)
                .error(errorResId)
                .into(imageView);
    }

    /**
     * @param url 通过URL得到 Drawable
     */
    public static void getDrawableGlide(Context context, String url, CustomTarget<Drawable> customTarget) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context).load(url).into(customTarget);
    }

    /**
     * @param url 通过URL得到 Drawable
     */
    public static void getDrawableGlide(Context context, String url, int errorResId, CustomTarget<Drawable> customTarget) {
        if (!checkContext(context)) {
            return;
        }
        Glide.with(context).load(url).error(errorResId).
                into(customTarget);
    }

}
