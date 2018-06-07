package com.inu.tmi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.SharedPrefManager;
import com.inu.tmi.api.LoginBody;
import com.inu.tmi.api.TMIServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by bmj on 2018-06-05.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TMI.TMILoginActivity";
    public static final int REQUEST_LOGIN = 100;

    EditText ID;
    EditText PW;
    Button LoginBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        permissionCheck();

        ID = (EditText)findViewById(R.id.IDtxt);
        PW = (EditText)findViewById(R.id.PWtxt);
        final CheckBox autoLogin = (CheckBox)findViewById(R.id.checkBox);

        //자동로그인 할때
        if(SharedPrefManager.preferencesLoadBoolean(this,"AutoLogin")) {
            ID.setText(SharedPrefManager.preferencesLoadString(this, "id"));
            PW.setText(SharedPrefManager.preferencesLoadString(this, "pass"));

            Log.i("info : login", "auto login");

            TMIServer.getInstance().login(ID.getText().toString(), PW.getText().toString(), new Callback<LoginBody>() {
                @Override
                public void onResponse(Call<LoginBody> call, Response<LoginBody> response) {
                    if(response.isSuccessful())
                    {
                        Log.i(TAG, response.body().getMsg());
                        if(response.body() != null) {
                            Log.i(TAG, " : signup success - ");
                            LoginBody loginBody = response.body();

                            if (loginBody.getMsg().equals("email not validated") || loginBody.getMsg().equals("success")) {
                                //로그인 성공 시 메인 액티비티로 intent
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<LoginBody> call, Throwable t) {
                    Log.i(TAG, " : register fail - " + t.toString());
                }
            });

        }
        else { //자동로그인 안할때

            ID.setNextFocusDownId(R.id.IDtxt);
            PW.setNextFocusDownId(R.id.PWtxt);

            PW.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        Log.i("info : login","no auto login");
                        TMIServer.getInstance().login(ID.getText().toString(), PW.getText().toString(), new Callback<LoginBody>() {
                            @Override
                            public void onResponse(Call<LoginBody> call, Response<LoginBody> response) {
                                if(response.isSuccessful())
                                {
                                    Log.i(TAG, response.body().getMsg());
                                    if(response.body() != null) {
                                        Log.i(TAG, " : signup success - ");
                                        LoginBody loginBody = response.body();

                                        if (loginBody.getMsg().equals("email not validated") || loginBody.getMsg().equals("success")) {
                                            //로그인 성공 시 메인 액티비티로 intent
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
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
                            public void onFailure(Call<LoginBody> call, Throwable t) {
                                Log.i(TAG, " : register fail - " + t.toString());
                            }
                        });

                        return true;
                    }
                    return false;
                }
            });



        }



        findViewById(R.id.loginbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID.getText().toString().equals("") )
                {
                    Toast.makeText(getApplicationContext(),"ID 입력해라", Toast.LENGTH_SHORT).show();
                }
                else if(PW.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"PW 입력해라", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.i(TAG, " : login  - id : " + ID.getText().toString()+" pw : "+ PW.getText().toString());

                    TMIServer.getInstance().login(ID.getText().toString(), PW.getText().toString(), new Callback<LoginBody>() {
                        @Override
                        public void onResponse(Call<LoginBody> call, Response<LoginBody> response) {
                            if(response.isSuccessful())
                            {
                                Log.i(TAG, response.body().getMsg());
                                if(response.body() != null) {
                                    Log.i(TAG, " : signup success - ");
                                    LoginBody loginBody = response.body();

                                    if (loginBody.getMsg().equals("email not validated") || loginBody.getMsg().equals("success")) {
                                        //자동로그인
                                        if(autoLogin.isChecked()) {
                                            Log.i("info : ","autoLogin Check");
                                            AutoLogin(ID.getText().toString(), PW.getText().toString());
                                            UserInfoSave(loginBody.getUserInfo().getUser_name(), loginBody.getUserInfo().getEmail(), loginBody.getUserInfo().getUser_img());
                                        }
                                        else{
                                            SharedPrefManager.NotAutoLogin(LoginActivity.this);
                                        }
                                        UserInfoSave(loginBody.getUserInfo().getUser_name(), loginBody.getUserInfo().getEmail(), loginBody.getUserInfo().getUser_img());
                                        //로그인 성공 시 메인 액티비티로 intent
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<LoginBody> call, Throwable t) {
                            Log.i(TAG, " : register fail - " + t.toString());
                        }
                    });
                }
            }
        });

        findViewById(R.id.buttonmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 회원가입 성공 시 데이터 바로 입력해서 로그인 고!
    }

    public void permissionCheck() {
        int permFineLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permCoaLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permFineLoc != PackageManager.PERMISSION_GRANTED && permCoaLoc != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }

    public void AutoLogin(String id, String pass){
        SharedPrefManager.preferenceSave(this,"AutoLogin",true);
        Log.i("info : ","AutoLogin Save");
        boolean result = SharedPrefManager.preferenceSave(this,"id",id);
        Log.i("info : ","AutoLogin " + result);
        result = SharedPrefManager.preferenceSave(this,"pass",pass);
        Log.i("info : ","AutoLogin " + result);
    }

    public void UserInfoSave(String name, String email, String image)
    {
        boolean result;
        result = SharedPrefManager.preferenceSave(this,"name",name);
        Log.i("info : ","AutoLogin " + result);
        result = SharedPrefManager.preferenceSave(this,"email",email);
        Log.i("info : ","AutoLogin " + result);
        result = SharedPrefManager.preferenceSave(this,"image",image);
        Log.i("info : ","AutoLogin " + result);
    }
}
