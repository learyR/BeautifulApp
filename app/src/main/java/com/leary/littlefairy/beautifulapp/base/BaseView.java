package com.leary.littlefairy.beautifulapp.base;

/**
 * Created by Shinelon on 2016-11-21.
 * View 的基类
 */

public interface BaseView {
    /**
     * 显示加载进度条
     * */
    void showLoading(String message);
    /**
     * 隐藏加载进度条
     * */
    void hideLoading();
    /**
     * 显示提示对话框
     * */
    void tips(String tips);
    void tips(int tips);

}
