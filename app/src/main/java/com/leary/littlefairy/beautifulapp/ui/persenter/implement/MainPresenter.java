package com.leary.littlefairy.beautifulapp.ui.persenter.implement;

import android.app.Activity;

import com.leary.littlefairy.beautifulapp.ui.persenter.contracts.IMainPresenter;
import com.leary.littlefairy.beautifulapp.ui.view.IMainView;

import rx.Subscription;

/**
 * Created by Administrator on 2017/12/7.
 */

public class MainPresenter implements IMainPresenter {
    private final IMainView mainView;
    private final Activity activity;
    private final Subscription subscription;

    public MainPresenter(IMainView mainView, Activity activity, Subscription subscription) {
        this.subscription = subscription;
        this.mainView = mainView;
        this.activity = activity;
    }

    @Override
    public void loadMainData() {
        mainView.loadMainDataOk();
    }
}
