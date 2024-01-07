package com.zaozhuang.newborn.popupwindow;

import static com.zaozhuang.newborn.config.Const.GSON;
import static com.zaozhuang.newborn.config.Const.JUMPER;
import static mangogo.appbase.Globals.BUS;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.impl.BottomListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.zaozhuang.newborn.db.dao.BabyDao;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.event.BabyRefreshEvent;
import com.zaozhuang.newborn.event.BabySelEvent;
import com.zaozhuang.newborn.manage.BabyManager;

import java.util.List;

public class SelBabyPopupWindow extends BottomListPopupView implements OnSelectListener {

    private Activity activity;
    private Baby baby;

    /**
     * @param context
     * @param bindLayoutId     layoutId 要求layoutId中必须有一个id为recyclerView的RecyclerView，如果你需要显示标题，则必须有一个id为tv_title的TextView
     * @param bindItemLayoutId itemLayoutId 条目的布局id，要求布局中有id为iv_image的ImageView（非必须），和id为tv_text的TextView
     */
    public SelBabyPopupWindow(@NonNull Context context, int bindLayoutId, int bindItemLayoutId, Baby baby) {
        super(context, bindLayoutId, bindItemLayoutId);
        this.activity = (Activity) context;
        this.baby = baby;
//        this.onSelectResultListener = onSelectResultListener;
        setOnSelectListener(this);

        setStringData("", new String[]{"选择", "修改", "删除", "取消"}, null);


    }

    @Override
    public void onSelect(int position, String text) {
        switch (position) {
            case 0:
                BabyManager.saveBabyInfo(baby);
                BUS.post(new BabySelEvent());
                break;
            case 1:
                JUMPER.babyAdd(GSON.toJson(baby)).startActivity(activity);
                break;
            case 2:
                BabyDao.getInstance(activity).delete(baby.id);
                List<Baby> list = BabyDao.getInstance(activity).getBabyList();
                if (!list.isEmpty()) {
                    BabyManager.saveBabyInfo(list.get(0));
                }else {
                    BabyManager.resetInit();
                }
                BUS.post(new BabyRefreshEvent());
                BUS.post(new BabySelEvent());
                break;
            case 3:
                //取消
                break;
        }
        dismiss();
    }

//    OnSelectResultListener onSelectResultListener;
//
//    public interface OnSelectResultListener {
//
//        void onSelect(String path);
//    }
}
