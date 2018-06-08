package com.inu.tmi.api;

import java.util.List;

public class GuestListBody {

    String msg;  //성공 여부
    List<result> result;

    public class result {
        int room_id;
        String host_name;
        double start_lat;
        double start_long;
        double last_lat;
        double last_long;
        String start_name;
        String last_name;
        double distance;
        String taxi_msg;

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public String getHost_name() {
            return host_name;
        }

        public void setHost_name(String host_name) {
            this.host_name = host_name;
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

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getTaxi_msg() {
            return taxi_msg;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<GuestListBody.result> getResult() {
        return result;
    }

    public void setResult(List<GuestListBody.result> result) {
        this.result = result;
    }
}
