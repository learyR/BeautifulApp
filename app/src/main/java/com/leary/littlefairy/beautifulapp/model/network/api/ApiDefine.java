package com.leary.littlefairy.beautifulapp.model.network.api;

import android.os.Build;

import com.leary.littlefairy.beautifulapp.BuildConfig;

/**
 * Created by Administrator on 2017/12/8.
 */

public final class ApiDefine {
    public static final String USER_AGENT = BuildConfig.VERSION_NAME + " (Android " + Build.VERSION.RELEASE + "; " + Build.MANUFACTURER + " - " + Build.MODEL + ")";
    public static final int USER_ID = 50;
    public static final String ID_APP_CLIENT_COMMON_PARENT = "10002";
    public static final String ID_APP_SCHOOL_CODE = "0";
}
