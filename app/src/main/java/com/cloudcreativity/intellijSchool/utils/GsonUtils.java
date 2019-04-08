package com.cloudcreativity.intellijSchool.utils;

/**
 * Created by Administrator on 2018\4\23
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson解析json的封装类
 * Created by Administrator on 2019/4/4.
 */
public class GsonUtils {

    private static final String TAG = GsonUtils.class.getSimpleName();

    public static <T> T jsonToBean(String json, Class<T> cls) {
        Gson gson = new Gson();
        T t = gson.fromJson(json, cls);
        return t;
    }

    /**
     * 将对象转化为json
     *
     * @return
     */
    public static <T> String beanToJson(T t) {
        Gson g = new Gson();
        String str = g.toJson(t);
        return str;
    }

    //将list集合转化为字符串，这样使用在7.0及以上手机会出错
    private static <T> String listToJson1(List<T> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<T>>() {
        }.getType(); // 指定集合对象属性
        String beanListToJson = gson.toJson(list, type);
        return beanListToJson;
    }

    public static <T> String listToJson(List<T> list) {
        StringBuilder beanListToJson = new StringBuilder();
        beanListToJson.append("[");
        int count = list.size();
        for (int i = 0; i < count; i++) {
            beanListToJson.append(beanToJson(list.get(i)));
            if (i != count - 1) {
                beanListToJson.append(",");
            } else {
                beanListToJson.append("]");
            }
        }
        return beanListToJson.toString();
    }

    /**
     *
     * @param jsonString
     * @param cls
     * @return
     */
    private <T> List<T> jsonFromList(String jsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 成功解决了泛型在编译期类型被擦除导致的问题
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        List<T> lst = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                lst.add(new Gson().fromJson(elem, clazz));
            }
        } catch (Exception e) {
        }
        return lst;
    }

}
