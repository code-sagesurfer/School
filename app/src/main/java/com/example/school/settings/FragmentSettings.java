package com.example.school.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.home.MainActivity;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 31/05/2022
 * Last Modified on
 */
public class FragmentSettings extends Fragment implements View.OnClickListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView tv_edit_profile,tv_change_password;
    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;
    public FragmentSettings() {
        // Required empty public constructor
    }


    public static FragmentSettings newInstance(String param1, String param2) {
        FragmentSettings fragment = new FragmentSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
            mainActivity.setToolbarTitleText("Settings");

        }
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
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        tv_edit_profile=view.findViewById(R.id.tv_edit_profile);
        tv_change_password=view.findViewById(R.id.tv_change_password);
        tv_edit_profile.setOnClickListener(this);
        tv_change_password.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_edit_profile:
                FragmentManager fragManager =getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragManager.beginTransaction();
                ft.replace(R.id.main_container, new FragmentEditProfile(), "FragmentEditProfile");
                ft.addToBackStack("FragmentSettings");
                ft.commit();
                break;

            case R.id.tv_change_password:
                FragmentManager fragManager1 =getActivity().getSupportFragmentManager();
                FragmentTransaction ft1 = fragManager1.beginTransaction();
                ft1.replace(R.id.main_container, new FragmentChangePassword(), "FragmentChangePassword");
                ft1.addToBackStack("FragmentSettings");
                ft1.commit();
                break;
        }
    }
}