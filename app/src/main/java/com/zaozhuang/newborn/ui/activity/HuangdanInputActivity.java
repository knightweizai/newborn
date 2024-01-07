package com.zaozhuang.newborn.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.adapter.HeightInputAdapter;
import com.zaozhuang.newborn.chart.ChartConst;
import com.zaozhuang.newborn.data.InputEntity;
import com.zaozhuang.newborn.db.dao.BabyDao;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import mangogo.appbase.util.ToastUtil;

public class HuangdanInputActivity extends BaseActivity {

    View mBackView;
    TextView mTitleView;
    TextView mRightText;
    private RecyclerView recyclerView;
    private TextView tv_save;
    private HeightInputAdapter adapter;

    private List<InputEntity> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_huangdan_input;
    }

    @Override
    protected void initView() {
        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        mRightText = findViewById(R.id.mine_text);
        recyclerView = findViewById(R.id.rv);
        tv_save = findViewById(R.id.tv_save);

        mTitleView.setText("黄疸值输入");
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText("查看");

        tv_save.setBackground(
                BabyManager.isBoy() ?
                        getResources().getDrawable(R.drawable.bg_btn_main_boy):getResources().getDrawable(R.drawable.bg_btn_main_girl));

    }

    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());

        mRightText.setOnClickListener(v -> {
            JUMPER.chart(2).startActivity(this);
        });

        tv_save.setOnClickListener(v -> {

            for (int i = 0; i < list.size(); i++) {
                String value = list.get(i).value;
                if (TextUtils.isEmpty(value)) {
                    list.get(i).value = "0.0";
                }
            }
            /**
             * 1. 获取是否有添加了一个宝宝
             * 2. 将宝宝数据入库
             */

            List<Baby> babyList = BabyDao.getInstance(this).getBabyList();

            if (babyList.isEmpty()) {
                ToastUtil.showMessage("请先添加宝宝信息哟");
                JUMPER.babyAdd().startActivity(this);
                return;
            }
            String str = GSON.toJson(list);
            BabyManager.setHuangdan(str);

            BabyDao.getInstance(this).update(BabyManager.getBabyInfo());
            JUMPER.chart(2).startActivity(this);

        });
    }

    @Override
    protected void initData() {
        genData();
        adapter = new HeightInputAdapter(list, 2);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void genData() {
        list = new ArrayList<>();
        String huangdan = BabyManager.getHuangdan();
        if (!TextUtils.isEmpty(huangdan)) {
            //数据回显
            List<InputEntity> inputList = GSON.fromJson(huangdan, new TypeToken<List<InputEntity>>() {
            }.getType());
            list.addAll(inputList);
        } else {
            for (int i = 0; i < ChartConst.huangDanHours.length; i++) {
                InputEntity entity = new InputEntity();

                entity.key = ChartConst.huangDanHours[i];
                entity.value = "0.0";
                list.add(entity);
            }
        }
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
