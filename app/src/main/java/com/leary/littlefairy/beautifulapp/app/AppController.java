package com.leary.littlefairy.beautifulapp.app;

import android.app.Application;

import com.leary.littlefairy.beautifulapp.model.HttpProcessor.HttpHelper;
import com.leary.littlefairy.beautifulapp.model.HttpProcessor.VolleyProcessor;

/**
 * Created by Administrator on 2017/12/7.
 */

public class AppController extends Application{
    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        HttpHelper.init(new VolleyProcessor(this));
        
    }

    public static AppController getInstance(){
        return instance;
    }
}
