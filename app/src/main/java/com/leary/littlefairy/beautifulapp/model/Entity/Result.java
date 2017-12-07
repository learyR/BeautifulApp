package com.leary.littlefairy.beautifulapp.model.Entity;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.MalformedJsonException;
import com.leary.littlefairy.beautifulapp.model.Entity.EntityUtils.EntityUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;

/**
 * 用来封装所有返回数据的基础类型
 * @param <T>
 */
public class Result<T> {


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class Error extends Result {

        @SerializedName("error_msg")
        private String errorMessage;

        public String getErrorMessage() {
            return errorMessage;
        }

    }

    public static <T> Error buildError(@NonNull Response<T> response) {
        try {
            return EntityUtils.gson.fromJson(response.errorBody().string(), Error.class);
        } catch (IOException | JsonSyntaxException e) {
            Error error = new Error();
            error.success = false;
            switch (response.code()) {
                case 400:
                    error.errorMessage = "请求参数有误";
                    break;
                case 403:
                    error.errorMessage = "请求被拒绝";
                    break;
                case 404:
                    error.errorMessage = "资源未找到";
                    break;
                case 405:
                    error.errorMessage = "请求方式不被允许";
                    break;
                case 408:
                    error.errorMessage = "请求超时";
                    break;
                case 422:
                    error.errorMessage = "请求语义错误";
                    break;
                case 500:
                    error.errorMessage = "服务器逻辑错误";
                    break;
                case 502:
                    error.errorMessage = "服务器网关错误";
                    break;
                case 504:
                    error.errorMessage = "服务器网关超时";
                    break;
                default:
                    error.errorMessage = response.message();
                    break;
            }
            //return error;
            error.errorMessage = "网络开小差咯~";
            return error;
        }
    }

    public static Error buildError(String errorMessage) {
        Error error = new Error();
        error.success = false;
        error.errorMessage = errorMessage;
        return error;
    }

    public static Error buildError(@NonNull Throwable t) {
        t.printStackTrace();
        Error error = new Error();
        error.success = false;
        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            error.errorMessage = "网络无法连接";
        } else if (t instanceof SocketTimeoutException) {
            error.errorMessage = "网络访问超时";
        } else if (t instanceof JsonSyntaxException || t instanceof MalformedJsonException) {
            error.errorMessage = "响应数据格式错误";
        } else {
            error.errorMessage = "未知错误：" + t.getLocalizedMessage();
        }
        error.errorMessage = "网络开小差咯!";
        return error;
    }

    protected boolean success;
    private int code;
    private String description;
    private T detail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }


}