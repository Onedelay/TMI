package com.inu.tmi.api;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Callback;
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


    public void signUp(@NonNull String email, @NonNull String pwd, @NonNull String name, Callback<ServerRequestBody> callback) {
        tmiService.signUp(email,pwd,name).enqueue(callback);

    }


    public void login(@NonNull String email, @NonNull String pwd, Callback<LoginBody> callback) {
        tmiService.login(email,pwd).enqueue(callback);
    }

    public void emailCheck(@NonNull String email, Callback<ServerRequestBody> callback) {
      //  Log.i(TAG,"TMIServer emailCheck");
        tmiService.emailCheck(email).enqueue(callback);
    }

    public void createRoom(@NonNull String user_token, @NonNull String start_lat, @NonNull String start_long, @NonNull String last_lat, @NonNull String last_long, @NonNull String taxi_msg, @NonNull String start_name, @NonNull String last_name, Callback<RoomBody> callback) {
       // Log.i(TAG,"TMIServer emailCheck");
        tmiService.createRoom(user_token, start_lat,start_long,last_lat,last_long,taxi_msg,start_name,last_name).enqueue(callback);
    }


    public void listCall(@NonNull double my_lat, @NonNull double my_long, Callback<GuestListBody> callback) {
        Log.i(TAG, "TMIServer listChck");
        tmiService.listCall(my_lat, my_long).enqueue(callback);
    }


    public void roomDel(@NonNull int room_id, Callback<ServerRequestBody> callback) {
       // Log.i(TAG,"TMIServer emailCheck");
        tmiService.roomDel(room_id).enqueue(callback);
    }

    public void addMem(@NonNull String user_token , @NonNull int room_id, Callback<ServerRequestBody> callback) {
       // Log.i(TAG,"TMIServer emailCheck");
        tmiService.addMem(user_token,room_id).enqueue(callback);
    }

    public void mailSend(@NonNull String email, Callback<ServerRequestBody> callback) {
       // Log.i(TAG,"TMIServer emailCheck");
        tmiService.mailSend(email).enqueue(callback);
    }

    public void taxiList(@NonNull String user_token , Callback<taxiAllListBody> callback) {
       // Log.i(TAG,"TMIServer emailCheck");
        tmiService.taxiList(user_token).enqueue(callback);
    }

}
