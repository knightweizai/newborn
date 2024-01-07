package com.zaozhuang.newborn.config;


//import com.ctb.opencar.consts.JumperParam;
//import com.ctb.opencar.data.AppInfoData;
//import com.ctb.opencar.data.CertificationData;
//import com.ctb.opencar.data.HistoryItemData;
//import com.ctb.opencar.data.HomeHotAgoraData;
//import com.ctb.opencar.data.MyOderData;
//import com.ctb.opencar.data.NavigationData;
//import com.ctb.opencar.data.NavigationResultData;
//import com.ctb.opencar.data.OrderDetailsData;
//import com.ctb.opencar.data.chatroom.FleetData;
//import com.ctb.opencar.data.message.MessageExtInfoData;
//import com.ctb.opencar.data.message.MessageSkillLisData;
//import com.ctb.opencar.quickstart.ui.main.SelectGameActivity;
//import com.ctb.opencar.ui.activity.AboutActivity;
//import com.ctb.opencar.ui.activity.AgreementBaseActivity;
//import com.ctb.opencar.ui.activity.CarBShopActivity;
//import com.ctb.opencar.ui.activity.FirstActivity;
//import com.ctb.opencar.ui.activity.FirstGuideActivity;
//import com.ctb.opencar.ui.activity.GameChannelListActivity;
//import com.ctb.opencar.ui.activity.InvitationActivity;
//import com.ctb.opencar.ui.activity.InviteActivity;
//import com.ctb.opencar.ui.activity.MainActivity;
//import com.ctb.opencar.ui.activity.NobleActivity;
//import com.ctb.opencar.ui.activity.SearchActivity;
//import com.ctb.opencar.ui.activity.SplashAdActivity;
//import com.ctb.opencar.ui.activity.StartSelActivity;
//import com.ctb.opencar.ui.activity.TestActivity;
//import com.ctb.opencar.ui.activity.WebViewActivity;
//import com.ctb.opencar.ui.activity.account.AccountSecurityActivity;
//import com.ctb.opencar.ui.activity.account.AuthenticationActivity;
//import com.ctb.opencar.ui.activity.account.BindPhoneActivity;
//import com.ctb.opencar.ui.activity.account.BlackListActivity;
//import com.ctb.opencar.ui.activity.account.CancellationAccountActivity;
//import com.ctb.opencar.ui.activity.account.CancellationPhoneActivity;
//import com.ctb.opencar.ui.activity.account.CancellationReasonActivity;
//import com.ctb.opencar.ui.activity.account.CheckPhoneActivity;
//import com.ctb.opencar.ui.activity.account.EditInfoActivity;
//import com.ctb.opencar.ui.activity.account.LoginBindPhoneActivity;
//import com.ctb.opencar.ui.activity.account.LoginBindPhoneActivity2;
//import com.ctb.opencar.ui.activity.account.TeenagersActivity;
//import com.ctb.opencar.ui.activity.account.ValidationPhoneActivity;
//import com.ctb.opencar.ui.activity.agreement.AccountAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.BewareAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.BlindBoxAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.CancellationAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.DisciplineActivity;
//import com.ctb.opencar.ui.activity.agreement.ManitoAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.PlatformRulesAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.PrivacyAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.PunishRulesAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.RefundAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.TopupAgreementActivity;
//import com.ctb.opencar.ui.activity.agreement.WithdrawalAgreementActivity;
//import com.ctb.opencar.ui.activity.chatroom.ChatRoomActivity;
//import com.ctb.opencar.ui.activity.chatroom.MusicListActivity;
//import com.ctb.opencar.ui.activity.chatroom.PlayMusicActivity;
//import com.ctb.opencar.ui.activity.chatroom.RankingListActivity;
//import com.ctb.opencar.ui.activity.chatroom.RankingListActivity2;
//import com.ctb.opencar.ui.activity.chatroom.ReportActivity;
//import com.ctb.opencar.ui.activity.chatroom.RoomForbidActivity;
//import com.ctb.opencar.ui.activity.chatroom.TeamFleetRoomActivity;
//import com.ctb.opencar.ui.activity.chatroom.TeamGameRoomActivity;
//import com.ctb.opencar.ui.activity.dynamic.DynamicDetailsActivity;
//import com.ctb.opencar.ui.activity.dynamic.DynamicPublishActivity;
//import com.ctb.opencar.ui.activity.enter.ApplyJoinActivity;
//import com.ctb.opencar.ui.activity.enter.ApplyJoinStep2Activity;
//import com.ctb.opencar.ui.activity.enter.ApplyJoinStep2ActivityNew;
//import com.ctb.opencar.ui.activity.enter.CertificationSkillActivity;
//import com.ctb.opencar.ui.activity.enter.CertificationSkillActivityNew;
//import com.ctb.opencar.ui.activity.enter.CertificationSkillCompletectivity;
//import com.ctb.opencar.ui.activity.enter.ChooseSkillsActivity;
//import com.ctb.opencar.ui.activity.enter.ChooseSkillsActivity2;
//import com.ctb.opencar.ui.activity.enter.FillWithDrawActivity;
//import com.ctb.opencar.ui.activity.focus.FocusActivity;
//import com.ctb.opencar.ui.activity.guardian.GuardianActivity;
//import com.ctb.opencar.ui.activity.home.CaptainListActivity;
//import com.ctb.opencar.ui.activity.home.CreateRoomActivity;
//import com.ctb.opencar.ui.activity.home.RoomChannelListActivity;
//import com.ctb.opencar.ui.activity.home.RoomListActivity;
//import com.ctb.opencar.ui.activity.im.MessageSettingActivity;
//import com.ctb.opencar.ui.activity.im.chat.ChatActivity;
//import com.ctb.opencar.ui.activity.im.conversation.MessageActivity;
//import com.ctb.opencar.ui.activity.login.ChooseAvatarActivity;
//import com.ctb.opencar.ui.activity.login.ChooseNameActivity;
//import com.ctb.opencar.ui.activity.login.ChooseSexActivity;
//import com.ctb.opencar.ui.activity.login.LoginActivity;
//import com.ctb.opencar.ui.activity.login.VerificationCodeActivity;
//import com.ctb.opencar.ui.activity.map.EndActivity1;
//import com.ctb.opencar.ui.activity.map.HistoryActivity;
//import com.ctb.opencar.ui.activity.map.HomeMapActivity;
//import com.ctb.opencar.ui.activity.map.HomeMapActivity2;
//import com.ctb.opencar.ui.activity.map.NavigationActivity;
//import com.ctb.opencar.ui.activity.map.NavigationResultActivity;
//import com.ctb.opencar.ui.activity.map.NavigationResultActivity2;
//import com.ctb.opencar.ui.activity.mine.HomePageActivity;
//import com.ctb.opencar.ui.activity.mine.PeopleDressActivity;
//import com.ctb.opencar.ui.activity.mine.PhotoAlbumActivity;
//import com.ctb.opencar.ui.activity.order.MentorOrderDetailsActivity;
//import com.ctb.opencar.ui.activity.order.MyOrderActivity;
//import com.ctb.opencar.ui.activity.order.OrderCancelDetailsActivity;
//import com.ctb.opencar.ui.activity.order.OrderDetailsActivity;
//import com.ctb.opencar.ui.activity.order.OrderListActivity;
//import com.ctb.opencar.ui.activity.order.OrderRefundApplyActivity;
//import com.ctb.opencar.ui.activity.order.OrderRefundDetailsActivity;
//import com.ctb.opencar.ui.activity.order.OrderSettlementActivity;
//import com.ctb.opencar.ui.activity.order.PerfectInformationActivity;
//import com.ctb.opencar.ui.activity.order.audit.IntroductionInfoActivity;
//import com.ctb.opencar.ui.activity.order.audit.VoiceInfoActivity;
//import com.ctb.opencar.ui.activity.setting.MessageTipsActivity;
//import com.ctb.opencar.ui.activity.setting.SettingActivity;
//import com.ctb.opencar.ui.activity.task.ExchangeStoreActivity;
//import com.ctb.opencar.ui.activity.task.MyTaskListActivity;
//import com.ctb.opencar.ui.activity.ticket.TicketGoodsActivity;
//import com.ctb.opencar.ui.activity.wallet.ReplaceWithdrawActivity;
//import com.ctb.opencar.ui.activity.wallet.TransactionRecordsActivity;
//import com.ctb.opencar.ui.activity.wallet.WalletTopUpActivity;
//import com.ctb.opencar.ui.activity.wallet.WithdrawalActivity;

import com.zaozhuang.newborn.consts.JumperParam;
import com.zaozhuang.newborn.ui.activity.BabyAddActivity;
import com.zaozhuang.newborn.ui.activity.BabyKnowledgeActivity2;
import com.zaozhuang.newborn.ui.mine.TouchEventActivity;
import com.zaozhuang.newborn.ui.activity.TransmitActivity;
import com.zaozhuang.newborn.ui.activity.BlueConnectActivity2;
import com.zaozhuang.newborn.ui.activity.ContactActivity;
import com.zaozhuang.newborn.ui.activity.FamilyDoctorActivity;
import com.zaozhuang.newborn.ui.activity.HuangdanInputActivity;
import com.zaozhuang.newborn.ui.activity.KnowledgeActivity;
import com.zaozhuang.newborn.ui.activity.PhoneActivity;
import com.zaozhuang.newborn.ui.activity.SlidingActivity;
import com.zaozhuang.newborn.ui.activity.UserInfoActivity;
import com.zaozhuang.newborn.ui.activity.VideoListActivity;
import com.zaozhuang.newborn.ui.activity.VideoPlayerActivity;
import com.zaozhuang.newborn.ui.activity.VolleyTestActivity;
import com.zaozhuang.newborn.ui.activity.WeightInputActivity;
import com.zaozhuang.newborn.ui.activity.XinlvActivity;
import com.zaozhuang.newborn.ui.activity.XueyaActivity;
import com.zaozhuang.newborn.ui.activity.XueyangActivity;
import com.zaozhuang.newborn.ui.chart.ChartActivity;
import com.zaozhuang.newborn.ui.activity.HeightInputActivity;
import com.zaozhuang.newborn.ui.activity.LoginActivity;
//import com.zaozhuang.newborn.ui.activity.MainActivity;
import com.zaozhuang.newborn.ui.activity.RegisterActivity;

import mangogo.appbase.jumper.ActivityInfo;
import mangogo.appbase.jumper.BaseIntent;
import mangogo.appbase.jumper.IntentParam;

public interface Jumper extends JumperParam {

//    @ActivityInfo(clz = SelectGameActivity.class)
//    BaseIntent selectGame();
//
//    @ActivityInfo(clz = EndActivity1.class)
//    BaseIntent end1(@IntentParam(JumperParam.START_DATA) HistoryItemData startData, @IntentParam(JumperParam.DATA) HistoryItemData data);
//
//
//    @ActivityInfo(clz = SplashAdActivity.class)
//    BaseIntent splashAd(@IntentParam(JumperParam.DATA) AppInfoData.StartupAdBean data);//v1.8.0 新添加的开屏广告页面
//
//    @ActivityInfo(clz = FirstGuideActivity.class)
//    BaseIntent firstGuide();
//
//    @ActivityInfo(clz = StartSelActivity.class)
//    BaseIntent startSel();

//    @ActivityInfo(clz = MainActivity.class)
//    BaseIntent main();

    @ActivityInfo(clz = HeightInputActivity.class)
    BaseIntent heightInput();

    @ActivityInfo(clz = WeightInputActivity.class)
    BaseIntent weightInput();

    @ActivityInfo(clz = HuangdanInputActivity.class)
    BaseIntent huangdanInput();

    @ActivityInfo(clz = RegisterActivity.class)
    BaseIntent register();

    @ActivityInfo(clz = BabyKnowledgeActivity2.class)
    BaseIntent babyKnowledge();
    @ActivityInfo(clz = FamilyDoctorActivity.class)
    BaseIntent familyDoctor();

    @ActivityInfo(clz = XinlvActivity.class)
    BaseIntent xinlv();

    @ActivityInfo(clz = XueyaActivity.class)
    BaseIntent xueya();

    @ActivityInfo(clz = XueyangActivity.class)
    BaseIntent xueyang();

    @ActivityInfo(clz = ChartActivity.class)
    BaseIntent chart();

    @ActivityInfo(clz = VideoListActivity.class)
    BaseIntent videoList();

    @ActivityInfo(clz = VideoPlayerActivity.class)
    BaseIntent videoPlayer(@IntentParam(JumperParam.VIDEO_TITLE) String title,@IntentParam(JumperParam.VIDEO_PATH) String path);

    @ActivityInfo(clz = ChartActivity.class)
    BaseIntent chart(@IntentParam(JumperParam.CHART_INDEX) int chartIndex);

    @ActivityInfo(clz = LoginActivity.class)
    BaseIntent login();

    @ActivityInfo(clz = UserInfoActivity.class)
    BaseIntent userInfo();

    @ActivityInfo(clz = BabyAddActivity.class)
    BaseIntent babyAdd();

    @ActivityInfo(clz = TransmitActivity.class)
    BaseIntent transmit();

    @ActivityInfo(clz = BlueConnectActivity2.class)
    BaseIntent blueConnect2();

    @ActivityInfo(clz = SlidingActivity.class)
    BaseIntent sliding();

    @ActivityInfo(clz = VolleyTestActivity.class)
    BaseIntent volleyTest();

    @ActivityInfo(clz = TouchEventActivity.class)
    BaseIntent touchEventTest();

    @ActivityInfo(clz = KnowledgeActivity.class)
    BaseIntent knowledge();

    @ActivityInfo(clz = PhoneActivity.class)
    BaseIntent phone();

    @ActivityInfo(clz = ContactActivity.class)
    BaseIntent contact();

    @ActivityInfo(clz = BabyAddActivity.class)
    BaseIntent babyAdd(@IntentParam(JumperParam.BABY_SERIAL) String serial);




//    @ActivityInfo(clz = HomeMapActivity.class)
//    BaseIntent homeMapActivity();
//
//    @ActivityInfo(clz = HomeMapActivity2.class)
//    BaseIntent homeMapActivity2();
//
//    @ActivityInfo(clz = LoginActivity.class)
//    BaseIntent login();
//
//    @ActivityInfo(clz = ChooseSexActivity.class)
//    BaseIntent chooseSex();
//
//    @ActivityInfo(clz = ChooseAvatarActivity.class)
//    BaseIntent chooseAvatar();
//
//    @ActivityInfo(clz = ChooseNameActivity.class)
//    BaseIntent chooseName();
//
//    @ActivityInfo(clz = VerificationCodeActivity.class)
//    BaseIntent verificationCode(@IntentParam(JumperParam.INVITECODE) String inviteCode, @IntentParam(JumperParam.PHONE) String phone);
//
//    @ActivityInfo(clz = WebViewActivity.class)
//    BaseIntent webview(@IntentParam(JumperParam.WEB_TITLE) String webTitle,
//                       @IntentParam(JumperParam.WEB_URL) String webUrl);
//
//    @ActivityInfo(clz = ApplyJoinActivity.class)
//    BaseIntent applyJoin(@IntentParam(JumperParam.WEB_URL) String webUrl);
//
//    @ActivityInfo(clz = ApplyJoinStep2Activity.class)
//    BaseIntent applyJoinStep2();
//
//    @ActivityInfo(clz = ApplyJoinStep2Activity.class)
//    BaseIntent applyJoinStep2(@IntentParam(JumperParam.DATA) CertificationData.UserAuthTutorData data);
//
//    @ActivityInfo(clz = ApplyJoinStep2ActivityNew.class)
//    BaseIntent applyJoinStep2New();
//
//    @ActivityInfo(clz = ApplyJoinStep2ActivityNew.class)
//    BaseIntent applyJoinStep2New(@IntentParam(JumperParam.DATA) CertificationData.UserAuthTutorData data);
//
//    @ActivityInfo(clz = FillWithDrawActivity.class)
//    BaseIntent applyWithDraw();
//
//    @ActivityInfo(clz = FillWithDrawActivity.class)
//    BaseIntent applyWithDraw(@IntentParam(JumperParam.SIGN_URL) String signUrl, @IntentParam(JumperParam.REAL_NAME) String realName,
//                             @IntentParam(JumperParam.USER_IDENTITY_CARD) String userIdentityCard,
//                             @IntentParam(JumperParam.DATA) CertificationData.UserAuthTutorData data);
//
//    @ActivityInfo(clz = WebViewActivity.class)
//    BaseIntent webview(
//            @IntentParam(JumperParam.WEB_CHANNEL) String WEB_CHANNEL,
//            @IntentParam(JumperParam.WEB_TITLE) String webTitle,
//            @IntentParam(JumperParam.WEB_URL) String webUrl,
//            @IntentParam(JumperParam.WEB_PARAMS) String webParams);
//
//    @ActivityInfo(clz = WebViewActivity.class)
//    BaseIntent webviewChannel(@IntentParam(JumperParam.WEB_CHANNEL) String WEB_CHANNEL,
//                              @IntentParam(JumperParam.WEB_URL) String webUrl);
//
//    @ActivityInfo(clz = SettingActivity.class)
//    BaseIntent setting();
//
//    @ActivityInfo(clz = MessageTipsActivity.class)
//    BaseIntent messageTips();
//
//    @ActivityInfo(clz = AuthenticationActivity.class)
//    BaseIntent authentication();
//
//    @ActivityInfo(clz = CheckPhoneActivity.class)
//    BaseIntent checkPhone(@IntentParam(JumperParam.PHONE) String phone);
//
//    @ActivityInfo(clz = BindPhoneActivity.class)
//    BaseIntent bindPhone(@IntentParam(JumperParam.TIMESTAMP) long timestamp);
//
//    @ActivityInfo(clz = LoginBindPhoneActivity.class)
//    BaseIntent loginBindPhone(@IntentParam(JumperParam.DATA) Object data, @IntentParam(JumperParam.LOGIN_STATUS) int loginStatus, @IntentParam(JumperParam.SOURCE) String source, @IntentParam(JumperParam.USER_ID) int userId);
//
//    @ActivityInfo(clz = LoginBindPhoneActivity2.class)
//    BaseIntent loginBindPhone2(@IntentParam(JumperParam.DATA) Object data, @IntentParam(JumperParam.LOGIN_STATUS) int loginStatus, @IntentParam(JumperParam.SOURCE) String source, @IntentParam(JumperParam.USER_ID) int userId);
//
//
//    @ActivityInfo(clz = ValidationPhoneActivity.class)
//    BaseIntent validationPhone(@IntentParam(JumperParam.PHONE) String phone);
//
//    @ActivityInfo(clz = BlackListActivity.class)
//    BaseIntent blackList();
//
//    @ActivityInfo(clz = TeenagersActivity.class)
//    BaseIntent teenagers();
//
//    @ActivityInfo(clz = EditInfoActivity.class)
//    BaseIntent editInfo();
//
//    @ActivityInfo(clz = FirstActivity.class)
//    BaseIntent first();
//
//
//    /**
//     * 测试
//     */
//    @ActivityInfo(clz = TestActivity.class)
//    BaseIntent testActivity(@IntentParam(JumperParam.USER_SIGN) String userSign);
//
//
//    /**
//     * 1、
//     * 行为规范及处罚规则
//     *
//     * @return
//     */
//    @ActivityInfo(clz = PunishRulesAgreementActivity.class)
//    BaseIntent punishRulesAgreementActivity();
//
//    /**
//     * 2、
//     * 常见诈骗类型
//     *
//     * @return
//     */
//    @ActivityInfo(clz = BewareAgreementActivity.class)
//    BaseIntent bewareAgreementActivity();
//
//    /**
//     * 3、
//     * 社区及动态规范
//     *
//     * @return
//     */
//    @ActivityInfo(clz = PlatformRulesAgreementActivity.class)
//    BaseIntent platformRulesAgreementActivity();
//
//    /**
//     * 4、
//     * 用户协议
//     *
//     * @return
//     */
//    @ActivityInfo(clz = AccountAgreementActivity.class)
//    BaseIntent accountAgreement();
//
//    @ActivityInfo(clz = ManitoAgreementActivity.class)
//    BaseIntent manitoAgreement();
//
//    /**
//     * 5、
//     * 隐私协议
//     *
//     * @return
//     */
//    @ActivityInfo(clz = PrivacyAgreementActivity.class)
//    BaseIntent privacyAgreement();
//
//    @ActivityInfo(clz = TopupAgreementActivity.class)
//    BaseIntent topUpAgreement();
//
//    @ActivityInfo(clz = BlindBoxAgreementActivity.class)
//    BaseIntent blindBoxAgreement();
//
//    @ActivityInfo(clz = DisciplineActivity.class)
//    BaseIntent discipline();
//
//    @ActivityInfo(clz = CancellationAgreementActivity.class)
//    BaseIntent cancellationAgreement();
//
//    @ActivityInfo(clz = NobleActivity.class)
//    BaseIntent noble();
//
//    @ActivityInfo(clz = CancellationReasonActivity.class)
//    BaseIntent cancellationReason();
//
//    @ActivityInfo(clz = CancellationPhoneActivity.class)
//    BaseIntent cancellationPhone(@IntentParam(JumperParam.REASON) String reason, @IntentParam(JumperParam.REMARKS) String remarks);
//
//
////    @ActivityInfo(clz = PermissionsActivity.class)
////    BaseIntent permissions();
//
//
//    @ActivityInfo(clz = AgreementBaseActivity.class)
//    BaseIntent agreementBase();
//
//    @ActivityInfo(clz = RefundAgreementActivity.class)
//    BaseIntent refundAgreement();
//
//    @ActivityInfo(clz = ChatActivity.class)
//    BaseIntent chat(@IntentParam(JumperParam.CHAT_ID) String chatId);
//
////    @ActivityInfo(clz = ChatActivity.class)
////    BaseIntent chat(@IntentParam(JumperParam.CHAT_ID) String chatId, @IntentParam(JumperParam.FLAG_CANCELLATION) String flagAccountCancellation);
//
//    @ActivityInfo(clz = ChatActivity.class)
//    BaseIntent chat(@IntentParam(JumperParam.CHAT_ID) String chatId, @IntentParam(JumperParam.FLAG_SAY_HI) String flagSayHi);
//
//    @ActivityInfo(clz = MessageActivity.class)
//    BaseIntent message();
//
//    @ActivityInfo(clz = HomePageActivity.class)
//    BaseIntent homePage(@IntentParam(JumperParam.USER_ID) long userId);
//
//    @ActivityInfo(clz = AboutActivity.class)
//    BaseIntent about();
//
//    @ActivityInfo(clz = CancellationAccountActivity.class)
//    BaseIntent cancellationAccount();
//
//    @ActivityInfo(clz = AccountSecurityActivity.class)
//    BaseIntent accountSecurity();
//
//    @ActivityInfo(clz = MyTaskListActivity.class)
//    BaseIntent myTaskList();
//
//    @ActivityInfo(clz = ExchangeStoreActivity.class)
//    BaseIntent exchangeStore();
//
//    @ActivityInfo(clz = WalletTopUpActivity.class)
//    BaseIntent walletTopUp();
//
//    @ActivityInfo(clz = WalletTopUpActivity.class)
//    BaseIntent walletTopUp(@IntentParam(JumperParam.ROOM_ID) long roomId);
//
//    @ActivityInfo(clz = GuardianActivity.class)
//    BaseIntent guardian();
//
//    @ActivityInfo(clz = ChooseSkillsActivity.class)
//    BaseIntent chooseSkills();
//
//    @ActivityInfo(clz = ChooseSkillsActivity2.class)
//    BaseIntent chooseSkillsNew();
//
//    @ActivityInfo(clz = CertificationSkillActivity.class)
//    BaseIntent certificationSkill(@IntentParam(JumperParam.DATA) MyOderData.Skill data);
//
//    @ActivityInfo(clz = CertificationSkillActivityNew.class)
//    BaseIntent certificationSkillNew(@IntentParam(JumperParam.DATA) MyOderData.Skill data);
//
//    @ActivityInfo(clz = CertificationSkillCompletectivity.class)
//    BaseIntent certificationSkillComplete();
//
//    @ActivityInfo(clz = PhotoAlbumActivity.class)
//    BaseIntent photoAlbum();
//
//    @ActivityInfo(clz = PeopleDressActivity.class)
//    BaseIntent peopleDress();
//
//    @ActivityInfo(clz = TicketGoodsActivity.class)
//    BaseIntent ticketGoods();
//
//    @ActivityInfo(clz = MyOrderActivity.class)
//    BaseIntent myOrder();
//
//    @ActivityInfo(clz = IntroductionInfoActivity.class)
//    BaseIntent introductionInfo(@IntentParam(JumperParam.DATA) MyOderData.SkillTemplateData data, @IntentParam(JumperParam.INDEX) int index, @IntentParam(JumperParam.POSITION) int position);
//
//    @ActivityInfo(clz = VoiceInfoActivity.class)
//    BaseIntent voiceInfo(@IntentParam(JumperParam.DATA) MyOderData.SkillTemplateData data, @IntentParam(JumperParam.INDEX) int index, @IntentParam(JumperParam.POSITION) int position);
//
//    @ActivityInfo(clz = PerfectInformationActivity.class)
//    BaseIntent perfectInformation(@IntentParam(JumperParam.DATA) MyOderData data, @IntentParam(JumperParam.INDEX) int index);
//
//    @ActivityInfo(clz = OrderListActivity.class)
//    BaseIntent orderList();
//
//    @ActivityInfo(clz = OrderSettlementActivity.class)
//    BaseIntent orderSettlement(@IntentParam(JumperParam.USER_DATA) MessageExtInfoData userData, @IntentParam(JumperParam.SKILL_DATA) MessageSkillLisData.TutorSkill skillData);
//
//    @ActivityInfo(clz = MentorOrderDetailsActivity.class)
//    BaseIntent mentorOrderDetails(@IntentParam(JumperParam.ORDER_ID) String orderId);
//
//    @ActivityInfo(clz = OrderDetailsActivity.class)
//    BaseIntent orderDetails(@IntentParam(JumperParam.ORDER_ID) String orderId);
//
//    @ActivityInfo(clz = OrderRefundApplyActivity.class)
//    BaseIntent orderRefundApply(@IntentParam(JumperParam.DATA) OrderDetailsData.OrderInfo data);
//
//    @ActivityInfo(clz = OrderRefundDetailsActivity.class)
//    BaseIntent orderRefundDetails(@IntentParam(JumperParam.ORDER_ID) String orderId);
//
//    @ActivityInfo(clz = OrderCancelDetailsActivity.class)
//    BaseIntent orderCancelDetails(@IntentParam(JumperParam.ORDER_ID) String orderId);
//
//    @ActivityInfo(clz = SearchActivity.class)
//    BaseIntent search();
//
//    @ActivityInfo(clz = RoomForbidActivity.class)
//    BaseIntent roomForbid(@IntentParam(JumperParam.ROOM_ID) long roomId);
//
//    @ActivityInfo(clz = TransactionRecordsActivity.class)
//    BaseIntent transactionRecords();
//
//    @ActivityInfo(clz = ChatRoomActivity.class)
//    BaseIntent startChatRoom();
//
//    @ActivityInfo(clz = ChatRoomActivity.class)
//    BaseIntent startChatRoom(@IntentParam(JumperParam.DATA) HomeHotAgoraData.MainList data);
//
//    @ActivityInfo(clz = ChatRoomActivity.class)
//    BaseIntent startChatRoom(@IntentParam(JumperParam.DATA) HomeHotAgoraData.MainList data, @IntentParam(JumperParam.FLEET_DATA) FleetData fleetData);
//
//    @ActivityInfo(clz = ChatRoomActivity.class)
//    BaseIntent startChatRoom(@IntentParam(JumperParam.USER_SIGN) String userSign, @IntentParam(JumperParam.DATA) HomeHotAgoraData.MainList data, @IntentParam(JumperParam.FLEET_DATA) FleetData fleetData);
//
//    //
//
//    @ActivityInfo(clz = TeamFleetRoomActivity.class)
//    BaseIntent startTeamFleetRoom(@IntentParam(JumperParam.LEADER) boolean leader);
//
//    @ActivityInfo(clz = TeamGameRoomActivity.class)
//    BaseIntent startTeamGameRoom(@IntentParam(JumperParam.LEADER) boolean leader,@IntentParam(JumperParam.ROOM_ID) long roomId);
//
//    @ActivityInfo(clz = RankingListActivity.class)
//    BaseIntent startRanking(@IntentParam(JumperParam.ROOM_ID) long rooId);
//
//    @ActivityInfo(clz = RankingListActivity2.class)
//    BaseIntent startRanking2(@IntentParam(JumperParam.ROOM_ID) long rooId, @IntentParam(JumperParam.INDEX) int index);
//
//    @ActivityInfo(clz = InviteActivity.class)
//    BaseIntent invite(@IntentParam(JumperParam.WEB_URL) String webUrl);
//
//    @ActivityInfo(clz = CarBShopActivity.class)
//    BaseIntent carBShop(@IntentParam(JumperParam.WEB_URL) String webUrl);
//
//    // 邀请页面 -- v1.5.0 添加 邀请码规则
//    @ActivityInfo(clz = InvitationActivity.class)
//    BaseIntent invitation(@IntentParam(JumperParam.WEB_URL) String webUrl);
//
//    @ActivityInfo(clz = FocusActivity.class)
//    BaseIntent focus(@IntentParam(JumperParam.INDEX) int index);
//
//    /**
//     * 举报页面
//     *
//     * @param reportType 1直播间举报 2扩列举报 默认用户举报0
//     * @param targetId   举报id 可能是 房间id 用户id 扩列 id
//     * @return
//     */
//    @ActivityInfo(clz = ReportActivity.class)
//    BaseIntent report(@IntentParam(JumperParam.REPORT_TYPE) int reportType, @IntentParam(JumperParam.TARGET_ID) long targetId);
//
//    @ActivityInfo(clz = WithdrawalActivity.class)
//    BaseIntent withdrawal();
//
//    @ActivityInfo(clz = ReplaceWithdrawActivity.class)
//    BaseIntent replaceWithdrawal(@IntentParam(JumperParam.BALANCE_STR) String balanceStr, @IntentParam(JumperParam.DIAMOND_STR) String diamondStr);
//
//    @ActivityInfo(clz = WithdrawalAgreementActivity.class)
//    BaseIntent withdrawalAgreement();
//
//    @ActivityInfo(clz = MessageSettingActivity.class)
//    BaseIntent messageSetting(@IntentParam(JumperParam.CHAT_ID) String chatId);
//
//    @ActivityInfo(clz = PlayMusicActivity.class)
//    BaseIntent startPlayMusic();
//
//    @ActivityInfo(clz = MusicListActivity.class)
//    BaseIntent startSelectMusic();
//
//    //某分类游戏列表页
//    // 由 开黑 上面的 横向 游戏分类的 标签图片 适配器 条目 点击 跳转
//    @ActivityInfo(clz = GameChannelListActivity.class)
//    BaseIntent startGameChannel(@IntentParam(JumperParam.GAME_ID) String gameId);
//
//    //某房间分类列表页
//    @ActivityInfo(clz = RoomChannelListActivity.class)
//    BaseIntent starRoomChannel(@IntentParam(JumperParam.CATEGORY_ID) int categoryId, @IntentParam(JumperParam.CATEGORY_NAME) String categoryName);
//
//
//    @ActivityInfo(clz = RoomListActivity.class)
//    BaseIntent roomList();
//
//    @ActivityInfo(clz = CaptainListActivity.class)
//    BaseIntent captainList();
//
//
//    //创建房间页
//    @ActivityInfo(clz = CreateRoomActivity.class)
//    BaseIntent startCreateRoom();
//
//    //帖子发布页
//    @ActivityInfo(clz = DynamicPublishActivity.class)
//    BaseIntent DynamicPublish();
//
//    //帖子发布页
//    @ActivityInfo(clz = DynamicDetailsActivity.class)
//    BaseIntent DynamicDetails(@IntentParam(JumperParam.COME_SOURCE) int comeSource, @IntentParam(JumperParam.FEED_ID) long feedId);
//
//
//    @ActivityInfo(clz = HistoryActivity.class)
//    BaseIntent history(@IntentParam(JumperParam.START_DATA) HistoryItemData startData);
//
//    @ActivityInfo(clz = NavigationActivity.class)
//    BaseIntent navigation(@IntentParam(JumperParam.START_DATA) HistoryItemData startData, @IntentParam(JumperParam.DATA) HistoryItemData data, @IntentParam(JumperParam.INFO) NavigationData info);
//
//    @ActivityInfo(clz = NavigationActivity.class)
//    BaseIntent navigation(@IntentParam(JumperParam.START_DATA) HistoryItemData startData, @IntentParam(JumperParam.DATA) HistoryItemData data);
//
//    @ActivityInfo(clz = NavigationResultActivity.class)
//    BaseIntent navigationResult(@IntentParam(JumperParam.DATA) NavigationResultData data);
//
//    @ActivityInfo(clz = NavigationResultActivity2.class)
//    BaseIntent navigationResult2(@IntentParam(JumperParam.DATA) NavigationResultData data);
}
