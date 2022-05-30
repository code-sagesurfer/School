package com.example.school.home;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.school.emotional_support.FragmentEmotionalSupport;
import com.example.school.home.ui.interfaces.MoodDataListner;
import com.example.school.resources.APIManager;
import com.example.school.resources.Actions_;
import com.example.school.resources.General;
import com.example.school.resources.GetCounters;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoodData {
    private int previous_min;
    boolean firstTimeLoading = true;
    public ArrayList<MoodJournal_> journalArrayList;
    private static final String TAG = "MoodData";
    MoodDataListner moodDataListner;
    HomeFragment fragmentHome;
    FragmentEmotionalSupport fragmentEmotionalSupport;
    public void fetchJournalMoodDataNew(int min, int max, Context context, Activity activity, Fragment fragment) {
        Calendar cal = Calendar.getInstance();
        previous_min = min;
        //int status = 11;
        String month = GetCounters.checkDigit(cal.get(Calendar.MONTH) + 1);
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.MOOD_STATUS);
        requestMap.put(General.DATE, "" + 0);
        requestMap.put(General.MONTH, "" + month);
        requestMap.put(General.YEAR, "" + cal.get(Calendar.YEAR));
        requestMap.put(General.CONSUMER_ID, Preferences.get(General.CONSUMER_ID));
        requestMap.put(General.MIN, "" + min);
        requestMap.put(General.MAX, "" + max);
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_MOOD_DASHBOARD;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, context, activity);

        APIManager.Companion.getInstance().showProgressDialog(context, true, "Loading....");

//        ApiService mApiService = ApiClient.getClient(activity.getApplicationContext(), Preferences.get(Constants.DOMAIN) + "/").create(ApiService.class);
//        Call<JsonElement> call = mApiService.fetchMoodData(requestBody);
        if (requestBody != null) {
            APIManager.Companion.getInstance().mobile_mood_dashboard(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    JsonElement mJsonElement = response.body();
                    Gson gson = new Gson();
                    if (mJsonElement != null) {
                        ModelMoodListResponse moodListResponse = gson.fromJson(mJsonElement.toString(), ModelMoodListResponse.class);
                        if (response != null) {
                            if (moodListResponse.getMoodDataList().get(0).getStatus() != 2) {
                                journalArrayList = moodListResponse.getMoodDataList();
                                if (journalArrayList.size() > 0) {
                                    if (firstTimeLoading) {
                                        firstTimeLoading = false;
                                        if (journalArrayList.get(0).getData().get(0).getMood().get(0).getStatus() == 1) {
                                            if (fragment instanceof HomeFragment) {
                                                fragmentHome = (HomeFragment) fragment;
                                                fragmentHome.moodDataResponse(journalArrayList.get(0).getData().get(0).getMood(), context);
                                            } else if (fragment instanceof FragmentEmotionalSupport) {
                                                fragmentEmotionalSupport = (FragmentEmotionalSupport) fragment;
                                                fragmentEmotionalSupport.moodDataResponse(journalArrayList.get(0).getData().get(0).getMood(), context);
                                            }
                                        } else {
                                            fragmentHome.moodDataResponseFailed();
                                        }
                                    } else {
                                        Log.i(TAG, "onResponse: fetchTeamDetailsNew not firstTimeLoading");
                                    }
                                } else {

                                }
                            } else {

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    //showError(true, 12);
                }
            });
        }else{
            APIManager.Companion.getInstance().dismissProgressDialog();
        }
    }
}
