package com.example.netty.chat.bean;

public class ServerConfig {

    private String host;
    private int port;

    public ServerConfig(String h, int p){
        this.host = h;
        this.port = p;
    }


    public String getHost() {
        return host;
    }


    public int getPort() {
        return port;
    }
}
