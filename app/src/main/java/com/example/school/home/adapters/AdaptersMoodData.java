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

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.MoodJournalDataMood_;
import com.example.school.home.MoodJournalData_;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptersMoodData extends RecyclerView.Adapter<AdaptersMoodData.ViewHolderMood> {
    Context mContext;
    Fragment fragment;
    public List<MoodJournalDataMood_> moodJournalData_list;

    public AdaptersMoodData(Context mContext, List<MoodJournalDataMood_> journalList) {
        this.mContext = mContext;
        this.fragment = fragment;
        this.moodJournalData_list = journalList;
    }

    @NonNull
    @Override
    public ViewHolderMood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_mood_item, parent, false);
        return new ViewHolderMood(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMood holder, int position) {
        MoodJournalDataMood_ data = moodJournalData_list.get(position);
        Log.i("AdapterMedicineNCounts", "onBindViewHolder: "+data.getMood_url());
        if (data.getMood_url() != null) {
            Glide.with(mContext)
                    .load(data.getMood_url())
                    .into(holder.circleImageView);
        }

        holder.tv_mood_type.setText(data.getMood());
        holder.tv_date.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return moodJournalData_list.size();
    }

    class ViewHolderMood extends RecyclerView.ViewHolder{
        TextView tv_mood_type,tv_date;
        CircleImageView circleImageView;
        public ViewHolderMood(@NonNull View itemView) {
            super(itemView);
            tv_mood_type=itemView.findViewById(R.id.tv_mood_type);
            circleImageView=itemView.findViewById(R.id.circleImageView);
            tv_date=itemView.findViewById(R.id.tv_date);
        }
    }
}
