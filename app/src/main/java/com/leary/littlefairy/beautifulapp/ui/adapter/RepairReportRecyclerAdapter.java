package com.leary.littlefairy.beautifulapp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leary.littlefairy.beautifulapp.R;
import com.leary.littlefairy.beautifulapp.base.BaseRecyclerViewAdapter;
import com.leary.littlefairy.beautifulapp.model.Entity.RepairReportEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/10/23.
 */

public class RepairReportRecyclerAdapter extends BaseRecyclerViewAdapter<RepairReportEntity> {

    public int[] COLORS = {0xff51c6ec,0xff51ecac,0xffee76d3, 0xff51ecd1,0xfffa6d9b,0xffffb45b};
    public RepairReportRecyclerAdapter(Context context) {
        super(context,ONLY_FOOTER);
    }
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ViewHolder(mInflater.inflate(R.layout.view_repair_item, parent, false));
    }
    public boolean isShowName = false;
    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final RepairReportEntity item, int position) {
        final ViewHolder vh = (ViewHolder) holder;
        vh.itemName.setText(TextUtils.isEmpty(item.getApplyPerson())? "" : item.getApplyPerson());
        vh.itemTime.setText(item.getCreateTime());
        vh.itemType.setText(TextUtils.isEmpty(item.getTypeName())? "" : "[" + item.getTypeName()+ "]");
        vh.itemDescript.setText(TextUtils.isEmpty(item.getDescription())? "" : item.getDescription());
        vh.itemHeaderName.setText(TextUtils.isEmpty(item.getApplyPerson())? "" : item.getApplyPerson());
        Drawable colorDra = new ColorDrawable(COLORS[position % COLORS.length]);
        vh.itemHeaderBg.setImageDrawable(colorDra);
//        状态0: 上报中, 1: 待处理, 2: 已处理, 3: 已评价
        switch (item.getStatus()) {
            case 0:
//                vh.tvState.setText("上报中");
                vh.tvState.setImageResource(R.drawable.repair_wait);
                break;
            case 1:
//                vh.tvState.setText("待处理");
                vh.tvState.setImageResource(R.drawable.repair_wait);
                break;
            case 2:
//                vh.tvState.setText("已处理");
                vh.tvState.setImageResource(R.drawable.repair_done);
                break;
            case 3:
//                vh.tvState.setText("已评价");
                vh.tvState.setImageResource(R.drawable.repair_appraise);
                break;
            default:
                break;
        }
        Glide.with(mContext).load(item.getApplyPortrait())
                .error(new ColorDrawable(Color.TRANSPARENT))
                .into(vh.itemHeader);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_name_tv)
        public TextView itemName;
        @BindView(R.id.item_type_tv)
        public TextView itemType;
        @BindView(R.id.item_time_tv)
        public TextView itemTime;
        @BindView(R.id.item_descript_tv)
        public TextView itemDescript;
        @BindView(R.id.tv_state)
        public ImageView tvState;
        @BindView(R.id.item_header)
        public CircleImageView itemHeader;
        @BindView(R.id.item_header_bg)
        public CircleImageView itemHeaderBg;
        @BindView(R.id.item_header_name)
        public TextView itemHeaderName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
