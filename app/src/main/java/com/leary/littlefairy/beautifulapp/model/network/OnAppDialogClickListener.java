package com.leary.littlefairy.beautifulapp.model.network;

import android.app.Dialog;
import android.content.DialogInterface;

/**
 * LePay
 * Created by wbobo on 2016/11/21 17:00
 */

public abstract class OnAppDialogClickListener implements Dialog.OnClickListener {

    protected abstract void onAppClick(DialogInterface dialogInterface, int i);

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        onAppClick(dialogInterface, i);
    }
}
