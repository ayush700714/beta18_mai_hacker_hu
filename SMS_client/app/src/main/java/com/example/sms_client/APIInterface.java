package com.example.sms_client;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("/sms")
    Call<Void> connect(@Body HashMap<String,String>map);
}
