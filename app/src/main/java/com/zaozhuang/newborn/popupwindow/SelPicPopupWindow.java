package com.zaozhuang.newborn.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.lxj.xpopup.impl.BottomListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zaozhuang.newborn.util.GlideHelper.GlideEngine;

import java.io.File;
import java.util.ArrayList;

import mangogo.appbase.util.ToastUtil;

public class SelPicPopupWindow extends BottomListPopupView implements OnSelectListener {

    private Activity activity;

    /**
     * @param context
     * @param bindLayoutId     layoutId 要求layoutId中必须有一个id为recyclerView的RecyclerView，如果你需要显示标题，则必须有一个id为tv_title的TextView
     * @param bindItemLayoutId itemLayoutId 条目的布局id，要求布局中有id为iv_image的ImageView（非必须），和id为tv_text的TextView
     */
    public SelPicPopupWindow(@NonNull Context context, int bindLayoutId, int bindItemLayoutId, OnSelectResultListener onSelectResultListener) {
        super(context, bindLayoutId, bindItemLayoutId);
        this.activity = (Activity) context;
        this.onSelectResultListener = onSelectResultListener;
        setOnSelectListener(this);

        setStringData("", new String[]{"拍照", "相册", "取消"}, null);


    }

    @Override
    public void onSelect(int position, String text) {
        switch (position) {
            case 0:
                //拍照
                EasyPhotos.createCamera(activity, false)
                        .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                //获取file,进行对应操作
                                File file = new File(photos.get(0).path);
                                Log.e("jinyan", "camera pic path: " + photos.get(0).path);
                                if (onSelectResultListener != null) {
                                    onSelectResultListener.onSelect(photos.get(0).path);
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });

                break;
            case 1:
                //相册
                EasyPhotos.createAlbum(activity, false,false, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮,是否使用宽高数据（false时宽高数据为0，扫描速度更快），[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                        .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                                //获取file,进行对应操作
                                File file = new File(photos.get(0).path);
                                Log.e("jinyan", "album pic path: " + photos.get(0).path);
                                if (onSelectResultListener != null) {
                                    onSelectResultListener.onSelect(photos.get(0).path);
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                break;
            case 2:
                //取消
                dismiss();
                break;
        }
    }

    OnSelectResultListener onSelectResultListener;

    public interface OnSelectResultListener {

        void onSelect(String path);
    }
}
