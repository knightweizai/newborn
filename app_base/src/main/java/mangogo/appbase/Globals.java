package mangogo.appbase;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import mangogo.appbase.eventbus.EventBus;


public interface Globals {
    Handler UI_HANDLER = new Handler(Looper.getMainLooper());
    Gson GSON = BaseApplication.getGlobalContext().getGson();
    EventBus BUS = new EventBus();
}
