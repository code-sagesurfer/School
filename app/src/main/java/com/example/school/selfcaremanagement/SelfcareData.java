package com.example.school.selfcaremanagement;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.school.R;
import com.example.school.emotional_support.FragmentEmotionalSupport;
import com.example.school.home.HomeFragment;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * @author Rahul Maske(monikam@sagesurfer.com)
 *         Created on 28-05-2022
 *         Last Modified on
 */
public class SelfcareData {
    FragmentEmotionalSupport fragmentEmotionalSupport;
    int previous_min=0;
    static boolean selfCareFirstTimeLoading;
    private ArrayList<Content_> contentArrayList;
    public void fetchSelfcareNewData(int min, int max, Context context, Activity activity, String TAG, boolean firstTimeLoading, Fragment fragment) {
        try {
            selfCareFirstTimeLoading=firstTimeLoading;
            previous_min = min;
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put(General.TIMEZONE, Preferences.get(General.TIMEZONE));
            requestMap.put(General.ACTION, Actions_.GET_DATA);
            requestMap.put(General.SEARCH, "");
            requestMap.put(General.CATEGORY, "0");
            requestMap.put(General.LANGUAGE, "0");
            requestMap.put(General.LIKE, "0");
            requestMap.put(General.COMMENT, "0");
            requestMap.put(General.TYPE, "" + "0");
            requestMap.put(General.COUNTRY, "" + "0");
            requestMap.put(General.STATE, "" + "0");
            requestMap.put(General.CITY, "" + "0");
            requestMap.put(General.PERSONAL, "0");
            requestMap.put(General.HC_ROLE_ID, Preferences.get(General.ROLE_ID));
            requestMap.put(General.AGE, "0");
            requestMap.put(General.MIN, "" + min);
            requestMap.put(General.MAX, "" + max);
            String url = Preferences.get(General.DOMAIN) + "/" + Urls_.SELF_CARE_URL;
            APIManager.Companion.getInstance().showProgressDialog(context, false, context.getResources().getString(R.string.loading));
            RequestBody requestBody = MakeCall.make(requestMap, url, TAG, activity, activity);


            APIManager.Companion.getInstance().fetchselfcare(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        AppLog.i(TAG, "fetchTeamDetailsNew onResponse: " + response.body().toString());
                        JsonElement mJsonElement = response.body();
                        Gson gson = new Gson();
                        ModelSelfCareListResponse teamResponse = gson.fromJson(mJsonElement.toString(), ModelSelfCareListResponse.class);
                        if (response != null) {
                            contentArrayList = teamResponse.getGet_data();
                            if (contentArrayList.size() > 0) {
                                if (contentArrayList.get(0).getStatus() == 1) {
                                    if (selfCareFirstTimeLoading) {
                                        Log.i(TAG, "onResponse: fetch firstTimeLoading");
                                        selfCareFirstTimeLoading = false;
                                        if (fragment instanceof FragmentEmotionalSupport) {
                                            fragmentEmotionalSupport = (FragmentEmotionalSupport) fragment;
                                            fragmentEmotionalSupport.setDataList(contentArrayList);
                                        }


                                    } else {
                                        Log.i(TAG, "onResponse: fetch not firstTimeLoading");
                                        //careContentListAdapter.addData(contentArrayList);

                                    }

                                } else {
                                    // showError(true, contentArrayList.get(0).getStatus());
                                }
                            } else {
                                //showError(true, 12);
                            }
//
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
}