package com.zaozhuang.newborn.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class DefaultTypeAdapterFactory<T> implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == Float.class || rawType == float.class) {
            return (TypeAdapter<T>) new FloatlNullAdapter();
        }else if (rawType == Integer.class || rawType == int.class){
            return (TypeAdapter<T>) new IntNullAdapter();
        }
        return null;
    }
}
