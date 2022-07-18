package com.sagesurfer.school.team_care;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sagesurfer.school.R;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.resources.General;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentTeamDetailsListing extends Fragment {
    private ArrayList<CometChatTeamMembers_> cometChatTeamMemberList;
    private static final String TAG = "FragmentTeamDetailsList";
    MainActivity mainActivity;
    @BindView(R.id.rv_team_listing)
    RecyclerView rv_team_listing;

    @BindView(R.id.tv_team_name)
    TextView tv_team_name;
    ModelTeamListResponse teamData;
    public FragmentTeamDetailsListing() {
        // Required empty public constructor
    }


    public static FragmentTeamDetailsListing newInstance(String param1, String param2) {
        FragmentTeamDetailsListing fragment = new FragmentTeamDetailsListing();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cometChatTeamMemberList = getArguments().getParcelableArrayList(General.TEAM_LIST);
            teamData=getArguments().getParcelable(General.TEAM_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_details_listing, container, false);
        ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_team_listing.setLayoutManager(layoutManager);
        rv_team_listing.setItemAnimator(new DefaultItemAnimator());


        setDataToTeamMemberList();
        return view;
    }

    private void setDataToTeamMemberList() {
        tv_team_name.setText(teamData.getAllTeams().get(0).getName());
        AdapterTeamMembersListing adapter = new AdapterTeamMembersListing(cometChatTeamMemberList, getContext(), this);
        rv_team_listing.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity) {
            mainActivity = (MainActivity) getContext();
            mainActivity.setToolbarTitleText(getString(R.string.Personal_Support_Team));
            mainActivity.changeDrawerIcon(true);
        }
    }
}