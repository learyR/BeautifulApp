package com.leary.littlefairy.beautifulapp.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leary.littlefairy.beautifulapp.R;


/**
 * Created by Lr on 2017/3/15.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {
    public TextView tvFooter;
    public FooterViewHolder(View itemView) {
        super(itemView);
        tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);
    }
}
