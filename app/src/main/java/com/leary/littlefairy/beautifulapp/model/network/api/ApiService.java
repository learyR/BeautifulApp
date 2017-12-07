package com.leary.littlefairy.beautifulapp.model.network.api;

import com.leary.littlefairy.beautifulapp.model.Entity.UserInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST
    Observable<UserInfo> getString(
            @Field("userName") String userName,
            @Field("personId") String personId
    );
}
