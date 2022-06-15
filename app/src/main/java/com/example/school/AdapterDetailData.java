package com.example.school;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.home.HomeFragment;
import com.example.school.home.ModelPlannerData;
import com.example.school.home.dailyplanner.FragmentPlannerMain;

import java.util.ArrayList;

public class AdapterDetailData extends RecyclerView.Adapter<AdapterDetailData.ViewHolderPlanner> {
    Context mContext;
    Fragment fragment;
    ArrayList<ModelDetailData> dataArrayList;
    Activity activity;
    private static final String TAG = "AdapterPlannerData";

    public AdapterDetailData(Context mContext, Activity activity, ArrayList<ModelDetailData> dataArrayList, Fragment fragment) {
        this.mContext = mContext;
        this.dataArrayList = dataArrayList;
        this.fragment = fragment;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolderPlanner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.planner_main_listing_item, parent, false);
        return new ViewHolderPlanner(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlanner holder, int position) {
        ModelDetailData modelPlannerData = dataArrayList.get(position);
        holder.tv_planner_title.setText(modelPlannerData.getTitle());
        holder.tv_date.setText(modelPlannerData.getDate());
        Log.i(TAG, "onBindViewHolder: " + modelPlannerData.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class ViewHolderPlanner extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_planner_title, tv_date;

        public ViewHolderPlanner(@NonNull View itemView) {
            super(itemView);
            tv_planner_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ModelDetailData modelDetailData = dataArrayList.get(getAbsoluteAdapterPosition());
            if (fragment instanceof FragmentDetailDisplay) {
                FragmentDetailDisplay detailDisplay=new FragmentDetailDisplay();
                //fragmentPlannerMain.showDetailDialog(dataArrayList.get(getAbsoluteAdapterPosition()));
                detailDisplay.showDetailDialog(modelDetailData,mContext,activity);
            }
        }
    }
}
