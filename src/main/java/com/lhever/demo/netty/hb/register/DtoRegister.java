package com.lhever.demo.netty.hb.register;

import com.lhever.demo.netty.hb.consts.NettyConstants;

import java.util.HashMap;
import java.util.Map;

public class DtoRegister {

    public static final Map<Integer, Class<?>> codeVsClass = new HashMap();
    public static final Map<Class<?>, Integer> classVsCode = new HashMap();


    static {
        innerRegister(NettyConstants.PING_PONG, PingPong.class);
        innerRegister(NettyConstants.NULL, null);

        register(1, AuthReq.class);
        register(2, AuthResp.class);
        register(3, User.class);
    }

    public static void register(Integer code, Class<?> cls) {
        if (code == null) {
            throw new IllegalArgumentException("code cannot be null");
        }
        if (code == NettyConstants.NULL || code == NettyConstants.PING_PONG) {
            throw new IllegalArgumentException("code illegal");
        }
        if (codeVsClass.containsKey(code) || classVsCode.containsKey(cls)) {
            throw new IllegalArgumentException("conflict");
        }
        codeVsClass.put(code, cls);
        classVsCode.put(cls, code);
    }

    private static void innerRegister(Integer code, Class<?> cls) {
        if (code == null) {
            throw new IllegalArgumentException("code cannot be null");
        }
        if (codeVsClass.containsKey(code) || classVsCode.containsKey(cls)) {
            throw new IllegalArgumentException("conflict");
        }
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
