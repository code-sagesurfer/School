package com.sagesurfer.school;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.resources.General;

import java.util.ArrayList;

public class FragmentDetailDisplay extends Fragment {
    RecyclerView rv_dataList;
    ArrayList<ModelDetailData> modelDetailDataList;
    String ToolbarTitle;
    private static final String TAG = "FragmentDetailDisplay";
    MainActivity mainActivity;
    public FragmentDetailDisplay() {
        // Required empty public constructor

    }

    public static FragmentDetailDisplay newInstance(String param1, String param2) {
        FragmentDetailDisplay fragment = new FragmentDetailDisplay();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate:");
        if (getArguments() != null) {
            Log.d(TAG, "onCreate: getArguments() != null ");
            modelDetailDataList=getArguments().getParcelableArrayList("DataList");
            Log.d(TAG, "onCreate: "+modelDetailDataList.size());
            if (getArguments().containsKey(General.TOOLBAR_TITLE)){
                ToolbarTitle=getArguments().getString(General.TOOLBAR_TITLE,"");
            }
            if (getContext() instanceof MainActivity){
                mainActivity=(MainActivity) getContext();
                mainActivity.setToolbarTitleText(ToolbarTitle);
                mainActivity.changeDrawerIcon(true);
            }

        }else{
            mainActivity.setToolbarTitleText(getString(R.string.self_care));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_display, container, false);
        rv_dataList=view.findViewById(R.id.rv_dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_dataList.setLayoutManager(mLayoutManager);
        rv_dataList.setItemAnimator(new DefaultItemAnimator());
        setData();
        return view;
    }

    private void setData() {
        Log.d(TAG, "setData: "+modelDetailDataList.size());
        AdapterDetailData adapterDetailData= new AdapterDetailData(getContext(),getActivity(),modelDetailDataList,FragmentDetailDisplay.this);
        rv_dataList.setAdapter(adapterDetailData);
    }

    public void showDetailDialog(ModelDetailData modelDetailData, Context mContext, Activity activity) {
        ItemDetailView detailView = new ItemDetailView();
        detailView.showDetailDialog(activity, mContext, TAG, modelDetailData);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void showDetailDialog(ModelDetailData modelPlannerData) {
        ItemDetailView detailView = new ItemDetailView();
        detailView.showDetailDialog(getActivity(), getContext(), TAG, modelPlannerData);
    }
}