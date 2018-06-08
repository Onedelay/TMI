package com.inu.tmi.activity.guest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.nhn.android.maps.maplib.NGeoPoint;

/**
 * Created by bmj on 2018-06-05.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Context context;

    public TabPagerAdapter(FragmentManager fm, int tabCount, Context context) {
        super(fm);
        this.context = context;
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                Guest_TabFragment1 guest_tabFragment1 = new Guest_TabFragment1();
                return guest_tabFragment1;
            case 1:
                Guest_TabFragment2 guest_tabFragment2 = new Guest_TabFragment2();
                return guest_tabFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
