package com.zaozhuang.newborn.data;

//import com.ctb.opencar.manage.UserManager;

import com.zaozhuang.newborn.manage.UserManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by libing on 2020/10/19.
 * e-mail 18810979455@163.com
 */
public class ConfigData {

    public ConfigData() {
    }

    public String isFirstHot; // 0 第一次
    public String isFirstRoom;
    public boolean isFirstJoinStartSel = true;//第一次进入启动选择页面（本地保存） 默认true
    public boolean isFirstJoinMain = true;//第一次进入娱乐主页面（本地保存） 默认true
    public boolean isFirstLoadGuide = true;//第一次 安装app ， 展示 功能引导页 默认true

    public String address;
    public String cityCode;
    public String isFirst;
    public String agreement;
    public Boolean agoraMainShow;
    public boolean isOpenMessageTips;

    public boolean youngModeSafetyMode;
    public String youngPassword;

    public boolean mapMainFlag;


    public List<Long> whiteListUsers;


    public Integer id;
    public Integer currentVersionCode = 0;
    public String currentVersionName;
    public Integer lowestForcedVersionCode;
    public String appType;
    public String upgradeCopy;
    public Object upgradeApkUrl;
    public Boolean needForcedUpgrade;
    public Boolean upgradeVersion;
    public Boolean reviewFlag;

    public HashMap<String, Long> youngModeMap = new HashMap<>();

    public long time;
    public long recommendTime;
    public HashMap<String, Long> rechargeFirstTime; //首充优惠 每隔24小时弹一次

//    public String getYoungModeKey (){
//        return "YoungMode" + UserManager.getsUserInfo().userId;
//    }

}