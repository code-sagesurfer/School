package com.example.school.team_care;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.school.R;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTeamDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTeamDetails extends Fragment {
    @BindView(R.id.tv_team_name)
    TextView tv_team_name;

     @BindView(R.id.iv_user_profile)
     CircleImageView iv_user_profile;

     @BindView(R.id.cv_bottom_seven_plus_panel)
     CardView cv_bottom_seven_plus_panel;

    ModelTeamListResponse teamData;
    private static final String TAG = "FragmentTeamDetails";
    public FragmentTeamDetails() {

    }


    public static FragmentTeamDetails newInstance(String param1, String param2) {
        FragmentTeamDetails fragment = new FragmentTeamDetails();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(General.TEAM_DATA)){
                teamData=getArguments().getParcelable(General.TEAM_DATA);
                Log.i(TAG, "onCreate: Team name "+teamData.getAllTeams().get(0).getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_team_details, container, false);
        ButterKnife.bind(this,view);

        tv_team_name.setText(teamData.getAllTeams().get(0).getName());
        if (Preferences.get(General.IMAGE) != null) {
            Glide.with(getContext())
                    .load(Preferences.get(General.IMAGE))
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user_profile);

        }
        if (teamData.getAllTeams().get(0).getMembers()>6){
            cv_bottom_seven_plus_panel.setVisibility(View.VISIBLE);
        }else{
            cv_bottom_seven_plus_panel.setVisibility(View.GONE);
        }
        return view;
    }
}