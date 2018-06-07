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

/**
 * Created by bmj on 2018-06-05.
 */

public class Guest_TabFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.guest_tabfragment1,container,false);

        final Host_TabFragment host_tabFragment1 = new Host_TabFragment();
        host_tabFragment1.setArguments(new Bundle());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.map,host_tabFragment1);
        fragmentTransaction.commit();


        //TODO
        //현재 위치 위도 경도 서버에 보내기

        return view;
    }
}
