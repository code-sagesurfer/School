package com.sagesurfer.school.social_activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sagesurfer.school.R;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.sagesurfer.school.selfcaremanagement.AdapterSelfcareData;
import com.sagesurfer.school.selfcaremanagement.Content_;
import com.sagesurfer.school.selfcaremanagement.SelfcareData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sagesurfer.school.team_care.AllTeam;
import com.sagesurfer.school.team_care.CometChatTeamMembers_;
import com.sagesurfer.school.team_care.FragmentTeamDetails;
import com.sagesurfer.school.team_care.ModelCometchatTeamListResponse;
import com.sagesurfer.school.team_care.ModelTeamListResponse;
import com.sagesurfer.school.team_care.TeamMembers;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocialActivityFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SelfcareData selfcareData;
    private String mParam1;
    private String mParam2;
    RecyclerView rv_events, rv_social_activity, rv_gamification;
    private static final String TAG = "SocialActivityFragment";
    BalanceResponse mRewardsCategoryResponseModel;
    MainActivity mainActivity;
    private ArrayList<TeamMembers> cometChatTeamMemberList = new ArrayList<>();
    private ArrayList<CometChatTeamMembers_> cometChatTeamMemberListMain = new ArrayList<>();

    @BindView(R.id.iv_user1)
    CircleImageView iv_user1;

    @BindView(R.id.iv_user2)
    CircleImageView iv_user2;

    @BindView(R.id.iv_user3)
    CircleImageView iv_user3;

    @BindView(R.id.tv_user_count)
    TextView tv_user_count;

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.view_user_count)
    View view_user_count;

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
        ButterKnife.bind(this, view);
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
        tv_user_name.setText(Preferences.get(General.NAME));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        selfcareData.fetchSelfcareNewData(0, 30, getContext(), getActivity(), TAG, true, SocialActivityFragment.this);
        getBalanceNew(0, 50);
        getTeamListFromServer();
    }

    private void getTeamListFromServer() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.ALL_TEAMS);
        requestMap.put(General.CODE, Preferences.get(General.DOMAIN_CODE));
        requestMap.put(General.ISFORTEAMCHAT, "0");
       /* if (isTeamDetails) {
            requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));
            requestMap.put(General.GROUP_ID, Preferences.get(General.GROUP_ID));
        }*/
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_NORMAL_TEAMS;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getActivity(), getActivity());
        try {
            APIManager.Companion.getInstance().get_team_list(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        Gson gson = new Gson();
                        assert response.body() != null;
                        String resposeBody = response.body().toString();
                        AppLog.i(TAG, "getTeamListFromServer onResponse: " + resposeBody);
                        ModelTeamListResponse teamListResponse = gson.fromJson(response.body(), ModelTeamListResponse.class);
                        if (teamListResponse.getAllTeams().get(0).getStatus() == 1) {
                            if (teamListResponse.getAllTeams().size() == 1) {
                                showImagesInBottomView(teamListResponse);
                            } else {
                                Toast.makeText(getActivity(), "Please login with adult..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
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
        /*FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        ft.replace(R.id.main_container, new FragmentTeamDetails(), "FragmentTeamDetails");
        ft.addToBackStack("HomeFragment");
        ft.commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);*/
    }



    private void setTeamData() {

    }

    private void showImagesInBottomView(ModelTeamListResponse teamListResponse) {
        Log.i(TAG, "showImagesInBottomView: ");

        tv_user_name.setText(teamListResponse.getAllTeams().get(0).getName());

        cometChatTeamMemberList = teamListResponse.getAllTeams().get(0).getMembersList();;
        if (cometChatTeamMemberList.get(0).getPhoto() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMemberList.get(0).getPhoto())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user1);
        }
        if (cometChatTeamMemberList.get(0).getPhoto() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMemberList.get(0).getPhoto())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user2);
        }
        if (cometChatTeamMemberList.get(0).getPhoto() != null) {
            Glide.with(getContext())
                    .load(cometChatTeamMemberList.get(0).getPhoto())
                    .placeholder(getContext().getDrawable(R.drawable.placeholder))
                    .into(iv_user3);
        }

        if (cometChatTeamMemberList.size() == 1) {
            iv_user1.setVisibility(View.VISIBLE);
            iv_user2.setVisibility(View.GONE);
            iv_user3.setVisibility(View.GONE);
            view_user_count.setVisibility(View.GONE);
        } else if (cometChatTeamMemberList.size() == 2) {
            iv_user1.setVisibility(View.VISIBLE);
            iv_user2.setVisibility(View.VISIBLE);
            iv_user3.setVisibility(View.GONE);
            view_user_count.setVisibility(View.GONE);
        } else if (cometChatTeamMemberList.size() == 3) {
            iv_user1.setVisibility(View.VISIBLE);
            iv_user2.setVisibility(View.VISIBLE);
            iv_user3.setVisibility(View.VISIBLE);
            view_user_count.setVisibility(View.GONE);
        } else if (cometChatTeamMemberList.size() >3) {
            iv_user1.setVisibility(View.VISIBLE);
            iv_user2.setVisibility(View.VISIBLE);
            iv_user3.setVisibility(View.VISIBLE);
            view_user_count.setVisibility(View.VISIBLE);
            tv_user_count.setText(cometChatTeamMemberList.size()-1);
        }
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