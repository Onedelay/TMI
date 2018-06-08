package com.inu.tmi.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inu.tmi.R;
import com.inu.tmi.api.ServerRequestBody;
import com.inu.tmi.api.TMIServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "TMI.TMIRegisterActivity";
    private static int duplicateCheck = 0;
    private static int GET_PICTURE_URI = 0011;

    EditText ID;
    EditText PW;
    EditText NAME;
    ImageView imageView;
    Bitmap bitmap;
    private static ServerRequestBody requestBody;
    private File user_img = null;
    private String filePath;
    private Uri uri;
    private FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ID = (EditText)findViewById(R.id.inputEmail);
        PW = (EditText)findViewById(R.id.inputPasswd);
        NAME = (EditText)findViewById(R.id.inputName);
        //imageView = (ImageView)findViewById(R.id.imageView);


        findViewById(R.id.authorBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ID.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일 입력해라", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i(TAG, ID.getText().toString());

                    TMIServer.getInstance().emailCheck(ID.getText().toString(), new Callback<ServerRequestBody>() {

                        @Override
                        public void onResponse(Call<ServerRequestBody> call, Response<ServerRequestBody> response) {
                            if(response.body() != null){
                                Log.i(TAG,"응답");
                                String requestBody = response.body().getMsg();
                                Log.i(TAG,"emailCheckBody : "+ requestBody);
                                if(requestBody.equals("duplicate email")){
                                    Toast.makeText(getApplicationContext(),"중복 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                }
                                else if(requestBody.equals("not inu email")){
                                    Toast.makeText(getApplicationContext(),"학교 이메일이 아닙니다.", Toast.LENGTH_SHORT).show();
                                }
                                else if(requestBody.equals("email available")){
                                    Toast.makeText(getApplicationContext(),"사용 가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                    duplicateCheck = 1;
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"잘못된 이메일 입니다." + requestBody, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerRequestBody> call, Throwable t) {
                            Log.i(TAG,"실패");
                            Log.i(TAG,t.toString());

                        }
                    });

                    Log.i(TAG,"emailCheck end");
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
                        TMIServer.getInstance().signUp(ID.getText().toString(), PW.getText().toString(), NAME.getText().toString(), new Callback<ServerRequestBody>() {
                            @Override
                            public void onResponse(Call<ServerRequestBody> call, Response<ServerRequestBody> response) {
                                if(response.body() != null){
                                    Log.i(TAG, " : signup success - ");
                                    ServerRequestBody requestBody = response.body();

                                    if(requestBody.getMsg().equals("success")){
                                        //로그인 성공 시 메인 액티비티로 intent
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
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
                            public void onFailure(Call<ServerRequestBody> call, Throwable t) {
                                Log.i(TAG, " : register fail - " + t.toString());

                            }
                        });
                    }
                }
            }
        });

    }

    public void permissionCheck() {
        int permFineLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permCoaLoc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permAlbum = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permFineLoc != PackageManager.PERMISSION_GRANTED && permCoaLoc != PackageManager.PERMISSION_GRANTED && permAlbum != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }


}
