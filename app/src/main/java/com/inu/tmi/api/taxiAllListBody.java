package com.inu.tmi.api;

import java.util.List;

/**
 * Created by bmj on 2018-06-08.
 */

public class taxiAllListBody {
    String msg;
    List<result> list;

    public class result{
        int room_id;
        String host_name;
        Double start_lat;
        Double start_long;
        Double last_lat;
        Double last_long;
        String start_name;
        String last_name;
        Double distance;
        Double b;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<result> getList() {
        return list;
    }

    public void setList(List<result> list) {
        this.list = list;
    }
}
