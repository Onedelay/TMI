package com.inu.tmi.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TMIService {

    //회원가입
    @FormUrlEncoded
    @POST("user/signup/send")
    Call<RequestBody> signUp(@Field("email") String email, @Field("pwd") String pwd);

    //로그인
    @FormUrlEncoded
    @POST("user/login")
    Call<LoginBody> login(@Field("email") String email, @Field("pwd") String pwd);

    //이메일 중복 체크

    @FormUrlEncoded
    @POST("user/signup/email")
    Call<RequestBody> emailCheck(@Field("email") String email);

}
