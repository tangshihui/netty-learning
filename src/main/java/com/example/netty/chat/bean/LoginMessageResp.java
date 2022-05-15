package com.example.netty.chat.bean;


import java.io.Serializable;

public class LoginMessageResp implements Serializable, Message{
    private boolean success;
    private String username;
    private String password;

    public LoginMessageResp(String u, String pwd) {
        this.username = u;
        this.password = pwd;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public int getSequenceId() {
        return 0;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
