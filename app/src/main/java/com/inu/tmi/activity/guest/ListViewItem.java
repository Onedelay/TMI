package com.inu.tmi.activity.guest;

import android.graphics.drawable.Drawable;

/**
 * Created by bmj on 2018-06-07.
 */

public class ListViewItem {
    private Drawable UserIcon;
    private String From;
    private String To;
    private String Dst;

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
}
