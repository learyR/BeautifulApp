package com.leary.littlefairy.beautifulapp.model.network;

import com.google.gson.Gson;
import com.leary.littlefairy.beautifulapp.utils.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * LePay
 * Created by wbobo on 2016/12/07 11:37
 */

public class JsonResponseConverter<T> implements Converter<ResponseBody, T> {

    private Type type;
    Gson gson = new Gson();

    public JsonResponseConverter(Type type) {
        this.type = type;
    }


    @Override
    public T convert(ResponseBody responseBody) throws IOException {

        String strResult = responseBody.string();
        LogUtils.e("---->返回之后的数据:" + strResult);
//        final byte[] byteResultBase64 = Base64.decode(strResult.getBytes("utf-8"), Base64.DEFAULT);
//        final SecretKey secretKey;
//        if (BuildConfig.DEBUG)
//            secretKey = Crypto.AES.generateSecret("1234567812345678".getBytes("utf-8"));
//        else
//            secretKey = Crypto.AES.generateSecret(XXXX.nativeMethod(AppController.getInstance().getApplicationContext()).getBytes("utf-8"));
//        try {
//            final byte[] strDecrypt = Crypto.AES.decrypt(secretKey, byteResultBase64);
//            strResult = new String(strDecrypt, "utf-8");
//            LogUtils.i("---->返回之后的数据(解密之后):" + strResult);
//        } catch (Crypto.CryptoException e) {
//            e.printStackTrace();
//        }
//        LogUtils.i("---->返回之后的数据(type类型):" + type.toString());
        T result = gson.fromJson(strResult, type);
        return result;


    }
}
