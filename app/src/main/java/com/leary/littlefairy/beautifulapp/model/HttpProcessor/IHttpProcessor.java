package com.leary.littlefairy.beautifulapp.model.HttpProcessor;

import java.util.Map;

public interface IHttpProcessor {
    //网络访问post ,get
    void post(String url, Map<String, Object> params, ICallback callback);

    void get(String url, Map<String, Object> params, ICallback callback);

}
