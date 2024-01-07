package mangogo.appbase;

import android.app.Application;
import android.content.Intent;

import com.google.gson.Gson;
//import com.tencent.tinker.entry.DefaultApplicationLike;


public abstract  class BaseApplication extends Application implements Globals {

    public static BaseApplication sGlobalApplication;
//    public static BaseApplication sBaseApplication;

//    public BaseApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
//        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
//    }

    public static BaseApplication getGlobalContext() {
        return sGlobalApplication;
    }

//    public static BaseApplication getInstance(){
//        return sBaseApplication;
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        sGlobalApplication = this;
//        sBaseApplication = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public abstract Gson getGson();

    public abstract boolean isPrintLog();

    public abstract void initApplication();
}
