package com.example.sms_client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {


    private Retrofit retrofit;
    private APIInterface apiInterface;
    private String BASE_URL = "http://71d77f2de0b5.ngrok.io/";

    public  APIInterface init(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

        return apiInterface;
    }


}
