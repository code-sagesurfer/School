package com.example.school.selfcaremanagement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.home.MainActivity;
import com.example.school.social_activity.SocialActivityFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSelfcareManagement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelfcareManagement extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SelfcareData selfcareData;
    RecyclerView rv_educational,rv_spiritual,rv_Campus_Resources,rv_Nutritional_Guidance;
    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;
    TextView tv_error_msg_education,tv_error_msg_campus,tv_error_msg_spiritual,tv_error_msg_nutri;
    private static final String TAG = "FragmentSelfcareManagem";
    public FragmentSelfcareManagement() {

    }

    public static FragmentSelfcareManagement newInstance(String param1, String param2) {
        FragmentSelfcareManagement fragment = new FragmentSelfcareManagement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            mainActivity=(MainActivity) context;
            mainActivity.setToolbarTitleText(getString(R.string.menu_self_care));
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
        View view = inflater.inflate(R.layout.fragment_selfcare_management, container, false);
        selfcareData=new SelfcareData();

        rv_educational=view.findViewById(R.id.rv_educational);
        rv_spiritual=view.findViewById(R.id.rv_spiritual);
        rv_Nutritional_Guidance=view.findViewById(R.id.rv_Nutritional_Guidance);
        rv_Campus_Resources=view.findViewById(R.id.rv_Campus_Resources);
        tv_error_msg_campus=view.findViewById(R.id.tv_error_msg_campus);
        tv_error_msg_education=view.findViewById(R.id.tv_error_msg_education);
        tv_error_msg_nutri=view.findViewById(R.id.tv_error_msg_nutri);
        tv_error_msg_spiritual=view.findViewById(R.id.tv_error_msg_spiritual);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_educational.setLayoutManager(layoutManager);
        rv_educational.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_spiritual.setLayoutManager(layoutManager2);
        rv_spiritual.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_Campus_Resources.setLayoutManager(layoutManager3);
        rv_Campus_Resources.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_Nutritional_Guidance.setLayoutManager(layoutManager4);
        rv_Nutritional_Guidance.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        selfcareData.fetchSelfcareNewData(0, 30, getContext(), getActivity(), TAG, true, FragmentSelfcareManagement.this);
    }

    public void setDataList(ArrayList<Content_> contentArrayList) {
        AdapterSelfcareData selfcareDataAdapter = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
        rv_spiritual.setAdapter(selfcareDataAdapter);

        AdapterSelfcareData selfcareDataAdapter2 = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
        rv_educational.setAdapter(selfcareDataAdapter2);

        AdapterSelfcareData selfcareDataAdapter3 = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
        rv_Nutritional_Guidance.setAdapter(selfcareDataAdapter3);

        AdapterSelfcareData selfcareDataAdapter4 = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
        rv_Campus_Resources.setAdapter(selfcareDataAdapter4);
    }

    public void showError() {
        rv_spiritual.setVisibility(View.GONE);
        rv_educational.setVisibility(View.GONE);
        rv_Campus_Resources.setVisibility(View.GONE);
        rv_Nutritional_Guidance.setVisibility(View.GONE);
        tv_error_msg_campus.setVisibility(View.VISIBLE);
        tv_error_msg_education.setVisibility(View.VISIBLE);
        tv_error_msg_campus.setVisibility(View.VISIBLE);
        tv_error_msg_campus.setVisibility(View.VISIBLE);
    }
}