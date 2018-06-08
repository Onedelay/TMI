package com.inu.tmi.activity.guest;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.api.GuestListBody;
import com.inu.tmi.api.TMIServer;
import com.inu.tmi.fragment.ApplyFragment;

import java.util.ArrayList;

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

    private ArrayList<ListViewItem> items;

    private double lat;
    private double lng;

    private LocationManager locationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guest_tabfragment2, container, false);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        items = new ArrayList<>();

        guestlist = (ListView) view.findViewById(R.id.guest_listview);
        guestlist.setAdapter(listViewAdapter = new ListViewAdapter(view.getContext(), R.layout.guest_tabfragment2));
        guestlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ApplyFragment fragment = new ApplyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("depart", items.get(i).getFrom());
                bundle.putString("dest", items.get(i).getTo());
                bundle.putString("msg", items.get(i).getContent());
                bundle.putString("name", items.get(i).getName());
                bundle.putInt("roomId", items.get(i).getRoomId());
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "fragment");
            }
        });

        ImageButton refreshbtn = (ImageButton) view.findViewById(R.id.refresh);
        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Refresh(lat, lng);
            }
        });
        return view;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lng = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public void Refresh(double lat, double lng) {
        //TODO
        //서버에서 주최자, 출발지 , 목적지, 위도 경도 갖고와서

        if(items.size() > 0){
            items.clear();
        }

        TMIServer.getInstance().listCall(lat, lng, new Callback<GuestListBody>() {
            @Override
            public void onResponse(Call<GuestListBody> call, Response<GuestListBody> response) {
                listViewAdapter.removeAllData();
                if (response.isSuccessful()) {
                    Log.i(TAG, response.body().getMsg());
                    if (response.body() != null) {
                        Log.i(TAG, " : server success - ");
                        GuestListBody guestListBody = response.body();
                        if (guestListBody.getMsg().equals("success")) {
                            for (int i = 0; i < guestListBody.getResult().size(); i++) {
                                listViewAdapter.addListitem(guestListBody.getResult().get(i).getStart_name(), guestListBody.getResult().get(i).getLast_name(), String.valueOf((int) guestListBody.getResult().get(i).getDistance()));
                                listViewAdapter.notifyDataSetChanged();

                                setItem(guestListBody.getResult().get(i).getHost_name(), guestListBody.getResult().get(i).getStart_name(),
                                        guestListBody.getResult().get(i).getLast_name(), guestListBody.getResult().get(i).getTaxi_msg(), guestListBody.getResult().get(i).getRoom_id());
                            }

                        } else {
                            Toast.makeText(getContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.i(TAG, " : response fail ");
                    //Log.i(TAG,response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<GuestListBody> call, Throwable t) {
                Log.i(TAG, " : register fail - " + t.toString());
            }
        });
    }

    public void setItem(String str1, String str2, String str3, String str4, int roomId) {
        items.add(new ListViewItem(str1, str2, str3, str4, roomId));
    }
}