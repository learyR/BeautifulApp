package com.leary.littlefairy.beautifulapp.model.network;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.model.Entity.Result;
import com.leary.littlefairy.beautifulapp.model.Entity.TipInfo;
import com.leary.littlefairy.beautifulapp.utils.ActivityAlertDialogManager;
import com.leary.littlefairy.beautifulapp.utils.LogUtils;
import com.leary.littlefairy.beautifulapp.utils.ToastUtils;

import okhttp3.Headers;

public class DefaultCallback<T> extends ForegroundCallback<T> {

    public DefaultCallback(@NonNull Activity activity) {
        super(activity);
    }


    /**
     * @param code    100009  100010  100011 未登录、过期、在其他设备登录； 100004 100006 账户不存在、账户冻结
     * @param headers
     * @param error
     * @return
     */
    @Override
    public final boolean onResultError(int code, Headers headers, Result.Error error) {
        if (code == 100009 || code == 100010 || code == 100011) {

            return onResultAuthError(code, headers, error);

        } else {

            return onResultOtherError(code, headers, error);

        }
    }

    public boolean onResultAuthError(int code, Headers headers, Result.Error error) {
        //清空缓存
//        UserShared.with(getActivity()).logout();
        //TipInfo tipInfo = TipInfo.createTipInfo("", getActivity().getResources().getMainData(R.string.access_token_out_of_date));
        TipInfo tipInfo = TipInfo.createTipInfo("", TextUtils.isEmpty(error.getErrorMessage()) ? getActivity().getResources().getString(R.string.access_token_out_of_date) : error.getErrorMessage());
        tipInfo.setSureBtnText("退出登录");
        if (!getActivity().isFinishing()) {
            try {
                AlertDialog alertDialog = ActivityAlertDialogManager.displayOneBtnDialog(getActivity(), tipInfo, new OnAppDialogClickListener() {
                    @Override
                    protected void onAppClick(DialogInterface dialogInterface, int i) {
//                        LoginActivity.startForResult(getActivity());
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
                LogUtils.d(getActivity() + "alertDialog -------- > " + alertDialog.toString());
            } catch (Exception e) {

            }
        }
        return false;
    }

    public boolean onResultOtherError(int code, Headers headers, Result.Error error) {
        Log.e("","error===getDescription===="+error);
        if (error == null) {
            return false;
        }
        Log.e("","error===getDescription===="+error.getDescription()+"===getErrorMessage==="+error.getErrorMessage());
        ToastUtils.with(getActivity()).show(error.getErrorMessage());
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, Result.Error error) {
        ToastUtils.with(getActivity()).show(error.getErrorMessage());
        return false;
    }

    @Override
    public boolean onCallCancel() {
        return super.onCallCancel();
    }
}
