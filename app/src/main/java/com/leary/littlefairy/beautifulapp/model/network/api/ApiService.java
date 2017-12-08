package com.leary.littlefairy.beautifulapp.model.network.api;

import com.leary.littlefairy.beautifulapp.model.Entity.HomeWork;
import com.leary.littlefairy.beautifulapp.model.Entity.Result;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("parent/homework/list")
    Observable<Result<List<HomeWork>>> getMainData(
            @Field("userName") String userName,
            @Field("personId") int personId,
            @Field("type") int type,
            @Field("page") int page,
            @Field("pageSize") int pageSize
//            @Field("uid") String uid,
//            @Field("timestamp") String timestamp,
//            @Field("token") String token,
//            @Field("schoolCode") String schoolCode,
//            @Field("appIdentify") String appIdentify
    );
}
