package mangogo.appbase.jumper;

import static mangogo.appbase.util.ReflectUtils.checkGenericType;
import static mangogo.appbase.util.ReflectUtils.isSubclassOf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class JumperInvokeHandler implements InvocationHandler {

    final Context mContext;
    public JumperInvokeHandler(Context context){
        mContext = context;
    }


    public static final HashMap<Class, Method> INTENT_PUT_EXTRA_MAP = new HashMap<Class, Method>() {
//         扩展类型   暂时用不上
//        final Map<Class, Class> mCastMap = Collections.unmodifiableMap(new HashMap<Class, Class>() {
//            {
//                put(Byte.class, byte.class);
//                put(Byte[].class, byte[].class);
//
//                put(Boolean.class, boolean.class);
//                put(Boolean[].class, boolean[].class);
//
//                put(Character.class, char.class);
//                put(Character[].class, char[].class);
//
//                put(Short.class, short.class);
//                put(Short[].class, short[].class);
//
//                put(Integer.class, int.class);
//                put(Integer[].class, int[].class);
//
//                put(Long.class, long.class);
//                put(Long[].class, long[].class);
//
//                put(Float.class, float.class);
//                put(Float[].class, float[].class);
//
//                put(Double.class, double.class);
//                put(Double[].class, double[].class);
//            }
//        });
//
//        @Override
//        public Method get(final Object key) {
//            Method result = super.get(key);
//            return result == null ? super.get(mCastMap.get(key)) : result;
//        }
    };

    static {
        Method[] methods = Intent.class.getMethods();
        for (Method m : methods) {
            if ("putExtra".equals(m.getName())) {
                INTENT_PUT_EXTRA_MAP.put(m.getParameterTypes()[1], m);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final boolean needReturnIntent = method.getReturnType() == Intent.class;
        final ActivityInfo aInfo = method.getAnnotation(ActivityInfo.class);

        assert aInfo != null;
        final Intent intent = new Intent();
        if (aInfo.clz() != Activity.class) {
            intent.setClass(mContext, aInfo.clz());
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        LinkedList<ParamHolder> holderList = new LinkedList<ParamHolder>();


        Type[] genericTypes = method.getGenericParameterTypes();
        for (int i = 0, n = parameterAnnotations.length; i < n; ++i) {
            if (parameterAnnotations[i] == null) {
                continue;
            }
            final Annotation annotation = parameterAnnotations[i][0];
            if (annotation == null) {
                continue;
            }
            if (annotation instanceof IntentParam) {
                holderList.add(new ParamHolder(parameterAnnotations[i], i, genericTypes[i]));
            }
        }

        for (ParamHolder holder : holderList) {
            Object o = args[holder.index];
            if (o == null) {
                // value为null,表示忽略该字段
                continue;
            }
            final Class<?> clz = o.getClass();
            final String key = holder.key;

            final Method m = INTENT_PUT_EXTRA_MAP.get(clz);
            //intent 本身支持的类型
            if (m != null) {
                m.invoke(intent, key, o);
            } else if (isSubclassOf(clz, List.class)) {
                Type listType = holder.genericType;

                ArrayList list = (ArrayList) o;
                if (checkGenericType(listType, String.class)) {
                    intent.putStringArrayListExtra(key, list);
                } else if (checkGenericType(listType, Integer.class)) {
                    intent.putIntegerArrayListExtra(key, list);
                } else if (checkGenericType(listType, CharSequence.class)) {
                    intent.putCharSequenceArrayListExtra(key, list);
                } else if (checkGenericType(listType, Parcelable.class)) {
                    intent.putParcelableArrayListExtra(key, list);
                } else if (isSubclassOf(clz, Serializable.class)
                        && checkGenericType(listType, Serializable.class)) {
                    intent.putExtra(key, (Serializable)o);
                }
            } else if (isSubclassOf(clz, Parcelable.class)) {   // 优先支持Parcelable
                intent.putExtra(key, (Parcelable) o);
            }  else if (isSubclassOf(clz, Serializable.class)) {
                intent.putExtra(key, (Serializable) o);
            } else if (isSubclassOf(clz, Parcelable[].class)) {
                intent.putExtra(key, (Parcelable[]) o);
            }
        }

        if (needReturnIntent) {
            return intent;
        } else if (method.getReturnType() == BaseIntent.class) {
            return new BaseIntent(intent);
        }

        return null;
    }

    static class ParamHolder {
        String key;
        int index;
        Type genericType;

        ParamHolder(Annotation[] annotations, int index, Type type) {
            for (Annotation a : annotations) {
                if (a instanceof IntentParam) {
                    IntentParam param = (IntentParam) a;
                    this.key = param.value();
                    break;
                }
            }
            this.index = index;
            this.genericType = type;
        }
    }
}
