package com.sagesurfer.school.assessment.pendingforms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sagesurfer.school.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingFormListAdapter extends RecyclerView.Adapter<PendingFormListAdapter.PendingFormViewHolder> {
    private ArrayList<Forms_> formsArrayList;
    Context context;
    final int min = 200;
    final int max = 350;
    int random;

    public PendingFormListAdapter(ArrayList<Forms_> formsArrayList, Context context) {
        this.formsArrayList = formsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingFormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_assessment_list_item, parent, false);
        return new PendingFormViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingFormViewHolder holder, int position) {
        Forms_ item = formsArrayList.get(position);
        holder.txt_date.setText(item.getActual_date_time());
        holder.txt_title.setText(item.getForm_name());

        final int random = new Random().nextInt((max - min) + 1) + min;

    }

    @Override
    public int getItemCount() {
        return formsArrayList.size();
    }

    public void addData(ArrayList<Forms_> formsArrayList) {
        if (formsArrayList != null) {
            formsArrayList.addAll(formsArrayList);
        }
        notifyDataSetChanged();
    }

    class PendingFormViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_heading_text)
        TextView tv_heading_text;

        @BindView(R.id.iv_form_bg)
        ImageView iv_form_bg;

        @BindView(R.id.txt_title)
        TextView txt_title;

        @BindView(R.id.txt_last_score)
        TextView txt_last_score;

        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.cl_assessment_item_main)
        MaterialCardView cl_assessment_item_main;

        @BindView(R.id.cl_second)
        ConstraintLayout cl_second;

        public PendingFormViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
