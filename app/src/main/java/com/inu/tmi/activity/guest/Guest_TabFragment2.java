package com.inu.tmi.activity.guest;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.activity.LoginActivity;
import com.inu.tmi.activity.MainActivity;
import com.inu.tmi.api.GuestListBody;
import com.inu.tmi.api.LoginBody;
import com.inu.tmi.api.TMIServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bmj on 2018-06-05.
 */

public class Guest_TabFragment2 extends Fragment {

    private static final String TAG = "TMI.TMIGuest";
    static private ListViewAdapter listViewAdapter;
    static private ListView guestlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.guest_tabfragment2,container,false);

        guestlist = (ListView)view.findViewById(R.id.guest_listview);
        guestlist.setAdapter(listViewAdapter = new ListViewAdapter(view.getContext(),R.layout.guest_tabfragment2));

        //Refresh();

       // listViewAdapter.addListitem(ContextCompat.getDrawable(getContext(),R.drawable.back_arrow),"인천대 ","집가고 싶어요","100"+"M");


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
        //listViewAdapter.addListitem(ContextCompat.getDrawable(getContext(),R.drawable.back_arrow),"인천대 헤헷","집가고 싶어요","100"+"M")
        //TODO
        //서버에서 주최자, 출발지 , 목적지, 위도 경도 갖고와서

        TMIServer.getInstance().listCall(12.22,12.22, new Callback<GuestListBody>() {
            @Override
            public void onResponse(Call<GuestListBody> call, Response<GuestListBody> response) {
                if(response.isSuccessful())
                {
                    Log.i(TAG, response.body().getMsg());
                    if(response.body() != null) {
                        Log.i(TAG, " : server success - ");
                        GuestListBody guestListBody = response.body();
                        if (guestListBody.getMsg().equals("success")) {
                            for(int i=0;i<guestListBody.getResult().size();i++)
                            {
                                listViewAdapter.addListitem(guestListBody.getResult().get(i).getStart_name(),guestListBody.getResult().get(i).getLast_name(),String.valueOf((int)guestListBody.getResult().get(i).getDistance()));
                                listViewAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(getContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Log.i(TAG, " : response fail ");
                    //Log.i(TAG,response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<GuestListBody> call, Throwable t) {
                Log.i(TAG, " : register fail - " + t.toString());
            }
        //for(bocy 사이즈 만큼)
        //
        });
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