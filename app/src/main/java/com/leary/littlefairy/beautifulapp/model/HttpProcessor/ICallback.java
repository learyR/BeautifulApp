package com.leary.littlefairy.beautifulapp.model.HttpProcessor;

public interface ICallback {
    void onSuccess(String result);

    void onFail(String e);
}
