package mangogo.appbase.util;





import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mangogo.appbase.BaseApplication;


public class JsonUtils {

    public static Object fromMapJson(Map jsonMap, Class<?> clazz) {
        Object object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                String name = field.getName();
                Object value = jsonMap.get(name);
                if (value == null) {
                    continue;
                }

                if (value instanceof Map) {
                    Object ret = fromMapJson((Map) value, field.getType());
                    if (ret != null) {
                        try {
                            field.set(object, ret);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    continue;
                }

                if (value instanceof List) {
                    Type type = field.getGenericType();
                    if (type == null) {
                        continue;
                    }

                    if (type instanceof ParameterizedType) {
                        /* 如果是泛型参数的类型,到泛型里的class类型对象。*/
                        type = ((ParameterizedType)type).getActualTypeArguments()[0];
                        if (type == null) {
                            continue;
                        }
                    }

                    Object ret = fromListJson((List)value, type);
                    if (ret != null) {
                        try {
                            field.set(object, ret);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    continue;
                }

                try {
                    String type = field.getType().toString();

                    if (type.endsWith("short") || type.endsWith("Short")) {
                        if (value instanceof String) {
                            short val = Short.valueOf((String)value);
                            field.setShort(object, val);
                        }
                        continue;
                    }

                    if (type.endsWith("int") || type.endsWith("Integer")) {
                        if (value instanceof String) {
                            int val = Integer.valueOf((String)value);
                            field.setInt(object, val);
                        }
                        continue;
                    }

                    if (type.endsWith("long") || type.endsWith("Long")) {
                        if (value instanceof String) {
                            long val = Long.valueOf((String)value);
                            field.setLong(object, val);
                        }
                        continue;
                    }

                    if (type.endsWith("float") || type.endsWith("Float")) {
                        if (value instanceof String) {
                            float val = Float.valueOf((String)value);
                            field.setFloat(object, val);
                        }
                        continue;
                    }

                    field.set(object, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        return object;
    }

    private static Object fromListJson(List jsonList, Type type) {
        if (!(type instanceof Class)) {
            if (!(type instanceof ParameterizedType)) {
                return null;
            }

            type = ((ParameterizedType)type).getActualTypeArguments()[0];
            if (type == null) {
                return null;
            }
        }

        List list = new ArrayList();
        for (Object item : jsonList) {
            if (item == null) {
                continue;
            }

            if (item instanceof Map && type instanceof Class) {
                Object object = fromMapJson((Map)item, (Class<?>)type);
                if (object != null) {
                    list.add(object);
                }
                continue;
            }

            if (item instanceof List) {
                Object object = fromListJson((List)item, type);
                if (object != null) {
                    list.add(object);
                }
                continue;
            }

            list.add(item);
        }

        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public static Object fromJson(String json, Class clazz1, Class clazz2) {
        if (clazz2 == null) {
            return BaseApplication.GSON.fromJson(json, clazz1);
        }
        Type objectType = type(clazz1, clazz2);
        return BaseApplication.GSON.fromJson(json, objectType);
    }

    public static void e(String msg) {
        int index = 0;
        int maxLength = 1024;

        String subString;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                subString = msg.substring(index);
                LogUtil.e("json", subString);
                return;
            }

            subString = msg.substring(index, index+maxLength);
            index += maxLength;
            LogUtil.e("json", subString);
        }
    }

    private static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
