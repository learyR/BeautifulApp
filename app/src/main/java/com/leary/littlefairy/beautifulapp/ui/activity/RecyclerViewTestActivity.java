package com.leary.littlefairy.beautifulapp.ui.activity;

import com.leary.littlefairy.beautifulapp.base.BaseRecyclerViewActivity;
import com.leary.littlefairy.beautifulapp.base.BaseRecyclerViewAdapter;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportEntity;
import com.leary.littlefairy.beautifulapp.ui.adapter.RepairReportRecyclerAdapter;
import com.leary.littlefairy.beautifulapp.ui.persenter.contracts.RecyclerViewContracts;
import com.leary.littlefairy.beautifulapp.ui.persenter.implement.RecyclerViewTestPresenter;
import com.leary.littlefairy.beautifulapp.widget.FamiliarToolbar;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RecyclerViewTestActivity extends BaseRecyclerViewActivity<RecyclerViewTestPresenter, RepairReportEntity> implements RecyclerViewContracts.View {
    // 当前分页位置
    private int page = 0;
    @Override
    protected BaseRecyclerViewAdapter<RepairReportEntity> getRecyclerAdapter() {
        return new RepairReportRecyclerAdapter(this);
    }

    @Override
    public void onRefreshListOk() {

    }

    @Override
    public void onRefreshListFinish() {

    }

    @Override
    public void onRefreshListError() {

    }

    @Override
    protected void initWidget() {
        isShowDivideLine =true;
        super.initWidget();
        mToolbar.showRightNav(FamiliarToolbar.NORMAL);
        mToolbar.setNormalRightText("上报");
        mToolbar.setTitleText("报事报修");
    }

    @Override
    protected void initInject() {
        mPresenter = new RecyclerViewTestPresenter();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        if (mPresenter.loadMoreCall != null && !mPresenter.loadMoreCall.isCanceled()) {
            mPresenter.loadMoreCall.cancel();
            mPresenter.loadMoreCall = null;
        }
        if (mIsRefresh) {
            page = 1;
        } else {
            page++;
        }
        mPresenter.getList(page, mDefaultCallback, this);
    }
}
