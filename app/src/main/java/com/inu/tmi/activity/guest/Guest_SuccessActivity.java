package com.inu.tmi.activity.guest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.SharedPrefManager;
import com.inu.tmi.activity.MainActivity;
import com.inu.tmi.api.RideMemBody;
import com.inu.tmi.api.TMIServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bmj on 2018-06-08.
 */

public class Guest_SuccessActivity extends AppCompatActivity {
    private static final String TAG = "TMI.TMI_GUEST_Success";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    TextView toolbar_title;
    DrawerLayout drawerLayout;

    ImageButton BACKbtn;
    int room_id;
    ImageView userimg1, userimg2, userimg3, userimg4;
    TextView username1, username2, username3, username4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_success);

        //상단 바 지정
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setLayoutDirection(toolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setTitle(null);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.app_name);  //TMI

        BACKbtn = (ImageButton) findViewById(R.id.Back);
        BACKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guest_SuccessActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //세줄 버튼 눌렀을 때
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        //오른쪽 navaation 화면
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.user:
                        Toast.makeText(getApplicationContext(), "회원 정보 클릭", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.certification:
                        Toast.makeText(getApplicationContext(), "인증하기 클릭", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.blacklist:
                        Toast.makeText(getApplicationContext(), "블랙리스트", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


        //TODO 서버에서 방 참가자에 대한 정보 갖고옴
        //참여한 순서대로  주최자는 1순위
        //

        //room_id = SharedPrefManager.preferencesLoadInt(getApplicationContext(),"roomnum");

        userimg1 = (ImageView) findViewById(R.id.userimage1);
        userimg2 = (ImageView) findViewById(R.id.userimage2);
        userimg3 = (ImageView) findViewById(R.id.userimage3);
        userimg4 = (ImageView) findViewById(R.id.userimage4);

        username1 = (TextView) findViewById(R.id.user1);
        username2 = (TextView) findViewById(R.id.user2);
        username3 = (TextView) findViewById(R.id.user3);
        username4 = (TextView) findViewById(R.id.user4);

        Refresh();

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Refresh();
            }
        });

    }

    public void Refresh() {
        TMIServer.getInstance().rideMem(SharedPrefManager.preferencesLoadString(getApplicationContext(), "token"),  SharedPrefManager.preferencesLoadInt(getApplicationContext(),"roomnum"), new Callback<RideMemBody>() {
            @Override
            public void onResponse(Call<RideMemBody> call, Response<RideMemBody> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, response.body().getMsg());
                    if (response.body() != null) {
                        Log.i(TAG, " : server success - ");
                        RideMemBody rideMemBody = response.body();
                        if (rideMemBody.getMsg().equals("success")) {
                            for (int i = 0; i < rideMemBody.getMemeNum()+1; i++) {
                                if (i == 0) {
                                    userimg1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.userimg1));
                                    username1.setText(rideMemBody.getMemInfo().get(i).getUser_name());
                                }
                                else if (i == 1) {
                                    userimg2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.userimg2));
                                    username2.setText(rideMemBody.getMemInfo().get(i).getUser_name());
                                }
                                else if (i == 2) {
                                    userimg3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.userimg3));
                                    username3.setText(rideMemBody.getMemInfo().get(i).getUser_name());
                                }
                                else {
                                    userimg4.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.userimg4));
                                    username4.setText(rideMemBody.getMemInfo().get(i).getUser_name());
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.i(TAG, " : response fail ");
                    //Log.i(TAG,response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RideMemBody> call, Throwable t) {
                Log.i(TAG, " : ridemem fail - " + t.toString());
            }
            //for(bocy 사이즈 만큼)
            //
        });
    }
}