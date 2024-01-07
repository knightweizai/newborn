//package com.ctb.opencar.consts
//
//import android.text.TextUtils
//import com.blankj.utilcode.util.SPUtils
//import com.ctb.opencar.data.room.RoomRequestData
//import com.ctb.opencar.util.GsonUtils.getObject
//import com.ctb.opencar.util.GsonUtils.toObject
//
///**
// *
// * @ProjectName: android_project
// * @Package: com.ctb.sjsVoice.consts
// * @Description:
// * @Author: jc
// * @CreateDate: 2022/7/8 12:48
// * @UpdateUser: 更新者
// * @UpdateDate: 2022/7/8 12:48
// * @UpdateRemark: 更新说明
// * @Version: 1.0
// */
//
//object SpManager {
//
//    private const val SP_APP = "app"
//    private const val SP_USER = "user"
//
//    private const val AVAILABLE_DOMAIN = "available_domain"
//    private const val AVAILABLE_DOMAIN_HOST = "available_domain_host"
//    private const val AVAILABLE_DOMAIN_VERSION = "available_domain_version"
//
//    private const val FIELD_IM_QUICK_REPLY_VERSION = "im_quick_reply_version"
//
//    private const val USER_ROOM_CREATE = "user_room_create"
//
//    fun clearApp(){
//        SPUtils.getInstance(SP_APP).clear()
//    }
//
//    fun clearUser(){
//        SPUtils.getInstance(SP_USER).clear()
//    }
//
//    fun putImReplyVersion(versionCode: Long) {
//        SPUtils.getInstance(SpManager.SP_APP)
//            .put(SpManager.FIELD_IM_QUICK_REPLY_VERSION, versionCode)
//    }
//
//    fun getDomainUrl(): String {
//        return SPUtils.getInstance(SpManager.SP_APP)
//            .getString(SpManager.AVAILABLE_DOMAIN, "")
//    }
//
//    fun putDomainUrl(domainUrl: String) {
//        SPUtils.getInstance(SpManager.SP_APP)
//            .put(SpManager.AVAILABLE_DOMAIN, domainUrl)
//    }
//
//    fun getDomainIp(): String {
//        return SPUtils.getInstance(SpManager.SP_APP)
//            .getString(SpManager.AVAILABLE_DOMAIN_HOST, "")
//    }
//
//    fun putDomainIp(domainHost: String) {
//        SPUtils.getInstance(SpManager.SP_APP)
//            .put(SpManager.AVAILABLE_DOMAIN_HOST, domainHost)
//    }
//
//    fun getDomainVersion(): String {
//        return SPUtils.getInstance(SpManager.SP_APP)
//            .getString(SpManager.AVAILABLE_DOMAIN_VERSION, "")
//    }
//
//    fun putDomainVersion(domainUrl: String) {
//        SPUtils.getInstance(SpManager.SP_APP)
//            .put(SpManager.AVAILABLE_DOMAIN_VERSION, domainUrl)
//    }
//
//    fun getImReplyVersion(): Long {
//        return SPUtils.getInstance(SpManager.SP_APP)
//            .getLong(SpManager.FIELD_IM_QUICK_REPLY_VERSION, 0)
//    }
//
//
//    fun putUserCreateRoomData(data: RoomRequestData) {
//        SPUtils.getInstance(SpManager.SP_USER).put(SpManager.USER_ROOM_CREATE, toObject(data))
//    }
//
//    fun getUserCreateRoomData(): RoomRequestData {
//        val jsonStr =
//            SPUtils.getInstance(SpManager.SP_USER).getString(SpManager.USER_ROOM_CREATE, "")
//        if (!TextUtils.isEmpty(jsonStr)) {
//            try {
//                return getObject(jsonStr, RoomRequestData::class.java);
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//        return RoomRequestData()
//
//    }
//
//}