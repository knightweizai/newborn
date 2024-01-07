package com.zaozhuang.newborn.ui.activity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zaozhuang.newborn.BuildConfig;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.adapter.TransmitAdapter;
import com.zaozhuang.newborn.data.InputEntity;
import com.zaozhuang.newborn.ui.base.BaseActivity;
import com.zaozhuang.newborn.view.MyLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

public class TransmitActivity extends BaseActivity {



    View mBackView;
    TextView mTitleView;
    private TextView tv_connecting;
    private RecyclerView recyclerView;
    private List<InputEntity> mListData = new ArrayList<>();

    private MyLayout myLayout;
    private Button btn1,btn2;
    private TransmitAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transmit;
    }

    @Override
    protected void initView() {

//        Executors.newFixedThreadPool()
//        mBackView = findViewById(R.id.common_title_bar_back_image);
//        mTitleView = findViewById(R.id.common_title_bar_title_text);
//        tv_connecting = findViewById(R.id.tv_connecting);
        myLayout = findViewById(R.id.myLayout);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransmitAdapter(mListData);
        recyclerView.setAdapter(adapter);
//        mTitleView.setText("手环连接");
    }

    @Override
    protected void initListener() {
//        mBackView.setOnClickListener(v -> finish());
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "myLayout on touch");
                return true;
            }
        });

        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "myLayout on click");
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"btn1 onclick ////");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"btn2 onclick ////");
            }
        });


        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG,"listview OnTouch  "+event.getAction());
                return false;
            }
        });


        genData();

    }

    private void genData() {
        for (int i = 0; i < 30; i++) {
            InputEntity entity = new InputEntity();
            entity.key = i+"";
            mListData.add(entity);
        }

        adapter.notifyDataSetChanged();

    }
}
