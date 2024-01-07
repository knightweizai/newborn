package mangogo.appbase.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;


public final class ReflectUtils {

    public static Field getField(Class clazz, String fieldName) {
        if (clazz != null && Predictor.isNotEmpty(fieldName)) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    return field;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object getFieldValue(Object object, Class clazz, String fieldName) {
        if (object != null && clazz != null && Predictor.isNotEmpty(fieldName)) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    return field.get(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object getFieldValue(Object object, Field field) {
        if (object != null && field != null) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field.get(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void setField(Object object, Field field, Object fieldValue) {
        if (object != null && field != null) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.set(object, fieldValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setField(Object object, Class clazz, String fieldName, Object fieldValue) {
        if (object != null && clazz != null && Predictor.isNotEmpty(fieldName)) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    field.set(object, fieldValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setField(Object object, Class clazz, String fieldName, int fieldValue) {
        if (object != null && clazz != null && Predictor.isNotEmpty(fieldName)) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    field.setInt(object, fieldValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Method getMethod(Class clazz, String methodName, Class<?>... parameterTypes) {
        if (clazz != null && Predictor.isNotEmpty(methodName)) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                if (method != null) {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    return method;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void invoke(Object object, Method method, Object... args) {
        if (object != null && method != null) {
            try {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                method.invoke(object, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int invokeInt(Object object, Method method, Object... args) {
        if (object != null && method != null) {
            try {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return (int)method.invoke(object, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static boolean invokeBoolean(Object object, Method method, Object... args) {
        if (object != null && method != null) {
            try {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return (boolean)method.invoke(object, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 判断type是否是superType的子类型
     *
     * @param type       子类型
     * @param superClass 父类型
     * @return 假如有父子关系，或者子类型实现了父类型的接口，返回true，否则返回false
     */
    public static boolean isSubclassOf(Class<?> type, Class<?> superClass) {
        if (type == null) {
            return false;
        }
        if (type.equals(superClass)) {
            return true;
        }
        Class[] interfaces = type.getInterfaces();
        for (Class i : interfaces) {
            if (isSubclassOf(i, superClass)) {
                return true;
            }
        }
        Class superType = type.getSuperclass();
        return superType != null && isSubclassOf(superType, superClass);
    }

    /**
     * 检查类型是否符合要求
     *
     * @param paramType
     * @param clz       待检查类型
     */
    public static boolean checkGenericType(final Type paramType, final Class clz) {
        if (paramType == null) {
            return false;
        }

        if (!(paramType instanceof ParameterizedType)) {
            return false;
        }

        ParameterizedType pt = (ParameterizedType) paramType;
        Type actual = pt.getActualTypeArguments()[0];
        if (actual instanceof Class) {
            return isSubclassOf((Class)actual, clz);
        } else if (actual instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) actual;
            Type[] typeArray = wildcardType.getLowerBounds();
            if (typeArray != null && typeArray.length != 0) {
                return isSubclassOf((Class) typeArray[0], clz);
            }
            typeArray = wildcardType.getUpperBounds();
            if (typeArray != null && typeArray.length != 0) {
                return isSubclassOf(clz, (Class) typeArray[0]);
            }
        }
        return false;
    }
}
