package com.inu.tmi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.api.RequestBody;
import com.inu.tmi.api.TMIBody;
import com.inu.tmi.api.TMINetwork;
import com.inu.tmi.api.TMIServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "TMI.TMIRegisterActivity";
    private static int duplicateCheck = 0;

    EditText ID;
    EditText PW;
    private static RequestBody requestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ID = (EditText)findViewById(R.id.inputEmail);
        PW = (EditText)findViewById(R.id.inputPasswd);

        findViewById(R.id.authorBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ID.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일 입력해라", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i(TAG, ID.getText().toString());

                    TMIServer.getInstance().emailCheck(ID.getText().toString(), new Callback<RequestBody>() {

                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                            if(response.body() != null){
                                Log.i(TAG,"응답");
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                            Log.i(TAG,"실패");
                            Log.i(TAG,t.toString());

                        }
                    });

                    Log.i(TAG,"emailCheck end");
                  /*  TMINetwork tmiNetwork = new TMINetwork();
                    tmiNetwork.getJSON(ID.getText().toString());*/
/*

                    TMINetwork.emailCheck(ID.getText().toString(), new TMINetwork.OnRequestListener(){
                        @Override
                        public void OnRequestComplete() {
                            String emailCheckBody = TMIBody.getEmailCheckBody();
                            Log.i(TAG,"emailCheckBody : "+ emailCheckBody);
                            if(emailCheckBody.equals("duplicate email")){
                                Toast.makeText(getApplicationContext(),"중복 이메일 입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else if(emailCheckBody.equals("success")){
                                Toast.makeText(getApplicationContext(),"사용 가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"잘못된 이메일 입니다." + emailCheckBody, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
*/

                }
            }
        });


        findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ID.getText().equals(null) )
                {
                    Toast.makeText(getApplicationContext(),"ID 입력해라", Toast.LENGTH_SHORT).show();
                }
                else if(PW.getText().equals(null))
                {
                    Toast.makeText(getApplicationContext(),"PW 입력해라", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //중복 체크 안했을 때
                    if(duplicateCheck != 1){
                        Toast.makeText(getApplicationContext(),"이메일 중복 체크 하세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        /*TMIServer.getInstance().login(ID.getText().toString(), PW.getText().toString(), new Callback<RequestBody>() {
                            @Override
                            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                                if(response.body() != null){
                                    Log.i(TAG, " : signup success - ");
                                    RequestBody requestBody = response.body();

                                    if(requestBody.getMsg().equals("success")){
                                        //로그인 성공 시 메인 액티비티로 intent
                                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
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
                                Log.i(TAG, " : register fail - " + t.toString());

                            }
                        });
*/
                    }

                }
            }
        });




    }
}
