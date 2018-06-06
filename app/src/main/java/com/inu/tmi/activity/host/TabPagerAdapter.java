package com.inu.tmi.activity.host;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by bmj on 2018-06-05.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Context context;

    public TabPagerAdapter(FragmentManager fm, int tabCount, Context context)
    {
        super(fm);
        this.context = context;
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                Host_TabFragment1 host_tabFragment1 = new Host_TabFragment1();
                return host_tabFragment1;
            case 1:
             Host_TabFragment2 host_tabFragment2 = new Host_TabFragment2();
             return host_tabFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
