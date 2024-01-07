package com.zaozhuang.newborn.manage;

import static java.util.logging.Level.CONFIG;

import android.text.TextUtils;

//import com.ctb.opencar.config.StorageSchema;
//import com.ctb.opencar.data.AppInfoData;
//import com.ctb.opencar.data.ConfigData;

import com.zaozhuang.newborn.config.StorageSchema;
import com.zaozhuang.newborn.data.ConfigData;

import java.util.HashMap;

import mangogo.appbase.util.TimeUtils;

/**
 * Created by libing on 2020/10/19.
 * e-mail 18810979455@163.com
 */
public class ConfigManager implements StorageSchema {

    private static final String TAG = "ConfigManager";
    private static ConfigData mConfigData = null;

    public static void init() {
        mConfigData = CONFIG.get();
        if (mConfigData == null) {
            mConfigData = new ConfigData();
        }
    }

    public static void delete() {
        CONFIG.delete();
        mConfigData = null;
    }

//    public static void saveAppInfo(AppInfoData appInfoData) {
//        if (appInfoData != null) {
//            if (appInfoData.appVersion != null) {
//                mConfigData.id = appInfoData.appVersion.id;
//                mConfigData.currentVersionCode = appInfoData.appVersion.currentVersionCode;
//                mConfigData.currentVersionName = appInfoData.appVersion.currentVersionName;
//                mConfigData.lowestForcedVersionCode = appInfoData.appVersion.lowestForcedVersionCode;
//                mConfigData.appType = appInfoData.appVersion.appType;
//                mConfigData.upgradeCopy = appInfoData.appVersion.upgradeCopy;
//                mConfigData.upgradeApkUrl = appInfoData.appVersion.upgradeApkUrl;
//                mConfigData.needForcedUpgrade = appInfoData.appVersion.needForcedUpgrade;
//            }
//            mConfigData.reviewFlag = appInfoData.reviewFlag;
//            mConfigData.agoraMainShow = appInfoData.agoraMainShow;
//            mConfigData.upgradeVersion = appInfoData.upgradeVersion;
//            CONFIG.save(mConfigData);
//        }
//    }

    public static boolean getUpgradeVersion() {
        return mConfigData.upgradeVersion;
    }


    public static boolean getMap() {
        return mConfigData == null ? false : mConfigData.mapMainFlag;
    }

    public static boolean getReviewFlag() {
        return mConfigData == null ? false : mConfigData.reviewFlag;
    }

//    public static boolean isContains() {
//        if (!UserManager.isLogin()) {
//            return false;
//        }
//        if (mConfigData.whiteListUsers == null || mConfigData.whiteListUsers.size() == 0) {
//            return false;
//        }
//
//        return mConfigData.whiteListUsers.contains(Long.valueOf(UserManager.getUserId()));
//    }

    public static void saveIsFirst(String isFirst) {
        mConfigData.isFirst = isFirst;
        CONFIG.save(mConfigData);
    }

    public static void saveAgree(String agree) {
        mConfigData.agreement = agree;
        CONFIG.save(mConfigData);
    }

    public static void saveMessageTips(boolean isOpenMessageTips) {
        mConfigData.isOpenMessageTips = isOpenMessageTips;
        CONFIG.save(mConfigData);
    }

    public static String getYoungPsd() {
        return mConfigData.youngPassword;
    }

    //第一次进入启动选择页面（本地保存） 默认true
    public static boolean isFirstJoinStartSel() {
        return mConfigData.isFirstJoinStartSel;
    }

    ////第一次进入娱乐主页面（本地保存） 默认true
    public static boolean isFirstJoinMain() {
        return mConfigData.isFirstJoinMain;
    }

    //第一次 安装app ， 展示 功能引导页 默认true
    public static boolean isFirstLoadGuide() {
        return mConfigData.isFirstLoadGuide;
    }

    public static boolean isOpenYang() {
        return mConfigData.youngModeSafetyMode;
    }

    public static void saveYoungPsd(String psd) {
        mConfigData.youngPassword = psd;
        CONFIG.save(mConfigData);
    }

    public static void saveYoungMode(boolean youngModeSafetyMode) {
        mConfigData.youngModeSafetyMode = youngModeSafetyMode;
        CONFIG.save(mConfigData);
    }

    public static void saveIsFirstLoadGuide(boolean isFirstLoadGuide) {
        mConfigData.isFirstLoadGuide = isFirstLoadGuide;
        CONFIG.save(mConfigData);
    }

    public static void saveIsJoinStartSel(boolean isFirstLaunch) {
        mConfigData.isFirstJoinStartSel = isFirstLaunch;
        CONFIG.save(mConfigData);
    }

    public static void saveIsFirstJoinMain(boolean isFirstLaunchMain) {
        mConfigData.isFirstJoinMain = isFirstLaunchMain;
        CONFIG.save(mConfigData);
    }

    public static boolean getIsOpenMessageTips() {
        return mConfigData.isOpenMessageTips;
    }


    public static boolean getAgree() {
        return mConfigData == null ? false : (!TextUtils.isEmpty(mConfigData.agreement) && !"0".equals(mConfigData.agreement));
    }


    public static boolean getIsFirst() {
        return mConfigData == null ? false : (TextUtils.isEmpty(mConfigData.isFirst) || "0".equals(mConfigData.isFirst));
    }

//    public static void setTime(long time) {
//        mConfigData.time = time;
//        mConfigData.youngModeMap.put(mConfigData.getYoungModeKey(), time);
//        CONFIG.save(mConfigData);
//    }

//    public static boolean isSameDay() {
//        Long time = mConfigData.youngModeMap.get(mConfigData.getYoungModeKey());
//        if (time == null || time == 0) {
//            return false;
//        }
////        if (mConfigData.time == 0) {
////            return false;
////        }
//        return TimeUtils.isSameDay(time);
//    }


    public static void setRechargeFirstTime(long time) {
        if (mConfigData.rechargeFirstTime == null) {
            mConfigData.rechargeFirstTime = new HashMap<>();
        }
//        mConfigData.rechargeFirstTime.put(UserManager.getUserId(), time);
        CONFIG.save(mConfigData);
    }

//    public static boolean isRechargeFirstSameDay() {
//        if (mConfigData.rechargeFirstTime == null) {
//            mConfigData.rechargeFirstTime = new HashMap<>();
//        }
//        Long time = mConfigData.rechargeFirstTime.get(UserManager.getUserId());
//        if (time == null) {
//            time = 0L;
//        }
//        return TimeUtils.isSameDay(time);
//    }


    public static void setRecommendTime(long recommendTime) {
        mConfigData.recommendTime = recommendTime;
        CONFIG.save(mConfigData);
    }

    public static boolean isRecommendSameDay() {
        if (mConfigData.recommendTime == 0) {
            return false;
        }
        return TimeUtils.isSameDay(mConfigData.recommendTime);
    }

    public static void setFirstHot(boolean isFirstHot) {
        mConfigData.isFirstHot = isFirstHot ? "0" : "1";
        CONFIG.save(mConfigData);
    }

    public static boolean getFirstHot() {
        return null == mConfigData.isFirstHot || "0".equals(mConfigData.isFirstHot) ? true : false;
    }

    public static void setFirstRoom(boolean isFirstRoom) {
        mConfigData.isFirstRoom = isFirstRoom ? "0" : "1";
        CONFIG.save(mConfigData);
    }

    public static boolean getFirstRoom() {
        return null == mConfigData.isFirstRoom || "0".equals(mConfigData.isFirstRoom) ? true : false;
    }

    public static void saveAddress(String address) {
        mConfigData.address = address;
        CONFIG.save(mConfigData);
    }

    public static void saveCityCode(String cityCode) {
        mConfigData.cityCode = cityCode;
        CONFIG.save(mConfigData);
    }


    public static String getCityCode() {
        return mConfigData == null ? "" : mConfigData.cityCode;
    }

    public static String getAddress() {
        return mConfigData == null ? "" : mConfigData.address;
    }
}