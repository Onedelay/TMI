package com.inu.tmi.api;

import android.util.Log;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TMIService {

    //회원가입
    @Multipart
    @POST("user/signup/send")
    Call<ServerRequestBody> signUp(@Part MultipartBody.Part user_img);

    //로그인
    @FormUrlEncoded
    @POST("user/login")
    Call<LoginBody> login(@Field("email") String email, @Field("pwd") String pwd);

    //이메일 중복 체크
    @FormUrlEncoded
    @POST("user/signup/email")
    Call<ServerRequestBody> emailCheck(@Field("email") String email);



}
