package com.example.school.team_care;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.home.adapters.AdapterPlannerData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTeamMembersListing extends RecyclerView.Adapter<AdapterTeamMembersListing.ViewHolderTeamMembersListing> {
    private ArrayList<CometChatTeamMembers_> cometChatTeamMemberList;
    private Context context;
    private FragmentTeamDetailsListing fragmentTeamDetailsListing;

    public AdapterTeamMembersListing(ArrayList<CometChatTeamMembers_> cometChatTeamMemberList,
                                     Context context, FragmentTeamDetailsListing fragmentTeamDetailsListing) {
        this.cometChatTeamMemberList = cometChatTeamMemberList;
        this.context = context;
        this.fragmentTeamDetailsListing = fragmentTeamDetailsListing;
    }

    @NonNull
    @Override
    public ViewHolderTeamMembersListing onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_team_details_listing_item, parent, false);
        return new ViewHolderTeamMembersListing(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTeamMembersListing holder, int position) {
        CometChatTeamMembers_ item= cometChatTeamMemberList.get(position);
        if (item.getA() != null) {
            Glide.with(context)
                    .load(item.getA())
                    .placeholder(context.getDrawable(R.drawable.placeholder))
                    .into(holder.iv_user_profile);
        }

        holder.tv_team_list_user_name.setText(item.getName());
        holder.tv_team_list_user_name.setText(item.getRole());

    }

    @Override
    public int getItemCount() {
        return cometChatTeamMemberList.size();
    }

    public class ViewHolderTeamMembersListing extends RecyclerView.ViewHolder{
        private TextView tv_team_list_user_name,tv_team_user_role;
        private CircleImageView iv_user_profile;
        public ViewHolderTeamMembersListing(@NonNull View itemView) {
            super(itemView);
            tv_team_list_user_name=itemView.findViewById(R.id.tv_team_list_user_name);
            tv_team_user_role=itemView.findViewById(R.id.tv_team_user_role);
            iv_user_profile=itemView.findViewById(R.id.iv_user_profile);

        }
    }
}
