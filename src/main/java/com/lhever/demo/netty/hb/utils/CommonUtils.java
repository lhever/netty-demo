package com.lhever.demo.netty.hb.utils;

import com.lhever.demo.netty.hb.register.CommonMsg;

public class CommonUtils {


    public  static <T> T getIfMatch(Object obj, Class<T> t) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof CommonMsg) {
            CommonMsg commonMsg = (CommonMsg) obj;
            Object data = commonMsg.getData();
            if (data != null &&  data.getClass() == t) {
                T cast = t.cast(data);
                return cast;
            }
        }
        return null;
    }




}
