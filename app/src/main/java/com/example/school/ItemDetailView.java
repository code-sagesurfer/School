package com.example.school;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.school.home.ModelPlannerData;
import com.example.school.resources.AppLog;
import com.makeramen.roundedimageview.RoundedImageView;

public class ItemDetailView {

    public void showDetailDialog(Activity activity, Context context, String TAG, ModelDetailData modelPlannerData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_detail, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        AppLog.i(TAG, " handleIntent showMembersDialog: ");
        //AppCompatImageView iv_main_banner = view.findViewById(R.id.iv_main_banner);
        // Glide.with(getContext()).load(categoryResponse.getBanner_image()).into(iv_main_banner);

        RoundedImageView iv_planner_item = view.findViewById(R.id.iv_planner_item);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_desc = view.findViewById(R.id.tv_desc);
        tv_title.setText(modelPlannerData.getTitle());
        tv_date.setText(modelPlannerData.getDate());
        tv_desc.setText(modelPlannerData.getDesc());




        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
