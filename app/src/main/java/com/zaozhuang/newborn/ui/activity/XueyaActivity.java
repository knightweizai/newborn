package com.zaozhuang.newborn.ui.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.adapter.XinlvAdapter;
import com.zaozhuang.newborn.chart.ChartConst;
import com.zaozhuang.newborn.data.InputEntity;
import com.zaozhuang.newborn.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class XueyaActivity extends BaseActivity {

    View mBackView;
    TextView mTitleView;
    TextView mRightText;
    private RecyclerView recyclerView;
    private TextView tv_save;
    private XinlvAdapter adapter;

    private List<InputEntity> list = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_xinlv;
    }

    @Override
    protected void initView() {
        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        recyclerView = findViewById(R.id.recyclerView);

        mTitleView.setText("血压");
    }


    @Override
    protected void initData() {
        genData();
        adapter = new XinlvAdapter(list, 0);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    private void genData() {
        for (int i = ChartConst.xinlvKey.length - 1; i >=0; i--) {
            InputEntity entity = new InputEntity();
            entity.key = ChartConst.xinlvKey[i];
            entity.value = ChartConst.xueyaValue[i];
            list.add(entity);
        }
    }


    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());
    }
}
