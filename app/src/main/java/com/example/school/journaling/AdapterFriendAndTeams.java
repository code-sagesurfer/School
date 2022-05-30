package com.example.school.journaling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.school.R;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFriendAndTeams extends RecyclerView.Adapter<AdapterFriendAndTeams.ViewHolderData> {
    ArrayList<ModelFriendListResponseData> dataArrayList;
    ArrayList<ModelFriendListResponseData> mainArrayList;
    Context mContext;
    Fragment fragment;
    private static final String TAG = "AdapterGratitudeMainListing";

    public AdapterFriendAndTeams(ArrayList<ModelFriendListResponseData> dataArrayList, Context mContext, Fragment fragment) {
        this.dataArrayList = dataArrayList;
        this.mContext = mContext;
        this.fragment = fragment;
        mainArrayList = new ArrayList<>(dataArrayList);

    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item_layout, parent, false);
        return new ViewHolderData(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        ModelFriendListResponseData data = dataArrayList.get(position);
        holder.team_list_item_name.setText(data.getFullname());

        if (data.getSelected_status()==1){
            holder.team_list_item_check.setVisibility(View.VISIBLE);
            holder.team_list_item_add.setVisibility(View.GONE);
        }else{
            holder.team_list_item_check.setVisibility(View.GONE);
            holder.team_list_item_add.setVisibility(View.VISIBLE);
        }
        if (!data.getProfile_image().equalsIgnoreCase("")){
        Glide.with(mContext).load(data.getProfile_image()).into(holder.team_list_item_image);
        }
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView team_list_item_name;
        CircleImageView team_list_item_image;
        AppCompatImageView team_list_item_check,team_list_item_add;
        public ViewHolderData(@NonNull View itemView) {
            super(itemView);

            team_list_item_add = itemView.findViewById(R.id.team_list_item_add);
            team_list_item_name = itemView.findViewById(R.id.team_list_item_name);
            team_list_item_check = itemView.findViewById(R.id.team_list_item_name_check);
            team_list_item_image = itemView.findViewById(R.id.team_list_item_image);
            team_list_item_check.setOnClickListener(this);
            team_list_item_add.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition();
            if (v.getId()==R.id.team_list_item_name_check){
                team_list_item_check.setVisibility(View.GONE);
                team_list_item_add.setVisibility(View.VISIBLE);
                dataArrayList.get(position).setSelected_status(0);
            }else if(v.getId()==R.id.team_list_item_add){
                team_list_item_check.setVisibility(View.VISIBLE);
                team_list_item_add.setVisibility(View.GONE);
                dataArrayList.get(position).setSelected_status(1);
            }
        }
    }
    public ArrayList<ModelFriendListResponseData> getAllUpdatedList(){
        return dataArrayList;
    }
}
