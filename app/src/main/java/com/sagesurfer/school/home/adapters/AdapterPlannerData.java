package com.sagesurfer.school.home.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.sagesurfer.school.ModelDetailData;
import com.sagesurfer.school.R;
import com.sagesurfer.school.home.HomeFragment;
import com.sagesurfer.school.home.ModelPlannerData;
import com.sagesurfer.school.home.dailyplanner.FragmentPlannerMain;

import java.util.ArrayList;

public class AdapterPlannerData extends RecyclerView.Adapter<AdapterPlannerData.ViewHolderPlanner>{
    Context mContext;
    Fragment fragment;
    ArrayList<ModelPlannerData> dataArrayList;
    private static final String TAG = "AdapterPlannerData";
    public AdapterPlannerData(Context mContext, ArrayList<ModelPlannerData> dataArrayList, Fragment fragment) {
        this.mContext = mContext;
        this.dataArrayList = dataArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolderPlanner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (fragment instanceof HomeFragment){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_planner_item, parent, false);
            return new ViewHolderPlanner(itemView);
        }else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.planner_main_listing_item, parent, false);
            return new ViewHolderPlanner(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlanner holder, int position) {
        ModelPlannerData modelPlannerData =dataArrayList.get(position);
        holder.tv_planner_title.setText(modelPlannerData.getName());
        holder.tv_date.setText(modelPlannerData.getcDate());
        Log.i(TAG, "onBindViewHolder: "+ modelPlannerData.getName());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class ViewHolderPlanner extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_planner_title,tv_date;
        public ViewHolderPlanner(@NonNull View itemView) {
            super(itemView);
            tv_planner_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ModelPlannerData modelPlannerData = dataArrayList.get(getAbsoluteAdapterPosition());
            if (fragment instanceof HomeFragment){
                HomeFragment homeFragment=(HomeFragment)fragment;
                homeFragment.showDetailDialog(new ModelDetailData(modelPlannerData.getName(),modelPlannerData.getcDate(),"",modelPlannerData.getDesc()));
            }else if (fragment instanceof FragmentPlannerMain){
                FragmentPlannerMain fragmentPlannerMain=(FragmentPlannerMain)fragment;
                //fragmentPlannerMain.showDetailDialog(dataArrayList.get(getAbsoluteAdapterPosition()));
                fragmentPlannerMain.showDetailDialog( new ModelDetailData(modelPlannerData.getName(),modelPlannerData.getcDate(),"",modelPlannerData.getDesc()));
            }
        }
    }
}
