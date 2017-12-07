package com.leary.littlefairy.beautifulapp.base;

import android.support.v7.app.ActionBar;

import com.leary.littlefairy.beautifulapp.base.swipe.SwipeBackLayout;


/**
 * 基础Activity
 */
public abstract class BaseBackActivity<T extends BasePresenter> extends BaseActivity<T>  {


    private SwipeBackLayout mSwipeBackLayout;
    @Override
    protected void initWindow() {
        super.initWindow();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(false);
        }
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        if (mSwipeBackLayout != null)
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        //mSwipeBackLayout.setEdgeSize(200);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




}

