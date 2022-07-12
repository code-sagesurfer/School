package com.sagesurfer.school.home;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.sagesurfer.school.R;
import com.sagesurfer.school.emotional_support.FragmentEmotionalSupport;
import com.sagesurfer.school.home.ui.ModelGratitudeListingResponse;
import com.sagesurfer.school.journaling.JournalingMainListing;
import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataJournaling {

    private static final String TAG = "JournalingData";
    ArrayList<ModelGratitudeListingResponseData> dataArrayList;
    HomeFragment fragmentHome;
    JournalingMainListing journalingMainListing;
    FragmentEmotionalSupport fragmentEmotionalSupport;

    /*"action=get_gratitude_list
           userid=10206
           date=//Selected date otherwise current date year-month-day fromat
           search=test"*/
    public void fetchGratitudesDataList(String searchText, String Date, String startDate, String endDate, Context context, Activity activity, Fragment fragment) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_GRATITUDE_LIST);
        requestMap.put(General.SEARCH, searchText);
        //requestMap.put(Constants.DATE, formattedDate);
        requestMap.put(General.DATE, "" + Date);
        requestMap.put("userid", "" + Preferences.get(General.USER_ID));
        requestMap.put(General.FROM_DATE, "" + startDate);
        requestMap.put(General.TO_DATE, "" + endDate);

        APIManager.Companion.getInstance().showProgressDialog(context, false,
                activity.getString(R.string.loading));
        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.MOBILE_GRATITUDE_JOURNALING;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, activity, activity);
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
                                    if (fragment instanceof HomeFragment){
                                        fragmentHome =(HomeFragment)fragment;
                                        fragmentHome.journalingData(dataArrayList);
                                    }else if(fragment instanceof FragmentEmotionalSupport){
                                        fragmentEmotionalSupport =(FragmentEmotionalSupport)fragment;
                                        fragmentEmotionalSupport.journalingData(dataArrayList);
                                    }else if(fragment instanceof JournalingMainListing){
                                        journalingMainListing =(JournalingMainListing)fragment;
                                        journalingMainListing.journalingData(dataArrayList);
                                    }
                                } else {
                                    showErrorMessage(fragment);
                                }
                            } else {
                                showErrorMessage(fragment);

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
            APIManager.Companion.getInstance().dismissProgressDialog();
        }
    }

    public void showErrorMessage(Fragment fragment){
        if (fragment instanceof HomeFragment){
            fragmentHome =(HomeFragment)fragment;
            fragmentHome.journalingDataFailed();
        }else if(fragment instanceof FragmentEmotionalSupport){
            //fragmentEmotionalSupport =(FragmentEmotionalSupport)fragment;
//                                        fragmentEmotionalSupport.journalingData(dataArrayList);
        }else if(fragment instanceof JournalingMainListing){
            journalingMainListing =(JournalingMainListing)fragment;
            journalingMainListing.showEmptyDataMessage();
        }
    }

}
