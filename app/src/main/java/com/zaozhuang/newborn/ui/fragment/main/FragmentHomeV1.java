package com.zaozhuang.newborn.ui.fragment.main;


import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.db.dao.BabyDao;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.event.BabyRefreshEvent;
import com.zaozhuang.newborn.event.BabySelEvent;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.ui.base.BaseFragment;
import com.zaozhuang.newborn.ui.base.fragment.IChildFragment;
import com.zaozhuang.newborn.util.GlideUtils;

import java.io.File;
import java.util.List;

import mangogo.appbase.eventbus.Event;
import mangogo.appbase.eventbus.ThreadType;
import mangogo.appbase.util.ToastUtil;

public class FragmentHomeV1 extends BaseFragment implements View.OnClickListener, IChildFragment {

    public static FragmentHomeV1 newInstance() {
        return new FragmentHomeV1();
    }

    private LinearLayout ll0;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;

    private RelativeLayout rl_home;
    private RelativeLayout rl_weight;
    private RelativeLayout rl_height;
    private RelativeLayout rl_huangdan;
    private RelativeLayout rl_chart;
    private RelativeLayout rl_xinlv;
    private RelativeLayout rl_xueya;
    private RelativeLayout rl_xueyang;
    private LinearLayout ll_add_baby;
    private LinearLayout ll_baby;
    private ImageView iv_head;
    private TextView tv_name;
    private TextView tv_age;
    private ToggleButton toggle;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homev1;
    }

    @Override
    protected void initView(View view) {
        ll0 = view.findViewById(R.id.ll0);
        ll1 = view.findViewById(R.id.ll1);
        ll2 = view.findViewById(R.id.ll2);
        ll3 = view.findViewById(R.id.ll3);
        rl_home = view.findViewById(R.id.rl_home);
        rl_weight = view.findViewById(R.id.rl_weight);
        rl_height = view.findViewById(R.id.rl_height);
        rl_huangdan = view.findViewById(R.id.rl_huangdan);
        rl_chart = view.findViewById(R.id.rl_chart);
        rl_xinlv = view.findViewById(R.id.rl_xinlv);
        rl_xueya = view.findViewById(R.id.rl_xueya);
        rl_xueyang = view.findViewById(R.id.rl_xueyang);
        ll_add_baby = view.findViewById(R.id.ll_add_baby);
        ll_baby = view.findViewById(R.id.ll_baby);
        iv_head = view.findViewById(R.id.iv_head);
        tv_name = view.findViewById(R.id.tv_name);
        tv_age = view.findViewById(R.id.tv_age);
        toggle = view.findViewById(R.id.toggle);

        setBabySelLogic();
        setAttr();
    }

    @Override
    protected void initListener() {
        ll0.setOnClickListener( v -> {
            JUMPER.familyDoctor().startActivity(getActivity());
        });
        ll1.setOnClickListener( v -> {
            JUMPER.knowledge().startActivity(getActivity());
        });
        ll2.setOnClickListener( v -> {
            JUMPER.phone().startActivity(getActivity());
        });
        ll3.setOnClickListener( v -> {
            JUMPER.contact().startActivity(getActivity());
        });
        ll_add_baby.setOnClickListener( v -> {
            JUMPER.babyAdd().startActivity(getActivity());
        });
        rl_height.setOnClickListener(v -> {
            JUMPER.heightInput().startActivity(getActivity());
        });
        rl_weight.setOnClickListener(v -> {
            JUMPER.weightInput().startActivity(getActivity());
        });
        rl_huangdan.setOnClickListener(v -> {
            JUMPER.huangdanInput().startActivity(getActivity());
        });
        rl_chart.setOnClickListener(v -> {
            JUMPER.chart().startActivity(getActivity());
        });
        rl_xinlv.setOnClickListener(v -> {
            JUMPER.xinlv().startActivity(getActivity());
        });
        rl_xueya.setOnClickListener(v -> {
            JUMPER.xueya().startActivity(getActivity());
        });
        rl_xueyang.setOnClickListener(v -> {
            JUMPER.xueyang().startActivity(getActivity());
        });

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ToastUtil.showMessage("设置成功");

            }
        });

    }

    @Event(runOn = ThreadType.MAIN)
    void onBabySelLogic(BabySelEvent event) {
        setAttr();
        setBabySelLogic();
    }

    private void setBabySelLogic() {
        List<Baby> list = BabyDao.getInstance(getActivity()).getBabyList();
        if (list.isEmpty()) {
            ll_baby.setVisibility(View.GONE);
            ll_add_baby.setVisibility(View.VISIBLE);
            return;
        }
        if (TextUtils.isEmpty(BabyManager.getName())) {
            ll_baby.setVisibility(View.GONE);
            ll_add_baby.setVisibility(View.VISIBLE);
            return;
        }
        ll_baby.setVisibility(View.VISIBLE);
        ll_add_baby.setVisibility(View.GONE);
        GlideUtils.loadCircleImage(getActivity(), iv_head, new File(BabyManager.getImgPath()), R.mipmap.default_avatar);
        tv_name.setText(BabyManager.getName());
        tv_age.setText(BabyManager.getAge());

    }

    @Event(runOn = ThreadType.MAIN)
    void onBabyLogic(BabyRefreshEvent event) {
        setAttr();
    }

    private void setAttr() {
        rl_home.setBackgroundColor(
                BabyManager.isBoy() ?
                        getResources().getColor(R.color.primary_color_boy):getResources().getColor(R.color.primary_color_girl));


    }


    @Override
    protected String getFragmentTag() {
        return null;
    }

    @Override
    protected void clear() {

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSelected() {

    }
}
