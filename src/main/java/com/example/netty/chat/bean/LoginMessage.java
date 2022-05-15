package com.example.netty.chat.bean;


import java.io.Serializable;

public class LoginMessage implements Serializable, Message{
    private String username;
    private String password;

    public LoginMessage(String u, String pwd) {
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
    public String toString() {
        return "LoginMessage{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public int getSequenceId() {
        return 0;
    }
}
