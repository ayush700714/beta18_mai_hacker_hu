package com.example.sms_client;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/sms")
    Call<Void> connect();
}
