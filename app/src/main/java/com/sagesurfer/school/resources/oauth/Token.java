package com.sagesurfer.school.resources.oauth;

import android.app.Activity;
import android.content.Context;

import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.General;
import com.sagesurfer.school.resources.Urls_;
import com.sagesurfer.school.resources.callbacks.TokenCallbacks;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 *         Created on 20/05/2022
 *         Last Modified on
 */

public class Token {

    private TokenCallbacks tokenCallbacks;
    private static final String TAG = Token.class.getSimpleName();
    Context context;

    public Token(Activity activity) {
        tokenCallbacks = (TokenCallbacks) activity;
        OauthPreferences.initialize(activity);
    }

    public void getToken(String user_name, String password, String domain, Context _context) {
        this.context = _context;
        RequestBody authBody = new FormBody.Builder()
                .add("client_id", user_name)
                .add("client_secret", password)
                .add("redirect_uri", domain)
                .add("scope", "android")
                .add("grant_type", "authorization_code")
                .add("code", OauthPreferences.get(Oauth.CODE))
                .build();

        Call_ call_ = new Call_(domain + Urls_.TOKEN, authBody, TAG, context);
        try {
            String json = call_.execute().get();
            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has(General.ACCESS_TOKEN)) {
                    AppLog.i(TAG, "getToken: "+jsonObject.toString());
                   SaveData.saveTokenData(jsonObject,_context);
                   tokenCallbacks.tokenSuccessCallback(jsonObject);


                } else {
                   tokenCallbacks.tokenFailCallback(new JSONObject());
                    AppLog.i(TAG, "getToken: "+jsonObject.toString());
                }
            } else {
                //tokenCallbacks.tokenFailCallback(new JSONObject(Warnings.ERROR_NETWORK_JSON));
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }
}
