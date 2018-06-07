package com.inu.tmi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.api.RequestBody;
import com.inu.tmi.api.TMIServer;
import com.inu.tmi.api.TMIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bmj on 2018-06-05.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TMI.LoginActivity";
    public static final int REQUEST_LOGIN = 100;

    EditText ID;
    EditText PW;
    Button LoginBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        ID = (EditText)findViewById(R.id.IDtxt);
        PW = (EditText)findViewById(R.id.PWtxt);

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
                    /*TMIServer.getInstance().login(ID.getText().toString(), PW.getText().toString(), new Callback<RequestBody>() {
                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                            if(response.body() != null){
                                Log.i(TAG, " : login success - ");
                                RequestBody requestBody = response.body();

                                if(requestBody.getMsg().equals("success")){
                                    //로그인 성공 시 메인 액티비티로 intent
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                            Log.i(TAG, " : login fail - " + t.toString());

                        }
                    });
*/




                }
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
}
