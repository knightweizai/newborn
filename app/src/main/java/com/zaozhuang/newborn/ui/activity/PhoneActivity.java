package com.zaozhuang.newborn.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.ui.base.BaseActivity;

public class PhoneActivity extends BaseActivity {

    View mBackView;
    TextView mTitleView;
    private RelativeLayout rl_phone;
//    private RelativeLayout rl_knowledge;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone;
    }

    @Override
    protected void initView() {
        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        rl_phone = findViewById(R.id.rl_phone);
//        rl_knowledge = findViewById(R.id.rl_knowledge);
        mTitleView.setText("向上转诊");
    }

    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());
        rl_phone.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:120"));
            startActivity(intent);
        });
//        rl_video.setOnClickListener(v -> {
//            JUMPER.videoList().startActivity(this);
//        });
    }
}
