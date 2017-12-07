package com.leary.littlefairy.beautifulapp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.leary.littlefairy.beautifulapp.utils.DialogUtils;
import com.leary.littlefairy.beautifulapp.utils.ToastUtils;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载
 * Fragment基类
 * create by longhailin
 */

public abstract class BaseFragment extends Fragment{
    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;
    private RequestManager mImgLoader;
    protected LayoutInflater mInflater;
    protected RequestManager mImageLoader;
    protected Unbinder unbinder = null;
    private ProgressDialog mWaitDialog;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            initInject();
            // Do something
            onBindViewBefore(mRoot);

            // Bind view
            ButterKnife.bind(this, mRoot);
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }


    protected abstract void initInject() ;

    protected void onBindViewBefore(View root) {
        // ...
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        synchronized (BaseFragment.class){
            if (unbinder != null){
                unbinder.unbind();
            }
            try {
                RequestManager manager = getImageLoader();
                if (manager != null)
                    manager.onDestroy();
            }catch (Exception e){

            }

        }

        mBundle = null;
    }

    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    protected void initWidget(View root) {

    }

    protected void initData() {

    }

    protected <T extends View> T findView(int viewId) {
        return (T) mRoot.findViewById(viewId);
    }

    protected <T extends Serializable> T getBundleSerializable(String key) {
        if (mBundle == null) {
            return null;
        }
        return (T) mBundle.getSerializable(key);
    }

    /**
     * 获取一个图片加载管理器
     *
     * @return RequestManager
     */
    public synchronized RequestManager getImgLoader() {
        if (mImgLoader == null)
            mImgLoader = Glide.with(this);
        return mImgLoader;
    }

    protected void onRestartInstance(Bundle bundle) {

    }

    public synchronized RequestManager getImageLoader() {
        synchronized (BaseFragment.this){
            if (mImageLoader == null)
                mImageLoader = Glide.with(this);
        }
        return mImageLoader;
    }


    public void showLoading(String message) {
        if (mWaitDialog == null) {
            mWaitDialog = DialogUtils.getProgressDialog(getContext(), true);
        }
        mWaitDialog.setMessage(message);
        mWaitDialog.show();
    }


    public void hideLoading() {
        if (mWaitDialog == null) return;
        synchronized (BaseActivity.class){
            if (mWaitDialog.isShowing())
                mWaitDialog.dismiss();
        }

    }


    public void tips(String tips) {
        ToastUtils.with(getActivity().getApplicationContext()).show(TextUtils.isEmpty(tips) ? "" : tips);
    }


    public void tips(@StringRes int tips) {
        ToastUtils.with(getActivity().getApplicationContext()).show(tips);
    }

}
