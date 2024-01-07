package com.zaozhuang.newborn.dialog;

//import com.ctb.opencar.GlobalApplication;
//import com.ctb.opencar.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.zaozhuang.newborn.GlobalApplication;
import com.zaozhuang.newborn.R;
//import com.lxj.xpopup.interfaces.OnCancelListener;
//import com.lxj.xpopup.interfaces.OnConfirmListener;

public class CommonDialog {

    public static void init() {

    }

//    public static void showNotBackDialog(String title, String content, String ok, String cancel, OnConfirmListener okClickListener, OnCancelListener cancelClickListener) {
//        new XPopup.Builder(GlobalApplication.getLatestActivity())
//                .autoOpenSoftInput(false)
//                .isDestroyOnDismiss(true)
//                .dismissOnTouchOutside(false)
//                .dismissOnBackPressed(false)
//                .asConfirm(title, content,
//                        cancel, ok, okClickListener, cancelClickListener, false, R.layout.common_dialog)
//                .show();
//    }
//
    public static void showDialog(String title,String content, String ok, OnConfirmListener okClickListener) {
        new XPopup.Builder(GlobalApplication.getLatestActivity())
                .autoOpenSoftInput(false)
                .isDestroyOnDismiss(true)
                .asConfirm(title, content, "", ok, okClickListener, null, true, R.layout.common_dialog)
                .show();
    }
//
    public static void showDialog(String title, String content, String ok, String cancel, OnConfirmListener okClickListener, OnCancelListener cancelClickListener) {
        new XPopup.Builder(GlobalApplication.getLatestActivity())
                .autoOpenSoftInput(false)
                .isDestroyOnDismiss(true)
                .asConfirm(title, content,
                        cancel, ok, okClickListener, cancelClickListener, false, R.layout.common_dialog)
                .show();
    }
//
//
//    public static void showNoContentDialog(String title, String ok, String cancel, OnConfirmListener okClickListener, OnCancelListener cancelClickListener) {
//        new XPopup.Builder(GlobalApplication.getLatestActivity())
//                .autoOpenSoftInput(false)
//                .isDestroyOnDismiss(true)
//                .asConfirm(title, "",
//                        cancel, ok, okClickListener, cancelClickListener, false, R.layout.common_dialog)
//                .show();
//    }
//
//    public static void showNoContentDialog(String title, String ok, OnConfirmListener okClickListener) {
//        new XPopup.Builder(GlobalApplication.getLatestActivity())
//                .autoOpenSoftInput(false)
//                .isDestroyOnDismiss(true)
//                .asConfirm(title, "",
//                        "", ok, okClickListener, null, true, R.layout.common_dialog)
//                .show();
//    }
//
//    public static void showDialogCantAutoDismiss(String title, String ok, String cancel, OnConfirmListener okClickListener, OnCancelListener cancelClickListener) {
//        new XPopup.Builder(GlobalApplication.getLatestActivity())
//                .autoOpenSoftInput(false)
//                .isDestroyOnDismiss(true)
//                .dismissOnTouchOutside(false)
//                .dismissOnBackPressed(false)
//                .autoDismiss(true)
//                .asConfirm(title, "",
//                        cancel, ok, okClickListener, cancelClickListener, false, R.layout.common_dialog)
//                .show();
//    }
//
//    public static void showNoContentDialog(CharSequence title, String ok, String cancel, OnConfirmListener okClickListener, OnCancelListener cancelClickListener) {
//        new XPopup.Builder(GlobalApplication.getLatestActivity())
//                .autoOpenSoftInput(false)
//                .isDestroyOnDismiss(true)
//                .asConfirm(title, "",
//                        cancel, ok, okClickListener, cancelClickListener, false, R.layout.common_dialog)
//                .show();
//    }
//
//
//    public static void showYoungDialog(OnConfirmListener okClickListener, OnCancelListener cancelClickListener) {
//        new XPopup.Builder(GlobalApplication.getLatestActivity())
//                .autoOpenSoftInput(false)
//                .dismissOnTouchOutside(false)
//                .dismissOnBackPressed(false)
//                .isDestroyOnDismiss(true)
//                .asConfirm("", "为了呵护未成年人健康成长，开车吧OpenCar\n" +
//                                "特别推出青少年模式。该模式下部\n" +
//                                "分功能无法正常使用。请监护人主\n" +
//                                "动选择，并设置监护密码。",
//                        "进入青少年模式", "我知道了", okClickListener, cancelClickListener, false, R.layout.young_dialog)
//                .show();
//    }
//
//    public static void showEggsDialog(String title, String content, String ok, OnConfirmListener okClickListener) {
//        new XPopup.Builder(GlobalApplication.getLatestActivity())
//                .autoOpenSoftInput(false)
//                .dismissOnTouchOutside(false)
//                .isDestroyOnDismiss(true)
//                .asConfirm(title, content, "", ok, okClickListener, null, true, R.layout.common_dialog)
//                .show();
//    }


}
