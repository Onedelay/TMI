package com.inu.tmi.activity.guest;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.inu.tmi.R;

/**
 * Created by bmj on 2018-06-05.
 */

public class Guest_TabFragment2 extends Fragment {

    static private ListViewAdapter listViewAdapter;
    static private ListView guestlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.guest_tabfragment2,container,false);

        guestlist = (ListView)view.findViewById(R.id.guest_listview);
        guestlist.setAdapter(listViewAdapter = new ListViewAdapter(view.getContext(),R.layout.guest_tabfragment2));

        //Refresh();

        listViewAdapter.addListitem(ContextCompat.getDrawable(getContext(),R.drawable.back_arrow),"인천대 ","집가고 싶어요","100"+"M");


        ImageButton refreshbtn = (ImageButton)view.findViewById(R.id.refresh);
        refreshbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Refresh();//

            }
        });
        return view;
    }

    public void Refresh()
    {
        //예시 추가
        listViewAdapter.addListitem(ContextCompat.getDrawable(getContext(),R.drawable.back_arrow),"인천대 헤헷","집가고 싶어요","100"+"M");

        listViewAdapter.notifyDataSetChanged();
        //TODO
        //서버에서 주최자, 출발지 , 목적지, 위도 경도 갖고와서

        //for(bocy 사이즈 만큼)
        //
    }
    public double GetDistance(double latitude_a , double longtitude_a, double latitude_b, double longtitude_b)
    {
        Location start = new Location("A");
        Location end = new Location("B");

        start.setLatitude(latitude_a);
        start.setLongitude(longtitude_a);
        end.setLatitude(latitude_b);
        end.setLongitude(longtitude_b);

        double distance = start.distanceTo(end);

        return distance;
    }
}