package com.example.school.home.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.school.R;
import com.example.school.home.PlannerData;

import java.util.ArrayList;

public class AdapterPlannerData extends RecyclerView.Adapter<AdapterPlannerData.ViewHolderPlanner>{
    Context mContext;
    Fragment fragment;
    ArrayList<PlannerData> dataArrayList;
    private static final String TAG = "AdapterPlannerData";
    public AdapterPlannerData(Context mContext, ArrayList<PlannerData> dataArrayList) {
        this.mContext = mContext;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public ViewHolderPlanner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_planner_item, parent, false);
        return new ViewHolderPlanner(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPlanner holder, int position) {
        PlannerData plannerData=dataArrayList.get(position);
        holder.tv_planner_title.setText(plannerData.getName());
        holder.tv_date.setText(plannerData.getcDate());
        Log.i(TAG, "onBindViewHolder: "+plannerData.getName());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class ViewHolderPlanner extends RecyclerView.ViewHolder {
        TextView tv_planner_title,tv_date;
        public ViewHolderPlanner(@NonNull View itemView) {
            super(itemView);
            tv_planner_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
        }
    }
}
