package com.inu.tmi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.activity.guest.Guest_ParticipaintingActivity;

import com.inu.tmi.activity.host.Host_MakingActivity;
import com.inu.tmi.handler.BackPressCloseHandler;

import com.inu.tmi.activity.host.Host_WritingActivity;


public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    private Toolbar toolbar;
    TextView toolbar_title;
    DrawerLayout drawerLayout;
    Button Host;
    Button Guest;

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        //상단 바 지정
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setLayoutDirection(toolbar, ViewCompat.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setTitle(null);
        toolbar_title= (TextView)findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.app_name);  //TMI

        //세줄 버튼 눌렀을 때
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        //오른쪽 navaation 화면
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item.getItemId();
                Intent intent;
                switch (id)
                {
                    case R.id.user:
                        Toast.makeText(getApplicationContext(),"회원 정보 클릭", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.certification:
                        Toast.makeText(getApplicationContext(),"인증하기 클릭", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.blacklist:
                        Toast.makeText(getApplicationContext(),"블랙리스트", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(getApplicationContext(),"로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


        //탈사람 눌렀을 때
        Host = (Button)findViewById(R.id.host);
        Host.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, Host_WritingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //탈래요 눌렀을 때
        Guest = (Button)findViewById(R.id.guest);
        Guest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, Guest_ParticipaintingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
