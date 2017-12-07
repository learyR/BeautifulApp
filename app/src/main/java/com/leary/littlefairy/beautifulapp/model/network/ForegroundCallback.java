package com.leary.littlefairy.beautifulapp.model.network;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.leary.littlefairy.beautifulapp.model.Entity.Result;
import com.leary.littlefairy.beautifulapp.utils.ActivityUtils;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForegroundCallback<T> implements Callback<T>, CallbackLifecycle<T> {

    private final Activity activity;

    public ForegroundCallback(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    protected final Activity getActivity() {
        return activity;
    }



    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        if (ActivityUtils.isAlive(activity)) {
            boolean interrupt;

            //每一个请求返回,都是Result,必须是Success = true 才能保证结果集正确
            if (response.isSuccessful()) {
                if(response.body() instanceof Result && ((Result) response.body()).isSuccess()){
                    interrupt = onResultOk(response.code(), response.headers(), response.body());
                }else{
                    int code = -1;
                    String description = "";
                    if(response.body() instanceof Result){
                        code = ((Result)(response.body())).getCode();
                        description = ((Result)(response.body())).getDescription();
                    }
                    interrupt = onResultError(code, response.headers(), Result.buildError(description));
                }
            } else {
                int code = -1;
                String description = "";
                if(response.body() instanceof Result){
                    code = ((Result)(response.body())).getCode();
                    description = ((Result)(response.body())).getDescription();
                }
                if (!TextUtils.isEmpty(description)){
                    interrupt = onResultError(code, response.headers(), Result.buildError(description));
                }else{
                    interrupt = onResultError(response.code(), response.headers(), Result.buildError(response));
                }

            }
            if (!interrupt) {
                onFinish();
            }
        }
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        if (ActivityUtils.isAlive(activity)) {
            boolean interrupt;
            if (call.isCanceled()) {
                interrupt = onCallCancel();
            } else {
                interrupt = onCallException(t, Result.buildError(t));
            }
            if (!interrupt) {
                onFinish();
            }
        }
    }

    @Override
    public boolean onResultOk(int code, Headers headers, T result) {
        return false;
    }

    @Override
    public boolean onResultError(int code, Headers headers, Result.Error error) {
        return false;
    }

    @Override
    public boolean onCallCancel() {
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, Result.Error error) {
        return false;
    }

    @Override
    public void onFinish() {}

}
