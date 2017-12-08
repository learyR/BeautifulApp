package com.leary.littlefairy.beautifulapp.model.network.api;

import com.leary.littlefairy.beautifulapp.model.Entity.HomeWork;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportDetailEntity;
import com.leary.littlefairy.beautifulapp.model.Entity.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("teacher/homework/list")
    Observable<Result<List<HomeWork>>> getMainData(
            @Field("userName") String userName,
            @Field("personId") int personId,
            @Field("type") int type,
            @Field("page") int page,
            @Field("pageSize") int pageSize
    );

    @FormUrlEncoded
    @POST("teacher/repair/list")
    Call<Result<RepairReportDetailEntity>> getRepairList(
            @Field("page") int page_num,
            @Field("pageSize") int pageSize,
            @Field("personId") String personId
    );
}
