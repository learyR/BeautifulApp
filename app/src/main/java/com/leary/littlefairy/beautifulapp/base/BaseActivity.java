package com.leary.littlefairy.beautifulapp.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.base.swipe.SwipeBackActivity;
import com.leary.littlefairy.beautifulapp.utils.ActivityAlertDialogManager;
import com.leary.littlefairy.beautifulapp.utils.DialogUtils;
import com.leary.littlefairy.beautifulapp.utils.ToastUtils;
import com.leary.littlefairy.beautifulapp.widget.FamiliarToolbar;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 基础Activity
 */
public abstract class BaseActivity<T extends BasePresenter> extends SwipeBackActivity implements BaseView{
    protected RequestManager mImageLoader;
    private boolean mIsDestroy;
    private final String mPackageNameUmeng = this.getClass().getName();
//    private Fragment mFragment;
    protected T mPresenter;
    private Unbinder mUnBinder;
    private ProgressDialog mWaitDialog;
    protected FamiliarToolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);


        if (initBundle(getIntent().getExtras())) {
            setContentView(getContentView());
            mToolbar = (FamiliarToolbar) findViewById(R.id.base_toolbar);
            if (mToolbar != null){
                mToolbar.setClickListener(new FamiliarToolbar.ClickListener() {
                    @Override
                    public void clickLeft() {
                        ActivityCompat.finishAfterTransition(BaseActivity.this);
                    }

                    @Override
                    public void clickTitle() {
                        onClickTitle();
                    }

                    @Override
                    public void clickRight() {
                        onClickRight();
                    }
                });
            }
            initInject();
            if (mPresenter != null)
                mPresenter.attachView(this);
            initWindow();
            mUnBinder = ButterKnife.bind(this);
            initWidget();
            initData();
        } else {
            finish();
        }

    }

    protected abstract void initInject() ;
    protected void onClickTitle(){}
    protected void onClickRight(){}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected abstract int getContentView();

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void initWindow() {
    }

    protected void initWidget() {
    }

    protected void initData() {
    }

    public synchronized RequestManager getImageLoader() {
        if (mImageLoader == null)
            mImageLoader = Glide.with(this);
        return mImageLoader;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsDestroy = true;
        mUnBinder.unbind();
        if (mPresenter != null)
            mPresenter.detachView();
        ActivityAlertDialogManager.destory(this);
    }


    public boolean isDestroy() {
        return mIsDestroy;
    }
    @Override
    public void showLoading(String message) {
        if (mWaitDialog == null) {
            mWaitDialog = DialogUtils.getProgressDialog(this, true);
        }
        mWaitDialog.setMessage(message);
        mWaitDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mWaitDialog == null) return;
        synchronized (BaseActivity.class){
            if (mWaitDialog.isShowing())
                mWaitDialog.dismiss();
        }

    }

    @Override
    public void tips(String tips) {
        ToastUtils.with(this).show(TextUtils.isEmpty(tips) ? "" : tips);
    }

    @Override
    public void tips(@StringRes int tips) {
        ToastUtils.with(this).show(tips);
    }
    /**
     * 跳转器
     * @param activityName:
     * @param intent:
     */
    public void navigateTo(final String activityName, final Intent intent) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(activityName);
            if (clazz != null) {
                intent.setClass(this, clazz);
                this.startActivity(intent);
            }
        } catch (final ClassNotFoundException e) {
            return;
        }
        navigateToWithAnim(0, 0);
    }

    /**
     * 跳转器
     * @param activityName:
     * @param intent:
     */
    public void navigateTo(final String activityName, final Intent intent, int requestCode) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(activityName);
            if (clazz != null) {
                intent.setClass(this, clazz);
                this.startActivityForResult(intent, requestCode);
            }
        } catch (final ClassNotFoundException e) {
            return;
        }
        navigateToWithAnim(0, 0);
    }

    public void navigateTo(final String activityName, final Intent intent, int resIn, int resOut) {
        navigateTo(activityName, intent);
        this.overridePendingTransition(resIn, resOut);
    }

    public void navigateToWithAnim( int resIn, int resOut) {
        if (resIn == 0 || resOut == 0) {
            this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
    /**
     * 跳转器
     * @param intent:
     */
    public void navigateTo(@NonNull final Intent intent) {
        startActivity(intent);
        navigateToWithAnim(0, 0);
    }

    public void navigateFinish(Activity activity){
        activity.finish();
        navigateToWithAnim(0, 0);
    }

}
