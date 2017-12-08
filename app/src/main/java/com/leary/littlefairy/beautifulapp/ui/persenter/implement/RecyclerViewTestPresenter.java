package com.leary.littlefairy.beautifulapp.ui.persenter.implement;

import android.app.Activity;

import com.leary.littlefairy.beautifulapp.base.RxPresenter;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportDetailEntity;
import com.leary.littlefairy.beautifulapp.model.Entity.Result;
import com.leary.littlefairy.beautifulapp.model.network.DefaultCallback;
import com.leary.littlefairy.beautifulapp.model.network.api.ApiClient;
import com.leary.littlefairy.beautifulapp.ui.persenter.contracts.RecyclerViewContracts;

import retrofit2.Call;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RecyclerViewTestPresenter extends RxPresenter<RecyclerViewContracts.View> implements RecyclerViewContracts.Presenter {
    public Call<Result<RepairReportDetailEntity>> loadMoreCall = null;
    public RecyclerViewTestPresenter() {
    }

    @Override
    public void getList(int page, DefaultCallback callBack, Activity activity) {
        if (loadMoreCall == null) {
            loadMoreCall = ApiClient.service.getRepairList(page, 100,"12477");
            loadMoreCall.enqueue(callBack);
        }
    }
}
