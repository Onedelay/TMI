package com.inu.tmi.api;

import java.util.List;

public class LoginBody {

    String msg;
    userInfo userInfo;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public userInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(LoginBody.userInfo userInfo) {
        this.userInfo = userInfo;
    }

    public class userInfo{
        String email;
        String user_id;
        String token;


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


}
