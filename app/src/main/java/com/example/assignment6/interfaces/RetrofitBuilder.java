package com.example.assignment6.interfaces;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private Retrofit retrofit = null;
     public RetrofitBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //returning retrofit instance
    public ApiInterface getRetrofitApiInterface() {
        return retrofit.create(ApiInterface.class);
    }


}

