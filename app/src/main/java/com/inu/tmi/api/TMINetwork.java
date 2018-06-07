package com.inu.tmi.api;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;


public class TMINetwork {
    private static final String TAG = "TMI.TMINetwork";
    private static final TMIService tmiService = TMIServer.getInstance().getTmiService();


    public static void emailCheck(String email) {
        Log.i(TAG,"emailCheck");
        Call<RequestBody> requestBodyCall = tmiService.emailCheck(email);
        requestBodyCall.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.isSuccessful()) {
                    RequestBody requestBody = response.body();
                    Log.i(TAG, " : signup success - ");
                    TMIBody.setEmailCheckBody(String.valueOf(requestBody.getMsg()));
                    Log.i(TAG, " : signup success - " + requestBody.getMsg());
                }
            }
            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.i(TAG, " : email check fail - " + t.toString());
            }
        });
        Log.i(TAG,"emailCheck 끝");
    }

    public interface OnRequestListener{
        public void OnRequestComplete();
    }
}

/*

public class TMINetwork{
    String serverIp = "http://13.125.78.152:5555/";
    private static final String TAG = "TMI.TMINetwork";

    public String getJSON(String email){
        try{
            URL url = new URL(serverIp + "user/signup/emaiㅣ/?email" + email);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setConnectTimeout(10*1000);
            conn.setReadTimeout(10*1000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Kepp-Alive");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);
            conn.connect();

            int status = conn.getResponseCode();
            Log.i(TAG, "ProxyResponseCode:"+ status);
            BufferedReader br = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();

            String line;
            while((line = br.readLine())!=null){
                sb.append(line + "\n");
            }
            Log.i(TAG, "ProxyResponseCode:"+ sb.toString());
            br.close();
            return sb.toString();

        }

        catch (Exception e){
            return "false";
        }
    }
}
*/

