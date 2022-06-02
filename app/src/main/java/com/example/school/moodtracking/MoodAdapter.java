package com.example.school.moodtracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.Mood;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {

    private List<Mood> listdata = new ArrayList<>();

    private Context mcontext;


    // RecyclerView recyclerView;
    public MoodAdapter(List<Mood> listdata, Context context) {
        this.listdata = listdata;
        this.mcontext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_mood_listing_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mood myListData = listdata.get(position);

        holder.tv_mood_title.setText(listdata.get(position).getMood_name());
        Glide.with(mcontext)
                .load(listdata.get(position).getMood_url())
                .placeholder(R.drawable.ic_user_male)
                .into(holder.iv_mood_image);

        holder.tv_counter.setText(listdata.get(position).getTotal_mood());


    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView iv_mood_image;

        TextView tv_mood_title,tv_date,tv_counter;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_mood_image = (CircleImageView) itemView.findViewById(R.id.iv_mood_image);
            tv_mood_title = (TextView) itemView.findViewById(R.id.tv_mood_title);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_counter = (TextView) itemView.findViewById(R.id.tv_counter);
        }
    }
}