package com.leary.littlefairy.beautifulapp.model.Entity;

import android.support.annotation.IdRes;

/**
 * 提示对话框
 * LePay
 * Created by wbobo on 2017/01/14 19:57
 */

public class TipInfo {

    private TipInfo(){}

    //标题
    private String title;

    //内容
    private String msg;

    //确认按钮文字
    private String sureBtnText;

    //取消按钮文字
    private String cancelBtnText;

    @IdRes
    private int iconResId;

    public static TipInfo createTipInfo(String title, String msg){
        TipInfo tipInfo = new TipInfo();
        tipInfo.setTitle(title);
        tipInfo.setMsg(msg);
        return tipInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSureBtnText() {
        return sureBtnText;
    }

    public void setSureBtnText(String sureBtnText) {
        this.sureBtnText = sureBtnText;
    }

    public String getCancelBtnText() {
        return cancelBtnText;
    }

    public void setCancelBtnText(String cancelBtnText) {
        this.cancelBtnText = cancelBtnText;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
