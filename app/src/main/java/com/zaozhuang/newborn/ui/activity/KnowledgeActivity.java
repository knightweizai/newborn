package com.zaozhuang.newborn.ui.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.ui.base.BaseActivity;

public class KnowledgeActivity extends BaseActivity {

    View mBackView;
    TextView mTitleView;
    private RelativeLayout rl_video;
    private RelativeLayout rl_knowledge;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge;
    }

    @Override
    protected void initView() {
        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        rl_video = findViewById(R.id.rl_video);
        rl_knowledge = findViewById(R.id.rl_knowledge);
        mTitleView.setText("科学育儿");
    }

    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());
        rl_knowledge.setOnClickListener(v -> {
            JUMPER.babyKnowledge().startActivity(this);
        });
        rl_video.setOnClickListener(v -> {
            JUMPER.videoList().startActivity(this);
        });
    }
}
