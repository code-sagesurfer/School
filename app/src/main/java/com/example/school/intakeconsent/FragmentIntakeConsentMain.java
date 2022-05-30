package com.example.school.intakeconsent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.school.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FragmentIntakeConsentMain extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TabLayout tab_layout_intake;
    TabItem tab1,tab2;
    private IntakePageAdapter pageAdapter;
    ViewPager2 view_pager_intake;
    public FragmentIntakeConsentMain() {
    }

    public static FragmentIntakeConsentMain newInstance(String param1, String param2) {
        FragmentIntakeConsentMain fragment = new FragmentIntakeConsentMain();
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
        View view= inflater.inflate(R.layout.fragment_intake_consent_main, container, false);
        tab_layout_intake = view.findViewById(R.id.tab_layout_intake);
        view_pager_intake = view.findViewById(R.id.view_pager_intake);
        tab1 = view.findViewById(R.id.tab1);
        tab2 = view.findViewById(R.id.tab2);
        pageAdapter = new IntakePageAdapter(getActivity().getApplicationContext(), getActivity(), this);
        view_pager_intake.setAdapter(pageAdapter);

        TabLayoutMediator mediator = new TabLayoutMediator(tab_layout_intake, view_pager_intake,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText(getActivity().getResources().getString(R.string.intake));
                        break;
                    }
                    case 1: {
                        tab.setText(getActivity().getResources().getString(R.string.consent_form));
                        break;
                    }
                }
            }
        });
        mediator.attach();

        return view;
    }
}