package com.inu.tmi.activity.host;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.tmi.R;

/**
 * Created by bmj on 2018-06-05.
 */

public class Host_MakingActivity extends AppCompatActivity {
    private static final String TAG = "TMI.TMI_HOST_Making";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    TextView toolbar_title;
    DrawerLayout drawerLayout;
    Button MtoW;  //from host_making to host_writing btn
    ImageButton BACKbtn;

    public static Fragment fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_making);

        //상단 바 지정
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setLayoutDirection(toolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setTitle(null);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.app_name);  //TMI

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

        BACKbtn = (ImageButton) findViewById(R.id.Back);
        BACKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Host_MakingActivity.this, Host_WritingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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


        final Host_TabFragment host_tabFragment1 = new Host_TabFragment();
        host_tabFragment1.setArguments(new Bundle());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.map, host_tabFragment1);
        fragmentTransaction.commit();

        findViewById(R.id.myLocationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                host_tabFragment1.MarkMyLocation();
            }
        });

        MtoW = (Button) findViewById(R.id.MTOW);
        MtoW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Host_MakingActivity.this, Host_WritingActivity.class);
                intent.putExtra("From", host_tabFragment1.MarkLocation());
                intent.putExtra("startlat",String.valueOf(host_tabFragment1.getPoint().getLatitude()));
                intent.putExtra("startlong",String.valueOf(host_tabFragment1.getPoint().getLongitude()));
                //TODO 일단 현재 주소를 목적지 주소에도 보냄
                intent.putExtra("lastlat",String.valueOf(host_tabFragment1.getPoint().getLatitude()));
                intent.putExtra("lastlong",String.valueOf(host_tabFragment1.getPoint().getLongitude()));

                intent.putExtra("startname",host_tabFragment1.getAddress());
                intent.putExtra("lastname",host_tabFragment1.getAddress());
                startActivity(intent);
                finish();
            }
        });

    }
}