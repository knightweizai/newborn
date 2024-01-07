package com.zaozhuang.newborn.ui.mine;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.ui.base.BaseActivity;
import com.zaozhuang.newborn.view.HorizontalScrollViewEx;

import java.util.ArrayList;

public class TouchEventActivity extends BaseActivity {


    private HorizontalScrollViewEx mListContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_touch_event;
    }

    @Override
    protected void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
        final int screenWidth = ScreenUtils.getScreenWidth();
        final int screenHeight = ScreenUtils.getScreenHeight();
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }

    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(TouchEventActivity.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void initListener() {


    }
}
