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

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 31/05/2022
 * Last Modified on
 */
public class StatesFetch {
    ArrayList<GetState> stateArrayList;
    public void loadStateList(int counrtyId, String Tag, Context context, Activity activity) {

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.ACTION, Actions_.GET_STATE);
        requestMap.put(General.COUNTRY_ID, String.valueOf(counrtyId));
        requestMap.put(General.USER_ID, Preferences.get(General.USER_ID));

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.SELF_CARE_URL;
        RequestBody requestBody = MakeCall.make(requestMap, url, Tag, context, activity);
        if (requestBody != null) {

            APIManager.Companion.getInstance().showProgressDialog(activity, false, "Loading States...");

            APIManager.Companion.getInstance().mobile_self_care(requestBody, new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    APIManager.Companion.getInstance().dismissProgressDialog();
                    try {
                        if (response!=null){
                            JsonElement element = response.body();
                            Gson gson = new Gson();

                            Log.i(Tag, "onResponse: loadStateList "+response.body().toString());
                            ModelStatesResponse modelStatesResponse = gson.fromJson(response.body(), ModelStatesResponse.class);
                            stateArrayList = new ArrayList<>();
                            stateArrayList=modelStatesResponse.getGetState();
                            /*if (fragment instanceof FragmentEditProfile){
                                FragmentEditProfile editProfile=(FragmentEditProfile) fragment;
                                editProfile.setStateData(stateArrayList);
                            }*/

                            FragmentEditProfile fragmentEditProfile=new FragmentEditProfile();
                            fragmentEditProfile.setStateData(stateArrayList);
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
           /* try {
                String response = MakeCall.post(url, requestBody, Tag, context, activity);
                hideDialog();
                if (response != null) {
                    stateArrayList = new ArrayList<>();
                    stateArrayList.addAll(CaseloadParser_.parseStudentList(response, Actions_.GET_STATE, getContext(), TAG));

                    if (stateArrayList.get(0).getStatus() == 1) {
                        ArrayList<String> stateNameList = new ArrayList<String>();
                        for (int i = 0; i < stateArrayList.size(); i++) {
                            stateNameList.add(stateArrayList.get(i).getName());
                        }

                        if (stateNameList.size() > 0) {
                            ArrayAdapter<String> adapterConsumer = new ArrayAdapter<String>(getContext(), R.layout.drop_down_selected_text_item_layout, stateNameList);
                            adapterConsumer.setDropDownViewResource(R.layout.drop_down_text_item_layout);
                            spinnerStateList.setAdapter(adapterConsumer);

                            for (int i = 0; i < stateArrayList.size(); i++) {
                                if (Integer.parseInt(Preferences.get(General.STATE_ID)) == stateArrayList.get(i).getId()) {
                                    //for default selection of state
                                    spinnerStateList.setSelection(i);
                                    break;
                                }
                            }

                            spinnerStateListOne.setVisibility(View.GONE);
                            spinnerStateList.setVisibility(View.VISIBLE);
                            spinnerCityListOne.setVisibility(View.GONE);
                            spinnerCityList.setVisibility(View.VISIBLE);
                        }

                    } else {
                        spinnerStateListOne.setVisibility(View.VISIBLE);
                        spinnerStateList.setVisibility(View.GONE);
                        spinnerCityListOne.setVisibility(View.VISIBLE);
                        spinnerCityList.setVisibility(View.GONE);

                        if (showMsg) {
                            stateArrayList.clear();
                            Toast.makeText(getContext(), "State: No Data", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }


}
