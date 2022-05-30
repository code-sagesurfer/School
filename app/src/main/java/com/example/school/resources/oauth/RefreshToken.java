package com.example.school.resources.oauth;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.school.resources.AppLog;
import com.example.school.resources.General;
import com.example.school.resources.Preferences;
import com.example.school.resources.Urls_;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author Rahul Maske (rahul.maske@sagesurfer.com)
 * Created on 26/05/2022
 * Last Modified on
 */

// handle refresh token com.sagesurfer.network call

public class RefreshToken {

    private static final String TAG = RefreshToken.class.getSimpleName();

    public RefreshToken(Context _context) {
        OauthPreferences.initialize(_context);
    }

    public synchronized ModelToken getRefreshToken(String user_name, String password, String domain, Context _context) {


        AppLog.i(General.MY_TEST_TAG, "getRefreshToken: user_name " + user_name);
        AppLog.i(General.MY_TEST_TAG, "getRefreshToken: password " + password);
        AppLog.i(General.MY_TEST_TAG, "getRefreshToken: domain " + domain);
        AppLog.i(General.MY_TEST_TAG, "getRefreshToken: refresh_token " + OauthPreferences.get(Oauth.REFRESH_TOKEN));

        RequestBody authBody = new FormBody.Builder()
                .add("client_id", user_name)
                .add("client_secret", password)
                .add("refresh_token", OauthPreferences.get(Oauth.REFRESH_TOKEN))
                .add("scope", "android")
                .add("grant_type", "refresh_token")
                .add("user_id", Preferences.get(General.USER_ID))
                .build();

        try {
            Call_ call_ = new Call_(domain + Urls_.TOKEN, authBody, TAG, _context);
            String json = call_.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            Log.i(RefreshToken.class.getSimpleName(),"TOKEN Response:" +json);
            Log.i(RefreshToken.class.getSimpleName(),"Domain URL :" +domain + Urls_.TOKEN);
            Log.i(RefreshToken.class.getSimpleName(),"Body :" +authBody.toString());

            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                SaveData.saveTokenData(jsonObject,_context);
                return SaveData.getRefreshToken(jsonObject,_context);
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
