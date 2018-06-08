package com.inu.tmi.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface TMIService {

    //회원가입
    @FormUrlEncoded
    @POST("user/signup/send")
    Call<ServerRequestBody> signUp(@Field("email") String email, @Field("pwd") String pwd,@Field("name") String name);

    //로그인
    @FormUrlEncoded
    @POST("user/login")
    Call<LoginBody> login(@Field("email") String email, @Field("pwd") String pwd);

    //이메일 중복 체크
    @FormUrlEncoded
    @POST("user/signup/email")
    Call<ServerRequestBody> emailCheck(@Field("email") String email);

    //방생성
    @FormUrlEncoded
    @POST("taxi/taxiAllList")
    Call<GuestListBody> listCall(@Field("my_lat") double mylat, @Field("my_long") double mylong);

    @FormUrlEncoded
    @POST("taxi/createRoom")
    Call<RoomBody> createRoom(@Header("user_token") String user_token, @Field("start_lat") String start_lat, @Field("start_long") String start_long,
                              @Field("last_lat") String last_lat, @Field("last_long") String last_long,
                              @Field("taxi_msg") String taxi_msg, @Field("start_name")String start_name,
                              @Field("last_name")String end_name);

    //방삭제
    @FormUrlEncoded
    @POST("taxi/roomDel")
    Call<ServerRequestBody> roomDel(@Field("room_id") int room_id);

    //탑승자 추가
    @FormUrlEncoded
    @POST("taxi/addMem")
    Call<ServerRequestBody> addMem(@Header("user_token")String user_token, @Field("room_id") int room_id);

    //학교인증
    @FormUrlEncoded
    @POST("user/mail/send")
    Call<ServerRequestBody> mailSend( @Field("email") String email);


    //택시 전체 리스트
    @FormUrlEncoded
    @POST("taxi/taxiList")
    Call<taxiAllListBody> taxiList(@Header("user_token") String user_token);

    //방삭제
    @FormUrlEncoded
    @POST("taxi/taxiDetail")
    Call<RideMemBody> rideMem(@Header("user_token") String user_token,@Field("room_id") int room_id);

}
