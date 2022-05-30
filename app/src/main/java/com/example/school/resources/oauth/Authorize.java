package com.example.school.resources.oauth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;
import com.example.school.resources.apidata.APIInterface;
import com.example.school.resources.apidata.LoginAPIManager;
import com.example.school.resources.apidata.KeyMaker_;
import com.example.school.resources.apidata.MakeCall;
import com.example.school.resources.callbacks.AuthorizationCallbacks;
import com.google.gson.Gson;
import com.google.gson.JsonElement;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 *         Created on 20/05/2022
 *         Last Modified on
 */
public class Authorize {
    private static final String TAG = "Authorize";

    private AuthorizationCallbacks callbacks;


    public Authorize(Activity activity) {
        callbacks = (AuthorizationCallbacks) activity;
        OauthPreferences.initialize(activity);
    }

    public void authorizeUserForApp(String client_id, String client_secret, String domain, Context _context, Activity activity) {
        HashMap<String, String> keyMap = KeyMaker_.getKey();
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put(General.CLIENT_ID, client_id);
        requestMap.put(General.CLIENT_SECRET, client_secret);
        requestMap.put(General.REDIRECT_URI, domain);
        requestMap.put(General.STATE, "android");
        requestMap.put(General.SCOPE, "android");
        requestMap.put(General.RESPONSE_TYPE, "code");

        String url = Preferences.get(General.DOMAIN) + "/" + Urls_.AUTHORIZE;
        APIInterface apiInterface = LoginAPIManager.getClient().create(APIInterface.class);
        RequestBody requestBody = MakeCall.makeLoginCall(requestMap, url, TAG, _context, activity);

        Call<JsonElement> jsonElementCall = apiInterface.doAuthorised(requestBody);
        jsonElementCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.e(TAG, "onResponse: " + response.toString());
                JsonElement mJsonElement = response.body();
                Gson gson = new Gson();
                /*ModelMainResponse mRewardsCategoryResponseModel = gson.fromJson(String.valueOf(response.body()), ModelMainResponse.class);
                Log.e(TAG, "onResponse: "+mRewardsCategoryResponseModel.getDrawer().get(0).getMenu());*/

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e(TAG, "onResponse: " + t.getMessage());
            }
        });
    }

    public void getAuthorized(String user_name, String password, String domain, Context _context) {
        RequestBody authBody = new FormBody.Builder()
                .add("client_id", user_name)
                .add("client_secret", password)
                .add("redirect_uri", domain)
                .add("state", "android")
                .add("scope", "android")
                .add("response_type", "code")
                .build();
        Call_ call_ = new Call_(domain + Urls_.AUTHORIZE, authBody, TAG, _context);
        try {
            String json = call_.execute().get();
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                AppLog.i(TAG, "getAuthorized: " + jsonObject.toString());
                Gson gson = new Gson();
                ModelAuthorizationResponse response = gson.fromJson(jsonObject.toString(), ModelAuthorizationResponse.class);

                int result = response.getStatus();
                if (result == 1) {
                    OauthPreferences.save(Oauth.CODE, jsonObject.getString(Oauth.CODE));
                    callbacks.authorizationSuccessCallback(jsonObject);

                } else {
                    callbacks.authorizationFailCallback(new JSONObject());
                }
            } else {
                AppLog.i(TAG, "getAuthorized: null object");
                //callbacks.authorizationFailCallback(new JSONObject(ErrorGenerator_.errorAuthorization(12)));
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
            AppLog.i(TAG, "catch block");
        }
    }
}
