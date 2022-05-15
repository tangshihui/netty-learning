package com.example.netty.chat.service;

import java.util.HashMap;

public class Database {
    private static HashMap<String,String> users = new HashMap<>();

    static {
        users.put("zhangsan","123");
        users.put("lisi","123");
        users.put("wangwu","123");
    }

    public boolean login(String user,String pwd) {
        if (users.containsKey(user) && users.get(user).equals(pwd)){
            return true;
        }

        return false;
    }
}
