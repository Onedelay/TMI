package com.inu.tmi.activity;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inu.tmi.R;
import com.inu.tmi.fragment.Fragment1;

public class RecruitActivity extends FragmentActivity {
    private Fragment1 fragment1;

    private LocationManager locationManager;
    private Location lastLocation;

    private TextView latitude;
    private TextView longitude;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        address = findViewById(R.id.address);

        findViewById(R.id.btnShowMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment1 = new Fragment1();
                fragment1.setArguments(new Bundle());
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.mapParentView, fragment1);
                fragmentTransaction.commit();
            }
        });

        findViewById(R.id.btnMyPOI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                if(lastLocation != null) {
                    latitude.setText("위도 : "+lastLocation.getLatitude());
                    longitude.setText("경도 : "+lastLocation.getLongitude());
                    address.setText("주소는 아직 모름");
                }

                View v = findViewById(R.id.parentView);
                v.invalidate();

                //locationManager.removeUpdates(locationListener);
            }
        });
    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastLocation = location;
            double lat = location.getLatitude();
            double lng = location.getLongitude();
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
}
