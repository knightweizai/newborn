package mangogo.appbase.viewmapping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;


public final class ViewMapUtil {

    public static View inflate(Object object, LayoutInflater inflater, ViewGroup root) {
        return inflater.inflate(getViewMapping(object.getClass()).value(), root, false);
    }

    public static View inflate(int object, LayoutInflater inflater, ViewGroup root) {
        return inflater.inflate(object, root, false);
    }

    public static <T> T mapForRecyclerView(
            Context context, Class<T> clazz, ViewGroup parentView) {
        T object = null;
        try {
            int layoutId = getViewMapping(clazz).value();
            View rootView = LayoutInflater.from(context).inflate(
                    layoutId, parentView, false);
            Constructor<T> constructor = clazz.getConstructor(View.class);
            constructor.setAccessible(true);
            object = constructor.newInstance(rootView);
            rootView.setTag(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    private static ViewMapping getViewMapping(Class<?> clazz) {
        return clazz.getAnnotation(ViewMapping.class);
    }
}
