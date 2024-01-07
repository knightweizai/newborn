package com.zaozhuang.newborn.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.db.dao.UserDao;
import com.zaozhuang.newborn.db.entity.User;
import com.zaozhuang.newborn.event.LoginEvent;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.manage.UserManager;
import com.zaozhuang.newborn.ui.base.BaseActivity;

import java.util.List;

import mangogo.appbase.util.ToastUtil;

public class LoginActivity extends BaseActivity {


    View mBackView;
    TextView mTitleView;
    private EditText et_phone, et_pwd;
    private TextView tv_register;
    private TextView tv_login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        et_phone = findViewById(R.id.et_phone);
        et_pwd = findViewById(R.id.et_pwd);
        tv_register = findViewById(R.id.tv_register);
        tv_login = findViewById(R.id.tv_login);

        mTitleView.setText("登录");

        tv_login.setBackground(
                BabyManager.isBoy() ?
                        getResources().getDrawable(R.drawable.bg_btn_main_boy):getResources().getDrawable(R.drawable.bg_btn_main_girl));
        tv_register.setBackground(
                BabyManager.isBoy() ?
                        getResources().getDrawable(R.drawable.bg_btn_main_boy):getResources().getDrawable(R.drawable.bg_btn_main_girl));

    }

    @Override
    protected void initListener() {

        mBackView.setOnClickListener(v -> finish());
        tv_register.setOnClickListener(v -> {
            JUMPER.register().startActivity(this);
        });

        tv_login.setOnClickListener(v -> {
            String phone = et_phone.getText().toString().trim();
            String pwd = et_pwd.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showMessage("手机号不能为空");
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                ToastUtil.showMessage("密码不能为空");
                return;
            }
            loginLogic(phone, pwd);

        });
    }

    //登录操作
    private void loginLogic(String phone, String pwd) {
        //数据库操作
        List<User> userList = UserDao.getInstance(this).getUserList();
        User user = null;
        for (int i = 0; i < userList.size(); i++) {
            String str = userList.get(i).phone;
            String password = userList.get(i).pwd;
            if (str.equals(phone) && password.equals(pwd)) {
                user = userList.get(i);
                break;
            }
        }
        if (user != null) {
            //登录成功
            UserManager.saveUserInfo(user);
            finish();
            BUS.post(new LoginEvent());
        } else {
            ToastUtil.showMessage("手机号与密码不匹配");
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
