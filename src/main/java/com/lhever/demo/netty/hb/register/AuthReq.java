package com.lhever.demo.netty.hb.register;

public class AuthReq {

    private String user;
    private String pwd;

    public AuthReq() {
    }

    public AuthReq(String user, String pwd) {
        this.user = user;
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
