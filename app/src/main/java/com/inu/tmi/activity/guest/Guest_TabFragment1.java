package com.inu.tmi.activity.guest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inu.tmi.R;
import com.inu.tmi.activity.host.Host_TabFragment;
import com.nhn.android.maps.maplib.NGeoPoint;

/**
 * Created by bmj on 2018-06-05.
 */

public class Guest_TabFragment1 extends Fragment {
    private NGeoPoint point;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.guest_tabfragment1,container,false);

        final Host_TabFragment host_tabFragment1 = new Host_TabFragment();
        point = host_tabFragment1.getPoint();
        host_tabFragment1.setArguments(new Bundle());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.map,host_tabFragment1);
        fragmentTransaction.commit();

        return view;
    }

    public NGeoPoint getPoint() {
        return point;
    }
}
