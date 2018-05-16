package com.leary.littlefairy.beautifulapp.model.HttpProcessor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHelper implements IHttpProcessor{

    private static IHttpProcessor mHttpProcessor = null;
    //单例
    private static HttpHelper _instance;
    //参数
    private Map<String, Object> mParams;

    public static void init(IHttpProcessor httpProcessor) {
        mHttpProcessor = httpProcessor;
    }


    private HttpHelper() {
        mParams = new HashMap<>();
    }

    public static HttpHelper getInstance() {
        synchronized (HttpHelper.class) {
            if (_instance == null) {
                _instance = new HttpHelper();
            }
        }
        return _instance;
    }


    @Override
    public void post(String url, Map<String, Object> params, ICallback callback) {
        final String strUrl = appendParams(url,params);
        mHttpProcessor.post(strUrl, params, callback);
    }


    @Override
    public void get(String url, Map<String, Object> params, ICallback callback) {
        final String strUrl = appendParams(url,params);
        mHttpProcessor.post(strUrl, params, callback);
    }

    /**
     * 参数拼接
     */
    private static String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (urlBuilder.indexOf("?") <= 0) {
            urlBuilder.append("?");
        } else {
            if (!urlBuilder.toString().endsWith("?")) {
                urlBuilder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        return urlBuilder.toString();
    }
    //Url不允许有空格字符，如果参数值有空格符，需要用这个方法转化
    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
