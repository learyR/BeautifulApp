package com.leary.littlefairy.beautifulapp.model.HttpProcessor;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.xml.transform.Result;

public abstract class HttpCallback<Result> implements ICallback {

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        Class<?> clz = analysisClassInfo(this);
        Result objResult = (Result) gson.fromJson(result, clz);
        onSuccess(objResult);
    }

    public abstract void onSuccess(Result result);
    //运用了反射
    public static Class<?> analysisClassInfo(Object o){
        Type genType = o.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }



    @Override
    public void onFail(String e) {

    }
}
