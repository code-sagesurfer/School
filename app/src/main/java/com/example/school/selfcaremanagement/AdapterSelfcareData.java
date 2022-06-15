package com.example.school.selfcaremanagement;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.school.ModelDetailData;
import com.example.school.R;
import com.example.school.emotional_support.FragmentEmotionalSupport;
import com.example.school.social_activity.SocialActivityFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class AdapterSelfcareData extends RecyclerView.Adapter<AdapterSelfcareData.ViewHolderSelfCare>{
    Context mContext;
    Fragment fragment;
    ArrayList<Content_> contentArrayList;
    private static final String TAG = "AdapterPlannerData";
    public AdapterSelfcareData(Context mContext, ArrayList<Content_> contentArrayList,Fragment fragment) {
        this.mContext = mContext;
        this.contentArrayList = contentArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolderSelfCare onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (fragment instanceof FragmentEmotionalSupport) {
            View  itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inspirational_content_item, parent, false);
            return new ViewHolderSelfCare(itemView);
        }else if (fragment instanceof SocialActivityFragment) {
            View  itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_planner_item, parent, false);
            return new ViewHolderSelfCare(itemView);
        }else {
            View  itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_planner_item, parent, false);
            return new ViewHolderSelfCare(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSelfCare holder, int position) {
        Content_ contents=contentArrayList.get(position);
        holder.tv_title.setText(contents.getTitle());
        holder.tv_date.setText("Static date");
        if (contents.getThumb_path() != null) {
            Glide.with(mContext)
                    .load(contents.getThumb_path())
                    .placeholder(mContext.getDrawable(R.drawable.placeholder))
                    .into(holder.iv_main);

        }
    }

    @Override
    public int getItemCount() {
        return contentArrayList.size();
    }

    public class ViewHolderSelfCare extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_title,tv_date,tv_duration;
        RoundedImageView iv_main;
        public ViewHolderSelfCare(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_duration=itemView.findViewById(R.id.tv_duration);
            iv_main=itemView.findViewById(R.id.iv_main);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Content_ content_= contentArrayList.get(getAbsoluteAdapterPosition());
            if (fragment instanceof FragmentEmotionalSupport) {
                FragmentEmotionalSupport emotionalSupport=(FragmentEmotionalSupport) fragment;

            }else if (fragment instanceof FragmentSelfcareManagement){
                FragmentSelfcareManagement selfcareManagement= (FragmentSelfcareManagement) fragment;
                selfcareManagement.showDetailDialog(new ModelDetailData(""+content_.getTitle(),
                        "",""+content_.getThumb_path(),""+content_.getDescription()));
            }
        }
    }
}
