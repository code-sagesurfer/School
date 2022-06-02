package com.example.school.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.adapters.AdaptersMoodData;
import com.example.school.resources.AppLog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoodAdapterForHomeListing extends RecyclerView.Adapter<MoodAdapterForHomeListing.ViewHolderMood>{
    Context mContext;
    Fragment fragment;
    public List<Mood> moodList;
    private static final String TAG = "AdaptersMoodData";
    public MoodAdapterForHomeListing(Context mContext, List<Mood> moodList) {
        this.mContext = mContext;
        this.fragment = fragment;
        this.moodList = moodList;
    }

    @NonNull
    @Override
    public ViewHolderMood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_mood_item, parent, false);
        return new ViewHolderMood(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMood holder, int position) {
        Mood data = moodList.get(position);
        AppLog.i(TAG, "onBindViewHolder: "+data.getMood_url());
        if (data.getMood_url() != null) {
            Glide.with(mContext)
                    .load(data.getMood_url())
                    .into(holder.circleImageView);
        }

        holder.tv_mood_type.setText(data.getMood_name());

        holder.tv_mood_count.setText(data.getTotal_mood());
    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }

    class ViewHolderMood extends RecyclerView.ViewHolder{
        TextView tv_mood_type,tv_date,tv_mood_count;
        CircleImageView circleImageView;
        public ViewHolderMood(@NonNull View itemView) {
            super(itemView);
            tv_mood_type=itemView.findViewById(R.id.tv_mood_type);
            circleImageView=itemView.findViewById(R.id.circleImageView);
            tv_mood_count=itemView.findViewById(R.id.tv_mood_count);
            tv_date=itemView.findViewById(R.id.tv_date);
        }
    }
}
