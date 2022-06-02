package com.example.school.moodtracking;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.school.R;
import com.example.school.home.Mood;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMoodTrackingListing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMoodTrackingListing extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static RecyclerView rv_mood_list;

    private String mParam1;
    private String mParam2;

    public FragmentMoodTrackingListing() {
        // Required empty public constructor
    }


    public static FragmentMoodTrackingListing newInstance(String param1, String param2) {
        FragmentMoodTrackingListing fragment = new FragmentMoodTrackingListing();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mood_tracking_listing, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_mood_list=view.findViewById(R.id.rv_mood_list);

        RecyclerView.LayoutManager mLayoutManagerPlanner = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_mood_list.setLayoutManager(mLayoutManagerPlanner);
        rv_mood_list.setItemAnimator(new DefaultItemAnimator());

        DashboardData dashboardData=new DashboardData();
        dashboardData.getDashboardData(getContext(),getActivity(),this);
    }

    public void setDataToMoodList(List<Mood> mood, Activity activity) {
        if (!mood.isEmpty()) {
            MoodAdapter mMoodAdapter = new MoodAdapter(mood, activity);
            rv_mood_list.setAdapter(mMoodAdapter);
            //textviewnodatfound3.setVisibility(View.GONE);
            //recyclerView3.setVisibility(View.VISIBLE);
        }
    }
}