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
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.SharedPrefManager;
import com.inu.tmi.activity.MainActivity;
import com.inu.tmi.api.RoomBody;
import com.inu.tmi.api.TMIServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bmj on 2018-06-05.
 */

public class Host_WritingActivity extends AppCompatActivity {
    private static final String TAG = "TMI.TMI_HOST_Writing";

    private String startlat;
    private String startlong;
    private String lastlat;
    private String lastlong;
    private String startname;
    private String lastname;

    private Toolbar toolbar;
    TextView toolbar_title;
    DrawerLayout drawerLayout;

    TextView pregps; //현재 위치
    TextView dstgps; //목적지
    Button WtoS;
    ImageButton BACKbtn;

    Spinner spinner;
    Spinner commentspinner;
    ImageButton GPS;
    String DST = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.host_writing);

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


        pregps = (TextView) findViewById(R.id.pregps);

        Intent intent = getIntent();
        if (intent != null || !intent.getStringExtra("From").equals("")) {
            pregps.setText(intent.getStringExtra("From"));
            startlat = intent.getStringExtra("startlat");
            startlong = intent.getStringExtra("startlong");
            lastlat = intent.getStringExtra("lastlat");
            lastlong = intent.getStringExtra("lastlong");
            startname = intent.getStringExtra("startname");
            lastname = intent.getStringExtra("lastname");
            if( startname == null) startname = "명주바보";
            if( lastname == null) lastname = "지연바보";

        }

        //Spinner 부분
        spinner = (Spinner) findViewById(R.id.dstspinner);
        final ArrayAdapter<CharSequence> splinner_adapter = ArrayAdapter.createFromResource(this, R.array.dst_arrays, android.R.layout.simple_spinner_item);
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

        //Spinner 부분
        commentspinner = (Spinner) findViewById(R.id.commentspinner);
        final ArrayAdapter<CharSequence> splinner_adapter1 = ArrayAdapter.createFromResource(this, R.array.comment_arrays, android.R.layout.simple_spinner_item);
        splinner_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        commentspinner.setAdapter(splinner_adapter1);
        commentspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                commentspinner.setPrompt(getResources().getString(R.string.spinner_title));
            }
        });


        //GPS 버튼 클릭 시 위치 구하러
        GPS = (ImageButton) findViewById(R.id.gps);
        GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Host_WritingActivity.this, Host_MakingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //TMI 생성하는 부분
        WtoS = (Button) findViewById(R.id.WTOS);
        WtoS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pregps.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "현재 위치 입력해라", Toast.LENGTH_SHORT).show();
                }
                if (spinner.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "목적지 입력해라", Toast.LENGTH_SHORT).show();
                }
                if (!pregps.getText().equals("") && !spinner.getSelectedItem().toString().equals("")) {
                    //TODO

                    String user_token = SharedPrefManager.preferencesLoadString(getApplicationContext(),"token");
                    TMIServer.getInstance().createRoom(user_token,startlat,startlong,lastlat,lastlong,commentspinner.getSelectedItem().toString(),startname,spinner.getSelectedItem().toString(), new Callback<RoomBody>() {

                        @Override
                        public void onResponse(Call<RoomBody> call, Response<RoomBody> response) {
                            if (response.body() != null) {
                                Log.i(TAG, "응답");
                                //db 저장 후 Taxi Mate 만들기
                                Intent intent = new Intent(Host_WritingActivity.this, Host_SuccessActivity.class);
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        }

                        @Override
                        public void onFailure(Call<RoomBody> call, Throwable t) {
                            Log.i(TAG, "실패");
                            Log.i(TAG, t.toString());

                        }
                    });



                }
            }
        });
    }
}
