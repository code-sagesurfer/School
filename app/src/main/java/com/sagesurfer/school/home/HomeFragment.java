package com.sagesurfer.school.home;

import android.app.Activity;
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

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sagesurfer.school.FragmentAdequateSleep;
import com.sagesurfer.school.ItemDetailView;
import com.sagesurfer.school.ModelDetailData;
import com.sagesurfer.school.R;
import com.sagesurfer.school.databinding.FragmentHomeBinding;
import com.sagesurfer.school.emotional_support.FragmentEmotionalSupport;
import com.sagesurfer.school.home.adapters.AdapterGratitudeJournalingList;
import com.sagesurfer.school.home.adapters.AdapterPlannerData;
import com.sagesurfer.school.home.adapters.AdaptersMoodData;
import com.sagesurfer.school.home.dailyplanner.FragmentPlannerMain;
import com.sagesurfer.school.home.main.MainActivity;
import com.sagesurfer.school.journaling.JournalingMainListing;
import com.sagesurfer.school.moodtracking.DashboardData;
import com.sagesurfer.school.moodtracking.FragmentMoodTrackingListing;
import com.sagesurfer.school.nutrition.FragmentProperNutrition;
import com.sagesurfer.school.physical_activity.FragmentPhysicalActivityMain;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.GetTime;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.sagesurfer.school.skill_development.FragmentSkillDevelopment;
import com.sagesurfer.school.social_activity.SocialActivityFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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
    DataMood dataMood;
    DataJournaling dataJournaling;
    FragmentHomeBinding homeBinding;
    private String mParam1;
    private String mParam2;
    DataPlanner dataPlanner;
    CircleImageView iv_user_profile;
    ArrayList<ModelGratitudeListingResponseData> dataArrayList;
    private int previous_min;
    boolean firstTimeLoading = true;
    public ArrayList<MoodJournal_> journalArrayList;
    private static final String TAG = "MoodData";
    RecyclerView rv_mood;
    MainActivity mainActivity;
    int [] viewIds = {
            R.id.tv_greeting_for_user,
            R.id.iv_greeting_img,
            R.id.iv_user_profile,
            R.id.rv_quotes,
            R.id.cl_physical_activity,
            R.id.cl_skill_development,
            R.id.cl_emotional_support,
            R.id.cl_proper_nutrition,
            R.id.cl_social_activities,
            R.id.cl_adequate_sleep,
            R.id.tv_text_mood,
            R.id.tv_view_all,
            R.id.rv_mood,
            R.id.tv_journaling,
            R.id.tv_view_all_journaling,
            R.id.rv_journaling,
            R.id.tv_planner,
            R.id.tv_view_all_planner,
            R.id.rv_planner,

    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
            mainActivity.setToolbarTitleText(getString(R.string.menu_home));
            mainActivity.changeDrawerIcon(false);
            mainActivity.toggleBellIcon(false);


        }
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



        dataMood = new DataMood();
        dataJournaling = new DataJournaling();
        dataPlanner = new DataPlanner();
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

        setGreetings();

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



        homeBinding.tvViewAllJournaling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragManager.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ft.replace(R.id.main_container, new JournalingMainListing(), "JournalingMainListing");
                ft.addToBackStack("HomeFragment");
                ft.commit();
            }
        });

        homeBinding.tvViewAllPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragManager.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ft.replace(R.id.main_container, new FragmentPlannerMain(), "FragmentPlannerMain");
                ft.addToBackStack("HomeFragment");
                ft.commit();
            }
        });

        homeBinding.tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragManager.beginTransaction();
                //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
                ft.replace(R.id.main_container, new FragmentMoodTrackingListing(), "FragmentMoodTrackingListing");
                ft.addToBackStack("HomeFragment");
                ft.commit();
            }
        });

        if (Preferences.get(General.IMAGE) != null && Preferences.get(General.IMAGE).length() != 0) {
            Glide.with(this)
                    .load(Preferences.get(General.IMAGE))
                    .placeholder(R.drawable.ic_user_male)
                    .error(R.drawable.ic_user_male)
                    .into(homeBinding.ivUserProfile);
        }
        return root;
    }

    private void setGreetings() {
        homeBinding.tvGreetingForUser.setText(GetTime.getGreetingTime());

        if (GetTime.getGreetingTime().equals("Good Morning")){
            homeBinding.ivGreetingImg.setImageResource(R.drawable.ic_good_morning);
        }else if (GetTime.getGreetingTime().equals("Good Afternoon")){
            homeBinding.ivGreetingImg.setImageResource(R.drawable.ic_good_afternoon);
        }else if (GetTime.getGreetingTime().equals("Good Evening")){
            homeBinding.ivGreetingImg.setImageResource(R.drawable.good_evening);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //moodData.fetchJournalMoodDataNew(0, 50, getContext(), getActivity(), HomeFragment.this);
        showAnimationEffect();

        DashboardData dashboardData = new DashboardData();
        dashboardData.getDashboardData(getContext(), getActivity(),
                HomeFragment.this);

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(currentTime);
        dataJournaling.fetchGratitudesDataList("", strDate, "", "", getContext(), getActivity(), this);
        dataPlanner.getPlannerData(0, 50, strDate, TAG, getContext(), getActivity(), HomeFragment.this);

    }

    private void showAnimationEffect() {

        for(int itemId :viewIds) {
            YoYo.with(Techniques.ZoomIn)
                    .duration(800)
                    .repeat(0)
                    .playOn(getView().findViewById(itemId));
        }
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
        ft.replace(R.id.main_container, new SocialActivityFragment(), "SocialActivityFragment");
        //ft.addToBackStack("HomeFragment");
        ft.commit();
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
                        if (plannerResponse.getGetData().get(0).getStatus() .equalsIgnoreCase("1")) {
                            AdapterPlannerData adapterPlannerData = new AdapterPlannerData(getContext(), plannerResponse.getGetData(), HomeFragment.this);
                            homeBinding.rvPlanner.setAdapter(adapterPlannerData);
                            homeBinding.tvErrorMsg3.setVisibility(View.GONE);
                            homeBinding.rvPlanner.setVisibility(View.VISIBLE);
                            Log.i(TAG, "onResponse: getPlannerData " + plannerResponse.getGetData().get(0).getStatus());
                        } else {
                            homeBinding.tvErrorMsg3.setVisibility(View.VISIBLE);
                            homeBinding.rvPlanner.setVisibility(View.GONE);
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
        } else {
            APIManager.Companion.getInstance().dismissProgressDialog();
        }
    }


    public void moodDataResponse(ArrayList<MoodJournalDataMood_> dataList, Context context) {
        //Toast.makeText(context, "data received", Toast.LENGTH_SHORT).show();
        AdaptersMoodData journalListAdapter = new AdaptersMoodData(getActivity(), dataList);
        this.homeBinding.rvMood.setAdapter(journalListAdapter);
        this.homeBinding.rvMood.setVisibility(View.VISIBLE);
        this.homeBinding.tvErrorMsg.setVisibility(View.GONE);
        // this.rv_mood.setAdapter(journalListAdapter);
    }

    public void moodDataResponse2(List<Mood> mood, Activity activity) {
        //Toast.makeText(context, "data received", Toast.LENGTH_SHORT).show();
        MoodAdapterForHomeListing journalListAdapter = new MoodAdapterForHomeListing(activity, mood);
        this.homeBinding.rvMood.setAdapter(journalListAdapter);
        this.homeBinding.rvMood.setVisibility(View.VISIBLE);
        this.homeBinding.tvErrorMsg.setVisibility(View.GONE);
        // this.rv_mood.setAdapter(journalListAdapter);
    }

    public void moodDataResponseFailed() {
        this.homeBinding.rvMood.setVisibility(View.GONE);
        this.homeBinding.tvErrorMsg.setVisibility(View.VISIBLE);
    }


    public void journalingData(ArrayList<ModelGratitudeListingResponseData> dataArrayList) {
        AdapterGratitudeJournalingList adapterGratitudeJournalingList = new AdapterGratitudeJournalingList(dataArrayList,
                getContext());
        homeBinding.rvJournaling.setAdapter(adapterGratitudeJournalingList);
    }

    public void journalingDataFailed() {
        this.homeBinding.rvJournaling.setVisibility(View.GONE);
        this.homeBinding.tvErrorMsg2.setVisibility(View.VISIBLE);
    }

    public void showDetailDialog(ModelDetailData modelPlannerData) {
        ItemDetailView detailView = new ItemDetailView();
        detailView.showDetailDialog(getActivity(), getContext(), TAG, modelPlannerData);
    }

    public void setPlannerData(ModelPlannerResponse plannerResponse) {
        if (plannerResponse.getGetData().get(0).getStatus().equalsIgnoreCase("1")) {
            AdapterPlannerData adapterPlannerData = new AdapterPlannerData(getContext(), plannerResponse.getGetData(), HomeFragment.this);
            this.homeBinding.rvPlanner.setAdapter(adapterPlannerData);
            this.homeBinding.tvErrorMsg3.setVisibility(View.GONE);
            this.homeBinding.rvPlanner.setVisibility(View.VISIBLE);
            Log.i(TAG, "onResponse: getPlannerData " + plannerResponse.getGetData().get(0).getStatus());
        } else {
            this.homeBinding.tvErrorMsg3.setVisibility(View.VISIBLE);
            this.homeBinding.rvPlanner.setVisibility(View.GONE);
        }
    }
}