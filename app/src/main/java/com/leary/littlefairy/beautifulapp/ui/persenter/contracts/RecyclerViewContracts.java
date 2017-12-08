package com.leary.littlefairy.beautifulapp.ui.persenter.contracts;

import android.app.Activity;

import com.leary.littlefairy.beautifulapp.base.BasePresenter;
import com.leary.littlefairy.beautifulapp.base.BaseView;
import com.leary.littlefairy.beautifulapp.model.network.DefaultCallback;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface RecyclerViewContracts {
    interface View extends BaseView {
        void onRefreshListOk();

        void onRefreshListFinish();

        void onRefreshListError();
    }
    interface Presenter extends BasePresenter<View> {
        void getList(int page, DefaultCallback callBack, Activity activity);
    }
}
