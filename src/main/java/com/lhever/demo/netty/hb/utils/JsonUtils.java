package com.lhever.demo.netty.hb.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {

    public static String obj2Json(Object obj, boolean pretty) {
        if (pretty) {
            return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
        }
        return JSON.toJSONString(obj);
    }

    public static String obj2Json(Object obj) {
        return obj2Json(obj, false);
    }

    public static byte[] obj2Byte(Object obj, boolean pretty) {
        if (pretty) {
            return JSON.toJSONBytes(obj, SerializerFeature.PrettyFormat);
        }
        return JSON.toJSONBytes(obj);
    }


    public static byte[] obj2Byte(Object obj) {
        return obj2Byte(obj, false);
    }

    public static <T> T json2Obj(String str, Class<T> clazz) {
        T t = JSON.parseObject(str, clazz);
        return t;
    }


    public static <T> T json2Obj(InputStream is, Class<T> clazz) {
        T t = null;
        try {
            t = (T) JSON.parseObject(is, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
