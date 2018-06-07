package com.inu.tmi.api;

public class GuestListBody {

    String msg;
    int room_id;
    int memNum;
    String start_lat;
    String start_long;
    String last_lat;
    String last_long;
    String start_name;
    String last_name;
    memInfo memInfo;

    public class memInfo
    {
        String user_name;
        String user_img;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getMemNum() {
        return memNum;
    }

    public void setMemNum(int memNum) {
        this.memNum = memNum;
    }

    public String getStart_lat() {
        return start_lat;
    }

    public void setStart_lat(String start_lat) {
        this.start_lat = start_lat;
    }

    public String getStart_long() {
        return start_long;
    }

    public void setStart_long(String start_long) {
        this.start_long = start_long;
    }

    public String getLast_lat() {
        return last_lat;
    }

    public void setLast_lat(String last_lat) {
        this.last_lat = last_lat;
    }

    public String getLast_long() {
        return last_long;
    }

    public void setLast_long(String last_long) {
        this.last_long = last_long;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public GuestListBody.memInfo getMemInfo() {
        return memInfo;
    }

    public void setMemInfo(GuestListBody.memInfo memInfo) {
        this.memInfo = memInfo;
    }
}
