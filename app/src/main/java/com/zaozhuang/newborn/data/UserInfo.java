package com.zaozhuang.newborn.data;


import android.text.TextUtils;

//import com.ctb.opencar.data.chatroom.UserDetailData;
//import com.ctb.opencar.data.login.PhoneLoginData;

import java.util.List;

/**
 * 存储类 不允许修改
 */
public class UserInfo {
    public String localDefaultCarPath;//升级车icon（本地保存）
    public String localDefaultCarName;//升级车名称（本地保存）
    public boolean isLocalNewUser;//是否首次注册的 （本地保存）  执行一次后， 这个标记置为false
    public int loginWay; // 1 手机号， 2微信，3qq
    public int userId;
    public String userToken;
    public String userSign;
    public int userRole; //1用户 2大神
    public boolean canInvite; //是否可以输入邀请码 Since V1.7.0
    public boolean manager;//是否为厅管
    public boolean worker;//是否为队长 厅管理
    public String nickName;
    public String userIcon;
    public String userSchool;
    public String userProfession;
    public String userQQ;
    public String userWX;
    public String userHometown;
//    public List<InterestData.LabelListBean> userLabelList;
    public Integer userSex;
    public Integer userStatus;
    public Long userBirthday;
    public int identityAuthStatus; // 0认证中 1认证中 2认证成功 3认证失败
    public UserSensitiveBean userSensitive;
    public boolean relation;//当前用户是否已关注该用户 动态中用到
    public String emId;//环信ID 动态中用到
    public UserStat userStat;
    public UserLevel userCharmLevel;
    public UserLevel userContributionLevel;
    public UserLevel userNobleLevel;
//    public PhoneLoginData.Emchat emchat;
//    public String nickNameColour;//彩昵
//    public UserDetailData.UserNaming userNaming;
//    public PrettyNumberData prettyNumber; //靓号
//    public UserDetailData.UserGiftWidgetDto gift7;//气泡框道具
//    public UserDetailData.UserGiftWidgetDto gift8;//头衔道具
//    public UserDetailData.UserGiftWidgetDto gift9;//挂件道具
//    public UserDetailData.UserGiftWidgetDto gift11;//头像框道具
    public String source = "Android";


    public static class UserSensitiveBean {
        public String userMobile;
    }

    public static class UserLevel {
        public int userId;
        public int userLevel;
        public int userExp;
        public int levelExp;
        public int maxLevel;
        public String levelName;
        public String levelIcon;
        public String levelBg;
    }

    public static class UserStat {
        public int attentionCnt;
        public int fansCnt;
        public UserLuckyStat userLuckyStat;//Since V1.7.2

        public static class UserLuckyStat{

            public int luckyCnt;//Since V1.7.2 用户当前幸运值，如果该值为-1则不显示幸运值信息
            public int luckyMaxCnt;//Since V1.7.2 幸运值最大值,即兑换钻石宝箱所需要的最幸运值
        }


        @Override
        public String toString() {
            return "UserStat{" +
                    "attentionCnt=" + attentionCnt +
                    ", fansCnt=" + fansCnt +
                    ", userLuckyStat=" + userLuckyStat +
                    '}';
        }
    }

    public UserInfo() {
    }


//    public boolean isValid() {
//        return userId > 0 && emchat != null && !TextUtils.isEmpty(emchat.emId);
//    }




}
