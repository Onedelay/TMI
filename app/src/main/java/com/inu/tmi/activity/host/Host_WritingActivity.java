package com.inu.tmi.activity.host;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.activity.MainActivity;

/**
 * Created by bmj on 2018-06-05.
 */

public class Host_WritingActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    TextView toolbar_title;
    DrawerLayout drawerLayout;

    EditText pregps; //현재 위치
    EditText dstgps; //목적지
    Button WtoS;
    ImageButton BACKbtn;

    Spinner spinner;
    ImageButton GPS;
    String DST="";

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        DST = spinner.getSelectedItem().toString();
//        outState.putString("Selected",DST);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_writing);

//        if(savedInstanceState != null)
//        {
//            String prespinner = savedInstanceState.getString("Selected");
//            switch (prespinner)
//            {
//                case "인천대 정문":
//                    spinner.setSelection(0);
//                    break;
//                case "인천대 자연대":
//                    spinner.setSelection(1);
//                    break;
//                case "인천대 공대":
//                    spinner.setSelection(2);
//                    break;
//                case "인천대 기숙사":
//                    spinner.setSelection(3);
//                    break;
//                case "인천대 미추홀":
//                    spinner.setSelection(4);
//                    break;
//
//            }
//        }
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

        BACKbtn = (ImageButton)findViewById(R.id.Back);
        BACKbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Host_WritingActivity.this, MainActivity.class);
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


        //Spinner 부분
        spinner = (Spinner)findViewById(R.id.dstspinner);
        ArrayAdapter<CharSequence> splinner_adapter = ArrayAdapter.createFromResource(this,R.array.dst_arrays,android.R.layout.simple_spinner_item);
        splinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(splinner_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner.setPrompt(getResources().getString(R.string.spinner_title));
            }
        });


        //GPS 버튼 클릭 시 위치 구하러
        GPS = (ImageButton)findViewById(R.id.gps);
        GPS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Host_WritingActivity.this,Host_MakingActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        //TMI 생성하는 부분
        WtoS = (Button)findViewById(R.id.WTOS);
        WtoS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(pregps.getText().equals(null) )
                {
                    Toast.makeText(getApplicationContext(),"현재 위치 입력해라", Toast.LENGTH_SHORT).show();
                }
                else if(dstgps.getText().equals(null))
                {
                    Toast.makeText(getApplicationContext(),"목적지 입력해라", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //TODO
                    //db 저장 후 Taxi Mate 만들기
                    Intent intent = new Intent(Host_WritingActivity.this,Host_SuccessActivity.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
