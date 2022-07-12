package com.sagesurfer.school.moodtracking;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.sagesurfer.school.home.HomeFragment;
import com.sagesurfer.school.home.YouthResponseModel;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardData {
    private static final String TAG = "DashboardData";
    Fragment fragment;
    public void getDashboardData(Context context, Activity activity, Fragment fragment) {
        this.fragment=fragment;
        APIManager.Companion.getInstance().showProgressDialog(context, true, "Loading....");

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, "get_youth_dashboard");
        requestMap.put("fetch_date", formattedDate);

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_YOUTH_DASHBOARD;

        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, context, activity);

        assert requestBody != null;
        APIManager.Companion.getInstance().mobile_youth_dashboard(requestBody, new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                APIManager.Companion.getInstance().dismissProgressDialog();

                try {
                    JsonElement element = response.body();

//                    Log.i(DashBoardFragment.class.getSimpleName(), "Response :" + element.toString());
//                    JSONObject serverResponse = null;
                    //                  serverResponse = new JSONObject(element.toString());
                    Gson gson = new Gson();
                    YouthResponseModel mDashBoardResponse = gson.fromJson(element.toString(), YouthResponseModel.class);
                   /* if (!mDashBoardResponse.getDaily_planner().isEmpty()) {
                        mMyPlanAdapter = new MyPlanAdapter(mDashBoardResponse.getDaily_planner(), context, activity);
                        recyclerView.setAdapter(mMyPlanAdapter);

                        textviewnodatfound1.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }*/

                  /*  if (!mDashBoardResponse.getRecent_updates().isEmpty()) {
                        mRecentUpdateAdapter = new RecentUpdateAdapter(mDashBoardResponse.getRecent_updates(), getContext(), DashBoardHomeYouthFragment.this);
                        recyclerView4.setAdapter(mRecentUpdateAdapter);
                        buttoncounter.setVisibility(View.VISIBLE);
                        buttoncounter.setText(mDashBoardResponse.getRecent_updates().size() + "");
                        textviewnodatfound4.setVisibility(View.GONE);
                    } else {
                        textviewnodatfound4.setVisibility(View.VISIBLE);
                        recyclerView4.setAdapter(null);
                        buttoncounter.setVisibility(View.GONE);
                    }*/

                    //FragmentMoodTrackingListing listing=new FragmentMoodTrackingListing();

                    if (fragment instanceof FragmentMoodTrackingListing){
                        FragmentMoodTrackingListing listing=(FragmentMoodTrackingListing) fragment;
                        listing.setDataToMoodList(mDashBoardResponse.getMood(),activity);
                    }else if (fragment instanceof HomeFragment){
                        HomeFragment homeFragment=(HomeFragment) fragment;
                        homeFragment.moodDataResponse2(mDashBoardResponse.getMood(),activity);
                    }
                   /* if (!mDashBoardResponse.getGratitude_journal().isEmpty()) {
                        mJornlingAdapter = new JornlingAdapter(mDashBoardResponse.getGratitude_journal(),
                                getContext(),
                                DashBoardHomeYouthFragment.this);
                        recyclerView2.setAdapter(mJornlingAdapter);

                        textviewnodatfound2.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.VISIBLE);
                    }*/


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                APIManager.Companion.getInstance().dismissProgressDialog();
            }
        });

    }
}
