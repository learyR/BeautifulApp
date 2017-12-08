package com.leary.littlefairy.beautifulapp.model.network;


import com.leary.littlefairy.beautifulapp.model.Entity.Result;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * LePay
 * Created by wbobo on 2016/12/07 11:33
 */

public class JsonConverterFactory extends Converter.Factory{

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //根据type判断是否是自己能处理的类型，不能的话，return null ,交给后面的Converter.Factory
        if(type instanceof ParameterizedType){
            Type rawType = ((ParameterizedType)type).getRawType();
            if(rawType == Result.class){
                return new JsonResponseConverter(type);
            }
        }
        return null;
    }
}
