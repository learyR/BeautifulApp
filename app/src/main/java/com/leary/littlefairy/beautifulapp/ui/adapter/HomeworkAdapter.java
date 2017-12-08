package com.leary.littlefairy.beautifulapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.model.Entity.HomeWork;
import com.leary.littlefairy.beautifulapp.model.util.GlobalVariable;
import com.leary.littlefairy.beautifulapp.ui.viewholder.FooterViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/7.
 */

public class HomeworkAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<HomeWork> mList;
    private boolean isMore;
    RecyclerView parent;
    private int footerState;

    public HomeworkAdapter(Context context, List<HomeWork> list) {
        this.context = context;
        this.mList = list;
    }

    public int getFooterState() {
        return footerState;
    }

    public void setFooterState(int footerState) {
        this.footerState = footerState;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = (RecyclerView) parent;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case  GlobalVariable.TYPE_FOOTER:
                view = inflater.inflate(R.layout.item_footer, parent, false);
                holder = new FooterViewHolder(view);
                break;
            case  GlobalVariable.TYPE_ITEM:
                view = inflater.inflate(R.layout.item_release_homework, parent, false);
                holder = new ReleaseHomeworkViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.tvFooter.setText(getFooter());
            return;
        }
        ReleaseHomeworkViewHolder viewHolder = (ReleaseHomeworkViewHolder) holder;
        HomeWork homeWork = mList.get(position);
//        if (!TextUtils.isEmpty(homeWork.getCode())) {
//            viewHolder.ivSubject.setImageResource(I.subjectRes[Integer.valueOf(homeWork.getCode()) % 1000]);
//        }
        Glide.with(context).load(homeWork.getSubjectPic())
                .dontAnimate()
                .crossFade()
                .into(viewHolder.ivSubject);
        viewHolder.tvHomeworkContent.setText(homeWork.getDescription());
        viewHolder.tvReleaseTime.setText(homeWork.getReleaseTime() + " " + homeWork.getWeek());
        viewHolder.itemHomework.setTag(homeWork.getId());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return  GlobalVariable.TYPE_FOOTER;
        }
        return  GlobalVariable.TYPE_ITEM;
    }

    private String getFooter() {
        if (footerState ==  GlobalVariable.FOOTER_HAVE_MORE) {
            return context.getString(R.string.load_more);
        } else if (footerState ==  GlobalVariable.FOOTER_NO_MORE) {
            return context.getString(R.string.no_more);
        } else {
            return "";
        }
    }

    public void initData(List<HomeWork> homeWorkList) {
        if (mList != null) {
            this.mList.clear();
        }
        this.mList.addAll(homeWorkList);
        notifyDataSetChanged();
    }

    public void addData(List<HomeWork> homeWorkList) {
        this.mList.addAll(homeWorkList);
        notifyDataSetChanged();
    }



    class ReleaseHomeworkViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_subject)
        ImageView ivSubject;
        @BindView(R.id.tv_class_name)
        TextView tvHomeworkContent;
        @BindView(R.id.tv_release_time)
        TextView tvReleaseTime;
        @BindView(R.id.item_homework)
        RelativeLayout itemHomework;

        ReleaseHomeworkViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
