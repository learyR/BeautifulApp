package com.leary.littlefairy.beautifulapp.model.network.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/12/6.
 */

public class Network {
    public static ApiService service = new Retrofit.Builder()
            .client(new OkHttpClient())
            .baseUrl("http://171.221.228.25:9200/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(ApiService.class);
}
