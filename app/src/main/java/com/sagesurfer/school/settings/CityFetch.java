package com.sagesurfer.school.settings;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.sagesurfer.school.resources.APIManager;
import com.sagesurfer.school.resources.Actions_;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Preferences;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.apidata.MakeCall;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityFetch {
    private static final String TAG = "CityFetch";
    public void loadCityList(int stateId, Context context, Activity activity) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_CITY);
        requestMap.put(General.STATE_ID, String.valueOf(stateId));
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.SELF_CARE_URL;
        RequestBody requestBody = MakeCall.make(requestMap, url, TAG, context, activity);
        if (requestBody != null) {
            try {
                APIManager.Companion.getInstance().showProgressDialog(activity, false, "Loading...");

                APIManager.Companion.getInstance().mobile_self_care(requestBody, new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        APIManager.Companion.getInstance().dismissProgressDialog();
                        try {
                            if (response!=null){
                                JsonElement element = response.body();
                                Gson gson = new Gson();
                                Log.i(TAG, "onResponse: loadStateList "+response.body().toString());
                                ModelStatesResponse modelStatesResponse = gson.fromJson(response.body(), ModelStatesResponse.class);
                                /*if (fragment instanceof FragmentEditProfile){
                                    FragmentEditProfile editProfile=(FragmentEditProfile) fragment;

                                    editProfile.setCityData(modelStatesResponse.getGet_city());
                                }*/
                                FragmentEditProfile editProfile=new FragmentEditProfile();
                                editProfile.setCityData(modelStatesResponse.getGet_city());


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


                /*String response = NetworkCall_.post(url, requestBody, TAG, context, activity);
                hideDialog();
                if (response != null) {
                    cityArrayList = new ArrayList<>();
                    cityArrayList.addAll(CaseloadParser_.parseStudentList(response, Actions_.GET_CITY, getContext(), TAG));

                    if (cityArrayList.get(0).getStatus() == 1) {

                        ArrayList<String> cityNameList = new ArrayList<String>();
                        for (int i = 0; i < cityArrayList.size(); i++) {
                            cityNameList.add(cityArrayList.get(i).getName());
                        }

                        if (cityNameList.size() > 0) {
                            ArrayAdapter<String> adapterConsumer = new ArrayAdapter<String>(getContext(), R.layout.drop_down_selected_text_item_layout, cityNameList);
                            adapterConsumer.setDropDownViewResource(R.layout.drop_down_text_item_layout);
                            spinnerCityList.setAdapter(adapterConsumer);

                            for (int i = 0; i < cityArrayList.size(); i++) {
                                if (Integer.parseInt(Preferences.get(General.CITY_ID)) == cityArrayList.get(i).getId()) {
                                    //for default selection of state
                                    spinnerCityList.setSelection(i);
                                    break;
                                }
                            }
                            spinnerCityListOne.setVisibility(View.GONE);
                            spinnerCityList.setVisibility(View.VISIBLE);
                        }

                    } else {
                        spinnerCityListOne.setVisibility(View.VISIBLE);
                        spinnerCityList.setVisibility(View.GONE);
                        if (showMsg) {
                            cityArrayList.clear();
                            Toast.makeText(getContext(), "City : No Data", Toast.LENGTH_LONG).show();
                        }
                    }
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
