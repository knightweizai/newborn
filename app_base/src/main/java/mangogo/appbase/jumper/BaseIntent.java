package mangogo.appbase.jumper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;


public class BaseIntent {
    final Intent mIntent;

    public BaseIntent(final Intent intent) {
        this.mIntent = intent;
    }

    public void startActivity(final Context context) {
        context.startActivity(mIntent);
    }

    public void startActivity(final Context context, int enterAnim, int exitAnim) {
        context.startActivity(mIntent);
        if (context instanceof Activity) {
            ((Activity)context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void startActivityForResult(final Activity activity, int requestCode) {
        activity.startActivityForResult(mIntent, requestCode);
    }

    public void startActivityForResult(final Fragment fragment, int requestCode) {
        fragment.startActivityForResult(mIntent, requestCode);
    }

    public BaseIntent addFlag(final int flag) {
        mIntent.addFlags(flag);
        return this;
    }

    public BaseIntent addCategory(final String catalog) {
        mIntent.addCategory(catalog);
        return this;
    }
}
