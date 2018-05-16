package com.leary.littlefairy.beautifulapp.model.network.api;

import com.leary.littlefairy.beautifulapp.model.Entity.HomeWork;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportDetailEntity;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportEntity;
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
            @Field("personId") String personId,
            @Field("page") int page,
            @Field("pageSize") int pageSize,
            @Field("beginTime") long startTime,
            @Field("endTime") long endTime,
            @Field("search") String search
    );

    @FormUrlEncoded
    @POST("teacher/repair/new-list")
    Call<Result<List<RepairReportEntity>>> getRepairList(
            @Field("page") int page_num,
            @Field("pageSize") int pageSize,
            @Field("personId") String personId
    );
}
