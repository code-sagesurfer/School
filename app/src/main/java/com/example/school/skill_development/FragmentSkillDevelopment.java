package com.example.school.skill_development;

import static com.example.school.home.BaseFragment.MAX_DEFAULT_VALUE_LARGE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.school.R;
import com.example.school.goalmanagement.FragmentAddGoal;
import com.example.school.home.main.MainActivity;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSkillDevelopment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSkillDevelopment extends Fragment implements SelfGoalListAdapter.GoalListAdapterListener {

    @BindView(R.id.tv_total_count)
    TextView tv_total_count;

    @BindView(R.id.tv_active)
    TextView tv_active;

    @BindView(R.id.fb_add_goal)
    FloatingActionButton fb_add_goal;

    @BindView(R.id.tv_missed)
    TextView tv_missed;

    @BindView(R.id.tv_completed)
    TextView tv_completed;

    @BindView(R.id.rv_goals)
    RecyclerView rv_goals;

    SelfGoalListAdapter goalListAdapter;
    private int Min = 0;
    private ArrayList<Goal_> goalArrayList = new ArrayList<>();
    private int Max = 10;
    boolean isLoading=true;
    int previousMax = 0;
    int previousMin = 0;
    boolean isLastPage=false;
    MainActivity mainActivity;
    boolean firstTimeLoading=true;
    private static final String TAG = "FragmentSkillDevelopment";
    public FragmentSkillDevelopment() {
        // Required empty public constructor
    }


    public static FragmentSkillDevelopment newInstance(String param1, String param2) {
        FragmentSkillDevelopment fragment = new FragmentSkillDevelopment();
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
        View view = inflater.inflate(R.layout.fragment_skill_development, container, false);
        ButterKnife.bind(this,view);
        RecyclerView.LayoutManager mLayoutManagerJournaling = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_goals.setLayoutManager(mLayoutManagerJournaling);
        rv_goals.setItemAnimator(new DefaultItemAnimator());
        fb_add_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentAddGoal developmentDetails=new FragmentAddGoal();
                FragmentManager fm=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.main_container,developmentDetails,TAG);
                ft.addToBackStack("FragmentSkillDevelopment");
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getContext() instanceof MainActivity){
            mainActivity =(MainActivity) getContext();
            mainActivity.setToolbarTitleText("Goal Management ");
            mainActivity.changeDrawerIcon(false);

            mainActivity.toggleBellIcon(false);
        }
        firstTimeLoading = true;
        getAllGoalNew(0,50);
        getGoalsNew();


    }

    private void getAllGoalNew(int min, int max) {
        previousMax = max;
        previousMin = min;
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.ALL_GOAL);
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_SELF_GOAL;
        requestMap.put(General.MIN, "" + previousMin);
        requestMap.put(General.MAX, "" + previousMax);

        APIManager.Companion.getInstance().showProgressDialog(getContext(), true, getActivity().getString(R.string.loading));
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().mobile_self_goal(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();

                        try {
                            JsonElement element = response.body();

                            Gson gson = new Gson();
                            ModelSelfGoalListResponse moodListResponse = gson.fromJson(element.toString(), ModelSelfGoalListResponse.class);
                            isLoading = false;

                            Min = Max + 1;
                            Max = Max + MAX_DEFAULT_VALUE_LARGE;
                            AppLog.e(TAG, " getSagesurfer PlannerResponse" + response);
                            if (response != null) {
                                goalArrayList = moodListResponse.getGoalArrayList();
                                if (goalArrayList.get(0).getStatus() == 1) {
                                    if (firstTimeLoading) {
                                        firstTimeLoading = false;
                                        //showError(false, 1);

                                        //errorLayout.setVisibility(View.GONE);
                                        //recycleViewFragmentSelfGoalMain.setVisibility(View.VISIBLE);
                                       goalListAdapter = new SelfGoalListAdapter(getActivity(), goalArrayList,
                                                FragmentSkillDevelopment.this);
                                        rv_goals.setAdapter(goalListAdapter);
                                    } else {
                                        goalListAdapter.addData(goalArrayList);
                                    }
                                } else {
                                    isLastPage = true;
                                    //showError(false, 1);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /*    //old calling code
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.ALL_GOAL);
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_SELF_GOAL;
        RequestBody requestBody = NetworkCall_.make(requestMap, url, TAG, activity, activity);
        if (requestBody != null) {
            try {
                String response = NetworkCall_.post(url, requestBody, TAG, activity, activity);
                Log.e("ResponseGetAllGoal", "" + response);
                if (response != null) {
                    goalArrayList = SelfGoal_.parseSpams(response, Actions_.ALL_GOAL, activity.getApplicationContext(), TAG);
                    if (goalArrayList.size() > 0) {
                        if (goalArrayList.get(0).getStatus() == 1) {
                            showError(false, 1);
                            SelfGoalListAdapter goalListAdapter = new SelfGoalListAdapter(activity, goalArrayList, this);
                            recycleViewFragmentSelfGoalMain.setAdapter(goalListAdapter);
                        } else {
                            showError(true, 2);
                        }
                    }
                } else {
                    showError(true, 2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void onGoalItemClicked(int position, Goal_ goal_) {
        FragmentSkillDevelopmentDetails developmentDetails=new FragmentSkillDevelopmentDetails();
        Bundle bundle= new Bundle();
        bundle.putSerializable(General.GOAL_OBJ,goal_);
        developmentDetails.setArguments(bundle);
        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.main_container,developmentDetails,TAG);
        ft.addToBackStack("FragmentSkillDevelopment");
        ft.commit();

    }



    // make network call to fetch goal counters based on goal status
    private void getGoalsNew() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.MY_GOAL);
        APIManager.Companion.getInstance().showProgressDialog(getContext(), true, getActivity().getString(R.string.loading));
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_SELF_GOAL;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());
        String response;
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().mobile_self_goal(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            JsonElement element = response.body();

                            Gson gson = new Gson();
                            ModelGetGoalResponse getGoalResponse = gson.fromJson(element.toString(), ModelGetGoalResponse.class);
                            if (getGoalResponse.getCounter().get(0).getStatus() == 1) {
                                ArrayList<ModelCounter> modelCounterArrayList = getGoalResponse.getCounter();
                                if (modelCounterArrayList.size() > 0) {
                                    setGoalCounter(getGoalResponse.getCounter().get(0).getCompleted(),
                                            getGoalResponse.getCounter().get(0).getMissOut(),getGoalResponse.getCounter().get(0).getRunning(),getGoalResponse.getCounter().get(0).getTotal());
                                }
                            } else {
                                setGoalCounter("0", "0", "0", "0");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//        getAllGoal();
    }

    // set goal counters to respective text fields
    private void setGoalCounter(String completed, String miss_out, String running, String total) {
        tv_completed.setText(completed);
        tv_missed.setText(miss_out);
        tv_active.setText(running);
        tv_total_count.setText(total);
    }


}