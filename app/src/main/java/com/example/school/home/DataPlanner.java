package com.example.school.home;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.school.home.adapters.AdapterPlannerData;
import com.example.school.home.dailyplanner.FragmentPlannerMain;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPlanner {

    public void getPlannerData(int min, int max, String date, String TAG, Context context, Activity activity, Fragment fragment) {
        Log.i(TAG, "get Sagesurfer: calling");
        APIManager.Companion.getInstance().showProgressDialog(context, true, "Loading....");
        HashMap<String, String> requestMap = new HashMap<>();

        requestMap.put(General.ACTION, Actions_.GET_DATA);
        requestMap.put("fetch_date", date);
        requestMap.put(General.MIN, "" + min);
        requestMap.put(General.MAX, "" + max);

        String url = Preferences.get(General.DOMAIN) + Urls_.MOBILE_DAY_PLANNER;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, context, activity);

        AppLog.e(TAG, "getPlannerData getSagesurfer: " + requestBody);
        if (requestBody != null) {
            APIManager.Companion.getInstance().mobile_day_planner(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        Gson gson = new Gson();
                        assert response.body() != null;
                        String resposeBody = response.body().toString();
                        AppLog.i(TAG, "getPlannerData onResponse: " + resposeBody);
                        ModelPlannerResponse plannerResponse = gson.fromJson(response.body(), ModelPlannerResponse.class);
                        if (fragment instanceof HomeFragment){
                            HomeFragment homeFragment=(HomeFragment) fragment;
                            homeFragment.setPlannerData(plannerResponse);
                        }else if (fragment instanceof FragmentPlannerMain){
                            FragmentPlannerMain fragmentPlannerMain=(FragmentPlannerMain) fragment;
                            fragmentPlannerMain.setPlannerData(plannerResponse);
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
        }else{
            APIManager.Companion.getInstance().dismissProgressDialog();
        }
    }
}
