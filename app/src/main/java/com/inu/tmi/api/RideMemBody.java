package com.inu.tmi.api;

import java.util.List;

/**
 * Created by bmj on 2018-06-08.
 */

public class RideMemBody {
    String msg;
    int room_id;
    int memeNum;
    double start_lat;
    double start_long;
    double last_lat;
    double last_long;
    String start_name;
    String last_name;
    List<memInfo> memInfo;

    public class memInfo
    {
        String user_name;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
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

    public int getMemeNum() {
        return memeNum;
    }

    public void setMemeNum(int memeNum) {
        this.memeNum = memeNum;
    }

    public double getStart_lat() {
        return start_lat;
    }

    public void setStart_lat(double start_lat) {
        this.start_lat = start_lat;
    }

    public double getStart_long() {
        return start_long;
    }

    public void setStart_long(double start_long) {
        this.start_long = start_long;
    }

    public double getLast_lat() {
        return last_lat;
    }

    public void setLast_lat(double last_lat) {
        this.last_lat = last_lat;
    }

    public double getLast_long() {
        return last_long;
    }

    public void setLast_long(double last_long) {
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

    public List<RideMemBody.memInfo> getMemInfo() {
        return memInfo;
    }

    public void setMemInfo(List<RideMemBody.memInfo> memInfo) {
        this.memInfo = memInfo;
    }
}
