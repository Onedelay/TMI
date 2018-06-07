package com.inu.tmi.activity.host;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inu.tmi.R;
import com.inu.tmi.map.NMapFragment;


/**
 * Created by bmj on 2018-06-05.
 */

public class Host_TabFragment extends NMapFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.host_tabfragment1,container,false);

        return view;
    }


}
