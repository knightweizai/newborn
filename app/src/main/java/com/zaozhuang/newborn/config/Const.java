package com.zaozhuang.newborn.config;


//import com.ctb.opencar.GlobalApplication;
//import com.ctb.opencar.net.Net;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zaozhuang.newborn.GlobalApplication;
import com.zaozhuang.newborn.gson.DefaultTypeAdapterFactory;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import mangogo.appbase.Globals;
import mangogo.appbase.jumper.JumperInvokeHandler;
//import mangogo.appbase.net.ApiInvokeHandler;

public interface Const extends Globals, StorageSchema {
    Gson GSON = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PRIVATE, Modifier.PROTECTED)
            .registerTypeAdapterFactory(new DefaultTypeAdapterFactory())
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

//    Api API = (Api) Proxy.newProxyInstance(Api.class.getClassLoader(), new Class[]{Api.class}, new ApiInvokeHandler(new Net()));
    Jumper JUMPER = (Jumper) Proxy.newProxyInstance(Jumper.class.getClassLoader(), new Class[]{Jumper.class}, new JumperInvokeHandler(GlobalApplication.getGlobalContext()));

    //file
    String ANDROID_DATA = "Android/data/";
    String DATA = "data";
    String CRASH = "crash";
    String LOG = "log";
    String IMG = "img";
    String UPLOAD_IMAGE = "UploadImage";
    String CROP_IMAGE = "cropImage";
    String DOWNLOAD = "CarsDownload";
    String WEBVIEW = "webview";
    String RECORD_PATH = "Record";
    String VOICE_PATH = "Voice";
    String CONFIG_PATH = "config";
    //聊天室自定义图片存储目录
    String RTM_IMAGE_PATH = "RtmImage";
    //svga动画
    String GIFT_SVGA_PATH = "giftSvga";

    String CUSTOMER_SERVICE_ID = "lwmaccount911"; //环信客服id
//    String SYSTEM_ID = "sysaccount911"; //环信系统通知id
    String SYSTEM_ID = "6646099205n1904898425"; //环信系统通知id

    String SIMPLE_URL = "simpleUrl";

    String WX ="WX";
    String QQ ="QQ";

    String SCHEME_ROOM = "room";
    String SCHEME_USER = "homePage";
    String SCHEME_DYNAMIC = "dynamic";

    String HX_PROD_KEY ="1159211102091216#opencar"; //环信线上key
    String HX_TEST_KEY ="1159211102091216#opencar-test"; //环信测试

    String UM_KEY = "631b06ca05844627b5453467";//友盟 APPKey

}
