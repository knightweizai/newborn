package com.zaozhuang.newborn.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.github.gzuliyujiang.wheelpicker.BirthdayPicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnLinkagePickedListener;
import com.lxj.xpopup.XPopup;
import com.zaozhuang.newborn.R;
import com.zaozhuang.newborn.consts.JumperParam;
import com.zaozhuang.newborn.db.dao.BabyDao;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.event.BabyRefreshEvent;
import com.zaozhuang.newborn.event.BabySelEvent;
import com.zaozhuang.newborn.manage.BabyManager;
import com.zaozhuang.newborn.popupwindow.SelPicPopupWindow;
import com.zaozhuang.newborn.ui.base.BaseActivity;
import com.zaozhuang.newborn.util.GlideUtils;
import com.zaozhuang.newborn.view.picker.BabyWeeksLikePicker;

import java.io.File;
import java.util.Date;
import java.util.List;

import mangogo.appbase.util.LogUtil;
import mangogo.appbase.util.ToastUtil;

public class BabyAddActivity extends BaseActivity implements OnLinkagePickedListener {

    View mBackView;
    TextView mTitleView;
    private ImageView imageView;
    private TextView tv_done;
    private TextView tv_birthday;
    private TextView tv_weeks;
    private EditText et_name;
    private RadioButton rb0, rb1;
    private String imgPath = "";
    private RelativeLayout rl_birthday;
    private RelativeLayout rl_weeks;
    private Baby intentBaby;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baby_add;
    }

    @Override
    protected void initView() {

        mBackView = findViewById(R.id.common_title_bar_back_image);
        mTitleView = findViewById(R.id.common_title_bar_title_text);
        imageView = findViewById(R.id.imageView);
        et_name = findViewById(R.id.et_name);
        rb0 = findViewById(R.id.rb0);
        rb1 = findViewById(R.id.rb1);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_weeks = findViewById(R.id.tv_weeks);
        tv_done = findViewById(R.id.tv_done);
        rl_birthday = findViewById(R.id.rl_birthday);
        rl_weeks = findViewById(R.id.rl_weeks);

        mTitleView.setText("添加宝宝");

        String serial = getIntent().getStringExtra(JumperParam.BABY_SERIAL);
        if (!TextUtils.isEmpty(serial)) {
            intentBaby = GSON.fromJson(serial, Baby.class);

            if (!TextUtils.isEmpty(intentBaby.imgPath)) {

                GlideUtils.loadCircleImage(this, imageView, new File(intentBaby.imgPath));
            }
            if (!TextUtils.isEmpty(intentBaby.name)) {
                et_name.setText(intentBaby.name);
            }
            if (intentBaby.gender == 0) {
                rb0.setChecked(true);
                rb1.setChecked(false);
            } else {
                rb0.setChecked(false);
                rb1.setChecked(true);
            }
            if (!TextUtils.isEmpty(intentBaby.birthday)) {

                tv_birthday.setText(intentBaby.birthday);
            }
            if (!TextUtils.isEmpty(intentBaby.weeks)) {
                tv_weeks.setText(intentBaby.weeks);
            }
        }

        tv_done.setBackground(
                BabyManager.isBoy() ?
                        getResources().getDrawable(R.drawable.bg_btn_main_boy):getResources().getDrawable(R.drawable.bg_btn_main_girl));

    }

    @Override
    protected void initListener() {
        mBackView.setOnClickListener(v -> finish());
        imageView.setOnClickListener(v -> {
            new XPopup.Builder(this)
                    .asCustom(new SelPicPopupWindow(this,
                            R.layout.popup_sel_pic, R.layout.popup_sel_pic_item, path -> {
                        imgPath = path;
                        GlideUtils.loadCircleImage(this, imageView, new File(path));
                    }))
                    .show();

        });
        rl_birthday.setOnClickListener(v -> {
            String birthday = tv_birthday.getText().toString();
            showTimePopup(birthday);
        });
        rl_weeks.setOnClickListener(v -> {
            showWeeks();
        });
        tv_done.setOnClickListener(v -> {
            addBaby();
        });
    }

    private void addBaby() {
        String name = et_name.getText().toString().trim();
        int gender = -1;
        if (rb0.isChecked()) {
            gender = 0;
        }
        if (rb1.isChecked()) {
            gender = 1;
        }
        String birthday = tv_birthday.getText().toString();
        String weeks = tv_weeks.getText().toString();


        if (TextUtils.isEmpty(name)) {
            ToastUtil.showMessage("请输入宝宝名字");
            return;
        }
        if (gender == -1) {
            ToastUtil.showMessage("请选择宝宝性别");
            return;
        }
        if (TextUtils.isEmpty(birthday)) {
            ToastUtil.showMessage("请选择宝宝出生日期");
            return;
        }
        if (TextUtils.isEmpty(weeks)) {
            ToastUtil.showMessage("请选择宝宝孕周");
            return;
        }
        //如果intent中传递过来的对象，也就是修改宝宝信息， 那就不需要查询库
        Baby tempBaby = null;
        if (intentBaby != null) {
            tempBaby = intentBaby;
        } else {
            List<Baby> babyList = BabyDao.getInstance(this).getBabyList();
            for (int i = 0; i < babyList.size(); i++) {
                String str = babyList.get(i).name;
                if (str.equals(name)) {
                    tempBaby = babyList.get(i);
                }
            }
        }

        if (tempBaby == null) {
            tempBaby = new Baby();
            setBaby(tempBaby, imgPath, name, gender, birthday, weeks);
            //新加一个宝宝
            BabyDao.getInstance(BabyAddActivity.this).add(tempBaby);
        } else {
            setBaby(tempBaby, imgPath, name, gender, birthday, weeks);
            //更新宝宝信息
            BabyDao.getInstance(BabyAddActivity.this).update(tempBaby);
        }
        ToastUtil.showMessage("添加成功");
        BabyManager.saveBabyInfo(BabyDao.getInstance(this).getBabyByName(name));
        BUS.post(new BabyRefreshEvent());
        BUS.post(new BabySelEvent());
        finish();

    }

    private void setBaby(Baby tempBaby, String imgPath, String name, int gender, String birthday, String weeks) {
        tempBaby.gender = gender;
        if (!TextUtils.isEmpty(imgPath)) tempBaby.imgPath = imgPath;
        if (!TextUtils.isEmpty(name)) tempBaby.name = name;
        if (!TextUtils.isEmpty(birthday)) tempBaby.birthday = birthday;
        if (!TextUtils.isEmpty(weeks)) tempBaby.weeks = weeks;
    }

    private void showTimePopup(String str) {
        BirthdayPicker picker = new BirthdayPicker(this);

        String[] split;
        if (!TextUtils.isEmpty(str)) {
            split = str.split("-");
        } else {
            String yearMonthDayRange = TimeUtils.millis2String(System.currentTimeMillis(), "yyyy-MM-dd");
            split = yearMonthDayRange.split("-");
        }

        picker.setDefaultValue(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        picker.setOnDatePickedListener(new OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {
//                ToastUtil.showMessage(year + "," + month + "," + day);
                Date selDate = TimeUtils.string2Date(year + "-" + month + "-" + day, "yyyy-MM-dd");
                String fitTimeSpan = TimeUtils.getFitTimeSpan(new Date(), selDate, 1);
//                ToastUtil.showMessage(": " + fitTimeSpan);

                tv_birthday.setText(year + "-" + month + "-" + day);
            }
        });
        picker.getWheelLayout().setResetWhenLinkage(false);
        picker.getCancelView().setText("取消");
        picker.getOkView().setText("确定");
        picker.show();


//        Calendar date = Calendar.getInstance();
//        date.setTime(new Date(System.currentTimeMillis()));

////        Calendar date2 = Calendar.getInstance();
////        date2.set(2020, 5,1);
//        TimePickerPopup popup = new TimePickerPopup(this)
//                .setDefaultDate(date)  //设置默认选中日期
//                .setYearRange(1990, 2030) //设置年份范围
////                        .setDateRange(date, date2) //设置日期范围
//                .setTimePickerListener(new TimePickerListener() {
//                    @Override
//                    public void onTimeChanged(Date date) {
//                        //时间改变
//                    }
//
//                    @Override
//                    public void onTimeConfirm(Date date, View view) {
//                        //点击确认时间
//                        ToastUtil.showMessage("选择的时间：" + date.toLocaleString());
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//
//        new XPopup.Builder(this)
//                .asCustom(popup)
//                .show();
    }


    public void showWeeks() {
        BabyWeeksLikePicker picker = new BabyWeeksLikePicker(this);
        picker.setOnLinkagePickedListener(this);
        picker.show();
    }

    @Override
    public void onLinkagePicked(Object first, Object second, Object third) {
        tv_weeks.setText(first + "-" + second);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("jinyan","oncreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("jinyan","onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i("jinyan","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("jinyan","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("jinyan","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("jinyan","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("jinyan","onDestroy");
    }


}
