package com.sagesurfer.school.home.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sagesurfer.school.R;

import java.util.ArrayList;

public class AdapterDrawerMenu extends RecyclerView.Adapter<AdapterDrawerMenu.ViewHolderMenus> {
    ArrayList<ModelDrawerMenuListItems> drawerMenuList;
    Context context;
    private static final String TAG = "AdapterDrawerMenu";
    public AdapterDrawerMenu(ArrayList<ModelDrawerMenuListItems> drawerMenuList, Context context) {
        this.drawerMenuList = drawerMenuList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderMenus onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_menu_item, parent, false);
        return new ViewHolderMenus(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMenus holder, int position) {
        ModelDrawerMenuListItems items = drawerMenuList.get(position);

            if (items.getName().equals("Divider")) {
                holder.view_divider.setVisibility(View.VISIBLE);
                holder.tv_name.setVisibility(View.GONE);
                holder.iv_icon.setVisibility(View.GONE);
            }else{
                holder.view_divider.setVisibility(View.GONE);
                holder.tv_name.setVisibility(View.VISIBLE);
                holder.iv_icon.setVisibility(View.VISIBLE);
                holder.tv_name.setText(items.getName());
                holder.iv_icon.setImageResource(items.getIconId());
                Log.i(TAG, "onBindViewHolder: " + items.getName());

            }
    }

    @Override
    public int getItemCount() {
        return drawerMenuList.size();
    }

    public class ViewHolderMenus extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_name;
        ImageView iv_icon;
        View view_divider;

        public ViewHolderMenus(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            view_divider = itemView.findViewById(R.id.view_divider);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (context instanceof MainActivity){
                MainActivity mainActivity=(MainActivity) context;
                mainActivity.onDrawerMenuItemClicked(drawerMenuList.get(getAbsoluteAdapterPosition()));
            }
        }
    }
}
