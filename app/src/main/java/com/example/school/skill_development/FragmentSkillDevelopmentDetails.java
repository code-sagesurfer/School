package com.example.school.skill_development;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.home.MainActivity;
import com.example.school.resources.General;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentSkillDevelopmentDetails extends Fragment {

    @BindView(R.id.tv_question_desc)
    TextView tv_question_desc;

    @BindView(R.id.goal_desc)
    TextView goal_desc;

    @BindView(R.id.tv_date)
    TextView tv_date;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Goal_ goal_;
    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;
    private static final String TAG = "FragmentSkillDevelopmen";

    public FragmentSkillDevelopmentDetails() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    public static FragmentSkillDevelopmentDetails newInstance(String param1, String param2) {
        FragmentSkillDevelopmentDetails fragment = new FragmentSkillDevelopmentDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(General.GOAL_OBJ)){
                goal_= (Goal_) getArguments().getSerializable(General.GOAL_OBJ);
                Log.d(TAG, "onCreate: "+goal_.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skill_development_details, container, false);
        ButterKnife.bind(this,view);

        tv_question_desc.setText(goal_.getName());
        tv_date.setText(goal_.getStart_date());
        goal_desc.setText(goal_.getDescription());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity){
            mainActivity =(MainActivity) getContext();
            mainActivity.setToolbarTitleText("Skill Development Details");
            mainActivity.changeDrawerIcon(true);
        }
    }
}