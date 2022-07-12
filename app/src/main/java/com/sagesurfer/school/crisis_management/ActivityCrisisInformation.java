package com.sagesurfer.school.crisis_management;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagesurfer.school.R;

public class ActivityCrisisInformation extends AppCompatActivity {


    public ActivityCrisisInformation() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_crisis_information);
    }
}