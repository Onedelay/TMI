package com.inu.tmi.activity.guest;

import android.graphics.drawable.Drawable;

/**
 * Created by bmj on 2018-06-07.
 */

public class ListViewItem {
    private Drawable UserIcon;
    private String From;
    private Double start_long;
    private Double start_lati;
    private String To;
    private Double end_long;
    private Double end_lati;
    private String Dst;
    private String content;
    private String name;
    private int roomId;

    public ListViewItem() {
    }

    public ListViewItem(String name, String from, String to, String content, int roomId) {
        From = from;
        To = to;
        this.content = content;
        this.name = name;
        this.roomId = roomId;
    }

    public void setEnd_long(Double end_long) {
        this.end_long = end_long;
    }

    public Double getEnd_lati() {
        return end_lati;
    }

    public void setEnd_lati(Double end_lati) {
        this.end_lati = end_lati;
    }

    public Drawable getUserIcon() {
        return UserIcon;
    }

    public void setUserIcon(Drawable userIcon) {
        UserIcon = userIcon;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getDst() {
        return Dst;
    }

    public void setDst(String dst) {
        Dst = dst;
    }

    public Double getStart_long() {
        return start_long;
    }

    public void setStart_long(Double start_long) {
        this.start_long = start_long;
    }

    public Double getStart_lati() {
        return start_lati;
    }

    public void setStart_lati(Double start_lati) {
        this.start_lati = start_lati;
    }

    public Double getEnd_long() {
        return end_long;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
