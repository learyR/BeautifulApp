package com.leary.littlefairy.beautifulapp.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.leary.littlefairy.beautifulapp.model.Entity.TipInfo;


/**
 * @link http://www.jianshu.com/p/4d97a8538b98
 * LePay
 * Created by wbobo on 2017/01/14 19:54
 */

public class ActivityAlertDialogManager {

    //==========常量==========
    private static final String TAG = "ActivityADManager";

    //==========普通静态变量==========
    // 一个Activity下只产生一个AlertDialog实例
    private static AlertDialog sAlertDialog;
    // 一个Activity下只产生一个AlertDialog.Builder实例
    private static AlertDialog.Builder sBuilder;
    private static Activity sLastActivity = null;

    //==========AlertDialog==========//
    public static AlertDialog displayOneBtnDialog(@NonNull Activity Activity, String title, String msg) {
        if (TextUtils.isEmpty(msg)) return null;
        if (sAlertDialog == null) {
            TipInfo tipInfo = TipInfo.createTipInfo(title, msg);
            sAlertDialog = displayOneBtnDialog(Activity, tipInfo, null);
        } else {
            sAlertDialog.setTitle(title);
            sAlertDialog.setMessage(msg);
            DialogInterface.OnClickListener listener = null;
            sAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", listener);
            sAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.GONE);
        }
        return sAlertDialog;
    }

    public static AlertDialog displayOneBtnDialog(@NonNull Activity Activity, TipInfo tipInfo, DialogInterface.OnClickListener sureListener) {
        if (tipInfo == null) return null;
        AlertDialog.Builder builder = getBuilder(Activity, tipInfo);
        builder = addAlertDialogPositiveButton(tipInfo.getSureBtnText(), sureListener, builder);
        LogUtils.i("builder====>" + builder);
        // 显示出该对话框
        if(sAlertDialog == null){
            sAlertDialog = builder.create();
        }

        LogUtils.i("sAlertDialog====>" + sAlertDialog);
        DialogInterface.OnClickListener listener = null;
        sAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "", listener);
        if (sAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE) != null) {
            sAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.GONE);
        }
        return sAlertDialog;
    }

    public static AlertDialog displayTwoBtnDialog(@NonNull Activity Activity, String title, String msg) {
        if (TextUtils.isEmpty(msg)) return null;
        if (sAlertDialog == null) {
            TipInfo tipInfo = TipInfo.createTipInfo(title, msg);
            sAlertDialog = displayTwoBtnDialog(Activity, tipInfo, null, null);
        } else {
            sAlertDialog.setTitle(title);
            sAlertDialog.setMessage(msg);
            if (sAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE) != null) {
                sAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText("取消");
                sAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.VISIBLE);
            }
        }
        return sAlertDialog;
    }

    public static AlertDialog displayTwoBtnDialog(@NonNull Activity activity, TipInfo tipInfo, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener sureListener) {
        if (tipInfo == null) return null;
        // 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = getBuilder(activity, tipInfo);
        builder = addAlertDialogPositiveButton(tipInfo.getSureBtnText(), sureListener, builder);
        builder = addAlertDialogNegativeButton(tipInfo.getCancelBtnText(), cancelListener, builder);
        if (!ActivityUtils.isAlive(activity)) {
            ActivityUtils.recreateDelayed(activity);
        }
        // 显示出该对话框
        sAlertDialog = builder.show();
        return sAlertDialog;
    }

    @NonNull
    private static AlertDialog.Builder getBuilder(@NonNull Activity Activity, TipInfo tipInfo) {
        AlertDialog.Builder builder;
        if (Activity == sLastActivity) {
            if (sBuilder != null) {
                builder = sBuilder;
            } else {
                builder = createNewBuilder(Activity);
            }
        } else {
            reset();
            builder = createNewBuilder(Activity);
            sLastActivity = Activity;
            sBuilder = builder;
        }
        // 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        // 设置Title的图标
        builder.setIcon(tipInfo.getIconResId());
        // 设置Title的内容
        builder.setTitle(tipInfo.getTitle());
        // 设置Content来显示一个信息
        builder.setMessage(tipInfo.getMsg());
        return builder;
    }

    private static void reset() {
        sBuilder = null;
        sAlertDialog = null;
        sLastActivity = null;
    }

    @NonNull
    private static AlertDialog.Builder createNewBuilder(@NonNull Activity Activity) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(Activity);
        sBuilder = builder;
        return builder;
    }

    private static AlertDialog.Builder addAlertDialogPositiveButton(String btnText, DialogInterface.OnClickListener listener, final AlertDialog.Builder builder) {
        listener = getDefaultOnClickListener(listener);
        // 设置一个PositiveButton
        builder.setPositiveButton(btnText, listener);
        return builder;
    }

    private static AlertDialog.Builder addAlertDialogNegativeButton(String btnText, DialogInterface.OnClickListener listener, final AlertDialog.Builder builder) {
        listener = getDefaultOnClickListener(listener);
        // 设置一个PositiveButton
        builder.setNegativeButton(btnText, listener);
        return builder;
    }

    @NonNull
    private static DialogInterface.OnClickListener getDefaultOnClickListener(DialogInterface.OnClickListener listener) {
        if (listener != null) return listener;
        listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e(TAG, "AlertDialog Button Click!");
            }
        };
        return listener;
    }
    //==========AlertDialog==========//


    //==========逻辑方法==========//
    public static void destory(@NonNull Activity Activity) {
        if (Activity != sLastActivity) {
            Activity = null;
            return;
        }
        if (sAlertDialog != null) {
            sAlertDialog.cancel();
        }
        Activity = null;
        reset();
    }

}