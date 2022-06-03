package com.example.school.social_activity;

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

import com.example.school.R;
import com.example.school.emotional_support.FragmentEmotionalSupport;
import com.example.school.home.MainActivity;
import com.example.school.resources.APIManager;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.example.school.selfcaremanagement.AdapterSelfcareData;
import com.example.school.selfcaremanagement.Content_;
import com.example.school.selfcaremanagement.SelfcareData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocialActivityFragment extends Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SelfcareData selfcareData;
    private String mParam1;
    private String mParam2;
    RecyclerView rv_events, rv_social_activity, rv_gamification;
    private static final String TAG = "SocialActivityFragment";
    BalanceResponse mRewardsCategoryResponseModel;
    MainActivity mainActivity;

    public SocialActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            mainActivity.setToolbarTitleText("Social Activity");
        }
    }

    public static SocialActivityFragment newInstance(String param1, String param2) {
        SocialActivityFragment fragment = new SocialActivityFragment();
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
        View view = inflater.inflate(R.layout.fragment_social_activity, container, false);
        rv_events = view.findViewById(R.id.rv_events);
        rv_social_activity = view.findViewById(R.id.rv_social_activity);
        rv_gamification = view.findViewById(R.id.rv_gamification);

        selfcareData = new SelfcareData();
        RecyclerView.LayoutManager mLayoutManagerJournaling = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_events.setLayoutManager(mLayoutManagerJournaling);
        rv_events.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManagerJournaling2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_social_activity.setLayoutManager(mLayoutManagerJournaling2);
        rv_social_activity.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManagerJournaling3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_gamification.setLayoutManager(mLayoutManagerJournaling3);
        rv_gamification.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        selfcareData.fetchSelfcareNewData(0, 30, getContext(), getActivity(), TAG, true, SocialActivityFragment.this);
        getBalanceNew(0, 50);
    }

    public void setDataList(ArrayList<Content_> contentArrayList) {
        AdapterSelfcareData selfcareDataAdapter = new AdapterSelfcareData(getContext(), contentArrayList, SocialActivityFragment.this);
        rv_events.setAdapter(selfcareDataAdapter);

        AdapterSelfcareData selfcareDataAdapter2 = new AdapterSelfcareData(getContext(), contentArrayList, SocialActivityFragment.this);
        rv_social_activity.setAdapter(selfcareDataAdapter2);
    }

    private void getBalanceNew(int min, int max) {
       /* previousMax = max;
        previousMin = min;*/

        APIManager.Companion.getInstance().showProgressDialog(getContext(), true, "Loading....");

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "get_reward_logs");
        requestMap.put(General.MIN, "" + min);
        requestMap.put(General.MAX, "" + max);
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_REWARDS;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getActivity(), getActivity());
        if (requestBody != null) {

//            mApiService = ApiClient.getClient(getActivity(), Preferences.get(General.DOMAIN) + "/").create(ApiService.class);
//            Call<JsonElement> call = mApiService.getRewardsData(requestBody);

            APIManager.Companion.getInstance().getRewardsData(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                    APIManager.Companion.getInstance().dismissProgressDialog();
                    /*isLoading = false;
                    Min = Max + 1;
                    Max = Max + MIN_DEFAULT_VALUE;*/
                    AppLog.i(TAG, "getBalanceNew onResponse: " + response.body());
                    JsonElement mJsonElement = response.body();
                    Gson gson = new Gson();
                    mRewardsCategoryResponseModel = gson.fromJson(String.valueOf(response.body()), BalanceResponse.class);
                    if (mRewardsCategoryResponseModel.getStatus() == 200) {
                      /*  if (isFirstTimeLoading) {
                            isFirstTimeLoading = false;*/
                        if (!mRewardsCategoryResponseModel.getData().isEmpty()) {
                            AdapterReward mRewardsBalanceListAdapter = new AdapterReward(SocialActivityFragment.this,
                                    mRewardsCategoryResponseModel.getData(), getContext());
                            rv_gamification.setAdapter(mRewardsBalanceListAdapter);
                            //textviewtotal.setText(mRewardsCategoryResponseModel.getData().get(0).getTotal() + "");
                            //showError(false, 1);
                        }
                     /*   } else {
                            mRewardsBalanceListAdapter.addDataInList(mRewardsCategoryResponseModel.getData());
                        }*/
                    } else {

                        // isLastPage = true;
                        //showAlertDialog("", mRewardsCategoryResponseModel.getMsg(), 300);
                       /* if (isFirstTimeLoading) {
                            showError(true, 2);
                        }*/
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                }
            });
        }
    }
}