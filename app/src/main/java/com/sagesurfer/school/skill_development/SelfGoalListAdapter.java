package com.sagesurfer.school.skill_development;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sagesurfer.school.R;
import com.sagesurfer.school.resources.GetTime;

import java.util.ArrayList;

/**
 * @author Rahul Maske (monikam@sagesurfer.com)
 * Created on 10-06-2022
 * Last Modified on
 */

public class SelfGoalListAdapter extends RecyclerView.Adapter {
    private final ArrayList<Goal_> goalArrayList;
    private final static String TAG = SelfGoalListAdapter.class.getSimpleName();
    private final Activity activity;
    private final GoalListAdapterListener goalListAdapterListener;
    public SelfGoalListAdapter(Activity activity, ArrayList<Goal_> goalArrayList, GoalListAdapterListener goalListAdapterListener) {
        this.goalArrayList = goalArrayList;
        this.activity = activity;
        this.goalListAdapterListener = goalListAdapterListener;
    }

    @Override
    public int getItemCount() {
        return goalArrayList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(ArrayList<Goal_> goalArrayList) {
        goalArrayList.clear();
        goalArrayList.addAll(goalArrayList);
        this.notifyDataSetChanged();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, date, percentText, statusText, inputTxt,inputLabel;
        TextView tv_goal_title,tv_today_status,tv_today_status_text,tv_goal_date,tv_final_status;

        ImageView imageviewstatusdisplay;

        ConstraintLayout mainLayout;
        //AppCompatImageView pinIcon;
        ProgressBar progressBar;

        CardView cardview;

        public myViewHolder(View view) {
            super(view);
            tv_goal_title = (TextView) view.findViewById(R.id.tv_goal_title);
            tv_today_status = (TextView) view.findViewById(R.id.tv_today_status);
            tv_today_status_text = (TextView) view.findViewById(R.id.tv_today_status_text);
            tv_goal_date = (TextView) view.findViewById(R.id.tv_goal_date);
            percentText = (TextView) view.findViewById(R.id.selfgoallistitem_percent);
            tv_final_status = (TextView) view.findViewById(R.id.tv_final_status);


            cardview=view.findViewById(R.id.cardview);

            progressBar = (ProgressBar) view.findViewById(R.id.selfgoallistitem_pb);

           // imageviewstatusdisplay=view.findViewById(R.id.imageviewstatusdisplay);

           // pinIcon = (AppCompatImageView) view.findViewById(R.id.selfgoallistitem_location);

            mainLayout =  view.findViewById(R.id.main_layout);
        }
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_skill_development_list_item, parent, false);

        return new myViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        final myViewHolder viewHolder = (myViewHolder) holder;
        final Goal_ goal_ = goalArrayList.get(position);
        if (goal_.getStatus() != 1) {
            viewHolder.mainLayout.setVisibility(View.GONE);
        }
        viewHolder.mainLayout.setVisibility(View.VISIBLE);
        viewHolder.tv_goal_title.setText(goal_.getName());
        ////////////////////////////////////////viewHolder.tv_goal_date.setText(GetTime.month_DdYyyy(goal_.getStart_date()) + " to " + GetTime.month_DdYyyy(goal_.getEnd_date()));
        viewHolder.tv_goal_date.setText(GetTime.month_DdYyyy(goal_.getStart_date()) + " to " + GetTime.month_DdYyyy(goal_.getEnd_date()));
        String goalProgress = goal_.getProgress() + "%";
        viewHolder.percentText.setText(goalProgress);
        setStatus(goal_.getGoal_status(), viewHolder);
        viewHolder.progressBar.setProgress(goal_.getProgress());

        if(goal_.getGoal_status() == 1){
            if(goal_.getGoal_type() == 2){
                viewHolder.progressBar.setProgress(100);
                goalProgress = "100%";
                viewHolder.percentText.setText(goalProgress);
            }
        }

        //setPin(goal_.getIs_dashboard(), viewHolder);

        if (goal_.getToday_status() == 1) {
            viewHolder.tv_today_status.setVisibility(View.VISIBLE);
            viewHolder.tv_today_status_text.setVisibility(View.VISIBLE);
            //viewHolder.inputLabel.setVisibility(View.VISIBLE);
//            viewHolder.mainLayout.setBackgroundResource(R.drawable.red_rounded_border);
            viewHolder.cardview.setBackgroundResource(R.drawable.cardview_red);
            viewHolder.tv_today_status.setText("Input Needed");
            viewHolder.tv_today_status.setTextColor(activity.getResources().getColor(R.color.self_goal_missed));
        } else if (goal_.getToday_status() == 2) {
            viewHolder.tv_today_status.setVisibility(View.VISIBLE);
            viewHolder.tv_today_status_text.setVisibility(View.VISIBLE);
           // viewHolder.inputLabel.setVisibility(View.VISIBLE);
//            viewHolder.mainLayout.setBackgroundResource(R.drawable.green_rounded_border);
            viewHolder.cardview.setBackgroundResource(R.drawable.carview_green);
            viewHolder.tv_today_status.setText("Input Provided");
            viewHolder.tv_today_status.setTextColor(activity.getResources().getColor(R.color.self_goal_completed));
        } else {
//            viewHolder.mainLayout.setBackgroundResource(R.drawable.gray_rounded_border);
            viewHolder.cardview.setBackgroundResource(R.drawable.cardview_whiteborder);
            viewHolder.tv_today_status.setVisibility(View.GONE);
            viewHolder.tv_today_status_text.setVisibility(View.GONE);
          //  viewHolder.inputLabel.setVisibility(View.GONE);
        }

        viewHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalListAdapterListener.onGoalItemClicked(position, goal_);
            }
        });

        applyReadStatus(viewHolder, goalArrayList.get(position));
    }

    private void applyReadStatus(myViewHolder holder, Goal_ goal_) {
        if (goal_.getIs_read() == 1) {
            holder.tv_goal_title.setTextColor(ContextCompat.getColor(activity, R.color.text_color_read));
        } else {
            holder.tv_goal_title.setTextColor(ContextCompat.getColor(activity, R.color.text_color_primary));
        }
    }

    public interface GoalListAdapterListener {
        void onGoalItemClicked(int position, Goal_ goal_);
    }

    private void setPin(int isDashboard, myViewHolder viewHolder) {
       /* if (isDashboard == 1) {
            viewHolder.pinIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.pinIcon.setVisibility(View.GONE);
        }

        //Hide Blue Pin goal icon For Senjam. as senjam have all custom goal only
        if (BuildConfig.FLAVOR.equalsIgnoreCase("senjam")) {
            viewHolder.pinIcon.setVisibility(View.GONE);
        }*/
    }

    private void setStatus(int status, myViewHolder viewHolder) {
        Resources resources = activity.getApplicationContext().getResources();
        Rect bounds = viewHolder.progressBar.getProgressDrawable().getBounds();
        viewHolder.tv_final_status.setVisibility(View.GONE);
        if (status == 0) {
            // viewHolder.imageviewstatusdisplay.setImageResource(R.drawable.ic_goal3);
            viewHolder.tv_final_status.setBackgroundResource(R.drawable.goal_active_rounded_rectangle);
            viewHolder.progressBar.setProgressDrawable(resources.getDrawable(R.drawable.goal_progress_active));
            viewHolder.tv_today_status.setText(resources.getString(R.string.active));
            viewHolder.percentText.setTextColor(resources.getColor(R.color.self_goal_active));
        } else if (status == 1) {
            //viewHolder.imageviewstatusdisplay.setImageResource(R.drawable.ic_goal1);
            viewHolder.tv_final_status.setBackgroundResource(R.drawable.goal_completed_rounded_rectangle);
            viewHolder.progressBar.setProgressDrawable(resources.getDrawable(R.drawable.goal_progress_completed));
            viewHolder.tv_today_status.setText(resources.getString(R.string.completed));
            viewHolder.percentText.setTextColor(resources.getColor(R.color.self_goal_completed));
        } else {
           // viewHolder.imageviewstatusdisplay.setImageResource(R.drawable.ic_goal2);
            viewHolder.tv_final_status.setBackgroundResource(R.drawable.goal_missed_rounded_rectangle);
            viewHolder.progressBar.setProgressDrawable(resources.getDrawable(R.drawable.goal_progress_missed));
            viewHolder.tv_today_status.setText(resources.getString(R.string.missed));
            viewHolder.percentText.setTextColor(resources.getColor(R.color.self_goal_missed));
        }
        viewHolder.progressBar.getProgressDrawable().setBounds(bounds);
    }

}