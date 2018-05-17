package com.leary.littlefairy.beautifulapp.model.HttpProcessor;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpProcessor implements IHttpProcessor {
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    public OkHttpProcessor() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler();
    }

    @Override
    public void post(String url, Map<String, Object> params, final ICallback callback) {
        RequestBody requestBody = appendBody(params);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("User-Agent", "a")
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFail(e.toString());
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        final String result = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(result);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail(response.message());
                        }
                    });

                }

            }
        });
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback callback) {

    }

    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
            return builder.build();
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue().toString());
        }
        return builder.build();
    }
}
