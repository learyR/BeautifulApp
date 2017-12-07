package com.leary.littlefairy.beautifulapp.model.network;


import com.leary.littlefairy.beautifulapp.model.Entity.Result;

import okhttp3.Headers;

public interface CallbackLifecycle<T> {

    boolean onResultOk(int code, Headers headers, T result);

    boolean onResultError(int code, Headers headers, Result.Error error);

    boolean onCallCancel();

    boolean onCallException(Throwable t, Result.Error error);

    void onFinish();

}
