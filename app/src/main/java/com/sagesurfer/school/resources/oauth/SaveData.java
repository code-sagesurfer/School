package com.sagesurfer.school.resources.oauth;

import android.content.Context;
import android.util.Log;

import com.sagesurfer.school.resources.AppLog;
import com.sagesurfer.school.resources.General;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */
public class SaveData {
    private static final String TAG = "SaveData";

    static void saveTokenData(JSONObject jsonObject, Context context) {
        OauthPreferences.initialize(context);
        if (jsonObject.has(Oauth.ACCESS_TOKEN) && jsonObject.has(Oauth.REFRESH_TOKEN)) {
            Gson gson = new Gson();
            ModelTokenResponse response = gson.fromJson(jsonObject.toString(), ModelTokenResponse.class);
            OauthPreferences.save(Oauth.ACCESS_TOKEN, response.getAccessToken());
            OauthPreferences.save(Oauth.REFRESH_TOKEN, response.getRefreshToken());
            OauthPreferences.save(Oauth.SCOPE, response.getScope());
            Log.i(TAG, General.MY_TEST_TAG+"saveTokenData: ACCESS_TOKEN " +OauthPreferences.get(Oauth.ACCESS_TOKEN));
            Log.i(TAG, General.MY_TEST_TAG+"saveTokenData  REFRESH_TOKEN "+OauthPreferences.get(Oauth.REFRESH_TOKEN));
            Log.i(TAG, General.MY_TEST_TAG+"saveTokenData: SCOPE "+OauthPreferences.get(Oauth.SCOPE));

            Long expires_in = response.getExpiresIn();
            Long sys_time = System.currentTimeMillis();
            Long expires_at = (expires_in * 1000) + sys_time;
            OauthPreferences.save(Oauth.EXPIRES_IN, "" + expires_in);
            OauthPreferences.save(Oauth.EXPIRES_AT, "" + expires_at);
            AppLog.i(TAG, "saveTokenData: ");
        }
    }

    static ModelToken getRefreshToken(JSONObject jsonObject, Context context) throws JSONException {

        try {
            OauthPreferences.initialize(context);

            if (jsonObject.has(Oauth.ACCESS_TOKEN) && jsonObject.has(Oauth.REFRESH_TOKEN)) {
                Gson gson = new Gson();
                ModelTokenResponse response = gson.fromJson(jsonObject.toString(), ModelTokenResponse.class);
                OauthPreferences.save(Oauth.ACCESS_TOKEN, response.getAccessToken());
                OauthPreferences.save(Oauth.REFRESH_TOKEN, response.getRefreshToken());
                OauthPreferences.save(Oauth.SCOPE, response.getScope());
                Long expires_in = response.getExpiresIn();
                Long sys_time = System.currentTimeMillis();
                Long expires_at = (expires_in * 1000) + sys_time;
                OauthPreferences.save(Oauth.EXPIRES_IN, "" + expires_in);
                OauthPreferences.save(Oauth.EXPIRES_AT, "" + expires_at);
                AppLog.i(TAG, "saveTokenData: ");
                return new ModelToken(jsonObject.getLong(Oauth.EXPIRES_IN),
                        jsonObject.getString(Oauth.SCOPE), jsonObject.getString(Oauth.REFRESH_TOKEN),
                        jsonObject.getString(Oauth.ACCESS_TOKEN), 1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
