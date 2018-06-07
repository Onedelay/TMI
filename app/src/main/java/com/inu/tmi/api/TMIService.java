package com.inu.tmi.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @FormUrlEncoded
    @POST("taxi/taxiList")
    Call<GuestListBody> listcall(@Field("") String email);

    @POST("taxi/createRoom")
    Call<RoomBody> createRoom(@Header("user_token") String user_token, @Field("start_lat") String start_lat, @Field("start_long") String start_long,
                              @Field("last_lat") String last_lat, @Field("last_long") String last_long,
                              @Field("taxi_msg") String taxi_msg, @Field("start_name")String start_name,
                              @Field("last_name")String end_name);

}
