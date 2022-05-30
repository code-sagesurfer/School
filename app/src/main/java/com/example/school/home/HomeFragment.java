package com.example.school.home;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;

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
import android.widget.Toast;

import com.example.school.FragmentAdequateSleep;
import com.example.school.R;
import com.example.school.databinding.FragmentHomeBinding;
import com.example.school.emotional_support.FragmentEmotionalSupport;
import com.example.school.home.adapters.AdapterGratitudeJournalingList;
import com.example.school.home.adapters.AdapterPlannerData;
import com.example.school.home.adapters.AdaptersMoodData;
import com.example.school.home.ui.ModelGratitudeListingResponse;
import com.example.school.home.ui.interfaces.MoodDataListner;
import com.example.school.intakeconsent.FragmentIntakeConsentMain;
import com.example.school.nutrition.FragmentProperNutrition;
import com.example.school.physical_activity.FragmentPhysicalActivityMain;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.GetCounters;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.example.school.resources.oauth.SaveData;
import com.example.school.skill_development.FragmentSkillDevelopment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on
 * Last Modified on 26/05/2022
 */
public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MoodData moodData;
    JournalingData journalingData;
    FragmentHomeBinding homeBinding;
    private String mParam1;
    private String mParam2;
    ArrayList<ModelGratitudeListingResponseData> dataArrayList;
    private int previous_min;
    boolean firstTimeLoading = true;
    public ArrayList<MoodJournal_> journalArrayList;
    private static final String TAG = "MoodData";
    RecyclerView rv_mood;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = homeBinding.getRoot();
        homeBinding.clPhysicalActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragmentPhysicalActivity();
            }
        });

        homeBinding.clSkillDevelopment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragmentSkillDevelopment();
            }
        });

        moodData = new MoodData();
        journalingData=new JournalingData();
        //rv_mood = getView().findViewById(R.id.rv_mood);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeBinding.rvMood.setLayoutManager(mLayoutManager);
        homeBinding.rvMood.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManagerJournaling = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeBinding.rvJournaling.setLayoutManager(mLayoutManagerJournaling);
        homeBinding.rvJournaling.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager mLayoutManagerPlanner = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        homeBinding.rvPlanner.setLayoutManager(mLayoutManagerPlanner);
        homeBinding.rvPlanner.setItemAnimator(new DefaultItemAnimator());

        homeBinding.clEmotionalSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragmentEmotionalSupport();
            }
        });

        homeBinding.clProperNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragmentProperNutrition();
            }
        });

        homeBinding.clSocialActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialActivityFragment();
            }
        });

        homeBinding.clAdequateSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFragmentAdequateSleep();
            }
        });


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        moodData.fetchJournalMoodDataNew(0, 50, getContext(), getActivity(), HomeFragment.this);
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(currentTime);
        journalingData.getGratitudes("", strDate, "", "",getContext(),getActivity(),this);

        getPlannerData(0, 20, strDate);
    }

    private void openFragmentAdequateSleep() {
        Log.i("MY_TAG", "onClick: ");
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentAdequateSleep(), "FragmentAdequateSleep");
        //ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    private void openFragmentSkillDevelopment() {
        Log.i("MY_TAG", "onClick: ");
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentSkillDevelopment(), "FragmentSkillDevelopment");
        //ft.addToBackStack("HomeFragment");
        ft.commit();
    }


    private void openFragmentPhysicalActivity() {
        Log.i("MY_TAG", "onClick: ");
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentPhysicalActivityMain(), "FragmentPhysicalActivityMain");
        //ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    private void openFragmentEmotionalSupport() {
        Log.i("MY_TAG", "onClick: ");
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentEmotionalSupport(), "FragmentEmotionalSupport");
        //ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    private void openFragmentProperNutrition() {
        Log.i("MY_TAG", "onClick: ");
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentProperNutrition(), "FragmentProperNutrition");
        //ft.addToBackStack("HomeFragment");
        ft.commit();
    }

    private void openSocialActivityFragment() {
        Log.i("MY_TAG", "onClick: ");
        FragmentManager fragManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        ft.replace(R.id.main_container, new FragmentIntakeConsentMain(), "SocialActivityFragment");
        //ft.addToBackStack("HomeFragment");
        ft.commit();
    }


    /*"action=get_gratitude_list
           userid=10206
           date=//Selected date otherwise current date year-month-day fromat
           search=test"*/
    private void getGratitudes(String searchText, String Date, String startDate, String endDate) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_GRATITUDE_LIST);
        requestMap.put(General.SEARCH, searchText);
        //requestMap.put(Constants.DATE, formattedDate);
        requestMap.put(General.DATE, "" + Date);
        requestMap.put("userid", "" + Preferences.get(General.USER_ID));
        requestMap.put(General.FROM_DATE, "" + startDate);
        requestMap.put(General.TO_DATE, "" + endDate);

        APIManager.Companion.getInstance().showProgressDialog(getContext(), false,
                getActivity().getString(R.string.loading));
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_GRATITUDE_JOURNALING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getActivity(), getActivity());
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().get_gratitude_list(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            Gson gson = new Gson();
                            assert response.body() != null;
                            String resposeBody = response.body().toString();
                            AppLog.i(TAG, "onResponse: " + resposeBody);
                            ModelGratitudeListingResponse gratitudeListingResponse = gson.fromJson(response.body(), ModelGratitudeListingResponse.class);
                            if (gratitudeListingResponse.getStatus() == 200) {
                                dataArrayList = gratitudeListingResponse.getData();
                                if (dataArrayList.size() > 0) {
                                    AdapterGratitudeJournalingList adapterGratitudeJournalingList = new AdapterGratitudeJournalingList(dataArrayList, getContext());
                                    homeBinding.rvJournaling.setAdapter(adapterGratitudeJournalingList);
                                } else {

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
        } else {
            //showError(true, status);
        }
    }


    private void getPlannerData(int min, int max, String date) {
        Log.i(TAG, "getSagesurfer: calling");

        APIManager.Companion.getInstance().showProgressDialog(getContext(), true, "Loading....");
        HashMap<String, String> requestMap = new HashMap<>();

        requestMap.put(General.ACTION, Actions_.GET_DATA);
        requestMap.put("fetch_date", date);
        requestMap.put(General.MIN, "" + min);
        requestMap.put(General.MAX, "" + max);

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_DAY_PLANNER;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, getContext(), getActivity());

        AppLog.e(TAG, "getSagesurfer: " + requestBody);
        if (requestBody != null) {
            APIManager.Companion.getInstance().mobile_day_planner(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        Gson gson = new Gson();
                        assert response.body() != null;
                        String resposeBody = response.body().toString();
                        AppLog.i(TAG, "onResponse: " + resposeBody);
                        ModelPlannerResponse plannerResponse = gson.fromJson(response.body(), ModelPlannerResponse.class);
                        AdapterPlannerData adapterPlannerData = new AdapterPlannerData(getContext(), plannerResponse.getGetData());
                        homeBinding.rvPlanner.setAdapter(adapterPlannerData);

                        Log.i(TAG, "onResponse: getPlannerData " + plannerResponse.getGetData().get(0).getStatus());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                }
            });
        }else{
            APIManager.Companion.getInstance().dismissProgressDialog();
        }
    }


    public void moodDataResponse(ArrayList<MoodJournalDataMood_> dataList, Context context) {
        Toast.makeText(context, "data received", Toast.LENGTH_SHORT).show();
        AdaptersMoodData journalListAdapter = new AdaptersMoodData(getActivity(), dataList);
        this.homeBinding.rvMood.setAdapter(journalListAdapter);
        // this.rv_mood.setAdapter(journalListAdapter);

    }

    public void moodDataResponseFailed() {
    }

    public void journalingData(ArrayList<ModelGratitudeListingResponseData> dataArrayList) {
        AdapterGratitudeJournalingList adapterGratitudeJournalingList = new AdapterGratitudeJournalingList(dataArrayList, getContext());
        homeBinding.rvJournaling.setAdapter(adapterGratitudeJournalingList);
    }
}