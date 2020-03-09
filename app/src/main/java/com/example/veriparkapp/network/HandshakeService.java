package com.example.veriparkapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HandshakeService {

    public static final String BASE_URL = "https://mobilechallenge.veripark.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
        return retrofit;
    }
}
