package com.sagesurfer.school.social_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sagesurfer.school.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterReward extends RecyclerView.Adapter<AdapterReward.RewardViewHolder> {

    List<Datax> dataList;
    Fragment fragment;
    Context context;

    public AdapterReward(Fragment fragment, List<Datax> dataList,Context context) {
        this.dataList = dataList;
        this.fragment = fragment;
        this.context=context;

    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_planner_item, parent, false);
        return new RewardViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        Datax dataxes= dataList.get(position);
        holder.tv_title.setText(dataxes.getTitle());
        holder.tv_date.setText(dataxes.getDate_time());
        if (dataxes.getAttached_image() != null) {
            Glide.with(context)
                    .load(dataxes.getAttached_image())
                    .placeholder(context.getDrawable(R.drawable.placeholder))
                    .into(holder.iv_main);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RewardViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_date;
        RoundedImageView iv_main;
        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            iv_main=itemView.findViewById(R.id.iv_main);
        }
    }
}
