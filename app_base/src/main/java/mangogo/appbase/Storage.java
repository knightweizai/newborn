package mangogo.appbase;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;

public class Storage<T> implements Globals {
    public static final HashMap<String, Object> CACHE_MAP = new HashMap<String, Object>();
    static final Application APP = BaseApplication.getGlobalContext();
    static SharedPreferences SP = APP.getSharedPreferences("newborn_json", Context.MODE_PRIVATE);

    Class<T> mClass;
    String mKey;

    public Storage(Class<T> clz) {
        this(clz, clz.getName());
    }

    public Storage(Class<T> clz, String key) {
        mClass = clz;
        mKey = key;
    }

    private static synchronized void saveJson(String key, Object o) {

        if (o == null) {
            return;
        }
        CACHE_MAP.put(key, o);
        SP.edit().putString(key, GSON.toJson(o)).apply();
    }

    private static synchronized void saveJsonSync(String key, Object o) {
        if (o == null) {
            return;
        }
        CACHE_MAP.put(key, o);
        SP.edit().putString(key, GSON.toJson(o)).commit();
    }

    public static <T> T getJson(String key, Class<T> clz) {
        Object o = CACHE_MAP.get(key);
        if (o == null) {
            try {
                String value = SP.getString(key, "");
                if (TextUtils.isEmpty(value)) {
                    o = clz.newInstance();
                } else {
                    o = GSON.fromJson(value, clz);
                }
            } catch (Exception e) {
                throw new Error(e);
            }
            CACHE_MAP.put(key, o);
        }
        return (T) o;
    }

    public static synchronized void delete(String key) {
        CACHE_MAP.remove(key);
        SP.edit().remove(key).apply();
    }

    public boolean contains() {
        return SP.contains(mKey);
    }

    public T get() {
        return getJson(mKey, mClass);
    }

    public void save(T t) {
        if (t == null) {
            return;
        }
        saveJson(mKey, t);
    }

    public void saveSync(T t) {
        if (t == null) {
            return;
        }
        saveJsonSync(mKey, t);
    }

    public void delete() {
        delete(mKey);
    }
}
