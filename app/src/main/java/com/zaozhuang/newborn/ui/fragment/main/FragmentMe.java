package com.zaozhuang.newborn.ui.fragment.main;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.adapter.BabyItemAdapter;
import com.zaozhuang.newborn.db.dao.BabyDao;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.event.BabyRefreshEvent;
import com.zaozhuang.newborn.event.BabySelEvent;
import com.zaozhuang.newborn.event.LoginEvent;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.manage.UserManager;
import com.zaozhuang.newborn.popupwindow.SelBabyPopupWindow;
import com.zaozhuang.newborn.ui.base.BaseFragment;
import com.zaozhuang.newborn.ui.base.fragment.IChildFragment;

import java.util.List;

import mangogo.appbase.eventbus.Event;
import mangogo.appbase.eventbus.ThreadType;

public class FragmentMe extends BaseFragment implements IChildFragment {

    private RelativeLayout rl_register;
    private RelativeLayout rl_add_baby;
    private RelativeLayout rl_blue_connect;
    private RelativeLayout rl_sliding;
    private RelativeLayout rl_volley_test;
    private RelativeLayout rl_transmit;
    private RelativeLayout rl_touchevent;
    private TextView tv_login;
    private RecyclerView recyclerView;
    private BabyItemAdapter adapter;
    private List<Baby> babyList;

    public static FragmentMe newInstance() {
        return new FragmentMe();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(View view) {
        rl_register = view.findViewById(R.id.rl_register);
        recyclerView = view.findViewById(R.id.recyclerView);
        tv_login = view.findViewById(R.id.tv_login);
        rl_add_baby = view.findViewById(R.id.rl_add_baby);
        rl_blue_connect = view.findViewById(R.id.rl_blue_connect);
        rl_sliding = view.findViewById(R.id.rl_sliding);
        rl_volley_test = view.findViewById(R.id.rl_volley_test);
        rl_transmit = view.findViewById(R.id.rl_transmit);
        rl_touchevent = view.findViewById(R.id.rl_touchevent);

        loginSuccessLogic();
        setAttr();
    }

    @Override
    protected void initData() {
        babyList = BabyDao.getInstance(getActivity()).getBabyList();

        adapter = new BabyItemAdapter(getActivity(), babyList, (position, baby) -> {
            new XPopup.Builder(getActivity())
                    .asCustom(new SelBabyPopupWindow(getActivity(),
                            R.layout.popup_sel_pic, R.layout.popup_sel_pic_item, baby))
                    .show();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

        rl_register.setOnClickListener(v -> {
            if (UserManager.isLogin()) {
                JUMPER.userInfo().startActivity(getActivity());
            } else {
                JUMPER.login().startActivity(getActivity());
            }

        });
        rl_add_baby.setOnClickListener(v -> {
            JUMPER.babyAdd().startActivity(getActivity());
        });
        rl_blue_connect.setOnClickListener(v -> {
            JUMPER.blueConnect2().startActivity(getActivity());
        });
        rl_transmit.setOnClickListener(v -> {
            JUMPER.transmit().startActivity(getActivity());
        });
        rl_touchevent.setOnClickListener(v -> {
            JUMPER.touchEventTest().startActivity(getActivity());
        });


        /**
         * 1. 如果 menu菜单显示出来后， 在进行滑动， 是否会将content布局隐藏掉，调用逻辑是什么
         */
        rl_sliding.setOnClickListener(v -> {
            JUMPER.sliding().startActivity(getActivity());
        });
        rl_volley_test.setOnClickListener(v -> {
            JUMPER.volleyTest().startActivity(getActivity());
        });
    }

    @Event(runOn = ThreadType.MAIN)
    void onLoginLogic(LoginEvent event) {
        loginSuccessLogic();
    }

    @Event(runOn = ThreadType.MAIN)
    void onBabyLogic(BabyRefreshEvent event) {
        List<Baby> list = BabyDao.getInstance(getActivity()).getBabyList();
        babyList.clear();
        babyList.addAll(list);
        adapter.notifyDataSetChanged();

        setAttr();


    }
    @Event(runOn = ThreadType.MAIN)
    void onBabySelLogic(BabySelEvent event) {
        setAttr();
    }

    private void setAttr() {
        rl_register.setBackgroundColor(
                BabyManager.isBoy() ?
                        getResources().getColor(R.color.primary_color_boy):getResources().getColor(R.color.primary_color_girl));

    }


    private void loginSuccessLogic() {
        if (UserManager.isLogin()) {
            tv_login.setText(UserManager.getName());
        } else {
            tv_login.setText("登录/注册");
        }
    }

    @Override
    protected String getFragmentTag() {
        return null;
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onSelected() {

    }
}
