package com.leary.littlefairy.beautifulapp.ui.persenter.implement;

import android.app.Activity;

import com.leary.littlefairy.beautifulapp.base.RxPresenter;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportDetailEntity;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportEntity;
import com.leary.littlefairy.beautifulapp.model.Entity.Result;
import com.leary.littlefairy.beautifulapp.model.network.DefaultCallback;
import com.leary.littlefairy.beautifulapp.model.network.api.ApiClient;
import com.leary.littlefairy.beautifulapp.ui.persenter.contracts.RecyclerViewContracts;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RecyclerViewTestPresenter extends RxPresenter<RecyclerViewContracts.View> implements RecyclerViewContracts.Presenter {
    public Call<Result<List<RepairReportEntity>>> loadMoreCall = null;
    public RecyclerViewTestPresenter() {
    }

    @Override
    public void getList(int page, DefaultCallback callBack, Activity activity) {
        if (loadMoreCall == null) {
            loadMoreCall = ApiClient.service.getRepairList(page, 100,"182");
            loadMoreCall.enqueue(callBack);
        }
    }
}
