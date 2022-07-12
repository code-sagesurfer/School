package com.sagesurfer.school.support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import com.sagesurfer.school.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyHolder> {


    private Context context;
    public ArrayList<ModelGetFaq> objList;
    View.OnClickListener clickListener = null;

    public FAQAdapter(Context context, View.OnClickListener clickListener) {
        objList = new ArrayList<>();
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_faq, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.faq_que.setText(objList.get(position).getQuestionTitle());
        holder.tv_faq_que_desc.setText(objList.get(position).getAnswer());

        if (objList.get(position).getIsSelected()==1){
            holder.tv_faq_que_desc.setVisibility(View.VISIBLE);
            holder.faq_que.setTextColor(context.getResources().getColor(R.color.color_primary));
            holder.iv_faq_btn.setImageResource(R.drawable.ic_down_arrow);
        }else{
            holder.faq_que.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_faq_que_desc.setVisibility(View.GONE);
            holder.iv_faq_btn.setImageResource(R.drawable.ic_arrow_forward);

        }

        /*
        if (objList.get(position).isVisible().equals("1")) {
            holder.lblTitle.setTextColor(Color.parseColor("#03a9f4"));
        } else {
            holder.lblTitle.setTextColor(Color.BLACK);
        }
        holder.btnForward.setImageResource(objList.get(position).isVisible().equals("1") ? R.drawable.down_icon : R.drawable.ic_arrow_endright);
        holder.lblDescription.setVisibility(objList.get(position).isVisible().equals("1") ? View.VISIBLE : View.GONE);
        holder.btnForward.setTag(position);
        holder.btnForward.setOnClickListener(clickListener);*/

    }



    @Override
    public int getItemCount() {
        return objList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.faq_que)
        TextView faq_que;

        @BindView(R.id.iv_faq_btn)
        ImageView iv_faq_btn;

        @BindView(R.id.tv_faq_que_desc)
        TextView tv_faq_que_desc;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            iv_faq_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.iv_faq_btn){
                tv_faq_que_desc.setVisibility(View.VISIBLE);
                changeData(getAbsoluteAdapterPosition());
            }
        }
    }

    public void addData(List<ModelGetFaq> obj) {
        objList = new ArrayList<>();
        objList.addAll(obj);
        objList.get(0).setIsSelected(1);
        this.notifyDataSetChanged();
    }

    public void changeData(int absoluteAdapterPosition){

        for (ModelGetFaq getFaq :objList){
            if (getFaq.getIsSelected()==1){
                getFaq.setIsSelected(0);
            }
        }
        if (objList.get(absoluteAdapterPosition).getIsSelected()==0) {
            objList.get(absoluteAdapterPosition).setIsSelected(1);
        }else{
            objList.get(absoluteAdapterPosition).setIsSelected(0);
        }
        this.notifyDataSetChanged();
    }

}
