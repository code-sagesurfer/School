package com.sagesurfer.school.home.dailyplanner;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sagesurfer.school.ItemDetailView;
import com.sagesurfer.school.ModelDetailData;
import com.sagesurfer.school.R;
import com.sagesurfer.school.home.DataPlanner;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.home.ModelPlannerResponse;
import com.sagesurfer.school.home.adapters.AdapterPlannerData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentPlannerMain extends Fragment {
    DataPlanner dataPlanner;
    static RecyclerView rv_planner;
    TextView tv_error_msg;
    private static final String TAG = "FragmentPlannerMain";
    MainActivity mainActivity;
    public FragmentPlannerMain() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            mainActivity.setToolbarTitleText(getString(R.string.menu_daily_planner));
            mainActivity.changeDrawerIcon(false);
            mainActivity.toggleBellIcon(false);

        }
    }

    public static FragmentPlannerMain newInstance(String param1, String param2) {
        FragmentPlannerMain fragment = new FragmentPlannerMain();
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
        View view = inflater.inflate(R.layout.fragment_planner_main, container, false);
        rv_planner = view.findViewById(R.id.rv_planner);
        tv_error_msg = view.findViewById(R.id.tv_error_msg);
        dataPlanner= new DataPlanner();
        RecyclerView.LayoutManager mLayoutManagerPlanner = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_planner.setLayoutManager(mLayoutManagerPlanner);
        rv_planner.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(currentTime);
        dataPlanner.getPlannerData(0, 50, strDate, TAG, getContext(), getActivity(), FragmentPlannerMain.this);
    }

    public void setPlannerData(ModelPlannerResponse plannerResponse) {
        if (plannerResponse.getGetData().get(0).getStatus() .equalsIgnoreCase("1")) {
            AdapterPlannerData adapterPlannerData = new AdapterPlannerData(getContext(), plannerResponse.getGetData(), FragmentPlannerMain.this);
            rv_planner.setAdapter(adapterPlannerData);
            tv_error_msg.setVisibility(View.GONE);
            rv_planner.setVisibility(View.VISIBLE);
            Log.i(TAG, "onResponse: getPlannerData " + plannerResponse.getGetData().get(0).getStatus());
        } else {
            tv_error_msg.setVisibility(View.VISIBLE);
            rv_planner.setVisibility(View.GONE);
        }
    }

    public void showDetailDialog(ModelDetailData modelPlannerData) {
        ItemDetailView detailView = new ItemDetailView();
        detailView.showDetailDialog(getActivity(), getContext(), TAG,modelPlannerData);
    }
}