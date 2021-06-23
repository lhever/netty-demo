package com.lhever.demo.netty.hb.register;

public class CommonMsg<T> {

    private String clientId;

    private T data;


    public CommonMsg() {
    }

    public CommonMsg(String clientId, T data) {
        this.data = data;
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <D> CommonMsg<D>  forInstance(D data) {
        CommonMsg<D> commonMsg = new CommonMsg<>();
        commonMsg.setData(data);
        return commonMsg;
    }

    public static <D> CommonMsg<D>  forInstance(String clientId, D data) {
        CommonMsg<D> commonMsg = new CommonMsg<>(clientId, data);
        return commonMsg;
    }
}
