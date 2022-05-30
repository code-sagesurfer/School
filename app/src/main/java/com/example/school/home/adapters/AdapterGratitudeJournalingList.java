package com.example.school.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.school.R;
import com.example.school.home.ModelGratitudeListingResponseData;
import com.example.school.home.MoodJournalDataMood_;

import java.util.ArrayList;

public class AdapterGratitudeJournalingList extends RecyclerView.Adapter<AdapterGratitudeJournalingList.ViewHolderJournaling> {
    Context mContext;
    Fragment fragment;
    ArrayList<ModelGratitudeListingResponseData> dataArrayList;

    public AdapterGratitudeJournalingList(ArrayList<ModelGratitudeListingResponseData> dataArrayList,Context mContext ) {
        this.mContext = mContext;
        this.fragment = fragment;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public ViewHolderJournaling onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_journaling_item, parent, false);
        return new ViewHolderJournaling(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJournaling holder, int position) {
        ModelGratitudeListingResponseData data = dataArrayList.get(position);
        holder.tv_title.setText(data.getGratituteName());
        holder.tv_location.setText("Location : Static data");
        holder.tv_date.setText(data.getAddedDate());
        holder.tv_date.setText(data.getAddedDate());
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class ViewHolderJournaling extends RecyclerView.ViewHolder{
        TextView tv_title,tv_location,tv_date;
        LinearLayout ll_like,ll_share;
        ConstraintLayout cl_view;
        public ViewHolderJournaling(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_location=itemView.findViewById(R.id.tv_location);
            tv_date=itemView.findViewById(R.id.tv_date);
            ll_like=itemView.findViewById(R.id.ll_like);
            ll_share=itemView.findViewById(R.id.ll_share);
            cl_view=itemView.findViewById(R.id.cl_view);
        }
    }
}