package com.inu.tmi.api;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.inu.tmi.activity.RegisterActivity;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMIServer extends Application{
    private static final String TAG = "TMI.TMIServer";
    private static TMIServer instance;
    private TMIService tmiService;
    private static final String ServerUrl = "http://13.125.78.152:5555/";

    public TMIService getTmiService(){
        return tmiService;

    }

    public static TMIServer getInstance(){
        Log.i(TAG,"getInstance");
        if(instance == null){
            instance = new TMIServer();
        }
        return instance;
    }

    private TMIServer() {
        Log.i(TAG,"TMI Server Retrofit");
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(ServerUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        tmiService = retrofit.create(TMIService.class);
    }


    public void signUp(@NonNull String email, @NonNull String pwd, @NonNull String name, @NonNull File user_img, Callback<ServerRequestBody> callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/webp"), user_img);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("userfile", user_img.getName(), requestBody);

        tmiService.signUp(multipartBody).enqueue(callback);

    }


    public void login(@NonNull String email, @NonNull String pwd, Callback<LoginBody> callback) {
        tmiService.login(email,pwd).enqueue(callback);
    }

    public void emailCheck(@NonNull String email, Callback<ServerRequestBody> callback) {
        Log.i(TAG,"TMIServer emailCheck");
        tmiService.emailCheck(email).enqueue(callback);
    }

}
