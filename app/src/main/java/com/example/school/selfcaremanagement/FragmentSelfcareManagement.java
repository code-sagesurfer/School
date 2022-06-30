package com.example.school.selfcaremanagement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school.FragmentDetailDisplay;
import com.example.school.ItemDetailView;
import com.example.school.ModelDetailData;
import com.example.school.R;
import com.example.school.home.main.MainActivity;
import com.example.school.resources.General;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSelfcareManagement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelfcareManagement extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SelfcareData selfcareData;
    ArrayList<Content_> dataListMain;
    RecyclerView rv_educational, rv_spiritual, rv_Campus_Resources, rv_Nutritional_Guidance;
    private String mParam1;
    private String mParam2;
    MainActivity mainActivity;
    TextView tv_error_msg_education, tv_error_msg_campus, tv_error_msg_spiritual, tv_error_msg_nutri, tv_education_view, tv_spiritual_view, tv_nutrition_view, tv_Campus_Resources_view;
    private static final String TAG = "FragmentSelfcareManagem";
    ArrayList<ModelDetailData> detailData;

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
        selfcareData = new SelfcareData();

        rv_educational = view.findViewById(R.id.rv_educational);
        rv_spiritual = view.findViewById(R.id.rv_spiritual);
        rv_Nutritional_Guidance = view.findViewById(R.id.rv_Nutritional_Guidance);
        rv_Campus_Resources = view.findViewById(R.id.rv_Campus_Resources);
        tv_error_msg_campus = view.findViewById(R.id.tv_error_msg_campus);
        tv_error_msg_education = view.findViewById(R.id.tv_error_msg_education);
        tv_error_msg_nutri = view.findViewById(R.id.tv_error_msg_nutri);
        tv_error_msg_spiritual = view.findViewById(R.id.tv_error_msg_spiritual);

        tv_education_view = view.findViewById(R.id.tv_education_view);
        tv_nutrition_view = view.findViewById(R.id.tv_nutrition_view);
        tv_spiritual_view = view.findViewById(R.id.tv_spiritual_view);
        tv_Campus_Resources_view = view.findViewById(R.id.tv_Campus_Resources_view);

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

        tv_education_view.setOnClickListener(this);
        tv_nutrition_view.setOnClickListener(this);
        tv_spiritual_view.setOnClickListener(this);
        tv_Campus_Resources_view.setOnClickListener(this);

        dataListMain = new ArrayList<>();
        detailData = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof MainActivity) {
            mainActivity = (MainActivity) getContext();
            mainActivity.setToolbarTitleText(getString(R.string.menu_self_care));
            mainActivity.changeDrawerIcon(false);
        }

        selfcareData.fetchSelfcareNewData(0, 30, getContext(), getActivity(), TAG, true, FragmentSelfcareManagement.this);
    }

    public void setDataList(ArrayList<Content_> contentArrayList) {
        dataListMain.addAll(contentArrayList);
        Log.d(TAG, "setDataList: contentArrayList.size() " + contentArrayList.size());
        if (contentArrayList.size() > 0) {
            for (Content_ content_ : contentArrayList) {
                detailData.add(new ModelDetailData("" + content_.getTitle(), "", "" + content_.getThumb_path(), "" + content_.getDescription()));
                Log.d(TAG, "setDataList: " + content_.getTitle());
            }

            AdapterSelfcareData selfcareDataAdapter = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
            rv_spiritual.setAdapter(selfcareDataAdapter);

            AdapterSelfcareData selfcareDataAdapter2 = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
            rv_educational.setAdapter(selfcareDataAdapter2);

            AdapterSelfcareData selfcareDataAdapter3 = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
            rv_Nutritional_Guidance.setAdapter(selfcareDataAdapter3);

            AdapterSelfcareData selfcareDataAdapter4 = new AdapterSelfcareData(getContext(), contentArrayList, FragmentSelfcareManagement.this);
            rv_Campus_Resources.setAdapter(selfcareDataAdapter4);
        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_education_view:
            case R.id.tv_nutrition_view:
            case R.id.tv_Campus_Resources_view:
            case R.id.tv_spiritual_view:
                openDetailDisplay(view.getId());
                break;
        }
    }

    private void openDetailDisplay(int id) {
        String displayTitle="";
        if (id == R.id.tv_education_view) {
            displayTitle=getString(R.string.educational);
        }else if (id == R.id.tv_nutrition_view) {
            displayTitle=getString(R.string.nutritional_guidance);
        }else if (id == R.id.tv_spiritual_view) {
            displayTitle=getString(R.string.spiritual);
        }else if (id == R.id.tv_Campus_Resources_view) {
            displayTitle=getString(R.string.campus_resources);
        }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("DataList", detailData);
            bundle.putString(General.TOOLBAR_TITLE, ""+displayTitle);
            FragmentDetailDisplay detailDisplay = new FragmentDetailDisplay();
            detailDisplay.setArguments(bundle);

            FragmentManager fragManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragManager.beginTransaction();
            //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            ft.replace(R.id.main_container, detailDisplay, "FragmentDetailDisplay");
            ft.addToBackStack("FragmentSelfcareManagement");
            ft.commit();
    }

    public void showDetailDialog(ModelDetailData modelPlannerData) {
        ItemDetailView detailView = new ItemDetailView();
        detailView.showDetailDialog(getActivity(), getContext(), TAG, modelPlannerData);
    }
}