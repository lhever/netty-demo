package com.lhever.demo.netty.hb.register;

import java.util.HashMap;
import java.util.Map;

public class DtoRegister {

    public static final Map<Integer, Class<?>> codeVsClass = new HashMap();
    public static final Map<Class<?>, Integer> classVsCode = new HashMap();


    static {
        register(1, User.class);
    }

    public static void register(Integer code, Class<?> cls) {
        codeVsClass.put(code, cls);
        classVsCode.put(cls, code);
    }


    public static <T> Class<T> getCls(int code) {
        Class<T> aClass = (Class<T>) codeVsClass.get(code);
        return aClass;
    }

    public static int getCode(Class<?> clazz) {
        Integer code = classVsCode.get(clazz);
        return code;
    }


}
