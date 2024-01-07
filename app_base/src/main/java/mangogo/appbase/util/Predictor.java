package mangogo.appbase.util;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;

public class Predictor {

    public static boolean isEmpty(Object obj) {
        return obj instanceof CharSequence ? ((CharSequence)obj).length() == 0 : obj == null;
    }

    public static boolean isNotEmpty(Object obj) {
        return obj instanceof CharSequence ? ((CharSequence)obj).length() != 0 : obj != null;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() != 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && map.size() != 0;
    }

    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] arr) {
        return arr != null && arr.length != 0;
    }

    public static boolean equals(Object object1, Object object2) {
        return object1 == null && object2 == null || object1 != null && object2 != null && object1.equals(object2);
    }

    public static boolean isStringEmpty(String obj) {
        if (TextUtils.isEmpty(obj)) {
            return true;
        }

        char c;
        int st = 0;
        int len = obj.length();
        while (st < len) {
            c = obj.charAt(st);
            if (c <= ' ' || c == '　' || c == 160) {
                st++;
            } else {
                return false;
            }
        }
        return true;
    }

    public static int getListSize(Collection collection) {
        return collection != null ? collection.size() : 0;
    }

    public static int getLength(CharSequence charSequence) {
        return charSequence != null ? charSequence.length() : 0;
    }
}
