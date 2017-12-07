package com.leary.littlefairy.beautifulapp.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.RequestManager;
import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.model.Entity.Result;
import com.leary.littlefairy.beautifulapp.model.network.DefaultCallback;
import com.leary.littlefairy.beautifulapp.utils.DeviceUtils;
import com.leary.littlefairy.beautifulapp.utils.DivisionDecoration;
import com.leary.littlefairy.beautifulapp.widget.EmptyLayout;
import com.leary.littlefairy.beautifulapp.widget.RecyclerRefreshLayout;

import java.util.Date;
import java.util.List;

import okhttp3.Headers;


/**
 * 基本列表类，重写getLayoutId()自定义界面
 * Created by longhailin
 * on 2016/4/12.
 */
public abstract class BaseRecyclerViewFragment<T extends BasePresenter, M> extends BaseFragment implements
        BaseRecyclerViewAdapter.OnItemClickListener,
        RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseRecyclerViewAdapter.Callback ,View.OnClickListener {

    protected RecyclerRefreshLayout mRefreshLayout;

    protected RecyclerView mRecyclerView;

    protected BaseRecyclerViewAdapter<M> mAdapter;
    protected MyDefaultCallback mDefaultCallback;
    protected boolean mIsRefresh;
    protected EmptyLayout mErrorLayout;
    /**
     * 显示没有更多数据的列表条数阈值
     */
    protected int mMaxNoMoreNumber = 20;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRefreshLayout = (RecyclerRefreshLayout) root.findViewById(R.id.refreshLayout);
        mErrorLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
        mDefaultCallback = new MyDefaultCallback(getActivity());
        mAdapter = mAdapter == null ? getRecyclerAdapter() : mAdapter;
        mAdapter.setState(BaseRecyclerViewAdapter.STATE_HIDE, false);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (isShowDivideLine)
            mRecyclerView.addItemDecoration(new DivisionDecoration(
                    getContext(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.list_divide_line)));
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState
                        && getActivity().getCurrentFocus() != null) {
                    DeviceUtils.hideSoftKeyboard(getActivity().getCurrentFocus());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mErrorLayout.setOnLayoutClickListener(this);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerViewAdapter.STATE_HIDE, false);
        mRefreshLayout.setColorSchemeResources(
                R.color.red_light, R.color.green_light,
                R.color.blue_light, R.color.orange_light);
    }

    protected boolean isShowDivideLine = false;

    @SuppressWarnings("unchecked")
    @Override
    public void initData() {
        boolean isNeedEmptyView = isNeedEmptyView();
        if (isNeedEmptyView) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            mRefreshLayout.setVisibility(View.GONE);
            onRefreshing();
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    onRefreshing();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Log.e("","onLoadMore=========onClick==");
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefreshing();
    }

    @Override
    public void onItemClick(int position, long itemId) {
        Log.e("","onItemClick=========onItemClick==");
        onItemClick(mAdapter.getItem(position), position);
    }

    @Override
    public void onRefreshing() {
        mIsRefresh = true;
        requestData();
    }

    @Override
    protected void initInject() {

    }

    @Override
    public void onLoadMore() {

        mAdapter.setState(mRefreshLayout.isRefreshing() ? BaseRecyclerViewAdapter.STATE_HIDE : BaseRecyclerViewAdapter.STATE_LOADING, true);
        requestData();
    }

    protected void onItemClick(M item, int position) {

    }

    protected void requestData() {

    }

    protected void setListData(List<M> resultBean) {
        if (mIsRefresh) {
            mAdapter.clear();
            mAdapter.addAll(resultBean);
            mRefreshLayout.setCanLoadMore(true);
        } else {
            mAdapter.addAll(resultBean);
        }

        if (resultBean != null && resultBean.size() < mMaxNoMoreNumber){//总条数
            mRefreshLayout.setCanLoadMore(false);
        }
        if (mAdapter.getCount() < mMaxNoMoreNumber){//总条数
            mRefreshLayout.setCanLoadMore(false);
            mAdapter.setState(BaseRecyclerViewAdapter.STATE_HIDE, true);
        }else{
            mAdapter.setState(resultBean == null || resultBean.size() < mMaxNoMoreNumber ? BaseRecyclerViewAdapter.STATE_NO_MORE : BaseRecyclerViewAdapter.STATE_LOADING, true);
        }

        if (mAdapter.getItems().size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setErrorType(
                    isNeedEmptyView()
                            ? EmptyLayout.NODATA
                            : EmptyLayout.HIDE_LAYOUT);
        }

    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    protected abstract BaseRecyclerViewAdapter<M> getRecyclerAdapter();

    @Override
    public RequestManager getImgLoader() {
        return getImageLoader();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public Date getSystemTime() {
        return new Date();
    }

    protected void prevListData(Result<List<M>> data){

    }
    public class MyDefaultCallback extends DefaultCallback<Result<List<M>>> {
        public MyDefaultCallback(@NonNull Activity activity) {
            super(activity);
        }

        @Override
        public boolean onResultOk(int code, Headers headers, Result<List<M>> result) {
            prevListData(result);
            Log.e("","getDetail====="+result.getDetail().size());
            setListData(result.getDetail());
            onRefreshListOk();
            return false;
        }


        @Override
        public boolean onCallCancel() {
            onRefreshListFailed();
            return true;
        }

        @Override
        public boolean onResultAuthError(int code, Headers headers, Result.Error error) {
            onRefreshListFailed();
            return super.onResultAuthError(code, headers, error);
        }

        @Override
        public boolean onResultOtherError(int code, Headers headers, Result.Error error) {
            onRefreshListFailed();
            return super.onResultOtherError(code, headers, error);
        }

        @Override
        public boolean onCallException(Throwable t, Result.Error error) {
            onRefreshListFailed();
            return super.onCallException(t, error);
        }

        @Override
        public void onFinish() {
            onRefreshListFinish();
        }

    }

    protected void onRefreshListStart(){}
    protected void onRefreshListOk(){
        mRefreshLayout.onComplete();
        mIsRefresh = false;
    }
    protected void onRefreshListFinish(){
        mRefreshLayout.onComplete();
        mIsRefresh = false;
    }

    protected void onRefreshListFailed(){
        if (mAdapter.getItems().size() == 0) {
            mAdapter.setState(BaseRecyclerViewAdapter.STATE_LOAD_ERROR, true);
            if (isNeedEmptyView()) mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        } else {
            mAdapter.setState(BaseRecyclerViewAdapter.STATE_NO_MORE, true);
        }
//        mRefreshLayout.onComplete();
    }

    /**
     * 需要缓存
     *
     * @return isNeedCache
     */
    protected boolean isNeedCache() {
        return true;
    }

    /**
     * 需要空的View
     *
     * @return isNeedEmptyView
     */
    protected boolean isNeedEmptyView() {
        return true;
    }




}
