package com.zaozhuang.newborn.manage;

import android.text.TextUtils;

//import com.ctb.opencar.AppConfig;
import com.zaozhuang.newborn.config.Const;
//import com.ctb.opencar.config.OssConfig;
//import com.ctb.opencar.config.StorageSchema;
//import com.ctb.opencar.data.InterestData;
//import com.ctb.opencar.data.UserData;
//import com.ctb.opencar.data.UserInfo;
//import com.ctb.opencar.data.chatroom.ChatRoomInstance;
//import com.ctb.opencar.data.chatroom.UserDetailData;
//import com.ctb.opencar.data.login.LoginInfoData;
//import com.ctb.opencar.data.login.PhoneLoginData;
//import com.ctb.opencar.enums.EnumInfo;

import com.zaozhuang.newborn.config.StorageSchema;
import com.zaozhuang.newborn.data.UserInfo;
import com.zaozhuang.newborn.db.entity.User;

import java.util.List;

import mangogo.appbase.util.TimeUtils;

public class UserManager implements StorageSchema {

    private static final String TAG = "UserManager";
    private static User sUserInfo = null;

    public static void init() {
        sUserInfo = USER_INFO.get();
        if (sUserInfo == null) {
            sUserInfo = new User();
        }
        if (UserManager.isLogin()) {
//            Const.API.queryUser(r -> {
//                if (r.check()) {
//                    updateInfo(r.data);
//                }
//            });
        }
    }

    public static boolean isLogin() {
        return sUserInfo != null && !TextUtils.isEmpty(sUserInfo.phone);
//        return sUserInfo != null && sUserInfo.isValid();
    }

    public static void deleteUserInfo() {
        USER_INFO.delete();
        sUserInfo = null;
    }

    public static void saveUserInfo(User user){
        sUserInfo = user;
        USER_INFO.save(sUserInfo);
    }

//    public static String getUserId() {
//        return isLogin() ? String.valueOf(sUserInfo.userId) : "";
//    }

//    public static boolean canInvite() {
//        return sUserInfo.canInvite;
//    }
//
//    public static boolean manager() {
//        return sUserInfo.manager;
//    }
//
//    public static boolean worker() {
//        return sUserInfo.worker;
//    }

//    public static void savePhoneInfo(PhoneLoginData loginData) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        ChatRoomInstance.getInstance().en = loginData.en;
//        ChatRoomInstance.getInstance().driveNewUser = loginData.driveNewUser;
//        if (loginData.userInfo != null) {
//            AppConfig.selfInfo = sUserInfo;
//            LoginInfoData userInfo = loginData.userInfo;
//            sUserInfo.userRole = userInfo.userRole;
//            sUserInfo.userBirthday = userInfo.userBirthday;
//            sUserInfo.userId = userInfo.userId;
//            sUserInfo.userSex = userInfo.userSex;
//            sUserInfo.userStatus = userInfo.userStatus;
//            sUserInfo.userIcon = userInfo.userIcon;
//            sUserInfo.userLabelList = userInfo.userLabelList;
//            sUserInfo.userNobleLevel = userInfo.userNobleLevel;
//            sUserInfo.userWX = userInfo.userWX;
//            sUserInfo.userQQ = userInfo.userQQ;
//            sUserInfo.userSchool = userInfo.userSchool;
//            sUserInfo.userProfession = userInfo.userProfession;
//            sUserInfo.userSign = userInfo.userSign;
//            sUserInfo.nickName = userInfo.nickName;
//            sUserInfo.userHometown = userInfo.userHometown;
//            sUserInfo.userSensitive = userInfo.userSensitive;
//            sUserInfo.userStat = userInfo.userStat;
//
//        }
//        sUserInfo.userToken = loginData.userToken;
//        if (loginData.emchat != null) {
//            sUserInfo.emchat = loginData.emchat;
//        }
//        USER_INFO.save(sUserInfo);
//    }

//    public static void setAvatar(String avatar) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.userIcon = avatar;
//        USER_INFO.save(sUserInfo);
//    }
//
//    public static void setLoginWay(int loginWay) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.loginWay = loginWay;
//        USER_INFO.save(sUserInfo);
//    }
//    public static void setLocalDefaultCarPath(String localDefaultCarPath) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.localDefaultCarPath = localDefaultCarPath;
//        USER_INFO.save(sUserInfo);
//    }
//
//    public static void setLocalDefaultCarName(String localDefaultCarName) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.localDefaultCarName = localDefaultCarName;
//        USER_INFO.save(sUserInfo);
//    }
//
//    public static void setIsLocalNewUser(boolean localNewUser) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.isLocalNewUser = localNewUser;
//        USER_INFO.save(sUserInfo);
//    }
//
//    public static void setSex(int sex) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.userSex = sex;
//        USER_INFO.save(sUserInfo);
//    }
//
//    public static void setName(String name) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.nickName = name;
//        USER_INFO.save(sUserInfo);
//    }
//
//    public static void setCanInvite(boolean canInvite) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.canInvite = canInvite;
//        USER_INFO.save(sUserInfo);
//    }
//
//    public static void setManager(boolean manager) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.manager = manager;
//        USER_INFO.save(sUserInfo);
//    }
//
//
//    public static void setWorker(boolean worker) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.worker = worker;
//        USER_INFO.save(sUserInfo);
//    }

//    public static void setUserLuckyStat(UserDetailData.UserStat.UserLuckyStat userLuckyStat) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        if (sUserInfo.userStat == null) {
//            sUserInfo.userStat = new UserInfo.UserStat();
//        }
//        if (sUserInfo.userStat.userLuckyStat == null) {
//            sUserInfo.userStat.userLuckyStat = new UserInfo.UserStat.UserLuckyStat();
//        }
//        sUserInfo.userStat.userLuckyStat.luckyCnt = userLuckyStat.luckyCnt;
//        sUserInfo.userStat.userLuckyStat.luckyMaxCnt = userLuckyStat.luckyMaxCnt;
//        USER_INFO.save(sUserInfo);
//    }

//    public static void updateInfo(UserData userData) {
//
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        if (userData.userInfo != null) {
//            AppConfig.selfInfo = sUserInfo;
//            LoginInfoData userInfo = userData.userInfo;
//            sUserInfo.userRole = userInfo.userRole;
//            sUserInfo.userBirthday = userInfo.userBirthday;
//            sUserInfo.userId = userInfo.userId;
//            sUserInfo.userSex = userInfo.userSex;
//            sUserInfo.userStatus = userInfo.userStatus;
//            sUserInfo.userIcon = userInfo.userIcon;
//            sUserInfo.userLabelList = userInfo.userLabelList;
//            sUserInfo.userWX = userInfo.userWX;
//            sUserInfo.userQQ = userInfo.userQQ;
//            sUserInfo.userSchool = userInfo.userSchool;
//            sUserInfo.userProfession = userInfo.userProfession;
//            sUserInfo.userSign = userInfo.userSign;
//            sUserInfo.nickName = userInfo.nickName;
//            sUserInfo.identityAuthStatus = userInfo.identityAuthStatus;
//            sUserInfo.userHometown = userInfo.userHometown;
//            if (userData.userInfo.userCharmLevel != null) {
//                sUserInfo.userCharmLevel = userInfo.userCharmLevel;
//            }
//            if (userData.userInfo.userContributionLevel != null) {
//                sUserInfo.userContributionLevel = userInfo.userContributionLevel;
//            }
//            if (userData.userInfo.userNobleLevel != null) {
//                sUserInfo.userNobleLevel = userInfo.userNobleLevel;
//            }
//            sUserInfo.prettyNumber = userInfo.prettyNumber;
//            sUserInfo.userSensitive = userInfo.userSensitive;
//            sUserInfo.userStat = userInfo.userStat;
//            sUserInfo.worker = userInfo.worker;
//        }
//        if (userData.emchat != null) {
//            sUserInfo.emchat = userData.emchat;
//        }
//
//        USER_INFO.save(sUserInfo);
//    }

//    public static void setUserLabel(List<InterestData.LabelListBean> userLabelList) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        sUserInfo.userLabelList = userLabelList;
//        USER_INFO.save(sUserInfo);
//        AppConfig.selfInfo = sUserInfo;
//    }

//    public static void setValueOfPosition(EnumInfo enumInfo, String value) {
//        if (sUserInfo == null) {
//            sUserInfo = new UserInfo();
//        }
//        switch (enumInfo) {
//            case NICKNAME:
//                sUserInfo.nickName = value;
//                break;
//            case SIGN:
//                sUserInfo.userSign = value;
//                break;
//            case AGE:
//                sUserInfo.userBirthday = TimeUtils.getTime(value, TimeUtils.DATE);
//                break;
//            case CITY:
//                sUserInfo.userHometown = value;
//                break;
//            case PROFESSION:
//                sUserInfo.userProfession = value;
//                break;
//            case SCHOOL:
//                sUserInfo.userSchool = value;
//                break;
//            case QQ:
//                sUserInfo.userQQ = value;
//                break;
//            case WX:
//                sUserInfo.userWX = value;
//                break;
//            case SEX:
//                sUserInfo.userSex = Integer.valueOf(value);
//                break;
//            case AVATAR:
//                sUserInfo.userIcon = OssConfig.PUBLIC_BASE_URL.concat(value);
//                break;
//        }
//
//        USER_INFO.save(sUserInfo);
//    }

//    public static int getFocus() {
//        return sUserInfo.userStat == null ? 0 : sUserInfo.userStat.attentionCnt;
//    }
//
//    public static int getFans() {
//        return sUserInfo.userStat == null ? 0 : sUserInfo.userStat.fansCnt;
//    }
//
//    public static UserInfo getsUserInfo() {
//        return sUserInfo;
//    }
//
//    public static String getAvatar() {
//        return sUserInfo == null ? "" : sUserInfo.userIcon;
//    }

    public static String getName(){
        return sUserInfo.name;
    }

    public static String getPhone(){
        return sUserInfo.phone;
    }

    public static String getPwd(){
        return sUserInfo.pwd;
    }

//    public static String getEmId() {
//        return (sUserInfo == null || sUserInfo.emchat == null || TextUtils.isEmpty(sUserInfo.emchat.emId)) ? "" : sUserInfo.emchat.emId;
//    }

//    public static boolean isMentor() {
//        return sUserInfo != null && sUserInfo.userRole == 2;
//    }
//
//    public static boolean isSimpleUser() {
//        return sUserInfo != null && sUserInfo.userRole == 1;
//    }
//
//    public static String getPhone() {
//        return sUserInfo.userSensitive == null ? "" : sUserInfo.userSensitive.userMobile;
//    }
//
//
//    public static void setIdentityAuthStatus(int identityAuthStatus) {
//        sUserInfo.identityAuthStatus = identityAuthStatus;
//        USER_INFO.save(sUserInfo);
////        AppConfig.selfInfo = sUserInfo;
//    }
//
//    public static int getIdentityAuthStatus() {
//        return sUserInfo.identityAuthStatus;
//    }
//
//
//    public static String getToken() {
//        return isLogin() && sUserInfo != null ? sUserInfo.userToken : "";
//    }
//
//    public static String getPushID() {
//        return isLogin() && sUserInfo != null ? sUserInfo.userToken : "";
//    }

}
