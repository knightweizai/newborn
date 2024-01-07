package mangogo.appbase.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.ctb.app_base.R;

//import com.zaozhuang.app_base.R;

import mangogo.appbase.BaseApplication;
import mangogo.appbase.Globals;
import mangogo.appbase.R;


public class ToastUtil {
    private static Toast sToast = null;

    public static void showMessage(final String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void showLongMessage(final String msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    public static void showMessage(final int msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(final String msg,
                                   final int len) {
        showMessage(msg, len, null);
    }

    public static void showMessage(View toastView, final int gravity, final int xOffset, final int yOffset) {
        Context appContext = BaseApplication.getGlobalContext();
        Toast result = new Toast(appContext);

        result.setView(toastView);
        result.setDuration(Toast.LENGTH_LONG);

        showMessage("", Toast.LENGTH_LONG, result, gravity, xOffset, yOffset, 6000);
    }

    public static void showMessage(final String msg,
                                   final int len, final Toast toast) {
        showMessage(msg, len, toast, Gravity.CENTER, 0, 0, 0);
    }

    public static void showMessage(final String msg,
                                   final int len, final Toast toast, final int gravity, final int xOffset, final int yOffset, int time) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            Globals.UI_HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    showMessage(msg, len, toast, gravity, xOffset, yOffset, time);
                }
            });
            return;
        }

        Toast toastTmp = toast;
        if (toast == null) {
            if (TextUtils.isEmpty(msg)) {
                return;
            }

            toastTmp = makeToast(BaseApplication.getGlobalContext().getApplicationContext(), msg, len);
        }

        if (sToast != null) {
            sToast.cancel();
        }

        sToast = toastTmp;
        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();
        if (time != 0) {
            Globals.UI_HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sToast.cancel();
                }
            }, time);
        }
    }

    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    public static void showMessage(final int msg,
                                   final int len) {
        showMessage(BaseApplication.getGlobalContext().getString(msg), len);
    }

    private static Toast makeToast(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        return makeToast(context, context.getResources().getText(resId), duration);
    }

    public static Toast makeToast(Context context, CharSequence text, int duration) {
        Context appContext = context.getApplicationContext();
        Toast result = new Toast(appContext);

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_text, null);
        TextView tv = (TextView) v.findViewById(R.id.toast_message);
        tv.setText(text);
        result.setView(v);
        result.setDuration(duration);
        return result;
    }
}
