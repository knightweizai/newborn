package com.zaozhuang.newborn.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.db.dao.UserDao;
import com.zaozhuang.newborn.db.entity.User;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.ui.base.BaseActivity;

import java.util.List;

import mangogo.appbase.util.ToastUtil;

public class RegisterActivity extends BaseActivity {


    View mBackView;
    TextView mTitleView;
    private EditText et_phone,et_name, et_pwd;
    private TextView tv_done;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        et_phone = findViewById(R.id.et_phone);
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        tv_done = findViewById(R.id.tv_done);

        mTitleView.setText("注册");

        tv_done.setBackground(
                BabyManager.isBoy() ?
                        getResources().getDrawable(R.drawable.bg_btn_main_boy):getResources().getDrawable(R.drawable.bg_btn_main_girl));
    }

    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());
        tv_done.setOnClickListener(v -> {
            String phone = et_phone.getText().toString().trim();
            String name = et_name.getText().toString().trim();
            String pwd = et_pwd.getText().toString().trim();

            if (TextUtils.isEmpty(phone) ) {
                ToastUtil.showMessage("手机号不能为空");
                return;
            }
            if (TextUtils.isEmpty(phone) ) {
                ToastUtil.showMessage("用户名不能为空");
                return;
            }
            if (TextUtils.isEmpty(phone) ) {
                ToastUtil.showMessage("密码不能为空");
                return;
            }

            addUserLogic(phone,name, pwd);

        });
    }

    private void addUserLogic(String phone,String name, String pwd) {
        //数据库操作
        List<User> userList = UserDao.getInstance(this).getUserList();
        User user = null;
        for (int i = 0; i < userList.size(); i++) {
            String str = userList.get(i).phone;
            if (str.equals(phone)) {
                user = userList.get(i);
                break;
            }
        }
        if (user != null) {
            user.name = name;
            user.pwd = pwd;
            UserDao.getInstance(this).update(user);
        } else {
            User addUser = new User();
            addUser.phone = phone;
            addUser.name = name;
            addUser.pwd = pwd;
            UserDao.getInstance(this).add(addUser);
        }
        ToastUtil.showMessage("注册成功");
        finish();
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
