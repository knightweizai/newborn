package com.zaozhuang.newborn.config;


//import com.zaozhuang.newborn.data.ConfigData;
//import com.zaozhuang.newborn.data.IntegralData;
//import com.zaozhuang.newborn.data.LeftWalletListData;
//import com.zaozhuang.newborn.data.MapData;
//import com.zaozhuang.newborn.data.SettingConfigData;
//import com.zaozhuang.newborn.data.TimeManageData;
//import com.zaozhuang.newborn.data.UserInfo;
//import com.zaozhuang.newborn.data.VoiceData;

import com.zaozhuang.newborn.data.ConfigData;
import com.zaozhuang.newborn.data.UserInfo;
import com.zaozhuang.newborn.db.entity.Baby;
import com.zaozhuang.newborn.db.entity.User;

import mangogo.appbase.Storage;


public interface StorageSchema {

//    Storage<VoiceData> DIRECTION = new Storage<>(VoiceData.class,
//            "com.zaozhuang.newborn.direction");
//
//    Storage<VoiceData> VOICE = new Storage<>(VoiceData.class,
//            "com.zaozhuang.newborn.voice");
//
//
//    Storage<IntegralData> PK_INTEGRAL = new Storage<>(IntegralData.class,
//            "com.zaozhuang.newborn.pkIntegral");
//
//
//    Storage<SettingConfigData> MAP_CONFIG = new Storage<>(SettingConfigData.class,
//            "com.zaozhuang.newborn.mapConfig");
//
//    Storage<MapData> MAP = new Storage<>(MapData.class,
//            "com.zaozhuang.newborn.map");
//
//    Storage<TimeManageData> TIME_MANAGER = new Storage<>(TimeManageData.class,
//            "com.zaozhuang.newborn.timeManager");
//
//    Storage<LeftWalletListData> PRIZE = new Storage<>(LeftWalletListData.class,
//            "com.zaozhuang.newborn.prize");
//
//    Storage<IntegralData> DISTANCE = new Storage<>(IntegralData.class,
//            "com.zaozhuang.newborn.distance");

    Storage<User> USER_INFO = new Storage<>(User.class,
            "com.zaozhuang.newborn.db.entity.user");

    Storage<Baby> BABY_INFO = new Storage<>(Baby.class,
            "com.zaozhuang.newborn.db.entity.baby");

    Storage<String> CHANNEL_NAME = new Storage<>(String.class,
            "com.zaozhuang.newborn.channelname");

    Storage<String> WIFI_MAC = new Storage<>(String.class,
            "com.zaozhuang.newborn.wifi_mac");

    Storage<ConfigData> CONFIG = new Storage<>(ConfigData.class,
            "com.zaozhuang.newborn.config");

    // 搜索历史记录 -- 类型 String 多个字符串以英文逗号为分隔符拼接的一个字符串
    Storage<String> SEARCH_HISTORY = new Storage<>(String.class,
            "com.zaozhuang.newborn.search_history");

    // 创建的车队信息记录 -- 类型 String 多个字符串以英文-为分隔符拼接的一个字符串 每个字符串是一个以英文逗号为分隔符拼接的一个字符串 包含 用户ID，车队名称，车队tags(房间id,区id)
    Storage<String> FLEET_INFO = new Storage<>(String.class,
            "com.zaozhuang.newborn.fleet_info_v2");


    Storage<Boolean> CAN_SEND = new Storage<>(Boolean.class,
            "com.zaozhuang.newborn.canSend");

    Storage<String> CAHAT_ID = new Storage<>(String.class,
            "com.zaozhuang.newborn.chatId");



}
