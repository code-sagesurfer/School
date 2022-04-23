package com.example.school.assessment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.school.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAssessmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAssessmentMain extends Fragment {


    public FragmentAssessmentMain() {

    }


    public static FragmentAssessmentMain newInstance(String param1, String param2) {
        FragmentAssessmentMain fragment = new FragmentAssessmentMain();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assessment_main, container, false);
    }
}