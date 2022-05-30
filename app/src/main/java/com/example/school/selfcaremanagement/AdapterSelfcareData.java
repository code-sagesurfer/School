package com.example.school.selfcaremanagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class AdapterSelfcareData extends RecyclerView.Adapter<AdapterSelfcareData.ViewHolderSelfCare>{
    Context mContext;
    Fragment fragment;
    ArrayList<Content_> contentArrayList;
    private static final String TAG = "AdapterPlannerData";
    public AdapterSelfcareData(Context mContext, ArrayList<Content_> contentArrayList) {
        this.mContext = mContext;
        this.contentArrayList = contentArrayList;
    }

    @NonNull
    @Override
    public ViewHolderSelfCare onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspirational_content_item, parent, false);
        return new ViewHolderSelfCare(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSelfCare holder, int position) {
        Content_ contents=contentArrayList.get(position);
        holder.tv_title.setText(contents.getTitle());
        holder.tv_date.setText("Static date");
        if (contents.getThumb_path() != null) {
            Glide.with(mContext)
                    .load(contents.getThumb_path())
                    .into(holder.iv_main);
        }
    }

    @Override
    public int getItemCount() {
        return contentArrayList.size();
    }

    class ViewHolderSelfCare extends RecyclerView.ViewHolder {
        TextView tv_title,tv_date,tv_duration;
        RoundedImageView iv_main;
        public ViewHolderSelfCare(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_duration=itemView.findViewById(R.id.tv_duration);
            iv_main=itemView.findViewById(R.id.iv_main);
        }
    }
}
