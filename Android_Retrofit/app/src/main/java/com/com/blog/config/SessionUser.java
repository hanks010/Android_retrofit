package com.com.blog.config;

import com.com.blog.model.User;


public class SessionUser {
    //전역적으로 관리, 어디서든지 담아서 사용
    public static User user;
    public static String token;

}
