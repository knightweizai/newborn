package com.zaozhuang.newborn.consts;


public interface JumperParam {

    String BABY_SERIAL = "BABY_SERIAL";
    String CHART_INDEX = "CHART_INDEX";//0身长，1体重，2黄疸
    String VIDEO_TITLE = "VIDEO_TITLE";//
    String VIDEO_PATH = "VIDEO_PATH";//


    String USER_IDENTITY_CARD = "userIdentityCard";
    String REAL_NAME = "realName";
    String SIGN_URL = "signUrl";
    String TASK_VALUE = "navigationSoundFlag";
    String INFO = "info";
    String START_DATA = "startData";
    String SHOW_INVITATION = "showInvitation";
    String INVITECODE = "inviteCode";
    String INDEX = "index";
    String PHONE = "phone";
    String TIMESTAMP = "timestamp";

    String WEB_TITLE = "web_title";
    String WEB_CHANNEL = "web_channel";
    String WEB_URL = "web_url";
    String VIDEO_URL = "video_url";
    String WEB_PARAMS = "web_params";
    String USER_ID = "userId";
    String CHAT_ID = "chatId";
    String FLAG_CANCELLATION = "accountCancellation";
    String FLAG_SAY_HI = "sayHi";
    String ORDER_ID = "orderId";
    String USER_SIGN = "userSign";
    String DATA = "data";
    String LOGIN_STATUS = "loginStatus";
    String SOURCE = "source";
    String FLEET_DATA = "fleet";
    String USER_DATA = "userData";
    String SKILL_DATA = "skillData";
    String ECHO_VALUE = "echo_value";
    String POSITION = "position";
    String ROOM_ID = "roomId";
    String GAME_ROOM_ID = "gameRoomId";

    String LEADER = "leader";// 可以查看队长排行的字段，v1.7.1添加
    String DEPART_TIME = "departTime";// 累计发车时长，v1.8.0添加
    String ROOM_CHANNEL_CODE = "roomChannelCode";
    String NEED_HEAD = "needHead";
    String SCHEME_TYPE = "schemeType";
    String GAME_ID = "gameId";
    String CATEGORY_ID = "categoryId";
    String CATEGORY_NAME = "categoryName";

    String FEED_ID = "feedId";
    String COME_SOURCE = "comeSource";

    /**
     * 注销
     */
    String REASON = "reason";
    String REMARKS = "remarks";
    /**
     *
     */
    String REPORT_TYPE = "reportType";
    String TARGET_ID = "targetId";

    String BALANCE_STR = "balanceStr";
    String DIAMOND_STR = "diamondStr";

    int APPLY_JOIN = 40001; //入驻
    int WALLET_TOP_UP = 40002; //充值

    int HOME = 10000; //首页
    int VOICE = 10001; //语音房
    int VOICE_RICH = 10002; //语音房-富豪财富榜
    int MESSAGE = 30000; //消息
    int MINE = 40000; //我的
    int HOME_PAGE = 40003; //用户主页
    int LOGIN = 50000; //登录
    int CLOSE = 60001; //关闭当前页面

    /**
     * 配置ID
     */
    String VIOLATIONS = "1"; //每允许1次违规驾驶所需里程数
    String SHARP_TURN = "2"; //急转阈值
    String SPEED_UP = "3"; //急刹车、急加速阈值
    String MILEAGE = "4"; //行驶里程达标得帮票
    String SECURITY = "5"; //安全驾驶竞赛
    String ALL = "1,2,3,4,5";

}
